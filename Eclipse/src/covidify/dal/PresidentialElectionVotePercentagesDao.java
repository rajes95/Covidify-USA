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
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`PresidentialElectionVotePercentages` (
  `PresidentialElectionVotePercentagesKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `DemocratsPercent` DECIMAL(5,2) NULL,
  `RepublicansPercent` DECIMAL(5,2) NULL,
  `OtherPercent` DECIMAL(5,2) NULL,
  PRIMARY KEY (`PresidentialElectionVotePercentagesKey`),
  INDEX `CountyFKey2_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey2`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;
 */

public class PresidentialElectionVotePercentagesDao {
  protected ConnectionManager connectionManager;
  private static PresidentialElectionVotePercentagesDao instance = null;

  protected PresidentialElectionVotePercentagesDao() {
    connectionManager = new ConnectionManager();
  }
  public static PresidentialElectionVotePercentagesDao getInstance() {
    if(instance == null) {
      instance = new PresidentialElectionVotePercentagesDao();
    }
    return instance;
  }

  public PresidentialElectionVotePercentages create(PresidentialElectionVotePercentages presidentialElectionVotePercentages) throws SQLException {
    String insertPresidentialElectionVotePercentages = "INSERT INTO PresidentialElectionVotePercentages(CountyFKey,Year,DemocratsPercent,RepublicansPercent,OtherPercent) "
            + "VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertPresidentialElectionVotePercentages, Statement.RETURN_GENERATED_KEYS);
      if (presidentialElectionVotePercentages.getCounty() == null) {
        insertStmt.setNull(1, Types.INTEGER);
      } else {
        insertStmt.setInt(1, presidentialElectionVotePercentages.getCounty().getCountyKey());
      }
      //TODO Not sure about this data type
      if (presidentialElectionVotePercentages.getDemocratsPercent() == null) {
        insertStmt.setNull(2, Types.DATE);
      } else {
        insertStmt.setDate(2, presidentialElectionVotePercentages.getYear());
      }
      if (presidentialElectionVotePercentages.getDemocratsPercent() == null) {
        insertStmt.setNull(3, Types.DECIMAL);
      } else {
        insertStmt.setFloat(3, presidentialElectionVotePercentages.getDemocratsPercent());
      }
      if (presidentialElectionVotePercentages.getRepublicansPercent() == null) {
        insertStmt.setNull(4, Types.DECIMAL);
      } else {
        insertStmt.setFloat(4, presidentialElectionVotePercentages.getRepublicansPercent());
      }
      if (presidentialElectionVotePercentages.getOtherPercent() == null) {
        insertStmt.setNull(5, Types.DECIMAL);
      } else {
        insertStmt.setFloat(5, presidentialElectionVotePercentages.getOtherPercent());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int presidentialElectionVotePercentagesKey = -1;
      if (resultKey.next()) {
        presidentialElectionVotePercentagesKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      presidentialElectionVotePercentages.setPresidentialElectionVotePercentagesKey(presidentialElectionVotePercentagesKey);
      return presidentialElectionVotePercentages;
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



  //TODO
  /*
  public PresidentialElectionVotePercentages getPresidentialElectionVotePercentagesById(int presidentialElectionVotePercentagesId) throws SQLException {
    String selectPresidentialElectionVotePercentages =
            "SELECT PresidentialElectionVotePercentagesKey,UserName,SitDownRestaurantKey,Start,End,PartySize " +
                    "FROM PresidentialElectionVotePercentages " +
                    "WHERE PresidentialElectionVotePercentagesKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPresidentialElectionVotePercentages);
      selectStmt.setInt(1, presidentialElectionVotePercentagesId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      StateDao stateDao = StateDao.getInstance();
      if (results.next()) {
        int presidentialElectionVotePercentagesKey = results.getInt("PresidentialElectionVotePercentagesKey");
        String userName = results.getString("UserName");
        int restaurantKey = results.getInt("SitDownRestaurantKey");
        Date start = new Date(results.getTimestamp("Start").getTime());
        Date end = new Date(results.getTimestamp("End").getTime());
        Integer partySize = results.getInt("PartySize");

        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        StateGovernor restaurant = stateDao.getSitDownRestaurantById(restaurantKey);
        PresidentialElectionVotePercentages presidentialElectionVotePercentages = new PresidentialElectionVotePercentages(presidentialElectionVotePercentagesKey, covidByDate,restaurant,start,end,partySize);
        return presidentialElectionVotePercentages;
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

  public List<PresidentialElectionVotePercentages> getPresidentialElectionVotePercentagessByUserName(String userName) throws SQLException {
    List<PresidentialElectionVotePercentages> presidentialElectionVotePercentagess = new ArrayList<PresidentialElectionVotePercentages>();
    String selectPresidentialElectionVotePercentages =
            "SELECT PresidentialElectionVotePercentagesKey,UserName,SitDownRestaurantKey,Start,End,PartySize " +
                    "FROM PresidentialElectionVotePercentages " +
                    "WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPresidentialElectionVotePercentages);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      CovidByDate covidByDate = populationDao.getUserByUserName(userName);
      StateDao stateDao = StateDao.getInstance();
      while (results.next()) {
        int presidentialElectionVotePercentagesKey = results.getInt("PresidentialElectionVotePercentagesKey");
        int restaurantKey = results.getInt("SitDownRestaurantKey");
        Date start = new Date(results.getTimestamp("Start").getTime());
        Date end = new Date(results.getTimestamp("End").getTime());
        Integer partySize = results.getInt("PartySize");

        // Re-using User that we found above to avoid duplicating work
        StateGovernor restaurant = stateDao.getSitDownRestaurantById(restaurantKey);
        PresidentialElectionVotePercentages presidentialElectionVotePercentages = new PresidentialElectionVotePercentages(presidentialElectionVotePercentagesKey, covidByDate,restaurant,start,end,partySize);
        presidentialElectionVotePercentagess.add(presidentialElectionVotePercentages);
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
    return presidentialElectionVotePercentagess;
  }

  public List<PresidentialElectionVotePercentages> getPresidentialElectionVotePercentagessBySitDownRestaurantId(int sitDownRestaurantId) throws SQLException {
    List<PresidentialElectionVotePercentages> presidentialElectionVotePercentagess = new ArrayList<PresidentialElectionVotePercentages>();
    String selectPresidentialElectionVotePercentages =
            "SELECT PresidentialElectionVotePercentagesKey,UserName,SitDownRestaurantKey,Start,End,PartySize " +
                    "FROM PresidentialElectionVotePercentages " +
                    "WHERE SitDownRestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectPresidentialElectionVotePercentages);
      selectStmt.setInt(1, sitDownRestaurantId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      StateDao stateDao = StateDao.getInstance();
      StateGovernor restaurant = stateDao.getSitDownRestaurantById(sitDownRestaurantId);
      while (results.next()) {
        int presidentialElectionVotePercentagesKey = results.getInt("PresidentialElectionVotePercentagesKey");
        String userName = results.getString("UserName");
        Date start = new Date(results.getTimestamp("Start").getTime());
        Date end = new Date(results.getTimestamp("End").getTime());
        Integer partySize = results.getInt("PartySize");

        // Re-using SitDownRestaurant that we found above to avoid duplicating work
        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        PresidentialElectionVotePercentages presidentialElectionVotePercentages = new PresidentialElectionVotePercentages(presidentialElectionVotePercentagesKey, covidByDate, restaurant, start, end, partySize);
        presidentialElectionVotePercentagess.add(presidentialElectionVotePercentages);
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
    return presidentialElectionVotePercentagess;
  }
*/


  public PresidentialElectionVotePercentages delete(PresidentialElectionVotePercentages presidentialElectionVotePercentages) throws SQLException {
    String deletePresidentialElectionVotePercentages = "DELETE FROM PresidentialElectionVotePercentages WHERE PresidentialElectionVotePercentagesKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deletePresidentialElectionVotePercentages);
      deleteStmt.setInt(1, presidentialElectionVotePercentages.getPresidentialElectionVotePercentagesKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the PresidentialElectionVotePercentages instance.
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
