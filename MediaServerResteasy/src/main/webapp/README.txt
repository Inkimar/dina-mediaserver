2014-07-31: Tar bort från DeterminationResourceFetch : Tror inte de används:
<stop>

 /**
     * Returns ONLY the mediaUUID - not the 'Metadata' for the Media
     *
     * returns one mediaUUID
     * http://localhost:8080/MediaServerResteasy/media/determination/test/02d2ef271-02ca-4934-860c-6c6a4ed043f9/view:sitting
     *
     * returns multiple mediUUID:s
     * http://localhost:8080/MediaServerResteasy/media/determination/test/02d2ef271-02ca-4934-860c-6c6a4ed043f9/country:sweden
     *
     * @param extUUID
     * @return
     */
    @GET
    @Path("/determination/xml/{extuuid}")
    @Produces({"application/xml", "application/json"})
    public Response getMediaUUIDFilterByextUUID(@PathParam("extuuid") String extUUID) {
        Determination determination = (Determination) bean.getDetermination(extUUID);
        List<String> listOfUUIDs = bean.getMediaUUID(extUUID);
        final GenericEntity<List<String>> entity = new GenericEntity<List<String>>(listOfUUIDs) {
        };
        return Response.ok().entity(entity).build();
    }
    
    /**
     * Returns ONLY the mediaUUID - not the 'Metadata' for the Media
     *
     * returns one mediaUUID
     *
     * returns multiple mediUUID:s
     * http://localhost:8080/MediaServerResteasy/media/determination/xml/02d2ef271-02ca-4934-860c-6c6a4ed043f9/country:sweden
     *
     * @param extUUID
     * @param tags
     * @return all MediaUUID that this externalUUID owns
     */
    @GET
    @Path("/determination/xml/{extuuid}/{tags}")
    @Produces({"application/json"})
    public Response getMediaUUIDFilterByextUUID_AND_tags(@PathParam("extuuid") String extUUID, @PathParam("tags") String tags) {
        Determination determination = (Determination) bean.getDetermination(extUUID);
        String uuid = determination.getMedia().getUuid();
        List<String> listOfUUIDs = bean.getMediaUUID(extUUID, tags);
        final GenericEntity<List<String>> entity = new GenericEntity<List<String>>(listOfUUIDs) {
        };
        
        return Response.ok().entity(entity).build();
    }
 /**
     * Test purposes A single XML-representation of the content of Determination
     *
     * @TODO, Does not give you Media - round-round - needs to be fixed
     * @param extUUID
     * @return
     */
    @GET
    @Path("/determination/test/xml/{extuuid}")
    public Determination getDeterminationAsXML(@PathParam("extuuid") String extUUID) {
        Determination determination = (Determination) bean.getDetermination(extUUID);
        return determination;
    }

    /**
     * Test purposes Streams a single media back.
     *
     * @param taxonUUID
     * @return Media Stream
     */
    @GET
    @Path("/determination/test/stream/{taxonUUID}")
    public Response getDeterminationAsStream(@PathParam("taxonUUID") String taxonUUID) {
        Determination determination = (Determination) bean.getDetermination(taxonUUID);
        String uuid = determination.getMedia().getUuid();
        MediaResourceFetchBinary bin = new MediaResourceFetchBinary();
        return bin.getMedia(uuid);
    }

    /**
     * Test purposes Streams a single media back.
     *
     * @param extUUID
     * @param tags
     * @return Media Stream
     */
    @GET
    @Path("/determination/test/stream/{extuuid}/{tags}")
    public Response getMediaStreamFilterByTags(@PathParam("extuuid") String extUUID, @PathParam("tags") String tags) {
        Determination determination = (Determination) bean.getDetermination(extUUID);
        String uuid = determination.getMedia().getUuid();
        List<String> listOfUUIDs = bean.getMediaUUID(extUUID, tags);

        int size = listOfUUIDs.size();
        System.out.println("Number of images is " + size);
        if (size == 1) {
            uuid = listOfUUIDs.get(0);
        }
        if (size > 1) {
            for (String id : listOfUUIDs) {
                System.out.println("    restful -> " + id);
            }
        }

        MediaResourceFetchBinary bin = new MediaResourceFetchBinary();
        return bin.getMedia(uuid);
    }

