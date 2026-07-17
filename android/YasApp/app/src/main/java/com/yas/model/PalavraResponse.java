package com.yas.model;

import java.util.List;

/**
 * Modelo para resposta da Free Dictionary API.
 * JSON: https://api.dictionaryapi.dev/api/v2/entries/en/{word}
 */
public class PalavraResponse {

    public String word;
    public String phonetic;
    public List<PhoneticInfo> phonetics;
    public List<Meaning> meanings;

    public static class PhoneticInfo {
        public String text;
        public String audio;
    }

    public static class Meaning {
        public String partOfSpeech;
        public List<Definition> definitions;
    }

    public static class Definition {
        public String definition;
        public String example;
    }
}