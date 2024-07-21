package smit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import smit.model.*;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ManchesterTireChangeService implements TireChangeService {

    private static final String MANCHESTER_URL = "http://localhost:9004/api/v2/tire-change-times";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CsvService csvService;

    @Override
    public List<AvailableTime> getTireChangeTimes(String from, String until) {
        String manchesterUrl = MANCHESTER_URL + "?from=" + from;
        Workshop manchesterWorkshop = getWorkshopData();
        return getManchesterTimes(manchesterUrl, manchesterWorkshop).stream()
                .filter(date -> isTimeLaterThanUntilParameter(date, until))
                .collect(Collectors.toList());
    }

    @Override
    public void bookTime(BookRequest bookRequest) {
        String url = MANCHESTER_URL + "/" + bookRequest.getId() + "/booking";

        TireChangeBookingRequest tireChangeBookingRequest = new TireChangeBookingRequest(bookRequest.getContactInformation());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TireChangeBookingRequest> request = new HttpEntity<>(tireChangeBookingRequest, headers);

        restTemplate.postForEntity(url, request, TireChangeBookingResponse.class);
    }

    public List<AvailableTime> getManchesterTimes(String url, Workshop workshop) {
        ResponseEntity<List<AvailableTimeManchester>> responseStringManchester = restTemplate.exchange(url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        return Objects.requireNonNull(responseStringManchester.getBody()).stream()
                .map(time -> time.setTypeAndAddress(workshop.getTypes(), workshop.getAddress()))
                .filter(AvailableTime::isAvailable)
                .toList();
    }

    private boolean isTimeLaterThanUntilParameter(AvailableTime availableTime, String until) {
        LocalDate targetDateTime = LocalDate.parse(until, DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
        ZonedDateTime date = ZonedDateTime.parse(availableTime.getTime(), DateTimeFormatter.ISO_DATE_TIME);
        return !date.toLocalDate().isAfter(targetDateTime);
    }

    private Workshop getWorkshopData() {
        List<Workshop> workshops = csvService.getWorkshops();
        return workshops.stream()
                .filter(item -> item.getName().equals(WorkshopLocation.MANCHESTER.getName()))
                .findFirst().orElse(null);
    }
}
