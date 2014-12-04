DROP TABLE IF EXISTS `mock_taxon`;
CREATE TABLE `mock_taxon` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ext_uuid` varchar(255) DEFAULT NULL,
  `common_name` varchar(255) DEFAULT NULL,
  `scientific_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

LOCK TABLES `mock_taxon` WRITE;
INSERT INTO `mock_taxon` VALUES (1,'02d2ef271-02ca-4934-860c-6c6a4ed043f9','skata','Pica pica'),(2,'1089d789a-2df5-4882-abce-76d4f22e0a7a','Korp','Corvus corax'),(4,'fe1b99c3-d517-4ca0-a5e5-a2545e59c9a2','Människa','Homo Sapiens'),(6,'b0b7e712-7d1b-46a7-985a-b7913e07d58d','duper','super'),(7,'f53f2bd4-fa72-4fdc-a136-9873cae4b48b','fish fish','cod');
UNLOCK TABLES;