package smit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TireChangeServiceFactory {

    @Autowired
    private LondonTireChangeService londonTireChangeService;

    @Autowired
    private ManchesterTireChangeService manchesterTireChangeService;

    public TireChangeService getService(String workshop) {
        return switch (workshop) {
            case "London" -> londonTireChangeService;
            case "Manchester" -> manchesterTireChangeService;
            default -> throw new IllegalArgumentException("Unknown workshop: " + workshop);
        };
    }
}

