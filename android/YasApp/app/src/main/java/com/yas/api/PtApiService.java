package com.yas.api;

import com.yas.model.PtWordResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/** API para o Dicionário Aberto (português). */
public interface PtApiService {

    /** Busca definição em português (palavra é convertida para minúsculo). */
    @GET("word/{word}")
    Call<List<PtWordResponse>> buscar(@Path("word", encoded=true) String palavra);
}