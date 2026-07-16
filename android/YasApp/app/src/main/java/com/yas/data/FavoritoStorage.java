package com.yas.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FavoritoStorage {

    private static final String PREF_NAME = "yas_favoritos";
    private static final String KEY_LISTA = "lista";

    private final SharedPreferences prefs;
    private final Gson gson;

    public FavoritoStorage(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public List<FavoritoLocal> listar() {
        String json = prefs.getString(KEY_LISTA, "");
        if (json.isEmpty()) return new ArrayList<>();
        Type type = new TypeToken<List<FavoritoLocal>>(){}.getType();
        return gson.fromJson(json, type);
    }

    public boolean salvar(FavoritoLocal favorito) {
        List<FavoritoLocal> lista = listar();

        // Verificar se já existe
        for (FavoritoLocal f : lista) {
            if (f.palavra.equals(favorito.palavra)) return false;
        }

        lista.add(0, favorito); // novo no topo
        prefs.edit().putString(KEY_LISTA, gson.toJson(lista)).apply();
        return true;
    }

    public void remover(String palavra) {
        List<FavoritoLocal> lista = listar();
        lista.removeIf(f -> f.palavra.equals(palavra));
        prefs.edit().putString(KEY_LISTA, gson.toJson(lista)).apply();
    }

    public boolean isSalvo(String palavra) {
        for (FavoritoLocal f : listar()) {
            if (f.palavra.equals(palavra)) return true;
        }
        return false;
    }
}