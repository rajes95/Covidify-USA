/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

/*
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

 */

import java.util.Date;

public class CovidByDate {
  protected int covidByDateKey;
  protected County county;
  protected Date date;
  protected Integer covidDeaths;
  protected Integer covidCases;

  public CovidByDate(int covidByDateKey, County county, Date date, Integer covidDeaths,Integer covidCases) {
    this.covidByDateKey = covidByDateKey;
    this.county = county;
    this.date = date;
    this.covidDeaths =covidDeaths;
    this.covidCases = covidCases;
  }

  public CovidByDate(int covidByDateKey) {
    this.covidByDateKey = covidByDateKey;
  }

  public int getCovidByDateKey() {
    return covidByDateKey;
  }

  public void setCovidByDateKey(int covidByDateKey) {
    this.covidByDateKey = covidByDateKey;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Integer getCovidDeaths() {
    return covidDeaths;
  }

  public void setCovidDeaths(Integer covidDeaths) {
    this.covidDeaths = covidDeaths;
  }
  public Integer getCovidCases() {
    return covidCases;
  }

  public void setCovidCases(Integer covidCases) {
    this.covidCases = covidCases;
  }
}
