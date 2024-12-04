package com.example.manttoprev.Vista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.example.manttoprev.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Reportes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private PDFAdapter pdfAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Verificar permisos en tiempo de ejecución
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            cargarPDFs();
        }
    }

    private void cargarPDFs() {
        List<File> pdfFiles = obtenerArchivosPDF();
        if (pdfFiles.isEmpty()) {
            Toast.makeText(this, "No se encontraron archivos PDF", Toast.LENGTH_SHORT).show();
        } else {
            pdfAdapter = new PDFAdapter(pdfFiles, this);
            recyclerView.setAdapter(pdfAdapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cargarPDFs();
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<File> obtenerArchivosPDF() {
        List<File> pdfFiles = new ArrayList<>();
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Aislamientos");

        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().endsWith(".pdf")) {
                        pdfFiles.add(file);
                    }
                }
            }
        }
        return pdfFiles;
    }
}