package com.yas.model;

import java.util.List;

public class PalavraResponse {
    public String palavra;
    public String fonetica;
    public String audio_url;
    public List<Significado> significados;
    public String fonte;

    public static class Significado {
        public String classe;
        public List<Definicao> definicoes;
    }

    public static class Definicao {
        public String definicao;
        public String exemplo;
    }
}
