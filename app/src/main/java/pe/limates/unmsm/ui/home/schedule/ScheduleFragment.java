package pe.limates.unmsm.ui.home.schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Course;
import pe.limates.unmsm.ui.home.schedule.adapters.ScheduleAdapter;
import pe.limates.unmsm.ui.home.schedule.fragments.ScheduleNewFragment;


public class ScheduleFragment extends Fragment implements ScheduleContractor.View {
    private static ScheduleContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = ScheduleFragment.class.getSimpleName();

    private RecyclerView mRecycler;
    private ScheduleAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;
    private FloatingActionButton mAddButton;
    private ArrayList<Course> mArrayList;
    private ImageView mImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_schedule);

        View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
        mContext = getActivity();
        //initialize presenter
        if (presenter == null) {
            presenter = new SchedulePresenter(mContext);
        }

        initializeViews(rootView);

        getPresenter().onViewAttached(ScheduleFragment.this);
        mAdapter = new ScheduleAdapter(mContext, mArrayList = getPresenter().getCourses());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScheduleNewFragment nextFrag = new ScheduleNewFragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.left_in, 0, R.anim.left_in_back, R.anim.right_out_back);
                ft.replace(R.id.content_layout, nextFrag, "Add new course").
                        addToBackStack(null)
                        .commit();
            }
        });

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getCourses();
            }
        });

        return rootView;
    }

    private void initializeViews(View view) {
        mSwipe = view.findViewById(R.id.schedule_swipe);
        mRecycler = view.findViewById(R.id.schedule_recycler);
        mAddButton = view.findViewById(R.id.add_course_schedule);
        mImage = view.findViewById(R.id.no_schedule);
    }

    @Override
    public void updateAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showRecycler() {
        mSwipe.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRecycler() {
        mSwipe.setVisibility(View.GONE);
    }

    @Override
    public void stopRefresh() {
        mSwipe.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(ScheduleFragment.this);
        if (mArrayList.size() > 0) showRecycler();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
        stopRefresh();
    }

    public static ScheduleContractor.Presenter getPresenter() {
        return presenter;
    }
}
