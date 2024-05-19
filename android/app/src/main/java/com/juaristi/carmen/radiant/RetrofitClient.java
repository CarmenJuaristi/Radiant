package com.juaristi.carmen.radiant;

import retrofit2.Retrofit; // Importación de Retrofit para la creación de la instancia
import retrofit2.converter.gson.GsonConverterFactory; // Importación del convertidor Gson para Retrofit

// Clase que proporciona una instancia de Retrofit para realizar solicitudes HTTP
public class RetrofitClient {
    private static Retrofit retrofit = null;

    // Método para obtener una instancia de Retrofit
    public static Retrofit getClient(String baseUrl) {
        // Si la instancia de Retrofit no está inicializada
        if (retrofit == null) {
            // Crear una nueva instancia de Retrofit con la URL base proporcionada
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl) // Establecer la URL base
                    .addConverterFactory(GsonConverterFactory.create()) // Agregar el convertidor Gson
                    .build(); // Construir la instancia de Retrofit
        }
        // Devolver la instancia de Retrofit
        return retrofit;
    }
}

