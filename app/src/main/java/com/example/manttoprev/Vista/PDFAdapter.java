package com.example.manttoprev.Vista;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manttoprev.Modelo.PDFItem;
;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
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
            textView = itemView.findViewById(android.R.id.text1);
        }
    }

    public interface PDFClickListener {
        void onPDFClick(String url);
    }
}
