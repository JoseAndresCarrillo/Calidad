package pe.limates.unmsm.ui.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import pe.limates.unmsm.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExampleActivity extends AppCompatActivity implements ExampleContractor.View{

    private ExampleContractor.Presenter presenter;
    private Context mContext;
    private final String TAG = ExampleActivity.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        mContext=this;
        if (presenter == null) {
            presenter = new ExamplePresenter(mContext);
        }
    }
//***** For Fragments *****
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Log.d(TAG, "onCreate()");
//        View rootView = inflater.inflate(R.layout.activity_example, container, false);
//        mContext = getActivity();
//        //initilize presenter
//        return rootView;
//    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttached(ExampleActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_in_back, R.anim.right_out_back);
    }

    private ExampleContractor.Presenter getPresenter(){
        return presenter;
    }
}
