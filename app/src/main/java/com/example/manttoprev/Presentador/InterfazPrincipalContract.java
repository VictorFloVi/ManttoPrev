package com.example.manttoprev.Presentador;

public interface InterfazPrincipalContract {

    interface View{
        void showRegistrarTextView(boolean show);
    }

    interface Presenter{
        void obtenerRolUsuario();
    }
}
