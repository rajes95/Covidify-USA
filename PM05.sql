USE `CovidifyUSA`;
DROP TABLE IF EXISTS `CovidifyUSA`.`NasdaqStage`;

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

# Should we include a line exporting this select ^^ statement to a CSV?

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


SELECT * FROM UnemploymentStage;


#WORK IN PROGRESS...
select `month`, StateKey, PostalCode, StatePop, March2020,April2020,OverTheMonthChange, sum(NumOfNewCases) from (SELECT Month(CovidDate) as `month`, StateKey, PostalCode, StatePop, March2020,April2020,OverTheMonthChange,NumOfNewCases FROM (SELECT * FROM 
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
where MONTH(CovidDate) = 3 or Month(CovidDate)=4
order by `month`, StateKey) marchAprilCovidUnemployment
group by `month`, StateKey;

DROP TABLE IF EXISTS `CovidifyUSA`.`NasdaqStage`;
Drop table if exists `CovidifyUSA`.UnemploymentStage;
