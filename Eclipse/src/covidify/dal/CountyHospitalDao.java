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

import covidify.model.CountyHospitalData;
import covidify.model.County;

public class CountyHospitalDao {
  protected ConnectionManager connectionManager;
  private static CountyHospitalDao instance = null;

  protected CountyHospitalDao() {
    connectionManager = new ConnectionManager();
  }

  public static CountyHospitalDao getInstance() {
    if (instance == null) {
      instance = new CountyHospitalDao();
    }
    return instance;
  }

  public CountyHospitalData create(CountyHospitalData countyHospitalData) throws SQLException {
    String insertCountyHospitalData = "INSERT INTO CountyHospitalData(CountyFKey,Year,ICUBeds) "
            + "VALUES(?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertCountyHospitalData, Statement.RETURN_GENERATED_KEYS);
      if (countyHospitalData.getIcuBeds() == null) {
        insertStmt.setNull(1, Types.VARCHAR);
      } else {
        insertStmt.setInt(1, countyHospitalData.getIcuBeds());
      }

      resultKey = insertStmt.getGeneratedKeys();
      int countyHospitalDataKey = -1;
      if (resultKey.next()) {
        countyHospitalDataKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      countyHospitalData.setCountyHospitalDataKey(countyHospitalDataKey);
      return countyHospitalData;
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


  public List<CountyHospitalData> getCountyHospitalDataByCounty(County county) throws SQLException {
	List<CountyHospitalData> countyHospitalDatas = new ArrayList<CountyHospitalData>();
    String selectCountyHospitalData =
            "SELECT CountyHospitalDataKey,CountyFKey,Year,ICUBeds FROM CountyHospitalData " +
                    "WHERE CountyFKey=?;";
    Connection connection = null;
    PreparedStatement selectStmt = null;
    ResultSet results = null;
    try {
      connection = connectionManager.getConnection();
      selectStmt = connection.prepareStatement(selectCountyHospitalData);
      selectStmt.setInt(1, county.getCountyKey());
      results = selectStmt.executeQuery();

      while(results.next()) {
        int countyHospitalDataKey = results.getInt("CountyHospitalDataKey");
        Date resyear = results.getDate("Year");
        int resicu = results.getInt("ICUBeds");
        

        CountyHospitalData countyHospitalData = new CountyHospitalData(countyHospitalDataKey, county, resyear, resicu);
        countyHospitalDatas.add(countyHospitalData);
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
    return countyHospitalDatas;
  }

  public CountyHospitalData updateCountyHospitalData(CountyHospitalData countyHospitalData, County county) throws SQLException {
		String updateCountyHospitalData = "UPDATE CountyHospitalData SET CountyFKey=? WHERE CountyHospitalDataKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCountyHospitalData);
			updateStmt.setInt(1, county.getCountyKey());
			updateStmt.setInt(2, countyHospitalData.getCountyHospitalDataKey());
			updateStmt.executeUpdate();
			countyHospitalData.setCounty(county);
			return countyHospitalData;
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



  public CountyHospitalData delete(CountyHospitalData countyHospitalData) throws SQLException {
    String deleteCountyHospitalData = "DELETE FROM CountyHospitalData WHERE CountyHospitalDataKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteCountyHospitalData);
      deleteStmt.setInt(1, countyHospitalData.getCountyHospitalDataKey());
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
