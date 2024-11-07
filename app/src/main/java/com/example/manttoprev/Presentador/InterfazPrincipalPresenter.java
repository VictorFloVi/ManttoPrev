package com.example.manttoprev.Presentador;

import androidx.annotation.NonNull;
import com.example.manttoprev.Vista.InterfazPrincipal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InterfazPrincipalPresenter implements InterfazPrincipalContract.Presenter{

    private static final String USUARIOS = "Usuarios";
    private static final String ROL = "rol";
    private final InterfazPrincipal view;
    private final FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    public InterfazPrincipalPresenter(InterfazPrincipal view) {
        this.view = view;
         mDatabase = FirebaseDatabase.getInstance().getReference();
         mAuth = FirebaseAuth.getInstance();
    }

    // Método para obtener el rol del usuario desde Firebase Realtime Database
    public void obtenerRolUsuario() {

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            mDatabase = FirebaseDatabase.getInstance().getReference().child(USUARIOS).child(user.getUid()).child(ROL);
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String rol = dataSnapshot.getValue(String.class);
                        // Ocultar el TextView de registro
                        view.showRegistrarTextView("administrador".equals(rol)); // Mostrar el TextView de registro
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar errores
                }
            });
        }
    }
}
