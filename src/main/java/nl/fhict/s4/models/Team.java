package nl.fhict.s4.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Team extends PanacheEntity {

    @Column(name = "name", nullable = false, unique = true)
    public String name;

}