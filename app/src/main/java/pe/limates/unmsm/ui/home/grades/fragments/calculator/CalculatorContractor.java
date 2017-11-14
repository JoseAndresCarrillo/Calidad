package pe.limates.unmsm.ui.home.grades.fragments.calculator;


public class CalculatorContractor {
    interface View {
        //methods here...
    }
    interface Presenter {
        //methods here...
        void onViewDettached();
        void onViewAttached(CalculatorContractor.View view);
    }
}
