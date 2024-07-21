package smit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import smit.model.AvailableTime;
import smit.model.BookRequest;
import smit.model.WorkshopLocation;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static smit.util.Helper.isTimeLaterThanUntilParameter;

@Service
public class TireChangeGeneralService {

    @Autowired
    private TireChangeServiceFactory tireChangeServiceFactory;

    public List<AvailableTime> getTireChangeTimes(String from, String until, String workshop, String type) {
        if (WorkshopLocation.contains(workshop)) {
            return getTimesForWorkshop(from, until, WorkshopLocation.valueOf(workshop.toUpperCase()));
        } else if ("truck".equals(type)) {
            return getTimesForWorkshop(from, until, WorkshopLocation.MANCHESTER);
        } else {
            return getCombinedTimes(from, until);
        }
    }

    public List<AvailableTime> getTimesForWorkshop(String from, String until, WorkshopLocation workshopLocation) {
        TireChangeService tireChangeService = tireChangeServiceFactory.getService(workshopLocation.getName());
        return tireChangeService.getTireChangeTimes(from, until);
    }

    public List<AvailableTime> getCombinedTimes(String from, String until) {
        List<AvailableTime> availableTimes = new ArrayList<>(getTimesForWorkshop(from, until, WorkshopLocation.LONDON));
        availableTimes.addAll(getTimesForWorkshop(from, until, WorkshopLocation.MANCHESTER));
        return availableTimes.stream()
                .filter(date -> isTimeLaterThanUntilParameter(date, until))
                .sorted(Comparator.comparing(date -> ZonedDateTime.parse(date.getTime(), DateTimeFormatter.ISO_DATE_TIME)))
                .collect(Collectors.toList());
    }

    public void bookTime(BookRequest bookRequest) {
        TireChangeService tireChangeService = tireChangeServiceFactory.getService(bookRequest.getWorkshop());
        tireChangeService.bookTime(bookRequest);
    }

}
