package smit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TireChangeBookingResponse {
    private boolean available;
    private int id;
    private String time;
}
