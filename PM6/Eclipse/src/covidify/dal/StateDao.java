/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.dal;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;

import covidify.model.State;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`State` (
  `StateKey` INT NOT NULL AUTO_INCREMENT,
  `StateFIPS` VARCHAR(45) NULL,
  `PostalCode` VARCHAR(45) NULL,
  `StateName` VARCHAR(100) NULL,
  PRIMARY KEY (`StateKey`),
  UNIQUE INDEX `StateFIPS_UNIQUE` (`StateFIPS` ASC))
  ENGINE = InnoDB;
*/

public class StateDao
{
	protected ConnectionManager connectionManager;
	private static StateDao instance = null;

	protected StateDao()
	{
		connectionManager = new ConnectionManager();
	}

	public static StateDao getInstance()
	{
		if (instance == null)
		{
			instance = new StateDao();
		}
		return instance;
	}

	public State create(State state) throws SQLException
	{
		String insertState = "INSERT INTO State(StateFIPS,PostalCode,StateName) "
				+ "VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try
		{
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertState,
					Statement.RETURN_GENERATED_KEYS);
			if (state.getStateFIPS() == null)
			{
				insertStmt.setNull(1, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(1, state.getStateFIPS());
			}
			if (state.getPostalCode() == null)
			{
				insertStmt.setNull(2, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(2, state.getPostalCode());
			}
			if (state.getStateName() == null)
			{
				insertStmt.setNull(3, Types.VARCHAR);
			}
			else
			{
				insertStmt.setString(3, state.getStateName());
			}

			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int stateKey = -1;
			if (resultKey.next())
			{
				stateKey = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			state.setStateKey(stateKey);
			return state;
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

	public State getStateByName(String stateName) throws SQLException
	{
		String selectState = "SELECT StateKey,StateFIPS,PostalCode,StateName "
				+ "FROM State " + "WHERE StateName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectState);
			selectStmt.setString(1, stateName);
			results = selectStmt.executeQuery();
			if (results.next())
			{
				int stateKey = results.getInt("StateKey");
				String stateFIPS = results.getString("StateFIPS");
				String postalCode = results.getString("PostalCode");
				String resultStateName = results.getString("StateName");
				State state = new State(stateKey, stateFIPS, postalCode, resultStateName);
				return state;
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

	public State getStateByKey(int stateKey) throws SQLException
	{
		String selectState = "SELECT StateKey,StateFIPS,PostalCode,StateName "
				+ "FROM State " + "WHERE StateKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectState);
			selectStmt.setInt(1, stateKey);
			results = selectStmt.executeQuery();
			if (results.next())
			{
				int resultStateKey = results.getInt("StateKey");
				String stateFIPS = results.getString("StateFIPS");
				String postalCode = results.getString("PostalCode");
				String resultStateName = results.getString("StateName");
				State state = new State(resultStateKey, stateFIPS, postalCode,
						resultStateName);
				return state;
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

	public State getStateByPostalCode(String postalCode) throws SQLException
	{
		String selectState = "SELECT StateKey,StateFIPS,PostalCode,StateName "
				+ "FROM State " + "WHERE PostalCode=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectState);
			selectStmt.setString(1, postalCode);
			results = selectStmt.executeQuery();
			if (results.next())
			{
				int resultStateKey = results.getInt("StateKey");
				String stateFIPS = results.getString("StateFIPS");
				String postCode = results.getString("PostalCode");
				String resultStateName = results.getString("StateName");
				State state = new State(resultStateKey, stateFIPS, postCode,
						resultStateName);
				return state;
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

	public State getStateByFIPS(String stateFIPS) throws SQLException
	{
		String selectState = "SELECT StateKey,StateFIPS,PostalCode,StateName "
				+ "FROM State " + "WHERE StateFIPS=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectState);
			selectStmt.setString(1, stateFIPS);
			results = selectStmt.executeQuery();
			if (results.next())
			{
				int resultStateKey = results.getInt("StateKey");
				String fips = results.getString("StateFIPS");
				String postalCode = results.getString("PostalCode");
				String resultStateName = results.getString("StateName");
				State state = new State(resultStateKey, fips, postalCode,
						resultStateName);
				return state;
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

	public State updateStateFIPS(State state, String newStateFIPS) throws SQLException
	{
		String updateState = "UPDATE State SET StateFIPS=? WHERE StateKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateState);
			updateStmt.setString(1, newStateFIPS);
			updateStmt.setInt(2, state.getStateKey());
			updateStmt.executeUpdate();
			state.setStateFIPS(newStateFIPS);
			return state;
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

	public State updatePostalCode(State state, String newPostalCode) throws SQLException
	{
		String updateState = "UPDATE State SET PostalCode=? WHERE StateKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateState);
			updateStmt.setString(1, newPostalCode);
			updateStmt.setInt(2, state.getStateKey());
			updateStmt.executeUpdate();
			state.setPostalCode(newPostalCode);
			return state;
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

	public State updateStateName(State state, String newStateName) throws SQLException
	{
		String updateState = "UPDATE State SET StateName=? WHERE StateKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateState);
			updateStmt.setString(1, newStateName);
			updateStmt.setInt(2, state.getStateKey());
			updateStmt.executeUpdate();
			state.setStateName(newStateName);
			return state;
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

	public State delete(State state) throws SQLException
	{
		String deleteState = "DELETE FROM State WHERE StateKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteState);
			deleteStmt.setInt(1, state.getStateKey());
			deleteStmt.executeUpdate();
			// Return null so the caller can no longer operate on the State instance.
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
