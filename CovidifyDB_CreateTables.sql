

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema CovidifyUSA
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema CovidifyUSA
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `CovidifyUSA` ;
USE `CovidifyUSA` ;

DROP TABLE IF EXISTS `CovidifyUSA`.`CovidByRace` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`Population` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`MortalityRates` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`CountyHospitalData` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateHospitalData` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`Climate` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`Demographics` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`PresidentialElectionVotePercentages` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`StateGovernor` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`CovidByDate` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`County` ;
DROP TABLE IF EXISTS `CovidifyUSA`.`State` ;

-- -----------------------------------------------------
-- Table `CovidifyUSA`.`State`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`State` (
  `StateKey` INT NOT NULL AUTO_INCREMENT,
  `StateFIPS` VARCHAR(45) NULL,
  `StateName` VARCHAR(100) NULL,
  PRIMARY KEY (`StateKey`),
  UNIQUE INDEX `StateFIPS_UNIQUE` (`StateFIPS` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`County`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`County` (
  `CountyKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `CountyFIPS` VARCHAR(45) NULL,
  `CountyName` VARCHAR(100) NULL,
  `Longitude` VARCHAR(45) NULL,
  `Latitude` VARCHAR(45) NULL,
  PRIMARY KEY (`CountyKey`),
  UNIQUE INDEX `CountyFIPS_UNIQUE` (`CountyFIPS` ASC),
  INDEX `StateFKey_idx` (`StateFKey` ASC),
  CONSTRAINT `StateFKey`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`CovidByDate`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidByDate` (
  `CovidByDateKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Date` DATE NULL,
  `CovidDeaths` INT NULL,
  `CovidCases` INT NULL,
  PRIMARY KEY (`CovidByDateKey`),
  INDEX `CountyFKey1_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `CovUniq` (`CountyFKey` ASC, `Date` ASC),
  CONSTRAINT `CountyFKey1`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`PresidentialElectionVotePercentages`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`PresidentialElectionVotePercentages` (
  `PresidentialElectionVotePercentagesKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `DemocratsPercent` DECIMAL(5,2) NULL,
  `RepublicansPercent` DECIMAL(5,2) NULL,
  `OtherPercent` DECIMAL(5,2) NULL,
  PRIMARY KEY (`PresidentialElectionVotePercentagesKey`),
  INDEX `CountyFKey2_idx` (`CountyFKey` ASC),
  CONSTRAINT `CountyFKey2`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`StateGovernor`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateGovernor` (
  `StateGovernorKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `Governor` VARCHAR(45) NULL,
  `GovernorParty` ENUM('Democratic', 'Republican', 'Other') NULL,
  PRIMARY KEY (`StateGovernorKey`),
  INDEX `StateFKey1_idx` (`StateFKey` ASC),
  CONSTRAINT `StateFKey1`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`Demographics`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`Demographics` (
  `DemographicsKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `White` DECIMAL(5,2) NULL,
  `AfricanAmerican` DECIMAL(5,2) NULL,
  `Latino` DECIMAL(5,2) NULL,
  `NativeAmerican` DECIMAL(5,2) NULL,
  `AsianAmerican` DECIMAL(5,2) NULL,
  `OtherEthnicity` DECIMAL(5,2) NULL,
  `PovertyRate` DECIMAL(5,2) NULL,
  `MedianAge` DECIMAL NULL,
  `MedianEarnings` DECIMAL NULL,
  PRIMARY KEY (`DemographicsKey`),
  INDEX `CountyFKey3_idx` (`CountyFKey` ASC),
  CONSTRAINT `CountyFKey3`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`Climate`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`Climate` (
  `ClimateKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `Elevation` DECIMAL NULL,
  `WinterPrcp` DECIMAL NULL,
  `SummerPrcp` DECIMAL NULL,
  `SpringPrcp` DECIMAL NULL,
  `AutumnPrcp` DECIMAL NULL,
  `WinterTavg` DECIMAL NULL,
  `SummerTavg` DECIMAL NULL,
  `SpringTavg` DECIMAL NULL,
  `AutumnTavg` DECIMAL NULL,
  PRIMARY KEY (`ClimateKey`),
  INDEX `CountyFKey4_idx` (`CountyFKey` ASC),
  CONSTRAINT `CountyFKey4`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`StateHospitalData`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateHospitalData` (
  `StateHospitalDataKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `NumberOfHospitals` VARCHAR(45) NULL,
  `NumberOfHospitalEmployees` VARCHAR(45) NULL,
  PRIMARY KEY (`StateHospitalDataKey`),
  INDEX `StateFKey2_idx` (`StateFKey` ASC),
  CONSTRAINT `StateFKey2`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`CountyHospitalData`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CountyHospitalData` (
  `CountyHospitalDataKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `ICUBeds` INT NULL,
  PRIMARY KEY (`CountyHospitalDataKey`),
  INDEX `CountyFKey5_idx` (`CountyFKey` ASC),
  CONSTRAINT `CountyFKey5`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`MortalityRates`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`MortalityRates` (
  `MortalityRatesKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `NeonatalDisordersMortalityRate` DECIMAL NULL,
  `HIVAIDSandTBMortalityRate` DECIMAL NULL,
  `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate` DECIMAL NULL,
  `ChronicRespiratoryDiseasesMortalityRate` DECIMAL NULL,
  `LiverDiseaseMortalityRate` DECIMAL NULL,
  `NutritionalDeficienciesMortalityRate` DECIMAL NULL,
  `CardiovascularDiseasesMortalityRate` DECIMAL NULL,
  PRIMARY KEY (`MortalityRatesKey`),
  INDEX `CountyFKey6_idx` (`CountyFKey` ASC),
  CONSTRAINT `CountyFKey6`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`Population`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`Population` (
  `PopulationKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `TotalPopulation` INT NULL,
  `Population60Plus` INT NULL,
  PRIMARY KEY (`PopulationKey`),
  INDEX `CountyFKey7_idx` (`CountyFKey` ASC),
  CONSTRAINT `CountyFKey7`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `CovidifyUSA`.`CovidByRace`
-- -----------------------------------------------------


CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidByRace` (
  `CovidByRaceKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Race` ENUM('White', 'Black', 'Hispanic', 'Asian', 'Multiracial', 'NHPI', 'Multi', 'Other', 'Unknown') NULL,
  `Positive` INT NULL,
  `Negative` INT NULL,
  `Death` INT NULL,
  `Date` DATE NULL,
  PRIMARY KEY (`CovidByRaceKey`),
  INDEX `fk_CovidByRace_State1_idx` (`StateFKey` ASC),
  UNIQUE INDEX `RaceUniq` (`StateFKey` ASC, `Date` ASC),
  CONSTRAINT `fk_CovidByRace_State1`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

