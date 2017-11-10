--Initial DB

--Create DB
CREATE database ems;

--Use DB
use ems;

--Create table
CREATE TABLE rule(
`id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Rule ID',
`name` varchar(120) COMMENT 'Rule Name',
`display_name` varchar(120) COMMENT 'Rule Display Name',
`when_clause` BLOB COMMENT 'When',
`then_clause` BLOB COMmENT 'Then',
`valid_to` timestamp COMMENT 'Valid To',
`valid_from` timestamp COMMENT 'valid From',
`delay` varchar(10) COMMENT 'Delay',
`priority` tinyint  COMMENT 'Priority',
`description` varchar(240) COMMENT 'Description',
`is_internal` boolean COMMENT 'Is Internal',
`version` double(5,2) COMMENT 'Version',
`model` BLOB COMMENT 'Model',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='Rule';

