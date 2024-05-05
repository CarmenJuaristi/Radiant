package com.juaristi.carmen.radiant;

import android.content.Context;
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

public class RegisterActivity extends AppCompatActivity {
    private ImageView logo;
    private TextView titulo;
    private TextView email_texto;
    private EditText email;
    private TextView username_text;
    private EditText username;
    private TextView password_text;
    private EditText password;
    private Button crear_cuenta;
    private RequestQueue queue;
    private Context context;
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.queue = Volley.newRequestQueue(this);
        // Establezco el diseño de la actividad
        setContentView(R.layout.activity_register);
        //Inicializo las vistas y componentes de la interfaz del usuario
        logo = findViewById(R.id.logo);
        titulo = findViewById(R.id.Radiant);
        email_texto = findViewById(R.id.email_text);
        email = findViewById(R.id.correo);
        username_text = findViewById(R.id.username_text);
        username = findViewById(R.id.username);
        password_text = findViewById(R.id.password_text);
        password = findViewById(R.id.password);
        crear_cuenta = findViewById(R.id.crear_cuenta);
        // Configuración del botón de crear cuenta
        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Llamo la función de enviar la solicitud de registro al servidor
                sendPostRegister();
            }
        });
    }
    private void sendPostRegister (){
        //Construyo el cuerpo de la solicitud con los datos del usuario
        JSONObject requestBody = new JSONObject();
        try {
            requestBody.put("username",username.getText().toString());
            requestBody.put("email",email.getText().toString());
            requestBody.put("password",password.getText().toString());
        }catch (JSONException e){
            //Manejo las excepciones en caso de error al construir el JSON
            throw new RuntimeException(e);
        }
        //Realiza la solicitud POST al servidor para registrar al usuario
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                Server.name + "/users",
                requestBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Notificación de éxito al usuario
                        Toast.makeText(RegisterActivity.this, "Usuario creado", Toast.LENGTH_SHORT).show();
                        //Cierro la actividad después de un registro exitoso
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error.networkResponse == null) {
                            // Notificación en caso de falta de conexión
                            Toast.makeText(RegisterActivity.this, "No se ha establecido la conexión", Toast.LENGTH_LONG).show();
                            System.out.println(error);
                        } else {
                            try {
                                int serverCode = error.networkResponse.statusCode;
                                //Toast.makeText(RegisterActivity.this, "Estado de respuesta: " + serverCode, Toast.LENGTH_LONG).show();
                                if (serverCode == 400) {
                                    // Manejo de diferentes códigos de respuesta del servidor
                                    Toast.makeText(RegisterActivity.this, "Alguno de los campos es incorrecto", Toast.LENGTH_SHORT).show();
                                }

                                if (serverCode == 409) {
                                    Toast.makeText(RegisterActivity.this, "Ya existe una cuenta con este correo ", Toast.LENGTH_SHORT).show();
                                }
                            }catch (NullPointerException e){} // Manejo de excepciones en caso de error desconocido
                        }
                    }
                }
        );
        //Añado la solicitud a la cola de solicitudes
        this.queue.add(request);
    }
}
