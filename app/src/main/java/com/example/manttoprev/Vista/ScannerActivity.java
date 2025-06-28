package com.example.manttoprev.Vista;

import android.os.Bundle;
import androidx.camera.view.PreviewView;
import android.content.Intent;
import androidx.activity.ComponentActivity;
import androidx.camera.core.*;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import android.util.Size;
import android.widget.Toast;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.vision.barcode.*;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.example.manttoprev.R;

public class ScannerActivity extends ComponentActivity {


    private PreviewView previewView;
    private ExecutorService cameraExecutor;
    private BarcodeScanner scanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


        previewView = findViewById(R.id.previewView);

        // Instancia el detector de ML Kit (QR + todos los formatos)
        scanner = BarcodeScanning.getClient(
                new BarcodeScannerOptions.Builder()
                        .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                        .build()
        );
        cameraExecutor = Executors.newSingleThreadExecutor();
        startCamera();
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();

                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis analysis = new ImageAnalysis.Builder()
                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                analysis.setAnalyzer(cameraExecutor, imageProxy -> {
                    @SuppressWarnings("UnsafeOptInUsageError")
                    ImageProxy.PlaneProxy[] planes = imageProxy.getPlanes();
                    if (planes.length > 0) {
                        @SuppressWarnings("UnsafeOptInUsageError")
                        android.media.Image mediaImage = imageProxy.getImage();
                        if (mediaImage != null) {
                            InputImage image =
                                    InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());
                            scanner.process(image)
                                    .addOnSuccessListener(barcodes -> {
                                        for (Barcode bc : barcodes) {
                                            String raw = bc.getRawValue();
                                            if (raw != null) {
                                                deliverResult(raw);
                                                break;
                                            }
                                        }
                                    })
                                    .addOnCompleteListener(task -> imageProxy.close());
                        }
                    } else {
                        imageProxy.close();
                    }
                });

                CameraSelector selector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, selector, preview, analysis);

            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(this, "No se pudo iniciar la cámara", Toast.LENGTH_SHORT).show();
            }
        }, ContextCompat.getMainExecutor(this));
    }

    private void deliverResult(String contenido) {
        Intent data = new Intent();
        data.putExtra("QR_CONTENT", contenido);
        setResult(RESULT_OK, data);
        finish(); // Cierra el escáner y regresa
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        scanner.close();
        cameraExecutor.shutdown();
    }
}