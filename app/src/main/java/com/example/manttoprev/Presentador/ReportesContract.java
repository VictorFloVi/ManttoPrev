package com.example.manttoprev.Presentador;

import com.example.manttoprev.Modelo.PDFItem;

import java.util.List;

public interface ReportesContract {
    interface View {
        void mostrarPDFs(List<PDFItem> pdfItems); // Muestra la lista filtrada en la UI
        void agregarPDF(PDFItem pdfItem); // Agrega un PDF a la lista original
        void showErrorMessage(String mensaje);
    }

    interface Presenter {
        void cargarPDFsDesdeFirebase(); // Carga los PDFs desde Firebase
        void agregarPDF(PDFItem pdfItem); // Agrega un PDF a la lista completa
        void filtrarPDFs(String query); // Filtra los PDFs en función de una consulta
    }
}
