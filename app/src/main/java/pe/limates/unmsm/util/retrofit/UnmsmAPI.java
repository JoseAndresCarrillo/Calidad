package pe.limates.unmsm.util.retrofit;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import pe.limates.unmsm.model.Course;
import pe.limates.unmsm.model.CourseGrade;
import pe.limates.unmsm.model.CoursePost;
import pe.limates.unmsm.model.Event;
import pe.limates.unmsm.model.LoginPost;
import pe.limates.unmsm.model.LoginResult;
import pe.limates.unmsm.model.Major;
import pe.limates.unmsm.model.Place;
import pe.limates.unmsm.model.RegisterPost;
import pe.limates.unmsm.model.Reminder;
import pe.limates.unmsm.model.ReminderPost;
import pe.limates.unmsm.model.Schedule;
import pe.limates.unmsm.model.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UnmsmAPI {

    @Headers({"Content-Type: application/json"})
    @POST("login")
    Call<LoginResult> login(@Body LoginPost loginPost);

    @Headers({"Content-Type: application/json"})
    @POST("register")
    Call<ResponseBody> register(@Body RegisterPost registerPost);

    @Headers({"Content-Type: application/json"})
    @GET("majors")
    Call<ArrayList<Major>> getMajors();

    @Headers({"Content-Type: application/json"})
    @GET("events")
    Call<ArrayList<Event>> getEvents(@Header("Authorization") String credentials);

    @Headers({"Content-Type: application/json"})
    @GET("places")
    Call<ArrayList<Place>> getPlaces(@Header("Authorization") String credentials);

    @GET("placeby")
    Call<Place> getPlaceInfo(@Header("Authorization") String credentials, @Query("id") String id);

    @Headers({"Content-Type: application/json"})
    @GET("courses")
    Call<ArrayList<Course>> getCourses(@Header("Authorization") String credentials);

    @Headers({"Content-Type: application/json"})
    @GET("reminders")
    Call<ArrayList<Reminder>> getReminders(@Header("Authorization") String credentials);

    @Headers({"Content-Type: application/json"})
    @DELETE("deleteCourse/{id}")
    Call<ResponseBody> deleteCourse(@Header("Authorization") String credentials, @Path("id") String id);

    @Headers({"Content-Type: application/json"})
    @DELETE("deleteReminder/{id}")
    Call<ResponseBody> deleteReminder(@Header("Authorization") String credentials, @Path("id") String id);

    @Headers({"Content-Type: application/json"})
    @POST("registerCourse")
    Call<ResponseBody> registerCourse(@Header("Authorization") String credentials, @Body CoursePost course);

    @Headers({"Content-Type: application/json"})
    @POST("registerReminder")
    Call<ResponseBody> registerReminder(@Header("Authorization") String credentials, @Body ReminderPost reminderPost);

    @Headers({"Content-Type: application/json"})
    @GET("coursesGrades")
    Call<ArrayList<CourseGrade>> getCoursesGrades(@Header("Authorization") String credentials);

//    @POST("orders/{id}/take")
//    Call<ResponseBody> takeOrder(@Header("Authorization") String credentials, @Path("id") String orderId);
//
//    @Headers({ "Content-Type: application/json"})
//    @GET("orders/")
//    Call<ArrayList<Order>> getOrders(@Header("Authorization") String credentials);
//
//    @Headers({ "Content-Type: application/json"})
//    @POST("orders/{orderid}/arrive/{pointid}")
//    Call<ResponseBody> arrivePoint(@Header("Authorization") String credentials, @Path("orderid") String orderID,
//                                   @Path("pointid") String pointID);
//
//    @Headers({ "Content-Type: application/json"})
//    @PUT("change-password/")
//    Call<ResponseBody> updatePassword(@Header("Authorization") String credentials, @Body PasswordForm passwordForm);


}
