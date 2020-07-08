/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;


/*
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
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey4`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

import java.util.Date;

public class Climate {
  protected int climateKey;
  protected County county;
  protected Date year;
  protected Double elevation;
  protected Double winterPrcp;
  protected Double summerPrcp;
  protected Double springPrcp;
  protected Double autumnPrcp;
  protected Double winterTavg;
  protected Double summerTavg;
  protected Double springTavg;
  protected Double autumnTavg;


  public Climate(int climateKey, County county, Date year, Double elevation, Double winterPrcp,
                 Double summerPrcp, Double springPrcp, Double autumnPrcp, Double winterTavg,
                 Double summerTavg, Double springTavg, Double autumnTavg) {
    this.climateKey = climateKey;
    this.county = county;
    this.year = year;
    this.elevation = elevation;
    this.winterPrcp = winterPrcp;
    this.summerPrcp = summerPrcp;
    this.springPrcp = springPrcp;
    this.autumnPrcp = autumnPrcp;
    this.winterTavg = winterTavg;
    this.summerTavg = summerTavg;
    this.springTavg = springTavg;
    this.autumnTavg = autumnTavg;
  }

  public Climate(int climateKey) {
    this.climateKey = climateKey;
  }

  public Climate(County county, Date year, Double elevation, Double winterPrcp,
                 Double summerPrcp, Double springPrcp, Double autumnPrcp, Double winterTavg,
                 Double summerTavg, Double springTavg, Double autumnTavg) {
    this.county = county;
    this.year = year;
    this.elevation = elevation;
    this.winterPrcp = winterPrcp;
    this.summerPrcp = summerPrcp;
    this.springPrcp = springPrcp;
    this.autumnPrcp = autumnPrcp;
    this.winterTavg = winterTavg;
    this.summerTavg = summerTavg;
    this.springTavg = springTavg;
    this.autumnTavg = autumnTavg;
  }

  public int getClimateKey() {
    return climateKey;
  }

  public void setClimateKey(int climateKey) {
    this.climateKey = climateKey;
  }

  public Date getYear() {
    return year;
  }

  public void setYear(Date year) {
    this.year = year;
  }

  public County getCounty(){ return county;}

  public void setCounty(County county) {
    this.county = county;
  }

  public Double getElevation() {
    return elevation;
  }

  public void setElevation(Double elevation) {
    this.elevation = elevation;
  }

  public Double getWinterPrcp() {
    return winterPrcp;
  }

  public void setWinterPrcp(Double winterPrcp) {
    this.winterPrcp = winterPrcp;
  }

  public Double getSummerPrcp() {
    return summerPrcp;
  }

  public void setSummerPrcp(Double summerPrcp) {
    this.summerPrcp = summerPrcp;
  }

  public Double getSpringPrcp() {
    return springPrcp;
  }

  public void setSpringPrcp(Double springPrcp) {
    this.springPrcp = springPrcp;
  }

  public Double getAutumnPrcp() {
    return autumnPrcp;
  }

  public void setAutumnPrcp(Double autumnPrcp) {
    this.autumnPrcp = autumnPrcp;
  }

  public Double getWinterTavg() {
    return winterTavg;
  }

  public void setWinterTavg(Double winterTavg) {
    this.winterTavg = winterTavg;
  }
  public Double getSummerTavg() {
    return summerTavg;
  }

  public void setSummerTavg(Double summerTavg) {
    this.summerTavg = summerTavg;
  }
  public Double getSpringTavg() {
    return springTavg;
  }

  public void setSpringTavg(Double springTavg) {
    this.springTavg = springTavg;
  }

  public Double getAutumnTavg() {
    return autumnTavg;
  }

  public void setAutumnTavg(Double autumnTavg) {
    this.autumnTavg = autumnTavg;
  }


}