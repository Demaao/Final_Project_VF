package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
public class HomeMovie extends Movie implements Serializable {
    private String link;

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
}

