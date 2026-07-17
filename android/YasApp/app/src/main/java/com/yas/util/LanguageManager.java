package com.yas.util;

import android.content.Context;
import android.content.SharedPreferences;

/** Gerencia o idioma ativo: "en" (inglês) ou "pt" (português). */
public class LanguageManager {

    private static final String PREF_NAME = "yas_idioma";
    private static final String KEY_IDIOMA = "idioma";

    public static final String EN = "en";
    public static final String PT = "pt";

    private final SharedPreferences prefs;

    public LanguageManager(Context context) {
        this.prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    /** Retorna o idioma atual ("en" ou "pt"). Padrão: "en". */
    public String getIdioma() {
        return prefs.getString(KEY_IDIOMA, EN);
    }

    /** Altera o idioma. */
    public void setIdioma(String idioma) {
        prefs.edit().putString(KEY_IDIOMA, idioma).apply();
    }

    /** Alterna entre EN e PT, retorna o novo valor. */
    public String toggle() {
        String atual = getIdioma();
        String novo = atual.equals(EN) ? PT : EN;
        setIdioma(novo);
        return novo;
    }

    public boolean isEN() {
        return getIdioma().equals(EN);
    }

    public boolean isPT() {
        return getIdioma().equals(PT);
    }
}