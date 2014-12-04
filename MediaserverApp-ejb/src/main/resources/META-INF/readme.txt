Working-Notes :

2013-11-14 - Set up log4j in Glassfish:
-Using SLF4J logging frame work with log4j implementation-

1. Create a log4j.xml or log4j.properties file
2. Put this file into glassfish-installation-dir/domains/domain1/lib/classes
3. Download slf4j-api-1.6.1.jar, slf4j-log4j12-1.6.1.jar and log4j-1.2.16.jar
4. Put above three jars into glassfish-install-dir/domains/domain1/lib/ext

measuring time : 
to log using my interceptor / @Profiled .
- beans.xml must be present.

2014-01-17 : 
Administration-table, setup is done from the
'Mediaserver-database-management' , here the
path-to-the-mediafiles are configured.
- No directory is created in your FS !
- No UI for the ADMIN_MEDIA has been created.
       