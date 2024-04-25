package com.example.manttoprev.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manttoprev.Presentador.LoginContract;
import com.example.manttoprev.Presentador.LoginPresenter;
import com.example.manttoprev.R;

public class Login extends AppCompatActivity implements LoginContract.View {

    TextView tvRegistrar;
    Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LoginContract.Presenter presenter;
        EditText etPassword;
        EditText etEmail;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnIngresar = findViewById(R.id.btnIngresar);
        tvRegistrar = findViewById(R.id.tvRegistrar);
        presenter = new LoginPresenter(this);

        btnIngresar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            presenter.accederAdmin(email, password);
        });

        tvRegistrar.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, RegistroUsuario.class);
            startActivity(intent);
        });

    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showMenuPrincipal() {
        Intent intent = new Intent(Login.this, InterfazPrincipal.class);
        startActivity(intent);
    }

}


