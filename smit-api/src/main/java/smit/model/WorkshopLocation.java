package smit.model;

public enum WorkshopLocation {
    LONDON("London"),
    MANCHESTER("Manchester");

    private final String name;

    WorkshopLocation(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static boolean contains(String workshop) {
        for (WorkshopLocation location : values()) {
            if (location.getName().equalsIgnoreCase(workshop)) {
                return true;
            }
        }
        return false;
    }
}
