package smit.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Workshop {
    private String id;
    private String name;
    private String address;
    private String types;

    public Workshop() {}

    public Workshop(
            String id,
            String name,
            String address,
            String types)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.types = types;
    }

    @Override
    public String toString() {
        return "Workshop{" +
                "id=" + this.id +
                ", name=" + this.name +
                ", address=" + this.address +
                ", types='" + this.types +
                "}";
    }
}
