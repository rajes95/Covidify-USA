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


public class PopulationDao {
  protected ConnectionManager connectionManager;
  private static PopulationDao instance = null;
  protected PopulationDao() {
    connectionManager = new ConnectionManager();
  }
  public static PopulationDao getInstance() {
    if(instance == null) {
      instance = new PopulationDao();
    }
    return instance;
  }

  //TODO here onwards
  public CovidByDate create(CovidByDate covidByDate) throws SQLException {
    String insertUser = "INSERT INTO User(UserName,PasswordHash,FirstName,LastName,Email,PhoneNum) "
            + "VALUES(?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
      if (covidByDate.getUserName() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setString(1, covidByDate.getUserName());
      }
      if (covidByDate.getPasswordHash() == null) {
        insertStmt.setNull(2, Types.VARCHAR);
      } else {
        insertStmt.setString(2, covidByDate.getPasswordHash());
      }
      if (covidByDate.getFirstName() == null) {
        insertStmt.setNull(3, Types.VARCHAR);
      } else {
        insertStmt.setString(3, covidByDate.getFirstName());
      }
      if (covidByDate.getLastName() == null) {
        insertStmt.setNull(4, Types.VARCHAR);
      } else {
        insertStmt.setString(4, covidByDate.getLastName());
      }
      if (covidByDate.getEmail() == null) {
        insertStmt.setNull(5, Types.VARCHAR);
      } else {
        insertStmt.setString(5, covidByDate.getEmail());
      }
      if (covidByDate.getPhoneNum() == null) {
        insertStmt.setNull(6, Types.VARCHAR);
      } else {
        insertStmt.setString(6, covidByDate.getPhoneNum());
      }
      insertStmt.executeUpdate();
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

  public CovidByDate getUserByUserName(String userName) throws SQLException {
    String selectUser =
            "SELECT UserName,PasswordHash,FirstName,LastName,Email,PhoneNum " +
                    "FROM User " +
                    "WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectUser);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      if(results.next()) {
        String passwordHash = results.getString("PasswordHash");
        String firstName = results.getString("FirstName");
        String lastName = results.getString("LastName");
        String email = results.getString("Email");
        String phoneNum = results.getString("PhoneNum");

        CovidByDate covidByDate = new CovidByDate(userName,passwordHash,firstName,lastName,email,phoneNum);
        return covidByDate;
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
    return null;
  }

  public CovidByDate delete(CovidByDate covidByDate) throws SQLException{
    String deleteUser = "DELETE FROM User WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteUser);
      deleteStmt.setString(1, covidByDate.getUserName());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the BlogComments instance.
      return null;
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    } finally {
      if(connection != null) {
        connection.close();
      }
      if(deleteStmt != null) {
        deleteStmt.close();
      }
    }
  }
}
