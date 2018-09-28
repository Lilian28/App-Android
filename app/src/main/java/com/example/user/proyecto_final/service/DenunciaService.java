package com.example.user.proyecto_final.service;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DenunciaService {

    @POST("denuncias")
    @FormUrlEncoded
    Call<Void> saveNoticia(@FieldMap Map<String, String> data);
}
