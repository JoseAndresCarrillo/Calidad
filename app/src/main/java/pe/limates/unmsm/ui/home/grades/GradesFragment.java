package pe.limates.unmsm.ui.home.grades;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.CourseGrade;
import pe.limates.unmsm.ui.home.grades.adapters.GradesAdapter;

public class GradesFragment extends Fragment implements GradesContractor.View {

    private GradesContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = GradesFragment.class.getSimpleName();

    private ArrayList<CourseGrade> grades;
    private RecyclerView mRecycler;
    private GradesAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_grades);

        View rootView = inflater.inflate(R.layout.fragment_grades, container, false);
        mContext = getActivity();
        //initialize presenter
        if (presenter == null) {
            presenter = new GradesPresenter(mContext);
        }
        initializeViews(rootView);

        getPresenter().onViewAttached(GradesFragment.this);

        mAdapter = new GradesAdapter(mContext, grades = getPresenter().getCoursesGrades());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();

        return rootView;
    }

    private void initializeViews(View view) {
        mRecycler = view.findViewById(R.id.list_grades);
    }

    @Override
    public void updateAdapter() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(GradesFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }


    private GradesContractor.Presenter getPresenter() {
        return presenter;
    }

}
