/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.nrm.mashup.simpletaxonmock.service;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import se.nrm.mashup.simpletaxonmock.domain.MockTaxon;

/**
 * http://localhost:8080/SimpleTaxonMock/webresources/mocktaxon
 * http://localhost:8080/SimpleTaxonMock/webresources/mocktaxon/common/skata
 * @author ingimar
 */
@Stateless
@Path("mocktaxon")
public class MockTaxonFacadeREST extends AbstractFacade<MockTaxon> {

    @PersistenceContext(unitName = "SimpleTaxonMock")
    private EntityManager em;

    public MockTaxonFacadeREST() {
        super(MockTaxon.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(MockTaxon entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, MockTaxon entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public MockTaxon find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @Override
    @GET
    @Path("/common/{name}")
    @Produces({"application/xml", "application/json"})
    public MockTaxon findByName(@PathParam("name") String name) {
        System.out.println("FindByName");
        return super.findByName(name);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<MockTaxon> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<MockTaxon> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
