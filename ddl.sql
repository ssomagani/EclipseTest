CREATE TABLE principals (
	id VARCHAR(36) NOT NULL, 
	kind VARCHAR(36), 
	note VARCHAR(128), 
	CONSTRAINT PK_principals PRIMARY KEY (id));
PARTITION TABLE principals ON COLUMN id;

CREATE PROCEDURE partition on table principals column id from class CreatePrincipalOrig;

