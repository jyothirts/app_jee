package org.java.jyothir.app.rest.host;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginRestService {

    /** Single @PathParam => ex : http://localhost:8080/myApp/login/44347 */
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getById(@PathParam("id") String id){
        String output = "getById : id " + id;
        return Response.status(Response.Status.OK).entity(output).build();
    }

    /** Multiple @PathParam => ex : http://localhost:8080/myApp/login/a/b/c */
    @GET
    @Path("/{firstName}/{middleName}/{lastName}") // initial / is optional
    @Produces(MediaType.TEXT_PLAIN)
    public Response getByNameCriteria(
            @PathParam("firstName") String firstName,
            @PathParam("middleName") String middleName,
            @PathParam("lastName") String lastName){
        String output = "getByNameCriteria : " + firstName + " : " + middleName + " : " + lastName;
        return Response.status(Response.Status.OK).entity(output).build();
    }

    /**
     * Matrix params are a set of "name=value" in URI path
     * http://localhost:8080/myApp/login/45667/jyothir;pKey=abc123
     * * http://localhost:8080/myApp/login/45667/jyothir;pKey=abc123;lastName=s
     */
    @GET
    @Path("/{id}/{firstName}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getByNameAndIdAndKey(
            @PathParam("id") String id,
            @PathParam("firstName") String firstName,
            @PathParam("lastName") String lastName,
            @PathParam("pKey") String pKey
            ){
        String output = "getByNameAndIdAndKey : "+  id + ":" + firstName + ":" + lastName + ":" + pKey;
        return Response.status(Response.Status.OK).entity(output).build();
    }

    /** @QueryParam http://localhost:8080/myApp/login/query1?firstName=j&lastName=s */
    @GET
    @Path("/query1")
    public Response queryByName(
            @QueryParam("firstName") String firstName,
            @DefaultValue("") @QueryParam("lastName") String lastName
            ){
                String result = "queryByName { firstName : " + firstName
                        + " , lastName " + lastName + "}";
        return Response.status(Response.Status.OK).entity(result).build();
    }




}
