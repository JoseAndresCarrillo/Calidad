package pe.limates.unmsm.ui.example;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pe.limates.unmsm.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExampleFragment extends Fragment implements ExampleContractor.View{

    private ExampleContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = ExampleFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        View rootView = inflater.inflate(R.layout.activity_example, container, false);
        mContext = getActivity();
        //initilize presenter
        if (presenter == null) {
            presenter = new ExamplePresenter(mContext);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPresenter().onViewAttached(ExampleFragment.this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    private ExampleContractor.Presenter getPresenter(){
        return presenter;
    }
}
