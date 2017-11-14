package pe.limates.unmsm.ui.details_activity;

import android.content.Context;
import android.support.annotation.Nullable;


public class DetailsMarkerPresenter implements DetailsMarkerContractor.Presenter{
    private DetailsMarkerContractor.View view;
    private Context mContext;

    public DetailsMarkerPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onViewAttached(DetailsMarkerContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private DetailsMarkerContractor.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
