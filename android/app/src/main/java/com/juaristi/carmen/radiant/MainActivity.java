package com.juaristi.carmen.radiant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity  implements Fragment2.OnAgentSelectedListener{
    private Context context = this;

    @Override
    protected void onCreate(@Nullable  Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Fragment myFragment = new Fragment1();
        Fragment myFragment2 = new Fragment2();
        Fragment myFragment3 = new Fragment3();
        Fragment myFragment4 = new Fragment4();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new Fragment1()).commit();
        BottomNavigationView bar = findViewById(R.id.bottomNavigation);
        bar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId()==R.id.home){
                    Fragment myFragment = new Fragment1();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,myFragment).commit();
                }
                if (item.getItemId()==R.id.agents){
                    Fragment myFragment2 = new Fragment2();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,myFragment2).commit();

                }
                if (item.getItemId()==R.id.weapons){
                    Fragment myFragment3 = new Fragment3();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,myFragment3).commit();

                }
                if (item.getItemId()==R.id.profile){
                    Fragment myFragment4 = new Fragment4();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,myFragment4).commit();

                }
                return true;

            }
        });



    }
    @Override
    public void onAgentSelected(String agentName) {
        // Inicia la actividad AgentDetailActivity con el nombre del agente seleccionado
        Intent intent = new Intent(this, AgentDetailActivity.class);
        intent.putExtra("AGENT_NAME", agentName);
        startActivity(intent);
    }
}
