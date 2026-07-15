package com.yas.api;

import com.yas.model.PalavraResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.DELETE;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Body;

public interface ApiService {

    @GET("api/palavra-do-dia")
    Call<PalavraResponse> palavraDoDia();

    @GET("api/buscar")
    Call<List<PalavraResponse>> buscar(@Query("q") String palavra);

    @GET("api/favoritos")
    Call<List<FavoritoResponse>> listarFavoritos();

    @POST("api/favoritos")
    Call<FavoritoResponse> salvarFavorito(@Body FavoritoRequest request);

    @DELETE("api/favoritos/{id}")
    Call<Void> deletarFavorito(@Path("id") int id);
}
