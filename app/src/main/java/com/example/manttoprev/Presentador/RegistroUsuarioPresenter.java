package com.example.manttoprev.Presentador;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import com.example.manttoprev.Vista.RegistroUsuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegistroUsuarioPresenter implements RegistroUsuarioContract.Presenter {
    private final RegistroUsuario view;
    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;

    public RegistroUsuarioPresenter(RegistroUsuario view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    @SuppressLint("SetTextI18n")
    public void registrarUsuario(String nombreCompleto, String dni, String email, String password) {
        if (nombreCompleto.isEmpty() || dni.isEmpty() || email.isEmpty() || password.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return; // No realizamos el registro si algún campo está vacío
        }

        ProgressBar progressBar = new ProgressBar(view);
        progressBar.setVisibility(View.VISIBLE);

        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(view);
        builder.setView(progressBar);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.setMessage("Registrando usuario");
        dialog.show();


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dialog.dismiss();
                Map<String, Object> crearUsuario = new HashMap<>();
                crearUsuario.put("nombre", nombreCompleto);
                crearUsuario.put("dni", dni);
                crearUsuario.put("email", email);
                mDatabase.child("Usuarios").child(task.getResult().getUser().getUid()).updateChildren(crearUsuario);
                view.showMenuPrincipal();
            } else {
                dialog.dismiss();
                view.showErrorMessage("Los campos son incorrectos.");
            }
        });
    }


}
