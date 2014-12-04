package se.nrm.mediaserver.resteasy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;
import se.nrm.mediaserver.media3.domain.Media;
import se.nrm.mediaserver.restful.util.JNDIFetchRemote;
import se.nrm.mediaserver.service.MediaService;

/**
 *
 * @author ingimar
 */
@Path("/media")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public class MediaResourceFetchMetaData {

    private final static Logger logger = Logger.getLogger(MediaResourceFetchMetaData.class);

    final MediaService bean = JNDIFetchRemote.outreach();

    @GET
    @Path("/metadata/{uuid}")
    public Media getMetadata(@PathParam("uuid") String mediaUUID) {
        Media media = (Media) bean.get(mediaUUID);
        return media;
    }
    
    @GET
    @Path("/search/{tags}")
    public List<Media> getMediaMetadataByLangAndTags(@PathParam("tags") String tags) {
        List<Media> medLiaList = bean.getMetadataByTags_MEDIA(tags);
        return medLiaList;
    }

    @GET
    @Path("/all/{mediaType}")
    public List<Media> getMediaType(@PathParam("mediaType") String mediaType) {
        int limit = 10;
        return this.getMediaTypeWithLimit(mediaType, limit);
    }

    @GET
    @Path("/all/{mediaType}/limit/{limit}")
    public List<Media> getMediaTypeWithLimit(@PathParam("mediaType") String mediaType, @PathParam("limit") int limit) {
        List<Media> mediaList = new ArrayList<>();
        if (limit < 0 || limit > 100) {
            limit = 10;
        }
        String jpql = mediaType.concat(".findAll");
        try {
            mediaList = bean.getXImages(jpql, limit);
        } catch (Exception ex) {
            logger.info(ex);
            return Collections.EMPTY_LIST;
        }
        return mediaList;
    }

//    @GET
//    @Path("/all")
//    public List<Media> getAllMediaMetaData() {
//        int limit = 10;
//        List<Media> images = bean.getAll();
//        return images;
//    }
}
