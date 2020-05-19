package nl.fhict.s4.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Unit extends PanacheEntity{
    @Column(name = "name", nullable = false, unique = true)
    public String name;

    @JoinColumn(nullable = false)
    @ManyToOne
    public Team team;

    public static Unit addUnit(final String name, final Long teamId) {
        Unit unit = new Unit();
        unit.name = name;
        unit.team = Team.findById(teamId);
        unit.persist();

        return unit;
    }

}