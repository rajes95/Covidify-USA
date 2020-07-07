/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;
//TODO here onwards
public class PresidentialElectionVotePercentages {
  protected int recommendationKey;
  protected CovidByDate covidByDate;
  protected MortalityRates mortalityRates;

  public Recommendation(int recommendationKey, CovidByDate covidByDate, MortalityRates mortalityRates){
    this.recommendationKey = recommendationKey;
    this.covidByDate = covidByDate;
    this.mortalityRates = mortalityRates;
  }

  public Recommendation(int recommendationKey){
    this.recommendationKey = recommendationKey;
  }

  public Recommendation(CovidByDate covidByDate, MortalityRates mortalityRates){
    this.covidByDate = covidByDate;
    this.mortalityRates = mortalityRates;
  }

  public int getRecommendationKey() {
    return recommendationKey;
  }

  public void setRecommendationKey(int recommendationKey) {
    this.recommendationKey = recommendationKey;
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

}

/*
CREATE TABLE IF NOT EXISTS `restdb`.`Recommendation` (
  `RecommendationKey` INT NOT NULL AUTO_INCREMENT,
  `UserKey` VARCHAR(45) NULL,
  `RestaurantKey` INT NULL,
  INDEX `UserKey2_idx` (`UserKey` ASC),
  INDEX `RestaurantKey5_idx` (`RestaurantKey` ASC),
  PRIMARY KEY (`RecommendationKey`),
  CONSTRAINT `UserKey2`
    FOREIGN KEY (`UserKey`)
    REFERENCES `restdb`.`User` (`UserName`)
    ON DELETE SET NULL
    ON UPDATE SET NULL,
  CONSTRAINT `RestaurantKey5`
    FOREIGN KEY (`RestaurantKey`)
    REFERENCES `restdb`.`Restaurant` (`RestaurantKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 */