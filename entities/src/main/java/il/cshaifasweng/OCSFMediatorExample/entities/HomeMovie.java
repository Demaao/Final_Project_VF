package il.cshaifasweng.OCSFMediatorExample.entities;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class HomeMovie extends Movie implements Serializable {
    private String link;

    @OneToMany(mappedBy = "homeMovie", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private List<HomeMoviePurchase> homeMoviePurchases = new ArrayList<>();

    public HomeMovie() {}

    public HomeMovie(int id, String engTitle, String hebTitle, String director, int year, byte[] imageData, String link, String genre, String description, String mainActors, String length) {
        super(id, engTitle, hebTitle, director, year, imageData, genre, description, mainActors, length);
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void addHomeMoviePurchase(HomeMoviePurchase homeMoviePurchase) {
        homeMoviePurchases.add(homeMoviePurchase);
        homeMoviePurchase.setHomeMovie(this); // Ensure the relationship is properly bidirectional
    }

    public void removeHomeMoviePurchase(HomeMoviePurchase homeMoviePurchase) {
        homeMoviePurchases.remove(homeMoviePurchase);
    }
}
