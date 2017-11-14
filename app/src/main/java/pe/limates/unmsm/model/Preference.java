package pe.limates.unmsm.model;

/**
 * Created by cristinacaballerohervias on 26/09/17.
 */

public class Preference {
    private int mStarColorId;
    private int mCategoryId;

    public Preference(int starColorId, int categoryId) {
        mStarColorId = starColorId;
        mCategoryId = categoryId;
    }

    public int getStarColorId() {
        return mStarColorId;
    }

    public int getCategoryId() {
        return mCategoryId;
    }
}
