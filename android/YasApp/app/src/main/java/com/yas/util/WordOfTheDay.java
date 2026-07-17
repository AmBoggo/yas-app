package com.yas.util;

import java.util.Calendar;

/** Listas curadas de palavras para a Palavra do Dia, em EN e PT. */
public class WordOfTheDay {

    // ── 100 palavras em inglês ──
    private static final String[] WORDS_EN = {
        "Serendipity", "Petrichor", "Ephemeral", "Defenestration", "Ethereal",
        "Nostalgia", "Melancholy", "Euphoria", "Reverie", "Zeal",
        "Ambiguous", "Eloquent", "Resilient", "Tenacious", "Paradigm",
        "Cascade", "Luminous", "Verdant", "Zephyr", "Ebullient",
        "Juxtaposition", "Anachronism", "Dichotomy", "Precarious", "Ominous",
        "Whimsical", "Incandescent", "Mellifluous", "Cacophony", "Opulent",
        "Transient", "Perpetual", "Voracious", "Inevitable", "Prolific",
        "Ambivalent", "Articulate", "Concise", "Plausible", "Prudent",
        "Surreptitious", "Benevolent", "Malevolent", "Candid", "Magnanimous",
        "Halcyon", "Sonder", "Liminal", "Ubiquitous", "Nemesis",
        "Awe", "Angst", "Ennui", "Aplomb", "Trepidation",
        "Paradox", "Perspicacious", "Meticulous", "Enigma", "Quintessential",
        "Resplendent", "Prismatic", "Viridescent", "Aurora", "Ripple",
        "Antithesis", "Epitome", "Frivolous", "Tangible", "Tenuous",
        "Breathtaking", "Radiant", "Sonorous", "Lissome", "Relentless",
        "Imminent", "Spontaneous", "Impulsive", "Diligent", "Apathetic",
        "Coherent", "Feasible", "Viable", "Tactful", "Superfluous",
        "Obfuscate", "Pragmatic", "Dogmatic", "Debacle", "Fiasco",
        "Callous", "Compassionate", "Empathetic", "Nefarious", "Vindictive",
        "Docile", "Stoic", "Blunt", "Whimsy", "Stellar"
    };

    // ── 100 palavras em português ──
    private static final String[] WORDS_PT = {
        "Saudade", "Serenidade", "Resiliência", "Altruísmo", "Empatia",
        "Labareda", "Crepúsculo", "Borbulhar", "Sussurro", "Deslumbrante",
        "Plenitude", "Efervescência", "Minguante", "Fulgor", "Orvalho",
        "Ventania", "Tempestade", "Balanço", "Despertar", "Encanto",
        "Afeto", "Sublime", "Profundo", "Singelo", "Exuberante",
        "Mansidão", "Ternura", "Vivacidade", "Lentidão", "Presságio",
        "Redemoinho", "Montanha", "Cachoeira", "Florescer", "Amanhecer",
        "Entardecer", "Horizonte", "Caminhada", "Descoberta", "Aventura",
        "Coragem", "Sabedoria", "Humildade", "Gratidão", "Esperança",
        "Acolhimento", "Liberdade", "Justiça", "Lealdade", "Amizade",
        "Paciência", "Silêncio", "Abraço", "Sorriso", "Olhar",
        "Lembrança", "Destino", "Sonho", "Vontade", "Propósito",
        "Harmonia", "Equilíbrio", "Sutileza", "Delicadeza", "Beleza",
        "Grandeza", "Nobreza", "Fortaleza", "Leveza", "Confiança",
        "Cumplicidade", "Sinceridade", "Simplicidade", "Bondade", "Caridade",
        "Generosidade", "Compaixão", "Tolerância", "Compreensão", "Perdão",
        "Respeito", "Dignidade", "Verdade", "Firmeza", "Persistência",
        "Inovação", "Criatividade", "Imaginação", "Intuição", "Inspiração",
        "Sapiência", "Clareza", "Astúcia", "Prudência", "Ponderação"
    };

    private WordOfTheDay() {}

    /** Retorna a palavra do dia baseada no idioma e dia do ano. */
    public static String get(String idioma) {
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        if ("pt".equals(idioma)) {
            return WORDS_PT[dia % WORDS_PT.length];
        }
        return WORDS_EN[dia % WORDS_EN.length];
    }

    /** Retorna a palavra do dia em inglês (compatibilidade). */
    public static String getPalavraDoDia() {
        return get("en");
    }
}