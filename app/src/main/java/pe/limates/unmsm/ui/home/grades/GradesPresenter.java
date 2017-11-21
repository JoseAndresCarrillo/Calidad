package pe.limates.unmsm.ui.home.grades;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.CourseGrade;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GradesPresenter implements GradesContractor.Presenter {
    private GradesContractor.View view;
    private Context mContext;
    private final String TAG = GradesPresenter.class.getSimpleName();

    private ArrayList<CourseGrade> grades;
    private RetrofitBuilder connection;

    public GradesPresenter(Context context) {
        grades = new ArrayList<>();
        this.mContext = context;
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public void onViewAttached(GradesContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private GradesContractor.View getView() {
        return view;
    }

    @Override
    public ArrayList<CourseGrade> getCoursesGrades() {
        grades.clear();
        if (connection.getRetrofit() != null) {
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
            Call<ArrayList<CourseGrade>> call = service.getCoursesGrades("Bearer " + App.user_info.getToken());
            call.enqueue(new Callback<ArrayList<CourseGrade>>() {
                @Override
                public void onResponse(Call<ArrayList<CourseGrade>> call, Response<ArrayList<CourseGrade>> response) {
                    switch (response.code()) {
                        case 200:
                            grades.addAll(response.body());
                            Log.d(TAG, "Grades: yey");
                            Log.d(TAG, "size: " + response.body().size());
                            if (isAttached()) {
                                getView().updateAdapter();
                            }
                            break;
                        default:
                            Log.d(TAG, response.raw().toString());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<CourseGrade>> call, Throwable t) {
                    Log.d(TAG, "Grades on failure");
                }
            });
        }
        return grades;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
