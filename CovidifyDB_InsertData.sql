USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;

DROP TABLE IF EXISTS `CovidifyUSA`.`GovernorsDataStaging`;
DROP TABLE IF EXISTS `CovidifyUSA`.`MultiStaging`;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateCounty`;
DROP TABLE IF EXISTS `CovidifyUSA`.`LongLatCounty`;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateHospitalStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CountyHospitalStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CovidStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CovidRaceStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`MortalityStage`;

# /var/lib/mysql-files/...
LOAD DATA INFILE './state_fips.csv' 
INTO TABLE `CovidifyUSA`.`State` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@StateName,@PostalCode, @Fips)
set `StateName`=@StateName,`StateFIPS`=@Fips;

LOAD DATA INFILE './county_fips.csv'
IGNORE INTO TABLE `CovidifyUSA`.`County`
FIELDS TERMINATED BY ',' ENCLOSED BY '"' ESCAPED BY '"'
LINES TERMINATED BY '\n'
(@fips,@county,@state)
set `StateFKey` = (SELECT `StateKey` FROM `CovidifyUSA`.`State` where `StateName`=@state), 
`CountyFIPS`=@fips, `CountyName`=@county;

# find the county key based on county name and state name
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateCounty` (
  `StateKey` INT,
  `StateName` TEXT,
  `CountyKey` INT,
  `CountyName` TEXT,
  `CountyFIPS` VARCHAR(45)
  )
ENGINE = InnoDB;
INSERT StateCounty SELECT StateKey, StateName, CountyKey, CountyName, CountyFIPS from State 
inner join `CovidifyUSA`.`County` on StateFKey=StateKey;

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidStage` (
  `CountyName` VARCHAR(45),
  `State` VARCHAR(45),
  `Date` VARCHAR(45),
  `FIPS` VARCHAR(45),
  `CovidDeaths` INT,
  `CovidCases` INT
  )
ENGINE = InnoDB;
LOAD DATA INFILE './covid-us-counties.csv' 
INTO TABLE `CovidifyUSA`.`CovidStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@date,@county,@state,@fips,@cases,@deaths)
set `CountyName`=@county,`State`=@state,`Date`=@date, `FIPS`=@fips, `CovidDeaths`=@deaths, `CovidCases`=@cases;

INSERT INTO `CovidifyUSA`.`CovidByDate`
(`CountyFKey`, `Date`, `CovidDeaths`, `CovidCases`)
SELECT `CountyKey`, `Date`, `CovidDeaths`, `CovidCases`
#from CovidStage inner join County on CovidStage.CountyName=County.CountyName; 
from CovidStage inner join StateCounty on StateCounty.CountyFIPS=CovidStage.FIPS;

