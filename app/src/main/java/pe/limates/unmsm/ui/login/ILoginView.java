package pe.limates.unmsm.ui.login;


public interface ILoginView {

    void showEmptyFieldError(String email, String password);

    void showEmailError();

    void showPasswordError();

    void showLoginErrorDialog();

    void showLoginInternalErrorDialog();

    void showLoginConnectionErrorDialog();

    void showLoginDialog();

    void hideLoginDialog();

    void startHomeActivity();

}

