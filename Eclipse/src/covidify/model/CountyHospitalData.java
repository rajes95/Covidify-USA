/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

import java.util.Date;

/*

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CountyHospitalData` (
  `CountyHospitalDataKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `ICUBeds` INT NULL,
  PRIMARY KEY (`CountyHospitalDataKey`),
  INDEX `CountyFKey5_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey5`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 */
public class CountyHospitalData {
    protected int countyHospitalDataKey;
    protected County county;
    protected Date year;
    protected Integer icuBeds;

    public CountyHospitalData(int countyHospitalDataKey, County county, Date year, Integer icuBeds) {
        this.countyHospitalDataKey = countyHospitalDataKey;
        this.county = county;
        this.year = year;
        this.icuBeds = icuBeds;
    }

    public CountyHospitalData(int countyHospitalDataKey) {
        this.countyHospitalDataKey = countyHospitalDataKey;
    }

    public CountyHospitalData(County county, Date year, Integer icuBeds) {
        this.county = county;
        this.year = year;
        this.icuBeds = icuBeds;
    }

    public int getCountyHospitalDataKey() {
        return countyHospitalDataKey;
    }

    public void setCountyHospitalDataKey(int countyHospitalDataKey) {
        this.countyHospitalDataKey = countyHospitalDataKey;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public Integer getIcuBeds() {
        return icuBeds;
    }

    public void setIcuBeds(Integer icuBeds) {
        this.icuBeds = icuBeds;
    }
}
