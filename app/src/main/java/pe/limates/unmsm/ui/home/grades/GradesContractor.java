package pe.limates.unmsm.ui.home.grades;


import java.util.ArrayList;

import pe.limates.unmsm.model.CourseGrade;

public class GradesContractor {
    interface View {
        void updateAdapter();
        //methods here...
    }
    interface Presenter {
        //methods here...
        ArrayList<CourseGrade> getCoursesGrades();
        void onViewDettached();
        void onViewAttached(GradesContractor.View view);
    }
}
