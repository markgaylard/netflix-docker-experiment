package example.service1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.netflix.karyon.health.HealthCheckHandler;

@Path("/healthcheck")
public class HealthcheckResource
{

    private final HealthCheckHandler healthCheckHandler;

    @Inject
    public HealthcheckResource(HealthCheckHandler healthCheckHandler)
    {
        this.healthCheckHandler = healthCheckHandler;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response healthcheck()
    {
        return Response.status(healthCheckHandler.getStatus()).build();
    }
}
