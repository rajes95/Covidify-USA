/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.dal;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;

import covidify.model.CovidByRace;
import covidify.model.State;

public class CovidByRaceDao {
  protected ConnectionManager connectionManager;
  private static CovidByRaceDao instance = null;

  protected CovidByRaceDao() {
    connectionManager = new ConnectionManager();
  }

  public static CovidByRaceDao getInstance() {
    if (instance == null) {
      instance = new CovidByRaceDao();
    }
    return instance;
  }

  public CovidByRace create(CovidByRace covidByRace) throws SQLException {
    String insertCovidByRace = "INSERT INTO CovidByRace(StateFKey,Race,Positive,Negative,Death,Date) "
            + "VALUES(?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCovidByRace, Statement.RETURN_GENERATED_KEYS);
      insertStmt.setInt(1, covidByRace.getState().getStateKey());
      insertStmt.setString(2, covidByRace.getRaceType().name());
      if (covidByRace.getPositive() == null) {
        insertStmt.setNull(3, Types.VARCHAR);
      } else {
        insertStmt.setInt(3, covidByRace.getPositive());
      }
      if (covidByRace.getNegative() == null) {
          insertStmt.setNull(4, Types.VARCHAR);
        } else {
          insertStmt.setInt(4, covidByRace.getNegative());
        }
      if (covidByRace.getDeath() == null) {
          insertStmt.setNull(5, Types.VARCHAR);
        } else {
          insertStmt.setInt(5, covidByRace.getDeath());
        }
      if (covidByRace.getDate() == null)
		{
			insertStmt.setNull(6, Types.TIMESTAMP);
		} else {
			insertStmt.setTimestamp(6,
					new Timestamp(covidByRace.getDate().getTime()));
		}

      insertStmt.executeUpdate();
      resultKey = insertStmt.getGeneratedKeys();
      int covidByRaceKey = -1;
      if (resultKey.next()) {
        covidByRaceKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      covidByRace.setCovidByRaceKey(covidByRaceKey);
      return covidByRace;
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


  public List<CovidByRace> getCovidByRaceByState(State state) throws SQLException {
	List<CovidByRace> covidByRaces = new ArrayList<CovidByRace>();
    String selectCovidByRace =
            "SELECT CovidByRaceKey,StateFKey,Race,Positive,Negative,Death,Date FROM CovidByRace " +
                    "WHERE StateFKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCovidByRace);
      selectStmt.setInt(1, state.getStateKey());
      results = selectStmt.executeQuery();

      while(results.next()) {
        int covidByRaceKey = results.getInt("CovidByRaceKey");
        Date resyear = results.getDate("Date");
        String resrace = results.getString("Race");
        int respos = results.getInt("Positive");
        int resneg = results.getInt("Negative");
        int resdeath = results.getInt("Death");
        
        CovidByRace.RaceType resultrace = CovidByRace.RaceType.valueOf(resrace);
        CovidByRace covidByRace = new CovidByRace(covidByRaceKey, state, resultrace, respos, resneg, resdeath, resyear);
        covidByRaces.add(covidByRace);
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
    return covidByRaces;
  }

  public CovidByRace updateCovidByRace(CovidByRace covidByRace, State state) throws SQLException {
		String updateCovidByRace = "UPDATE CovidByRace SET StateFKey=? WHERE CovidByRaceKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCovidByRace);
			updateStmt.setInt(1, state.getStateKey());
			updateStmt.setInt(2, covidByRace.getCovidByRaceKey());
			updateStmt.executeUpdate();
			covidByRace.setState(state);
			return covidByRace;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(updateStmt != null) {
				updateStmt.close();
			}
		}
	}



  public CovidByRace delete(CovidByRace covidByRace) throws SQLException {
    String deleteCovidByRace = "DELETE FROM CovidByRace WHERE CovidByRaceKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCovidByRace);
      deleteStmt.setInt(1, covidByRace.getCovidByRaceKey());
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
