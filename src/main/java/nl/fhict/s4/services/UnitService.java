package nl.fhict.s4.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.panache.common.Parameters;
import nl.fhict.s4.models.Team;
import nl.fhict.s4.models.Unit;

@ApplicationScoped
public class UnitService {
    
    public Response deleteUnit(Long id) {

        Unit unit = Unit.findById(id);
        if(unit == null) {
            return Response.status(404).build();
        }
        unit.delete();
        return Response.noContent().build();
    }

    public Response addUnit(String name, Long teamId) {

        if(Unit.count("name = :name", Parameters.with("name", name)) > 0) {
            //return a conflict response if there is a unit with the same name
            return Response.status(Status.CONFLICT).build();
        }
        else if(Team.count("id = :id", Parameters.with("id", teamId)) == 0) {
            //return a bad request response if the given team does not exist
            return Response.status(Status.BAD_REQUEST).build();
        }

        Unit unit = Unit.addUnit(name, teamId);

        return Response.ok(unit).build();
    }

    public Response updateUnit(String name, Long teamId,Long unitId){
        Unit unit = Unit.findById(unitId);
        Team team = Team.findById(teamId);

        if(unit == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        if(team == null){
            return Response.status(Status.BAD_REQUEST).build();
        }
        
        unit.name = name;
        unit.team = team;
        return Response.ok(unit).build();
        
    }

    public Response getUnits(Long teamId) {
        if(teamId == null) {
            return Response.ok(Unit.listAll()).build();
        }
        else {
            Team team = Team.findById(teamId);
            return Response.ok(Unit.list("team = :team", Parameters.with("team", team))).build();
        }
    }

    public Response getUnitById(Long id) {
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        Response response = Response.noContent().build();
        Unit unit = Unit.findById(id);
        if(unit != null) {
            response = Response.ok(unit).build();
        }
        return response;
    }
}