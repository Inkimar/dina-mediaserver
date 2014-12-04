package se.nrm.mediaserver.resteasy;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.nrm.mediaserver.media3.domain.Determination;
import se.nrm.mediaserver.restful.util.JNDIFetchRemote;
import se.nrm.mediaserver.service.MediaService;

/**
 *
 * @author ingimar
 */
@Path("/link")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class DeterminationPostForm {

    final MediaService bean = JNDIFetchRemote.outreach();

    @POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("text/plain")
    public Response createNewCoupling(Determination d) {
        writeToDatabase(d);
        String responseOutput = Response.Status.OK.toString();
        return Response.status(200).entity(responseOutput).build();
    }

    private void writeToDatabase(Determination d) {
        bean.save(d);
    }
}