SET SESSION sql_mode = '';
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`MultiStaging`(
	## PresElection table
	`County` TEXT, # County - read in and get rid of comma, get rid of 'county'
    `FIPS` VARCHAR(45),
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
set `County`=@County, `FIPS`=@Fips, `State`=@State,`DemocratsPercent08`=@Dem08Frac, `DemocratsPercent12`=@Dem12Frac, `DemocratsPercent16`=@Dem16Frac, 
`RepublicansPercent08`=@Rep08Frac, `RepublicansPercent12`=@Rep12Frac, `RepublicansPercent16`=@Rep16Frac, 
 `OtherPercent08`=@Other08Frac, `OtherPercent12`=@Other12Frac, `OtherPercent16`=@Other16Frac, 
 `White`=@White, `AfricanAmerican`=@AfricanAmerican, `Latino`=@Latino, `NativeAmerican`=@NativeAmerican, `AsianAmerican`=@AsianAmerican,
 `OtherEthnicity`=@Other, `PovertyRate`=@PovertyRate, `MedianAge`=@MedianAge, `MedianEarnings`=@MedianEarnings10,
 `Elevation`=@Elevation, `WinterPrcp`=@WinterPrcp, `SummerPrcp`=@SummerPrcp, `SpringPrcp`=@SpringPrcp, `AutumnPrcp`=@AutumnPrcp,
 `WinterTavg`=@WinterTavg, `SummerTavg`=@SummerTavg, `SpringTavg`=@SpringTavg, `AutumnTavg`=@AutumnTavg, `TotalPopulation`=@TotalPopulation,
 `Longitude`=@longitude, `Latitude`=@latitude;

UPDATE `CovidifyUSA`.`MultiStaging` SET County = SUBSTRING_INDEX(County, ',', 1);
UPDATE `CovidifyUSA`.`MultiStaging` SET County = SUBSTRING_INDEX(County, ' County', 1);

# Election table

SET @year08= 2008;
INSERT INTO `CovidifyUSA`.`PresidentialElectionVotePercentages` 
(`CountyFKey`,  `DemocratsPercent`, `RepublicansPercent`, `OtherPercent`, `Year`)
SELECT `CountyKey`,`DemocratsPercent08`, `RepublicansPercent08`, `OtherPercent08`,@year08
from MultiStaging inner join StateCounty on StateCounty.CountyFIPS=MultiStaging.FIPS;

SET @year12= 2012;
INSERT INTO `CovidifyUSA`.`PresidentialElectionVotePercentages` 
(`CountyFKey`,  `DemocratsPercent`, `RepublicansPercent`, `OtherPercent`, `Year`)
SELECT `CountyKey`,`DemocratsPercent12`, `RepublicansPercent12`, `OtherPercent12`,@year12
from MultiStaging inner join StateCounty on StateCounty.CountyFIPS=MultiStaging.FIPS;

SET @year16= 2016;
INSERT INTO `CovidifyUSA`.`PresidentialElectionVotePercentages` 
(`CountyFKey`,  `DemocratsPercent`, `RepublicansPercent`, `OtherPercent`, `Year`)
SELECT `CountyKey`,`DemocratsPercent16`, `RepublicansPercent16`, `OtherPercent16`,@year16
from MultiStaging inner join StateCounty on StateCounty.CountyFIPS=MultiStaging.FIPS;

## Demographics table
INSERT INTO `CovidifyUSA`.`Demographics` 
(`CountyFKey`, `Year`, `White`, `AfricanAmerican`, `Latino`, `NativeAmerican`,
  `AsianAmerican`, `OtherEthnicity`, `PovertyRate`, `MedianAge`, `MedianEarnings`) 
SELECT `CountyKey`, @year16,`White`, `AfricanAmerican`, `Latino`, `NativeAmerican`, 
`AsianAmerican`, `OtherEthnicity`, `PovertyRate`, `MedianAge`, `MedianEarnings`
from MultiStaging inner join StateCounty on StateCounty.CountyFIPS=MultiStaging.FIPS;

## Climate Table
INSERT INTO `CovidifyUSA`.`Climate` 
(`CountyFKey`, `Year`, `Elevation`, `WinterPrcp`, `SummerPrcp`, `SpringPrcp`,
  `AutumnPrcp`, `WinterTavg`, `SummerTavg`, `SpringTavg`, `AutumnTavg`)
SELECT `CountyKey`, @year16, `Elevation`, `WinterPrcp`, `SummerPrcp`, `SpringPrcp`,
  `AutumnPrcp`, `WinterTavg`, `SummerTavg`, `SpringTavg`, `AutumnTavg`
from MultiStaging inner join StateCounty on StateCounty.CountyFIPS=MultiStaging.FIPS; 
;
# Why is WinterTavg being read in as an INT?
#SELECT `WinterTavg` from MultiStaging;

## Population Table - Where is population 60+ information?
INSERT INTO `CovidifyUSA`.`Population`
(`CountyFKey`, `Year`, `TotalPopulation`)
SELECT `CountyKey`, @year16, `TotalPopulation`
from MultiStaging inner join StateCounty on StateCounty.CountyFIPS=MultiStaging.FIPS; 

## Update Latitude/Longitude in County table

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`LongLatCounty` (
  `CountyFKey` INT,
  `Longitude` VARCHAR(45),
  `Latitude` VARCHAR(45)
  )
ENGINE = InnoDB;
INSERT LongLatCounty SELECT `CountyKey`, `Longitude`, `Latitude` from MultiStaging 
inner join StateCounty on StateCounty.CountyFIPS=MultiStaging.FIPS;

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
  `FIPS` VARCHAR(45),
  `ICUBeds` INT,
  `PopulationTotal` INT,
  `Population60plus` INT,
  `Population60percent` DECIMAL
  )
