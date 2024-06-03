package com.juaristi.carmen.radiant;

import java.util.List;

import retrofit2.Call; // Importación de clases necesarias de Retrofit
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET; // Anotación GET para definir una solicitud HTTP GET
import retrofit2.http.Path;

// Interfaz que define una API REST para obtener una lista de agentes
public interface ApiService {

    // Método GET para obtener la lista de agentes
    @GET("v1/agents")
    Call<AgentsResponse> getAgents(); // Retorna una llamada asíncrona que espera la respuesta de tipo AgentsResponse

    @GET("v1/weapons")
    Call<WeaponsResponse> getWeapons();
}


