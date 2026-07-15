package com.yas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WordResponse {
    public String word;
    public List<Meaning> meanings;
    public String phonetic;

    public WordResponse() {}

    public WordResponse(String word, List<Meaning> meanings, String phonetic) {
        this.word = word;
        this.meanings = meanings;
        this.phonetic = phonetic;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meaning {
        public String partOfSpeech;
        public List<Definition> definitions;

        public Meaning() {}

        public Meaning(String partOfSpeech, List<Definition> definitions) {
            this.partOfSpeech = partOfSpeech;
            this.definitions = definitions;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Definition {
        public String definition;
        public String example;

        public Definition() {}

        public Definition(String definition, String example) {
            this.definition = definition;
            this.example = example;
        }
    }
}
