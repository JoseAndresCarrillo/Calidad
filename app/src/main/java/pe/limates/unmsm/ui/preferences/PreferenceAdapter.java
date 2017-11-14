package pe.limates.unmsm.ui.preferences;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Preference;

/**
 * Created by cristinacaballerohervias on 27/09/17.
 */

public class PreferenceAdapter extends ArrayAdapter<Preference> {

    public PreferenceAdapter(Context context, ArrayList<Preference> preferences) {
        super(context, 0, preferences);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.item_prefer, parent, false);
        }

        // Get the {@link Card} object located at this position in the list
        final Preference currentPreference = getItem(position);

        ImageView starImageView = (ImageView) listView.findViewById(R.id.category_star);
        if (currentPreference.getStarColorId() == 1)
            DrawableCompat.setTint(starImageView.getDrawable(), ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
        else
            DrawableCompat.setTint(starImageView.getDrawable(), ContextCompat.getColor(getContext(), R.color.grey_400));

        TextView nameTextView = (TextView) listView.findViewById(R.id.category_name);
        nameTextView.setText(currentPreference.getCategoryId());

        return listView;
    }

}
