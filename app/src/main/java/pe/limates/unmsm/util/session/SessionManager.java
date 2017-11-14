package pe.limates.unmsm.util.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

import pe.limates.unmsm.model.LoginResult;
import pe.limates.unmsm.util.app.App;


public class SessionManager {

    private static String LOG_TAG = "session_manager";

    // Shared Preferences
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;

    // Shared preferences file name
    private static final String PREF_NAME = "unmsm";

    private static final int DEFAULT_INT = 0;
    private static final String DEFAULT_STRING = "";
    private static final boolean DEFAULT_BOOLEAN = false;

    // Keys in Shared Pref
    private static final String KEY_USER = "user",
                                KEY_IS_LOGGEDIN = "isLoggedIn";

    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setUserInfo(LoginResult loginResult) {

        Gson gson = new Gson();
        String json = gson.toJson(loginResult);
        editor.putString(KEY_USER, json);
        editor.commit();
    }

    public void getUserInfo() {

        Gson gson = new Gson();
        String json = pref.getString(KEY_USER, DEFAULT_STRING);


        if (json.equals(DEFAULT_STRING)) {
            Log.d(LOG_TAG, "No data saved to the session.");

        } else {

            Log.d(LOG_TAG, "Data exists, setting variables...");
            LoginResult user_info = gson.fromJson(json, LoginResult.class);
            new App(user_info);

        }
    }

    public void setLogin(boolean isLoggedIn) {
        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);
        editor.commit();

        Log.d(LOG_TAG, "User login session modified!");
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}