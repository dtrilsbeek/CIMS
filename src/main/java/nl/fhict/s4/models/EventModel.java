package nl.fhict.s4.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.ws.rs.FormParam;

@Entity
public class EventModel extends PanacheEntity {
    @FormParam("lat")
    public double lat;

    @FormParam("lon")
    public double lon;

    @FormParam("type")
    public int type;

    public Status status;

    @FormParam("description")
    public String description;

    public EventModel() {
        status = Status.ACTIVE;
    }

    public  EventModel(double lat, double lon, int type, String description) {
         this.lat = lat;
         this.lon = lon; 
         this.type = type;
         this.description = description;
         this.status = Status.ACTIVE;
    }

    public void finish() {
        this.status = Status.FINISHED;
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