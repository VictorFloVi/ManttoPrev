package com.example.manttoprev.Presentador;

public interface RegistroUsuarioContract {
    interface View {
        void showErrorMessage(String message);
        void showSuccessMessage(String message);
    }

    interface Presenter {
        void registrarUsuario(String nombreCompleto, String dni, String email, String password, String rol);
    }
}
