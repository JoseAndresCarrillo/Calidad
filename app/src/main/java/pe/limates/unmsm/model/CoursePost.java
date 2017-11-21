package pe.limates.unmsm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CoursePost {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("shedule")
    @Expose
    private ArrayList<Schedule> schedule = null;
    @SerializedName("student")
    @Expose
    private String student;


    public CoursePost(String name, ArrayList<Schedule> schedule, String student) {
        this.name = name;
        this.schedule = schedule;
        this.student = student;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Schedule> schedule) {
        this.schedule = schedule;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }
}