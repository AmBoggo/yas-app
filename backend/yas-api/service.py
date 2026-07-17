"""Camada de serviço — lógica de negócio do YAS.

Separa as regras de negócio dos endpoints HTTP,
facilitando manutenção e testes.
"""

from datetime import date

import httpx

from palavras import palavra_do_dia
from models import listar_favoritos, adicionar_favorito, remover_favorito, registrar_visualizacao

import os
import tempfile

import edge_tts
import httpx

DICTIONARY_API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en"

# Mapas de idioma -> voz do edge-tts
VOZES = {
    "pt": "pt-BR-FranciscaNeural",
    "en": "en-US-JennyNeural",
}


async def gerar_audio(texto: str, lang: str = "en") -> str:
    """Gera um arquivo MP3 com edge-tts e retorna o caminho."""
    voz = VOZES.get(lang, "en-US-JennyNeural")
    arquivo = os.path.join(tempfile.gettempdir(), f"yas_tts_{abs(hash(texto + lang))}.mp3")

    if not os.path.exists(arquivo):
        comunicacao = edge_tts.Communicate(texto, voz)
        await comunicacao.save(arquivo)

    return arquivo


async def buscar_palavra_do_dia(hoje: date | None = None) -> dict:
    """Retorna palavra do dia + detalhes da Free Dictionary API."""
    palavra = palavra_do_dia(hoje)

    # Tenta buscar definição completa da API externa
    try:
        dados_api = await _consultar_api(palavra.texto)
        if dados_api:
            return _formatar_resposta(dados_api)
    except Exception:
        pass  # fallback para dados locais

    # Fallback: dados da nossa lista
    return {
        "palavra": palavra.texto,
        "fonetica": palavra.fonetica,
        "audio_url": "",
        "significados": [
            {
                "classe": "unknown",
                "definicoes": [
                    {
                        "definicao": palavra.definicao,
                        "exemplo": palavra.exemplo,
                    }
                ],
            }
        ],
        "fonte": "local",
    }


async def buscar(q: str) -> list[dict]:
    """Busca uma palavra na Free Dictionary API."""
    dados = await _consultar_api(q)
    if dados is None:
        return []

    registrar_visualizacao(q)
    return [_formatar_resposta(d) for d in dados]


async def _consultar_api(palavra: str) -> list | None:
    """Faz a requisição HTTP para a Free Dictionary API."""
    url = f"{DICTIONARY_API_URL}/{palavra.lower()}"
    async with httpx.AsyncClient(timeout=10) as cliente:
        resposta = await cliente.get(url)
        if resposta.status_code == 200:
            return resposta.json()
        return None


def _formatar_resposta(dados: dict) -> dict:
    """Converte a resposta da API externa para o formato do YAS."""
    significados = []
    for item in dados.get("meanings", []):
        definicoes = [
            {
                "definicao": d.get("definition", ""),
                "exemplo": d.get("example", ""),
            }
            for d in item.get("definitions", [])
            if d.get("definition")
        ]
        significados.append(
            {
                "classe": item.get("partOfSpeech", ""),
                "definicoes": definicoes,
            }
        )

    return {
        "palavra": dados.get("word", ""),
        "fonetica": _extrair_fonetica(dados),
        "audio_url": _extrair_audio(dados),
        "significados": significados,
        "fonte": "api",
    }


def _extrair_fonetica(dados: dict) -> str:
    """Extrai a fonética dos dados da API."""
    foneticas = dados.get("phonetics", [])
    for f in foneticas:
        if f.get("text"):
            return f["text"]
    return dados.get("phonetic", "")


def _extrair_audio(dados: dict) -> str:
    """Extrai a URL do áudio dos dados da API."""
    for f in dados.get("phonetics", []):
        url = f.get("audio", "")
        if url and url.endswith(".mp3"):
            return url
    return ""


# ── Favoritos ──


def get_favoritos() -> list[dict]:
    return listar_favoritos()


def add_favorito(palavra: str, definicao: str, fonetica: str = "") -> dict:
    return adicionar_favorito(palavra, definicao, fonetica)


def delete_favorito(favorito_id: int) -> bool:
    return remover_favorito(favorito_id)