package il.cshaifasweng.OCSFMediatorExample.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime screeningTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "branch_id", nullable = true) // branch יכול להיות null במקרה של סרטי בית
    private Branch branch;

    @ManyToOne(fetch = FetchType.LAZY)
   //@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id", nullable = true) // hall יכול להיות null אם ההקרנה אינה מתקיימת באולם מסוים
    private Hall hall;


    public Screening() {}

    public Screening(LocalDateTime screeningTime, Movie movie, Branch branch) {
        this.screeningTime = screeningTime;
        this.movie = movie;
       this.branch = branch;
    }

    public Screening(LocalDateTime screeningTime, Movie movie, Branch branch, Hall hall) {
        this.screeningTime = screeningTime;
        this.movie = movie;
        this.branch = branch;
        this.hall = hall;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
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

    public String myToString() {
        if(this.movie instanceof HomeMovie)
            return this.screeningTime.toLocalDate().toString() + "      " + this.screeningTime.toLocalTime();
        else
            return this.screeningTime.toLocalDate().toString() + "      " + this.screeningTime.toLocalTime() + "        " + this.branch.getName();
    }
}