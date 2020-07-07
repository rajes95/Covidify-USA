/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

import java.sql.Date;

public class StateHospitalData {
  protected int reservationKey;
  protected CovidByDate covidByDate;
  protected StateGovernor restaurant;
  protected Date startDate;
  protected Date endDate;
  protected Integer partySize;

  public StateHospitalData(int reservationKey, CovidByDate covidByDate, StateGovernor restaurant,
                           Date startDate, Date endDate, Integer partySize){
    this.reservationKey = reservationKey;
    this.covidByDate = covidByDate;
    this.restaurant = restaurant;
    this.startDate = startDate;
    this.endDate = endDate;
    this.partySize = partySize;
  }

  public StateHospitalData(int reservationKey){
    this.reservationKey = reservationKey;
  }

  public StateHospitalData(CovidByDate covidByDate, StateGovernor restaurant,
                           Date startDate, Date endDate, Integer partySize){
    this.covidByDate = covidByDate;
    this.restaurant = restaurant;
    this.startDate = startDate;
    this.endDate = endDate;
    this.partySize = partySize;
  }

  public int getReservationKey() {
    return reservationKey;
  }

  public void setReservationKey(int reservationKey) {
    this.reservationKey = reservationKey;
  }

  public CovidByDate getCovidByDate() {
    return covidByDate;
  }

  public void setCovidByDate(CovidByDate covidByDate) {
    this.covidByDate = covidByDate;
  }

  public StateGovernor getRestaurant() {
    return restaurant;
  }

  public void setRestaurant(StateGovernor restaurant) {
    this.restaurant = restaurant;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Integer getPartySize() {
    return partySize;
  }

  public void setPartySize(Integer partySize) {
    this.partySize = partySize;
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