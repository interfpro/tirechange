import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import smit.model.*;
import smit.service.CsvService;
import smit.service.ManchesterTireChangeService;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ManchesterTireChangeServiceTest {

    @InjectMocks
    private ManchesterTireChangeService manchesterTireChangeService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private CsvService csvService;

    private Workshop manchesterWorkshop;

    @BeforeEach
    public void setUp() {
        manchesterWorkshop = new Workshop();
        manchesterWorkshop.setName("Manchester");
        manchesterWorkshop.setTypes("car, truck");
        manchesterWorkshop.setAddress("Manchester Address");
    }

    @Test
    public void testGetManchesterTimes() {
        String url = "http://localhost:9004/api/v2/tire-change-times?from=2024-08-08";

        Workshop manchester = new Workshop();
        manchester.setAddress("14 Bury New Rd, Manchester");
        manchester.setTypes("Sõiduauto, Veoauto");

        AvailableTimeManchester availableTimeManchester = new AvailableTimeManchester();
        availableTimeManchester.setTypeAndAddress("Sõiduauto, Veoauto", "14 Bury New Rd, Manchester");
        availableTimeManchester.setId("1");
        availableTimeManchester.setAvailable(true);
        AvailableTimeManchester availableTimeManchester2 = new AvailableTimeManchester();
        availableTimeManchester2.setTypeAndAddress("Sõiduauto, Veoauto", "14 Bury New Rd, Manchester");
        availableTimeManchester2.setId("2");
        availableTimeManchester2.setAvailable(false);
        List<AvailableTimeManchester> mockResponse = Arrays.asList(availableTimeManchester, availableTimeManchester2);
        ResponseEntity<List<AvailableTimeManchester>> responseEntity =
                new ResponseEntity<>(mockResponse, HttpStatus.OK);

        when(restTemplate.exchange(
                eq(url),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        )).thenReturn(responseEntity);



        List<AvailableTime> availableTimes = manchesterTireChangeService.getManchesterTimes(url, manchester);

        assertEquals(1, availableTimes.size());
        assertEquals(true, availableTimes.get(0).isAvailable());
        assertEquals("Sõiduauto, Veoauto", availableTimes.get(0).getType());
        assertEquals("14 Bury New Rd, Manchester", availableTimes.get(0).getAddress());

        verify(restTemplate, times(1)).exchange(
                eq(url),
                eq(HttpMethod.GET),
                isNull(),
                any(ParameterizedTypeReference.class)
        );

    }
}
