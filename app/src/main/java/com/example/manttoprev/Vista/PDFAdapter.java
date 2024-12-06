package com.example.manttoprev.Vista;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manttoprev.Modelo.PDFItem;
import com.example.manttoprev.R;

import java.util.List;

public class PDFAdapter extends RecyclerView.Adapter<PDFAdapter.PDFViewHolder> {

    private final List<PDFItem> pdfList;
    private final PDFClickListener listener;



    public PDFAdapter(List<PDFItem> pdfList, PDFClickListener listener) {
        this.pdfList = pdfList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PDFViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_pdf_item, parent, false);
        return new PDFViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PDFViewHolder holder, int position) {
        PDFItem item = pdfList.get(position);
        holder.textView.setText(item.getName());
        holder.itemView.setOnClickListener(v -> listener.onPDFClick(item.getUrl()));
    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    static class PDFViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public PDFViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tvPdf);
        }
    }

    public interface PDFClickListener {
        void onPDFClick(String url);
    }
}
