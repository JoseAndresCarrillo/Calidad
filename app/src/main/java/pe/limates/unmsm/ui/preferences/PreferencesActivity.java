package pe.limates.unmsm.ui.preferences;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Preference;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        // Create a list of events
        final ArrayList<Preference> preferences = new ArrayList<Preference>();

        preferences.add(new Preference(1, R.string.cat1));
        preferences.add(new Preference(1, R.string.cat2));
        preferences.add(new Preference(0, R.string.cat3));
        preferences.add(new Preference(0, R.string.cat4));
        preferences.add(new Preference(1, R.string.cat5));
        preferences.add(new Preference(1, R.string.cat6));
        preferences.add(new Preference(0, R.string.cat7));
        preferences.add(new Preference(0, R.string.cat8));
        preferences.add(new Preference(1, R.string.cat9));

        // Create the adapter to convert the array to views
        PreferenceAdapter adapter = new PreferenceAdapter(getApplicationContext(), preferences);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list_preferences);
        listView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in_back, R.anim.right_out_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
