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

public class StateHospitalDataDao  {
  protected ConnectionManager connectionManager;
  private static StateHospitalDataDao instance = null;

  protected StateHospitalDataDao() {
    connectionManager = new ConnectionManager();
  }

  public static StateHospitalDataDao getInstance() {
    if (instance == null) {
      instance = new StateHospitalDataDao();
    }
    return instance;
  }

  public StateHospitalData create(StateHospitalData stateHospitalData) throws SQLException {
    // Insert into the superclass table first.
    create(new MortalityRates(stateHospitalData.getRestaurantKey(), stateHospitalData.getName(), stateHospitalData.getDescription(),
            stateHospitalData.getMenu(), stateHospitalData.getListedHours(), stateHospitalData.getIsActive(),
            stateHospitalData.getStreet1(), stateHospitalData.getStreet2(), stateHospitalData.getCity(),
            stateHospitalData.getState(), stateHospitalData.getZipCode(), stateHospitalData.getCuisine(), stateHospitalData.getCounty()));

    String insertRestaurant = "INSERT INTO TakeOutRestaurant(TakeOutRestaurantKey,MaxWaitMinutes) VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertRestaurant);
      insertStmt.setInt(1, stateHospitalData.getRestaurantKey());
      if (stateHospitalData.getMaxWaitMinutes() == null) {
        insertStmt.setNull(2, Types.INTEGER);
      } else {
        insertStmt.setInt(2, stateHospitalData.getMaxWaitMinutes());
      }
      insertStmt.executeUpdate();
      return stateHospitalData;
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



  public StateHospitalData getTakeOutRestaurantById(int takeOutRestaurantId) throws SQLException {
    String selectRestaurant =
            "SELECT RestaurantKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey,MaxWaitMinutes,TakeOutRestaurantKey "
                    + "FROM TakeOutRestaurant "
                    + "INNER JOIN Restaurant "
                    + "ON Restaurant.RestaurantKey = TakeOutRestaurant.TakeOutRestaurantKey "
                    + "WHERE TakeOutRestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setInt(1, takeOutRestaurantId);
      results = selectStmt.executeQuery();
      CountyDao countyDao = CountyDao.getInstance();
      if (results.next()) {
        int stateHospitalDataKey = results.getInt("RestaurantKey");
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
        Integer maxWaitMinutes = results.getInt("MaxWaitMinutes");

        County county = countyDao.getCompanyById(companyKey);
        StateHospitalData stateHospitalData = new StateHospitalData(stateHospitalDataKey, name, description, menu,
                listedHours, isActive, street1, street2, city, state, zipCode,
                MortalityRates.CuisineType.valueOf(cuisine), county, maxWaitMinutes);
        return stateHospitalData;
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

  public List<StateHospitalData> getTakeOutRestaurantsByCompanyName(String companyName) throws SQLException {
    List<StateHospitalData> stateHospitalDatas = new ArrayList<StateHospitalData>();
    String selectRestaurant =
            "SELECT RestaurantKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey,MaxWaitMinutes,TakeOutRestaurantKey "
                    + "FROM TakeOutRestaurantKey "
                    + "INNER JOIN Restaurant "
                    + "ON Restaurant.RestaurantKey = TakeOutRestaurant.TakeOutRestaurantKey "
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
        int stateHospitalDataKey = results.getInt("RestaurantKey");
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
        Integer maxWaitMinutes = results.getInt("MaxWaitMinutes");

        // Re-using company that we found above to avoid duplicating work
        StateHospitalData stateHospitalData = new StateHospitalData(stateHospitalDataKey, name, description, menu,
                listedHours, isActive, street1, street2, city, state, zipCode,
                MortalityRates.CuisineType.valueOf(cuisine), county, maxWaitMinutes);
        stateHospitalDatas.add(stateHospitalData);
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
    return stateHospitalDatas;
  }

  public StateHospitalData delete(StateHospitalData takeOutRestaurant) throws SQLException {
    String deleteRestaurant = "DELETE FROM TakeOutRestaurant WHERE TakeOutRestaurantKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteRestaurant);
      deleteStmt.setInt(1, takeOutRestaurant.getRestaurantKey());
      deleteStmt.executeUpdate();

      // Also delete from super Restaurant class.
      super.delete(takeOutRestaurant);
      // Return null so the caller can no longer operate on the TakeOutRestaurant instance.
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
