/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

public class Demographics {
  protected Boolean isLicensed;
  protected int foodCartRestaurantKey;

  public Demographics(int restaurantKey, String name, String description, String menu,
                      String listedHours, Boolean isActive, String street1, String street2,
                      String city, String state, String zipCode, CuisineType cuisine,
                      County county, Boolean isLicensed) {
    super(restaurantKey, name, description, menu, listedHours, isActive, street1, street2,
            city, state, zipCode, cuisine, county);
    this.foodCartRestaurantKey = restaurantKey;
    this.isLicensed = isLicensed;
  }

  public Demographics(int restaurantKey) {
    super(restaurantKey);
    this.foodCartRestaurantKey = restaurantKey;
  }

  public Demographics(String name, String description, String menu,
                      String listedHours, Boolean isActive, String street1, String street2,
                      String city, String state, String zipCode, CuisineType cuisine,
                      County county, Boolean isLicensed) {
    super(name, description, menu, listedHours, isActive, street1, street2,
            city, state, zipCode, cuisine, county);
    this.foodCartRestaurantKey = restaurantKey;
    this.isLicensed = isLicensed;
  }

  public Boolean getIsLicensed() {
    return isLicensed;
  }

  public void setIsLicensed(Boolean isLicensed) {
    this.isLicensed = isLicensed;
  }
}
/*
CREATE TABLE IF NOT EXISTS `restdb`.`FoodCartRestaurant` (
  `FoodCartRestaurantKey` INT NOT NULL,
  `IsLicensed` TINYINT NULL,
  INDEX `RestaurantKey3_idx` (`FoodCartRestaurantKey` ASC),
  PRIMARY KEY (`FoodCartRestaurantKey`),
  CONSTRAINT `RestaurantKey3`
    FOREIGN KEY (`FoodCartRestaurantKey`)
    REFERENCES `restdb`.`Restaurant` (`RestaurantKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 */