package smit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.xml.bind.annotation.XmlElement;

@Getter
public class AvailableTime {

    @XmlElement(name = "time")
    @JsonProperty("time")
    private String time;

    @JsonProperty("available")
    private boolean available;

    @JsonProperty("type")
    private String type;

    @JsonProperty("address")
    private String address;

    public AvailableTime() {}

    public AvailableTime setTypeAndAddress(String type, String address) {
        this.type = type;
        this.address = address;
        return this;
    }

    public AvailableTime setAvailable(boolean available) {
        this.available = available;
        return this;
    }

}
