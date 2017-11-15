package org.java.jyothir.app.rest.host;

import com.sun.org.apache.regexp.internal.RE;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.*;
import java.util.List;

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

    /** @QueryParam
     * http://localhost:8080/myApp/login/query2?firstName=j&lastName=s&sortBy=firstname&sortBy=id */
    @GET
    @Path("/query2")
    public Response queryByName2(@Context UriInfo uriInfo
    ){
        String firstName = uriInfo.getQueryParameters().getFirst("firstName");
        String lastName = uriInfo.getQueryParameters().getFirst("lastName");
        List<String> sortBy = uriInfo.getQueryParameters().get("sortBy");
        String result = "queryByName2 { firstName : " + firstName
                + " , lastName " + lastName + "} sortBy => " + sortBy.toString();

        return Response.status(Response.Status.OK).entity(result).build();
    }

    /**
     * Get an html page for registering new user
     * http://localhost:8080/myApp/login/queryHtml
     */
    @GET
    @Path("/queryHtml")
    @Produces(MediaType.TEXT_HTML)
    public Response getQueryAsHtml(){
        StringBuilder html = new StringBuilder();
        html.append("<html><body>")
                .append("<form action=\"user/register\" method=\"post\">")
                .append("<p>FirstName : <input type=\"text\" name=\"firstName\"></p>")
                .append("<p>LastName : <input type=\"text\" name=\"lastName\"></p>")
                .append("<input type=\"submit\" value=\"Register\">")
                .append("</form></body></html>");
        return Response.status(Response.Status.OK).entity(html.toString()).build();
    }

    /**
     * @FormParam example => data will be posted to
     * http://localhost:8080/myApp/login/user/register
     */
    @POST // insert
    @Path("/user/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response register(@FormParam("firstName") String firstName,
                             @FormParam("lastName") String lastName
                             ){
        String result = "registered new user : " + firstName + " " + lastName;
        return Response.status(Response.Status.OK).entity(result).build();
    }


/** file upload
 * http://localhost:8080/myApp/login/uploadHtml
 */
    @GET
    @Path("/uploadHtml")
    @Produces(MediaType.TEXT_HTML)
    public Response uploadFileHtml(){
        StringBuilder html = new StringBuilder();
        html.append("<html><body>")
                .append("<form action=\"user/file/upload\" method=\"post\" enctype=\"multipart/form-data\">")
                .append("<p>Add file to upload : <input type=\"file\" name=\"file\" size=\"50\" ></p>")
                .append("<input type=\"submit\" value=\"Upload\">")
                .append("</form></body></html>");
        return Response.status(Response.Status.OK).entity(html.toString()).build();
    }


    /**
     * http://localhost:8080/myApp/login/user/file/upload
     */
    @POST
    @Path("/user/file/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file")FormDataContentDisposition fileMetaData) {
        String destination = "/usr/local/uploaded" + fileMetaData.getFileName();
        saveFile(uploadedInputStream, destination);
        String output = "File uploaded : " + destination;
        return Response.status(Response.Status.OK).entity(output).build();
    }

    private void saveFile(InputStream uploadedInputStream ,
                          String saveDestination) {
        try(OutputStream out = new FileOutputStream(new File(saveDestination));){
            int read = 0;
            byte[] bytes = new byte[1024];
            while((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes,0,read);
            }
        }catch (FileNotFoundException e1){
            e1.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }
    }

    /** http://localhost:8080/myApp/login/getEmployee */
    @GET
    @Path("/getEmployee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployee(){
        return Response.status(Response.Status.OK).entity("" +
                "{ \"name\":\"John\",\"age\":31, \"city\":\"New York\"}").build();
    }







}
