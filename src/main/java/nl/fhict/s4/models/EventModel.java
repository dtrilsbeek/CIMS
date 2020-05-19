package nl.fhict.s4.models;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jdk.jfr.Event;

import javax.persistence.*;


@Entity
public class EventModel extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	public double lat;
	public double lon;
	public int type;
	public Status status;
	public String description;
	public Action action;

	public EventModel() {
		status = Status.ACTIVE;
	}

	public EventModel(double lat, double lon, int type, String description) {
		this.lat = lat;
		this.lon = lon;
		this.type = type;
		this.description = description;
		this.status = Status.ACTIVE;
	}

	public EventModel(EventModel model) {
		this.id = model.id;
		this.lat = model.lat;
		this.lon = model.lon;
		this.type = model.type;
		this.description = model.description;
		this.status = model.status;
//		this.updateId = model.updateId;
		this.action = model.action;
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