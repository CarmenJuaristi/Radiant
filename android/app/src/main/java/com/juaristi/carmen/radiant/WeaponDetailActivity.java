package com.juaristi.carmen.radiant;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class WeaponDetailActivity extends AppCompatActivity {
   private ApiService apiService;
   @Override
    protected void onCreate (@Nullable Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_weapon_detail);
        //Inicialización del servicio API usando Retrofit
       apiService = RetrofitClient.getClient("http://10.0.2.2:8000/").create(ApiService.class);

       //Obtención del nombre del arma desde intent
      String weaponName = getIntent().getStringExtra("WEAPON_NAME");

   }
}
