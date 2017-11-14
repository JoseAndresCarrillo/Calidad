package pe.limates.unmsm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Luis on 11/11/2017.
 */

public class Hour implements Serializable {
    @SerializedName("h")
    @Expose
    private String h;
    @SerializedName("m")
    @Expose
    private String m;

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

}
