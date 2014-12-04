package se.nrm.mediaserver.startup;

import se.nrm.mediaserver.util.AdminProperties;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.log4j.Logger;
import se.nrm.mediaserver.media3.domain.AdminConfig;
import se.nrm.mediaserver.service.MediaLocalService;

/**
 * http://blog.eisele.net/2010/12/seven-ways-to-get-things-started-java.html
 *
 * @author ingimar
 */
@Singleton
@Startup
public class StartupBean {

    private final static Logger logger = Logger.getLogger(StartupBean.class);

    private static final String ENVIRONMENT = "development";

    @EJB
    MediaLocalService bean;

    @PostConstruct
    private void startup() {
        List<AdminConfig> config = bean.getAdminConfig(ENVIRONMENT);
        try {
            writeToPropertyFile(config);
        } catch (IOException ex) {
            logger.info(ex);
        }
    }

    private void writeToPropertyFile(List<AdminConfig> configs) throws IOException {
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream(AdminProperties.PROPERTY_FILE) ) {
            
            for (AdminConfig conf : configs) {
                prop.setProperty(conf.getAdminKey(), conf.getAdminValue());
            }
            prop.store(output, null);
        } 
    }

    @PreDestroy
    private void shutdown() {
    }

}
