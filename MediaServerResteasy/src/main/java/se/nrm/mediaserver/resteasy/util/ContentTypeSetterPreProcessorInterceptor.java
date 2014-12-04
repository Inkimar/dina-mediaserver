package se.nrm.mediaserver.resteasy.util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

/**
 * https://searchcode.com/codesearch/view/18815657/
 *
 * - not used , 2014-10-23
 * @author ingimar
 */
@Provider
@ServerInterceptor
public class ContentTypeSetterPreProcessorInterceptor implements PreProcessInterceptor {

    protected static final String WILDCARD_WITH_CHARSET_UTF_8 = "*/*; charset=UTF-8";

    @Override
    public ServerResponse preProcess(HttpRequest request,
            ResourceMethod method) throws Failure, WebApplicationException {

        HttpHeaders httpHeaders = request.getHttpHeaders();
        request.setAttribute(InputPart.DEFAULT_CONTENT_TYPE_PROPERTY, WILDCARD_WITH_CHARSET_UTF_8);
        return null;
    }

}
