package pe.limates.unmsm.ui.details_activity;


public class DetailsMarkerContractor {
    interface View {
        //methods here...
    }
    interface Presenter {
        //methods here...
        void onViewDettached();
        void onViewAttached(DetailsMarkerContractor.View view);
    }
}
