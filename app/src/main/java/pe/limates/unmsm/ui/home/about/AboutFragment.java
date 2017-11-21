package pe.limates.unmsm.ui.home.about;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.limates.unmsm.R;
import pe.limates.unmsm.ui.home.profile.ProfileFragment;

/**
 * Created by cristinacaballerohervias on 5/11/17.
 */

public class AboutFragment extends Fragment {

    private Context mContext;
    private final String TAG = ProfileFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.nav_about);

        mContext = getActivity();
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        return rootView;

    }
}
