package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Engtitle;
    private String Hebtitle;
    private String director;
    private int newYear ;
    private String screeningTime;

    @Lob  // מציין שזהו שדה גדול (Large Object)
    @Column(columnDefinition="BLOB")  // הגדרה של עמודת BLOB במסד הנתונים
    private byte[] imageData;

    // Constructors
    public Movie() {
    }

    public Movie(int id, String Engtitle, String Hebtitle, String director, int year, String screeningTime, byte[] imageData) {
        this.id = id;
        this.Engtitle = Engtitle;
        this.Hebtitle = Hebtitle;
        this.director = director;
        this.newYear = year;
        this.screeningTime = screeningTime;
        this.imageData = imageData;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEngtitle() {
        return Engtitle;
    }

    public void setEngtitle(String Engtitle) {
        this.Engtitle = Engtitle;
    }

    public String getHebtitle() {
        return Hebtitle;
    }

    public void setHebtitle(String Hebtitle) {
        this.Hebtitle = Hebtitle;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getYear() {
        return newYear;
    }

    public void setYear(int year) {
        this.newYear = year;
    }

    public String getScreeningTime() {
        return screeningTime;
    }

    public void setScreeningTime(String screeningTime) {
        this.screeningTime = screeningTime;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
