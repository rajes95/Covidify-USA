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

public class Popluation {
  protected int countyKey;
  protected String name;
  protected String description;

  public Popluation(int companyKey, String name, String description){
    this.companyKey = companyKey;
    this.name = name;
    this.description = description;
  }

  public Popluation(int companyKey){
    this.companyKey = companyKey;
  }

  public Popluation(String name, String description){
    this.name = name;
    this.description = description;
  }
  public int getCompanyKey() {
    return companyKey;
  }

  public void setCompanyKey(int companyKey) {
    this.companyKey = companyKey;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}