#Milestones
Common for the milestones is RESTful API:s are used as a way to access the service.

##Milestone 1 
Goal :  Minimum Mediaserver functionality &  integration with 'the Naturalist'.

1. Mediafiles and a subset of their Metadata  <p>
1.1 Implement CRUD :  Images and  Sound <p>
2. Database Structure  <p>
2.1. Create the schema for the database.  <p>
2.2. Database agnostic , should be easy to change database-engine  <p>
3. It should be possible to tag the mediafiles.  <p>
3.1. Tag-entity ( implemented as key/value)  <p>
4. 'linkage'-table to an external system.  <p>
4.1. to couple a 'taxon' from  'the Naturalist' to #medifiles [ one-to-many] <p>

Validate the functionality <p>
- 'demo with a user interface' + demo-data.  <p>
1. to store images/sound and their metadata.  <p>
2. to store metadata in a string (key/value) : i.e 'view=left'.  <p>
3. to search for a 'key/value' and present the mediafile(s).  <p>


##Milestone 2
Goal   : Export data from 'the Naturalist'  to the Mediaserver + *integrate* . <p>

1.  Export Data ( Mediafiles + their Metadata ) from 'the Naturalist'  to the Mediaserver  <p>
1.1.  Move the Mediafiles  from  'the Naturalist'   to the Mediaserver <p>
2.  Integrate  'the Naturalist' with the Mediaserver  <p>
2.1 read : refactor the code in 'the Naturalist' <p>
3. Validate the integration:  <p>
3.1 Ability to search on a taxon in 'the Naturalist' and get a response from the Mediaserver <p>
3.1.1 #mediafiles will be returned and displayed  <p>
4.  'the Naturalist'  adminstration UI  <p>
4.1 Ability to add mediafiles, store them in the Mediaserver ( coupled to taxon ) <p>
5. Validate the admin-integration <p>


##Milestone 3 
Goal : Added functionality.<p>

1. Ability to tag Sound- and Video-files <p>
1.1 either start+duration or start- and end-time <p>
2. To return image-files with another dimension <p>
2.1 return the 'derivate' on the fly <p>
3. To store Exif-information in the database <p>
3.1 A 'configuration manager' for the mediaserver should be able to set a flag ( true or false) in the database ( false = no Exif-metadata will be stored, true = Exif-metadata is stored).

##Milestone 4
Integration  with Specify <p>
1. gather/dig for requirments that the Specify-team has on the Mediaserver <p>
2. structure requirements<p>
3. Implement requirements<p>

Questions:
Which version of Specify, version 6 or 7 ?

##Milestone 5
Integration  with Morphank <p>
1. Observe that Morphbank only stores images today ( 2014-12-06 ) <p>
2. An easy way to configure which Morphbank installation to export to. <p>
2.1 i.e the Swedish-morphbank installation <p>
3. Using an 'export' check-box when images + metadata should be exported to morphbank. <p>

Questions:
1.Single-Sign-On , ability to login into the mediaserver + morphbank. <p>
2. when successfully exported a morphbank-ID needs to be stored in the Mediaserver ?<p>

##Milestone XX
implement the DINA RESTful standard when that standard is finalized.<p>
- [dina-api-std.html](http://dina-collections.github.io/dina-api-std.html "dina-api-std.html").<p>