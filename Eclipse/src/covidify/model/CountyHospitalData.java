/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

import java.sql.Date;

public class CountyHospitalData {
  protected int reviewKey;
  protected CovidByDate covidByDate;
  protected MortalityRates mortalityRates;
  protected Date createdWhen;
  protected String writtenContent;
  protected Float rating;

  public CountyHospitalData(int reviewKey, CovidByDate covidByDate, MortalityRates mortalityRates,
                            Date createdWhen, String writtenContent, Float rating){
    this.reviewKey = reviewKey;
    this.covidByDate = covidByDate;
    this.mortalityRates = mortalityRates;
    this.createdWhen = createdWhen;
    this.writtenContent = writtenContent;
    this.rating = rating;
  }

  public CountyHospitalData(int reviewKey){
    this.reviewKey = reviewKey;
  }

  public CountyHospitalData(CovidByDate covidByDate, MortalityRates mortalityRates,
                            Date createdWhen, String writtenContent, Float rating){
    this.covidByDate = covidByDate;
    this.mortalityRates = mortalityRates;
    this.createdWhen = createdWhen;
    this.writtenContent = writtenContent;
    this.rating = rating;
  }

  public int getReviewKey() {
    return reviewKey;
  }

  public void setReviewKey(int reviewKey) {
    this.reviewKey = reviewKey;
  }

  public CovidByDate getCovidByDate() {
    return covidByDate;
  }

  public void setCovidByDate(CovidByDate covidByDate) {
    this.covidByDate = covidByDate;
  }

  public MortalityRates getMortalityRates() {
    return mortalityRates;
  }

  public void setMortalityRates(MortalityRates mortalityRates) {
    this.mortalityRates = mortalityRates;
  }

  public Date getCreatedWhen() {
    return createdWhen;
  }

  public void setCreatedWhen(Date createdWhen) {
    this.createdWhen = createdWhen;
  }

  public String getWrittenContent() {
    return writtenContent;
  }

  public void setWrittenContent(String writtenContent) {
    this.writtenContent = writtenContent;
  }

  public Float getRating() {
    return rating;
  }

  public void setRating(Float rating) {
    this.rating = rating;
  }

}

/*
CREATE TABLE IF NOT EXISTS `restdb`.`Review` (
  `ReviewKey` INT NOT NULL AUTO_INCREMENT,
  `UserName` VARCHAR(45) NULL,
  `RestaurantKey` INT NULL,
  `CreatedWhen` TIMESTAMP NOT NULL DEFAULT NOW(),
  `WrittenContent` TEXT NULL,
  `Rating` DECIMAL(2,1) NOT NULL,
  PRIMARY KEY (`ReviewKey`),
  INDEX `UserKey1_idx` (`UserName` ASC),
  INDEX `RestaurantKey4_idx` (`RestaurantKey` ASC),
  UNIQUE INDEX `RevUniq` (`UserName` ASC, `RestaurantKey` ASC),
  CONSTRAINT `UserKey1`
    FOREIGN KEY (`UserName`)
    REFERENCES `restdb`.`User` (`UserName`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `RestaurantKey4`
    FOREIGN KEY (`RestaurantKey`)
    REFERENCES `restdb`.`Restaurant` (`RestaurantKey`)
    ON DELETE SET NULL
    ON UPDATE SET NULL)
ENGINE = InnoDB;
 */