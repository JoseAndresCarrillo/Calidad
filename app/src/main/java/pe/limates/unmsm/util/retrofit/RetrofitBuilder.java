package pe.limates.unmsm.util.retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import pe.limates.unmsm.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//this creates and validates retrofit objects
public class RetrofitBuilder {

    private Context mContext;
    private Retrofit retrofit;
    private String url;
    private final String TAG;

    public RetrofitBuilder(Context context, final String TAG, String url) {
        this.mContext = context;
        this.url = url;
        this.TAG = TAG;
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.i(TAG, "Retrofit created!");
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public Retrofit getRetrofit() {
        if(isOnline()){
            return retrofit;
        }
        else{
            Toast.makeText(mContext, mContext.getResources().getString(R.string.error_network_text), Toast.LENGTH_LONG).show();
            return null;
        }

    }
}
