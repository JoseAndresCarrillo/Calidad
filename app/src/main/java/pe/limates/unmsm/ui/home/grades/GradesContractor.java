package pe.limates.unmsm.ui.home.grades;


public class GradesContractor {
    interface View {
        //methods here...
    }
    interface Presenter {
        //methods here...
        void onViewDettached();
        void onViewAttached(GradesContractor.View view);
    }
}
