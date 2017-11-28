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
`when_string` varchar(1024) COMMENT 'When String',
`then_string` varchar(1024) COMMENT 'Then String',
`when_drl` varchar(1024) COMMENT 'When DRL',
`then_drl` varchar(1024) COMMENT 'Then DRL',
`valid_to` timestamp COMMENT 'Valid To',
`valid_from` timestamp COMMENT 'valid From',
`delay` varchar(10) COMMENT 'Delay',
`priority` tinyint  COMMENT 'Priority',
`description` varchar(240) COMMENT 'Description',
`is_internal` boolean COMMENT 'Is Internal',
`version` double(5,2) COMMENT 'Version',
`model` BLOB COMMENT 'Model',
`is_enable` boolean COMMENT 'Is Enable',
`is_dirty` boolean COMMENT 'Is Dirty',
`is_deployed` boolean COMMENT 'Is Deployed',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=10000 DEFAULT CHARSET=utf8 COMMENT='Rule';

CREATE TABLE session_persistence(
`id` bigint NOT NULL AUTO_INCREMENT COMMENT 'Session Persistence ID',
`session_binary` BLOB COMMENT 'session Binary',
`last_update` timestamp COMMENT 'Last Update',
`version` tinyint default 0 COMMENT 'Version',
`active` boolean COMMENT 'Active',
`slot` tinyint COMMENT 'Slot',
`fact_count` tinyint COMMENT 'Fact Count',
`active_rules` tinyint COMMENT 'Active Rules',
`last_verification` timestamp COMMENT 'Last Verification',
`migration_done` boolean COMMENT 'Migration Done',
`verified` boolean COMMENT 'Verified',
PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=100000 DEFAULT CHARSET=utf8 COMMENT='Session Persistence';
