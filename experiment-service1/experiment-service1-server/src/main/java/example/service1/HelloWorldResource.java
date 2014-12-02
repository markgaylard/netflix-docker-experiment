package example.service1;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Singleton;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Path("/hello")
public class HelloWorldResource
{
    private static final Logger logger = LoggerFactory.getLogger(HelloWorldResource.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    @Path("to/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloTo(@PathParam("name") String name)
    {
        try
        {
            Map<String, String> response = Collections.singletonMap("Message", "Hello " + name + " from service 1");
            return Response.ok(mapper.writeValueAsString(response)).build();
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path("to/person")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response helloToPerson(String name)
    {
        try
        {
            Map<String, String> response = Collections.singletonMap("Message", "Hello " + name + " from service 1");
            return Response.ok(mapper.writeValueAsString(response)).build();
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello()
    {
        try
        {
            Map<String, String> response = Collections.singletonMap("Message", "Hello from service 1");
            return Response.ok(mapper.writeValueAsString(response)).build();
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Path("object/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response topLevelObject(@PathParam("id") String id)
    {
        try
        {
            return Response.ok(mapper.writeValueAsString(new TopLevelDomainObject(id, 123))).build();
        }
        catch (Exception e)
        {
            logger.error("Error creating json response.", e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}