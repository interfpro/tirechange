package smit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableTimeManchester extends AvailableTime{

    @JsonProperty("id")
    private String id;

    @JsonProperty("workshop")
    private final String workshop = "Manchester";

}
