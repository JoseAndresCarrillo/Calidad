package pe.limates.unmsm.ui.register;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Major;
import pe.limates.unmsm.ui.register.adapters.MajorSAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements RegisterContractor.View {

    private RegisterContractor.Presenter presenter;
    private Context mContext;
    private MaterialDialog.Builder builder;
    private ProgressBar mProgressBar;
    private MaterialDialog dialog;
    private TextInputEditText mPassword, mConfirmPassword, mName, mLastName, mCode, mDni, mEmail;
    private final String TAG = RegisterActivity.class.getSimpleName();
    private MajorSAdapter mMajorsAdapter;
    private ProgressDialog mProgressDialog;
    private ArrayList<Major> mMajorArrayList;
    private Spinner mMajors;
    private String mStringCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;
        if (presenter == null) {
            presenter = new RegisterPresenter(mContext);
        }
        mStringCode = getIntent().getStringExtra("code");
        if (mStringCode != null) {
            getPresenter().onViewAttached(RegisterActivity.this);
            initializeViews();
        } else {
            onBackPressed();
        }
    }


    private void initializeViews() {
        mProgressBar = findViewById(R.id.register_progress_bar);
        mPassword = findViewById(R.id.register_password);
        mConfirmPassword = findViewById(R.id.register_confirm_password);
        mCode = findViewById(R.id.register_code);
        mCode.setText(mStringCode);
        mCode.setEnabled(false);
        mEmail = findViewById(R.id.register_email);
        mDni = findViewById(R.id.register_dni);
        mName = findViewById(R.id.register_name);
        mLastName = findViewById(R.id.register_lastname);
        mMajors = findViewById(R.id.register_spinner_major);
        mMajorArrayList = new ArrayList<>();
        mMajorsAdapter = new MajorSAdapter(mContext, mMajorArrayList = getPresenter().getMajors());
        mMajors.setAdapter(mMajorsAdapter);
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setMessage(getText(R.string.default_loading_text));
        mProgressDialog.setCancelable(false);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_send_button:
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                String code = mCode.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String dni = mDni.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String lastName = mLastName.getText().toString().trim();
                Integer major = ((Major) mMajors.getSelectedItem()).getId();
                getPresenter().register(password, confirmPassword,
                        code, email, dni, name, lastName, major);
                break;
        }
    }

    @Override
    public void updateSpinner() {
        mMajorsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showRegisterError() {
        builder = new MaterialDialog.Builder(mContext)
                .title(R.string.default_error_text)
                .content("Usuario ya registrado!")
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
    public void showRegisteInternalErrorDialog() {
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
    public void showLoginDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoginDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showRegisterSuccessful() {
        Toast.makeText(mContext, "Registro exitoso!", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void showFieldError(int id, int string) {
        ((EditText) findViewById(id)).setError(getText(string));
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttached(RegisterActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoginDialog();
        getPresenter().onViewDettached();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_in_back, R.anim.right_out_back);
    }

    private RegisterContractor.Presenter getPresenter() {
        return presenter;
    }
}
