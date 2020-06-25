package nl.fhict.s4.models;


import javax.persistence.Column;
import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Team extends PanacheEntity {

    @Column(name = "name", nullable = false, unique = true)
    public String name;

}