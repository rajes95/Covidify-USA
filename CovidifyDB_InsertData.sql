USE `CovidifyUSA`;
DROP TABLE IF EXISTS `CovidifyUSA`.`GovernorsDataStaging`;
DROP TABLE IF EXISTS `CovidifyUSA`.`MultiStaging`;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateCounty`;
DROP TABLE IF EXISTS `CovidifyUSA`.`LongLatCounty`;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateHospitalStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CountyHospitalStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CovidStage`;

LOAD DATA INFILE './state_fips.csv' 
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

# find the county key based on county name and state name
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateCounty` (
  `StateKey` INT,
  `StateName` TEXT,
  `CountyKey` INT,
  `CountyName` TEXT
  )
ENGINE = InnoDB;
INSERT StateCounty SELECT StateKey, StateName, CountyKey, CountyName from State 
inner join `CovidifyUSA`.`County` on StateFKey=StateKey;

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidStage` (
  `CountyName` VARCHAR(45),
  `State` VARCHAR(45),
  `Date` VARCHAR(45),
  `CovidDeaths` INT,
  `CovidCases` INT
  )
ENGINE = InnoDB;
LOAD DATA INFILE './covid-us-counties.csv' 
INTO TABLE `CovidifyUSA`.`CovidStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@date,@county,@state,@fips,@cases,@deaths)
set `CountyName`=@county,`State`=@state,`Date`=@date, `CovidDeaths`=@deaths, `CovidCases`=@cases;

INSERT INTO `CovidifyUSA`.`CovidByDate`
(`CountyFKey`, `Date`, `CovidDeaths`, `CovidCases`)
SELECT `CountyKey`, `Date`, `CovidDeaths`, `CovidCases`
#from CovidStage inner join County on CovidStage.CountyName=County.CountyName; 
from CovidStage inner join StateCounty on StateCounty.CountyName=CovidStage.CountyName
and StateCounty.StateName=CovidStage.State;


SET SESSION sql_mode = '';
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`MultiStaging`(
	## PresElection table
	`County` TEXT, # County - read in and get rid of comma, get rid of 'county'
    `State` TEXT,
	#`Year` INT, #08, 12, 16
	`DemocratsPercent08` DECIMAL(5,2),
	`DemocratsPercent12` DECIMAL(5,2),
	`DemocratsPercent16` DECIMAL(5,2),
	`RepublicansPercent08` DECIMAL(5,2),
	`RepublicansPercent12` DECIMAL(5,2),
	`RepublicansPercent16` DECIMAL(5,2),
	`OtherPercent08` DECIMAL(5,2),
	`OtherPercent12` DECIMAL(5,2),
	`OtherPercent16` DECIMAL(5,2),

	
	## Demographics table
	# Year - all 2016?
	`White` DECIMAL(5,2), 
	`AfricanAmerican` DECIMAL(5,2), 
	`Latino` DECIMAL(5,2),
    `NativeAmerican` DECIMAL(5,2), 
	`AsianAmerican` DECIMAL(5,2), 
	`OtherEthnicity` DECIMAL(5,2),
    `PovertyRate` DECIMAL(5,2),
    `MedianAge` DECIMAL, 
	`MedianEarnings` DECIMAL,
	
	## Climate table
	-- `Year`
	`Elevation` DECIMAL,
	`WinterPrcp` DECIMAL,
	`SummerPrcp` DECIMAL,
	`SpringPrcp` DECIMAL,
	`AutumnPrcp` DECIMAL,
	`WinterTavg` DECIMAL,
	`SummerTavg` DECIMAL,
	`SpringTavg` DECIMAL,
	`AutumnTavg` DECIMAL,
	
	##Population table
	# total population
	`TotalPopulation` INT,
	# population over 60 ## Where is this?
	
	## county table
	`Longitude` VARCHAR(45),
	`Latitude` VARCHAR(45)
)
ENGINE = InnoDB;
LOAD DATA INFILE './usa-2016-presidential-election-by-county.csv' 
INTO TABLE MultiStaging
CHARACTER SET utf8
FIELDS TERMINATED BY ';' ENCLOSED BY '"' LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(@State, @ST, @Fips, @County, @dummy, @dummy, @Democrats08, @Democrats12, 
	@Republicans08, @Republicans12, @Republicans16, @Democrats16, @Green16,
    @Libertarians16, @Republicans12, @Republicans08, @Democrats12, @Democrats08,
    @dummy, @dummy, @dummy, @dummy, @dummy, @MedianEarnings10,
    @White, @AfricanAmerican, @NativeAmerican, @AsianAmerican,
    @Other, @Latino, @dummy, @dummy, @TotalPopulation, @dummy, @PovertyRate, @dummy,
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy,
    @dummy, @dummy, @MedianAge, @longitude, @latitude, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy,
    @dummy, @dummy, @Unemployment, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy,
    @dummy, @dummy, @Elevation, @AnnualPrcp, @WinterPrcp, @SummerPrcp, @SpringPrcp, @AutumnPrcp, @AnnualTavg,
    @AnnualTmax, @AnnualTmin, @WinterTavg, @WinterTmax, @WinterTmin, @SummerTavg, @SummerTmax, @SummerTmin,
    @SpringTavg, @SpringTmax, @SpringTmin, @AutumnTavg, @AutumnTmax, @AutumnTmin, @dummy, @temp, @precip,
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy,
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy,
    @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @dummy, @total16, @Other16Frac,
    @Rep16Frac, @Dem16Frac, @dummy, @dummy, @total08, @total12, @other08, @other12, @Other12Frac,
	@Other08Frac, @Rep12Frac, @Rep08Frac, @Dem12Frac, @Dem08Frac)
set `County`=@County, `State`=@State,`DemocratsPercent08`=@Dem08Frac, `DemocratsPercent12`=@Dem12Frac, `DemocratsPercent16`=@Dem16Frac, 
`RepublicansPercent08`=@Rep08Frac, `RepublicansPercent12`=@Rep12Frac, `RepublicansPercent16`=@Rep16Frac, 
 `OtherPercent08`=@Other08Frac, `OtherPercent12`=@Other12Frac, `OtherPercent16`=@Other16Frac, 
 `White`=@White, `AfricanAmerican`=@AfricanAmerican, `Latino`=@Latino, `NativeAmerican`=@NativeAmerican, `AsianAmerican`=@AsianAmerican,
 `OtherEthnicity`=@Other, `PovertyRate`=@PovertyRate, `MedianAge`=@MedianAge, `MedianEarnings`=@MedianEarnings10,
 `Elevation`=@Elevation, `WinterPrcp`=@WinterPrcp, `SummerPrcp`=@SummerPrcp, `SpringPrcp`=@SpringPrcp, `AutumnPrcp`=@AutumnPrcp,
 `WinterTavg`=@WinterTavg, `SummerTavg`=@SummerTavg, `SpringTavg`=@SpringTavg, `AutumnTavg`=@AutumnTavg, `TotalPopulation`=@TotalPopulation,
 `Longitude`=@longitude, `Latitude`=@latitude;

UPDATE `CovidifyUSA`.`MultiStaging` SET County = SUBSTRING_INDEX(County, ',', 1);
UPDATE `CovidifyUSA`.`MultiStaging` SET County = SUBSTRING_INDEX(County, ' ', 1);

# Election table


SET @year08= 2008;
INSERT INTO `CovidifyUSA`.`PresidentialElectionVotePercentages` 
(`CountyFKey`,  `DemocratsPercent`, `RepublicansPercent`, `OtherPercent`, `Year`)
SELECT `CountyKey`,`DemocratsPercent08`, `RepublicansPercent08`, `OtherPercent08`,@year08
from MultiStaging inner join StateCounty on CountyName=County and StateName=State;

SET @year12= 2012;
INSERT INTO `CovidifyUSA`.`PresidentialElectionVotePercentages` 
(`CountyFKey`,  `DemocratsPercent`, `RepublicansPercent`, `OtherPercent`, `Year`)
SELECT `CountyKey`,`DemocratsPercent12`, `RepublicansPercent12`, `OtherPercent12`,@year12
from MultiStaging inner join StateCounty on CountyName=County and StateName=State;

SET @year16= 2016;
INSERT INTO `CovidifyUSA`.`PresidentialElectionVotePercentages` 
(`CountyFKey`,  `DemocratsPercent`, `RepublicansPercent`, `OtherPercent`, `Year`)
SELECT `CountyKey`,`DemocratsPercent16`, `RepublicansPercent16`, `OtherPercent16`,@year16
from MultiStaging inner join StateCounty on CountyName=County and StateName=State;

SELECT * from `CovidifyUSA`.`PresidentialElectionVotePercentages` where `Year`=2016;

## Demographics table
INSERT INTO `CovidifyUSA`.`Demographics` 
(`CountyFKey`, `Year`, `White`, `AfricanAmerican`, `Latino`, `NativeAmerican`,
  `AsianAmerican`, `OtherEthnicity`, `PovertyRate`, `MedianAge`, `MedianEarnings`) 
SELECT `CountyKey`, @year16,`White`, `AfricanAmerican`, `Latino`, `NativeAmerican`, 
`AsianAmerican`, `OtherEthnicity`, `PovertyRate`, `MedianAge`, `MedianEarnings`
from MultiStaging inner join StateCounty on CountyName=County and StateName=State;

## Climate Table
INSERT INTO `CovidifyUSA`.`Climate` 
(`CountyFKey`, `Year`, `Elevation`, `WinterPrcp`, `SummerPrcp`, `SpringPrcp`,
  `AutumnPrcp`, `WinterTavg`, `SummerTavg`, `SpringTavg`, `AutumnTavg`)
SELECT `CountyKey`, @year16, `Elevation`, `WinterPrcp`, `SummerPrcp`, `SpringPrcp`,
  `AutumnPrcp`, `WinterTavg`, `SummerTavg`, `SpringTavg`, `AutumnTavg`
from MultiStaging inner join StateCounty on CountyName=County and StateName=State; 
;
# Why is WinterTavg being read in as an INT?
#SELECT `WinterTavg` from MultiStaging;

## Population Table - Where is population 60+ information?
INSERT INTO `CovidifyUSA`.`Population`
(`CountyFKey`, `Year`, `TotalPopulation`)
SELECT `CountyKey`, @year16, `TotalPopulation`
from MultiStaging inner join StateCounty on CountyName=County and StateName=State; 

## Update Latitude/Longitude in County table

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`LongLatCounty` (
  `CountyFKey` INT,
  `Longitude` VARCHAR(45),
  `Latitude` VARCHAR(45)
  )
ENGINE = InnoDB;
INSERT LongLatCounty SELECT `CountyKey`, `Longitude`, `Latitude` from MultiStaging 
inner join StateCounty on CountyName=County and StateName=State;

Update `CovidifyUSA`.`County` 
Inner Join LongLatCounty on CountyKey=CountyFKey
SET County.Longitude = LongLatCounty.Longitude, County.Latitude = LongLatCounty.Latitude; 

## State Hospital Data
# Do we need the short term acute care hospitals?
-- `StateHospitalDataKey` INT NOT NULL AUTO_INCREMENT,
--  `StateFKey` INT NOT NULL, ## peopleperhospitalperstate (2019)
--   `Year` YEAR NULL,
--   `NumberOfHospitals` VARCHAR(45) NULL, ## peopleperhospitalperstate (2019)
--   `NumberOfHospitalEmployees` VARCHAR(45) NULL, ## peopleperhospitalperstate (2019)

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateHospitalStage` (
  `StateName` TEXT,
  `NumberOfHospitals` VARCHAR(45),
  `NumberOfHospitalEmployees` VARCHAR(45)
  )
