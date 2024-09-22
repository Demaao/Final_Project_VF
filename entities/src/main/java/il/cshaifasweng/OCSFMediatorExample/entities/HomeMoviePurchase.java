package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HomeMoviePurchase extends Purchase implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_movie_id")
    private HomeMovie homeMovie;

    //private String availabilityStartTime;
    private LocalDateTime availabilityStartTime;
   // private String availabilityEndTime;
    private LocalDateTime availabilityEndTime;

/*
    @OneToOne(mappedBy = "homeMoviePurchase", cascade = CascadeType.ALL) // Correct reference to the owning field
    private Screening screening;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screening_id") // hall ×××× ×××××ª null ×× ×××§×¨× × ××× × ××ª×§××××ª ××××× ××¡×××
    private Screening screening;

    public HomeMoviePurchase() {}

    public HomeMoviePurchase(String productType, LocalDateTime  purchaseDate, String paymentMethod, double pricePaid,
                             Customer customer, String purchaseDescription, HomeMovie homeMovie, LocalDateTime availabilityStartTime, LocalDateTime availabilityEndTime
    , Screening screening) {
        super(productType, purchaseDate, paymentMethod, pricePaid, customer, purchaseDescription);
        this.homeMovie = homeMovie;
        this.availabilityStartTime = availabilityStartTime;
        this.availabilityEndTime = availabilityEndTime;
        this.screening = screening;
    }

    public HomeMovie getHomeMovie() {
        return homeMovie;
    }

    public Screening getScreening() {
        return screening;
    }

    public void setScreening(Screening screening) {
        this.screening = screening;
    }

    public int getId() {
        return id;
    }

    public void setHomeMovie(HomeMovie homeMovie) {
        this.homeMovie = homeMovie;
    }

    public LocalDateTime getAvailabilityStartTime() {
        return availabilityStartTime;
    }

    public void setAvailabilityStartTime(LocalDateTime availabilityStartTime) {
        this.availabilityStartTime = availabilityStartTime;
    }

    public LocalDateTime getAvailabilityEndTime() {
        return availabilityEndTime;
    }

    public void setAvailabilityEndTime(LocalDateTime availabilityEndTime) {
        this.availabilityEndTime = availabilityEndTime;
    }
}