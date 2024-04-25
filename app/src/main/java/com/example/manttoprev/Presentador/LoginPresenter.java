package com.example.manttoprev.Presentador;

import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import com.example.manttoprev.Vista.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class LoginPresenter implements LoginContract.Presenter {
    private final Login view;
    private final FirebaseAuth mAuth;
    private final DatabaseReference mDatabase;


    public LoginPresenter(Login view) {
        this.view = view;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void accederAdmin(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showErrorMessage("Todos los campos son obligatorios");
            return;
        }

        ProgressBar progressBar = new ProgressBar(view);
        progressBar.setVisibility(View.VISIBLE);

        AlertDialog.Builder builder = new AlertDialog.Builder(view);
        builder.setView(progressBar);
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.setMessage("Ingresando...");
        dialog.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                dialog.dismiss();
                mDatabase.child("Usuarios").child(task.getResult().getUser().getUid());
                view.showMenuPrincipal();

            } else {
                dialog.dismiss();
                view.showErrorMessage("Credenciales no válidas.");
            }
        });



    }
}
