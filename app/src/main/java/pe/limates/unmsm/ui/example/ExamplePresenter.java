package pe.limates.unmsm.ui.example;

import android.content.Context;
import android.support.annotation.Nullable;


public class ExamplePresenter implements ExampleContractor.Presenter{
    private ExampleContractor.View view;
    private Context mContext;

    public ExamplePresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onViewAttached(ExampleContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private ExampleContractor.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
