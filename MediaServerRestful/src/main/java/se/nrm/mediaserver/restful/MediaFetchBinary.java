/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.mediaserver.restful;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import se.nrm.mediaserver.restful.util.PathHelper;

/**
 *
 * @author ingimar
 */
@Path("/restful")
@Produces({"image/jpeg", "image/png"})
public class MediaFetchBinary {

    private final static Logger logger = Logger.getLogger(MediaFetchBinary.class);

    @GET
    @Path("/stream/{uuid}")
    public Response getMedia(@PathParam("uuid") String uuid) {
        String dynPath = getDynamicPath(uuid);
        String filePath = dynPath.concat(uuid);

        File file = new File(filePath);
        Response response = returnFile(file);
        return response;
    }

    private static Response returnFile(File file) {
        if (!file.exists()) {
            logger.info("File does not exist");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        try {
            String mimeType = getMimeType(file);
            FileInputStream fileInputStream = new FileInputStream(file);
            return Response.ok(fileInputStream, mimeType).build();
        } catch (IOException ioEx) {
            logger.info(ioEx);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    private String getDynamicPath(String uuid) {
        return PathHelper.getDynamicPathToFile(uuid, "");
    }

    private static String getMimeType(File file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        return mimeType;
    }
}
