/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;
/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidByRace` (
  `CovidByRaceKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Race` ENUM('White', 'Black', 'Hispanic', 'Asian', 'Multiracial', 'NHPI', 'Multi', 'Other', 'Unknown') NOT NULL,
  `Positive` INT NULL,
  `Negative` INT NULL,
  `Death` INT NULL,
  `Date` DATE NULL,
  PRIMARY KEY (`CovidByRaceKey`),
  INDEX `fk_CovidByRace_State1_idx` (`StateFKey` ASC),
  CONSTRAINT `fk_CovidByRace_State1`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

import java.util.Date;

public class CovidByRace {
  protected int covidByRaceKey;
  protected State state;
  protected RaceType race;
  protected Integer positive;
  protected Integer negative;
  protected Integer death;
  protected Date date;

  public enum RaceType {
    White, Black, Hispanic, Asian, Multiracial, NHPI, Multi, Other, Unknown
  }

  public CovidByRace(int covidByRaceKey, State state, RaceType race,
                     Integer positive, Integer negative, Integer death, Date date) {
    this.covidByRaceKey = covidByRaceKey;
    this.state = state;
    this.race = race;
    this.positive = positive;
    this.negative = negative;
    this.death = death;
    this.date = date;
  }

  public CovidByRace(int covidByRaceKey) {
    this.covidByRaceKey = covidByRaceKey;
  }

  public CovidByRace(State state, RaceType race,
                     Integer positive, Integer negative, Integer death, Date date) {
    this.state = state;
    this.race = race;
    this.positive = positive;
    this.negative = negative;
    this.death = death;
    this.date = date;
  }

  public int getCovidByRaceKey() {
    return covidByRaceKey;
  }

  public void setCovidByRaceKey(int covidByRaceKey) {
    this.covidByRaceKey = covidByRaceKey;
  }
  
  public State getState() {
    return state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public RaceType getRaceType() {
    return race;
  }

  public void setRaceType(RaceType race) {
    this.race = race;
  }

  public Integer getPositive() {
    return positive;
  }

  public void setPositive(Integer positive) {
    this.positive = positive;
  }

  public Integer getNegative() {
    return negative;
  }

  public void setNegative(Integer negative) {
    this.negative = negative;
  }

  public Integer getDeath() {
    return death;
  }

  public void setDeath(Integer death) {
    this.death = death;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }
}

