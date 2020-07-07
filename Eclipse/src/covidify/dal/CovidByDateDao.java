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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import covidify.model.*;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidByDate` (
  `CovidByDateKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Date` DATE NULL,
  `CovidDeaths` INT NULL,
  `CovidCases` INT NULL,
  PRIMARY KEY (`CovidByDateKey`),
  INDEX `CountyFKey1_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `CovUniq` (`CountyFKey` ASC, `Date` ASC),
  CONSTRAINT `CountyFKey1`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

public class CovidByDateDao {
  protected ConnectionManager connectionManager;
  private static CovidByDateDao instance = null;

  protected CovidByDateDao() {
    connectionManager = new ConnectionManager();
  }
  public static CovidByDateDao getInstance() {
    if(instance == null) {
      instance = new CovidByDateDao();
    }
    return instance;
  }

  public CovidByDate create(CovidByDate covidByDate) throws SQLException {
    String insertCovidByDate = "INSERT INTO CovidByDate(CountyFKey,Date,CovidDeaths,CovidCases) "
            + "VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCovidByDate, Statement.RETURN_GENERATED_KEYS);
      if (covidByDate.getCounty() == null) {
        insertStmt.setNull(1, Types.INTEGER);
      } else {
        insertStmt.setInt(1, covidByDate.getCounty().getCountyKey());
      }
      if (covidByDate.getDate() == null) {
        insertStmt.setNull(2, Types.TIMESTAMP);
      } else {
        insertStmt.setTimestamp(2,new Timestamp(covidByDate.getDate().getTime()));
      }
      if (covidByDate.getCovidDeaths() == null) {
        insertStmt.setNull(3, Types.INTEGER);
      } else {
        insertStmt.setInt(3, covidByDate.getCovidDeaths());
      }
      if (covidByDate.getCovidCases() == null) {
        insertStmt.setNull(4, Types.INTEGER);
      } else {
        insertStmt.setInt(4, covidByDate.getCovidCases());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int covidByDateKey = -1;
      if (resultKey.next()) {
        covidByDateKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      covidByDate.setCovidByDateKey(covidByDateKey);
      return covidByDate;
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

  public CovidByDate getCovidByDateById(int covidByDateId) throws SQLException {
    String selectCovidByDate =
            "SELECT CovidByDateKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey "
                    + "FROM CovidByDate "
                    + "WHERE CovidByDateKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCovidByDate);
      selectStmt.setInt(1, covidByDateId);
      results = selectStmt.executeQuery();

      CountyDao countyDao = CountyDao.getInstance();

      if (results.next()) {
        int resultsCovidByDateKey = results.getInt("CovidByDateKey");
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
        CovidByDate covidByDate = new CovidByDate(resultsCovidByDateKey, name, description,menu,
                listedHours,isActive,street1,street2,city,state,zipCode,
                CovidByDate.CuisineType.valueOf(cuisine), county);
        return covidByDate;
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
  public List<CovidByDate> getCovidByDatesByCuisine(CovidByDate.CuisineType cuisine) throws SQLException{
    List<CovidByDate> covidByDates = new ArrayList<CovidByDate>();
    String selectCovidByDate =
            "SELECT CovidByDateKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey "
                    + "FROM CovidByDate "
                    + "WHERE Cuisine=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCovidByDate);
      selectStmt.setString(1, cuisine.toString());
      results = selectStmt.executeQuery();

      CountyDao countyDao = CountyDao.getInstance();
      while(results.next()) {
        int covidByDateKey = results.getInt("CovidByDateKey");
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
        CovidByDate covidByDate = new CovidByDate(covidByDateKey, name, description,menu,
                listedHours,isActive,street1,street2,city,state,zipCode,
                CovidByDate.CuisineType.valueOf(cuisineResult), county);
        covidByDates.add(covidByDate);
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
    return covidByDates;
  }

  public List<CovidByDate> getCovidByDatesByCompanyName(String companyName) throws SQLException{
    List<CovidByDate> covidByDates = new ArrayList<CovidByDate>();
    String selectCovidByDate =
            "SELECT CovidByDateKey,Name,Description,Menu,ListedHours,IsActive,Street1,"
                    + "Street2,City,State,ZipCode,Cuisine,CompanyKey "
                    + "FROM CovidByDate "
                    + "WHERE CompanyKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCovidByDate);

      CountyDao countyDao = CountyDao.getInstance();
      County county = countyDao.getCompanyByCompanyName(companyName);

      selectStmt.setInt(1, county.getCompanyKey());
      results = selectStmt.executeQuery();


      while(results.next()) {
        int covidByDateKey = results.getInt("CovidByDateKey");
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
        CovidByDate covidByDate = new CovidByDate(covidByDateKey, name, description,menu,
                listedHours,isActive,street1,street2,city,state,zipCode,
                CovidByDate.CuisineType.valueOf(cuisine), county);
        covidByDates.add(covidByDate);
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
    return covidByDates;
  }


  public CovidByDate delete(CovidByDate covidByDate) throws SQLException {
    String deleteCovidByDate = "DELETE FROM CovidByDate WHERE CovidByDateKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCovidByDate);
      deleteStmt.setInt(1, covidByDate.getCovidByDateKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the CovidByDate instance.
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

