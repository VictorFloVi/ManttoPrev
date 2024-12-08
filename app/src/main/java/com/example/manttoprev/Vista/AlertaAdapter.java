package com.example.manttoprev.Vista;

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
    private List<Alertas> alertas;

    public AlertaAdapter(List<Alertas> alertas) {
        this.alertas = alertas;
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

        /*
        // Formatear la fecha
        Alertas.Date fecha = alerta.getFecha();
        String fechaFormateada = String.format("%d de %d de %d, %02d:%02d",
                fecha, fecha.getMonth() + 1, fecha.getYear() + 1900, fecha.getHours(), fecha.getMinutes());

        holder.tvFecha.setText("Fecha: " + fechaFormateada);
        holder.tvMotor.setText("Motor: " + alerta.getMotor());
        holder.tvMaquina.setText("Máquina: " + alerta.getMaquina());
        holder.tvEquipo.setText("Equipo: " + alerta.getEquipo());

         */
    }


    @Override
    public int getItemCount() {
        return alertas.size();
    }

    static class AlertaViewHolder extends RecyclerView.ViewHolder {
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

            /*
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvMotor = itemView.findViewById(R.id.tvMotor);
            tvMaquina = itemView.findViewById(R.id.tvMaquina);
            tvEquipo = itemView.findViewById(R.id.tvEquipo);

             */
        }
    }
}
