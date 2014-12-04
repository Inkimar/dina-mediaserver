package se.nrm.mediaserver.restful;

import java.io.IOException;
import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.UUID;
import javax.ws.rs.DefaultValue;
import org.apache.log4j.Logger;
import org.apache.tika.Tika;
import se.nrm.mediaserver.media3.domain.Media;
import se.nrm.mediaserver.restful.util.FileSystemWriter;
import se.nrm.mediaserver.restful.util.JNDIFetchRemote;
import se.nrm.mediaserver.restful.util.PathHelper;
import se.nrm.mediaserver.restful.util.Writeable;
import se.nrm.mediaserver.service.MediaService;
import se.nrm.mediaserver.util.AdminProperties;
import se.nrm.mediaserver.util.MediaFactory;

@Path("/file")
public class UploadFileService {
    
    private final static Logger logger = Logger.getLogger(UploadFileService.class);
    
    private final int STATUS_INTERNAL_SERVER_ERROR = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
    
    MediaService bean = JNDIFetchRemote.outreach();

    /**
     * curl -H 'Content-Type: multipart/form-data' -F "owner=åsa" -F
     * "access=private" -F \ "file=@pica-pica-flying.jpg"
     * localhost:8080/MediaServerRestful/resources/file/upload
     *
     * @param fileData
     * @param fileDetail
     * @param owner
     * @param access
     * @param exportMedia
     * @return
     * @throws java.io.IOException
     */
    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(
            @FormDataParam("file") InputStream fileData,
            @DefaultValue("N/A") @FormDataParam("file") FormDataContentDisposition fileDetail,
            @DefaultValue("N/A") @FormDataParam("owner") String owner,
            @DefaultValue("N/A") @FormDataParam("access") String access,
            @DefaultValue("false") @FormDataParam("export") String exportMedia) throws IOException {
        
        if (null == fileData) {
            String msg = "attribute 'fileData' is null or empty \n";
            logger.info(msg);
            return Response.status(STATUS_INTERNAL_SERVER_ERROR).entity(msg).build();
        }
        String mimeType = "unknown", hashChecksum = "unknown";
        final String fileUUID = generateRandomUUID();
//        String uploadedFileLocation = absolutePathToFile(fileUUID);
//        writeToFile(fileData, uploadedFileLocation);
        
        String uploadedFileLocation = getAbsolutePathToFile(fileUUID);
        if (uploadedFileLocation.equals("")) {
            String msg = "could not create a directory for the mediafile \n";
            logger.info(msg);
            return Response.status(STATUS_INTERNAL_SERVER_ERROR).entity(msg).build();
        }
        writeToFile(fileData, uploadedFileLocation);
        
        Tika tika = new Tika();
        InputStream is = new BufferedInputStream(new FileInputStream(uploadedFileLocation));
        mimeType = tika.detect(is);
        
        Media media = null;
        switch (mimeType) {
            case "image/jpeg":
            case "image/gif": {

//                boolean exportImage = exportMedia;
                boolean exportImage = Boolean.parseBoolean(exportMedia);
                media = MediaFactory.createImage(exportImage);
                break;
            }
//            case "video/quicktime":
//            case "video/mp4": {
//                String startTime = form.getStartTime(), endTime = form.getEndTime();
//                media = MediaFactory.createVideo(checkStartEndTime(startTime), checkStartEndTime(endTime));
//                break;
//            }
//            case "audio/mpeg":
//            case "audio/ogg": {
//                String startTime = form.getStartTime(), endTime = form.getEndTime();
//                media = MediaFactory.createSound(checkStartEndTime(startTime), checkStartEndTime(endTime));
//                break;
//            }
            case "application/pdf": {
                media = MediaFactory.createAttachement();
                break;
            }
        }
        
        media.setUuid(fileUUID);
        media.setFilename(fileDetail.getFileName());
        media.setMimetype(mimeType);
        media.setOwner(owner);
        media.setVisibility(access);
        
        writeToDatabase(media);
        
        String responseOutput = "File uploaded to : " + uploadedFileLocation;
        
        return Response.status(200).entity(responseOutput).build();
        
    }

     private String getAbsolutePathToFile(String uuid) {
        String basePath = AdminProperties.getImagesFilePath();
        return PathHelper.getEmptyOrAbsolutePathToFile(uuid, basePath);
    }
    private void writeToFile(InputStream inStream, String location) {
        Writeable writer = new FileSystemWriter();
        writer.writeStreamToFS(inStream, location);
    }
    
    private String generateRandomUUID() {
        final String uuIdFilename = UUID.randomUUID().toString();
        return uuIdFilename;
    }
    
    private void writeToDatabase(Media media) {
        bean.save(media);
    }
}