ENGINE = InnoDB;
  
LOAD DATA INFILE './PeoplePerHospitalPerState2019.csv' 
INTO TABLE `CovidifyUSA`.`StateHospitalStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 2 ROWS
(@StateName, @dummy, @numhospitals, @dummy, @numemployees, @dummy)
set `StateName`=@StateName,`NumberOfHospitals`=@numhospitals, `NumberOfHospitalEmployees`=@numemployees;

SET @year19 = 2019;
INSERT INTO `CovidifyUSA`.`StateHospitalData` 
(`StateFKey`,  `NumberOfHospitals`, `NumberOfHospitalEmployees`, `Year`)
SELECT `StateKey`,`NumberOfHospitals`, `NumberOfHospitalEmployees` ,@year19
from State Inner Join StateHospitalStage on State.StateName=StateHospitalStage.StateName;

## County Hospital Data
-- `CountyFKey` INT NOT NULL,
--   `Year` YEAR NULL, #2020
--   `ICUBeds` INT NULL,
# match by state and county like before

# How do we reconcile different fields in StatevsCounty Hospital Data Tables
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CountyHospitalStage` (
  `StateName` TEXT,
  `CountyName` TEXT,
  `ICUBeds` INT,
  `PopulationTotal` INT,
  `Population60plus` INT,
  `Population60percent` DECIMAL
  )