ENGINE = InnoDB;
LOAD DATA INFILE './ICUBedsByCounty2020.csv'
INTO TABLE `CovidifyUSA`.`CountyHospitalStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@StateName, @CountyName,@fips, @ICUBeds, @totalPopulation, @population60, @percpopulation60, @dummy)
set `StateName`=@StateName, `CountyName`=@CountyName,`FIPS`=@fips, `ICUBeds`=@ICUBeds, 
`PopulationTotal`=@totalPopulation, `Population60plus`=@population60, 
`Population60percent`=@percpopulation60;

SET @year20 = 2020;
INSERT INTO `CovidifyUSA`.`CountyHospitalData` 
(`CountyFKey`,  `ICUBeds`, `Year`)
SELECT `CountyKey`, `ICUBeds`,@year20
from CountyHospitalStage inner join StateCounty 
on StateCounty.CountyFIPS=CountyHospitalStage.FIPS; 

#SELECT COUNT(*) FROM CountyHospitalData; #3143 in csv, 2960 here
# Possible - check if new counties in each new read in csv file and add first!

## Population update from stage. add 2020 totals, add 60+
#INSERT INTO `CovidifyUSA`.`Population`
#SELECT * from Population ;

INSERT INTO `CovidifyUSA`.`Population`(`CountyFKey`, `TotalPopulation`, `Population60Plus`, `Year`)
SELECT `CountyKey`, `PopulationTotal`, `Population60plus`,@year20
from CountyHospitalStage inner join StateCounty 
on StateCounty.CountyFIPS=CountyHospitalStage.FIPS;

## Mortality Rates
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`MortalityStage` (
  `CountyName` TEXT,
  `FIPS` VARCHAR(45),
  `Category` TEXT,
  `MRate80` DECIMAL(5, 2),
  `MRate85` DECIMAL(5, 2),
  `MRate90` DECIMAL(5, 2),
  `MRate95` DECIMAL(5, 2),
  `MRate00` DECIMAL(5, 2),
  `MRate05` DECIMAL(5, 2),
  `MRate10` DECIMAL(5, 2),
  `MRate14` DECIMAL(5, 2)
  );
LOAD DATA INFILE './MortalityByCounty.csv' 
INTO TABLE `CovidifyUSA`.`MortalityStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 2 ROWS
(@Location,	@FIPS, @Category, @MRate80, @dummy, @dummy, @MRate85, @dummy, @dummy, @MRate90,	@dummy, @dummy,	
@MRate95, @dummy, @dummy, @MRate00, @dummy, @dummy,	@MRate05, @dummy, @dummy, @MRate10, @dummy,	@dummy,
@MRate14, @dummy, @dummy, @dummy, @dummy, @dummy)
set `CountyName`=@Location, `FIPS`=@FIPS, `Category`=@Category, 
`MRate80`=@MRate80, `MRate85`=@MRate85,`MRate90`=@MRate90, `MRate95`=@MRate95,
`MRate00`=@MRate00, `MRate05`=@MRate05, `MRate10`=@MRate10, `MRate14`=@MRate85;

#ALTER TABLE `MortalityStage` CHANGE `FIPS` `FIPS` VARCHAR(45); #changes type
UPDATE MortalityStage SET `FIPS`=LPAD(`FIPS`, 5, '0'); #pads everything
UPDATE State SET StateFIPS=LPAD(StateFIPS,2,0);

