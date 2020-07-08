/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

import java.util.Date;
/*

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateHospitalData` (
  `StateHospitalDataKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `NumberOfHospitals` VARCHAR(45) NULL,
  `NumberOfHospitalEmployees` VARCHAR(45) NULL,
  PRIMARY KEY (`StateHospitalDataKey`),
  INDEX `StateFKey2_idx` (`StateFKey` ASC),
  UNIQUE INDEX `Unique` (`StateFKey` ASC, `Year` ASC),
  CONSTRAINT `StateFKey2`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 */

public class StateHospitalData {
  protected int stateHospitalDataKey;
  protected int stateFKey;
  protected Date year;
  protected Long numberOfHospitals;
  protected Long numberOfHospitalEmployees;

  public StateHospitalData(int stateHospitalDataKey, int stateFKey, Date year, Long numberOfHospitals,
                           Long numberOfHospitalEmployees) {
    this.stateHospitalDataKey = reservationKey;
    this.stateFKey = stateFKey;
    this.year = year;
    this.numberOfHospitals = numberOfHospitals;
    this.numberOfHospitalEmployees = numberOfHospitalEmployees;
  }

  public StateHospitalData(int stateHospitalDataKey) {
    this.stateHospitalDataKey = reservationKey;
  }

  public StateHospitalData(int stateFKey, Date year, Long numberOfHospitals, Long numberOfHospitalEmployees) {
    this.stateFKey = stateFKey;
    this.year = year;
    this.numberOfHospitals = numberOfHospitals;
    this.numberOfHospitalEmployees = numberOfHospitalEmployees;
  }

  public int getStateHospitalDataKey() {
    return stateHospitalDataKey;
  }

  public void setStateHospitalDataKey(int stateHospitalDataKey) {
    this.stateHospitalDataKey = stateHospitalDataKey;
  }

  public int getStateFKey() {
    return stateFKey;
  }

  public void setStateFKey(int stateFKey) {
    this.stateFKey = stateFKey;
  }

  public Date getYear() {
    return year;
  }

  public void setYear(Date year) {
    this.year = year;
  }

  public Long getNumberOfHospitals() {
    return numberOfHospitals;
  }

  public void setNumberOfHospitals(Long numberOfHospitals) {
    this.numberOfHospitals = numberOfHospitals;
  }

  public Long getNumberOfHospitalEmployees() {
    return numberOfHospitalEmployees;
  }

  public void setNumberOfHospitalEmployees(Long numberOfHospitalEmployees) {
    this.numberOfHospitalEmployees = numberOfHospitalEmployees;
  }
}

/*

CREATE TABLE IF NOT EXISTS `restdb`.`Reservation` (
  `ReservationKey` INT NOT NULL AUTO_INCREMENT,
  `UserName` VARCHAR(45) NOT NULL,
  `SitDownRestaurantKey` INT NOT NULL,
  `Start` TIMESTAMP NOT NULL,
  `End` TIMESTAMP NULL,
  `PartySize` INT NOT NULL,
  INDEX `UserKey3_idx` (`UserName` ASC),
  INDEX `SitDownRestaurantKey1_idx` (`SitDownRestaurantKey` ASC),
  PRIMARY KEY (`ReservationKey`),
  CONSTRAINT `UserKey3`
    FOREIGN KEY (`UserName`)
    REFERENCES `restdb`.`User` (`UserName`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `SitDownRestaurantKey1`
    FOREIGN KEY (`SitDownRestaurantKey`)
    REFERENCES `restdb`.`SitDownRestaurant` (`SitDownRestaurantKey`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;
 */