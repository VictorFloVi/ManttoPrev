package com.example.manttoprev.Presentador;


import com.example.manttoprev.Modelo.PDFItem;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
public class ReportesPresenter implements ReportesContract.Presenter {
    private final ReportesContract.View view;
    private final List<PDFItem> pdfList; // Lista original de PDFs

    public ReportesPresenter(ReportesContract.View view) {
        this.view = view;
        this.pdfList = new ArrayList<>();
    }


    @Override
    public void cargarPDFsDesdeFirebase() {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("pdfs");

        storageRef.listAll()
                .addOnSuccessListener(listResult -> {
                    for (StorageReference item : listResult.getItems()) {
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            PDFItem pdfItem = new PDFItem(item.getName(), uri.toString());
                            agregarPDF(pdfItem);
                        });
                    }
                })
                .addOnFailureListener(e -> view.showErrorMessage("Error al listar PDFs: " + e.getMessage()));
    }

    @Override
    public void agregarPDF(PDFItem pdfItem) {
        pdfList.add(pdfItem); // Agregar a la lista completa
        view.mostrarPDFs(new ArrayList<>(pdfList)); // Mostrar lista completa inicialmente
    }



    @Override
    public void filtrarPDFs(String query) {
        List<PDFItem> filteredList = new ArrayList<>();
        for (PDFItem item : pdfList) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
        }
        view.mostrarPDFs(filteredList); // Actualizar la vista con la lista filtrada
    }
}
