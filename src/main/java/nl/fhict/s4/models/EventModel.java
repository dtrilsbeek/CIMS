package nl.fhict.s4.models;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


import javax.persistence.*;


@Entity
public class EventModel extends PanacheEntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	public double lat;
	public double lon;

	@JoinColumn(nullable = false)
    @ManyToOne
	public EventType type;

	public EventStatus status;
	public String description;
	public boolean isUpdate;

	public EventModel() {
		status = EventStatus.ACTIVE;
	}

	public EventModel(double lat, double lon, EventType type, String description) {
		this.lat = lat;
		this.lon = lon;
		this.type = type;
		this.description = description;
		this.status = EventStatus.ACTIVE;
	}

	public EventModel(EventModel model) {
		this.id = model.id;
		this.lat = model.lat;
		this.lon = model.lon;
		this.type = model.type;
		this.description = model.description;
		this.status = model.status;
		this.isUpdate = false;
	}
	
}