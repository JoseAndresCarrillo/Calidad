package pe.limates.unmsm.ui.home.profile;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.limates.unmsm.R;
import pe.limates.unmsm.util.app.App;

public class ProfileFragment extends Fragment {

    private Context mContext;
    private final String TAG = ProfileFragment.class.getSimpleName();
    private TextView welcomeTextView, firstNameTextView, lastNameTextView, codeTextView, dniTextView, facultyTextView, majorTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        welcomeTextView = rootView.findViewById(R.id.std_welcome);
        firstNameTextView = rootView.findViewById(R.id.std_data_name);
        lastNameTextView = rootView.findViewById(R.id.std_data_lastname);
        codeTextView = rootView.findViewById(R.id.std_data_code);
        dniTextView = rootView.findViewById(R.id.std_data_dni);
        facultyTextView = rootView.findViewById(R.id.std_data_faculty);
        majorTextView = rootView.findViewById(R.id.std_data_major);

        try {
            welcomeTextView.setText(getString(R.string.home_welcome) + " " + App.user_info.getStudent().getUser().getName().getFirst() + "!");
            firstNameTextView.setText(App.user_info.getStudent().getUser().getName().getFirst());
            lastNameTextView.setText(App.user_info.getStudent().getUser().getName().getLast());
            codeTextView.setText(String.valueOf(App.user_info.getStudent().getCode()));
            dniTextView.setText(String.valueOf(App.user_info.getStudent().getDni()));
            facultyTextView.setText(App.user_info.getStudent().getMajor().getFaculty().getName());
            majorTextView.setText(App.user_info.getStudent().getMajor().getName());

        } catch (Exception e) {

        }
        return rootView;

    }

}
