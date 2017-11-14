package pe.limates.unmsm.ui.register;


import java.util.ArrayList;

import pe.limates.unmsm.model.Major;

public class RegisterContractor {
    interface View {
        void showFieldError(int id, int string);
        void showRegisterSuccessful();
        void showRegisteInternalErrorDialog();
        void showRegisterError();
        void showLoginDialog();
        void hideLoginDialog();
        void updateSpinner();
        void showProgressBar();
        void hideProgressBar();
        //methods here...
    }
    interface Presenter {
        //methods here...
        ArrayList<Major> getMajors();
        void register(String password, String confirmPassword, String code, String email, String dni, String name, String lastName, Integer major);
        void onViewDettached();
        void onViewAttached(RegisterContractor.View view);
    }
}
