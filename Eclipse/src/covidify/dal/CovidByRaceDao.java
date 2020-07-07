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
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import covidify.model.County;
import covidify.model.MortalityRates;
//TODO here onwards
public class CovidByRaceDao {
  protected ConnectionManager connectionManager;
  private static RestaurantDao instance = null;

  protected RestaurantDao() {
    connectionManager = new ConnectionManager();
  }
  public static RestaurantDao getInstance() {
    if(instance == null) {
      instance = new RestaurantDao();
    }
    return instance;
  }

  public MortalityRates create(MortalityRates mortalityRates) throws SQLException {
    String insertRestaurant = "INSERT INTO Restaurant(Name,Description,Menu,ListedHours,"
            + "IsActive,Street1,Street2,City,State,ZipCode,Cuisine,CompanyKey) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertRestaurant, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setString(1, mortalityRates.getName());
      if (mortalityRates.getDescription() == null) {
        insertStmt.setNull(2, Types.LONGVARCHAR);
      } else {
        insertStmt.setString(2, mortalityRates.getDescription());
      }
      if (mortalityRates.getMenu() == null) {
        insertStmt.setNull(3, Types.LONGVARCHAR);
      } else {
        insertStmt.setString(3, mortalityRates.getMenu());
      }
      if (mortalityRates.getListedHours() == null) {
        insertStmt.setNull(4, Types.LONGVARCHAR);
      } else {
        insertStmt.setString(4, mortalityRates.getListedHours());
      }
      if (mortalityRates.getIsActive() == null) {
        insertStmt.setNull(5, Types.BOOLEAN);
      } else {
        insertStmt.setBoolean(5, mortalityRates.getIsActive());
      }
      if (mortalityRates.getStreet1() == null) {
        insertStmt.setNull(6, Types.VARCHAR);
      } else {
        insertStmt.setString(6, mortalityRates.getStreet1());
      }
      if (mortalityRates.getStreet2() == null) {
        insertStmt.setNull(7, Types.VARCHAR);
      } else {
        insertStmt.setString(7, mortalityRates.getStreet2());
      }
      if (mortalityRates.getCity() == null) {
        insertStmt.setNull(8, Types.VARCHAR);
      } else {
        insertStmt.setString(8, mortalityRates.getCity());
      }
      if (mortalityRates.getState() == null) {
        insertStmt.setNull(9, Types.CHAR);
      } else {
        insertStmt.setString(9, mortalityRates.getState());
      }
      if (mortalityRates.getZipCode() == null) {
        insertStmt.setNull(10, Types.CHAR);
      } else {
        insertStmt.setString(10, mortalityRates.getZipCode());
      }
      if (mortalityRates.getCuisine() == null) {
        // Wasn't sure how to represent ENUMs here as there is no Enum type in Types for SQL
        insertStmt.setNull(11, Types.VARCHAR);
      } else {
        insertStmt.setString(11, mortalityRates.getCuisine().name());
      }
      if (mortalityRates.getCounty() == null) {
        insertStmt.setNull(12, Types.INTEGER);
      } else {
        insertStmt.setInt(12, mortalityRates.getCounty().getCompanyKey());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int restaurantKey = -1;
      if (resultKey.next()) {
        restaurantKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      mortalityRates.setRestaurantKey(restaurantKey);
      return mortalityRates;
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
      if (resultKey != null) {
        resultKey.close();
      }
    }
  }


  public MortalityRates getRestaurantById(int restaurantId) throws SQLException {
    String selectRestaurant =
            "SELECT RestaurantKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey "
                    + "FROM Restaurant "
                    + "WHERE RestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setInt(1, restaurantId);
      results = selectStmt.executeQuery();

      CountyDao countyDao = CountyDao.getInstance();

      if (results.next()) {
        int resultsRestaurantKey = results.getInt("RestaurantKey");
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

        County county = countyDao.getCompanyById(companyKey);
        MortalityRates mortalityRates = new MortalityRates(resultsRestaurantKey, name, description,menu,
                listedHours,isActive,street1,street2,city,state,zipCode,
                MortalityRates.CuisineType.valueOf(cuisine), county);
        return mortalityRates;
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

  // Note: I think there was a typo in the spec for the method argument 'cusine' instead of
  // cuisine, so I made it cuisine instead.
  public List<MortalityRates> getRestaurantsByCuisine(MortalityRates.CuisineType cuisine) throws SQLException{
    List<MortalityRates> restaurants = new ArrayList<MortalityRates>();
    String selectRestaurant =
            "SELECT RestaurantKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey "
                    + "FROM Restaurant "
                    + "WHERE Cuisine=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectRestaurant);
      selectStmt.setString(1, cuisine.toString());
      results = selectStmt.executeQuery();

      CountyDao countyDao = CountyDao.getInstance();
      while(results.next()) {
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
        String cuisineResult = results.getString("Cuisine");
        int companyKey = results.getInt("CompanyKey");

        County county = countyDao.getCompanyById(companyKey);
        MortalityRates mortalityRates = new MortalityRates(restaurantKey, name, description,menu,
                listedHours,isActive,street1,street2,city,state,zipCode,
                MortalityRates.CuisineType.valueOf(cuisineResult), county);
        restaurants.add(mortalityRates);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return restaurants;
  }

  public List<MortalityRates> getRestaurantsByCompanyName(String companyName) throws SQLException{
    List<MortalityRates> restaurants = new ArrayList<MortalityRates>();
    String selectRestaurant =
            "SELECT RestaurantKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey "
                    + "FROM Restaurant "
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


      while(results.next()) {
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

        // Re-using company that we found above to avoid duplicating work
        MortalityRates mortalityRates = new MortalityRates(restaurantKey, name, description,menu,
                listedHours,isActive,street1,street2,city,state,zipCode,
                MortalityRates.CuisineType.valueOf(cuisine), county);
        restaurants.add(mortalityRates);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(selectStmt != null) {
        selectStmt.close();
      }
      if(results != null) {
        results.close();
      }
    }
    return restaurants;
  }


  public MortalityRates delete(MortalityRates mortalityRates) throws SQLException {
    String deleteRestaurant = "DELETE FROM Restaurant WHERE RestaurantKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteRestaurant);
      deleteStmt.setInt(1, mortalityRates.getRestaurantKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Restaurant instance.
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

