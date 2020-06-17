package nl.fhict.s4.services;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import io.quarkus.panache.common.Parameters;
import nl.fhict.s4.models.EventType;

@ApplicationScoped
public class EventTypeService {

    public Response deleteEventType(Long id) {
        EventType type = EventType.findById(id);

        if(type == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }
    

    public Response addEventType(String name,  String description,  String icon) {

        if(EventType.count("name = :name", Parameters.with("name", name)) > 0) {
            //return a conflict response if the EventType already exists
            return Response.status(Status.CONFLICT).build();
        }

        EventType eventType = new EventType();
        eventType.name = name;
        eventType.description = description;
        eventType.icon = icon;
        eventType.persist();

        return Response.ok(eventType).build();
    }


    public Response getEventTypes() {
        return Response.ok(EventType.listAll()).build();
    }


    public Response getEventTypeById(Long id) {
        EventType eventType = EventType.findById(id);

        if(eventType == null) {
            return Response.status(Status.NOT_FOUND).build();
        }

        return Response.ok(eventType).build();
    }
}