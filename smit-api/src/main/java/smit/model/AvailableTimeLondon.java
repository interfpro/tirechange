package smit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlElement;

public class AvailableTimeLondon extends AvailableTime {

    @XmlElement(name = "uuid")
    @JsonProperty("id")
    private String id;

    @JsonProperty("workshop")
    private final String workshop = "London";

}
