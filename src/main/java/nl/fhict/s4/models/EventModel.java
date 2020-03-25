package nl.fhict.s4.models;

public class EventModel {
    private double lat;
    private double lon;
    private int type;
    private String description;

    public EventModel() {
        //default constructor
    }

    public  EventModel(double lat, double lon, int type, String description) {
         this.lat = lat;
         this.lon = lon; 
         this.type = type;
         this.description = description;
    }

    /**
     * @return the lat
     */
    public double getLat() {
        return lat;
    }

    /**
     * @return the lon
     */
    public double getLon() {
        return lon;
    }

    /**
     * @return the type
     */
    public int getType() {
        return type;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(double lat) {
        this.lat = lat;
    }

    /**
     * @param lon the lon to set
     */
    public void setLon(double lon) {
        this.lon = lon;
    }

    /**
     * @param type the type to set
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}