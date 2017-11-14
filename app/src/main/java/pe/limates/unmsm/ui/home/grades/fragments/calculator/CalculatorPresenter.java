package pe.limates.unmsm.ui.home.grades.fragments.calculator;

import android.content.Context;
import android.support.annotation.Nullable;


public class CalculatorPresenter implements CalculatorContractor.Presenter{
    private CalculatorContractor.View view;
    private Context mContext;

    public CalculatorPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onViewAttached(CalculatorContractor.View view) {
        this.view = view;
    }

    @Override
    public void onViewDettached() {
        view = null;
    }

    @Nullable
    private CalculatorContractor.View getView() {
        return view;
    }

    private boolean isAttached() {
        return getView() != null;
    }
}
