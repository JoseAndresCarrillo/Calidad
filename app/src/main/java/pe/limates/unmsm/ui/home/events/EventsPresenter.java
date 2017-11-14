package pe.limates.unmsm.ui.home.events;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Event;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventsPresenter implements EventsContractor.Presenter {
    private EventsContractor.View view;
    private Context mContext;
    private ArrayList<Event> events;
    private final String TAG = EventsPresenter.class.getSimpleName();
    private RetrofitBuilder connection;

    public EventsPresenter(Context mContext) {
        this.mContext = mContext;
        events = new ArrayList<>();
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public void onViewAttached(EventsContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private EventsContractor.View getView() {
        return view;
    }

    @Override
    public ArrayList<Event> getEvents() {
        if(isAttached()) getView().hideRecycler();
        events.clear();
        if(connection.getRetrofit() != null){
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
//            Log.d(TAG, App.user_info.getToken());
            Call<ArrayList<Event>> call = service.getEvents("Bearer "+App.user_info.getToken());
            call.enqueue(new Callback<ArrayList<Event>>() {
                @Override
                public void onResponse(Call<ArrayList<Event>> call, Response<ArrayList<Event>> response) {
                    switch (response.code()){
                        case 200: events.addAll(response.body());
                            Log.d(TAG, "Events: yey");
                            if(isAttached()) {
                                getView().updateAdapter();
                                getView().showRecycler();
                                getView().stopRefresh();
                            }
                            break;
                        default: Log.d(TAG, response.raw().toString()); break;
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Event>> call, Throwable t) {
                    Log.d(TAG, "Events on failture");
                }
            });
        }
        return events;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
