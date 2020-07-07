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

import covidify.model.*;


public class MortalityRatesDao {
  protected ConnectionManager connectionManager;
  private static MortalityRatesDao instance = null;

  protected MortalityRatesDao() {
    connectionManager = new ConnectionManager();
  }

  public static MortalityRatesDao getInstance() {
    if (instance == null) {
      instance = new MortalityRatesDao();
    }
    return instance;
  }

  public MortalityRates create(MortalityRates mortalityRates) throws SQLException {
    String insertMortalityRates = "INSERT INTO MortalityRates(UserKey,RestaurantKey) "
            + "VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertMortalityRates, Statement.RETURN_GENERATED_KEYS);
      if (mortalityRates.getCovidByDate().getUserName() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setString(1, mortalityRates.getCovidByDate().getUserName());
      }
      if (mortalityRates.getMortalityRates() == null) {
        insertStmt.setNull(2, Types.INTEGER);
      } else {
        insertStmt.setInt(2, mortalityRates.getMortalityRates().getRestaurantKey());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int mortalityRatesKey = -1;
      if (resultKey.next()) {
        mortalityRatesKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      mortalityRates.setMortalityRatesKey(mortalityRatesKey);
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


  public MortalityRates getMortalityRatesById(int mortalityRatesId) throws SQLException {
    String selectMortalityRates =
            "SELECT MortalityRatesKey,UserKey,RestaurantKey " +
                    "FROM MortalityRates " +
                    "WHERE MortalityRatesKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMortalityRates);
      selectStmt.setInt(1, mortalityRatesId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      RestaurantDao restaurantDao = RestaurantDao.getInstance();
      if (results.next()) {
        int mortalityRatesKey = results.getInt("MortalityRatesKey");
        String userName = results.getString("UserName");
        int restaurantKey = results.getInt("RestaurantKey");

        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantKey);
        MortalityRates mortalityRates = new MortalityRates(mortalityRatesKey, covidByDate, mortalityRates);
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

  public List<MortalityRates> getMortalityRatessByUserName(String userName) throws SQLException {
    List<MortalityRates> mortalityRatess = new ArrayList<MortalityRates>();
    String selectMortalityRates =
            "SELECT MortalityRatesKey,UserKey,RestaurantKey " +
                    "FROM MortalityRates " +
                    "WHERE UserKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMortalityRates);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      CovidByDate covidByDate = populationDao.getUserByUserName(userName);
      RestaurantDao restaurantDao = RestaurantDao.getInstance();
      while (results.next()) {
        int mortalityRatesKey = results.getInt("MortalityRatesKey");
        int restaurantKey = results.getInt("RestaurantKey");

        // Re-using User that we found above to avoid duplicating work
        MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantKey);
        MortalityRates mortalityRates = new MortalityRates(mortalityRatesKey, covidByDate, mortalityRates);
        mortalityRatess.add(mortalityRates);
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
    return mortalityRatess;
  }

  public List<MortalityRates> getMortalityRatessByRestaurantId(int restaurantId) throws SQLException {
    List<MortalityRates> mortalityRatess = new ArrayList<MortalityRates>();
    String selectMortalityRates =
            "SELECT MortalityRatesKey,UserKey,RestaurantKey " +
                    "FROM MortalityRates " +
                    "WHERE RestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectMortalityRates);
      selectStmt.setInt(1, restaurantId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      RestaurantDao restaurantDao = RestaurantDao.getInstance();
      MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantId);
      while (results.next()) {
        int mortalityRatesKey = results.getInt("MortalityRatesKey");
        String userName = results.getString("UserName");

        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        MortalityRates mortalityRates = new MortalityRates(mortalityRatesKey, covidByDate, mortalityRates);
        mortalityRatess.add(mortalityRates);
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
    return mortalityRatess;
  }


  public MortalityRates delete(MortalityRates mortalityRates) throws SQLException {
    String deleteMortalityRates = "DELETE FROM MortalityRates WHERE MortalityRatesKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteMortalityRates);
      deleteStmt.setInt(1, mortalityRates.getMortalityRatesKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the MortalityRates instance.
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
