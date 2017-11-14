package pe.limates.unmsm.ui.home;

import android.content.Context;

import pe.limates.unmsm.util.session.SessionManager;

/**
 * Created by josan on 1/10/2017.
 */

public class HomePresenter {
    private Context mContext;
    private IHomeActivity iHomeActivity;
    private SessionManager session;

    public HomePresenter(Context context) {
        this.mContext = context;
    }

    public void onViewAttached(IHomeActivity iHomeActivity){
        this.iHomeActivity = iHomeActivity;

        session = new SessionManager(mContext);

        if (!session.isLoggedIn()) {
            logoutUser();
        } else {
            session.getUserInfo();
        }

    }

    public void onViewDettached() {
        iHomeActivity = null;
    }

    public IHomeActivity getView() {
        return iHomeActivity;
    }

    public void logoutUser() {
        session.setLogin(false);
        getView().startLogOutActivity();
    }
}
