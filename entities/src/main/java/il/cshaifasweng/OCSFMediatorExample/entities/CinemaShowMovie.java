//package il.cshaifasweng.OCSFMediatorExample.entities;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.persistence.Entity;
//import javax.persistence.FetchType;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//@Entity
//public class CinemaShowMovie extends Movie {
//
//
//    double ticketCost;
//    private int ticketsSold;
//
//
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movie")
//    private List<Screening> screenings;
//
//    public CinemaShowMovie() {}
//
//    public CinemaShowMovie (int id,String title,String titleHeb, String mainActors, String description, double cost) {
//        this.id=id;
//        this.Engtitle = title;
//        this.Hebtitle = titleHeb;
//        this.mainActors = mainActors;
//        this.description = description;
//        this.ticketCost = cost;
//        this.screenings = new ArrayList<Screening>();
//        setTicketsSold(0);
//    }
//
//    public CinemaShowMovie (int id,String title,String titleHeb, String producer, String actors, String description, double cost/*, Image image*/) {
//        this.id = id;
//        this.Engtitle = title;
//        this.Hebtitle = titleHeb;
//        this.mainActors = actors;
//        this.description = description;
//        this.ticketCost = cost;
//        this.screenings = new ArrayList<Screening>();
//        setTicketsSold(0);
//    }
//
//
//
//    public double getTicketCost() {
//        return ticketCost;
//    }
//
//    public void setTicketCost(double ticketCost) {
//        this.ticketCost = ticketCost;
//    }
//
//    public List<Screening> getScreenings() {
//        return screenings;
//    }
//
//    public void setScreenings(List<Screening> screenings) {
//        this.screenings = screenings;
//    }
//
//    public void addScreening (Screening newScreening) {
//
//                this.screenings.add(newScreening);
//
//    }
//
//    public int getTicketsSold() {
//        return ticketsSold;
//    }
//
//    public void setTicketsSold(int ticketsSold) {
//        this.ticketsSold = ticketsSold;
//    }
//
//}
