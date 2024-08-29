package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "soon_movies")
public class SoonMovie extends Movie implements Serializable {
    // Constructors
    public SoonMovie() {
    }

    public SoonMovie(int id, String engTitle, String hebTitle, String director, int year, byte[] imageData, String genre, String description, String mainActors, String length) {
        super(id, engTitle, hebTitle, director, year, imageData, genre, description, mainActors, length);
    }
}