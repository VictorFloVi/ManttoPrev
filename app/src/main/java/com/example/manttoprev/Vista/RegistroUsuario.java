package com.example.manttoprev.Vista;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.manttoprev.Presentador.RegistroUsuarioContract;
import com.example.manttoprev.Presentador.RegistroUsuarioPresenter;
import com.example.manttoprev.R;


public class RegistroUsuario extends AppCompatActivity implements RegistroUsuarioContract.View {
    EditText etNombre;
    EditText etDNI;
    EditText etxtEmail;
    EditText etxtPassword;
    EditText etxtConfirmarPass;
    Spinner cboRol;
    Button btnRegistrarUsuario;
    TextView tvAcceder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        RegistroUsuarioContract.Presenter presenter;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        etNombre = findViewById(R.id.etNombre);
        etDNI = findViewById(R.id.etDNI);
        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtConfirmarPass =findViewById(R.id.etxtConfirmarPass);
        cboRol = findViewById(R.id.cboRol);
        String[] fuenteDatos = getResources().getStringArray(R.array.lista_roles);
        ArrayAdapter<String> adp = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, fuenteDatos);
        cboRol.setAdapter(adp);

        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);
        tvAcceder = findViewById(R.id.tvAcceder);

        presenter = new RegistroUsuarioPresenter(this);

        btnRegistrarUsuario.setOnClickListener(v -> {
            String nombreCompleto = etNombre.getText().toString().trim();
            String dni = etDNI.getText().toString().trim();
            String email = etxtEmail.getText().toString().trim();
            String password = etxtPassword.getText().toString().trim();
            String rol = cboRol.getSelectedItem().toString().trim();
            presenter.registrarUsuario(nombreCompleto, dni, email, password, rol);
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

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        clearEditTextFields();
    }

    public void clearEditTextFields() {
        etNombre.setText("");
        etDNI.setText("");
        etxtEmail.setText("");
        etxtPassword.setText("");
        etxtConfirmarPass.setText("");
        cboRol.setSelection(0);
    }

}

