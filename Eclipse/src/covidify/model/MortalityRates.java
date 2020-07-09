/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;
//TODO here onwards

import java.util.Date;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`MortalityRates` (
  `MortalityRatesKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `NeonatalDisordersMortalityRate` DECIMAL(10,2) NULL,
  `HIVAIDSandTBMortalityRate` DECIMAL(10,2) NULL,
  `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate` DECIMAL(10,2) NULL,
  `ChronicRespiratoryDiseasesMortalityRate` DECIMAL(10,2) NULL,
  `LiverDiseaseMortalityRate` DECIMAL(10,2) NULL,
  `NutritionalDeficienciesMortalityRate` DECIMAL(10,2) NULL,
  `CardiovascularDiseasesMortalityRate` DECIMAL(10,2) NULL,
  PRIMARY KEY (`MortalityRatesKey`),
  INDEX `CountyFKey6_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey6`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */
public class MortalityRates {
  protected int mortalityRatesKey;
  protected County county;
  protected Date year;
  protected Double neonatalRate;
  protected Double hivRate;
  protected Double diabetesRate;
  protected Double chronicRespitoraryRate;
  protected Double liverDiseaseRate;
  protected Double nutritionalDeficienciesRate;
  protected Double cardiovascularRate;


  public MortalityRates(int mortalityRatesKey, County county, Date year, Double neonatalRate, Double hivRate,
                        Double diabetesRate, Double chronicRespitoraryRate, Double liverDiseaseRate,
                        Double nutritionalDeficienciesRate, Double cardiovascularRate) {
    this.mortalityRatesKey = mortalityRatesKey;
    this.county = county;
    this.year = year;
    this.neonatalRate = neonatalRate;
    this.hivRate = hivRate;
    this.diabetesRate = diabetesRate;
    this.chronicRespitoraryRate = chronicRespitoraryRate;
    this.liverDiseaseRate = liverDiseaseRate;
    this.nutritionalDeficienciesRate = nutritionalDeficienciesRate;
    this.cardiovascularRate = cardiovascularRate;
  }

  public MortalityRates(int mortalityRatesKey) {
    this.mortalityRatesKey = mortalityRatesKey;
  }

  public MortalityRates(County county, Date year, Double neonatalRate, Double hivRate, Double diabetesRate,
                        Double chronicRespitoraryRate, Double liverDiseaseRate, Double nutritionalDeficienciesRate,
                        Double cardiovascularRate) {
    this.county = county;
    this.year = year;
    this.neonatalRate = neonatalRate;
    this.hivRate = hivRate;
    this.diabetesRate = diabetesRate;
    this.chronicRespitoraryRate = chronicRespitoraryRate;
    this.liverDiseaseRate = liverDiseaseRate;
    this.nutritionalDeficienciesRate = nutritionalDeficienciesRate;
    this.cardiovascularRate = cardiovascularRate;
  }
  
  public int getMortalityRatesKey() {
    return mortalityRatesKey;
  }

  public void setMortalityRatesKey(int mortalityRatesKey) {
    this.mortalityRatesKey = mortalityRatesKey;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public Date getYear() {
    return year;
  }

  public void setYear(Date year) {
    this.year = year;
  }

  public Double getNeonatalRate() {
    return neonatalRate;
  }

  public void setNeonatalRate(Double neonatalRate) {
    this.neonatalRate = neonatalRate;
  }

  public Double getHivRate() {
    return hivRate;
  }

  public void setHivRate(Double hivRate) {
    this.hivRate = hivRate;
  }

  public Double getDiabetesRate() {
    return diabetesRate;
  }

  public void setDiabetesRate(Double diabetesRate) {
    this.diabetesRate = diabetesRate;
  }

  public Double getChronicRespitoraryRate() {
    return chronicRespitoraryRate;
  }

  public void setChronicRespitoraryRate(Double chronicRespitoraryRate) {
    this.chronicRespitoraryRate = chronicRespitoraryRate;
  }

  public Double getLiverDiseaseRate() {
    return liverDiseaseRate;
  }

  public void setLiverDiseaseRate(Double liverDiseaseRate) {
    this.liverDiseaseRate = liverDiseaseRate;
  }

  public Double getNutritionalDeficienciesRate() {
    return nutritionalDeficienciesRate;
  }

  public void setNutritionalDeficienciesRate(Double nutritionalDeficienciesRate) {
    this.nutritionalDeficienciesRate = nutritionalDeficienciesRate;
  }

  public Double getCardiovascularRate() {
    return cardiovascularRate;
  }

  public void setCardiovascularRate(Double cardiovascularRate) {
    this.cardiovascularRate = cardiovascularRate;
  }
}
