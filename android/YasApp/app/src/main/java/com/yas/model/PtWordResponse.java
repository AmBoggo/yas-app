package com.yas.model;

/**
 * Modelo para resposta da API Dicionário Aberto (português).
 * JSON: https://api.dicionario-aberto.net/word/{palavra}
 */
public class PtWordResponse {
    public String word;
    public String xml;  // definições em XML
}