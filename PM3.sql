USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;

#1. How many new Covid cases has Massachussetts had per day over time
SELECT `Covid1`.`Date`, sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0) `NumOfCases`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidBYDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
WHERE `County`.`StateFKey` = 21
GROUP BY `Covid1`.`Date`
ORDER BY `Covid1`.`Date`;

#8. Proportion of Deaths per Covid-19 cases in each state 
SELECT `State`.`StateName`, sum(`CovidByDate`.`CovidDeaths`) / sum(`CovidByDate`.`CovidCases`) * 100 AS `DeathsPerCase`
FROM `CovidByDate`
INNER JOIN `County` ON `CovidByDate`.`CountyFKey` = `County`.`CountyKey` 
INNER JOIN `State` ON `County`.`StateFKey` = `State`.`StateKey`
GROUP BY `State`.`StateName`