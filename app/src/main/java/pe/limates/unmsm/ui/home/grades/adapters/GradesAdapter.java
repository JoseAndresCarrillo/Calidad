package pe.limates.unmsm.ui.home.grades.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Grade;

/**
 * Created by cristinacaballerohervias on 28/09/17.
 */

public class GradesAdapter extends ArrayAdapter<Grade> {

    public GradesAdapter(Context context, ArrayList<Grade> grades) {
        super(context, 0, grades);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View cardListView = convertView;
        if (cardListView == null) {
            cardListView = LayoutInflater.from(getContext()).inflate(R.layout.item_grade, parent, false);
        }

        // Get the {@link Card} object located at this position in the list
        final Grade currentGrade = getItem(position);

        if (Double.parseDouble(getContext().getString(currentGrade.getGradeId())) >= 10.5)
            currentGrade.setIconId(R.drawable.ic_mood_black_24dp);
        else
            currentGrade.setIconId(R.drawable.ic_mood_bad_black_24dp);

        ImageView iconImageView = (ImageView) cardListView.findViewById(R.id.grade_icon);
        iconImageView.setImageResource(currentGrade.getIconId());

        TextView courseTextView = (TextView) cardListView.findViewById(R.id.grade_course);
        courseTextView.setText(currentGrade.getCourseId());

        TextView numTextView = (TextView) cardListView.findViewById(R.id.grade_num);
        numTextView.setText(currentGrade.getGradeId());

        return cardListView;
    }
}
