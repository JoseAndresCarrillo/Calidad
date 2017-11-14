package pe.limates.unmsm.ui.maps;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Place;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapsPresenter implements MapsContractor.Presenter{
    private MapsContractor.View view;
    private Context mContext;
    private RetrofitBuilder connection;
    private final String TAG = MapsPresenter.class.getSimpleName();
    private ArrayList<Place> places;
    private Place place;

    public MapsPresenter(Context mContext) {
        this.mContext = mContext;
        places = new ArrayList<>();
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public void onViewAttached(MapsContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Override
    public ArrayList<Place> getPlaces() {
        places.clear();
        if(connection.getRetrofit() != null){
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
//            Log.d(TAG, App.user_info.getToken());
            Call<ArrayList<Place>> call = service.getPlaces("Bearer "+ App.user_info.getToken());
            call.enqueue(new Callback<ArrayList<Place>>() {
                @Override
                public void onResponse(Call<ArrayList<Place>> call, Response<ArrayList<Place>> response) {
                    switch (response.code()){
                        case 200: places.addAll(response.body());
                            Log.d(TAG, "Places: yey");
                            if(isAttached()) {
                                getView().putMarkers();
                            }
                            break;
                        default: Log.d(TAG, response.raw().toString()); break;
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Place>> call, Throwable t) {
                    Log.d(TAG, "Places on failture");
                }
            });
        }
        return places;
    }

    @Override
    public Place getPlaceInfo(String id) {
        if(connection.getRetrofit() != null){
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
            Call<Place> call = service.getPlaceInfo("Bearer "+ App.user_info.getToken(), id);
            call.enqueue(new Callback<Place>() {
                @Override
                public void onResponse(Call<Place> call, Response<Place> response) {
                    switch (response.code()){
                        case 200:
                            place = response.body();
                            Log.d(TAG, "Place info: yey");
                            if(isAttached()) {
                                getView().goPlaceDetailsActivity();
                            }
                            break;
                        default: Log.d(TAG, response.raw().toString()); break;
                    }
                }

                @Override
                public void onFailure(Call<Place> call, Throwable t) {
                    Log.d(TAG, "Places on failture");
                }
            });
        }
        return place;
    }

    @Nullable
    private MapsContractor.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
