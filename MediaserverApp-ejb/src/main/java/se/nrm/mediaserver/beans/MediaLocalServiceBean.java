/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.mediaserver.beans;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import se.nrm.mediaserver.media3.domain.AdminConfig;
import se.nrm.mediaserver.service.MediaLocalService;

/**
 *
 * @author ingimar
 */
@Stateless
public class MediaLocalServiceBean implements MediaLocalService {

    @PersistenceContext(unitName = "MysqlPU")
    private EntityManager em;

    @Override
    public void saveLocal(Object media) {
        em.merge(media);
    }

    @Override
    public String getLocalServerDate() {
        System.out.println("Metoden getLocalServerDate");
        Date date = new Date();
        return "EJB-LOCAL says Hello. Servertime is " + date.toString();
    }
    
    @Override
    public List<AdminConfig> getAdminConfig(String env) {
        Query query = em.createNamedQuery(AdminConfig.FIND_BY_ENV);
        query.setParameter("environment", env);
        List<AdminConfig> configs = query.getResultList();
        return configs;
    }
}