-- select MortalityStage.CountyName, CountyKey, MortalityStage.FIPS,Category,MRate80,MRate85,MRate90,MRate95,MRate00,MRate05,MRate10,MRate14

SET @year80 = 1980;
SET @year85 = 1985;
SET @year90 = 1990;
SET @year95 = 1995;
SET @year00 = 2000;
SET @year05 = 2005;
SET @year10 = 2010;
SET @year14 = 2014;

# join based on FIPS
# TODO: may want to use a for loop to to fill out our table else will have 9yrs*7categories select statements
#SELECT * from MortalityStage WHERE Category='Cardiovascular diseases';
#SELECT * from County WHERE CountyFIPS=5137;
INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year80 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate80 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate80 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate80 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate80 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate80 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate80 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate80 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year85 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate85 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate85 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate85 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate85 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate85 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate85 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate85 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year90 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate90 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate90 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate90 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate90 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate90 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate90 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate90 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year95 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate95 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate95 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate95 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate95 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate95 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate95 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate95 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year00 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate00 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate00 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate00 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate00 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate00 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate00 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate00 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year05 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate05 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate05 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate05 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate05 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate05 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate05 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate05 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year10 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate10 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate10 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate10 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate10 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate10 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate10 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate10 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

