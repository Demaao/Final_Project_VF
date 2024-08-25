package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String Engtitle;
    private String Hebtitle;
    private String director;
    private int RlsYear ;
    private String genre;
    private String description;
    private String mainActors;
    private String length;
    //private String screeningTime;
    @Lob
    @Column(columnDefinition="BLOB")  // הגדרה של עמודת BLOB במסד הנתונים
    private byte[] imageData;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "movie_branch",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private List<Branch> branches;

    // Constructors
    public Movie() {
    }

    public Movie(int Id,String engtitle, String hebtitle, String director, int rlsYear, byte[] imageData,
                 String genre, String description, String mainActors, String length,List<Branch> branches) {
        this.id=Id;
        this.Engtitle = engtitle;
        this.Hebtitle = hebtitle;
        this.director = director;
        this.RlsYear = rlsYear;
        // this.screeningTime = screeningTime;
        this.imageData = imageData;
        this.genre = genre;
        this.description = description;
        this.mainActors = mainActors;
        this.length = length;
        this.branches = branches;
    }

    public Movie(int id, String engTitle, String hebTitle, String director, int rlsYear, byte[] imageData, String genre, String description, String mainActors, String length) {
        this.id=id;
        this.Engtitle = engTitle;
        this.Hebtitle = hebTitle;
        this.director = director;
        this.RlsYear = rlsYear;
        // this.screeningTime = screeningTime;
        this.imageData = imageData;
        this.genre = genre;
        this.description = description;
        this.mainActors = mainActors;
        this.length = length;
        this.branches = new ArrayList<Branch>(); ////////////////////////
    }


    //public Movie(String engTitle, String hebTitle, byte[] imageData) {
    // this.Engtitle = engTitle;
    //this.Hebtitle = hebTitle;
    // this.imageData = imageData;
    //}
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
        return RlsYear;
    }

    public void setYear(int year) {
        this.RlsYear = year;
    }


    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainActors() {
        return mainActors;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public List<Branch> getBranches() {
        return branches;
    }
    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}