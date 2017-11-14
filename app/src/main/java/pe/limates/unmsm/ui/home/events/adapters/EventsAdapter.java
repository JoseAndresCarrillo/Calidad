package pe.limates.unmsm.ui.home.events.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Event;
import pe.limates.unmsm.ui.home.HomeActivity;
import pe.limates.unmsm.ui.home.events.fragments.EventsDetailFragment;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.CustomViewHolder> {

    private final String TAG = EventsAdapter.this.getClass().getSimpleName();
    private View view;
    private Context mContext;
    private ArrayList<Event> arrayList;


    public EventsAdapter(Context mContext, ArrayList<Event> array) {
        this.mContext = mContext;
        this.arrayList = array;
    }

    @Override
    public EventsAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(mContext).inflate(R.layout.item_event, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapter.CustomViewHolder holder, int position) {
        try {
            final Event event = arrayList.get(position);
            holder.name.setText(event.getTitle());
            holder.place.setText(event.getPlace());
            holder.date.setText(event.getDay()+"/"+event.getMonth()+"/"+event.getYear());
            holder.hour.setText(event.getStartHour().getH()+":"+event.getStartHour().getM()+"-"+event.getEndHour().getH()+":"+event.getEndHour().getM());
            holder.seemore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("event", event);
                    EventsDetailFragment fragment = new EventsDetailFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction ft = ((HomeActivity)mContext).getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.left_in, R.anim.right_out_frag, R.anim.left_in_back , R.anim.right_out_back);
                    ft.replace(R.id.content_layout, fragment, "EventsDetails").
                            addToBackStack(null)
                            .commit();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void setFilter(ArrayList<Event> newEventArrayList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(newEventArrayList);
        notifyDataSetChanged();
    }

    public ArrayList<Event> getAdapterArrayList() {
        return arrayList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView name, place, date, hour, seemore;

        private CustomViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.event_title);
            place = itemView.findViewById(R.id.event_place);
            date = itemView.findViewById(R.id.event_date);
            hour = itemView.findViewById(R.id.event_time);
            seemore = itemView.findViewById(R.id.item_see_more);
        }
    }
}
