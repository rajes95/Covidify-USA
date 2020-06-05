USE `CovidifyUSA`;
DROP TABLE IF EXISTS `CovidifyUSA`.`GovernorsDataStaging`;

LOAD DATA INFILE '/var/lib/mysql-files/state_fips.csv' 
INTO TABLE `CovidifyUSA`.`State` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@StateName,@PostalCode, @Fips)
set `StateName`=@StateName,`StateFIPS`=@Fips;

LOAD DATA INFILE './covid-us-counties.csv'
IGNORE INTO TABLE `CovidifyUSA`.`County`
FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@date,@county,@state,@fips,@cases,@deaths)
set `StateFKey` = (SELECT `StateKey` FROM `CovidifyUSA`.`State` where `StateName`=@state), 
`CountyFIPS`=@fips, `CountyName`=@county;

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`GovernorsDataStaging` (
  `State` TEXT,
  `Governor` TEXT,
  `Date of Birth` TEXT,
  `Date of Inauguration` varchar(400),
  `Time in Office` varchar(400),
  `Party` TEXT
  )
ENGINE = InnoDB;

LOAD DATA INFILE '/var/lib/mysql-files/StateGovernorsData.csv' 
INTO TABLE GovernorsDataStaging 
CHARACTER SET utf8
FIELDS TERMINATED BY ',' ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 LINES;

ALTER TABLE GovernorsDataStaging
ADD COLUMN `Year` YEAR NULL;

ALTER TABLE GovernorsDataStaging
ADD COLUMN StateGovernorKey INT NULL;

UPDATE GovernorsDataStaging
SET `Year` = 2020
WHERE `Year` IS NULL;

UPDATE GovernorsDataStaging
SET Party = 'Other'
WHERE !(Party = 'Republican' or Party = 'Democratic');

UPDATE GovernorsDataStaging
SET State = 'Virgin Islands'
WHERE State = 'United States Virgin Islands';

Insert Into StateGovernor Select StateGovernorKey, StateKey, `Year`, Governor, Party From GovernorsDataStaging inner join State on StateName = State;

DROP TABLE IF EXISTS `CovidifyUSA`.`GovernorsDataStaging`;

Select * from State Inner Join StateGovernor on StateKey=StateFKey;
