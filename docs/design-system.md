# 🎨 YAS — Design System

## Conceito Geral

"Elegância literária com frescor moderno."
Um app que parece um **caderno de anotações de uma pessoa culta** — com um toque de aquarela sem ser brega.

---

## 🖌️ Paleta de Cores

### Primária — Indigo Suave (#4F46E5 → #818CF8)
Um azul-roxo elegante, sóbrio. Transmite confiança e conhecimento.
Uso: header, botões principais, links.

### Secundária — Terracota Suave (#E8A87C)
Acento quente que contrasta com o azul. Lembra telhas, papel envelhecido, um toque artesanal.
Uso: badges, destaques, coração de favoritos.

### Fundo — Off-white (#F8F6F1)
Não é branco puro — parece papel de verdade. Olhos descansam mais.
Uso: fundo geral.

### Cartão — Branco (#FFFFFF)
Para cards com sombra suave.
Uso: cards de palavras.

### Texto Principal — Charcoal (#2D2B2B)
Quase preto, mas mais suave.
Uso: textos principais.

### Texto Secundário — Stone (#6B7280)
Cinza quente.
Uso: descrições, metadados.

### Aquarela — Gradiente sutil (CSS)
Um gradiente suave entre as cores primária e terracota, com opacidade baixa.
Uso: detalhes decorativos, splash screen.

---

## 🔤 Tipografia

### Títulos — Playfair Display (serifada)
Google Fonts. Elegante, literária, clássica.
Uso: nome do app YAS, palavra do dia em destaque.

### Corpo — Inter (sans-serif)
Limpa, moderna, altamente legível.
Uso: definições, textos, botões.

---

## 📐 Espaçamento

- **4px** — espaço mínimo
- **8px** — padding interno de cards
- **16px** — entre elementos
- **24px** — entre seções
- **32px** — margens laterais

---

## 📦 Componentes

### Card de Palavra
```
┌─────────────────────────────┐
│  ✦ palavra do dia           │  ← badge terracota
│                             │
│  Serendipity                │  ← título serifado, grande
│                             │
│  /ˌserənˈdɪpɪti/           │  ← fonética (cinza)
│                             │
│  The occurrence of events   │
│  by chance in a happy way.  │  ← definição (Inter)
│                             │
│  🔖  ♡  📤                  │  ← ações: áudio, favoritar, compartilhar
└─────────────────────────────┘
```

### Barra de Busca
- Fundo off-white
- Borda fina (1px) em indigo claro
- Placeholder: "Search for a word..."
- Ícone de lupa à esquerda

### Bottom Navigation
- 3 itens: Home | Busca | Favoritos
- Ícone + texto
- Item ativo em indigo, inativo em cinza

---

## 🖼️ Toque Aquarela (sutil)

Em vez de imagens pesadas, o app usa:

1. **Splash screen** — Gradiente suave indigo → terracota com o nome YAS centralizado
2. **Detalhes decorativos** — Pequenas manchas circulares com opacidade 5% nos cantos dos cards
3. **Ícone do app** — Um "Y" estilizado com pincelada fina, como feito a nanquim

Nada de flores ou pinceladas óbvias — a aquarela é sentida, não gritada.

---

## 🤔 Por que esse design atrai mais usuários?

| Característica | Efeito no usuário |
|---------------|-------------------|
| Off-white (papel) | Sensação de leitura, não de "app genérico" |
| Serifada no título | Transmite conhecimento, credibilidade |
| Cores suaves | Não cansa os olhos, uso prolongado |
| Muito espaço em branco | Parece premium, não amador |
| Aquarela sutil | Diferencia de apps padrão Material Design |