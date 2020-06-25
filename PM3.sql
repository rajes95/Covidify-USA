USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;

#1. How many new Covid cases has Massachussetts had per day over time
SELECT `Covid1`.`Date`, sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0) AS `NumOfNewCases`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidBYDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
WHERE `County`.`StateFKey` = 21
GROUP BY `Covid1`.`Date`
ORDER BY `Covid1`.`Date`;

#8. What is the Case Fatality Rate of Covid-19 by State?
SELECT `State`.`StateName`, (sum(`Covid1`.`CovidDeaths`) - ifnull(sum(`Covid2`.`CovidDeaths`), 0)) / (sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidBYDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
JOIN `State` ON `County`.`StateFKey` = `State`.`StateKey`
GROUP BY `State`.`StateName`;