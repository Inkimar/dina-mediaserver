package se.nrm.simpletaxonmock.client.controller;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.apache.commons.io.IOUtils;

/**
 *
 * @author ingimar
 */
@WebServlet(name = "SimpleProxyWithTags", urlPatterns = {"/SimpleProxyWithTags"})
public class SimpleProxyWithTags extends HttpServlet {

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final String MEDIA_BASE_URL = "http://localhost:8080/MediaServerResteasy/media/determination/metadata/";
        final String LOCALE_ONLY_SWEDISH = "/sv_SE/";

        String url = "";
        String vernaculaName = request.getParameter("name");
        String tags = request.getParameter("tags");
        String uriToDetermination = "http://localhost:8080/SimpleTaxonMockServer/webresources/mocktaxon/common/" + vernaculaName;
        String externalUUID = fetch(uriToDetermination, "extUuid");

        if (tags != null && !tags.isEmpty()) {
            url = MEDIA_BASE_URL + externalUUID + LOCALE_ONLY_SWEDISH + tags;
        } else {
            url = MEDIA_BASE_URL + externalUUID + LOCALE_ONLY_SWEDISH;
        }

        String xml = fetchMedia(url, "mediaURL");
        transformation(xml, response);
    }

    private String fetch(String uri, String key) {
        Client client = Client.create();
        WebResource webResource = client.resource(uri);
        ClientResponse response = webResource.accept("application/xml")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String xmlResult = response.getEntity(String.class);
        Document doc = Jsoup.parse(xmlResult, "", Parser.xmlParser());

        String chosen = doc.select(key).text();
        return chosen;
    }

    private String fetchMedia(String uri, String key) {
        Client client = Client.create();
        WebResource webResource = client.resource(uri);
        ClientResponse response = webResource.accept("application/xml")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }
        String xmlResult = response.getEntity(String.class);

//        Document doc = Jsoup.parse(xmlResult, "", Parser.xmlParser());
//        String chosen = doc.select(key).text();
        return xmlResult;
    }

    private void transformation(String xml2, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        InputSource source = new InputSource(new StringReader(xml2));
        Source xmlSource = null;
        try {
            org.w3c.dom.Document domDoc = factory.newDocumentBuilder().parse(source);
            xmlSource = new javax.xml.transform.dom.DOMSource(domDoc);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(SimpleProxyWithTags.class.getName()).log(Level.SEVERE, null, ex);

        }
        String xsl3 = getXSLT_Content();
        Source xsltSource = new StreamSource(new StringReader(xsl3));

        TransformerFactory transFact = TransformerFactory.newInstance();
        try {
            // get a transformer for this particular stylesheet
            Transformer trans = transFact.newTransformer(xsltSource);

            // do the transformation
            trans.transform(xmlSource, new StreamResult(out));
        } catch (Exception ex) {
            Logger.getLogger(SimpleProxyWithTags.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    private String getXSLT_Content() {
        InputStream resourceAsStream = getServletContext().getResourceAsStream("/WEB-INF/media_uuid.xsl");
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(resourceAsStream, writer);
        } catch (IOException ex) {
            Logger.getLogger(SimpleProxyWithTags.class.getName()).log(Level.SEVERE, null, ex);
        }
        String fileContent = writer.toString();
        return fileContent;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
