package com.juaristi.carmen.radiant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment3 extends Fragment {
    private RecyclerView recyclerView;
    private WeaponsAdapter weaponsAdapter;
    private ApiService apiService;

    // Interfaz par comunicarse con la actividad
    public interface onWeaponSelectedListener {
        void onWeaponSelected (String weaponName);
    }
    private onWeaponSelectedListener mListener;

    @Override
    public void onAttach (@NonNull Context context){
        super.onAttach(context);
        try{
            mListener = (onWeaponSelectedListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()+" must implement OnWeaponSelectedListener");

        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        // Inflaremos el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_weapons, container, false);

        // Inicializamos el RecyclerView y lo configuramos con un GridLayoutManager
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));

        // Inicializamos el servicio de API
        apiService = RetrofitClient.getClient("https://valorant-api.com/").create(ApiService.class);

        // Realizaos la solicitud para obtener las armas
        fetchWeapons();

        return view;
    }

    //Método para obtener las armas de la API

    private  void fetchWeapons(){
        Call<WeaponsResponse> call = apiService.getWeapons();

        call.enqueue(new Callback<WeaponsResponse>() {
            @Override
            public void onResponse(Call<WeaponsResponse> call, Response<WeaponsResponse> response) {
                if (response.isSuccessful() && response.body() != null){
                    //Obtener la lista de las armas de la respuesta
                    List<Weapon> weaponList = response.body().getData();

                    //Configuramos el adaptador con la lista de armas
                    weaponsAdapter = new WeaponsAdapter(getContext(),weaponList);
                    recyclerView.setAdapter(weaponsAdapter);

                    //Manejo del evento de la selección de arma
                    weaponsAdapter.setOnWeaponClickListener(new WeaponsAdapter.OnWeaponClickListener(){
                        @Override
                        public void onWeaponClick(Weapon weapon) {
                            mListener.onWeaponSelected(weapon.getDisplayName());
                        }
                    });
                }else{
                    //Manejo de respuesta fallida
                    String errorMessage = "Error: ";
                    if(response.errorBody() != null){
                        try{
                            errorMessage += response.errorBody().string();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        errorMessage += "Response body is empty.";
                    }
                    Toast.makeText(getContext(),errorMessage,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<WeaponsResponse> call, Throwable t) {
                // Manejo de errores
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


}
