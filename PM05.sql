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




DROP TABLE IF EXISTS `CovidifyUSA`.`NasdaqStage`;
