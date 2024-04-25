package com.example.manttoprev.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manttoprev.Presentador.RegistroUsuarioContract;
import com.example.manttoprev.Presentador.RegistroUsuarioPresenter;
import com.example.manttoprev.R;


public class RegistroUsuario extends AppCompatActivity implements RegistroUsuarioContract.View {



    Button btnRegistrarUsuario;
    TextView tvAcceder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        EditText etxtPass;
        EditText etxtEmail;
        EditText etDNI;
        EditText etNombre;
        RegistroUsuarioContract.Presenter presenter;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario_admin);

        etNombre = findViewById(R.id.etNombre);
        etDNI = findViewById(R.id.etDNI);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPass = findViewById(R.id.etxtPassword);

        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);
        tvAcceder = findViewById(R.id.tvAcceder);

        presenter = new RegistroUsuarioPresenter(this);

        btnRegistrarUsuario.setOnClickListener(v -> {
            String nombreCompleto = etNombre.getText().toString().trim();
            String dni = etDNI.getText().toString().trim();
            String email = etxtEmail.getText().toString().trim();
            String password = etxtPass.getText().toString().trim();
            presenter.registrarUsuario(nombreCompleto, dni, email, password);
        });

        tvAcceder.setOnClickListener(v -> {
            Intent intent = new Intent(RegistroUsuario.this, Login.class);
            startActivity(intent);
        });

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showMenuPrincipal() {
        Intent intent = new Intent(RegistroUsuario.this, InterfazPrincipal.class);
        startActivity(intent);
    }
}

