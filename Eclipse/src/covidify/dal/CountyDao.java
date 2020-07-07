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

import covidify.model.*;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`County` (
  `CountyKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `CountyFIPS` VARCHAR(45) NULL,
  `CountyName` VARCHAR(100) NULL,
  `Longitude` VARCHAR(45) NULL,
  `Latitude` VARCHAR(45) NULL,
  PRIMARY KEY (`CountyKey`),
  UNIQUE INDEX `CountyFIPS_UNIQUE` (`CountyFIPS` ASC),
  INDEX `StateFKey_idx` (`StateFKey` ASC),
  CONSTRAINT `StateFKey`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT)
ENGINE = InnoDB;

 */

public class CountyDao {
  protected ConnectionManager connectionManager;
  private static CountyDao instance = null;

  protected CountyDao() {
    connectionManager = new ConnectionManager();
  }

  public static CountyDao getInstance() {
    if (instance == null) {
      instance = new CountyDao();
    }
    return instance;
  }


  public County create(County county) throws SQLException {
    String insertCounty = "INSERT INTO County(StateFKey,CountyFIPS,CountyName,Longitude,Latitude) "
            + "VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCounty, Statement.RETURN_GENERATED_KEYS);
      if (county.getState() == null) {
        insertStmt.setNull(1, Types.INTEGER);
      } else {
        insertStmt.setInt(1, county.getState().getStateKey());
      }
      if (county.getCountyFIPS() == null) {
        insertStmt.setNull(2, Types.VARCHAR);
      } else {
        insertStmt.setString(2, county.getCountyFIPS());
      }
      if (county.getCountyName() == null) {
        insertStmt.setNull(3, Types.VARCHAR);
      } else {
        insertStmt.setString(3, county.getCountyName());
      }
      if (county.getLongitude() == null) {
        insertStmt.setNull(4, Types.VARCHAR);
      } else {
        insertStmt.setString(4, county.getLongitude());
      }
      if (county.getLatitude() == null) {
        insertStmt.setNull(5, Types.VARCHAR);
      } else {
        insertStmt.setString(5, county.getLatitude());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int countyKey = -1;
      if (resultKey.next()) {
        countyKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      county.setCountyKey(countyKey);
      return county;
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


  public County getCountyByFIPS(String countyFIPs) throws SQLException {
    String selectCounty =
            "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude " +
                    "FROM County " +
                    "WHERE CountyFIPs=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCounty);
      selectStmt.setString(1, countyFIPs);
      results = selectStmt.executeQuery();
      StateDao stateDao = StateDao.getInstance();

      if (results.next()) {
        int countyKey = results.getInt("CountyKey");
        int stateFKey = results.getInt("StateFKey");
        String countyName = results.getString("CountyName");
        String longitude = results.getString("Longitude");
        String latitude = results.getString("Latitude");

        State state = stateDao.getStateByKey(stateFKey);

        County county = new County(countyKey, state, countyFIPs, countyName, longitude, latitude);
        return county;
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


  public County delete(County county) throws SQLException {
    String deleteCounty = "DELETE FROM County WHERE CountyKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCounty);
      deleteStmt.setInt(1, county.getCountyKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the County instance.
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
