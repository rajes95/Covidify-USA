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

import covidify.model.County;

public class ClimateDao {
  protected ConnectionManager connectionManager;
  private static ClimateDao instance = null;

  protected ClimateDao() {
    connectionManager = new ConnectionManager();
  }

  public static ClimateDao getInstance() {
    if (instance == null) {
      instance = new ClimateDao();
    }
    return instance;
  }


  public County create(County county) throws SQLException {
    String insertCompany = "INSERT INTO Company(Name,Description) "
            + "VALUES(?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCompany, Statement.RETURN_GENERATED_KEYS);
      if (county.getDescription() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setString(1, county.getName());
      }
      if (county.getDescription() == null) {
        insertStmt.setNull(2, Types.LONGVARCHAR);
      } else {
        insertStmt.setString(2, county.getDescription());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int companyKey = -1;
      if (resultKey.next()) {
        companyKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      county.setCompanyKey(companyKey);
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


  public County getCompanyByCompanyName(String companyName) throws SQLException {
    String selectCompany =
            "SELECT CompanyKey,Name,Description " +
                    "FROM Company " +
                    "WHERE Name=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCompany);
      selectStmt.setString(1, companyName);
      results = selectStmt.executeQuery();

      if (results.next()) {
        int companyKey = results.getInt("CompanyKey");
        String resultsCompanyName = results.getString("Name");
        String description = results.getString("Description");

        County county = new County(companyKey, resultsCompanyName, description);
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

  public County getCompanyById(int companyKey) throws SQLException {
    String selectCompany =
            "SELECT CompanyKey,Name,Description " +
                    "FROM Company " +
                    "WHERE CompanyKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCompany);
      selectStmt.setInt(1, companyKey);
      results = selectStmt.executeQuery();

      if (results.next()) {
        int resultsCompanyKey = results.getInt("CompanyKey");
        String companyName = results.getString("Name");
        String description = results.getString("Description");

        County county = new County(resultsCompanyKey, companyName, description);
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


  public County updateAbout(County county, String newAbout) throws SQLException {
    String updateAboutCompany = "UPDATE Company SET Description=? WHERE CompanyKey=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateAboutCompany);
      updateStmt.setString(1, newAbout);
      updateStmt.setInt(2, county.getCompanyKey());
      updateStmt.executeUpdate();

      county.setDescription(newAbout);
      return county;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if (connection != null) {
        connection.close();
      }
      if (updateStmt != null) {
        updateStmt.close();
      }
    }
  }

  public County delete(County county) throws SQLException {
    String deleteCompany = "DELETE FROM Company WHERE CompanyKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCompany);
      deleteStmt.setInt(1, county.getCompanyKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Company instance.
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
