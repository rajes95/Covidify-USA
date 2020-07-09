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
import java.util.ArrayList;
import java.util.List;

import covidify.model.County;
import covidify.model.State;

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

public class CountyDao
{
	protected ConnectionManager connectionManager;
	private static CountyDao instance = null;

	protected CountyDao()
	{
		connectionManager = new ConnectionManager();
	}

	public static CountyDao getInstance()
	{
		if (instance == null)
		{
			instance = new CountyDao();
		}
		return instance;
	}

	public County create(County county) throws SQLException
	{
		String insertCounty = "INSERT INTO County(StateFKey,CountyFIPS,CountyName,Longitude,Latitude) "
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try
		{
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCounty,
					Statement.RETURN_GENERATED_KEYS);
			if (county.getState() == null)
			{
				insertStmt.setNull(1, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(1, county.getState().getStateKey());
			}
			if (county.getCountyFIPS() == null)
			{
				insertStmt.setNull(2, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(2, county.getCountyFIPS());
			}
			if (county.getCountyName() == null)
			{
				insertStmt.setNull(3, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(3, county.getCountyName());
			}
			if (county.getLongitude() == null)
			{
				insertStmt.setNull(4, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(4, county.getLongitude().toString());
			}
			if (county.getLatitude() == null)
			{
				insertStmt.setNull(5, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(5, county.getLatitude().toString());
			}
			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int countyKey = -1;
			if (resultKey.next())
			{
				countyKey = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			county.setCountyKey(countyKey);
			return county;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (insertStmt != null)
			{
				insertStmt.close();
			}
			if (resultKey != null)
			{
				resultKey.close();
			}
		}
	}

	public County getCountyByFIPS(String countyFIPS) throws SQLException
	{
		String selectCounty = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE CountyFIPs=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounty);
			selectStmt.setString(1, countyFIPS);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			if (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				int stateFKey = results.getInt("StateFKey");
				String countyName = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				State state = stateDao.getStateByKey(stateFKey);

				County county = new County(countyKey, state, countyFIPS, countyName,
						longitude, latitude);
				return county;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return null;
	}

	public County getCountyByCountyKey(int countyKey) throws SQLException
	{
		String selectCounty = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE CountyKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounty);
			selectStmt.setInt(1, countyKey);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			if (results.next())
			{
				int stateFKey = results.getInt("StateFKey");
				String countyFIPS = results.getString("CountyFIPS");
				String countyName = results.getString("CountyName");

				Double longitude;
				if (results.getString("Longitude") == null)
				{
					longitude = 0.;
				}
				else
				{
					longitude = Double.valueOf(results.getString("Longitude"));
				}

				Double latitude;
				if (results.getString("Latitude") == null)
				{
					latitude = 0.;
				}
				else
				{
					latitude = Double.valueOf(results.getString("Latitude"));
				}

				State state = stateDao.getStateByKey(stateFKey);

				County county = new County(countyKey, state, countyFIPS, countyName,
						longitude, latitude);
				return county;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return null;
	}

	public County getCountyByCountyNameAndStateName(String countyName, String stateName)
			throws SQLException
	{
		String selectCounty = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "JOIN State ON County.StateFKey = State.StateKey "
				+ "WHERE County.CountyName=? and State.StateName=?";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounty);
			selectStmt.setString(1, countyName);
			selectStmt.setString(2, stateName);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			if (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				int stateFKey = results.getInt("StateFKey");
				String countyFIPS = results.getString("CountyFIPS");
				String name = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				State state = stateDao.getStateByKey(stateFKey);

				County county = new County(countyKey, state, countyFIPS, name, longitude,
						latitude);
				return county;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return null;
	}

	public County getCountyByCountyNameAndStatePostalCode(String countyName,
			String statePostalCode) throws SQLException
	{
		String selectCounty = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "JOIN State ON County.StateFKey = State.StateKey"
				+ "WHERE County.CountyName=? and State.PostalCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounty);
			selectStmt.setString(1, countyName);
			selectStmt.setString(2, statePostalCode);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			if (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				int stateFKey = results.getInt("StateFKey");
				String countyFIPS = results.getString("CountyFIPS");
				String name = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				State state = stateDao.getStateByKey(stateFKey);

				County county = new County(countyKey, state, countyFIPS, name, longitude,
						latitude);
				return county;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return null;
	}

	public List<County> getCountyByStateFKey(int stateFKey) throws SQLException
	{
		List<County> counties = new ArrayList<County>();
		String selectCounties = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE StateFKey=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounties);
			selectStmt.setInt(1, stateFKey);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();
			while (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				int stateKey = results.getInt("StateFKey");
				String countyFIPS = results.getString("CountyFIPS");
				String countyName = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				State state = stateDao.getStateByKey(stateKey);
				County county = new County(countyKey, state, countyFIPS, countyName,
						longitude, latitude);
				counties.add(county);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return counties;
	}

	public List<County> getCountyByStateFIPS(String stateFIPS) throws SQLException
	{
		List<County> counties = new ArrayList<County>();
		String selectCounties = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE StateFKey=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounties);
			StateDao stateDao = StateDao.getInstance();
			State state = stateDao.getStateByFIPS(stateFIPS);
			selectStmt.setInt(1, state.getStateKey());
			results = selectStmt.executeQuery();
			while (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				String countyFIPS = results.getString("CountyFIPS");
				String countyName = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				County county = new County(countyKey, state, countyFIPS, countyName,
						longitude, latitude);
				counties.add(county);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return counties;
	}

	public List<County> getCountyByStatePostalCode(String statePostalCode)
			throws SQLException
	{
		List<County> counties = new ArrayList<County>();
		String selectCounties = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE StateFKey=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounties);
			StateDao stateDao = StateDao.getInstance();
			State state = stateDao.getStateByPostalCode(statePostalCode);
			selectStmt.setInt(1, state.getStateKey());
			results = selectStmt.executeQuery();
			while (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				String countyFIPS = results.getString("CountyFIPS");
				String countyName = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				County county = new County(countyKey, state, countyFIPS, countyName,
						longitude, latitude);
				counties.add(county);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return counties;
	}

	public List<County> getCountyByStateName(String stateName) throws SQLException
	{
		List<County> counties = new ArrayList<County>();
		String selectCounties = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE StateFKey=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounties);
			StateDao stateDao = StateDao.getInstance();
			State state = stateDao.getStateByName(stateName);
			selectStmt.setInt(1, state.getStateKey());
			results = selectStmt.executeQuery();
			while (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				String countyFIPS = results.getString("CountyFIPS");
				String countyName = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				County county = new County(countyKey, state, countyFIPS, countyName,
						longitude, latitude);
				counties.add(county);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return counties;
	}

	public List<County> getCountyByState(State state) throws SQLException
	{
		List<County> counties = new ArrayList<County>();
		String selectCounties = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE StateFKey=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounties);
			selectStmt.setInt(1, state.getStateKey());
			results = selectStmt.executeQuery();
			while (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				String countyFIPS = results.getString("CountyFIPS");
				String countyName = results.getString("CountyName");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				County county = new County(countyKey, state, countyFIPS, countyName,
						longitude, latitude);
				counties.add(county);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return counties;
	}

	public List<County> getCountyByCountyName(String countyName) throws SQLException
	{
		List<County> counties = new ArrayList<County>();
		String selectCounties = "SELECT CountyKey,StateFKey,CountyFIPS,CountyName,Longitude,Latitude "
				+ "FROM County " + "WHERE CountyName=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCounties);
			selectStmt.setString(1, countyName);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();
			while (results.next())
			{
				int countyKey = results.getInt("CountyKey");
				int stateFKey = results.getInt("StateFKey");
				String nameOfCounty = results.getString("CountyName");
				String countyFIPS = results.getString("CountyFIPS");
				Double longitude = Double.valueOf(results.getString("Longitude"));
				Double latitude = Double.valueOf(results.getString("Latitude"));

				State state = stateDao.getStateByKey(stateFKey);
				County county = new County(countyKey, state, countyFIPS, nameOfCounty,
						longitude, latitude);
				counties.add(county);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (selectStmt != null)
			{
				selectStmt.close();
			}
			if (results != null)
			{
				results.close();
			}
		}
		return counties;
	}

	public County updateState(County county, State newState) throws SQLException
	{
		String updateCounty = "UPDATE County SET StateFKey=? WHERE CountyKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCounty);
			updateStmt.setInt(1, newState.getStateKey());
			updateStmt.setInt(2, county.getCountyKey());
			updateStmt.executeUpdate();
			county.setState(newState);
			return county;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (updateStmt != null)
			{
				updateStmt.close();
			}
		}
	}

	public County updateCountyFIPS(County county, String newCountyFIPS)
			throws SQLException
	{
		String updateCounty = "UPDATE County SET CountyFIPS=? WHERE CountyKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCounty);
			updateStmt.setString(1, newCountyFIPS);
			updateStmt.setInt(2, county.getCountyKey());
			updateStmt.executeUpdate();
			county.setCountyFIPS(newCountyFIPS);
			return county;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (updateStmt != null)
			{
				updateStmt.close();
			}
		}
	}

	public County updateCountyName(County county, String newCountyName)
			throws SQLException
	{
		String updateCounty = "UPDATE County SET CountyName=? WHERE CountyKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCounty);
			updateStmt.setString(1, newCountyName);
			updateStmt.setInt(2, county.getCountyKey());
			updateStmt.executeUpdate();
			county.setCountyName(newCountyName);
			return county;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (updateStmt != null)
			{
				updateStmt.close();
			}
		}
	}

	public County updateLongitude(County county, double newLongitude) throws SQLException
	{
		String updateCounty = "UPDATE County SET Longitude=? WHERE CountyKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCounty);
			updateStmt.setString(1, String.valueOf(newLongitude));
			updateStmt.setInt(2, county.getCountyKey());
			updateStmt.executeUpdate();
			county.setLongitude(newLongitude);
			return county;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (updateStmt != null)
			{
				updateStmt.close();
			}
		}
	}

	public County updateLatitude(County county, double newLatitude) throws SQLException
	{
		String updateCounty = "UPDATE County SET Latitude=? WHERE CountyKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCounty);
			updateStmt.setString(1, String.valueOf(newLatitude));
			updateStmt.setInt(2, county.getCountyKey());
			updateStmt.executeUpdate();
			county.setLatitude(newLatitude);
			return county;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (updateStmt != null)
			{
				updateStmt.close();
			}
		}
	}

	public County delete(County county) throws SQLException
	{
		String deleteCounty = "DELETE FROM County WHERE CountyKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCounty);
			deleteStmt.setInt(1, county.getCountyKey());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the County instance.
			return null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			throw e;
		}
		finally
		{
			if (connection != null)
			{
				connection.close();
			}
			if (deleteStmt != null)
			{
				deleteStmt.close();
			}
		}
	}

}
