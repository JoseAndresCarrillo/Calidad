package pe.limates.unmsm.ui.home.reminders.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Reminder;
import pe.limates.unmsm.model.ReminderPost;
import pe.limates.unmsm.ui.home.reminders.RemindersFragment;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReminderNewFragment extends Fragment {

    private Context mContext;
    private final String TAG = ReminderNewFragment.class.getSimpleName();
    TextView reminderNewTextView;
    private RetrofitBuilder connection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_reminders);

        mContext = getActivity();
        final View rootView = inflater.inflate(R.layout.fragment_reminder_new, container, false);
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
        reminderNewTextView = rootView.findViewById(R.id.reminder_new);

        rootView.findViewById(R.id.reminder_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(reminderNewTextView.getWindowToken(), 0);
                String rmdr = reminderNewTextView.getText().toString();
                if (connection.getRetrofit() != null) {
                    UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
                    Call<ResponseBody> call = service.registerReminder("Bearer " + App.user_info.getToken(), new ReminderPost(rmdr, App.user_info.getStudent().getCode().toString()));
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            switch (response.code()) {
                                case 200:
                                    Toast.makeText(mContext, "Registro exitoso!", Toast.LENGTH_SHORT).show();
                                    getActivity().getSupportFragmentManager().popBackStack();
                                    break;
                                default:
                                    Toast.makeText(mContext, "Registro fallido...", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.getStackTraceString(t);
                        }
                    });
                }

            }
        });

        return rootView;
    }

    public int getRandomColor() {
        int rdm;
        rdm = (int) (Math.random() * 4);
        switch (rdm) {
            case 1:
                return R.color.red_100;
            case 2:
                return R.color.blue_100;
            case 3:
                return R.color.yellow_100;
            case 4:
                return R.color.green_100;
            default:
                return R.color.grey_100;
        }
    }

}
