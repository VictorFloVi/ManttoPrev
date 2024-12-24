package com.example.manttoprev.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manttoprev.Modelo.Alertas;
import com.example.manttoprev.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AlertasAdmin extends AppCompatActivity {

    private TextView tvNoAlertas;
    private RecyclerView rvAlertas;
    private List<Alertas> alertaList;
    private AlertaAdapter alertaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas_admin);


        // Inicializar el RecyclerView y la lista de alertas
        rvAlertas = findViewById(R.id.rvAlertas);
        rvAlertas.setLayoutManager(new LinearLayoutManager(this));
        alertaList = new ArrayList<>();
        alertaAdapter = new AlertaAdapter(alertaList, this::abrirPDF);
        rvAlertas.setAdapter(alertaAdapter);

        // Inicializar la referencia a la base de datos
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("alertas");

        // Inicializar el TextView para mostrar el mensaje cuando no haya alertas
        tvNoAlertas = findViewById(R.id.tvNoAlertas);

        // Configurar el listener para cargar las alertas desde Firebase
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                alertaList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Aquí se convierte el snapshot en un objeto de tipo Alertas
                    Alertas alerta = snapshot.getValue(Alertas.class);
                    if (alerta != null) alertaList.add(alerta);
                }
                alertaAdapter.notifyDataSetChanged();

                if (alertaList.isEmpty()) {
                    tvNoAlertas.setVisibility(View.VISIBLE);
                    rvAlertas.setVisibility(View.GONE);
                } else {
                    tvNoAlertas.setVisibility(View.GONE);
                    rvAlertas.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AlertasAdmin.this, "Error al cargar alertas: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void abrirPDF(String url) {
        if (url != null && !url.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.parse(url), "application/pdf");
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
        } else {
            Toast.makeText(this, "URL no válida", Toast.LENGTH_SHORT).show();
        }
    }


}
