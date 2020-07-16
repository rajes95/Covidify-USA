USE `CovidifyUSA`;

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`NasdaqStage` (
  `Date` DATE,
  `AdjClose` DECIMAL(10,2)
  )
ENGINE = InnoDB;
LOAD DATA INFILE '/var/lib/mysql-files/nasdaq_apr2019-apr2020.csv' 
INTO TABLE `CovidifyUSA`.`NasdaqStage` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@date,@high,@low,@open,@close,@volume,@adjClose)
set `Date`=@date,`AdjClose`=@adjClose;


select * from NasdaqStage;