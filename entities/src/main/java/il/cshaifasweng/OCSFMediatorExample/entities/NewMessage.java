package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;

public class NewMessage implements Serializable {
    Object object;
    String message;
    String userName;//////////////////////
    String password;////////////////////////////
    Employee employee; //////////////////////////
    Movie movie;

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
}