package pe.limates.unmsm.ui.home.grades.adapters;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.CourseGrade;
import pe.limates.unmsm.ui.home.HomeActivity;
import pe.limates.unmsm.ui.home.grades.fragments.calculator.CalculatorFragment;

/**
 * Created by cristinacaballerohervias on 28/09/17.
 */

public class GradesAdapter extends RecyclerView.Adapter<GradesAdapter.CustomViewHolder> {

    private final String TAG = GradesAdapter.this.getClass().getSimpleName();
    private View view;
    private Context mContext;
    private ArrayList<CourseGrade> arrayList;

    public GradesAdapter(Context context, ArrayList<CourseGrade> array) {
        this.mContext = context;
        this.arrayList = array;
    }

    @Override
    public GradesAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.item_grade, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GradesAdapter.CustomViewHolder holder, final int position) {
        try {
            final CourseGrade currentGrade = arrayList.get(position);
            holder.mCourseName.setText(currentGrade.getName());
            holder.mCourseGrade.setText(currentGrade.getGrade());
            if ((Double.parseDouble(holder.mCourseGrade.getText().toString())) >= 10.5)
                holder.mCourseIcon.setImageResource(R.drawable.ic_mood_black_24dp);
            else
                holder.mCourseIcon.setImageResource(R.drawable.ic_mood_bad_black_24dp);
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CalculatorFragment nextFrag = new CalculatorFragment();
                    FragmentTransaction ft = ((HomeActivity) mContext).getSupportFragmentManager().beginTransaction();
                    ft.setCustomAnimations(R.anim.left_in, 0, R.anim.left_in_back, R.anim.right_out_back);
                    ft.replace(R.id.content_layout, nextFrag, "Calculator").
                            addToBackStack(null)
                            .commit();

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

    public void setFilter(ArrayList<CourseGrade> newGradesArrayList) {
        arrayList = new ArrayList<>();
        arrayList.addAll(newGradesArrayList);
        notifyDataSetChanged();
    }

    public ArrayList<CourseGrade> getAdapterArrayList() {
        return arrayList;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {

        private CardView mCardView;
        private ImageView mCourseIcon;
        private TextView mCourseName;
        private TextView mCourseGrade;

        private CustomViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.grade_card);
            mCourseIcon = itemView.findViewById(R.id.grade_icon);
            mCourseName = itemView.findViewById(R.id.grade_course);
            mCourseGrade = itemView.findViewById(R.id.grade_num);
        }

    }
}