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
import java.sql.Types;

import covidify.model.Climate;
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

  public Climate create(Climate climate) throws SQLException {
    String insertClimate = "INSERT INTO Climate(ClimateFKey,Year,Elevation,WinterPrcp,"
    		+ "SummerPrcp,SprintPrcp,AutumnPrcp,WinterTavg,SummerTavg,SpringTavg,AutumnTavg) "
            + "VALUES(?,?,?,?,?,?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertClimate, Statement.RETURN_GENERATED_KEYS);
      if (climate.getElevation() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setDouble(1, climate.getElevation());
      }

      resultKey = insertStmt.getGeneratedKeys();
      int climateKey = -1;
      if (resultKey.next()) {
        climateKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      climate.setClimateKey(climateKey);
      return climate;
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


  public List<Climate> getClimateByCounty(County county) throws SQLException {
	List<Climate> climates = new ArrayList<Climate>();
    String selectClimate =
            "SELECT ClimateKey,CountyFKey,Year,Elevation,WinterPrcp,SummerPrcp,SpringPrcp,"
            + "AutumnPrcp,WinterTavg,SummerTavg,SpringTavg,AutumnTavg " +
                    "FROM Climate " +
                    "WHERE CountyFKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectClimate);
      selectStmt.setInt(1, county.getCountyKey());
      results = selectStmt.executeQuery();

      while(results.next()) {
        int climateKey = results.getInt("ClimateKey");
        Date resyear = results.getDate("Year");
        Double reselev = results.getDouble("Elevation");
        Double reswinterprcp = results.getDouble("WinterPrcp");
        Double ressumprcp = results.getDouble("SummerPrcp");
        Double resspringprcp = results.getDouble("SpringPrcp");
        Double resautprcp = results.getDouble("AutumnPrcp");
        Double reswintertemp = results.getDouble("WinterTavg");
        Double ressumtemp = results.getDouble("SummerTavg");
        Double resspringtemp = results.getDouble("SpringTAvg");
        Double resauttemp = results.getDouble("AutumnTAvg");
        

        Climate climate = new Climate(climateKey, county, resyear, reselev, reswinterprcp, ressumprcp,
        		resspringprcp, resautprcp, reswintertemp, ressumtemp, resspringtemp, resauttemp);
        climates.add(climate);
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
    return climates;
  }

  public Climate updateClimate(Climate climate, County county) throws SQLException {
		String updateClimate = "UPDATE Climate SET CountyFKey=? WHERE ClimateKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateClimate);
			updateStmt.setInt(1, county.getCountyKey());
			updateStmt.setInt(2, climate.getClimateKey());
			updateStmt.executeUpdate();
			climate.setCounty(county);
			return climate;
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



  public Climate delete(Climate climate) throws SQLException {
    String deleteClimate = "DELETE FROM Climate WHERE ClimateKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteClimate);
      deleteStmt.setInt(1, climate.getClimateKey());
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
