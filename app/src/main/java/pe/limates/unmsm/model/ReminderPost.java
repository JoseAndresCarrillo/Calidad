package pe.limates.unmsm.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReminderPost implements Serializable {
    @SerializedName("description")
    @Expose
    private String mDescription;
    private String student;

    public ReminderPost(String mDescription, String student) {
        this.mDescription = mDescription;
        this.setStudent(student);
    }


    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}
