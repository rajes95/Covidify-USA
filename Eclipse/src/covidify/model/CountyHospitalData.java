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
    protected int countyFKey;
    protected Date year;
    protected Integer icuBeds;

    public CountyHospitalData(int countyHospitalDataKey, int countyFKey, Date year, Integer icuBeds) {
        this.countyHospitalDataKey = countyHospitalDataKey;
        this.countyFKey = countyFKey;
        this.year = year;
        this.icuBeds = icuBeds;
    }

    public CountyHospitalData(int countyHospitalDataKey) {
        this.countyHospitalDataKey = countyHospitalDataKey;
    }

    public CountyHospitalData(int countyFKey, Date year, Integer icuBeds) {
        this.countyFKey = countyFKey;
        this.year = year;
        this.icuBeds = icuBeds;
    }

    public int getCountyHospitalDataKey() {
        return countyHospitalDataKey;
    }

    public void setCountyHospitalDataKey(int countyHospitalDataKey) {
        this.countyHospitalDataKey = countyHospitalDataKey;
    }

    public int getCountyFKey() {
        return countyFKey;
    }

    public void setCountyFKey(int countyFKey) {
        this.countyFKey = countyFKey;
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

/*
 * CREATE TABLE IF NOT EXISTS `restdb`.`Review` ( `ReviewKey` INT NOT NULL
 * AUTO_INCREMENT, `UserName` VARCHAR(45) NULL, `RestaurantKey` INT NULL,
 * `CreatedWhen` TIMESTAMP NOT NULL DEFAULT NOW(), `WrittenContent` TEXT NULL,
 * `Rating` DECIMAL(2,1) NOT NULL, PRIMARY KEY (`ReviewKey`), INDEX
 * `UserKey1_idx` (`UserName` ASC), INDEX `RestaurantKey4_idx` (`RestaurantKey`
 * ASC), UNIQUE INDEX `RevUniq` (`UserName` ASC, `RestaurantKey` ASC),
 * CONSTRAINT `UserKey1` FOREIGN KEY (`UserName`) REFERENCES `restdb`.`User`
 * (`UserName`) ON DELETE SET NULL ON UPDATE SET NULL, CONSTRAINT
 * `RestaurantKey4` FOREIGN KEY (`RestaurantKey`) REFERENCES
 * `restdb`.`Restaurant` (`RestaurantKey`) ON DELETE SET NULL ON UPDATE SET
 * NULL) ENGINE = InnoDB;
 */
