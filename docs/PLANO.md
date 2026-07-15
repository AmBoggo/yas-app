# YAS — Plano Executivo

## Stack
- **Backend:** Python + FastAPI + SQLite (roda no container Hermes, porta 8080)
- **App:** Android nativo (Java) — você desenvolve no PC
- **Comunicação:** REST/JSON via Retrofit

---

## Tarefas (sequenciais)

### Fase 1 — Backend (aqui no servidor)

| # | Tarefa | Status |
|---|--------|--------|
| 1 | Instalar FastAPI + Uvicorn + HTTPX | Pendente |
| 2 | `main.py` — endpoints da API | Pendente |
| 3 | `models.py` — SQLite (tabelas palavras, favoritos) | Pendente |
| 4 | `service.py` — lógica de negócio | Pendente |
| 5 | `palavras.py` — 100 palavras curadas + palavra do dia | Pendente |
| 6 | Testar endpoints com curl | Pendente |

### Fase 2 — App Android (no seu PC com Android Studio)

| # | Tarefa | Status |
|---|--------|--------|
| 7 | Criar projeto Android + Retrofit | Pendente |
| 8 | Tela Home (Palavra do Dia) | Pendente |
| 9 | Tela Busca | Pendente |
| 10 | Tela Favoritos | Pendente |
| 11 | Aplicar design system (roxo pastel) | Pendente |
| 12 | Publicar na Play Store (US$ 25) | Pendente |

---

## Estrutura do Backend

```
/opt/data/projetos/app-vocabulario/backend/yas-api/
├── main.py          ← FastAPI app + endpoints
├── models.py        ← SQLite + CREATE TABLE
├── service.py       ← lógica (buscar, favoritar, palavra do dia)
├── palavras.py      ← 100 palavras + rotação diária
└── requirements.txt ← fastapi, uvicorn, httpx
```

**Boas práticas:** cada arquivo tem uma responsabilidade única. Separação clara entre rota (main), banco (models), regra de negócio (service) e dados (palavras).