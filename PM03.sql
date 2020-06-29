USE `CovidifyUSA`;
SET SQL_SAFE_UPDATES = 0;
# Rajesh Sakhamuru, Elise Jortberg, Ari Fleischer, Lily Bessette 
# REAL TEAM SIX -> COVIDIFY - USA -> PM03 


# 1. How many new Covid cases has Massachusetts had per day over time ? 
SELECT
    County.StateFKey,
    `Covid1`.`Date`,
    SUM(`Covid1`.`CovidCases` - IFNULL((`Covid2`.`CovidCases`), 0)) AS `NumOfNewCases` 
FROM
    `CovidByDate` AS `Covid1` 
    LEFT JOIN
        `CovidByDate` AS `Covid2` 
        ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
        AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
    JOIN
        `County` 
        ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
WHERE
    `County`.`StateFKey` = 21 
GROUP BY
    `County`.StateFKey,
    `Covid1`.`Date` 
ORDER BY
    `County`.StateFKey,
    `Covid1`.`Date`;
    
    
# 2. COVID - 19 new cases per day over time stratified by Governor Political affiliation (i.e. states with democratic vs republican governors). 
SELECT
    CovidDate,
    SUM(newCasesInDemStates) AS DemStatesNewCovidCases,
    SUM(newCasesinRepStates) AS RepStatesNewCovidCases 
FROM
    (
        SELECT
            GovernorParty,
            CovidDate,
            SUM(
            CASE
                WHEN
                    GovernorParty = 'Democratic' 
                THEN
                    `NumOfNewCases` 
                ELSE
                    0 
            END
) AS NewCasesInDemStates, SUM(
            CASE
                WHEN
                    GovernorParty = 'Republican' 
                THEN
                    `NumOfNewCases` 
                ELSE
                    0 
            END
) AS NewCasesInRepStates 
        FROM
            (
                SELECT
                    StateKey,
                    StateName,
                    StateGovernor.`Year` AS `Year`,
                    Governor,
                    GovernorParty 
                FROM
                    State 
                    INNER JOIN
                        StateGovernor 
                        ON StateKey = StateFKey 
                WHERE
                    StateGovernor.`Year` = 2020
            )
            Govs 
            INNER JOIN
                (
                    SELECT
                        County.StateFKey AS StateKey,
                        `Covid1`.`Date` AS CovidDate,
                        SUM(`Covid1`.`CovidCases` - IFNULL((`Covid2`.`CovidCases`), 0)) AS `NumOfNewCases` 
                    FROM
                        `CovidByDate` AS `Covid1` 
                        LEFT JOIN
                            `CovidByDate` AS `Covid2` 
                            ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
                            AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
                        JOIN
                            `County` 
                            ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
                    GROUP BY
                        `County`.StateFKey,
                        `Covid1`.`Date` 
                    ORDER BY
                        `County`.StateFKey,
                        `Covid1`.`Date`
                )
                StateCovidDaily 
                ON Govs.StateKey = StateCovidDaily.StateKey 
        WHERE
            Govs.`Year` = YEAR(CovidDate) 
        GROUP BY
            CovidDate,
            GovernorParty 
        ORDER BY
            GovernorParty,
            CovidDate
    )
    NewCasesPartySplit 
GROUP BY
    CovidDate 
ORDER BY
    CovidDate;
    
    
