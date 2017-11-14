package pe.limates.unmsm.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reminder implements Serializable {
    @SerializedName("description")
    @Expose
    private String mDescription;
    private String _id;
    private int mBackgroundId;

    public Reminder(String description, int backgroundId) {
        mDescription = description;
        mBackgroundId = backgroundId;
    }


    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getBackgroundId() {
        return mBackgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        mBackgroundId = backgroundId;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
