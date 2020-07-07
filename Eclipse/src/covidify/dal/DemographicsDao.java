/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import covidify.model.*;

public class DemographicsDao extends RestaurantDao {
  private static DemographicsDao instance = null;

  protected DemographicsDao() {
    super();
  }

  public static DemographicsDao getInstance() {
    if (instance == null) {
      instance = new DemographicsDao();
    }
    return instance;
  }


  public Demographics create(Demographics restaurant) throws SQLException {
    // Insert into the superclass table first.
    create(new MortalityRates(restaurant.getRestaurantKey(), restaurant.getName(), restaurant.getDescription(),
            restaurant.getMenu(), restaurant.getListedHours(), restaurant.getIsActive(),
            restaurant.getStreet1(), restaurant.getStreet2(), restaurant.getCity(),
            restaurant.getState(), restaurant.getZipCode(), restaurant.getCuisine(), restaurant.getCounty()));

    String insertRestaurant = "INSERT INTO FoodCartRestaurant(FoodCartRestaurantKey,IsLicensed) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertRestaurant);
      insertStmt.setInt(1, restaurant.getRestaurantKey());
      if (restaurant.getIsLicensed() == null) {
        insertStmt.setNull(2, Types.BOOLEAN);
      } else {
        insertStmt.setBoolean(2, restaurant.getIsLicensed());
      }
      insertStmt.executeUpdate();
      return restaurant;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (insertStmt != null) {
        insertStmt.close();
      }
    }
  }

  public Demographics getFoodCartRestaurantById(int foodCartRestaurantId) throws SQLException {
    String selectRestaurant =
            "SELECT RestaurantKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey,IsLicensed,FoodCartRestaurantKey "
                    + "FROM FoodCartRestaurant "
                    + "INNER JOIN Restaurant "
                    + "ON Restaurant.RestaurantKey = FoodCartRestaurant.FoodCartRestaurantKey "
                    + "WHERE FoodCartRestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setInt(1, foodCartRestaurantId);
      results = selectStmt.executeQuery();
      CountyDao countyDao = CountyDao.getInstance();
      if (results.next()) {
        int restaurantKey = results.getInt("RestaurantKey");
        String name = results.getString("Name");
        String description = results.getString("Description");
        String menu = results.getString("Menu");
        String listedHours = results.getString("ListedHours");
        Boolean isActive = results.getBoolean("IsActive");
        String street1 = results.getString("Street1");
        String street2 = results.getString("Street2");
        String city = results.getString("City");
        String state = results.getString("State");
        String zipCode = results.getString("ZipCode");
        String cuisine = results.getString("Cuisine");
        int companyKey = results.getInt("CompanyKey");
        Boolean isLicensed = results.getBoolean("IsLicensed");

        County county = countyDao.getCompanyById(companyKey);
        Demographics restaurant = new Demographics(restaurantKey, name, description, menu,
                listedHours, isActive, street1, street2, city, state, zipCode,
                MortalityRates.CuisineType.valueOf(cuisine), county, isLicensed);
        return restaurant;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return null;
  }

  public List<Demographics> getFoodCartRestaurantsByCompanyName(String companyName) throws SQLException {
    List<Demographics> restaurants = new ArrayList<Demographics>();
    String selectRestaurant =
            "SELECT RestaurantKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey,MaxWaitMinutes,FoodCartRestaurantKey "
                    + "FROM FoodCartRestaurantKey "
                    + "INNER JOIN Restaurant "
                    + "ON Restaurant.RestaurantKey = FoodCartRestaurant.FoodCartRestaurantKey "
                    + "WHERE CompanyKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      CountyDao countyDao = CountyDao.getInstance();
      County county = countyDao.getCompanyByCompanyName(companyName);
      selectStmt.setInt(1, county.getCompanyKey());
      results = selectStmt.executeQuery();

      while (results.next()) {
        int restaurantKey = results.getInt("RestaurantKey");
        String name = results.getString("Name");
        String description = results.getString("Description");
        String menu = results.getString("Menu");
        String listedHours = results.getString("ListedHours");
        Boolean isActive = results.getBoolean("IsActive");
        String street1 = results.getString("Street1");
        String street2 = results.getString("Street2");
        String city = results.getString("City");
        String state = results.getString("State");
        String zipCode = results.getString("ZipCode");
        String cuisine = results.getString("Cuisine");
        Boolean isLicensed = results.getBoolean("IsLicensed");

        // Re-using company that we found above to avoid duplicating work
        Demographics restaurant = new Demographics(restaurantKey, name, description, menu,
                listedHours, isActive, street1, street2, city, state, zipCode,
                MortalityRates.CuisineType.valueOf(cuisine), county, isLicensed);
        restaurants.add(restaurant);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (selectStmt != null) {
        selectStmt.close();
      }
      if (results != null) {
        results.close();
      }
    }
    return restaurants;
  }


  public Demographics delete(Demographics demographics) throws SQLException {
    String deleteRestaurant = "DELETE FROM FoodCartRestaurant WHERE FoodCartRestaurantKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteRestaurant);
      deleteStmt.setInt(1, demographics.getRestaurantKey());
      deleteStmt.executeUpdate();

      // Also delete from super Restaurant class.
      super.delete(demographics);
      // Return null so the caller can no longer operate on the FoodCartRestaurant instance.
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }

}
