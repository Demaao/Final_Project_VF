package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class NewMessage implements Serializable {
    Object object;
    String message;
    String userName;
    String password;
    Employee employee;
    Movie movie;
    String branchName;
    List<Branch> branches;
    List<LocalDateTime> dateTimes;
    Customer customer;
    int id;

    public NewMessage(Object object, String message) {
        this.object = object;
        this.message = message;
    }

    public NewMessage(Object object, String message, String branchName) {////////////////////
        this.object = object;
        this.message = message;
        this.branchName = branchName;
    }

    public NewMessage(Object object, String message, List<Branch> branches, List<LocalDateTime> dateTimes) {////////////////////
        this.object = object;
        this.message = message;
        this.branches = branches;
        this.dateTimes = dateTimes;
    }

    public NewMessage(String message) {
        this.message = message;
    }

    public NewMessage(String message, String username, String password) {
        this.message = message;;
        this.userName = username;
        this.password = password;
    }
    public NewMessage(String message, Employee employee) {
        this.message = message;
        this.employee = employee;
    }
    public NewMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public String getBranchName(){ return branchName; }

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

    public List<Branch> getBranches() {return branches;}
    public List<LocalDateTime> getDateTimes() {return dateTimes;}

    public Customer getCustomer() {
        return customer;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}