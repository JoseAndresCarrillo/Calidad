package pe.limates.unmsm.ui.home.schedule.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Course;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.CustomViewHolder> {

    private final String TAG = ScheduleAdapter.this.getClass().getSimpleName();
    private View view;
    private Context mContext;
    private ArrayList<Course> arrayList;
    private MaterialDialog.Builder builder;
    private MaterialDialog dialog;

    private RetrofitBuilder connection;

    public ScheduleAdapter(Context mContext, ArrayList<Course> array) {
        this.mContext = mContext;
        this.arrayList = array;
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public ScheduleAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_schedule, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.CustomViewHolder holder, final int position) {
        try {
            final Course course = arrayList.get(position);
            ScheduleDetailAdapter adapter = new ScheduleDetailAdapter(mContext, course.getSchedule());
            holder.name.setText(course.getName());
            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder = new MaterialDialog.Builder(mContext)
                            .title(R.string.dialog_delete_text)
                            .positiveText("Sí").onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    deleteCourse(course.getId(), position);
                                }
                            })
                            .negativeText("No").onNegative(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            });
                    dialog = builder.build();
                    dialog.show();
                    dialog.setCanceledOnTouchOutside(false);
                }
            });
            holder.schedule.setAdapter(adapter);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
            ((SimpleItemAnimator) holder.schedule.getItemAnimator()).setSupportsChangeAnimations(false);
            holder.schedule.setLayoutManager(mLayoutManager);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setFilter(ArrayList<Course> newCourseArrayList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(newCourseArrayList);
        notifyDataSetChanged();
    }

    public ArrayList<Course> getAdapterArrayList() {
        return arrayList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView delete;
        private RecyclerView schedule;

        private CustomViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.schedule_course);
            delete = itemView.findViewById(R.id.course_delete);
            schedule = itemView.findViewById(R.id.schedule_detail_recycler);
        }
    }

    public void deleteCourse(String id, final int position) {
        if (connection.getRetrofit() != null) {
            UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
            Call<ResponseBody> call = service.deleteCourse("Bearer " + App.user_info.getToken(), id);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    switch (response.code()) {
                        case 200:
                            Log.d(TAG, "Delete success");
                            arrayList.remove(position);
                            notifyDataSetChanged();
                            break;
                        default:
                            Log.d(TAG, response.raw().toString());
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d(TAG, "Delete failure");
                }
            });
        }

    }

}
