package pe.limates.unmsm.ui.details_activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import pe.limates.unmsm.R;
import pe.limates.unmsm.model.Place;
import pe.limates.unmsm.ui.details_activity.TabFragments.DescriptionFragment;
import pe.limates.unmsm.ui.details_activity.TabFragments.ServiceFragment;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DetailsMarkerActivity extends AppCompatActivity implements DetailsMarkerContractor.View{

    private DetailsMarkerContractor.Presenter presenter;
    private Context mContext;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private final String TAG = DetailsMarkerActivity.class.getSimpleName();
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_marker);
        mContext=this;
        initializeViews();
        setUpToolbar();
        if (presenter == null) {
            presenter = new DetailsMarkerPresenter(mContext);
        }

        mPlace = (Place) getIntent().getSerializableExtra("place");
        if(mPlace == null){
            onBackPressed();
        }
        else{
            Log.d(TAG, mPlace.getName());
            setupViewPager(mViewPager);
        }


    }

    private void setUpToolbar() {

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(R.string.default_marker_details_title);
        setSupportActionBar(mToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
    }

    private void initializeViews() {
        mViewPager = findViewById(R.id.viewpager);
        mTabLayout = findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        bundle.putSerializable("place", mPlace);
        DescriptionFragment descriptionFragment = new DescriptionFragment();
        ServiceFragment serviceFragment = new ServiceFragment();
        descriptionFragment.setArguments(bundle);
        serviceFragment.setArguments(bundle);
        adapter.addFragment(descriptionFragment, getString(R.string.markerDescription));
        adapter.addFragment(serviceFragment, getString(R.string.markerService));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewAttached(DetailsMarkerActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.left_in_back, R.anim.right_out_back);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed(); break;
        }
        return super.onOptionsItemSelected(item);
    }
    private DetailsMarkerContractor.Presenter getPresenter(){
        return presenter;
    }
}
