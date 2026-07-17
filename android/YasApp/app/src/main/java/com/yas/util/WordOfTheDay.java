package com.yas.util;

import java.util.Calendar;

/** Lista curada de 100 palavras e seleção por dia do ano. */
public class WordOfTheDay {

    public static final String[] WORDS = {
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

    /** Retorna a palavra do dia baseada no dia do ano (0-99). */
    public static String getPalavraDoDia() {
        int dia = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        return WORDS[dia % WORDS.length];
    }
}