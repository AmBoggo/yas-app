"""YAS API — Servidor FastAPI.

Endpoints:
  GET  /api/palavra-do-dia   → palavra em destaque
  GET  /api/buscar?q=hello   → definições da palavra
  POST /api/favoritos        → salvar favorito
  GET  /api/favoritos        → listar favoritos
  DELETE /api/favoritos/{id} → remover favorito
  GET  /health               → status do servidor
"""

from datetime import date

from fastapi import FastAPI, HTTPException, Query
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import FileResponse
from pydantic import BaseModel

from service import (
    buscar_palavra_do_dia,
    buscar,
    gerar_audio,
    get_favoritos,
    add_favorito,
    delete_favorito,
)

app = FastAPI(
    title="YAS — Your Amazing Sentences",
    description="API de descoberta de palavras em inglês",
    version="1.0.0",
)

# Libera acesso de qualquer origem (necessário para o app Android)
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)


# ── Schemas ──


class FavoritoRequest(BaseModel):
    palavra: str
    definicao: str
    fonetica: str = ""


class FavoritoResponse(BaseModel):
    id: int
    palavra: str
    definicao: str
    fonetica: str
    criado_em: str


# ── Endpoints ──


@app.get("/health")
async def health():
    return {"status": "ok", "app": "YAS"}


@app.get("/api/palavra-do-dia")
async def palavra_do_dia():
    """Retorna a palavra em destaque do dia."""
    resultado = await buscar_palavra_do_dia()
    return resultado


@app.get("/api/buscar")
async def buscar_palavra(q: str = Query(..., min_length=1, description="Palavra a ser pesquisada")):
    """Busca definições de uma palavra em inglês."""
    resultado = await buscar(q)
    if not resultado:
        raise HTTPException(
            status_code=404,
            detail=f"Palavra '{q}' não encontrada",
        )
    return resultado


@app.get("/api/tts")
async def text_to_speech(
    texto: str = Query(..., min_length=1, description="Texto a ser lido"),
    lang: str = Query("en", description="Idioma: en ou pt"),
):
    """Gera áudio MP3 do texto usando edge-tts."""
    caminho = await gerar_audio(texto, lang)
    return FileResponse(caminho, media_type="audio/mpeg")


@app.get("/api/favoritos")
async def listar_favoritos():
    """Lista todas as palavras favoritadas."""
    return get_favoritos()


@app.post("/api/favoritos", status_code=201)
async def criar_favorito(body: FavoritoRequest):
    """Salva uma palavra como favorita."""
    resultado = add_favorito(body.palavra, body.definicao, body.fonetica)
    return resultado


@app.delete("/api/favoritos/{favorito_id}")
async def deletar_favorito(favorito_id: int):
    """Remove um favorito pelo ID."""
    if not delete_favorito(favorito_id):
        raise HTTPException(
            status_code=404,
            detail="Favorito não encontrado",
        )
    return {"mensagem": "Favorito removido"}