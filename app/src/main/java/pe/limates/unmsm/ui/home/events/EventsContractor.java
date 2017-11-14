package pe.limates.unmsm.ui.home.events;


import java.util.ArrayList;

import pe.limates.unmsm.model.Event;

public class EventsContractor {
    interface View {
        void updateAdapter();
        void showRecycler();
        void hideRecycler();
        void stopRefresh();
        //methods here...
    }
    interface Presenter {
        //methods here...
        ArrayList<Event> getEvents();
        void onViewDettached();
        void onViewAttached(EventsContractor.View view);
    }
}
