package smit.model;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Setter
@XmlRootElement(name = "london.tireChangeBookingRequest")
public class TireChangeBookingRequest {

    private String contactInformation;

    public TireChangeBookingRequest() {}

    @XmlElement
    public String getContactInformation() {
        return contactInformation;
    }

    public TireChangeBookingRequest(String contactInformation) {
        this.contactInformation = contactInformation;
    }
}
