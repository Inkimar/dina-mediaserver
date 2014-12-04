package se.nrm.mediaserver.resteasy;

import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import se.nrm.mediaserver.media3.domain.AdminConfig;
import se.nrm.mediaserver.media3.domain.Lic;
import se.nrm.mediaserver.restful.util.JNDIFetchRemote;
import se.nrm.mediaserver.service.MediaService;

/**
 *
 * @author ingimar
 */
@Path("/media")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class Administration {

    final MediaService bean = JNDIFetchRemote.outreach();

    @GET
    @Path("/admin/licenses")
    public List<Lic> getLicenses() {
        List<Lic> mediaList = bean.getLicenses();
        return mediaList;
    }

    @GET
    @Path("/admin/config/{environment}")
    public List<AdminConfig> getAdminConfig(@PathParam("environment") String environment) {
        List<AdminConfig> list = bean.getAdminConfig(environment);
        return list;
    }
}
