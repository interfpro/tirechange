import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import smit.model.AvailableTime;
import smit.model.WorkshopLocation;
import smit.service.TireChangeGeneralService;
import smit.service.TireChangeService;
import smit.service.TireChangeServiceFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TireChangeGeneralServiceTest {

    @Mock
    private TireChangeServiceFactory tireChangeServiceFactory;

    @Mock
    private TireChangeService londonService;

    @Mock
    private TireChangeService manchesterService;

    @InjectMocks
    private TireChangeGeneralService tireChangeClientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTireChangeTimesLondon() {
        List<AvailableTime> expectedTimes = new ArrayList<>();
        when(tireChangeServiceFactory.getService("London")).thenReturn(londonService);
        when(londonService.getTireChangeTimes(anyString(), anyString())).thenReturn(expectedTimes);

        List<AvailableTime> actualTimes = tireChangeClientService.getTireChangeTimes("2023-01-01", "2023-01-10", "London", "car");

        assertEquals(expectedTimes, actualTimes);
    }

    @Test
    public void testGetTireChangeTimesManchesterTruck() {
        List<AvailableTime> expectedTimes = new ArrayList<>();
        when(tireChangeServiceFactory.getService("Manchester")).thenReturn(manchesterService);
        when(manchesterService.getTireChangeTimes(anyString(), anyString())).thenReturn(expectedTimes);

        List<AvailableTime> actualTimes = tireChangeClientService.getTireChangeTimes("2023-01-01", "2023-01-10", "unknown", "truck");

        assertEquals(expectedTimes, actualTimes);
    }

    @Test
    public void testGetTireChangeTimesCombined() {
        List<AvailableTime> londonTimes = new ArrayList<>();
        List<AvailableTime> manchesterTimes = new ArrayList<>();
        when(tireChangeServiceFactory.getService("London")).thenReturn(londonService);
        when(tireChangeServiceFactory.getService("Manchester")).thenReturn(manchesterService);
        when(londonService.getTireChangeTimes(anyString(), anyString())).thenReturn(londonTimes);
        when(manchesterService.getTireChangeTimes(anyString(), anyString())).thenReturn(manchesterTimes);

        List<AvailableTime> actualTimes = tireChangeClientService.getTireChangeTimes("2023-01-01", "2023-01-10", "unknown", "car");

        assertEquals(londonTimes.size() + manchesterTimes.size(), actualTimes.size());
    }

    @Test
    public void testGetTimesForWorkshop() {
        List<AvailableTime> expectedTimes = new ArrayList<>();
        when(tireChangeServiceFactory.getService("London")).thenReturn(londonService);
        when(londonService.getTireChangeTimes(anyString(), anyString())).thenReturn(expectedTimes);

        List<AvailableTime> actualTimes = tireChangeClientService.getTimesForWorkshop("2023-01-01", "2023-01-10", WorkshopLocation.LONDON);

        assertEquals(expectedTimes, actualTimes);
    }

    @Test
    public void testGetCombinedTimes() {
        List<AvailableTime> londonTimes = new ArrayList<>();
        List<AvailableTime> manchesterTimes = new ArrayList<>();
        when(tireChangeServiceFactory.getService("London")).thenReturn(londonService);
        when(tireChangeServiceFactory.getService("Manchester")).thenReturn(manchesterService);
        when(londonService.getTireChangeTimes(anyString(), anyString())).thenReturn(londonTimes);
        when(manchesterService.getTireChangeTimes(anyString(), anyString())).thenReturn(manchesterTimes);

        List<AvailableTime> actualTimes = tireChangeClientService.getCombinedTimes("2023-01-01", "2023-01-10");

        assertEquals(londonTimes.size() + manchesterTimes.size(), actualTimes.size());
    }
}

