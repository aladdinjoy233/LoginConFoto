package com.example.loginarchivos.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.loginarchivos.model.Usuario;
import com.example.loginarchivos.request.ApiClient;
import com.example.loginarchivos.ui.registro.RegistroActivity;

public class MainViewModel extends AndroidViewModel {

    private Context context;

    public MainViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public void login(String mail, String pass) {
        Usuario usuario = ApiClient.login(context, mail, pass);
        if (usuario == null) {
            Toast.makeText(context, "Datos incorrectos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(context, RegistroActivity.class);

//        Guardar el usuario en un Intent
        intent.putExtra("logeado", true);

//        Navegar a la actividad
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void registrar() {
        Intent intent = new Intent(context, RegistroActivity.class);

//        Guardar el usuario en un Intent
        intent.putExtra("logeado", false);

//        Navegar a la actividad
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
