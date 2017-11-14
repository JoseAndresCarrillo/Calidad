package pe.limates.unmsm.ui.home.reminders.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Reminder;
import pe.limates.unmsm.ui.home.reminders.RemindersFragment;


public class ReminderNewFragment extends Fragment {

    private Context mContext;
    private final String TAG = ReminderNewFragment.class.getSimpleName();
    TextView reminderNewTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        mContext = getActivity();
        final View rootView = inflater.inflate(R.layout.fragment_reminder_new, container, false);
        reminderNewTextView = rootView.findViewById(R.id.reminder_new);

        rootView.findViewById(R.id.reminder_create_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rmdr = reminderNewTextView.getText().toString();
                Toast.makeText(mContext, rmdr, Toast.LENGTH_SHORT).show();
                /*FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                RemindersFragment fragment2 = new RemindersFragment();
                Bundle bundle = new Bundle();
                Reminder myReminder = new Reminder(rmdr, getRandomColor());
                bundle.putSerializable("my_reminder", myReminder);
                fragment2.setArguments(bundle);
                ft.replace(android.R.id.content, fragment2);
                ft.addToBackStack(null);*/
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
