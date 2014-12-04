package se.nrm.simpletaxonmock.client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * @author ingimar
 */
@WebServlet(urlPatterns = {"/apache/searchByName"})
public class ProxyApacheRestServlet extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String commonName = request.getParameter("name");
        String result = getFromRestfulService(commonName);

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProxyServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>You searched for  " + commonName + "</h1>");
            out.println("<h1>result is ->  " + result + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String getFromRestfulService(String name) throws IOException {
//        final String uri = "http://172.16.23.12:8080/MockTaxonomy/webresources/mocktaxon/common/" + name;
        final String uri = "http://localhost:8080/SimpleTaxonMockServer/webresources/mocktaxon/common/" + name;
//           final String uri = "http://localhost:8080/SimpleTaxonMockServer/webresources/mocktaxon/common/" + name;
        
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(uri);
        getRequest.addHeader("accept", "application/xml");

        HttpResponse response = httpClient.execute(getRequest);

        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));

        String output;
        StringBuilder buffer = new StringBuilder();
        System.out.println("Output from Server .... \n");
        while ((output = br.readLine()) != null) {
            buffer.append(output);
            System.out.println(output);
        }
        
        return buffer.toString(); 
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
