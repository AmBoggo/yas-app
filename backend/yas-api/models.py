"""Modelos de dados e acesso ao SQLite para o YAS.

Responsabilidades:
- Criar e gerenciar a conexão com o banco SQLite
- Definir as tabelas: favoritos, palavras_visualizadas
- Operações CRUD de favoritos
"""

import sqlite3
import os
from datetime import datetime, timezone

DB_PATH = os.path.join(os.path.dirname(__file__), "yas.db")


def get_connection() -> sqlite3.Connection:
    """Retorna uma conexão com o banco SQLite."""
    conn = sqlite3.connect(DB_PATH)
    conn.row_factory = sqlite3.Row
    conn.execute("PRAGMA journal_mode=WAL")  # melhor performance
    conn.execute("PRAGMA foreign_keys=ON")
    return conn


def init_db() -> None:
    """Cria as tabelas se não existirem."""
    with get_connection() as conn:
        conn.executescript("""
            CREATE TABLE IF NOT EXISTS favoritos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                palavra TEXT NOT NULL,
                definicao TEXT NOT NULL,
                fonetica TEXT DEFAULT '',
                criado_em TEXT NOT NULL DEFAULT (datetime('now'))
            );

            CREATE UNIQUE INDEX IF NOT EXISTS idx_favoritos_palavra
                ON favoritos(palavra);

            CREATE TABLE IF NOT EXISTS palavras_visualizadas (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                palavra TEXT NOT NULL,
                visualizada_em TEXT NOT NULL DEFAULT (datetime('now'))
            );
        """)


# ── CRUD Favoritos ──


def listar_favoritos() -> list[dict]:
    """Retorna todos os favoritos ordenados do mais recente."""
    with get_connection() as conn:
        rows = conn.execute(
            "SELECT * FROM favoritos ORDER BY criado_em DESC"
        ).fetchall()
        return [dict(row) for row in rows]


def adicionar_favorito(palavra: str, definicao: str, fonetica: str = "") -> dict:
    """Adiciona um favorito. Se já existir, retorna o existente."""
    with get_connection() as conn:
        try:
            cursor = conn.execute(
                "INSERT INTO favoritos (palavra, definicao, fonetica) VALUES (?, ?, ?)",
                (palavra, definicao, fonetica),
            )
            return {
                "id": cursor.lastrowid,
                "palavra": palavra,
                "definicao": definicao,
                "fonetica": fonetica,
                "criado_em": datetime.now(timezone.utc).isoformat(),
            }
        except sqlite3.IntegrityError:
            # Já existe
            row = conn.execute(
                "SELECT * FROM favoritos WHERE palavra = ?", (palavra,)
            ).fetchone()
            return dict(row) if row else {}


def remover_favorito(favorito_id: int) -> bool:
    """Remove um favorito pelo ID. Retorna True se removeu."""
    with get_connection() as conn:
        cursor = conn.execute("DELETE FROM favoritos WHERE id = ?", (favorito_id,))
        return cursor.rowcount > 0


def registrar_visualizacao(palavra: str) -> None:
    """Registra que o usuário visualizou uma palavra."""
    with get_connection() as conn:
        conn.execute(
            "INSERT INTO palavras_visualizadas (palavra) VALUES (?)",
            (palavra,),
        )


def inicializar() -> None:
    """Inicializa o banco na primeira execução."""
    init_db()


# Inicializa ao importar o módulo
inicializar()