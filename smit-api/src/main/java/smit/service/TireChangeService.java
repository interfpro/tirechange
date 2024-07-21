package smit.service;

import smit.model.AvailableTime;
import smit.model.BookRequest;

import java.util.List;

public interface TireChangeService {

    List<AvailableTime> getTireChangeTimes(String from, String until);

    void bookTime(BookRequest bookRequest);
}
