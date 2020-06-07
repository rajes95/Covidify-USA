USE `CovidifyUSA`;
DROP TABLE IF EXISTS `CovidifyUSA`.`GovernorsDataStaging`;
DROP TABLE IF EXISTS `CovidifyUSA`.`MultiStaging`;

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

LOAD DATA INFILE './covid-us-counties.csv'
IGNORE INTO TABLE `CovidifyUSA`.`CovidByDate`
FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@date,@county,@state,@fips,@cases,@deaths)
set `CountyFKey`=@county, `Date`=@date, `CovidDeaths`=@deaths, `CovidCases`=@cases;


SET SESSION sql_mode = '';
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`MultiStaging`(
	## PresElection table
	`County` TEXT, # County - read in and get rid of comma, get rid of 'county'
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
set `County`=@County, `DemocratsPercent08`=@Dem08Frac, `DemocratsPercent12`=@Dem12Frac, `DemocratsPercent16`=@Dem16Frac, 
`RepublicansPercent08`=@Rep08Frac, `RepublicansPercent12`=@Rep12Frac, `RepublicansPercent16`=@Rep16Frac, 
 `OtherPercent08`=@Other08Frac, `OtherPercent12`=@Other12Frac, `OtherPercent16`=@Other16Frac, 
 `White`=@White, `AfricanAmerican`=@AfricanAmerican, `Latino`=@Latino, `NativeAmerican`=@NativeAmerican, `AsianAmerican`=@AsianAmerican,
 `OtherEthnicity`=@Other, `PovertyRate`=@PovertyRate, `MedianAge`=@MedianAge, `MedianEarnings`=@MedianEarnings10,
 `Elevation`=@Elevation, `WinterPrcp`=@WinterPrcp, `SummerPrcp`=@SummerPrcp, `SpringPrcp`=@SpringPrcp, `AutumnPrcp`=@AutumnPrcp,
 `WinterTavg`=@WinterTavg, `SummerTavg`=@SummerTavg, `SpringTavg`=@SpringTavg, `AutumnTavg`=@AutumnTavg, `TotalPopulation`=@TotalPopulation,
 `Longitude`=@longitude, `Latitude`=@latitude;

Select County from `CovidifyUSA`.`MultiStaging`;
-- SELECT DISTINCT County from MultiStaging;
Select @countynew := SUBSTRING_INDEX(County, ',', 1) from MultiStaging;
UPDATE MultiStaging SET `County`  = @countynew; # something wrong here
-- Select @countynew2 := SUBSTRING_INDEX(County, ' ', 1) from MultiStaging;

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



