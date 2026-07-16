package com.yas.data;

import java.util.List;

public class FavoritoLocal {
    public String palavra;
    public String definicao;
    public String fonetica;
    public long salvoEm;

    public FavoritoLocal() {}

    public FavoritoLocal(String palavra, String definicao, String fonetica) {
        this.palavra = palavra;
        this.definicao = definicao;
        this.fonetica = fonetica;
        this.salvoEm = System.currentTimeMillis();
    }
}