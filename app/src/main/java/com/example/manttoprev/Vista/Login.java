package com.example.manttoprev.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manttoprev.Presentador.LoginContract;
import com.example.manttoprev.Presentador.LoginPresenter;
import com.example.manttoprev.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements LoginContract.View {

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
        presenter = new LoginPresenter(this);

        btnIngresar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            presenter.accederAdmin(email, password);
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


