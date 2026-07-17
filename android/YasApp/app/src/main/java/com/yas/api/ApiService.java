package com.yas.api;

import com.yas.model.PalavraResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    /** Busca uma palavra na Free Dictionary API. */
    @GET("api/v2/entries/en/{word}")
    Call<List<PalavraResponse>> buscar(@Path("word") String palavra);
}