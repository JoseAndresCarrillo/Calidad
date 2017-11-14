package pe.limates.unmsm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("startHour")
    @Expose
    private Hour startHour;
    @SerializedName("endHour")
    @Expose
    private Hour endHour;

    public Schedule(String day, Hour startHour, Hour endHour) {
        super();
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Hour getStartHour() {
        return startHour;
    }

    public void setStartHour(Hour startHour) {
        this.startHour = startHour;
    }

    public Hour getEndHour() {
        return endHour;
    }

    public void setEndHour(Hour endHour) {
        this.endHour = endHour;
    }

}