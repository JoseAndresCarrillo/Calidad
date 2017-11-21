package pe.limates.unmsm.ui.home.events.fragments;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Event;

public class EventsDetailFragment extends Fragment {

    private Context mContext;
    private final String TAG = EventsDetailFragment.class.getSimpleName();

    private TextView mDescription, mOrganizer, mPlace, mDate, mHour, mTitle;
    private Event mEvent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_event_details, container, false);

        initializeViews(rootView);

        mEvent = (Event) getArguments().getSerializable("event");
        if (mEvent != null) {
            setInfo();
        }

        return rootView;
    }

    private void initializeViews(View rootView) {
        mTitle = rootView.findViewById(R.id.event_description_title);
        mDescription = rootView.findViewById(R.id.event_description_details);
        mOrganizer = rootView.findViewById(R.id.event_description_organizer);
        mPlace = rootView.findViewById(R.id.event_description_place);
        mDate = rootView.findViewById(R.id.event_description_date);
        mHour = rootView.findViewById(R.id.event_description_hour);
    }


    public void setInfo() {
        try {
            mTitle.setText(mEvent.getTitle());
            mDescription.setText(mEvent.getDescription());
            mOrganizer.setText(mEvent.getFaculty().getName());
            mPlace.setText(mEvent.getPlace());
            mDate.setText(mEvent.getDay() + "/" + mEvent.getMonth() + "/" + mEvent.getYear());
            mHour.setText(mEvent.getStartHour().getH() + ":" + mEvent.getStartHour().getM() + "-" + mEvent.getEndHour().getH() + ":" + mEvent.getEndHour().getM());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
