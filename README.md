# YAS — Your Amazing Sentences 🌟

Expanda seu vocabulário em inglês com uma palavra nova todo dia!

## 🏗️ Estrutura

```
yas-app/
├── backend/           ← API em Python (FastAPI + SQLite)
│   └── yas-api/
│       ├── main.py        → Endpoints REST
│       ├── service.py     → Lógica de negócio
│       ├── models.py      → SQLite
│       ├── palavras.py    → 100 palavras curadas
│       └── requirements.txt
│
├── android/           ← App Android (Java)
│   └── YasApp/
│       └── app/src/main/java/com/yas/
│           ├── api/       → Retrofit client
│           ├── model/     → DTOs
│           ├── ui/        → Activities
│           └── YasApp.java
│
└── docs/              ← Design, planejamento
    ├── PLANO.md
    ├── design-system.md
    └── sketch-yas/
```

## 🚀 Começando

### Backend
```bash
cd backend/yas-api
python3 -m venv .venv
source .venv/bin/activate
pip install -r requirements.txt
uvicorn main:app --host 0.0.0.0 --port 8080
```

### Android
Abra a pasta `android/YasApp` no Android Studio e clique em Run ▶️

## 📡 API

| Endpoint | Descrição |
|----------|-----------|
| `GET /api/palavra-do-dia` | Palavra em destaque |
| `GET /api/buscar?q=hello` | Buscar definições |
| `GET /api/favoritos` | Listar favoritos |
| `POST /api/favoritos` | Salvar favorito |
| `DELETE /api/favoritos/{id}` | Remover favorito |

## 🎨 Design

Roxo pastel (#A78BFA) + fundo off-white. [Ver design system](docs/design-system.md)

## 📄 Licença

MIT