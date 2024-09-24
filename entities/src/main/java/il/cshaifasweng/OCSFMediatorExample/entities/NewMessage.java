package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    List<Hall> halls;
    int id;
    private String branch;
    private LocalDateTime date;

    int ticketsSold;
    int ticketPrice;
    int totalSales;




    public NewMessage(Object object, String message) {
        this.object = object;
        this.message = message;
    }

    public NewMessage(Object object, String message, String branchName) {////////////////////
        this.object = object;
        this.message = message;
        this.branchName = branchName;
    }

    public NewMessage(Object object, String message, List<Branch> branches, List<LocalDateTime> dateTimes, List<Hall> halls) {////////////////////
        this.object = object;
        this.message = message;
        this.branches = branches;
        this.dateTimes = dateTimes;
        this.halls = halls;
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

    public NewMessage(Object object, String message, int id) {
        this.object = object;
        this.message = message;
        this.id = id;
    }
    public NewMessage(Object object, String message, String branch, LocalDateTime date) {
        this.object = object;
        this.message = message;
        this.branch = branch;
        this.date = date;
    }

    public NewMessage(Object object, String message, int ticketsSold, int ticketPrice, int totalSales) {
        this.object = object;
        this.message = message;
        this.ticketsSold = ticketsSold;
        this.ticketPrice= ticketPrice;
        this.totalSales = totalSales;
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
    public List<Hall> getHalls() {return halls;}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    // Getter and Setter for ticketPrice
    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    // Getter and Setter for totalSales
    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }


//    public LocalDate getSelectedDate() { return selectedDate; }
//    public void setSelectedDate(LocalDate date) { this.selectedDate = date; }
//
//
//    public LocalTime getSelectedTime() { return selectedTime; }
//    public void setSelectedTime(LocalTime time) { this.selectedTime = time; }
//
//    public int getSelectedMovieId() {
//        return selectedMovieId;
//    }
//
//    public void setSelectedMovieId(int selectedMovieId) {
//        this.selectedMovieId = selectedMovieId;
//    }
//    public String getSelectedBranchName() {
//        return selectedBranchName;
//
//    }
//    public void setSelectedBranchName(String selectedBranchName) {
//        this.selectedBranchName = selectedBranchName;
//    }
}