package se.nrm.mediaserver.resteasy;

import java.io.FileInputStream;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import se.nrm.mediaserver.restful.util.PathHelper;
import se.nrm.mediaserver.util.AdminProperties;

/**
 *
 * @author ingimar
 */
@Path("/media")
@Produces({"image/jpeg", "image/png"})
public class MediaResourceFetchBinary {

    private final static Logger logger = Logger.getLogger(MediaResourceFetchBinary.class);

    @GET
    @Path("/stream/{uuid}")
    public Response getMedia(@PathParam("uuid") String uuid) {
        String filename = getDynamicPath(uuid, getBasePath());

        File file = new File(filename);
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

    @GET
    @Path("/stream/image/{uuid}/{height}")
    public byte[] getImageByDimension(@PathParam("uuid") String uuid, @PathParam("height") String height) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(2048);

        try {
            BufferedImage transformedImage = this.getTtrans(uuid, height);
            ImageIO.write(transformedImage, "jpeg", outputStream);
        } catch (IOException ex) {
            logger.info(ex);
        }

        return outputStream.toByteArray();
    }

    /**
     * better syntax :
     * http://localhost:8080/MediaServerResteasy/media/stream/image/?uuid=18ac3829-49bd-42ed-a975-0ba839167f33&width=500&height=400
     *
     * @param info
     * @param uuid
     * @return
     * @throws IOException
     */
    private BufferedImage getTtrans(String uuid, String inHeight) throws IOException {
        int height = 150;
        if (inHeight != null) {
            height = Integer.parseInt(inHeight);
            if (height < 100) {
                height = 150;
            }
        }

        String filename = getDynamicPath(uuid, getBasePath());

        File fileHandle = new File(filename);
        BufferedImage originalImage = ImageIO.read(fileHandle);
        BufferedImage image = Thumbnails.of(originalImage).height(height).asBufferedImage();

        return image;

    }

    private String getDynamicPath(String uuid, String path) {
        return PathHelper.getEmptyOrAbsolutePathToFile(uuid, path);
    }

    private static String getMimeType(File file) throws IOException {
        Tika tika = new Tika();
        String mimeType = tika.detect(file);
        return mimeType;
    }
    
    private String getBasePath(){
        String basePath = AdminProperties.getImagesFilePath();
        return basePath;
    }
}
