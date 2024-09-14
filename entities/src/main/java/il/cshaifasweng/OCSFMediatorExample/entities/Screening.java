package il.cshaifasweng.OCSFMediatorExample.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "screenings")
public class Screening implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime screeningTime;
    private String screeningBranch;
    private String screeningHall;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = true) // branch יכול להיות null במקרה של סרטי בית
    private Branch branch;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "screening")
    private List<MovieTicket> tickets;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "screening_hall")
    private Hall hall;


    private boolean[] seatsArray;
    private int availableSeats;
    private int takenSeats;
    public int getTakenSeats() {
        return takenSeats;
    }

    public void setSoldSeats(int takenSeats) {
        this.takenSeats = takenSeats;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Screening() {}

    public Screening(LocalDateTime screeningTime, Movie movie, Branch branch, Hall hall) {
        this.screeningTime = screeningTime;
        this.movie = movie;
        this.branch = branch;
     //   branch.getScreenings().add(this);
       this.screeningBranch = branch.getLocation();
//        movie.getScreenings().add(this);
        this.tickets = new ArrayList<MovieTicket>();
        this.setHall(hall);
        hall.addScreening(this);
    }
    public void setHall(Hall hall) {
        this.hall = hall;
  this.setScreeningHall(hall.getHallName());//TODO
     availableSeats = hall.getSeatsNum();//TODO
        this.seatsArray = new boolean[availableSeats];
        for (int i = 0; i <availableSeats ; i++ ) {
            seatsArray[i]=false;
        }

    }
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(LocalDateTime screeningTime) {
        this.screeningTime = screeningTime;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public void setTakenSeatAt(int i) {
        if (i<hall.getSeatsNum()) {
            seatsArray[i]=true;
        }
    }
    public boolean getSeatStatus(int i) {
        return seatsArray[i];
    }

    public void setAvailableSeatAt(int i) {
        if (i<hall.getSeatsNum()) {
            seatsArray[i]=false;
        }
    }

    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }

    public Hall getHall() {
        return hall;
    }
}