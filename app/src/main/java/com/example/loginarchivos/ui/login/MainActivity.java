package com.example.loginarchivos.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.loginarchivos.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(MainViewModel.class);

        binding.btLogin.setOnClickListener(v -> {
            viewModel.login(binding.etMail.getText().toString().trim(), binding.etPass.getText().toString());
        });

        binding.btRegistro.setOnClickListener(v -> {
            viewModel.registrar();
        });
    }
}