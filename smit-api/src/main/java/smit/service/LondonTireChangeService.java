package smit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import smit.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.StringWriter;

@Service
public class LondonTireChangeService implements TireChangeService {

    private static final String LONDON_URL = "http://localhost:9003/api/v1/tire-change-times/";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CsvService csvService;

    @Override
    public List<AvailableTime> getTireChangeTimes(String from, String until) {
        LocalDate untilOriginal = LocalDate.parse(until, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        Workshop londonWorkshop = getWorkshopData();
        String londonUrl = LONDON_URL + "available?from=" + from + "&until=" + untilOriginal.plusDays(1);
        return getLondonTimes(londonUrl, londonWorkshop);
    }

    @Override
    public void bookTime(BookRequest bookRequest) {
        String url = LONDON_URL + bookRequest.getId() + "/booking";
        TireChangeBookingRequest tireChangeBookingRequest = new TireChangeBookingRequest(bookRequest.getContactInformation());

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(TireChangeBookingRequest.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(tireChangeBookingRequest, stringWriter);
            String xmlBody = stringWriter.toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_XML);

            HttpEntity<String> request = new HttpEntity<>(xmlBody, headers);

            restTemplate.put(url, request);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public List<AvailableTime> getLondonTimes(String url, Workshop workshop) {
        try {
            ResponseEntity<String> responseString = restTemplate.getForEntity(url, String.class);
            JAXBContext context = JAXBContext.newInstance(TireChangeTimesLondonResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            TireChangeTimesLondonResponse tireChangeTimesResponse = (TireChangeTimesLondonResponse) unmarshaller.unmarshal(new StringReader(Objects.requireNonNull(responseString.getBody())));
            return tireChangeTimesResponse.getAvailableTimes().stream()
                    .map(time -> time.setTypeAndAddress(workshop.getTypes(), workshop.getAddress()))
                    .toList();
        } catch (JAXBException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    private Workshop getWorkshopData() {
        List<Workshop> workshops = csvService.getWorkshops();
        return workshops.stream()
                .filter(item -> item.getName().equals(WorkshopLocation.LONDON.getName()))
                .findFirst().orElse(null);
    }
}

