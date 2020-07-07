/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;
//TODO here onwards
public class CovidByRace  {
  protected Integer maxWaitMinutes;
  protected int  takeOutRestaurantKey;

  public TakeOutMortalityRates(int restaurantKey, String name, String description, String menu,
                               String listedHours, Boolean isActive, String street1, String street2,
                               String city, String state, String zipCode, CuisineType cuisine,
                               County county, Integer maxWaitMinutes) {
    super(restaurantKey, name, description, menu, listedHours, isActive, street1, street2,
            city, state, zipCode, cuisine, county);
    this.takeOutRestaurantKey = restaurantKey;
    this.maxWaitMinutes = maxWaitMinutes;
  }

  public TakeOutMortalityRates(int restaurantKey) {
    super(restaurantKey);
    this.takeOutRestaurantKey = restaurantKey;
  }

  public TakeOutMortalityRates(String name, String description, String menu,
                               String listedHours, Boolean isActive, String street1, String street2,
                               String city, String state, String zipCode, CuisineType cuisine,
                               County county, Integer maxWaitMinutes) {
    super(name, description, menu, listedHours, isActive, street1, street2,
            city, state, zipCode, cuisine, county);
    this.maxWaitMinutes = maxWaitMinutes;
  }

  public Integer getMaxWaitMinutes() {
    return maxWaitMinutes;
  }

  public void setMaxWaitMinutes(Integer maxWaitMinutes) {
    this.maxWaitMinutes = maxWaitMinutes;
  }
}

