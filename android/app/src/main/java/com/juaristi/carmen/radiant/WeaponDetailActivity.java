package com.juaristi.carmen.radiant;

import static java.lang.Float.parseFloat;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeaponDetailActivity extends AppCompatActivity {
   private ApiService apiService;

   final int MAX_FBA = 5;
   final int MAX_RT = 5;
   final int MAX_RSM = 1;
   final int MAX_FR = 16;

   final int MAX_BAR_SIZE = 500;



   private View barra_fba;
   private View barra_rt;
   private View barra_rsm;
   private View barra_wp;
   private View barra_fr;

   @Override
    protected void onCreate (@Nullable Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_weapon_detail);
       //Obtención del nombre del arma desde intent
       String uuid = getIntent().getStringExtra("UUID");
       //Inicialización del servicio API usando Retrofit
       apiService = RetrofitClient.getClient("https://valorant-api.com/").create(ApiService.class);
        barra_fba = findViewById(R.id.FBA);
        barra_fr = findViewById(R.id.FR);
        barra_rt = findViewById(R.id.RT);
        barra_rsm = findViewById(R.id.RSM);
        barra_wp = findViewById(R.id.WP);
       loadWeapons(uuid);
   }



   private void loadWeapons(String uuid){
       //Realizo la llamada a la API
       apiService.getWeaponsDetail(uuid).enqueue(new Callback<WeaponsDetailResponse>() {
           @Override
           public void onResponse(Call<WeaponsDetailResponse> call, Response<WeaponsDetailResponse> response) {
               //Verificamos que la respuesta es exitosa y no es nula
               if(response.isSuccessful() && response.body()!= null){
                   Weapon arma = response.body().getData();
                   ViewGroup.LayoutParams params_fba = barra_fba.getLayoutParams();
                   params_fba.height = (int) ((parseFloat(arma.getWeaponStats().getFirstBulletAccuracy())/MAX_FBA)*MAX_BAR_SIZE);
                   barra_fba.setLayoutParams(params_fba);
                   ViewGroup.LayoutParams params_fr = barra_fr.getLayoutParams();
                   params_fr.height = (int)((parseFloat(arma.getWeaponStats().getFireRate())/MAX_FR)*MAX_BAR_SIZE);
                   barra_fr.setLayoutParams(params_fr);
                   ViewGroup.LayoutParams params_rsm = barra_rsm.getLayoutParams();
                   params_rsm.height = (int)((parseFloat(arma.getWeaponStats().getRunSpeedMultiplier())/MAX_RSM)*MAX_BAR_SIZE);
                   barra_rsm.setLayoutParams(params_rsm);
                   ViewGroup.LayoutParams params_rt = barra_rt.getLayoutParams();
                   params_rt.height = (int) ((parseFloat(arma.getWeaponStats().getReloadTimeSeconds())/MAX_RT)*MAX_BAR_SIZE);
                   barra_rt.setLayoutParams(params_rt);

                   //falta wp pero no da un valor numérico así q no sé como hacer



               }else {
                   // Manejo de respuesta fallida
                   String message;
                   if (response.errorBody() != null) {
                       try {
                           message = response.errorBody().string();
                       } catch (IOException e) {
                           message = "Error en la respuesta.";
                           e.printStackTrace();
                       }
                   } else {
                       message = "Error en la respuesta.";
                   }
                   // Mostrar un Toast con el mensaje de error y el código de respuesta
                   Toast.makeText(WeaponDetailActivity.this, message + " Código: " + response.code(), Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<WeaponsDetailResponse> call, Throwable t) {
                // Manejo del error en caso de fallo en la llamada
               Toast.makeText(WeaponDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
           }
       });
   }
}
