package pe.limates.unmsm.ui.maps;


import java.util.ArrayList;

import pe.limates.unmsm.model.Place;

public class MapsContractor {
    interface View {
        void putMarkers();
        void goPlaceDetailsActivity(Place place);
        void showLoadingDialog();
        void hideLoadingdialog();
        //methods here...
    }
    interface Presenter {
        //methods here...
        void getPlaceInfo(String id);
        ArrayList<Place> getPlaces();
        void onViewDettached();
        void onViewAttached(MapsContractor.View view);
    }
}
