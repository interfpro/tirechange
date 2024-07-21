package smit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequest {

    private String id;
    private String workshop;
    private String contactInformation;

    @Override
    public String toString() {
        return "BookRequest{" +
                "id=" + this.id +
                ", workshop=" + this.workshop +
                ", contactInformation='" + this.contactInformation +
                "}";
    }
}
