/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */

package covidify.dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import covidify.model.*;

public class StateGovernorDao {
  protected ConnectionManager connectionManager;
  private static StateGovernorDao instance = null;

  protected StateGovernorDao() {
    connectionManager = new ConnectionManager();
  }

  public static StateGovernorDao getInstance() {
    if (instance == null) {
      instance = new StateGovernorDao();
    }
    return instance;
  }

  public StateGovernor create(StateGovernor countyHospitalData) throws SQLException {
    String insertReview = "INSERT INTO Review(UserName,RestaurantKey,CreatedWhen,WrittenContent,Rating) "
            + "VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertReview, Statement.RETURN_GENERATED_KEYS);
      if (countyHospitalData.getCovidByDate().getUserName() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setString(1, countyHospitalData.getCovidByDate().getUserName());
      }
      if (countyHospitalData.getMortalityRates() == null) {
        insertStmt.setNull(2, Types.INTEGER);
      } else {
        insertStmt.setInt(2, countyHospitalData.getMortalityRates().getRestaurantKey());
      }
      if (countyHospitalData.getCreatedWhen() == null) {
        insertStmt.setNull(3, Types.TIMESTAMP);
      } else {
        insertStmt.setTimestamp(3, new Timestamp(countyHospitalData.getCreatedWhen().getTime()));
      }
      if (countyHospitalData.getWrittenContent() == null) {
        insertStmt.setNull(4, Types.LONGVARCHAR);
      } else {
        insertStmt.setString(4, countyHospitalData.getWrittenContent());
      }
      if (countyHospitalData.getRating() == null) {
        insertStmt.setNull(5, Types.DECIMAL);
      } else {
        insertStmt.setFloat(5, countyHospitalData.getRating());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int reviewKey = -1;
      if (resultKey.next()) {
        reviewKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      countyHospitalData.setReviewKey(reviewKey);
      return countyHospitalData;
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


  public StateGovernor getReviewById(int reviewId) throws SQLException {
    String selectReview =
            "SELECT ReviewKey,UserName,RestaurantKey,CreatedWhen,WrittenContent,Rating " +
                    "FROM Review " +
                    "WHERE ReviewKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReview);
      selectStmt.setInt(1, reviewId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      RestaurantDao restaurantDao = RestaurantDao.getInstance();
      if (results.next()) {
        int reviewKey = results.getInt("ReviewKey");
        String userName = results.getString("UserName");
        int restaurantKey = results.getInt("RestaurantKey");
        ;
        Date createdWhen = new Date(results.getTimestamp("CreatedWhen").getTime());
        String writtenContent = results.getString("WrittenContent");
        Float rating = results.getFloat("Rating");


        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantKey);
        StateGovernor countyHospitalData = new StateGovernor(reviewKey, covidByDate, mortalityRates, createdWhen, writtenContent, rating);
        return countyHospitalData;
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

  public List<StateGovernor> getReviewsByUserName(String userName) throws SQLException {
    List<StateGovernor> reviews = new ArrayList<StateGovernor>();
    String selectReview =
            "SELECT ReviewKey,UserName,RestaurantKey,CreatedWhen,WrittenContent,Rating " +
                    "FROM Review " +
                    "WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReview);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      CovidByDate covidByDate = populationDao.getUserByUserName(userName);
      RestaurantDao restaurantDao = RestaurantDao.getInstance();

      while (results.next()) {
        int reviewKey = results.getInt("ReviewKey");
        int restaurantKey = results.getInt("RestaurantKey");
        ;
        Date createdWhen = new Date(results.getTimestamp("CreatedWhen").getTime());
        String writtenContent = results.getString("WrittenContent");
        Float rating = results.getFloat("Rating");

        // Re-using User that we found above to avoid duplicating work
        MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantKey);
        StateGovernor countyHospitalData = new StateGovernor(reviewKey, covidByDate, mortalityRates, createdWhen, writtenContent, rating);
        reviews.add(countyHospitalData);
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
    return reviews;
  }

  public List<StateGovernor> getReviewsByRestaurantId(int restaurantId) throws SQLException {
    List<StateGovernor> reviews = new ArrayList<StateGovernor>();
    String selectReview =
            "SELECT ReviewKey,UserName,RestaurantKey,CreatedWhen,WrittenContent,Rating " +
                    "FROM Review " +
                    "WHERE RestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReview);
      selectStmt.setInt(1, restaurantId);
      results = selectStmt.executeQuery();


      PopulationDao populationDao = PopulationDao.getInstance();
      RestaurantDao restaurantDao = RestaurantDao.getInstance();
      MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantId);
      while (results.next()) {
        int reviewKey = results.getInt("ReviewKey");
        String userName = results.getString("UserName");
        Date createdWhen = new Date(results.getTimestamp("CreatedWhen").getTime());
        String writtenContent = results.getString("WrittenContent");
        Float rating = results.getFloat("Rating");

        // Re-using Restaurant that we found above to avoid duplicating work
        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        StateGovernor countyHospitalData = new StateGovernor(reviewKey, covidByDate, mortalityRates, createdWhen, writtenContent, rating);
        reviews.add(countyHospitalData);
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
    return reviews;
  }

  public StateGovernor delete(StateGovernor countyHospitalData) throws SQLException {
    String deleteReview = "DELETE FROM Review WHERE ReviewKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteReview);
      deleteStmt.setInt(1, countyHospitalData.getReviewKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Review instance.
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
