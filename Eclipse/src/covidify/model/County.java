/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`County` (
  `CountyKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `CountyFIPS` VARCHAR(45) NULL,
  `CountyName` VARCHAR(100) NULL,
  `Longitude` VARCHAR(45) NULL,
  `Latitude` VARCHAR(45) NULL,
  PRIMARY KEY (`CountyKey`),
  UNIQUE INDEX `CountyFIPS_UNIQUE` (`CountyFIPS` ASC),
  INDEX `StateFKey_idx` (`StateFKey` ASC),
  CONSTRAINT `StateFKey`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
    ENGINE = InnoDB;
*/

public class County {
  protected int countyKey;
  protected String name;
  protected String description;

  public County(int companyKey, String name, String description){
    this.companyKey = companyKey;
    this.name = name;
    this.description = description;
  }

  public County(int companyKey){
    this.companyKey = companyKey;
  }

  public County(String name, String description){
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