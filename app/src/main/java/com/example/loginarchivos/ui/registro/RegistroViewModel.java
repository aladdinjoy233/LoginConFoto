package com.example.loginarchivos.ui.registro;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.loginarchivos.model.Usuario;
import com.example.loginarchivos.request.ApiClient;
import com.example.loginarchivos.ui.login.MainActivity;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class RegistroViewModel extends AndroidViewModel {

    private Context context;

    MutableLiveData<Usuario> usuario;
    private MutableLiveData<Bitmap> foto;

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

    public LiveData<Usuario> getUsuario() {
        if (usuario == null) {
            usuario = new MutableLiveData<>();
        }
        return usuario;
    }

    public LiveData<Bitmap> getFoto() {
        if (foto == null) {
            foto = new MutableLiveData<>();
        }
        return foto;
    }

    public void obtenerDatos(Intent intent) {
        if (intent == null) return;

        if (intent.getBooleanExtra("logeado", false)) {
            Usuario usuario = ApiClient.leer(context);
            this.usuario.setValue(usuario);
            obtenerFoto();
        }
    }

    public void obtenerFoto() {
        Bitmap bitmap = ApiClient.leerFoto(context);
        if (bitmap != null) foto.setValue(bitmap);
    }

    public void registrar(Long dni, String nombre, String apellido, String mail, String pass) {
        Usuario usuario = new Usuario(dni, nombre, apellido, mail, pass);
        ApiClient.guardar(context, usuario);
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void respuestaDeCamara(int requestCode, int resultCode, @Nullable Intent data, int REQUEST_IMAGE_CAPTURE){
        Log.d("salida",requestCode+"");
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Recupero los datos provenientes de la camara.
            Bundle extras = data.getExtras();
            //Casteo a bitmap lo obtenido de la camara.
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            foto.setValue(ApiClient.guardarFoto(context, imageBitmap));
        }
    }

}
