/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

public class StateGovernor extends MortalityRates {
  protected Integer capacity;
  protected int sitDownRestaurantKey;

  public StateGovernor(int restaurantKey, String name, String description, String menu,
                       String listedHours, Boolean isActive, String street1, String street2,
                       String city, String state, String zipCode, CuisineType cuisine,
                       County county, Integer capacity) {
    super(restaurantKey, name, description, menu, listedHours, isActive, street1, street2,
            city, state, zipCode, cuisine, county);
    this.sitDownRestaurantKey = restaurantKey;
    this.capacity = capacity;
  }

  public StateGovernor(int restaurantKey) {
    super(restaurantKey);
    this.sitDownRestaurantKey = restaurantKey;
  }

  public StateGovernor(String name, String description, String menu,
                       String listedHours, Boolean isActive, String street1, String street2,
                       String city, String state, String zipCode, CuisineType cuisine,
                       County county, Integer capacity) {
    super(name, description, menu, listedHours, isActive, street1, street2,
            city, state, zipCode, cuisine, county);
    this.capacity = capacity;
  }

  public Integer getCapacity() {
    return capacity;
  }

  public void setCapacity(Integer capacity) {
    this.capacity = capacity;
  }
}
