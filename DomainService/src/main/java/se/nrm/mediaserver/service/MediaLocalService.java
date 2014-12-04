package se.nrm.mediaserver.service;

import java.util.List;
import javax.ejb.Local;
import se.nrm.mediaserver.media3.domain.AdminConfig;

/**
 *
 * @author ingimar
 * @param <T>
 */
@Local
public interface MediaLocalService <T extends Object> {
    void saveLocal(T media);
    
    String getLocalServerDate();
    
    List<AdminConfig> getAdminConfig(String env);
    
}
