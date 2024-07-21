package smit.model;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Setter
@XmlRootElement(name = "tireChangeTimesResponse")
public class TireChangeTimesLondonResponse {

    private List<AvailableTimeLondon> availableTimes = new ArrayList<>();

    @XmlElement(name = "availableTime")
    public List<AvailableTimeLondon> getAvailableTimes() {
        return availableTimes;
    }

}
