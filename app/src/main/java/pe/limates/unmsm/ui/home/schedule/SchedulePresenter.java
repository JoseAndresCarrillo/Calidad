package pe.limates.unmsm.ui.home.schedule;


import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Course;
import pe.limates.unmsm.model.Hour;
import pe.limates.unmsm.model.Schedule;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchedulePresenter implements ScheduleContractor.Presenter {
    private ScheduleContractor.View view;
    private Context mContext;
    private ArrayList<Course> courses;
    private final String TAG = SchedulePresenter.class.getSimpleName();
    private RetrofitBuilder connection;

    public SchedulePresenter(Context mContext) {
        this.mContext = mContext;
        courses = new ArrayList<>();
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public void onViewAttached(ScheduleContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private ScheduleContractor.View getView() {
        return view;
    }

    @Override
    public ArrayList<Course> getCourses() {
        if (isAttached()) getView().hideRecycler();
        courses.clear();
        if (connection.getRetrofit() != null) {
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
//            Log.d(TAG, App.user_info.getToken());
            Call<ArrayList<Course>> call = service.getCourses("Bearer " + App.user_info.getToken());
            call.enqueue(new Callback<ArrayList<Course>>() {
                @Override
                public void onResponse(Call<ArrayList<Course>> call, Response<ArrayList<Course>> response) {
                    switch (response.code()) {
                        case 200:
                            courses.addAll(response.body());
                            Log.d(TAG, "Courses: yey");
                            if (isAttached()) {
                                getView().updateAdapter();
                                getView().showRecycler();
                                getView().stopRefresh();
                            }
                            break;
                        default:
                            Log.d(TAG, response.raw().toString());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Course>> call, Throwable t) {
                    Log.d(TAG, "Courses on failure");
                }
            });
        }
        return courses;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
