package se.nrm.mediaserver.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import se.nrm.mediaserver.media3.domain.AdminConfig;
import se.nrm.mediaserver.media3.domain.Determination;
import se.nrm.mediaserver.media3.domain.Image;
import se.nrm.mediaserver.media3.domain.Lic;
import se.nrm.mediaserver.media3.domain.Media;
import se.nrm.mediaserver.media3.domain.MediaText;
import se.nrm.mediaserver.media3.domain.Tag;
import se.nrm.mediaserver.service.MediaService;
import se.nrm.mediaserver.util.TagHelper;

/**
 * http://netbeans.dzone.com/articles/how-to-combine-rest-and-ejb-31
 *
 * @author ingimar
 */
@Stateless
public class MediaServiceBean<T> implements MediaService<T>, Serializable {

    @PersistenceContext(unitName = "MysqlPU")
    private EntityManager em;

    /**
     * @Profiled, to measure time.
     *
     * @param media
     */
    @Override
//    @Profiled
    public void save(T media) {
        T merge = em.merge(media);
    }

    @Override
    public void delete(MediaText iText) {
        MediaText text = em.find(MediaText.class, iText.getUuid());
        em.remove(text);
    }

    /**
     * obs: Removes the media from 'DETERMINATION'-table as well. ?
     *
     * @param mediaUUID
     * @return
     */
    @Override
    public boolean deleteMediaMetadata(final String mediaUUID) {
        boolean deleted = false;
        T fetchedEntity;
        try {
            fetchedEntity = get(mediaUUID);
        } catch (Exception e) {
            return deleted;
        }

        try {
            em.remove(fetchedEntity);
        } catch (Exception e) {
            return deleted;
        }
        deleted = true;

        return deleted;
    }

    @Override
    public boolean isMediaUUIDCoupled(final String mediaUUID) {
        boolean isCoupled = false;
        Query q = em.createNativeQuery("select TAG_VALUE from DETERMINATION where MEDIA_UUID='" + mediaUUID + "'");
        try {
            String taxonUUID = (String) q.getSingleResult();
            isCoupled = true;
        } catch (Exception ex) {
        }

        return isCoupled;
    }

    @Override
    public T get(String uuid) {
        Query namedQuery = em.createNamedQuery(Media.FIND_BY_UUID);
        namedQuery.setParameter("uuid", uuid);

        T media = null;
        try {
            media = (T) namedQuery.getSingleResult();
        } catch (Exception ex) {

        }
        return media;
    }

