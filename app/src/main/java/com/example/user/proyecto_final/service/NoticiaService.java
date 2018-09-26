package com.example.user.proyecto_final.service;

import com.example.user.proyecto_final.model.Noticia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NoticiaService {

    @GET("noticias")
    Call<List<Noticia>> getNoticias();
}
