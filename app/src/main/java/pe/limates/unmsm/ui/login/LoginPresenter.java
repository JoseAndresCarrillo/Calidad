package pe.limates.unmsm.ui.login;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.LoginPost;
import pe.limates.unmsm.model.LoginResult;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.session.SessionManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginPresenter {

    private final String TAG = LoginPresenter.this.getClass().getSimpleName();
    private Context mContext;


    private ILoginView loginView;
    private boolean validatedEmail, validatedPassword;
    private RetrofitBuilder connection;

    private SessionManager session;

    public LoginPresenter(Context mContext) {
        this.mContext = mContext;
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    public ILoginView getView() {
        return loginView;
    }

    public void onViewAttached(ILoginView loginView) {
        this.loginView = loginView;
        session = new SessionManager(mContext);
        if (session.isLoggedIn()) {
            getView().startHomeActivity();
            Log.d(TAG, "Session from login inside func: " + session.isLoggedIn());
            session.getUserInfo();
        }
    }

    public void onViewDettached() {
        this.loginView = null;
    }

    public void login(String email, String password) {

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {

            getView().showEmptyFieldError(email, password);

        } else {

            validatedEmail = isValidEmail(email);
            validatedPassword = isValidPassword(password);

            if (!validatedEmail) {

                getView().showEmailError();

            } else if (!validatedPassword) {

                getView().showPasswordError();

            } else {
                performLogin(email, password);
            }
        }
    }

    private void performLogin(String email, String password) {
        if(connection.getRetrofit()!=null){
            getView().showLoginDialog();
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
            Call<LoginResult> call = service.login(new LoginPost(email, password));
            call.enqueue(new Callback<LoginResult>() {
                @Override
                public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                    if(isAttached()){
                        getView().hideLoginDialog();
                        if (response.isSuccessful()) {
                            if (response.body().getToken() != null) {
                                session.setUserInfo(response.body());
                                session.setLogin(true);
                                getView().startHomeActivity();
                                Log.i(TAG, "Success!");
                            } else {
                                Log.i(TAG, "Wrong username/password!");
                                getView().showLoginErrorDialog();
                            }
                        } else {
                            Log.d(TAG, "onResponse error" + response.raw() + " " + response.code());
                            switch (response.code()){
                                case 401:
                                    Log.i(TAG, "Wrong username/password!");
                                    getView().showLoginErrorDialog();
                                    break;
                                default:
                                    getView().showLoginInternalErrorDialog();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResult> call, Throwable t) {
                    if(isAttached()){
                        getView().hideLoginDialog();
                        Log.d("Login", "onFailture!" + t.getMessage());
                        getView().showLoginConnectionErrorDialog();
                    }
                }
            });
        }
    }


    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() > 6;
    }

    private boolean isAttached(){
        return getView() != null;
    }

}
