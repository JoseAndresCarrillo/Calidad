package pe.limates.unmsm.ui.home.reminders.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Reminder;
import pe.limates.unmsm.util.app.App;
import pe.limates.unmsm.util.retrofit.RetrofitBuilder;
import pe.limates.unmsm.util.retrofit.UnmsmAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.CustomViewHolder> {

    private final String TAG = RemindersAdapter.this.getClass().getSimpleName();
    private View view;
    private Context mContext;
    private ArrayList<Reminder> arrayList;
    private ArrayList<Integer> colors;
    private RetrofitBuilder connection;


    public RemindersAdapter(Context mContext, ArrayList<Reminder> array) {
        this.mContext = mContext;
        this.arrayList = array;
        colors = new ArrayList<>();
        colors.add(R.color.red_100);
        colors.add(R.color.blue_100);
        colors.add(R.color.yellow_100);
        colors.add(R.color.green_100);
        connection = new RetrofitBuilder(this.mContext, TAG, this.mContext.getString(R.string.BASE_URL));
    }

    @Override
    public RemindersAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_reminder, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RemindersAdapter.CustomViewHolder holder, final int position) {
        try {
            final Reminder reminder = arrayList.get(position);
            holder.mContent.setText(reminder.getDescription());
            holder.mCardView.setBackgroundResource(colors.get((int) (Math.random() * colors.size())));
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, holder.mContent.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (connection.getRetrofit() != null) {
                        UnmsmAPI service = connection.getRetrofit().create(UnmsmAPI.class);
                        Call<ResponseBody> call = service.deleteReminder("Bearer " + App.user_info.getToken(), reminder.get_id());
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                switch (response.code()) {
                                    case 200:
                                        Log.d(TAG, "RemindersAdapter: yey");
                                        Toast.makeText(mContext, "Reminder borrado", Toast.LENGTH_SHORT).show();
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
                                Log.d(TAG, "RemindersAdapter on failure");
                            }
                        });
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setFilter(ArrayList<Reminder> newReminderArrayList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(newReminderArrayList);
        notifyDataSetChanged();
    }

    public ArrayList<Reminder> getAdapterArrayList() {
        return arrayList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView mContent;
        private CardView mCardView;
        private ImageView mDelete;

        private CustomViewHolder(View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.reminder_text);
            mCardView = itemView.findViewById(R.id.reminderCard);
            mDelete = itemView.findViewById(R.id.reminder_delete);
        }

    }
}
