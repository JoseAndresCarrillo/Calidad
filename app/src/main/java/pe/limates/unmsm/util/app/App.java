package pe.limates.unmsm.util.app;

import android.app.Application;

import pe.limates.unmsm.model.LoginResult;

public class App extends Application {
    private final String TAG = App.this.getClass().getSimpleName();
    public static LoginResult user_info;
    //Global variables here...

    public App(LoginResult user_info) {
        App.user_info=user_info;
}
}