<stop>
    /**
     * String[] items = commaSeparatedList.split(",");
     *
     * @param link
     * @return
     */
//    private Collection<String> transform(String[] items) {
//        Collection<String> collection = new HashSet<>(Arrays.asList(items));
//        return collection;
//    }

3Juni-2014
- Skyttner pushar in bilder+länk(DETERMINATION) 
Klass : MediaResourcePostForm
URL : /postJSONWithLIsta
@POST
    @Path("/postJSONWithLIsta")
    @Produces("text/plain")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createNewCouplingParams(@MultipartForm LinkUploadForm form)

Radera: strängen kan ha ändrats
 curl -X DELETE http://localhost:8080/MediaServerResteasy/media/image/768bed28-0805-4c9f-800a-440476f2c823



Problems with create JAXB
-> http://stackoverflow.com/questions/3630827/why-writer-for-media-type-application-json-missing

Detta löser upp :
<dependency>
            <groupId>org.jboss.resteasy</groupId>
            <artifactId>resteasy-jackson-provider</artifactId>
            <version>2.3.2.Final</version>
</dependency>

Problem ; Med ovanstående Jacksson !?
Mkyong har -> http://www.mkyong.com/webservices/jax-rs/integrate-jackson-with-resteasy/ - ej testat
måste det vara ett EJB-Projekt !?
kraschar om jag har :
 <dependency>
		<groupId>org.jboss.resteasy</groupId>
		<artifactId>resteasy-jaxrs</artifactId>
		<version>2.2.1.GA</version>
	</dependency>


Finns i databas ( default liquibase ):
Hämta en bild: Sittande Skata :
http://localhost:8080/MediaServerResteasy/media/stream/863ec044-17cf-4c87-81cc-783ab13230ae
via mock-taxon :
http://localhost:8080/MediaServerResteasy/media/determination/stream/02d2ef271-02ca-4934-860c-6c6a4ed043f9/view:sitting

Hämta en bild : Flygande skata :
http://localhost:8080/MediaServerResteasy/media/stream/c41bd445-8796-4421-9b77-fd1e65b14974
Via mock-taxon :
http://localhost:8080/MediaServerResteasy/media/determination/stream/02d2ef271-02ca-4934-860c-6c6a4ed043f9/view:flying

Use this way :
All pica pica ( filter = country:sweden )
http://localhost:8080/MediaServerResteasy/media/determination/test/02d2ef271-02ca-4934-860c-6c6a4ed043f9/country:sweden
Svar =  ["863ec044-17cf-4c87-81cc-783ab13230ae","c41bd445-8796-4421-9b77-fd1e65b14974"]
On sitting pica pica( filter = view:sitting )
http://localhost:8080/MediaServerResteasy/media/determination/test/02d2ef271-02ca-4934-860c-6c6a4ed043f9/view:sitting
["863ec044-17cf-4c87-81cc-783ab13230ae"]

Hämta en bild : Sittande korp :
http://localhost:8080/MediaServerResteasy/media/stream/e4a3cf7d-add4-4949-a6ce-0f5594e61970


http://localhost:8080/MediaServerResteasy/media/image/863ec044-17cf-4c87-81cc-783ab13230ae
Where the TAGS-table is populated :
http://localhost:8080/MediaServerResteasy/media/image/e4a3cf7d-add4-4949-a6ce-0f5594e61970


Resteasy:
Har ApplicationConfig men använder filtret i web.xml

2014-05-08
http://localhost:8080/MediaServerResteasy/media/determination/metadata/02d2ef271-02ca-4934-860c-6c6a4ed043f9/country:sweden

http://localhost:8080/MediaServerResteasy/media/image/ebb45da5-bd25-45af-8a04-3470d38523d1

curl -X GET -H "Accept: application/xml" http://localhost:8080/MediaServerResteasy/media/image/8fc7e8c9-6a04-4c61-adf6-b7c1c2f9154e
curl -X GET -H "Accept: application/json" http://localhost:8080/MediaServerResteasy/media/image/8fc7e8c9-6a04-4c61-adf6-b7c1c2f9154e
