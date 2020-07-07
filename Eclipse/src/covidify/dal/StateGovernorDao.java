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

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateGovernor` (
  `StateGovernorKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `Governor` VARCHAR(45) NULL,
  `GovernorParty` ENUM('Democratic', 'Republican', 'Other') NULL,
  PRIMARY KEY (`StateGovernorKey`),
  INDEX `StateFKey1_idx` (`StateFKey` ASC),
  UNIQUE INDEX `Unique` (`StateFKey` ASC, `Year` ASC),
  CONSTRAINT `StateFKey1`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
 */

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

  public StateGovernor create(StateGovernor stateGovernor) throws SQLException {
    String insertStateGovernor = "INSERT INTO StateGovernor(StateFKey,Year,Governor,GovernorParty) "
            + "VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertStateGovernor, Statement.RETURN_GENERATED_KEYS);
      if (stateGovernor.getState() == null) {
        insertStmt.setNull(1, Types.INTEGER);
      } else {
        insertStmt.setInt(1, stateGovernor.getState().getStateKey());
      }
      if (stateGovernor.getYear() == null) {
        insertStmt.setNull(2, Types.DATE);
      } else {
        insertStmt.setDate(2, stateGovernor.getYear());
      }
      if (stateGovernor.getGovernor() == null) {
        insertStmt.setNull(3, Types.VARCHAR);
      } else {
        insertStmt.setString(3, stateGovernor.getGovernor());
      }
      if (stateGovernor.getGovernorParty() == null) {
        insertStmt.setNull(4, Types.VARCHAR);
      } else {
        insertStmt.setString(4, stateGovernor.getGovernorParty().toString());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int stateGovernorKey = -1;
      if (resultKey.next()) {
        stateGovernorKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      stateGovernor.setStateGovernorKey(stateGovernorKey);
      return stateGovernor;
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


  //TODO here onwards
/*
  public StateGovernor getStateGovernorById(int stateGovernorId) throws SQLException {
    String selectStateGovernor =
            "SELECT StateGovernorKey,UserName,RestaurantKey,CreatedWhen,WrittenContent,Rating " +
                    "FROM StateGovernor " +
                    "WHERE StateGovernorKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStateGovernor);
      selectStmt.setInt(1, stateGovernorId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      RestaurantDao restaurantDao = RestaurantDao.getInstance();
      if (results.next()) {
        int stateGovernorKey = results.getInt("StateGovernorKey");
        String userName = results.getString("UserName");
        int restaurantKey = results.getInt("RestaurantKey");
        ;
        Date createdWhen = new Date(results.getTimestamp("CreatedWhen").getTime());
        String writtenContent = results.getString("WrittenContent");
        Float rating = results.getFloat("Rating");


        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantKey);
        StateGovernor stateGovernor = new StateGovernor(stateGovernorKey, covidByDate, mortalityRates, createdWhen, writtenContent, rating);
        return stateGovernor;
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

  public List<StateGovernor> getStateGovernorsByUserName(String userName) throws SQLException {
    List<StateGovernor> stateGovernors = new ArrayList<StateGovernor>();
    String selectStateGovernor =
            "SELECT StateGovernorKey,UserName,RestaurantKey,CreatedWhen,WrittenContent,Rating " +
                    "FROM StateGovernor " +
                    "WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStateGovernor);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      CovidByDate covidByDate = populationDao.getUserByUserName(userName);
      RestaurantDao restaurantDao = RestaurantDao.getInstance();

      while (results.next()) {
        int stateGovernorKey = results.getInt("StateGovernorKey");
        int restaurantKey = results.getInt("RestaurantKey");
        ;
        Date createdWhen = new Date(results.getTimestamp("CreatedWhen").getTime());
        String writtenContent = results.getString("WrittenContent");
        Float rating = results.getFloat("Rating");

        // Re-using User that we found above to avoid duplicating work
        MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantKey);
        StateGovernor stateGovernor = new StateGovernor(stateGovernorKey, covidByDate, mortalityRates, createdWhen, writtenContent, rating);
        stateGovernors.add(stateGovernor);
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
    return stateGovernors;
  }

  public List<StateGovernor> getStateGovernorsByRestaurantId(int restaurantId) throws SQLException {
    List<StateGovernor> stateGovernors = new ArrayList<StateGovernor>();
    String selectStateGovernor =
            "SELECT StateGovernorKey,UserName,RestaurantKey,CreatedWhen,WrittenContent,Rating " +
                    "FROM StateGovernor " +
                    "WHERE RestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectStateGovernor);
      selectStmt.setInt(1, restaurantId);
      results = selectStmt.executeQuery();


      PopulationDao populationDao = PopulationDao.getInstance();
      RestaurantDao restaurantDao = RestaurantDao.getInstance();
      MortalityRates mortalityRates = restaurantDao.getRestaurantById(restaurantId);
      while (results.next()) {
        int stateGovernorKey = results.getInt("StateGovernorKey");
        String userName = results.getString("UserName");
        Date createdWhen = new Date(results.getTimestamp("CreatedWhen").getTime());
        String writtenContent = results.getString("WrittenContent");
        Float rating = results.getFloat("Rating");

        // Re-using Restaurant that we found above to avoid duplicating work
        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        StateGovernor stateGovernor = new StateGovernor(stateGovernorKey, covidByDate, mortalityRates, createdWhen, writtenContent, rating);
        stateGovernors.add(stateGovernor);
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
    return stateGovernors;
  }
*/
    public StateGovernor delete(StateGovernor stateGovernor) throws SQLException {
    String deleteStateGovernor = "DELETE FROM StateGovernor WHERE StateGovernorKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteStateGovernor);
      deleteStmt.setInt(1, stateGovernor.getStateGovernorKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the StateGovernor instance.
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
