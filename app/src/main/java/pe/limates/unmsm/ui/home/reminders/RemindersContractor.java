package pe.limates.unmsm.ui.home.reminders;


import java.util.ArrayList;

import pe.limates.unmsm.model.Reminder;

public class RemindersContractor {
    interface View {
        void updateAdapter();
        //methods here...
    }
    interface Presenter {
        //methods here...
        ArrayList<Reminder> getReminders();
        void onViewDettached();
        void onViewAttached(RemindersContractor.View view);
    }
}
