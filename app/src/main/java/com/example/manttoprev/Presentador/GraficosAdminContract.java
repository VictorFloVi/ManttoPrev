package com.example.manttoprev.Presentador;

import com.github.mikephil.charting.data.LineDataSet;

public interface GraficosAdminContract {
    interface View{
        void showDatosGrafico(LineDataSet lineDataSet);

    }
    interface Presenter{
        void listarMotores();
        void showGraficoMotorSeleccionado(String selectedMotor);

    }
}
