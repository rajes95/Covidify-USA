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

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNum() {
    return email;
  }

  public void setPhoneNum(String phoneNum) {
    this.phoneNum = phoneNum;
  }
}
