package com.yas.api;

public class FavoritoRequest {
    public String palavra;
    public String definicao;
    public String fonetica;

    public FavoritoRequest(String palavra, String definicao, String fonetica) {
        this.palavra = palavra;
        this.definicao = definicao;
        this.fonetica = fonetica;
    }
}
