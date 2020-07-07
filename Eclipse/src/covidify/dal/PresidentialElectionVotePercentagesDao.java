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
  //TODO here onwards

  public StateHospitalData create(StateHospitalData stateHospitalData) throws SQLException {
    String insertReservation = "INSERT INTO Reservation(UserName,SitDownRestaurantKey,Start,End,PartySize) "
            + "VALUES(?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertReservation, Statement.RETURN_GENERATED_KEYS);
      if (stateHospitalData.getCovidByDate().getUserName() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setString(1, stateHospitalData.getCovidByDate().getUserName());
      }
      if (stateHospitalData.getRestaurant() == null) {
        insertStmt.setNull(2, Types.INTEGER);
      } else {
        insertStmt.setInt(2, stateHospitalData.getRestaurant().getRestaurantKey());
      }
      if (stateHospitalData.getStartDate() == null) {
        insertStmt.setNull(3, Types.TIMESTAMP);
      } else {
        insertStmt.setTimestamp(3, new Timestamp(stateHospitalData.getStartDate().getTime()));
      }
      if (stateHospitalData.getEndDate() == null) {
        insertStmt.setNull(4, Types.TIMESTAMP);
      } else {
        insertStmt.setTimestamp(4, new Timestamp(stateHospitalData.getEndDate().getTime()));
      }
      if (stateHospitalData.getPartySize() == null) {
        insertStmt.setNull(5, Types.INTEGER);
      } else {
        insertStmt.setInt(5, stateHospitalData.getPartySize());
      }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int reservationKey = -1;
      if (resultKey.next()) {
        reservationKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      stateHospitalData.setReservationKey(reservationKey);
      return stateHospitalData;
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


  public StateHospitalData getReservationById(int reservationId) throws SQLException {
    String selectReservation =
            "SELECT ReservationKey,UserName,SitDownRestaurantKey,Start,End,PartySize " +
                    "FROM Reservation " +
                    "WHERE ReservationKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReservation);
      selectStmt.setInt(1, reservationId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      StateDao stateDao = StateDao.getInstance();
      if (results.next()) {
        int reservationKey = results.getInt("ReservationKey");
        String userName = results.getString("UserName");
        int restaurantKey = results.getInt("SitDownRestaurantKey");
        Date start = new Date(results.getTimestamp("Start").getTime());
        Date end = new Date(results.getTimestamp("End").getTime());
        Integer partySize = results.getInt("PartySize");

        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        StateGovernor restaurant = stateDao.getSitDownRestaurantById(restaurantKey);
        StateHospitalData stateHospitalData = new StateHospitalData(reservationKey, covidByDate,restaurant,start,end,partySize);
        return stateHospitalData;
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

  public List<StateHospitalData> getReservationsByUserName(String userName) throws SQLException {
    List<StateHospitalData> reservations = new ArrayList<StateHospitalData>();
    String selectReservation =
            "SELECT ReservationKey,UserName,SitDownRestaurantKey,Start,End,PartySize " +
                    "FROM Reservation " +
                    "WHERE UserName=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReservation);
      selectStmt.setString(1, userName);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      CovidByDate covidByDate = populationDao.getUserByUserName(userName);
      StateDao stateDao = StateDao.getInstance();
      while (results.next()) {
        int reservationKey = results.getInt("ReservationKey");
        int restaurantKey = results.getInt("SitDownRestaurantKey");
        Date start = new Date(results.getTimestamp("Start").getTime());
        Date end = new Date(results.getTimestamp("End").getTime());
        Integer partySize = results.getInt("PartySize");

        // Re-using User that we found above to avoid duplicating work
        StateGovernor restaurant = stateDao.getSitDownRestaurantById(restaurantKey);
        StateHospitalData stateHospitalData = new StateHospitalData(reservationKey, covidByDate,restaurant,start,end,partySize);
        reservations.add(stateHospitalData);
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
    return reservations;
  }

  public List<StateHospitalData> getReservationsBySitDownRestaurantId(int sitDownRestaurantId) throws SQLException {
    List<StateHospitalData> reservations = new ArrayList<StateHospitalData>();
    String selectReservation =
            "SELECT ReservationKey,UserName,SitDownRestaurantKey,Start,End,PartySize " +
                    "FROM Reservation " +
                    "WHERE SitDownRestaurantKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectReservation);
      selectStmt.setInt(1, sitDownRestaurantId);
      results = selectStmt.executeQuery();

      PopulationDao populationDao = PopulationDao.getInstance();
      StateDao stateDao = StateDao.getInstance();
      StateGovernor restaurant = stateDao.getSitDownRestaurantById(sitDownRestaurantId);
      while (results.next()) {
        int reservationKey = results.getInt("ReservationKey");
        String userName = results.getString("UserName");
        Date start = new Date(results.getTimestamp("Start").getTime());
        Date end = new Date(results.getTimestamp("End").getTime());
        Integer partySize = results.getInt("PartySize");

        // Re-using SitDownRestaurant that we found above to avoid duplicating work
        CovidByDate covidByDate = populationDao.getUserByUserName(userName);
        StateHospitalData stateHospitalData = new StateHospitalData(reservationKey, covidByDate, restaurant, start, end, partySize);
        reservations.add(stateHospitalData);
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
    return reservations;
  }



  public StateHospitalData delete(StateHospitalData stateHospitalData) throws SQLException {
    String deleteReservation = "DELETE FROM Reservation WHERE ReservationKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteReservation);
      deleteStmt.setInt(1, stateHospitalData.getReservationKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the Reservation instance.
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
