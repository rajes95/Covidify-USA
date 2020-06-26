USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;

#1. How many new Covid cases has Massachussetts had per day over time?
SELECT `Covid1`.`Date`, sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0) AS `NumOfNewCases`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidByDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
WHERE `County`.`StateFKey` = 21
GROUP BY `Covid1`.`Date`
ORDER BY `Covid1`.`Date`;

#2. How did States vote in 2016, sorted by Descending Case Fatality Ratio?
-- TODO: We should move this question to after # 8 probably since we are reusing the case fatality ratio code.
select StateName, CaseFatalityRate, ElectionYear, TotalPopulation, DemPerc, RepPerc, OtherPerc
from (
		select StateKey, StateName, PresidentialElectionVotePercentages.`Year` as ElectionYear, Sum(TotalPopulation) as TotalPopulation,
				(Sum(TotalPopulation* DemocratsPercent)/(Sum(TotalPopulation* DemocratsPercent)+Sum(TotalPopulation* RepublicansPercent)+Sum(TotalPopulation* OtherPercent))) as DemPerc,
				(Sum(TotalPopulation* RepublicansPercent)/(Sum(TotalPopulation* DemocratsPercent)+Sum(TotalPopulation* RepublicansPercent)+Sum(TotalPopulation* OtherPercent))) as RepPerc,
				(Sum(TotalPopulation* OtherPercent)/(Sum(TotalPopulation* DemocratsPercent)+Sum(TotalPopulation* RepublicansPercent)+Sum(TotalPopulation* OtherPercent))) as OtherPerc
		from PresidentialElectionVotePercentages
			inner join County on PresidentialElectionVotePercentages.CountyFKey=County.CountyKey
			inner join State on StateFkey=StateKey
			inner join Population on Population.CountyFKey=County.CountyKey
		where PresidentialElectionVotePercentages.`Year`=2016 and Population.`Year`=2016
		group by StateName
		-- For some reason missing Alaska election data from dataset so this cleans that up
		having (DemPerc is not null and RepPerc is not null and OtherPerc is not null)) stateVotes2016 
	inner join ( 
		SELECT `State`.`StateKey` as StateKey,(sum(`Covid1`.`CovidDeaths`) - ifnull(sum(`Covid2`.`CovidDeaths`), 0)) / (sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate`
		FROM `CovidByDate` AS `Covid1`
		LEFT JOIN `CovidByDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
		JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
		JOIN `State` ON `County`.`StateFKey` = `State`.`StateKey`
		GROUP BY `State`.`StateName`
		ORDER BY `CaseFatalityRate` DESC) caseFatalities 
	on stateVotes2016.StateKey=caseFatalities.StateKey
order by `CaseFatalityRate` DESC;

#3. COVID-19 deaths per day over the last 3 months stratified by political region (i.e. states with democratic governors vs states with republican governors?)

#8. What is the Case Fatality Rate of Covid-19 by State in descending order?
SELECT `State`.`StateName`, (sum(`Covid1`.`CovidDeaths`) - ifnull(sum(`Covid2`.`CovidDeaths`), 0)) / (sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidByDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
JOIN `State` ON `County`.`StateFKey` = `State`.`StateKey`
GROUP BY `State`.`StateName`
ORDER BY `CaseFatalityRate` DESC;