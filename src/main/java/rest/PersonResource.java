package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.PersonDTO;
import entities.Person;
import facades.PersonFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.List;

//Todo Remove or change relevant parts before ACTUAL use
@Path("xxx")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Path("all")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<PersonDTO> getAllPersons() {
        List<PersonDTO> p = FACADE.getAllPersons();
        return p;
    }

    @Path("/{id}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public PersonDTO getPerson(@PathParam("id") Integer id) {
        PersonDTO p = FACADE.getPerson(id);
        return p;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePerson(@PathParam("id") int id) {
        Person p = FACADE.deletePerson(id);
        return Response.ok().entity(GSON.toJson(p)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(String jsonContext) {
        Person p = GSON.fromJson(jsonContext, Person.class);
        Person addP = new Person(p.getFirstName(), p.getLastName(), p.getPhone());
        return Response
                .ok("SUCCESS")
                .cookie(new NewCookie("test", p.getFirstName()))
                .entity(GSON.toJson(FACADE.addPerson(addP.getFirstName(), addP.getLastName(), addP.getPhone())))
                .build();
    }

    @PUT
    @Path("/edit")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPerson(String jsonContext) {
        PersonDTO personToBeChanged = GSON.fromJson(jsonContext, PersonDTO.class);
        PersonDTO updatedCustomer = FACADE.editPerson(personToBeChanged);
        return Response.ok().entity(GSON.toJson(updatedCustomer)).build();
    }
}