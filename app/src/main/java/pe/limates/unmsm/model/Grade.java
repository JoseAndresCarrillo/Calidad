package pe.limates.unmsm.model;

/**
 * Created by cristinacaballerohervias on 28/09/17.
 */

public class Grade {
    private int mIconId;
    private int mCourseId;
    private int mGradeId;

    public Grade(int courseId, int gradeId) {
        mCourseId = courseId;
        mGradeId = gradeId;
    }

    public void setIconId(int iconId) {
        mIconId = iconId;
    }

    public int getIconId() {
        return mIconId;
    }

    public int getCourseId() {
        return mCourseId;
    }

    public int getGradeId() {
        return mGradeId;
    }
}