INSERT INTO `CovidifyUSA`.`MortalityRates`(`CountyFKey`,`Year`,`NeonatalDisordersMortalityRate`,
`HIVAIDSandTBMortalityRate`,`DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,`ChronicRespiratoryDiseasesMortalityRate`,
`LiverDiseaseMortalityRate`,`NutritionalDeficienciesMortalityRate`,`CardiovascularDiseasesMortalityRate`)
SELECT
	CountyKey as CountyFKey, @year14 as `Year`,
	SUM(CASE WHEN Category="Neonatal disorders" THEN MRate14 END) as `NeonatalDisordersMortalityRate`,
	SUM(CASE WHEN Category="HIV/AIDS and tuberculosis" THEN MRate14 END) as `HIVAIDSandTBMortalityRate`,
    SUM(CASE WHEN Category="Diabetes, urogenital, blood, and endocrine diseases" THEN MRate14 END) as `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Chronic respiratory diseases" THEN MRate14 END) as `ChronicRespiratoryDiseasesMortalityRate`,
	SUM(CASE WHEN Category="Cirrhosis and other chronic liver diseases" THEN MRate14 END) as `LiverDiseaseMortalityRate`,
    SUM(CASE WHEN Category="Nutritional deficiencies" THEN MRate14 END) as `NutritionalDeficienciesMortalityRate`,
    SUM(CASE WHEN Category="Cardiovascular diseases" THEN MRate14 END) as `CardiovascularDiseasesMortalityRate`
from MortalityStage inner join StateCounty on CountyFIPS = FIPS
where Category="Neonatal disorders" or Category="HIV/AIDS and tuberculosis" or Category="Diabetes, urogenital, blood, and endocrine diseases"
		or Category="Chronic respiratory diseases" or Category="Cirrhosis and other chronic liver diseases" or Category="Nutritional deficiencies"
		or Category="Cardiovascular diseases"
group by FIPS
order by FIPS;

select * from MortalityRates;

## Covid By Race
-- `StateFKey` INT NOT NULL,
--   `Race` ENUM('White', 'Black', 'Hispanic', 'Asian', 'Multiracial', 'NHPI', 'Multi', 'Other', 'Unknown') NULL,
--   `Positive` INT NULL,
--   `Negative` INT NULL,
--   `Death` INT NULL,
--   `Date` DATE NULL,

# States in this csv use two letter format- is this in state csv? Will join on this
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidRaceStage` (
  `State` VARCHAR(45), `Datetime` TEXT,
  `PosWhite` INT, `PosBlack` INT, `PosHispanic` INT, `PosAsian` INT, 
   `PosAIAN` INT,`PosNHPI` INT, `PosMulti` INT, `PosOther` INT, `PosUnknown` INT,
  `NegWhite` INT, `NegBlack` INT, `NegHispanic` INT, `NegAsian` INT, 
   `NegAIAN` INT, `NegNHPI` INT, `NegMulti` INT, `NegOther` INT, `NegUnknown` INT,
  `DeathWhite` INT, `DeathBlack` INT, `DeathHispanic` INT, `DeathAsian` INT, 
   `DeathAIAN` INT, `DeathNHPI` INT, `DeathMulti` INT, `DeathOther` INT, `DeathUnknown` INT
  );
LOAD DATA INFILE './Race Data Entry.csv' 
INTO TABLE `CovidifyUSA`.`CovidRaceStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 3 ROWS
(@dummy, @state, @dummy, @dummy, @datets, @dummy, @dummy,@dummy, @poswhite, @posblack, 
@poshispanic, @posasian, @posAIAN, @posNHPI, @posmulti, @posother, @posunknown, @dummy, 
@dummy, @dummy, @dummy, @dummy, @dummy, @deathwhite, @deathblack, 
@deathhispanic, @deathasian, @deathAIAN, @deathNHPI, @deathmulti, @deathother, @deathunknown, @dummy, 
@dummy, @dummy, @dummy, @dummy, @negwhite, @negblack, @neghispanic, @negasian, @negAIAN,
@negNHPI, @negmulti, @negother, @negunknown, @dummy, @dummy, @dummy, @dummy, @dummy)
set `State`=@state, `Datetime`=@datets, `PosWhite`=@poswhite, `PosBlack`=@posblack,
 `PosHispanic`=@poshispanic, `PosAsian`=@posasian, `PosMulti`=@posmulti,
   `PosAIAN`=@posAIAN,`PosNHPI`=@posNHPI, `PosMulti`=@posmulti, `PosOther`=@posother,
   `PosUnknown`=@posunknown, `NegWhite`=@negwhite, `NegBlack`=@negblack, 
   `NegHispanic`=@neghispanic, `NegAsian`=@negasian, `NegMulti`=@negmulti,
   `NegAIAN`=@negaian, `NegNHPI`=@negnhpi, `NegOther`=@negother, `NegUnknown`=@negunknown,
  `DeathWhite`=@deathwhite, `DeathBlack`=@deathblack, `DeathHispanic`=@deathhispanic,
  `DeathAsian`=@deathasian, `DeathMulti`=@deathmulti, `DeathAIAN`=@deathAIAN, 
  `DeathNHPI`=@deathNHPI, `DeathOther`=@deathother, `DeathUnknown`=@deathunknown;

# TODO: may want to use a for loop to to fill out our table else will have too many select statements
# SELECT * from CovidRaceStage;
																		
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
DROP TABLE IF EXISTS `CovidifyUSA`.`MultiStaging`;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateCounty`;
DROP TABLE IF EXISTS `CovidifyUSA`.`LongLatCounty`;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateHospitalStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CountyHospitalStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CovidStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`CovidRaceStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`MortalityStage`;
  
SELECT
  (SELECT COUNT(*) FROM State) as N_State, 
  (SELECT COUNT(*) FROM County) as N_County,
  (SELECT COUNT(*) FROM StateGovernor) as N_Governor,
  (SELECT COUNT(*) FROM CovidByDate) as N_Covid,
  (SELECT COUNT(*) FROM PresidentialElectionVotePercentages) as N_Election,
  (SELECT COUNT(*) FROM Demographics) as N_Demographic,
  (SELECT COUNT(*) FROM Climate) as N_Climate,
  (SELECT COUNT(*) FROM Population) as N_Population,
  (SELECT COUNT(*) FROM StateHospitalData) as N_StHospital,
  (SELECT COUNT(*) FROM CountyHospitalData) as N_CtHospital,
  (SELECT COUNT(*) FROM MortalityRates) as N_MortalityRts
  ;