# 3. What is the Case Fatality Rate of Covid - 19 by State in descending order ? 
    SELECT
        `State`.`StateName`,
        (
            SUM(`Covid1`.`CovidDeaths`) - IFNULL(SUM(`Covid2`.`CovidDeaths`), 0)
        )
        / (SUM(`Covid1`.`CovidCases`) - IFNULL(SUM(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate` 
    FROM
        `CovidByDate` AS `Covid1` 
        LEFT JOIN
            `CovidByDate` AS `Covid2` 
            ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
            AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
        JOIN
            `County` 
            ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
        JOIN
            `State` 
            ON `County`.`StateFKey` = `State`.`StateKey` 
    GROUP BY
        `State`.`StateName` 
    ORDER BY
        `CaseFatalityRate` DESC;
        
        
# 4. What is the Case Fatality Rate of Covid - 19 of the 50 Counties With the Most Total Confirmed Cases ? 
    SELECT
        `County`.`CountyName`,
        `State`.`StateName`,
        SUM(`Covid1`.`CovidCases`) - IFNULL(SUM(`Covid2`.`CovidCases`), 0) AS `TotalNumOfCases`,
        (
            SUM(`Covid1`.`CovidDeaths`) - IFNULL(SUM(`Covid2`.`CovidDeaths`), 0)
        )
        / (SUM(`Covid1`.`CovidCases`) - IFNULL(SUM(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate` 
    FROM
        `CovidByDate` AS `Covid1` 
        LEFT JOIN
            `CovidByDate` AS `Covid2` 
            ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
            AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
        JOIN
            `County` 
            ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
        JOIN
            `State` 
            ON `County`.`StateFKey` = `State`.`StateKey` 
    GROUP BY
        `County`.`CountyKey` 
    ORDER BY
        `TotalNumOfCases` DESC LIMIT 50;
        
        
# 5. How did States vote in 2016, sorted by Descending COVID19 Case Fatality Ratio ? 
    SELECT
        StateName,
        CaseFatalityRate,
        ElectionYear,
        TotalPopulation,
        DemPerc,
        RepPerc,
        OtherPerc 
    FROM
        (
            SELECT
                StateKey,
                StateName,
                PresidentialElectionVotePercentages.`Year` AS ElectionYear,
                SUM(TotalPopulation) AS TotalPopulation,
                (
                    SUM(TotalPopulation * DemocratsPercent) / (SUM(TotalPopulation * DemocratsPercent) + SUM(TotalPopulation * RepublicansPercent) + SUM(TotalPopulation * OtherPercent))
                )
                AS DemPerc,
                (
                    SUM(TotalPopulation * RepublicansPercent) / (SUM(TotalPopulation * DemocratsPercent) + SUM(TotalPopulation * RepublicansPercent) + SUM(TotalPopulation * OtherPercent))
                )
                AS RepPerc,
                (
                    SUM(TotalPopulation * OtherPercent) / (SUM(TotalPopulation * DemocratsPercent) + SUM(TotalPopulation * RepublicansPercent) + SUM(TotalPopulation * OtherPercent))
                )
                AS OtherPerc 
            FROM
                PresidentialElectionVotePercentages 
                INNER JOIN
                    County 
                    ON PresidentialElectionVotePercentages.CountyFKey = County.CountyKey 
                INNER JOIN
                    State 
                    ON StateFkey = StateKey 
                INNER JOIN
                    Population 
                    ON Population.CountyFKey = County.CountyKey 
            WHERE
                PresidentialElectionVotePercentages.`Year` = 2016 
                AND Population.`Year` = 2016 
            GROUP BY
                StateName 
            HAVING
(DemPerc IS NOT NULL 
                AND RepPerc IS NOT NULL 
                AND OtherPerc IS NOT NULL)
        )
        stateVotes2016 
        INNER JOIN
            (
                SELECT
                    `State`.`StateKey` AS StateKey,
                    (
                        SUM(`Covid1`.`CovidDeaths`) - IFNULL(SUM(`Covid2`.`CovidDeaths`), 0)
                    )
                    / (SUM(`Covid1`.`CovidCases`) - IFNULL(SUM(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate` 
                FROM
                    `CovidByDate` AS `Covid1` 
                    LEFT JOIN
                        `CovidByDate` AS `Covid2` 
                        ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
                        AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
                    JOIN
                        `County` 
                        ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
                    JOIN
                        `State` 
                        ON `County`.`StateFKey` = `State`.`StateKey` 
                GROUP BY
                    `State`.`StateName` 
                ORDER BY
                    `CaseFatalityRate` DESC
            )
            caseFatalities 
            ON stateVotes2016.StateKey = caseFatalities.StateKey 
    ORDER BY
        `CaseFatalityRate` DESC;
        
        
# Q6: How many ICU beds per 10,000 people did States have sorted by Descending Case Fatality Ratio ? 
# (i.e. are deaths less / more prevalent in states with a higher number of ICU beds per person ? ) 
    SELECT
        StateName,
        ICUBeds,
        TotalPopulation,
        (
            ICUBeds / (TotalPopulation / 10000)
        )
        AS NumICUBedsPer10000People,
        CaseFatalityRate 
    FROM
        (
            SELECT
                `StateName`,
                `ICUBeds`,
                `StateKey`,
                `TotalPopulation` 
            FROM
                (
                    SELECT
                        `ICUBeds`,
                        `StateFKey`,
                        `TotalPopulation` 
                    FROM
                        `CountyHospitalData` 
                        JOIN
                            `County` 
                            ON `County`.`CountyKey` = `CountyHospitalData`.`CountyFKey` 
                        JOIN
                            `Population` 
                            ON `Population`.`CountyFKey` = `CountyHospitalData`.`CountyFKey`
                )
                CountyICUBedsPop 
                JOIN
                    `State` 
                    ON CountyICUBedsPop.`StateFKey` = `State`.`StateKey` 
            GROUP BY
                `State`.`StateName`
        )
        StateICDUBedsPop 
        INNER JOIN
            (
                SELECT
                    `State`.`StateKey` AS StateKey,
                    (
                        SUM(`Covid1`.`CovidDeaths`) - IFNULL(SUM(`Covid2`.`CovidDeaths`), 0)
                    )
                    / (SUM(`Covid1`.`CovidCases`) - IFNULL(SUM(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate` 
                FROM
                    `CovidByDate` AS `Covid1` 
                    LEFT JOIN
                        `CovidByDate` AS `Covid2` 
                        ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
                        AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
                    JOIN
                        `County` 
                        ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
                    JOIN
                        `State` 
                        ON `County`.`StateFKey` = `State`.`StateKey` 
                GROUP BY
                    `State`.`StateName` 
                ORDER BY
                    `CaseFatalityRate` DESC
            )
            CaseFatality 
            ON StateICDUBedsPop.`StateKey` = CaseFatality.StateKey 
    ORDER BY
        `CaseFatalityRate` DESC;
        
        
# Q7: How many Hospitals per 10,000 people did States have sorted by Descending Case Fatality Ratio ? 
# (i.e. are deaths less / more prevalent in states with a higher number of hospitals per person ? ) 
    SELECT
        StateName,
        NumberOfHospitals,
        TotalPopulation,
        (
            NumberOfHospitals / (TotalPopulation / 10000)
        )
        AS NumHospPer10000People,
        CaseFatalityRate 
    FROM
        (
            SELECT
                `StateName`,
                `NumberOfHospitals`,
                `StateKey`,
                `TotalPopulation` 
            FROM
                (
                    SELECT
                        * 
                    FROM
                        `StateHospitalData` 
                        JOIN
                            `State` 
                            ON `State`.`StateKey` = `StateHospitalData`.`StateFKey`
                )
                StateHosp 
                JOIN
                    `County` 
                    ON StateHosp.`StateFKey` = `County`.`StateFKey` 
                JOIN
                    `Population` 
                    ON `Population`.`CountyFKey` = `County`.`CountyKey` 
            GROUP BY
                StateHosp.`StateName`
        )
        StateHospPop 
        INNER JOIN
            (
                SELECT
                    `State`.`StateKey` AS StateKey,
                    (
                        SUM(`Covid1`.`CovidDeaths`) - IFNULL(SUM(`Covid2`.`CovidDeaths`), 0)
                    )
                    / (SUM(`Covid1`.`CovidCases`) - IFNULL(SUM(`Covid2`.`CovidCases`), 0)) * 100 AS `CaseFatalityRate` 
                FROM
                    `CovidByDate` AS `Covid1` 
                    LEFT JOIN
                        `CovidByDate` AS `Covid2` 
                        ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
                        AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
                    JOIN
                        `County` 
                        ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
                    JOIN
                        `State` 
                        ON `County`.`StateFKey` = `State`.`StateKey` 
                GROUP BY
                    `State`.`StateName` 
                ORDER BY
                    `CaseFatalityRate` DESC
            )
            CaseFatality 
            ON StateHospPop.`StateKey` = CaseFatality.StateKey 
    ORDER BY
        `CaseFatalityRate` DESC;
        
        
# Q8: Number of COVID - 19 cases per race 
SELECT
    A.CasesBlack,
    B.CasesHispanic,
    C.CasesWhite 
FROM
    (
(
        SELECT
            SUM(Positive) AS 'CasesBlack' 
        FROM
            CovidByRace 
        WHERE
            Race = 'Black' 
            AND DATE_FORMAT(Date, '%m-%d') = DATE_FORMAT('2020-06-03', '%m-%d')) AS A 
            CROSS JOIN
                (
                    SELECT
                        SUM(Positive) AS 'CasesHispanic' 
                    FROM
                        CovidByRace 
                    WHERE
                        Race = 'Hispanic' 
                        AND DATE_FORMAT(Date, '%m-%d') = DATE_FORMAT('2020-06-03', '%m-%d')
                )
                AS B 
            CROSS JOIN
                (
                    SELECT
                        SUM(Positive) AS 'CasesWhite' 
                    FROM
                        CovidByRace 
                    WHERE
                        Race = 'White' 
                        AND DATE_FORMAT(Date, '%m-%d') = DATE_FORMAT('2020-06-03', '%m-%d')
                )
                AS C
    )
;


# Q9: States with top mortality rates due to chronic respiratory disease correlation to fatality rate. 
SELECT
    r.StateName,
    RespDiseasePer100k,
    FatalityRate 
FROM
    (
        SELECT
            StateName,
            RespDiseasePer100k 
        FROM
            (
                SELECT
                    CountyFKey,
                    ChronicRespiratoryDiseasesMortalityRate AS RespDiseasePer100k 
                FROM
                    MortalityRates 
                WHERE
                    Year = 2014
            )
            AS A 
            JOIN
                StateCounty 
                ON CountyFKey = CountyKey 
        GROUP BY
            StateName 
        ORDER BY
            RespDiseasePer100k DESC
    )
    AS r 
    JOIN
        (
            SELECT
                `State`.`StateName`,
                (
                    SUM(`Covid1`.`CovidDeaths`) - IFNULL(SUM(`Covid2`.`CovidDeaths`), 0)
                )
                / (SUM(`Covid1`.`CovidCases`) - IFNULL(SUM(`Covid2`.`CovidCases`), 0)) * 100 AS `FatalityRate` 
            FROM
                `CovidByDate` AS `Covid1` 
                LEFT JOIN
                    `CovidByDate` AS `Covid2` 
                    ON DATEDIFF(`Covid1`.`Date`, `Covid2`.`Date`) = 1 
                    AND `Covid1`.`CountyFKey` = `Covid2`.`CountyFKey` 
                JOIN
                    `County` 
                    ON `Covid1`.`CountyFKey` = `County`.`CountyKey` 
                JOIN
                    `State` 
                    ON `County`.`StateFKey` = `State`.`StateKey` 
            GROUP BY
                `State`.`StateName` 
            ORDER BY
                `FatalityRate`
        )
        AS death 
        ON death.StateName = r.StateName;
        
        
# Q10: County COVID - 19 cases and deaths per 10000 people ? 
SELECT
    `CovidByDate`.`Date`,
    `Population`.`Year`,
    `State`.`StateName`,
    `County`.`CountyName`,
    `CovidByDate`.`CovidDeaths`,
    `CovidByDate`.`CovidCases`,
    `Population`.`TotalPopulation`,
    (
        `CovidByDate`.`CovidDeaths` / (`Population`.`TotalPopulation` / 10000)
    )
    AS DeathsPer10000People,
    (
        `CovidByDate`.`CovidCases` / (`Population`.`TotalPopulation` / 10000)
    )
    AS CasesPer10000People 
FROM
    `CovidByDate` 
    JOIN
        `County` 
        ON `CovidByDate`.`CountyFKey` = `County`.`CountyKey` 
    JOIN
        `Population` 
        ON `Population`.`CountyFKey` = `County`.`CountyKey` 
    JOIN
        `State` 
        ON `County`.`StateFKey` = `State`.`StateKey` 
HAVING
    `Population`.`Year` = 2020 
    AND `CovidByDate`.`Date` = 
    (
        SELECT
            MAX(`CovidByDate`.`Date`) 
        FROM
            `CovidByDate`
    )
ORDER BY
    DeathsPer10000People DESC;