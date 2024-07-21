package smit.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import smit.model.*;
import smit.service.CsvService;
import smit.service.TireChangeGeneralService;
import java.util.List;

@RestController
public class Controller {

    @Autowired
    TireChangeGeneralService tireChangeGeneralService;

    @Autowired
    CsvService csvService;

    @GetMapping(path="/times")
    public List<AvailableTime> getAvailableTimes(@RequestParam(name = "from") String from,
                                                 @RequestParam(name = "until") String until,
                                                 @RequestParam(name = "workshop", required = false) String workshop,
                                                 @RequestParam(name = "type", required = false) String type) throws Exception {
        return tireChangeGeneralService.getTireChangeTimes(from, until, workshop, type);
    }

    @PostMapping(path="/book")
    public void insertEntry(@RequestBody BookRequest bookRequest) {
        tireChangeGeneralService.bookTime(bookRequest);
    }

    @GetMapping(path="/workshops")
    public List<Workshop> getWorkshops() {
        return csvService.getWorkshops();
    }

    @PutMapping(path="/edit-workshop")
    public void editWorkshop(@RequestBody Workshop workshop) {
        csvService.editWorkshop(workshop);
    }

}
