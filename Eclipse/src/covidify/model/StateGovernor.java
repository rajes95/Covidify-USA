/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;
import java.io.Serializable;
import java.sql.Date;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateGovernor` (
  `StateGovernorKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `Governor` VARCHAR(45) NULL,
  `GovernorParty` ENUM('Democratic', 'Republican', 'Other') NULL,
  PRIMARY KEY (`StateGovernorKey`),
  INDEX `StateFKey1_idx` (`StateFKey` ASC),
  UNIQUE INDEX `Unique` (`StateFKey` ASC, `Year` ASC),
  CONSTRAINT `StateFKey1`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
 */

public class StateGovernor  {
  protected int stateGovernorKey;
  protected State state;
  protected Date year;
  protected String governor;
  protected GovernorPartyType governorParty;

  public enum GovernorPartyType {
    Democratic, Republican, Other
  }

  public StateGovernor(int stateGovernorKey, State state, Date year, String governor, GovernorPartyType governorParty) {
    this.stateGovernorKey = stateGovernorKey;
    this.state = state;
    this.year = year;
    this.governor = governor;
    this.governorParty = governorParty;
  }

  public StateGovernor(int stateGovernorKey) {
    this.stateGovernorKey = stateGovernorKey;
  }

  public StateGovernor(State state, Date year, String governor, GovernorPartyType governorParty) {
    this.state = state;
    this.year = year;
    this.governor = governor;
    this.governorParty = governorParty;
  }

  public int getStateGovernorKey() {
    return stateGovernorKey;
  }
  public void setStateGovernorKey(int stateGovernorKey) {
    this.stateGovernorKey = stateGovernorKey;
  }

  public State getState() {
    return state;
  }
  public void setState(State state) {
    this.state = state;
  }

  public Date getYear() {
    return year;
  }
  public void setYear(Date year) {
    this.year = year;
  }

  public String getGovernor() {
    return governor;
  }
  public void setGovernor(String governor) {
    this.governor = governor;
  }

  public GovernorPartyType getGovernorParty() {
    return governorParty;
  }
  public void setGovernorParty(GovernorPartyType governorParty) {
    this.governorParty = getGovernorParty();
  }
}
