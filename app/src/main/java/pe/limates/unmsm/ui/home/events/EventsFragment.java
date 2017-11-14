package pe.limates.unmsm.ui.home.events;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Event;
import pe.limates.unmsm.ui.home.events.adapters.EventsAdapter;

public class EventsFragment extends Fragment implements EventsContractor.View{

    private EventsContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = EventsFragment.class.getSimpleName();

    private RecyclerView mRecycler;
    private EventsAdapter mAdapter;
    private SwipeRefreshLayout mSwipe;
    private ArrayList<Event> mArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        mContext = getActivity();
        //initilize presenter
        if (presenter == null) {
            presenter = new EventsPresenter(mContext);
        }

        initializeViews(rootView);

        getPresenter().onViewAttached(EventsFragment.this);
        mAdapter = new EventsAdapter(mContext, mArrayList = getPresenter().getEvents());
        mRecycler.setAdapter(mAdapter);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        ((SimpleItemAnimator) mRecycler.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecycler.setLayoutManager(mLayoutManager);
        mAdapter.notifyDataSetChanged();

        mSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getEvents();
            }
        });

        return  rootView;
    }

    private void initializeViews(View view) {
        mSwipe = view.findViewById(R.id.events_swipe);
        mRecycler = view.findViewById(R.id.events_recycler);
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
        getPresenter().onViewAttached(EventsFragment.this);
        if(mArrayList.size() > 0) showRecycler();
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
        stopRefresh();
    }


    private EventsContractor.Presenter getPresenter(){
        return presenter;
    }
}
