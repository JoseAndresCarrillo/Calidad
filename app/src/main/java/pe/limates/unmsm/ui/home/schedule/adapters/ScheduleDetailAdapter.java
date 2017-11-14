package pe.limates.unmsm.ui.home.schedule.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Schedule;


public class ScheduleDetailAdapter extends RecyclerView.Adapter<ScheduleDetailAdapter.CustomViewHolder> {

    private final String TAG = ScheduleDetailAdapter.this.getClass().getSimpleName();
    private View view;
    private Context mContext;
    private ArrayList<Schedule> arrayList;

    public ScheduleDetailAdapter(Context mContext, ArrayList<Schedule> array) {
        this.mContext = mContext;
        this.arrayList = array;
    }

    @Override
    public ScheduleDetailAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_schedule_daytime, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ScheduleDetailAdapter.CustomViewHolder holder, int position) {
        try {
            final Schedule schedule = arrayList.get(position);
            holder.day.setText(schedule.getDay());
            holder.time.setText(schedule.getStartHour().getH() + ":"
                    + schedule.getStartHour().getM() + "-"
                    + schedule.getEndHour().getH() + ":"
                    + schedule.getEndHour().getM());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setFilter(ArrayList<Schedule> newCourseArrayList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(newCourseArrayList);
        notifyDataSetChanged();
    }

    public ArrayList<Schedule> getAdapterArrayList() {
        return arrayList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView day, time;

        private CustomViewHolder(View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.course_day);
            time = itemView.findViewById(R.id.course_time);
        }
    }

}
