package smit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WorkshopRequest {

    private String id;
    private String address;
    private String types;

    @Override
    public String toString() {
        return "WorkshopRequest{" +
                "id=" + this.id +
                ", address=" + this.address +
                ", types='" + this.types +
                "}";
    }
}
