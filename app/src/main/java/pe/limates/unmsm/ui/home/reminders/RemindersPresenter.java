package pe.limates.unmsm.ui.home.reminders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Reminder;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cristinacaballerohervias on 5/11/17.
 */

public class RemindersPresenter implements RemindersContractor.Presenter {
    private RemindersContractor.View view;
    private Context mContext;
    private final String TAG = RemindersPresenter.class.getSimpleName();

    private ArrayList<Reminder> reminders;
    private RetrofitBuilder connection;

    public RemindersPresenter(Context context) {
        reminders = new ArrayList<>();
        this.mContext = context;
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public void onViewAttached(RemindersContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private RemindersContractor.View getView() {
        return view;
    }

    @Override
    public ArrayList<Reminder> getReminders() {
        reminders.clear();
        if (connection.getRetrofit() != null) {
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
//            Log.d(TAG, App.user_info.getToken());
            Call<ArrayList<Reminder>> call = service.getReminders("Bearer " + App.user_info.getToken());
            call.enqueue(new Callback<ArrayList<Reminder>>() {
                @Override
                public void onResponse(Call<ArrayList<Reminder>> call, Response<ArrayList<Reminder>> response) {
                    switch (response.code()) {
                        case 200:
                            reminders.addAll(response.body());
                            Log.d(TAG, "Reminders: yey");
                            Log.d(TAG, "size: " + response.body().size());
                            if (isAttached()) {
                                getView().updateAdapter();
//                                getView().showRecycler();
//                                getView().stopRefresh();
                            }
                            break;
                        default:
                            Log.d(TAG, response.raw().toString());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Reminder>> call, Throwable t) {
                    Log.d(TAG, "Reminders on failure");
                }
            });
        }
        return reminders;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
