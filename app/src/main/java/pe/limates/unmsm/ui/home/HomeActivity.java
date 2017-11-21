package pe.limates.unmsm.ui.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import pe.limates.unmsm.R;
import pe.limates.unmsm.ui.home.about.AboutFragment;
import pe.limates.unmsm.ui.home.events.EventsFragment;
import pe.limates.unmsm.ui.home.grades.GradesFragment;
import pe.limates.unmsm.ui.home.profile.ProfileFragment;
import pe.limates.unmsm.ui.home.reminders.RemindersFragment;
import pe.limates.unmsm.ui.home.schedule.ScheduleFragment;
import pe.limates.unmsm.ui.login.LoginActivity;
import pe.limates.unmsm.ui.maps.MapsActivity;
import pe.limates.unmsm.util.app.App;

public class HomeActivity extends AppCompatActivity implements IHomeActivity {

    private Toolbar toolbar;
    private Context mContext;

    private HomePresenter homePresenter;
    private Drawer drawerResult;
    private Fragment fragment;
    private MaterialDialog.Builder builder;
    private MaterialDialog dialog;
    private final String TAG = HomeActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        if (homePresenter == null) {
            homePresenter = new HomePresenter(mContext);
        }

        setUpToolbar();

        FirebaseMessaging.getInstance().subscribeToTopic("test");
        FirebaseInstanceId.getInstance().getToken();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.i("tokenMain", "" + token);
        RegisterToken(token);

    }

    private void RegisterToken(String refreshedToken) {
        Log.i("token", "" + refreshedToken);
        String URL = "http://marcadorperu.com/public_proycris/api/v1/user/token?user_token=" + refreshedToken;
        Log.i("url", URL);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Response error", "error");

            }
        });

        queue.add(stringRequest);

    }

    public HomePresenter getPresenter() {
        return homePresenter;
    }

    private void setUpToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.nav_home));
        setSupportActionBar(toolbar);
    }

    //clean fragment back stack!
    private void cleanStack() {
        Log.d(TAG, "Clean stack!");
        this.getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void setUpNavDrawer() {

        new DrawerBuilder().withActivity(this).build();

        PrimaryDrawerItem home = new PrimaryDrawerItem().withIdentifier(R.string.nav_home).withName(R.string.nav_home).withSelectable(false).withIcon(R.drawable.ic_home_black_24dp);
        PrimaryDrawerItem events = new PrimaryDrawerItem().withIdentifier(R.string.nav_events).withName(R.string.nav_events).withSelectable(false).withIcon(R.drawable.ic_event_black_24dp);
        PrimaryDrawerItem maps = new PrimaryDrawerItem().withIdentifier(R.string.nav_maps).withName(R.string.nav_maps).withSelectable(false).withIcon(R.drawable.ic_place_black_24dp);
        PrimaryDrawerItem reminders = new PrimaryDrawerItem().withIdentifier(R.string.nav_reminders).withName(R.string.nav_reminders).withSelectable(false).withIcon(R.drawable.ic_note_black_24dp);
        PrimaryDrawerItem schedule = new PrimaryDrawerItem().withIdentifier(R.string.nav_schedule).withName(R.string.nav_schedule).withSelectable(false).withIcon(R.drawable.ic_date_range_black_24dp);
        PrimaryDrawerItem notes = new PrimaryDrawerItem().withIdentifier(R.string.nav_grades).withName(R.string.nav_grades).withSelectable(false).withIcon(R.drawable.ic_done_all_black_24dp);
        PrimaryDrawerItem about = new PrimaryDrawerItem().withIdentifier(R.string.nav_about).withName(R.string.nav_about).withSelectable(false).withIcon(R.drawable.ic_info_black_24dp);
        PrimaryDrawerItem logout = new PrimaryDrawerItem().withIdentifier(R.string.nav_logout).withName(R.string.nav_logout).withSelectable(false).withIcon(R.drawable.ic_power_settings_new_black_24dp);

        if (getCurrentFragment() == null) loadFragment(R.string.nav_home);

        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.material_unmsm)
                .withSelectionListEnabledForSingleProfile(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(App.user_info.getStudent().getUser().getName().getLast().toUpperCase() + ", " + App.user_info.getStudent().getUser().getName().getFirst().toUpperCase())
                                .withEmail(App.user_info.getStudent().getMajor().getName().toUpperCase()).withIcon(R.drawable.juan_gabriel)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        cleanStack();
                        loadFragment(R.string.nav_home);
                        return false;
                    }
                })
                .build();

        //create the drawer and remember the `Drawer` result object
        drawerResult = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        home, events, maps,
                        reminders, schedule, notes,
                        about, logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        cleanStack();
                        int id = (int) drawerItem.getIdentifier();
                        switch (id) {
                            default:
                                loadFragment(id);
                                break;
                            case R.string.nav_maps:
                                startActivity(new Intent(mContext, MapsActivity.class));
                                overridePendingTransition(R.anim.left_in, R.anim.right_out);
                                return true;
                            case R.string.nav_logout:
                                builder = new MaterialDialog.Builder(mContext)
                                        .title(R.string.dialog_logout_text)
                                        .positiveText("Sí").onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                getPresenter().logoutUser();
                                            }
                                        })
                                        .negativeText("No").onNegative(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                                dialog.dismiss();
                                            }
                                        });
                                dialog = builder.build();
                                dialog.show();
                                dialog.setCanceledOnTouchOutside(false);
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    private void loadFragment(int name) {
        boolean none = true;
        if (!isFragmentVisible(name)) {
            switch (name) {
                case R.string.nav_home:
                    fragment = new ProfileFragment();
                    break;
                case R.string.nav_events:
                    fragment = new EventsFragment();
                    break;
                case R.string.nav_reminders:
                    fragment = new RemindersFragment();
                    break;
                case R.string.nav_schedule:
                    fragment = new ScheduleFragment();
                    break;
                case R.string.nav_grades:
                    fragment = new GradesFragment();
                    break;
                case R.string.nav_about:
                    fragment = new AboutFragment();
                    break;
                default:
                    none = false;
                    break;
            }
            if (none) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.left_in, 0);
                ft.replace(R.id.content_layout, fragment, getString(name)).commit();
            }
        }
    }

    //handle on back pressed for all fragments
    @Override
    public void onBackPressed() {
        if (drawerResult.getDrawerLayout().isDrawerOpen(GravityCompat.START)) {
            drawerResult.closeDrawer();
        } else {
            if (isFragmentVisible(R.string.nav_home)) {
                super.onBackPressed();
            } else {
                if (getSupportFragmentManager().getBackStackEntryCount() != 0)
                    getSupportFragmentManager().popBackStack();
                else
                    loadFragment(R.string.nav_home);
            }
        }
    }

    //to know if a fragment is currently been shown on the screen
    public boolean isFragmentVisible(int tag) {
        Fragment f = getSupportFragmentManager().findFragmentByTag(getString(tag));
        if (f != null && f.isVisible()) return true;
        return false;
    }

    Fragment getCurrentFragment() {
        return getSupportFragmentManager()
                .findFragmentById(R.id.content_layout);
    }

    @Override
    public void startLogOutActivity() {
        startActivity(new Intent(mContext, LoginActivity.class));
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPresenter().onViewDettached();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        setUpNavDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawerResult != null) drawerResult.closeDrawer();
        getPresenter().onViewAttached(HomeActivity.this);
    }
}