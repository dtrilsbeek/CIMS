package nl.fhict.s4.models;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class EventType extends PanacheEntity {
    public String name;
    public String description;
    public String icon;

    public EventType() {
        
    }
}