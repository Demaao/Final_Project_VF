package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class NewMessage implements Serializable {
    Object object;
    String message;
    String userName;//////////////////////
    String password;////////////////////////////
    Employee employee; //////////////////////////
    Movie movie;
    private int screeningId; // שדה חדש למזהה ההקרנה

    public NewMessage(Object object, String message) {
        this.object = object;
        this.message = message;
    }

    public NewMessage(String message) {
        this.message = message;
    }

    public NewMessage(String message, String username, String password) {////////////////////////////////
        this.message = message;;
        this.userName = username;
        this.password = password;
    }
    public NewMessage(String message, Employee employee) { //////////////////////////
        this.message = message;
        this.employee = employee;
    }

    // Constructor to handle String and Movie
    public NewMessage(String message, Movie movie) {
        this.message = message;
        this.object = movie;
    }

    public NewMessage(String message, int screeningId) {
        this.message = message;
        this.screeningId = screeningId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    } //////////////////////////////////////////////////////

    public Movie getMovie(){
        return movie;
    }

    public Object getObject() {
        return object;
    }
    public String getMessage() {
        return message;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setMovie(Movie movie) { this.movie = movie; }

    public int getScreeningId() {
        return screeningId; // גטר למזהה ההקרנה
    }

    public void setScreeningId(int screeningId) {
        this.screeningId = screeningId; // סטר למזהה ההקרנה
    }

}