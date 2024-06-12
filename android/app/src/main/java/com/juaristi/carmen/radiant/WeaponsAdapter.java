package com.juaristi.carmen.radiant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class WeaponsAdapter extends RecyclerView.Adapter<WeaponsAdapter.WeaponViewHolder> {
    private Context context;
    private List<Weapon> weaponList;
    private OnWeaponClickListener onWeaponClickListener;

    public WeaponsAdapter(Context context, List<Weapon> weaponList) {
        this.context = context;
        this.weaponList = weaponList;
    }

    // Método que crea un nuevo ViewHolder al inflar el layout del item del arma
    @NonNull
    @Override
    public WeaponViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.weapon_item, parent, false);
        return new WeaponViewHolder(view);
    }

    // Método que enlaza los datos de las armas con las vistas del ViewHolder
    @Override
    public void onBindViewHolder(@NonNull WeaponViewHolder holder, int position) {
        // Obtención del arma de la posición actual
        Weapon weapon = weaponList.get(position);
        // Carga de la imagen del arma usando Glide
        Glide.with(context).load(weapon.getDisplayIcon()).into(holder.iconImageView);
        // Configuración del nombre del agente en el TextView
        holder.nameTextView.setText(weapon.getDisplayName());

        // Configuración del click listener para el item del arma
        holder.itemView.setOnClickListener(v -> {
            // Verificamos que el listener no sea nulo
            if (onWeaponClickListener != null) {
                // Llamamos al método onWeaponClick del listener pasando el arma como parámetro
                onWeaponClickListener.onWeaponClick(weapon);
            }
        });
    }

    @Override
    public int getItemCount() {
        return weaponList.size();
    }

    // Clase ViewHolder para representar cada item del arma
    public static class WeaponViewHolder extends RecyclerView.ViewHolder {
        ImageView iconImageView;
        TextView nameTextView;

        public WeaponViewHolder(@NonNull View itemView) {
            super(itemView);
            iconImageView = itemView.findViewById(R.id.weapon_icon);  // Asegúrate de que el ID sea correcto
            nameTextView = itemView.findViewById(R.id.weapon_name);  // Asegúrate de que el ID sea correcto
        }
    }

    // Interfaz para el click listener
    public interface OnWeaponClickListener {
        void onWeaponClick(Weapon weapon);
    }

    // Método para asignar el listener
    public void setOnWeaponClickListener(OnWeaponClickListener listener) {
        this.onWeaponClickListener = listener;
    }
}
