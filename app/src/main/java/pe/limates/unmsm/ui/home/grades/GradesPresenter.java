package pe.limates.unmsm.ui.home.grades;

import android.content.Context;
import android.support.annotation.Nullable;


public class GradesPresenter implements GradesContractor.Presenter{
    private GradesContractor.View view;
    private Context mContext;

    public GradesPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onViewAttached(GradesContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private GradesContractor.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
