/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;
//TODO here onwards

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`MortalityRates` (
  `MortalityRatesKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `NeonatalDisordersMortalityRate` DECIMAL(10,2) NULL,
  `HIVAIDSandTBMortalityRate` DECIMAL(10,2) NULL,
  `DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate` DECIMAL(10,2) NULL,
  `ChronicRespiratoryDiseasesMortalityRate` DECIMAL(10,2) NULL,
  `LiverDiseaseMortalityRate` DECIMAL(10,2) NULL,
  `NutritionalDeficienciesMortalityRate` DECIMAL(10,2) NULL,
  `CardiovascularDiseasesMortalityRate` DECIMAL(10,2) NULL,
  PRIMARY KEY (`MortalityRatesKey`),
  INDEX `CountyFKey6_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey6`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */
public class MortalityRates {
  protected int restaurantKey;
  protected String name;
  protected String description;
  protected String menu;
  protected String listedHours;
  protected Boolean isActive;
  protected String street1;
  protected String street2;
  protected String city;
  protected String state;
  protected String zipCode;
  protected CuisineType cuisine;
  protected County county;

  public enum CuisineType {
    african, american, asian, european, hispanic
  }

  public MortalityRates(int restaurantKey, String name, String description, String menu,
                        String listedHours, Boolean isActive, String street1, String street2,
                        String city, String state, String zipCode, CuisineType cuisine, County county) {
    this.restaurantKey = restaurantKey;
    this.name = name;
    this.description = description;
    this.menu = menu;
    this.listedHours = listedHours;
    this.isActive = isActive;
    this.street1 = street1;
    this.street2 = street2;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.cuisine = cuisine;
    this.county = county;
  }

  public MortalityRates(int restaurantKey) {
    this.restaurantKey = restaurantKey;
  }

  public MortalityRates(String name, String description, String menu,
                        String listedHours, Boolean isActive, String street1, String street2,
                        String city, String state, String zipCode, CuisineType cuisine, County county) {
    this.name = name;
    this.description = description;
    this.menu = menu;
    this.listedHours = listedHours;
    this.isActive = isActive;
    this.street1 = street1;
    this.street2 = street2;
    this.city = city;
    this.state = state;
    this.zipCode = zipCode;
    this.cuisine = cuisine;
    this.county = county;
  }

  public int getRestaurantKey() {
    return restaurantKey;
  }

  public void setRestaurantKey(int restaurantKey) {
    this.restaurantKey = restaurantKey;
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

  public String getMenu() {
    return menu;
  }

  public void setMenu(String menu) {
    this.menu = menu;
  }

  public String getListedHours() {
    return listedHours;
  }

  public void setListedHours(String listedHours) {
    this.listedHours = listedHours;
  }

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public String getStreet1() {
    return street1;
  }

  public void setStreet1(String street1) {
    this.street1 = street1;
  }

  public String getStreet2() {
    return street2;
  }

  public void setStreet2(String street2) {
    this.street2 = street2;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public CuisineType getCuisine() {
    return cuisine;
  }

  public void setCuisine(CuisineType cuisine) {
    this.cuisine = cuisine;
  }

  public County getCounty() {
    return county;
  }

  public void setCounty(County county) {
    this.county = county;
  }

}
