/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM04
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.dal;

import covidify.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateHospitalData` (
  `StateHospitalDataKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `NumberOfHospitals` VARCHAR(45) NULL,
  `NumberOfHospitalEmployees` VARCHAR(45) NULL,
  PRIMARY KEY (`StateHospitalDataKey`),
  INDEX `StateFKey2_idx` (`StateFKey` ASC),
  UNIQUE INDEX `Unique` (`StateFKey` ASC, `Year` ASC),
  CONSTRAINT `StateFKey2`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

public class StateHospitalDataDao
{
	protected ConnectionManager connectionManager;
	private static StateHospitalDataDao instance = null;

	protected StateHospitalDataDao()
	{
		connectionManager = new ConnectionManager();
	}

	public static StateHospitalDataDao getInstance()
	{
		if (instance == null)
		{
			instance = new StateHospitalDataDao();
		}
		return instance;
	}

	public StateHospitalData create(StateHospitalData data) throws SQLException
	{
		String insertSHD = "INSERT INTO StateHospitalData(StateFKey,Year,NumberOfHospitals,NumberOfHospitalEmployees) "
				+ "VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try
		{
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertSHD,
					Statement.RETURN_GENERATED_KEYS);
			if (data.getState() == null)
			{
				insertStmt.setNull(1, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(1, data.getState().getStateKey());
			}
			if (data.getYear() == null)
			{
				insertStmt.setNull(2, Types.DATE);
			}
			else
			{
				insertStmt.setShort(2, data.getYear());
			}
			if (data.getNumberOfHospitals() == null)
			{
				insertStmt.setNull(3, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(3, data.getNumberOfHospitals().toString());
			}
			if (data.getNumberOfHospitalEmployees() == null)
			{
				insertStmt.setNull(4, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(4, data.getNumberOfHospitalEmployees().toString());
			}

			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int stateHospDataKey = -1;
			if (resultKey.next())
			{
				stateHospDataKey = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException("Unable to retrieve auto-generated key.");
			}

			data.setStateHospitalDataKey(stateHospDataKey);
			return data;
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

	public StateHospitalData getStateHospitalDataByStateHospitalDataKey(
			int stateHospitalDataKey) throws SQLException
	{
		String selectSHD = "SELECT * " + "FROM StateHospitalData "
				+ "WHERE StateHospitalDataKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectSHD);
			selectStmt.setInt(1, stateHospitalDataKey);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			if (results.next())
			{
				StateHospitalData shd = new StateHospitalData(stateHospitalDataKey,
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"),
						Long.parseLong(results.getString("NumberOfHospitals")),
						Long.parseLong(results.getString("NumberOfHospitalEmployees")));

				return shd;
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

	public List<StateHospitalData> getStateHospitalDataByStateKey(int stateKey)
			throws SQLException
	{
		List<StateHospitalData> shds = new ArrayList<StateHospitalData>();
		String selectShd = "SELECT * " + "FROM StateHospitalData "
				+ "WHERE `StateFKey`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectShd);
			selectStmt.setInt(1, stateKey);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			while (results.next())
			{
				StateHospitalData shd = new StateHospitalData(
						results.getInt("StateHospitalDataKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"),
						Long.parseLong(results.getString("NumberOfHospitals")),
						Long.parseLong(results.getString("NumberOfHospitalEmployees")));
				shds.add(shd);
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
		return shds;
	}

	public List<StateHospitalData> getStateHospitalDataByYear(short year)
			throws SQLException
	{
		List<StateHospitalData> shds = new ArrayList<StateHospitalData>();
		String selectShd = "SELECT * " + "FROM StateHospitalData " + "WHERE `Year`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectShd);
			selectStmt.setShort(1, year);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			while (results.next())
			{
				StateHospitalData shd = new StateHospitalData(
						results.getInt("StateHospitalDataKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"),
						Long.parseLong(results.getString("NumberOfHospitals")),
						Long.parseLong(results.getString("NumberOfHospitalEmployees")));
				shds.add(shd);
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
		return shds;
	}

	public StateHospitalData updateStateHospitalDataYear(StateHospitalData shd,
			Short year) throws SQLException
	{
		String updateShd = "UPDATE StateHospitalData SET `Year`=? WHERE StateHospitalDataKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateShd);
			updateStmt.setShort(1, year);
			updateStmt.setInt(2, shd.getStateHospitalDataKey());
			updateStmt.executeUpdate();
			shd.setYear(year);
			;
			return shd;
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

	public StateHospitalData updateStateHospitalDataNumOfHospitals(StateHospitalData shd,
			Long numHospitals) throws SQLException
	{
		String updateShd = "UPDATE StateHospitalData SET `NumberOfHospitals`=? WHERE StateHospitalDataKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateShd);
			updateStmt.setLong(1, numHospitals);
			updateStmt.setInt(2, shd.getStateHospitalDataKey());
			updateStmt.executeUpdate();
			shd.setNumberOfHospitals(numHospitals);
			;
			return shd;
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

	public StateHospitalData updateStateHospitalDataNumOfHospitalEmployees(
			StateHospitalData shd, Long numHospEmployees) throws SQLException
	{
		String updateShd = "UPDATE StateHospitalData SET `NumberOfHospitalEmployees`=? WHERE StateHospitalDataKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateShd);
			updateStmt.setLong(1, numHospEmployees);
			updateStmt.setInt(2, shd.getStateHospitalDataKey());
			updateStmt.executeUpdate();
			shd.setNumberOfHospitalEmployees(numHospEmployees);
			return shd;
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

	public StateHospitalData delete(StateHospitalData shd) throws SQLException
	{
		String deleteShd = "DELETE FROM StateHospitalData WHERE StateHospitalDataKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteShd);
			deleteStmt.setInt(1, shd.getStateHospitalDataKey());
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
