package pe.limates.unmsm.ui.home.schedule;

import java.util.ArrayList;

import pe.limates.unmsm.model.Course;
import pe.limates.unmsm.model.Schedule;

public class ScheduleContractor {
    interface View {
        void updateAdapter();
        void showRecycler();
        void hideRecycler();
        void stopRefresh();
        //methods here...
    }
    interface Presenter {
        //methods here...
        ArrayList<Course> getCourses();
        void onViewDettached();
        void onViewAttached(ScheduleContractor.View view);
    }
}
