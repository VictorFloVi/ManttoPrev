package com.example.manttoprev.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.manttoprev.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Alertas extends AppCompatActivity {

    private static final String TAG = "Alertas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertas);

        // Llamar la función para probar la conexión a Firebase Storage
        testFirebaseStorageConnection();
    }

    // Función para probar la conexión a Firebase Storage
    private void testFirebaseStorageConnection() {
        try {
            // Crear un archivo de prueba en el almacenamiento local
            File testFile = new File(getExternalFilesDir(null), "testfile.txt");

            if (!testFile.exists()) {
                testFile.createNewFile(); // Crear el archivo si no existe
            }

            // Obtener referencia de Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("testfiles/testfile.txt");

            // Subir archivo de prueba a Firebase Storage
            Uri fileUri = Uri.fromFile(testFile);
            UploadTask uploadTask = storageRef.putFile(fileUri);

            uploadTask.addOnSuccessListener(taskSnapshot -> {
                Log.d(TAG, "Archivo subido correctamente");

                // Ahora intentar descargar el archivo
                storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Log.d(TAG, "Archivo descargado correctamente desde Firebase Storage: " + uri.toString());
                    Toast.makeText(this, "Conexión a Firebase Storage exitosa", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(e -> {
                    Log.e(TAG, "Error al obtener la URL de descarga: " + e.getMessage());
                    Toast.makeText(this, "Error al obtener la URL de descarga", Toast.LENGTH_SHORT).show();
                });

            }).addOnFailureListener(e -> {
                if (e instanceof StorageException) {
                    Log.e(TAG, "Error al subir el archivo: " + e.getMessage());
                    Toast.makeText(this, "Error al subir el archivo a Firebase Storage", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Error inesperado: " + e.getMessage());
                    Toast.makeText(this, "Error inesperado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            Log.e(TAG, "Error al realizar el test de conexión: " + e.getMessage());
            Toast.makeText(this, "Error al realizar el test de conexión", Toast.LENGTH_SHORT).show();
        }
    }

}