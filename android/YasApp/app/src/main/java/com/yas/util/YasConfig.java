package com.yas.util;

/**
 * Configurações do YAS — URL do backend e endpoints.
 */
public class YasConfig {

    // IP público da VM GCP onde o backend FastAPI está rodando
    // Altere para o IP/hostname correto quando mudar de ambiente
    public static final String BACKEND_URL = "http://34.70.145.11:8080";

    public static String getTtsUrl(String texto, String lang) {
        try {
            String encoded = java.net.URLEncoder.encode(texto, "UTF-8");
            return BACKEND_URL + "/api/tts?texto=" + encoded + "&lang=" + lang;
        } catch (Exception e) {
            return BACKEND_URL + "/api/tts?texto=" + texto + "&lang=" + lang;
        }
    }
}