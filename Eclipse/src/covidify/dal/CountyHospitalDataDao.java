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

public class CountyHospitalDataDao {
  protected ConnectionManager connectionManager;
  private static CountyHospitalDataDao instance = null;

  protected CountyHospitalDataDao() {
    connectionManager = new ConnectionManager();
  }

  public static CountyHospitalDataDao getInstance() {
    if (instance == null) {
      instance = new CountyHospitalDataDao();
    }
    return instance;
  }

  public State create(State state) throws SQLException {
    String insertCreditCard = "INSERT INTO CreditCard(NameOnCard,CardNum,CardExpiration,UserName) "
            + "VALUES(?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCreditCard, Statement.RETURN_GENERATED_KEYS);
      if (state.getNameOnCard() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setString(1, state.getNameOnCard());
      }
      if (state.getCardNum() == null) {
        insertStmt.setNull(2, Types.VARCHAR);
      } else {
        insertStmt.setString(2, String.valueOf(state.getCardNum()));
      }
      if (state.getCardExpiration() == null) {
        insertStmt.setNull(3, Types.TIMESTAMP);
      } else {
        insertStmt.setTimestamp(3, new Timestamp(state.getCardExpiration().getTime()));
      }
      if (state.getCovidByDate().getUserName() == null) {
        insertStmt.setNull(4, Types.VARCHAR);
      } else {
        insertStmt.setString(4, state.getCovidByDate().getUserName());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int creditCardKey = -1;
      if (resultKey.next()) {
        creditCardKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      state.setCreditCardKey(creditCardKey);
      return state;
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

  public State updateExpiration(State state, Date newExpiration) throws SQLException {
    String updateExpiration = "UPDATE CreditCard SET CardExpiration=? WHERE CreditCardKey=?;";
    Connection connection = null;
    PreparedStatement updateStmt = null;
    try {
      connection = connectionManager.getConnection();
      updateStmt = connection.prepareStatement(updateExpiration);
      updateStmt.setTimestamp(1, new Timestamp(newExpiration.getTime()));
      updateStmt.setInt(2, state.getCreditCardKey());
      updateStmt.executeUpdate();

      state.setCardExpiration(newExpiration);
      return state;
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

  public State getCreditCardByCardNumber(long cardNumber) throws SQLException {
    String selectCreditCard =
            "SELECT CreditCardKey,NameOnCard,CardNum,CardExpiration,UserName " +
                    "FROM CreditCard " +
                    "WHERE CardNum=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCreditCard);
      selectStmt.setString(1, String.valueOf(cardNumber));
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      if (results.next()) {
        String resultCardNum = results.getString("CardNum");
        String nameOnCard = results.getString("NameOnCard");
        Date cardExpiration = new Date(results.getTimestamp("CardExpiration").getTime());
        int creditCardKey = results.getInt("CreditCardKey");
        String userName = results.getString("UserName");

        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        State state = new State(creditCardKey, nameOnCard, Long.valueOf(resultCardNum), cardExpiration, covidByDate);
        return state;
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

  public State getCreditCardById(int creditCardKey) throws SQLException {
    String selectCreditCard =
            "SELECT CreditCardKey,NameOnCard,CardNum,CardExpiration,UserName " +
                    "FROM CreditCard " +
                    "WHERE CreditCardKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCreditCard);
      selectStmt.setInt(1, creditCardKey);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      if (results.next()) {
        int resultsCreditCardKey = results.getInt("CreditCardKey");
        String cardNum = results.getString("CardNum");
        String nameOnCard = results.getString("NameOnCard");
        Date cardExpiration = new Date(results.getTimestamp("CardExpiration").getTime());
        String userName = results.getString("UserName");

        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        State state = new State(resultsCreditCardKey, nameOnCard, Long.valueOf(cardNum), cardExpiration, covidByDate);
        return state;
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

  public List<State> getCreditCardsByUserName(String userName) throws SQLException {
    List<State> creditCards = new ArrayList<State>();
    String selectCreditCards =
            "SELECT CreditCardKey,NameOnCard,CardNum,CardExpiration,UserName " +
                    "FROM CreditCard " +
                    "WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCreditCards);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      while (results.next()) {
        int creditCardKey = results.getInt("CreditCardKey");
        String cardNum = results.getString("CardNum");
        String nameOnCard = results.getString("NameOnCard");
        Date cardExpiration = new Date(results.getTimestamp("CardExpiration").getTime());

        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        State state = new State(creditCardKey, nameOnCard, Long.valueOf(cardNum), cardExpiration, covidByDate);
        creditCards.add(state);
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
    return creditCards;
  }

  public State delete(State state) throws SQLException {
    String deleteCreditCard = "DELETE FROM CreditCard WHERE CreditCardKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCreditCard);
      deleteStmt.setInt(1, state.getCreditCardKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the CreditCard instance.
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
