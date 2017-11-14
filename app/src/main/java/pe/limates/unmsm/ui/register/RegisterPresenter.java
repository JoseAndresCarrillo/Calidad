package pe.limates.unmsm.ui.register;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Major;
import pe.limates.unmsm.model.Name;
import pe.limates.unmsm.model.RegisterPost;
import pe.limates.unmsm.model.User;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pe.limates.unmsm.util.app.App.user_info;


public class RegisterPresenter implements RegisterContractor.Presenter {
    private RegisterContractor.View view;
    private final String TAG = RegisterPresenter.class.getSimpleName();
    private Context mContext;
    private RetrofitBuilder connection;
    private ArrayList<Major> majors = new ArrayList<>();
    private boolean validFields;

    public RegisterPresenter(Context mContext) {
        this.mContext = mContext;
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public void register(String password,
                         String confirmPassword, String code, String email, String dni,
                         String name, String lastName, Integer major) {
        getView().showLoginDialog();
        validFields = true;
        try {
            if (TextUtils.isEmpty(name)) {
                validFields = false;
                getView().showFieldError(R.id.register_name, R.string.login_name_error);
            }
            if (TextUtils.isEmpty(lastName)) {
                validFields = false;
                getView().showFieldError(R.id.register_lastname, R.string.login_last_name_error);
            }
            if (!isValidPassword(password)) {
                validFields = false;
                getView().showFieldError(R.id.register_password, R.string.login_error_password_length_hint);
            }
            if (!isValidEmail(email)) {
                validFields = false;
                getView().showFieldError(R.id.register_email, R.string.login_email_error);
            }
            if (!isValidDni(dni)) {
                validFields = false;
                getView().showFieldError(R.id.register_dni, R.string.login_register_dni_error);
            }
            if (!isValidCode(code)) {
                validFields = false;
                getView().showFieldError(R.id.register_code, R.string.login_register_code_error);
            }
            if (!isValidConfirmPass(password, confirmPassword)) {
                validFields = false;
                getView().showFieldError(R.id.register_confirm_password, R.string.login_password_must_coincide_error);
            }
            if (major == null) {
                validFields = false;
            }
            Log.d(TAG, "gg: " + major);
        } catch (NullPointerException e) {
            Log.d(TAG, "View is not attached");
            validFields = false;
        }
        if(validFields){
//            Log.d(TAG, "yey");
            RegisterPost registerPost = new RegisterPost(new User(new Name(name,lastName), email, password), dni, code, major);
            if (connection.getRetrofit() != null) {
                UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
                Call<ResponseBody> call = service.register(registerPost);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(isAttached()){
                            getView().hideLoginDialog();
                            switch (response.code()){
                                case 200:
                                    getView().showRegisterSuccessful();
                                    break;
                                default:
                                    getView().showRegisterError();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                       if(isAttached()){
                           getView().hideLoginDialog();
                           getView().showRegisteInternalErrorDialog();
                       }
                    }
                });
            }
        }
        else{
            getView().hideLoginDialog();
        }

    }

    @Override
    public ArrayList<Major> getMajors() {
        majors.clear();
        getView().showProgressBar();
        if (connection.getRetrofit() != null) {
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
            Call<ArrayList<Major>> call = service.getMajors();
            call.enqueue(new Callback<ArrayList<Major>>() {
                @Override
                public void onResponse(Call<ArrayList<Major>> call, Response<ArrayList<Major>> response) {
                    if(isAttached()){
                        getView().hideProgressBar();
                        switch (response.code()){
                            case 200:
                                Log.d(TAG, "yey");
                                majors.addAll(response.body());
                                Log.d(TAG, majors.get(0).getName());
                                getView().updateSpinner();
                                break;
                            default:
                        }
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Major>> call, Throwable t) {
//                    if(isAttached()) getView().hideProgressBar();
                }
            });

        }
        return majors;
    }

    @Override
    public void onViewAttached(RegisterContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private RegisterContractor.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }

    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private boolean isValidPassword(String password) {
        return password.length() > 6;
    }


    private boolean isValidDni(String dni) {
        return dni.length() == 8;
    }

    private boolean isValidCode(String code) {
        return code.length() == 8;
    }

    private boolean isValidConfirmPass(String password, String confirmPassword) {
        return confirmPassword.equals(password);
    }
}
