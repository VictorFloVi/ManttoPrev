package com.example.manttoprev.Vista;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.manttoprev.Modelo.PDFItem;
import com.example.manttoprev.Presentador.ReportesContract;
import com.example.manttoprev.Presentador.ReportesPresenter;
import com.example.manttoprev.R;


import java.util.ArrayList;
import java.util.List;

public class Reportes extends AppCompatActivity implements ReportesContract.View {

    private PDFAdapter pdfAdapter;
    private List<PDFItem> filteredList; // Lista filtrada para mostrar en la UI
    private ReportesContract.Presenter presenter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        Toolbar toolbar = findViewById(R.id.toolbarReportes);
        setSupportActionBar(toolbar);

        // Habilitar la flecha de retroceso en la barra de acción
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        toolbar.setNavigationOnClickListener(v ->  {

            Intent intent = new Intent(Reportes.this, InterfazPrincipal.class);
            startActivity(intent);

        });

        RecyclerView recyclerViewPDFs = findViewById(R.id.recyclerViewPDFs);
        recyclerViewPDFs.setLayoutManager(new LinearLayoutManager(this));

        searchView = findViewById(R.id.searchView);
        filteredList = new ArrayList<>();
        pdfAdapter = new PDFAdapter(filteredList, this::abrirPDF);
        recyclerViewPDFs.setAdapter(pdfAdapter);

        // Inicialización del Presenter
        presenter = new ReportesPresenter(this);

        // Cargar PDFs desde Firebase
        presenter.cargarPDFsDesdeFirebase();

        setupSearchView();
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void mostrarPDFs(List<PDFItem> pdfItems) {
        filteredList.clear();
        filteredList.addAll(pdfItems);
        pdfAdapter.notifyDataSetChanged();
    }

    @Override
    public void agregarPDF(PDFItem pdfItem) {
        presenter.agregarPDF(pdfItem);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                presenter.filtrarPDFs(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                presenter.filtrarPDFs(newText);
                return true;
            }
        });
    }

    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private void abrirPDF(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(url), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(intent);
    }


}