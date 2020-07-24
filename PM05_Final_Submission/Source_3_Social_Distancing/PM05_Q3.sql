USE `CovidifyUSA`;
DROP TABLE IF EXISTS `CovidifyUSA`.`SocialDistanceStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`SocialDistanceScore`;

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`SocialDistanceStage` (
  `State` TEXT,
  `Reopening` TEXT,
  `StayAtHome` TEXT,
  `QuarForTravelers` TEXT,
  `NonEssentialBus` TEXT,
  `LargeGatherings` TEXT, 
  `RestaurantLimit` TEXT,
  `BarClosed` TEXT,
  `MaskRequirement` TEXT
  );

LOAD DATA INFILE './social_distancing_state_data.csv' 
INTO TABLE `CovidifyUSA`.`SocialDistanceStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 4 ROWS
(@state,@reopen,@stayhome,@quar,@noness,@gather,@restlim,@bars,@masks,@dummy,@dummy,@dummy)
SET `State`=@state,`Reopening`=@reopen,`StayAtHome`=@stayhome,`QuarForTravelers`=@quar,
`NonEssentialBus`=@noness,`LargeGatherings`=@gather,`RestaurantLimit`=@restlim,
`BarClosed`=@bars,`MaskRequirement`=@masks;

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`SocialDistanceScore` (
  `State` TEXT,
  `Score` INT,
  `NewCovidCases` INT
  );
  
# Quarantine for travelers sparse policy entry

INSERT INTO `CovidifyUSA`.`SocialDistanceScore` 
(`State`, `NewCovidCases`,`Score`)
SELECT State, newcase, totScore FROM (
(Select newcase, StateName FROM (
SELECT case15-case14 as newcase, a1.StateFKey FROM(
SELECT SUM(CovidCases) as case15, StateFKey From CovidByDate JOIN County ON County.CountyKey=CovidByDate.CountyFKey
WHERE Date='2020-07-15' GROUP BY StateFKey) as a1
INNER JOIN(
SELECT SUM(CovidCases) as case14, StateFKey From CovidByDate JOIN County ON County.CountyKey=CovidByDate.CountyFKey
WHERE Date='2020-07-14' GROUP BY StateFKey) as a2 ON a1.StateFKey=a2.StateFKey) as a3
INNER JOIN State On a3.StateFKey=State.StateKey) as a4

INNER JOIN

(SELECT t1.State, (StayHomeScore+ReopenScore+BusinessScore+
GatheringScore+RestaurantScore+BarScore+MaskScore) as totScore FROM(
(SELECT State, CASE
	WHEN StayAtHome = 'Lifted'
		THEN 3
	WHEN StayAtHome = 'Rolled Back to High Risk Groups'
		THEN 2
	WHEN StayAtHome = 'Statewide'
		THEN 1
	ELSE 0
    END as StayHomeScore
FROM SocialDistanceStage) as t1
JOIN
(SELECT State, CASE
	WHEN Reopening = 'Paused'
		THEN 1
	WHEN Reopening = 'Proceeding with Reopening'
		THEN 2
	WHEN Reopening = 'New Restrictions Imposed'
		THEN 3
	WHEN Reopening = 'Reopened'
		THEN 4
	ELSE
		0
	END as ReopenScore
FROM SocialDistanceStage) as t2 ON t1.State=t2.State)
JOIN 
(SELECT State, CASE
	WHEN NonEssentialBus = 'All Non-Essential Businesses Permitted to Reopen with Reduced Capacity'
		THEN 4
	WHEN NonEssentialBus = 'All Non-Essential Businesses Permitted to Reopen'
		THEN 5
	WHEN NonEssentialBus = 'New Business Closures or Limits'
		THEN 1
	WHEN NonEssentialBus = 'Some Non-Essential Businesses Permitted to Reopen with Reduced Capacity'
		THEN 2
	WHEN NonEssentialBus = 'Some Non-Essential Businesses Permitted to Reopen'
		THEN 3
	ELSE
		0
	END as BusinessScore
FROM SocialDistanceStage) as t3 ON t3.State=t2.State
JOIN(
SELECT State, CASE
	WHEN LargeGatherings = 'Lifted'
		THEN 6
	WHEN LargeGatherings = 'New Limit on Large Gatherings in Place'
		THEN 5
	WHEN LargeGatherings = 'All Gatherings Prohibited'
		THEN 1
	WHEN LargeGatherings = 'Expanded to New Limit Below 25' OR LargeGatherings = 'Expanded to New Limit of 25'
		THEN 3
	WHEN LargeGatherings = 'Expanded to New Limit Above 25'
		THEN 4
	WHEN LargeGatherings = '>10 People Prohibited'
		THEN 2
	ELSE
		0
	END as GatheringScore
FROM SocialDistanceStage) as t4 ON t4.State = t3.State
JOIN(
SELECT State, CASE
	WHEN RestaurantLimit = 'Reopened to Dine-in Service'
		THEN 3
	WHEN RestaurantLimit = 'New Capacity Limits' OR RestaurantLimit = 'Reopened to Dine-in Service with Capacity Limits'
		THEN 2
	WHEN RestaurantLimit = 'Newly Closed to Dine-in Service'
		THEN 1
	ELSE
		0
	END as RestaurantScore
FROM SocialDistanceStage) as t5 ON t5.State=t4.State
JOIN(
SELECT State, CASE
	WHEN BarClosed = 'Reopened'
		THEN 4
	WHEN BarClosed = 'Newly Closed'
		THEN 2
	WHEN BarClosed = 'Closed'
		THEN 1
	WHEN BarClosed = 'New Service Limits'
		THEN 3
	ELSE
		0
	END as BarScore
FROM SocialDistanceStage) as t6 ON t6.State = t5.State
JOIN(
SELECT State, CASE
	WHEN MaskRequirement = 'Required for General Public'
		THEN 1
	WHEN MaskRequirement = 'Required for Certain Employees' OR MaskRequirement= 'Required for Certain Employees; Allows Local Officials to Require for General Public'
		THEN 2
	WHEN MaskRequirement = 'Allows Local Officals to Require for General Public'
		THEN 2
	ELSE
		0
	END as MaskScore
FROM SocialDistanceStage) as t7 ON t7.State = t6.State) as t8 ON a4.StateName=t8.State);

SELECT * From SocialDistanceScore
INTO OUTFILE './Q3_SocialDistance.csv'
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n';

DROP TABLE IF EXISTS `CovidifyUSA`.`SocialDistanceStage`;
DROP TABLE IF EXISTS `CovidifyUSA`.`SocialDistanceScore`;