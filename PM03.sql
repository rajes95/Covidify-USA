USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;
# Rajesh Sakhamuru, Elise Jortberg, Ari Fleischer, Lily Bessette
# REAL TEAM SIX -> COVIDIFY-USA -> PM03

#1. How many new Covid cases has Massachussetts had per day over time?
SELECT County.StateFKey, `Covid1`.`Date`, sum(`Covid1`.`CovidCases` - ifnull((`Covid2`.`CovidCases`), 0)) AS `NumOfNewCases`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidByDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey`  
WHERE `County`.`StateFKey` = 21
GROUP BY `County`.StateFKey,`Covid1`.`Date`
ORDER BY `County`.StateFKey,`Covid1`.`Date`;

#2. COVID-19 new cases per day over time stratified by Governor Political affiliation (i.e. states with democratic vs republican governors).
select CovidDate, sum(newCasesInDemStates) as DemStatesNewCovidCases, sum(newCasesinRepStates) as RepStatesNewCovidCases
from (
	select  GovernorParty, CovidDate,
		sum(CASE WHEN GovernorParty="Democratic" THEN `NumOfNewCases` else 0 END) as NewCasesInDemStates,
		sum(CASE WHEN GovernorParty="Republican" THEN `NumOfNewCases` else 0 END) as NewCasesInRepStates
	from 
			(select StateKey, StateName, StateGovernor.`Year` as `Year`, Governor, GovernorParty
			from State inner join StateGovernor on StateKey=StateFKey
			where StateGovernor.`Year` = 2020) Govs
		inner join
			(SELECT County.StateFKey as StateKey, `Covid1`.`Date` as CovidDate, sum(`Covid1`.`CovidCases` - ifnull((`Covid2`.`CovidCases`), 0)) AS `NumOfNewCases`
			FROM `CovidByDate` AS `Covid1`
			LEFT JOIN `CovidByDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
			JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey`  
			GROUP BY `County`.StateFKey,`Covid1`.`Date`
			ORDER BY `County`.StateFKey,`Covid1`.`Date`) StateCovidDaily
		on Govs.StateKey=StateCovidDaily.StateKey
	where Govs.`Year` = year(CovidDate)
	group by CovidDate, GovernorParty
	order by GovernorParty, CovidDate) NewCasesPartySplit
group by CovidDate
order by CovidDate;

USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;

#3. What is the Case Fatality Rate of Covid-19 by State in descending order?
SELECT `State`.`StateName`, (sum(`Covid1`.`CovidDeaths`) - ifnull(sum(`Covid2`.`CovidDeaths`), 0)) / (sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidByDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
JOIN `State` ON `County`.`StateFKey` = `State`.`StateKey`
GROUP BY `State`.`StateName`
ORDER BY `CaseFatalityRate` DESC;

#4. What is the Case Fatality Rate of Covid-19 of the 50 Counties With the Most Total Confirmed Cases?
SELECT `County`.`CountyName`, `State`.`StateName`, sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0) AS `TotalNumOfCases`, (sum(`Covid1`.`CovidDeaths`) - ifnull(sum(`Covid2`.`CovidDeaths`), 0)) / (sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate`
FROM `CovidByDate` AS `Covid1`
LEFT JOIN `CovidBYDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
JOIN `State` ON `County`.`StateFKey` = `State`.`StateKey`
GROUP BY `County`.`CountyKey`
ORDER BY `TotalNumOfCases` DESC
LIMIT 50;

#5. How did States vote in 2016, sorted by Descending COVID19 Case Fatality Ratio?
-- TODO: We should move this question to after #8 probably since I am reusing the case fatality ratio code here.
Select StateName, CaseFatalityRate, ElectionYear, TotalPopulation, DemPerc, RepPerc, OtherPerc
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


#Q9: 
SELECT r.StateName, RespDiseasePer100k, FatalityRate FROM
	(SELECT StateName, RespDiseasePer100k FROM
	(Select CountyFKey, ChronicRespiratoryDiseasesMortalityRate as RespDiseasePer100k
	from MortalityRates where Year=2014 
	) as A
		JOIN StateCounty on CountyFKey=CountyKey GROUP BY StateName 
		ORDER BY RespDiseasePer100k DESC) AS r
	JOIN
	(SELECT `State`.`StateName`, (sum(`Covid1`.`CovidDeaths`) - ifnull(sum(`Covid2`.`CovidDeaths`), 0)) / (sum(`Covid1`.`CovidCases`) - ifnull(sum(`Covid2`.`CovidCases`), 0)) * 100  AS `FatalityRate`
	FROM `CovidByDate` AS `Covid1`
	LEFT JOIN `CovidByDate` AS `Covid2` ON  datediff(`Covid1`.`Date`, `Covid2`.`Date`) = 1 AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey`
	JOIN `County` ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
	JOIN `State` ON `County`.`StateFKey` = `State`.`StateKey`
	GROUP BY `State`.`StateName`
	ORDER BY `FatalityRate`) AS death ON death.StateName=r.StateName;
