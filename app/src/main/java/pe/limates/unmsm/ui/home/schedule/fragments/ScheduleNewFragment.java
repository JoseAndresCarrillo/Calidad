package pe.limates.unmsm.ui.home.schedule.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pe.limates.unmsm.R;
import pe.limates.unmsm.util.TimePickerFragment;
import pe.limates.unmsm.model.CoursePost;
import pe.limates.unmsm.model.Hour;
import pe.limates.unmsm.model.Schedule;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleNewFragment extends Fragment {

    private Context mContext;
    private final String TAG = ScheduleNewFragment.class.getSimpleName();

    private View rootView;
    private Spinner mSpinnerDay;
    private Button mButtonStart, mButtonEnd, mButtonOK;
    private TextView mName, mStart, mEnd;

    private RetrofitBuilder connection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_schedule);

        mContext = getActivity();
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
        rootView = inflater.inflate(R.layout.fragment_schedule_new, container, false);

        initializeViews(rootView);

        mButtonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(R.id.schedule_start_hour);
            }
        });

        mButtonEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePickerDialog(R.id.schedule_end_hour);
            }
        });

        mButtonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerCourse(mName.getText().toString(), mSpinnerDay.getSelectedItem().toString(), mStart.getText().toString(), mEnd.getText().toString());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mName.getWindowToken(), 0);
            }
        });

        return rootView;
    }

    private void initializeViews(View rootView) {
        mName = rootView.findViewById(R.id.schedule_course_name);
        mSpinnerDay = rootView.findViewById(R.id.schedule_spinner_day);
        ArrayAdapter mySpinnerAdapter = ArrayAdapter.createFromResource(mContext, R.array.days, android.R.layout.simple_spinner_item);
        mySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDay.setAdapter(mySpinnerAdapter);
        mButtonStart = rootView.findViewById(R.id.button_pick_start);
        mButtonEnd = rootView.findViewById(R.id.button_pick_end);
        mStart = rootView.findViewById(R.id.schedule_start_hour);
        mEnd = rootView.findViewById(R.id.schedule_end_hour);
        mButtonOK = rootView.findViewById(R.id.schedule_new_ok);
    }

    public void showFieldError(int id, int string) {
        ((EditText) rootView.findViewById(id)).setError(getText(string));
    }

    public void showTimePickerDialog(int id) {
        DialogFragment newFragment = new TimePickerFragment(id);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void registerCourse(String name, String day, String startHour, String endHour) {
        boolean validFields = true;
        try {
            if (TextUtils.isEmpty(name)) {
                validFields = false;
                showFieldError(R.id.schedule_course_name, R.string.login_name_error);
            }
            if (day == null) {
                validFields = false;
            }
            if (startHour == null) {
                validFields = false;
            }
            if (endHour == null) {
                validFields = false;
            }
        } catch (NullPointerException e) {
            Log.d(TAG, "View is not attached");
            validFields = false;
        }
        if (validFields) {
            Log.d(TAG, "yey");
            if (connection.getRetrofit() != null) {
                UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
                ArrayList<Schedule> hours = new ArrayList<>();
                hours.add(new Schedule(day, new Hour(startHour.split(":")[0], startHour.split(":")[1]), new Hour(endHour.split(":")[0], endHour.split(":")[1])));
                Call<ResponseBody> call = service.registerCourse("Bearer " + App.user_info.getToken(), new CoursePost(name, hours, App.user_info.getStudent().getCode().toString()));
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
    }

}
