package com.example.manttoprev.Presentador;

public interface LoginContract {
    interface View {
        void showErrorMessage(String message);

    }

    interface Presenter {

        void accederAdmin(String email, String password);

    }
}
