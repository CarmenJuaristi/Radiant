package com.juaristi.carmen.radiant;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView titulo;
    private TextView email_text;
    private EditText email;
    private TextView password_text;
    private EditText password;
    private Button iniciar_sesion;
    private Button crear_cuenta;
    private RequestQueue queue;
    private Context context = this;
    private Activity activity = this;
@Override
    protected void onCreate (Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    // Incializamos las vistas y los objetos q sean necesarios
    logo = findViewById(R.id.logo);
    titulo = findViewById(R.id.Radiant);
    email_text = findViewById(R.id.email_text);
    email = findViewById(R.id.correo);
    password_text = findViewById(R.id.password_text);
    password = findViewById(R.id.password);
    iniciar_sesion = findViewById(R.id.iniciar_sesion);
    crear_cuenta = findViewById(R.id.crear_cuenta);
    queue = Volley.newRequestQueue(this);

    //Cuando el usuario pulse el botón crear cuenta, lo mandaremos a la actividad RegisterActivity
    crear_cuenta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Navegar a la pantalla de registro
            Intent intent = new Intent(activity, RegisterActivity.class);
            activity.startActivity(intent);
        }
    });

    //Ahora configuraré el botón de iniciar sesión
    iniciar_sesion.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Iniciar sesión del usuario
            loginUser();
        }
    });
}
private void loginUser(){
    //Crear un objeto JSON con las credenciales del usuario
    JSONObject requestBody = new JSONObject();
    try{
        requestBody.put("username",email.getText().toString());
        requestBody.put("password",password.getText().toString());

    }catch (JSONException e){
        throw new RuntimeException(e);
    }
    //Crear una solicitud JsonObjectRequest para iniciar sesión
    JsonObjectRequest request = new JsonObjectRequest(
            Request.Method.POST,
            Server.name + "/sessions",
            requestBody,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String receivedToken;
                    try{
                        //Obtener el token de sesión de la respuesta JSON
                        receivedToken = response.getString("sessionToken");
                    }catch (JSONException e){
                        throw new RuntimeException(e);
                    }
                    //Mostrar mensajes indicando que la sesión se inició correctamente
                    Toast.makeText(context, "Token de sesión:"+receivedToken,Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Iniciando sesión...",Toast.LENGTH_SHORT).show();

                    SharedPreferences preferences = context.getSharedPreferences("SESSIONS_APP_PREFS", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("VALID_EMAIL",email.getText().toString());
                    editor.putString("VALID_TOKEN",receivedToken);
                    editor.commit();
                    //Navegar a la actividad principal después de inciar sesión
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                }
            },
            new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    if(error.networkResponse == null){
                        Toast.makeText(LoginActivity.this, "error",Toast.LENGTH_SHORT).show();
                    }else{
                        int serverCode = error.networkResponse.statusCode;
                        // Manejaremos los diferentes códigos de error del servidor
                        if(serverCode==404){
                            Toast.makeText(context,"Correo no válido",Toast.LENGTH_SHORT).show();
                        }
                        if(serverCode==401){
                            Toast.makeText(context,"Contraseña incorrecta",Toast.LENGTH_SHORT).show();
                        }
                        if (serverCode==500){
                            Toast.makeText(context,"Problemas con el servidor",Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
    );
    // Agregar la solicitud a la cola de solicitudes
    this.queue.add(request);

        }



}