    /**
     *
     * @param abbrevation
     * @return null if no license abbrevation is found in db.
     */
    @Override
    public T getLicenseByAbbr(String abbrevation) {
        Query namedQuery = em.createNamedQuery(Lic.FIND_BY_ABBREV);
        namedQuery.setParameter("abbrev", abbrevation);
        T licence = null;
        try {
            licence = (T) namedQuery.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
        return licence;
    }

    /**
     * Should introduce 'paging', in case they are too many
     *
     * @return
     */
    @Override
    public List<Image> getAll() {
        Query query = em.createNamedQuery(Media.FIND_ALL);
        List<Image> images = query.getResultList();
        return images;
    }

    @Override
    public List getXImages(String in, int limit) {

        Query query = em.createNamedQuery(in);
        List<Media> images = query.setMaxResults(limit).getResultList();
        return images;
    }

    @Override
    public List<Tag> getTags(String key, String value) {
        Query query = em.createNamedQuery(Tag.FIND_KEY_VALUE);
        query.setParameter("tagKey", key);
        query.setParameter("tagValue", value);
        List<Tag> tags = query.getResultList();
        return tags;
    }

  
//    @Override
//    public T getDetermination(String eUUID) {
//        Query query = em.createNamedQuery(Determination.FIND_BY_EXTERNAL_TAG);
//        query.setParameter("tagValue", eUUID);
//        List<Determination> list = query.getResultList();
//        T determination = (T) list.get(0);
//
//        return determination;
//    }

    @Override
    public List<T> getDeterminationsByTagValue(String externalUUID) {
        Query query = em.createNamedQuery(Determination.FIND_BY_EXTERNAL_TAG);
        query.setParameter("tagValue", externalUUID);
        List<T> list = query.getResultList();

        return list;
    }

    @Override
    public List<String> getMediaUUID(String externalUUID) {
        final String tags = "";
        return this.getMediaUUID(externalUUID, tags);
    }

    /**
     * Using Native SQL-Query
     *
     * @param externalUUID
     * @param tags
     * @return
     */
    @Override
    public List<String> getMediaUUID(String externalUUID, String tags) {
        String result_media_uuid;
        if (!tags.isEmpty()) {
            TagHelper h = new TagHelper();
            String parsedTags = h.sqlUsageParseWithRegexp(tags);
            result_media_uuid = "SELECT MEDIA_UUID FROM DETERMINATION d,"
                    + "MEDIA m WHERE d.TAG_VALUE = '" + externalUUID + "' "
                    + "AND d.MEDIA_UUID=m.UUID AND " + parsedTags;
        } else {
            result_media_uuid = "SELECT MEDIA_UUID FROM DETERMINATION d,"
                    + "MEDIA m WHERE d.TAG_VALUE = '" + externalUUID + "' "
                    + "AND d.MEDIA_UUID=m.UUID";
        }

        Query query = em.createNativeQuery(result_media_uuid);

        List<String> listOfUUID = query.getResultList();

        return listOfUUID;
    }

    @Override
    public List<Media> getMetaDataForMedia(String externalUUID) {
        final String tags = "";
        return this.getMetaDataForMedia(externalUUID, tags);
    }

    @Override
    public List<Media> getMetaDataForMedia(String externalUUID, String tags) {

        String sql;
        if (!tags.isEmpty()) {
             TagHelper h = new TagHelper();
            String parsedTags = h.sqlUsageParseWithLike(tags);
            sql = "SELECT DISTINCT d.media FROM Determination d, Media m where d.media.uuid = m.uuid "
                    + "AND d.tagValue= '" + externalUUID + "' AND " + parsedTags;
        } else {
            sql = "SELECT DISTINCT d.media FROM Determination d, Media m where d.media.uuid = m.uuid "
                    + "AND d.tagValue= '" + externalUUID + "'";
        }

        Query query = em.createQuery(sql);
        List<Media> mediaList = query.getResultList();
        return mediaList;
    }

    @Override
    public List<Image> getMetadataByLanguage(String externalUUID, String language) {
        String sql;
        sql = "SELECT DISTINCT d.media FROM Determination d, Media m, MEDIA_TEXT t"
                + " WHERE d.media.uuid = m.uuid AND m.uuid=t.media.uuid"
                + " AND d.tagValue= '" + externalUUID + "'"
                + " AND t.lang= '" + language + "' ORDER BY d.sortOrder";

        Query query = em.createQuery(sql);
        List<Image> images = query.getResultList();
        return images;
    }

    @Override
    public List<Image> getMetadataByLanguageAndTags(String externalUUID, String language, String tags) {
        String sql;
        TagHelper tagHelper = new TagHelper();
        String parsedTags = tagHelper.sqlUsageParseWithLike(tags);
        sql = "SELECT DISTINCT d.media FROM Determination d, Media m, MEDIA_TEXT t"
                + " WHERE d.media.uuid = m.uuid AND m.uuid=t.media.uuid"
                + " AND d.tagValue= '" + externalUUID + "'"
                + " AND t.lang= '" + language + "' AND " + parsedTags;

        Query query = em.createQuery(sql);
        List<Image> images = query.getResultList();
        return images;
    }

    @Override
    public List<Image> getMetadataByTags_MEDIA(String tags) {
         TagHelper tagHelper = new TagHelper();
        String parsedTags = tagHelper.sqlUsageParseWithLike(tags);
        String sql = "SELECT m from Media m where " + parsedTags;
        Query query = em.createQuery(sql);
        List<Image> images = query.getResultList();
        return images;
    }

    /**
     *
     * @param tagValue for Naturforskaren.se it is the taxonUUID
     * @return
     */
    @Override
    public Boolean isTagPresentInDetermination(String tagValue) {
        boolean existence = false;

        String sql = "SELECT count(d.tagValue) FROM Determination d WHERE d.tagValue = '" + tagValue + "'";
        Query query = em.createQuery(sql);
        Long singleResult = (Long) query.getSingleResult();

        if (singleResult > 0) {
            existence = true;
        }

        return existence;
    }

    @Override
    public List<AdminConfig> getAdminConfig(String env) {
        Query query = em.createNamedQuery(AdminConfig.FIND_BY_ENV);
        query.setParameter("environment", env);
        List<AdminConfig> configs = query.getResultList();
        return configs;
    }

    @Override
    public List<Lic> getLicenses() {
        Query query = em.createNamedQuery(Lic.FIND_ALL);
        List<Lic> licenses = query.getResultList();
        return licenses;
    }

    @Override
    public String getServerDate() {
        Date date = new Date();
        return "EJB-bean says Hello. Servertime is " + date.toString();
    }
}
