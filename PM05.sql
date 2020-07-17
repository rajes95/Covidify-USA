USE `CovidifyUSA`;
DROP TABLE IF EXISTS `CovidifyUSA`.`NasdaqStage`;

#1
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`NasdaqStage` (
  `Date` DATE,
  `AdjClose` DECIMAL(10,2)
  )
ENGINE = INNODB;
LOAD DATA INFILE '/var/lib/mysql-files/nasdaq_apr2019-apr2020.csv' 
INTO TABLE `CovidifyUSA`.`NasdaqStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@date,@high,@low,@open,@close,@volume,@adjClose)
SET `Date`=@date,`AdjClose`=@adjClose;

SELECT `Date`, AdjClose, NumOfNewCases, NumOfNewDeaths FROM NasdaqStage INNER JOIN
(SELECT `Covid1`.`Date` AS CovidDate, SUM(`Covid1`.`CovidCases` - IFNULL((`Covid2`.`CovidCases`), 0)) AS `NumOfNewCases`, SUM(`Covid1`.`CovidDeaths` - IFNULL((`Covid2`.`CovidDeaths`), 0)) AS `NumOfNewDeaths`
FROM `CovidByDate` AS `Covid1` LEFT JOIN `CovidByDate` AS `Covid2` 
		ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
        JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey`
GROUP BY `Covid1`.`Date`
ORDER BY `Covid1`.`Date`) newCases ON newCases.CovidDate=NasdaqStage.`Date`;

# Should we include a line exporting this ^^ select ^^ statement to a CSV?

#2
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`UnemploymentStage` (
  `State` VARCHAR(45),
  `March2020` DECIMAL(10,2),
  `April2020` DECIMAL(10,2),
  `OverTheMonthChange` DECIMAL(10,2)
  )
ENGINE = INNODB;
LOAD DATA INFILE '/var/lib/mysql-files/States_unemployment_March2020-April2020_season.csv' 
INTO TABLE `CovidifyUSA`.`UnemploymentStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@state,@marchRate,@aprilRate,@monthlyChange)
SET `State`=@state,`March2020`=@marchRate,`April2020`=@aprilRate,`OverTheMonthChange`=@monthlyChange;

# March vs April per state Unemployment and newCasesPer100000
SELECT PostalCode, `month`, 
	(CASE WHEN month = 3 THEN March2020 ELSE April2020 END) AS UnemploymentRate,
    ((SUM(NumOfNewCases)/StatePop) * 100000) AS newCasesPer100000 FROM 
    (SELECT MONTH(CovidDate) AS `month`, StateKey, PostalCode, StatePop, March2020, April2020, OverTheMonthChange, NumOfNewCases FROM 
    (SELECT * FROM 
	(SELECT PostalCode, StatePop, StateFKey FROM 
		(SELECT StateFKey, SUM(TotalPopulation) AS StatePop FROM 
			Population INNER JOIN County ON CountyFKey=CountyKey
			WHERE Population.`Year` = 2020
			GROUP BY StateFKey) StatePops 
		INNER JOIN State ON StateFKey=StateKey) StatePopPC
	INNER JOIN UnemploymentStage ON UnemploymentStage.State=StatePopPC.PostalCode) PopulationUnemployment 
		INNER JOIN (SELECT County.StateFKey AS StateKey, `Covid1`.`Date` AS CovidDate, SUM(`Covid1`.`CovidCases` - IFNULL((`Covid2`.`CovidCases`), 0)) AS `NumOfNewCases`
		FROM `CovidByDate` AS `Covid1` LEFT JOIN `CovidByDate` AS `Covid2` ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1
        AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
    JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey`
    GROUP BY `County`.StateFKey , `Covid1`.`Date`
    ORDER BY `County`.StateFKey , `Covid1`.`Date`) newStateCases ON PopulationUnemployment.StateFKey=newStateCases.StateKey
WHERE MONTH(CovidDate) = 3 OR MONTH(CovidDate)=4
ORDER BY `month`, StateKey) marchAprilCovidUnemployment
GROUP BY `month`, StateKey
ORDER BY PostalCode, `month`;

# increase in unemployment vs increase in CovidCasesPer100000 per state.
SELECT PostalCode, OverTheMonthChange AS IncreaseInUnemploymentMarchToApril, (SUM(newAprilCasesPer100000)-SUM(newMarchCasesPer100000)) AS increaseInCasesPer100000  FROM
	(SELECT PostalCode, OverTheMonthChange,	(CASE WHEN month = 3 THEN newCasesPer100000 ELSE 0 END) AS newMarchCasesPer100000,
    (CASE WHEN month = 4 THEN newCasesPer100000 ELSE 0 END) AS newAprilCasesPer100000 FROM (SELECT PostalCode, `month`,
    OverTheMonthChange, 
    ((SUM(NumOfNewCases)/StatePop) * 100000) AS newCasesPer100000 FROM 
    (SELECT MONTH(CovidDate) AS `month`, StateKey, PostalCode, StatePop, March2020, April2020, OverTheMonthChange, NumOfNewCases FROM 
    (SELECT * FROM 
	(SELECT PostalCode, StatePop, StateFKey FROM 
		(SELECT StateFKey, SUM(TotalPopulation) AS StatePop FROM 
			Population INNER JOIN County ON CountyFKey=CountyKey
			WHERE Population.`Year` = 2020
			GROUP BY StateFKey) StatePops 
		INNER JOIN State ON StateFKey=StateKey) StatePopPC
	INNER JOIN UnemploymentStage ON UnemploymentStage.State=StatePopPC.PostalCode) PopulationUnemployment 
		INNER JOIN (SELECT County.StateFKey AS StateKey, `Covid1`.`Date` AS CovidDate, SUM(`Covid1`.`CovidCases` - IFNULL((`Covid2`.`CovidCases`), 0)) AS `NumOfNewCases`
		FROM `CovidByDate` AS `Covid1` LEFT JOIN `CovidByDate` AS `Covid2` ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1
        AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
    JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey`
    GROUP BY `County`.StateFKey , `Covid1`.`Date`
    ORDER BY `County`.StateFKey , `Covid1`.`Date`) newStateCases ON PopulationUnemployment.StateFKey=newStateCases.StateKey
WHERE MONTH(CovidDate) = 3 OR MONTH(CovidDate)=4
ORDER BY `month`, StateKey) marchAprilCovidUnemployment
GROUP BY `month`, StateKey
ORDER BY PostalCode, `month`) tbl0)tbl1
GROUP BY PostalCode
ORDER BY IncreaseInUnemploymentMarchToApril DESC;


-- DROP TABLE IF EXISTS `CovidifyUSA`.`NasdaqStage`;
-- DROP TABLE IF EXISTS `CovidifyUSA`.UnemploymentStage; 
