/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;
/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`Demographics` (
  `DemographicsKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `White` DECIMAL(5,2) NULL,
  `AfricanAmerican` DECIMAL(5,2) NULL,
  `Latino` DECIMAL(5,2) NULL,
  `NativeAmerican` DECIMAL(5,2) NULL,
  `AsianAmerican` DECIMAL(5,2) NULL,
  `OtherEthnicity` DECIMAL(5,2) NULL,
  `PovertyRate` DECIMAL(5,2) NULL,
  `MedianAge` DECIMAL NULL,
  `MedianEarnings` DECIMAL NULL,
  PRIMARY KEY (`DemographicsKey`),
  INDEX `CountyFKey3_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey3`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
 */


import java.util.Date;


public class Demographics {
  protected int demographicsKey;
  protected County county;
  protected Date year;
  protected Double white;
  protected Double africanAmerican;
  protected Double latino;
  protected Double nativeAmerican;
  protected Double asianAmerican;
  protected Double otherEthnicity;
  protected Double povertyRate;
  protected Double medianAge;
  protected Double medianEarnings;

  public Demographics(int demographicsKey, County county, Date year, Double white,
                      Double africanAmerican, Double latino, Double nativeAmerican,
                      Double asianAmerican, Double otherEthnicity, Double povertyRate,
                      Double medianAge, Double medianEarnings) {
    this.demographicsKey = demographicsKey;
    this.county = county;
    this.year = year;
    this.white = white;
    this.africanAmerican = africanAmerican;
    this.latino = latino;
    this.nativeAmerican = nativeAmerican;
    this.asianAmerican = asianAmerican;
    this.otherEthnicity = otherEthnicity;
    this.povertyRate = povertyRate;
    this.medianAge = medianAge;
    this.medianEarnings = medianEarnings;
  }

  public Demographics(int demographicsKey) {
    this.demographicsKey = demographicsKey;
  }

  public Demographics(County county, Date year, Double white,
                      Double africanAmerican, Double latino, Double nativeAmerican,
                      Double asianAmerican, Double otherEthnicity, Double povertyRate,
                      Double medianAge, Double medianEarnings) {
    this.county = county;
    this.year = year;
    this.white = white;
    this.africanAmerican = africanAmerican;
    this.latino = latino;
    this.nativeAmerican = nativeAmerican;
    this.asianAmerican = asianAmerican;
    this.otherEthnicity = otherEthnicity;
    this.povertyRate = povertyRate;
    this.medianAge = medianAge;
    this.medianEarnings = medianEarnings;
  }

  public int getDemographicsKey() {
    return demographicsKey;
  }

  public void setDemographicsKey(int demographicsKey) {
    this.demographicsKey = demographicsKey;
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

  public Double getWhite(){
    return white;
  }
  public void setWhite(Double white){
    this.white = white;
  }

  public Double getAfricanAmerican(){
    return africanAmerican;
  }
  public void setAfricanAmerican(Double africanAmerican){
    this.africanAmerican = africanAmerican;
  }

  public Double getLatino(){
    return latino;
  }
  public void setLatino(Double latino){
    this.latino = latino;
  }

  public Double getNativeAmerican(){
    return latino;
  }
  public void setNativeAmerican(Double nativeAmerican){
    this.nativeAmerican = nativeAmerican;
  }
  public Double getAsianAmerican(){
    return asianAmerican;
  }
  public void setAsianAmerican(Double asianAmerican){
    this.asianAmerican = asianAmerican;
  }

  public Double getOtherEthnicity(){
    return otherEthnicity;
  }
  public void setOtherEthnicity(Double otherEthnicity){
    this.otherEthnicity = otherEthnicity;
  }
  public Double getPovertyRate(){
    return povertyRate;
  }
  public void setPovertyRate(Double povertyRate){
    this.povertyRate = povertyRate;
  }
  public Double getMedianAge(){
    return medianAge;
  }
  public void setMedianAge(Double medianAge){
    this.medianAge = medianAge;
  }

  public Double getMedianEarnings(){
    return medianEarnings;
  }
  public void setMedianEarnings(Double medianEarnings){
    this.medianEarnings = medianEarnings;
  }

}