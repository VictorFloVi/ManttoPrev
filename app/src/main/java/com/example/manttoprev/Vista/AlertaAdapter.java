package com.example.manttoprev.Vista;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manttoprev.Modelo.Alertas;
import com.example.manttoprev.R;

import java.util.List;

public class AlertaAdapter extends RecyclerView.Adapter<AlertaAdapter.AlertaViewHolder> {
    private final List<Alertas> alertas;
    private final AlertaClickListener listener;

    public AlertaAdapter(List<Alertas> alertas, AlertaClickListener listener) {
        this.alertas = alertas;
        this.listener = listener;
    }


    @NonNull
    @Override
    public AlertaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alerta, parent, false);
        return new AlertaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertaViewHolder holder, int position) {
        Alertas alerta = alertas.get(position);

        // Mostrar los datos de la alerta
        holder.tvMensaje.setText(alerta.getFechap());
        holder.tvMensajeAlerta.setText(alerta.getMensaje());
        holder.tvMotor.setText(alerta.getMotor());
        holder.tvMaquina.setText(alerta.getMaquina());
        // Agregar log para verificar la URL
        Log.d("AlertaAdapter", "URL: " + alerta.getUrl());
        holder.tvMensaje.setOnClickListener(v -> listener.onPDFClick(alerta.getUrl()));
    }


    @Override
    public int getItemCount() {
        return alertas.size();
    }

    public static class AlertaViewHolder extends RecyclerView.ViewHolder {
        TextView tvMensaje;
        TextView tvMensajeAlerta;
        TextView tvMotor;
        TextView tvMaquina;

        public AlertaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvMensaje);
            tvMensajeAlerta = itemView.findViewById(R.id.tvMensajeAlerta);
            tvMotor = itemView.findViewById(R.id.tvMotor);
            tvMaquina = itemView.findViewById(R.id.tvMaquina);

        }
    }

    public interface AlertaClickListener {
        void onPDFClick(String url);
    }
}
