#Requirement for the Mediaserver


![alt mediaserver in fokus](Mediaserver-module(1-2).png)

* * *
#Versionhistory
Date | Version | Author | Description
------------- | ------------- | ------------- | -------------
2013-03-21  |  1  | ingimar.erlingsson@nrm.se  | original
2015-02-01  |  2  | ingimar.erlingsson@nrm.se  | moving from .odt to .md
2015-02-10  |  3  | ingimar.erlingsson@nrm.se  | restructuring the document
<date>  | <version>  | <email>  | <desc>

## About DINA
“ The DINA system is comprised of several parts, the most important one being the Specify database and its client software. Various different types of media (including documents) are proposed to be handled by the DINA system and there is a need for a simple, yet flexible storage system which can be accessed by the various parts of the DINA system “ 
- from the draft document 'DINA attachment-server: A simple Approach'

Every DINA-module is self-contained and is therefore deployable in an application server.

#Introduction to the Mediaserver
This document is the requirement document for the Mediaserver.
The Mediaserver (aka. 'attachment server')  handles mediafiles and their metadata and  tags.
The Mediaserver is a DINA module.

## The purpose
The scope of the Mediaserver is small and should be kept at a minimum.
The purpose of the Mediaserver is to serve other modules/system with storing and retrieving binaries.

1. The Mediaserver can be used  as a standalone server with no coupling  to other systems.
2. The Mediaserveras can be used as an integrated part of your system, coupled to your system.

The Mediaservers responsibility is: <p>
1. Storing : <p>
1.1 Storing binaries to the filesystem and Storing the metadata/tags to a database. <p>
2. Retrieving: <p>
2.1 Retrieving the binaries and retrieving the metadata for the binaries <p>
3. update and delete binaries and metadata. 

##Glossary
When we mention mediafiles in this document we are referring to  the following entities.

1. Image: its metadata and tags. 
2. Sound: its metadata and tags. 
3. Video : its metadata and tags. 
4. 3D : its metadata and tags. 
5. Attachment : its metadata and tags

Where : image, Sound, Video, 3D and Attachment are binaries.

## Constraints
- No dependency on a JEE framework.
- No User management.
- No Events-logging.
- No metadata is writeen back to the binary.
- No UI.
- No dependency on a specific database provider
- No dependecny on a specific Application server

* * *
#Functional requirements
The Mediaserver should provide services for other systems :  i.e. naturforskaren/ 'the Naturalist'  & the DINA-system.

1. To store mediafiles (Create)
2. To edit mediafiles (Update)
3. To search for mediafiles (Retrieve) using metadata/tags
4. To delete/disable mediafiles (Delete)

### Storing the mediafiles 
All medifiles are stored using UUID as names. <p>
UUID, " Universally unique identifier, 128-bit number." ( http://tools.ietf.org/html/rfc4122) <p>

The mediafiles are stored in the filesystem with the depth of 3 layers.
Only one image is stored, no derivates are created at the same time.

The structure is that there are directories ranging from '0' to 'F' (hexadecimal) with the depth of 3 layers, this gives 4096 directories ( 16^3 ).

Ex. Say that we have 10 000 000 ( ten millions ) mediafiles, with an even spread this would give 2441 mediafiles per directory. 

All mediafiles are processed in the same way regarding that they are streamed to the filesystem and streamed back to their client.<p>
The mediafile will hold their own 'media-type'/'mime-type' ( stored in the database )

- The principle is the following : 
- - if the generated UUID is **'ab30899c-58a0-4305-85a6-bbfa14f89b92'**
- Then the file is stored in the following directory ( subdirectory is made up by the first 3 chars ):
- - directory = **/opt/data/mediaserver/newmedia/a/b/3**
- - directory and file = /opt/data/mediaserver/newmedia/a/b/3/**ab30899c-58a0-4305-85a6-bbfa14f89b92**


### Metadata and Tags
a Distinguish between metadata and tags

#### Tags (about the content)
Generics tags are supported and saved as a text-string in the database. <p>
- ':' is used as the delimiter between key and its value.
- '&' is used as the delimiter between key/value-pairs  <p>

<b>i.e setting key='country' with value='sweden' and key='view' with value='dorsal'. </b><p>
<b>gives the following textstring => 'country:sweden&value=dorsal'</b> <p>

The Mediaserver sets no constraint on the keys that are used.  <p>
This gives an external module using the Mediaserver freedom to define its own keys and constrain others keys.

#### Metadata (about the file)
such as
- original filename <p>
- mime type <p>
- owner <p>
- visibility <p>
-- i.e  'private' or 'public'
- md5hash <p>
-- Saving the md5hash for every mediafile (facilitates finding duplicates)

### Exif
To store Exif-metadata for Mediafiles of type images.<p>
- Exif-metadata is stored in a table of its own..<p>
- configurable.<p>

### Coupling to an external module
A table will be created to couple an external module to the mediaserver.<p>
For instance : .<p>
'the Naturalist', the 'taxon-UUID' will map to one or many  'media-UUID'..<p>

### Control over the licenses
Licences will be stored in a licence-table. <p>
This gives the administrator full control of what licenses are permitted in the system.<p>

## Configuration Management
Maintenability <p>
It is important to consider ease of maintenability for the future 'configuration manager' of the Mediaserver.<p>

### Ease of installation
- to download and build the source code
- to install and populate the chosen database-engine
- to deploy the service
- Application  server :  how-to set up a datasource/datapool/JNDI-handle
- to set the filesystem path for the mediafiles

A table ( key/value) in the schema is used for managing settables :
For instance the below ( key/value ) is set in the database.

1. is_exif = [true||false]
2. path_to_files = '/opt/data/mediaserver/newmedia'

![alt Admin-table](admin-table.png)

## Demo-data and Demo-Application

### demo-data provided with the mediaserver
- Ability to verify the installation <p>
-- the following demo-data is provided : xx, yy, zz <p>

### A demo application  is provided
the 'demo application' is acting as an external module <p>
Mediaserver + 'demo SimpleTaxonMock'. <p>
- [mediaserver](https://github.com/Inkimar/dina-mediaserver "dina mediaserver").<p>
- [demo SimpleTaxonMock ](https://github.com/Inkimar/dina-mediaserver-demo "dina mediaserver demo").

## Single-Sign-On
- N/A

## Security
- N/A

## References
- roadmap.md
- technology-choices.md
- requirements-suggestions.md


#Appendix A

## services
- naturforskaren : 'the naturalist' , http://www.naturforskaren.se/ 
- specify : http://specifyx.specifysoftware.org/
- morphank:  http://www.morphbank.net/

## additional
- Exif : Exchangeable image file format 
-- http://en.wikipedia.org/wiki/Exchangeable_image_file_format

- media-types: 
-- http://www.iana.org/assignments/media-types 




