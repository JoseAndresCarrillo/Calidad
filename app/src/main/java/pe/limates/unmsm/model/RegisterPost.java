package pe.limates.unmsm.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RegisterPost implements Serializable{
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("dni")
    @Expose
    private String dni;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("major")
    @Expose
    private Integer major;

    public RegisterPost(User user, String dni, String code, Integer major) {
        this.user = user;
        this.dni = dni;
        this.code = code;
        this.major = major;
    }
    public RegisterPost(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getMajor() {
        return major;
    }

    public void setMajor(Integer major) {
        this.major = major;
    }
}
