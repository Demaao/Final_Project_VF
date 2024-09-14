package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Hall;
import il.cshaifasweng.OCSFMediatorExample.entities.Screening;

import java.util.List;

public class UpdateScreeningEvent {
    private List<Screening> screenings;

    public UpdateScreeningEvent(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

}