/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

/*

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`Population` (
  `PopulationKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `TotalPopulation` INT NULL,
  `Population60Plus` INT NULL,
  PRIMARY KEY (`PopulationKey`),
  INDEX `CountyFKey7_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey7`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

//TODO here onwards

import java.util.Date;

public class Popluation {
  protected int populationKey;
  protected int countyFKey;
  protected Date year;
  protected Integer totalPopulation;
  protected Integer population60Plus;

  public Popluation(int populationKey, int countyFKey, Date year, Integer totalPopulation, Integer population60Plus) {
    this.populationKey = populationKey;
    this.countyFKey = countyFKey;
    this.year = year;
    this.totalPopulation = totalPopulation;
    this.population60Plus = population60Plus;
  }

  public Popluation(int populationKey){
    this.populationKey = populationKey;
  }

  public Popluation(int countyFKey, Date year, Integer totalPopulation, Integer population60Plus) {
    this.countyFKey = countyFKey;
    this.year = year;
    this.totalPopulation = totalPopulation;
    this.population60Plus = population60Plus;

}