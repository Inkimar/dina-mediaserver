package se.nrm.mediaserver.service;

import java.util.List;
import javax.ejb.Remote;
import se.nrm.mediaserver.media3.domain.AdminConfig;
import se.nrm.mediaserver.media3.domain.Image;
import se.nrm.mediaserver.media3.domain.Lic;
import se.nrm.mediaserver.media3.domain.Media;
import se.nrm.mediaserver.media3.domain.MediaText;
import se.nrm.mediaserver.media3.domain.Tag;

/**
 * Concerns only the database
 *
 * @author ingimar
 * @param <T>
 */
@Remote
public interface MediaService<T extends Object> {

    void save(T media);

    T get(String uuid);

    List getAll();

    List<Tag> getTags(String key, String value);

//    T getDetermination(String uuid);
    List<String> getMediaUUID(String externalUUID);

    List<String> getMediaUUID(String externalUUID, String tags);

    List<Media> getMetaDataForMedia(String externalUUID);

    List<Media> getMetaDataForMedia(String externalUUID, String tags);

    List<Image> getMetadataByLanguage(String externalUUID, String language);

    List<Image> getMetadataByLanguageAndTags(String externalUUID, String language, String tags);

    Boolean isTagPresentInDetermination(String tagValue);

    List<AdminConfig> getAdminConfig(String env);

    public T getLicenseByAbbr(String uuid);

    boolean isMediaUUIDCoupled(String mediaUUID);

    public boolean deleteMediaMetadata(final String mediaUUID);

    public void delete(MediaText iText);

    public List<T> getDeterminationsByTagValue(String externalUUID);

    public List<Image> getMetadataByTags_MEDIA(String tags);

    public List<Lic> getLicenses();

    public List getXImages(String in, int limit);

    String getServerDate();
}
