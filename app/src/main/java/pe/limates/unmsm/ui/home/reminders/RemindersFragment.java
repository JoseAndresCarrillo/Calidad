package pe.limates.unmsm.ui.home.reminders;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Reminder;
import pe.limates.unmsm.ui.home.reminders.adapters.RemindersAdapter;
import pe.limates.unmsm.ui.home.reminders.fragments.ReminderNewFragment;


public class RemindersFragment extends Fragment implements RemindersContractor.View {

    private RemindersContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = RemindersFragment.class.getSimpleName();

    private ArrayList<Reminder> reminders;
    private RecyclerView mRecycler;
    private RemindersAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_reminders);

        View rootView = inflater.inflate(R.layout.fragment_reminders, container, false);
        mContext = getActivity();
        //initilize presenter
        if (presenter == null) {
            presenter = new RemindersPresenter(mContext);
        }
        initializeViews(rootView);

        getPresenter().onViewAttached(RemindersFragment.this);

        mAdapter = new RemindersAdapter(mContext, reminders = getPresenter().getReminders());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();

        rootView.findViewById(R.id.add_reminder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReminderNewFragment nextFrag = new ReminderNewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.left_in, 0, R.anim.left_in_back, R.anim.right_out_back);
                ft.replace(R.id.content_layout, nextFrag, "Add Reminder").
                        addToBackStack(null)
                        .commit();
            }
        });

        return rootView;
    }

    private void initializeViews(View view) {
//        mSwipe = view.findViewById(R.id.events_swipe);
        mRecycler = view.findViewById(R.id.list_reminders);
    }

    @Override
    public void updateAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(RemindersFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    private RemindersContractor.Presenter getPresenter() {
        return presenter;
    }
}
