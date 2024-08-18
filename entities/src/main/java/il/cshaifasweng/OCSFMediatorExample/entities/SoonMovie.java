package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "soon_movies")
public class SoonMovie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String Engtitle;

    private String releaseDate;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] imageData;

    // Constructors
    public SoonMovie() {
    }

    public SoonMovie(int id,String Engtitle, String releaseDate, byte[] imageData) {
        this.id=id;
        this.Engtitle = Engtitle;
        this.releaseDate = releaseDate;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }
}
