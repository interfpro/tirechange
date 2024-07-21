import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.*;
import smit.model.*;
import smit.service.CsvService;
import smit.service.LondonTireChangeService;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LondonTireChangeServiceTest {

    @InjectMocks
    private LondonTireChangeService londonTireChangeService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CsvService csvService;

    private Workshop londonWorkshop;

    @BeforeEach
    public void setUp() {
        londonWorkshop = new Workshop();
        londonWorkshop.setName("London");
        londonWorkshop.setTypes("car, truck");
        londonWorkshop.setAddress("London Address");
    }

    @Test
    public void testGetLondonTimes() throws JAXBException {
        String londonUrl = "http://localhost:9003/api/v1/tire-change-times/available?from=2024-08-08&until=2024-08-10";
        Workshop london = new Workshop();
        london.setAddress("1A Gunton Rd, London");
        london.setTypes("Sõiduauto");

        String mockXmlResponse = "<tireChangeTimesResponse><availableTime><uuid>3b85296a-2100-41ce-b64a-648576127af6</uuid><time>2024-07-11T12:00:00Z</time></availableTime><availableTime><uuid>0d000fee-f11d-4abb-9e42-c795fc9573b2</uuid><time>2024-07-11T13:00:00Z</time></availableTime></tireChangeTimesResponse>";
        ResponseEntity<String> responseEntity = new ResponseEntity<>(mockXmlResponse, HttpStatus.OK);

        when(restTemplate.getForEntity(londonUrl, String.class)).thenReturn(responseEntity);

        JAXBContext context = mock(JAXBContext.class);
        Unmarshaller unmarshaller = mock(Unmarshaller.class);
        lenient().when(context.createUnmarshaller()).thenReturn(unmarshaller);


        TireChangeTimesLondonResponse mockResponse = new TireChangeTimesLondonResponse();
        AvailableTimeLondon time1 = new AvailableTimeLondon();
        time1.setTypeAndAddress("Sõiduauto", "1A Gunton Rd, London");
        AvailableTimeLondon time2 = new AvailableTimeLondon();
        time2.setTypeAndAddress("Sõiduauto", "1A Gunton Rd, London");
        mockResponse.setAvailableTimes(Arrays.asList(time1, time1));
        lenient().when(unmarshaller.unmarshal(any(StringReader.class))).thenReturn(mockResponse);

        List<AvailableTime> availableTimes = londonTireChangeService.getLondonTimes(londonUrl, london);

        assertEquals(2, availableTimes.size());
        assertEquals("2024-07-11T12:00:00Z", availableTimes.get(0).getTime());
        assertEquals("Sõiduauto", availableTimes.get(0).getType());
        assertEquals("1A Gunton Rd, London", availableTimes.get(0).getAddress());

        verify(restTemplate, times(1)).getForEntity(londonUrl, String.class);
    }

    @Test
    public void testBookTime() throws JAXBException {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setWorkshop("London");
        bookRequest.setId("123");
        bookRequest.setContactInformation("contact info");

        String url = "http://localhost:9003/api/v1/tire-change-times/123/booking";
        TireChangeBookingRequest tireChangeBookingRequest = new TireChangeBookingRequest(bookRequest.getContactInformation());

        JAXBContext jaxbContext = JAXBContext.newInstance(TireChangeBookingRequest.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(tireChangeBookingRequest, stringWriter);
        String xmlBody = stringWriter.toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity<String> request = new HttpEntity<>(xmlBody, headers);

        doNothing().when(restTemplate).put(url, request);

        londonTireChangeService.bookTime(bookRequest);

        verify(restTemplate, times(1)).put(url, request);
    }
}

