package se.nrm.mediaserver.restful.util;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import se.nrm.mediaserver.service.MediaService;

/**
 *
 * @author ingimar
 */
public class JNDIFetchRemote {
    
    private final static Logger logger = Logger.getLogger(JNDIFetchRemote.class);

    public static MediaService outreach() {
        MediaService bean = null;
        // property-fil
        final String ip = "localhost"; 
        
        try {
            Properties jndiProps = new Properties();
            jndiProps.put("java.naming.factory.initial", "com.sun.enterprise.naming.impl.SerialInitContextFactory");
            jndiProps.put("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
            jndiProps.put("java.naming.factory.state", "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
            jndiProps.setProperty("org.omg.CORBA.ORBInitialHost", ip);
            Context ctx = new InitialContext(jndiProps);
            // String lookupEAR = "java:global/MediaserverApp-ear/MediaserverApp-ejb-1.0-SNAPSHOT/MediaServiceBean!se.nrm.mediaserver.service.MediaService";
            String lookupEJB = "java:global/MediaserverApp-ejb/MediaServiceBean!se.nrm.mediaserver.service.MediaService";
            bean = (MediaService) ctx.lookup(lookupEJB);
        } catch (NamingException ex) {
            logger.info(ex);
            System.exit(1);
        }
        
        return bean;
    }
}
