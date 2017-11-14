package pe.limates.unmsm.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import pe.limates.unmsm.R;
import pe.limates.unmsm.ui.camera.CameraActivity;
import pe.limates.unmsm.ui.home.HomeActivity;
import pe.limates.unmsm.ui.register.RegisterActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class LoginActivity extends AppCompatActivity implements ILoginView {

    private final String TAG = LoginActivity.this.getClass().getSimpleName();
    private Context mContext;

    private LoginPresenter loginPresenter;
    private ProgressDialog mProgressDialog;
    private MaterialDialog.Builder builder;
    private MaterialDialog dialog;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;
        initializeViews();

        if (loginPresenter == null) {
            loginPresenter = new LoginPresenter(mContext);
        }

    }

    private void initializeViews() {
        mEditTextEmail = (EditText) findViewById(R.id.edit_text_email);
        mEditTextPassword = (EditText) findViewById(R.id.edit_text_password);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle( getString(R.string.login_button_login_text));
        mProgressDialog.setMessage( getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);
    }

    public LoginPresenter getPresenter() {
        return loginPresenter;
    }


    @Override
    protected void onPause() {
        super.onPause();
        hideLoginDialog();
        getPresenter().onViewDettached();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttached(LoginActivity.this);
    }

    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.button_login:
                String email = mEditTextEmail.getText().toString().trim();
                String password = mEditTextPassword.getText().toString().trim();
                getPresenter().login(email, password);
                break;
            case R.id.button_register:
                startActivity(new Intent(mContext, CameraActivity.class));
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                break;

        }
    }

    @Override
    public void showEmptyFieldError(String email, String password) {

        if (TextUtils.isEmpty(email)) {

            builder = new MaterialDialog.Builder(mContext)
                    .title(R.string.default_error_text)
                    .content(R.string.login_email_required)
                    .positiveText(R.string.default_ok_text).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });

            dialog = builder.build();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);

        } else {

            builder = new MaterialDialog.Builder(mContext)
                    .title(R.string.default_error_text)
                    .content(R.string.login_password_required)
                    .positiveText(R.string.default_ok_text).onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    });

            dialog = builder.build();
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public void showLoginDialog() {
        mProgressDialog.show();
    }

    @Override
    public void showLoginErrorDialog() {
        builder = new MaterialDialog.Builder(mContext)
                .title(R.string.default_error_text)
                .content(R.string.login_error_auth)
                .positiveText(R.string.default_ok_text).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });

        dialog = builder.build();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void showLoginConnectionErrorDialog() {
        builder = new MaterialDialog.Builder(mContext)
                .title(R.string.default_error_text)
                .content(R.string.error_timeout_error_text)
                .positiveText(R.string.default_ok_text).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });

        dialog = builder.build();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void showLoginInternalErrorDialog() {
        builder = new MaterialDialog.Builder(mContext)
                .title(R.string.default_error_text)
                .content(R.string.error_500_text)
                .positiveText(R.string.default_ok_text).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                });

        dialog = builder.build();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideLoginDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showEmailError() {
        mEditTextEmail.setError(getText(R.string.login_error_email));
    }

    @Override
    public void showPasswordError() {
        mEditTextPassword.setError(getText(R.string.login_error_password_length_hint));
    }

    @Override
    public void startHomeActivity() {
        startActivity(new Intent(mContext, HomeActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }


}
