/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM04
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.dal;

import covidify.model.*;
import covidify.model.StateGovernor.GovernorPartyType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`StateGovernor` (
  `StateGovernorKey` INT NOT NULL AUTO_INCREMENT,
  `StateFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `Governor` VARCHAR(45) NULL,
  `GovernorParty` ENUM('Democratic', 'Republican', 'Other') NULL,
  PRIMARY KEY (`StateGovernorKey`),
  INDEX `StateFKey1_idx` (`StateFKey` ASC),
  UNIQUE INDEX `Unique` (`StateFKey` ASC, `Year` ASC),
  CONSTRAINT `StateFKey1`
    FOREIGN KEY (`StateFKey`)
    REFERENCES `CovidifyUSA`.`State` (`StateKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

public class StateGovernorDao
{
	protected ConnectionManager connectionManager;
	private static StateGovernorDao instance = null;

	protected StateGovernorDao()
	{
		connectionManager = new ConnectionManager();
	}

	public static StateGovernorDao getInstance()
	{
		if (instance == null)
		{
			instance = new StateGovernorDao();
		}
		return instance;
	}

	public StateGovernor create(StateGovernor gov) throws SQLException
	{
		String insertGov = "INSERT INTO StateGovernor(StateFKey,Year,Governor,GovernorParty) "
				+ "VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try
		{
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertGov,
					Statement.RETURN_GENERATED_KEYS);
			if (gov.getState() == null)
			{
				insertStmt.setNull(1, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(1, gov.getState().getStateKey());
			}
			if (gov.getYear() == null)
			{
				insertStmt.setNull(2, Types.DATE);
			}
			else
			{
				insertStmt.setShort(2, gov.getYear());
			}
			if (gov.getGovernor() == null)
			{
				insertStmt.setNull(3, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(3, gov.getGovernor());
			}
			if (gov.getGovernorParty() == null)
			{
				insertStmt.setNull(4, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(4, gov.getGovernorParty().name());
			}

			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int stateGovernorKey = -1;
			if (resultKey.next())
			{
				stateGovernorKey = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException("Unable to retrieve auto-generated key.");
			}

			gov.setStateGovernorKey(stateGovernorKey);
			return gov;
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

	public StateGovernor getStateGovernorByStateGovernorKey(int stateGovernorKey)
			throws SQLException
	{
		String selectGov = "SELECT * " + "FROM StateGovernor "
				+ "WHERE StateGovernorKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGov);
			selectStmt.setInt(1, stateGovernorKey);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			if (results.next())
			{
				StateGovernor gov = new StateGovernor(stateGovernorKey,
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"), results.getString("Governor"),
						StateGovernor.GovernorPartyType
								.valueOf(results.getString("GovernorParty")));

				return gov;
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

	public List<StateGovernor> getStateGovernorsByStateKeyAndYear(int stateKey,
			Short year) throws SQLException
	{
		List<StateGovernor> govs = new ArrayList<StateGovernor>();
		String selectGovs = "SELECT * " + "FROM StateGovernor "
				+ "WHERE StateFKey=? AND `Year`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGovs);
			selectStmt.setInt(1, stateKey);
			selectStmt.setShort(2, year);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			while (results.next())
			{
				StateGovernor gov = new StateGovernor(results.getInt("StateGovernorKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"), results.getString("Governor"),
						StateGovernor.GovernorPartyType
								.valueOf(results.getString("GovernorParty")));
				govs.add(gov);
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
		return govs;
	}

	

	public StateGovernor getStateGovernorsByStateKeyYearGovernorName(int stateKey,
			Short year, String governorName) throws SQLException
	{
		StateGovernor gov;
		String selectGovs = "SELECT * " + "FROM StateGovernor "
				+ "WHERE StateFKey=? AND`Year`=? AND `Governor`\"?\";";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGovs);
			selectStmt.setInt(1, stateKey);
			selectStmt.setShort(2, year);
			selectStmt.setString(3, governorName);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			if (results.next())
			{
				gov = new StateGovernor(results.getInt("StateGovernorKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"), results.getString("Governor"),
						StateGovernor.GovernorPartyType
								.valueOf(results.getString("GovernorParty")));
				return gov;
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

	
	
	
	
	
	public List<StateGovernor> getStateGovernorsByGovernorName(String govName)
			throws SQLException
	{
		List<StateGovernor> govs = new ArrayList<StateGovernor>();
		String selectGovs = "SELECT * " + "FROM StateGovernor " + "WHERE Governor=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGovs);
			selectStmt.setString(1, govName);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			while (results.next())
			{
				StateGovernor gov = new StateGovernor(results.getInt("StateGovernorKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"), results.getString("Governor"),
						StateGovernor.GovernorPartyType
								.valueOf(results.getString("GovernorParty")));
				govs.add(gov);
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
		return govs;
	}

	public List<StateGovernor> getStateGovernorsByStateKey(int stateKey)
			throws SQLException
	{
		List<StateGovernor> govs = new ArrayList<StateGovernor>();
		String selectGovs = "SELECT * " + "FROM StateGovernor " + "WHERE StateFKey=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGovs);
			selectStmt.setInt(1, stateKey);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			while (results.next())
			{
				StateGovernor gov = new StateGovernor(results.getInt("StateGovernorKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"), results.getString("Governor"),
						StateGovernor.GovernorPartyType
								.valueOf(results.getString("GovernorParty")));
				govs.add(gov);
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
		return govs;
	}

	public List<StateGovernor> getStateGovernorsByPartyAndYear(GovernorPartyType party,
			Short year) throws SQLException
	{
		List<StateGovernor> govs = new ArrayList<StateGovernor>();
		String selectGovs = "SELECT * " + "FROM StateGovernor "
				+ "WHERE GovernorParty=? AND `Year`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGovs);
			selectStmt.setString(1, party.name());
			selectStmt.setShort(2, year);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			while (results.next())
			{
				StateGovernor gov = new StateGovernor(results.getInt("StateGovernorKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"), results.getString("Governor"),
						StateGovernor.GovernorPartyType
								.valueOf(results.getString("GovernorParty")));
				govs.add(gov);
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
		return govs;
	}

	public List<StateGovernor> getStateGovernorsByYear(Short year) throws SQLException
	{
		List<StateGovernor> govs = new ArrayList<StateGovernor>();
		String selectGovs = "SELECT * " + "FROM StateGovernor " + "WHERE Year=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectGovs);
			selectStmt.setShort(1, year);
			results = selectStmt.executeQuery();
			StateDao stateDao = StateDao.getInstance();

			while (results.next())
			{
				StateGovernor gov = new StateGovernor(results.getInt("StateGovernorKey"),
						stateDao.getStateByKey(results.getInt("StateFKey")),
						results.getShort("Year"), results.getString("Governor"),
						StateGovernor.GovernorPartyType
								.valueOf(results.getString("GovernorParty")));
				govs.add(gov);
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
		return govs;
	}

	public StateGovernor updateGovernorName(StateGovernor gov, String newGovName)
			throws SQLException
	{
		String updateGov = "UPDATE StateGovernor SET Governor=? WHERE StateGovernorKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateGov);
			updateStmt.setString(1, newGovName);
			updateStmt.setInt(2, gov.getStateGovernorKey());
			updateStmt.executeUpdate();
			gov.setGovernor(newGovName);
			return gov;
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

	public StateGovernor updateGovernorYear(StateGovernor gov, Short year)
			throws SQLException
	{
		String updateGov = "UPDATE StateGovernor SET `Year`=? WHERE StateGovernorKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateGov);
			updateStmt.setShort(1, year);
			updateStmt.setInt(2, gov.getStateGovernorKey());
			updateStmt.executeUpdate();
			gov.setYear(year);
			return gov;
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

	public StateGovernor updateGovernorParty(StateGovernor gov, GovernorPartyType party)
			throws SQLException
	{
		String updateGov = "UPDATE StateGovernor SET GovernorParty=? WHERE StateGovernorKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateGov);
			updateStmt.setString(1, party.name());
			updateStmt.setInt(2, gov.getStateGovernorKey());
			updateStmt.executeUpdate();
			gov.setGovernorParty(party);
			return gov;
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

	public StateGovernor delete(StateGovernor gov) throws SQLException
	{
		String deleteGov = "DELETE FROM StateGovernor WHERE StateGovernorKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteGov);
			deleteStmt.setInt(1, gov.getStateGovernorKey());
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