ENGINE = InnoDB;
LOAD DATA INFILE './ICUBedsByCounty2020.csv' 
INTO TABLE `CovidifyUSA`.`CountyHospitalStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@StateName, @CountyName, @ICUBeds, @totalPopulation, @population60, @percpopulation60, @dummy)
set `StateName`=@StateName, `CountyName`=@CountyName, `ICUBeds`=@ICUBeds, 
`PopulationTotal`=@totalPopulation, `Population60plus`=@population60, 
`Population60percent`=@percpopulation60;

SET @year20 = 2020;
INSERT INTO `CovidifyUSA`.`CountyHospitalData` 
(`CountyFKey`,  `ICUBeds`, `Year`)
SELECT `CountyKey`, `ICUBeds`,@year20
from CountyHospitalStage inner join StateCounty 
on StateCounty.CountyName=CountyHospitalStage.CountyName 
and StateCounty.StateName=CountyHospitalStage.StateName;

SELECT COUNT(*) FROM CountyHospitalData; #3143 in csv, 2960 here
# Possible - check if new counties in each new read in csv file and add first!

## Population update from stage. add 2020 totals, add 60+
## Mortality Rates
## Covid By Race

SET SQL_SAFE_UPDATES = 0;
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`GovernorsDataStaging` (
  `State` TEXT,
  `Governor` TEXT,
  `Date of Birth` TEXT,
  `Date of Inauguration` varchar(400),
  `Time in Office` varchar(400),
  `Party` TEXT
  )
ENGINE = InnoDB;

LOAD DATA INFILE './StateGovernorsData.csv' 
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



