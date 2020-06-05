USE `CovidifyUSA`;
LOAD DATA INFILE './state_fips.csv' INTO TABLE `CovidifyUSA`.`State` FIELDS TERMINATED BY ',' ENCLOSED BY '"'
LINES TERMINATED BY '\n' IGNORE 1 ROWS
(@StateName,@PostalCode, @Fips)
set `StateName`=@StateName,`StateFIPS`=@Fips;