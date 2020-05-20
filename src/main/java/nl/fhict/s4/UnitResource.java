package nl.fhict.s4;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nl.fhict.s4.models.Unit;

@Path("units")
@RequestScoped
public class UnitResource {
    @DELETE
    @Path("{id}")
    public Response deleteUnit(@PathParam("id") int id) {
        //TODO: HANDLE NULL?
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        Unit.findById(id).delete();
        return Response.noContent().build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Transactional
    public Response addUnit(@FormParam("name") String name, @FormParam("teamId") Long teamId) {
        Unit unit = Unit.addUnit(name, teamId);

        return Response.ok(unit).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUnits() {
        return Response.ok(Unit.listAll()).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response getUnitById(@PathParam("id") Long id) {
        //TODO: HANDLE NULL?
        //TODO: STATUS AND RETURN VALUE OF DELETE?
        Unit unit = Unit.findById(id);
        return Response.ok(unit).build();
    }

}