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

import covidify.model.*;
/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`PresidentialElectionVotePercentages` (
  `PresidentialElectionVotePercentagesKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `DemocratsPercent` DECIMAL(5,2) NULL,
  `RepublicansPercent` DECIMAL(5,2) NULL,
  `OtherPercent` DECIMAL(5,2) NULL,
  PRIMARY KEY (`PresidentialElectionVotePercentagesKey`),
  INDEX `CountyFKey2_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey2`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;
 */

public class PresidentialElectionVotePercentagesDao
{
	protected ConnectionManager connectionManager;
	private static PresidentialElectionVotePercentagesDao instance = null;

	protected PresidentialElectionVotePercentagesDao()
	{
		connectionManager = new ConnectionManager();
	}

	public static PresidentialElectionVotePercentagesDao getInstance()
	{
		if (instance == null)
		{
			instance = new PresidentialElectionVotePercentagesDao();
		}
		return instance;
	}

	public PresidentialElectionVotePercentages create(
			PresidentialElectionVotePercentages presidentialElectionVotePercentages)
			throws SQLException
	{
		String insertPresidentialElectionVotePercentages = "INSERT INTO PresidentialElectionVotePercentages"
				+ "(CountyFKey,Year,DemocratsPercent,RepublicansPercent,OtherPercent) "
				+ "VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try
		{
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(
					insertPresidentialElectionVotePercentages,
					Statement.RETURN_GENERATED_KEYS);
			if (presidentialElectionVotePercentages.getCounty() == null)
			{
				insertStmt.setNull(1, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(1,
						presidentialElectionVotePercentages.getCounty().getCountyKey());
			}
			// TODO Not sure about this data type
			if (presidentialElectionVotePercentages.getDemocratsPercent() == null)
			{
				insertStmt.setNull(2, Types.DATE);
			}
			else
			{
				insertStmt.setShort(2, presidentialElectionVotePercentages.getYear());
			}
			if (presidentialElectionVotePercentages.getDemocratsPercent() == null)
			{
				insertStmt.setNull(3, Types.DECIMAL);
			}
			else
			{
				insertStmt.setDouble(3,
						presidentialElectionVotePercentages.getDemocratsPercent());
			}
			if (presidentialElectionVotePercentages.getRepublicansPercent() == null)
			{
				insertStmt.setNull(4, Types.DECIMAL);
			}
			else
			{
				insertStmt.setDouble(4,
						presidentialElectionVotePercentages.getRepublicansPercent());
			}
			if (presidentialElectionVotePercentages.getOtherPercent() == null)
			{
				insertStmt.setNull(5, Types.DECIMAL);
			}
			else
			{
				insertStmt.setDouble(5,
						presidentialElectionVotePercentages.getOtherPercent());
			}
			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int presidentialElectionVotePercentagesKey = -1;
			if (resultKey.next())
			{
				presidentialElectionVotePercentagesKey = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			presidentialElectionVotePercentages.setPresidentialElectionVotePercentagesKey(
					presidentialElectionVotePercentagesKey);
			return presidentialElectionVotePercentages;
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

	public PresidentialElectionVotePercentages getPresidentialElectionVotePercentagesByPresidentialElectionVotePercentagesKey(
			int elecVotePercsKey) throws SQLException
	{
		String selectPevp = "SELECT * " + "FROM PresidentialElectionVotePercentages "
				+ "WHERE PresidentialElectionVotePercentagesKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPevp);
			selectStmt.setInt(1, elecVotePercsKey);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			if (results.next())
			{
				PresidentialElectionVotePercentages pevp = new PresidentialElectionVotePercentages(
						elecVotePercsKey,
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getShort("Year"), results.getDouble("DemocratsPercent"),
						results.getDouble("RepublicansPercent"),
						results.getDouble("OtherPercent"));

				return pevp;
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

	public List<PresidentialElectionVotePercentages> getPresidentialElectionVotePercentagesByYear(
			Short year) throws SQLException
	{
		List<PresidentialElectionVotePercentages> pevps = new ArrayList<PresidentialElectionVotePercentages>();
		String selectPevps = "SELECT * " + "FROM PresidentialElectionVotePercentages "
				+ "WHERE `Year`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPevps);
			selectStmt.setShort(1, year);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			while (results.next())
			{
				PresidentialElectionVotePercentages pevp = new PresidentialElectionVotePercentages(
						results.getInt("PresidentialElectionVotePercentagesKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getShort("Year"), results.getDouble("DemocratsPercent"),
						results.getDouble("RepublicansPercent"),
						results.getDouble("OtherPercent"));
				pevps.add(pevp);
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
		return pevps;
	}

	public List<PresidentialElectionVotePercentages> getPresidentialElectionVotePercentagesByCountyKey(
			int countKey) throws SQLException
	{
		List<PresidentialElectionVotePercentages> pevps = new ArrayList<PresidentialElectionVotePercentages>();
		String selectPevps = "SELECT * " + "FROM PresidentialElectionVotePercentages "
				+ "WHERE `CountyFKey`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPevps);
			selectStmt.setInt(1, countKey);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			while (results.next())
			{
				PresidentialElectionVotePercentages pevp = new PresidentialElectionVotePercentages(
						results.getInt("PresidentialElectionVotePercentagesKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getShort("Year"), results.getDouble("DemocratsPercent"),
						results.getDouble("RepublicansPercent"),
						results.getDouble("OtherPercent"));
				pevps.add(pevp);
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
		return pevps;
	}

	public PresidentialElectionVotePercentages updateYear(
			PresidentialElectionVotePercentages pevp, Short year) throws SQLException
	{
		String updatePevp = "UPDATE PresidentialElectionVotePercentages SET `Year`=? WHERE PresidentialElectionVotePercentagesKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePevp);
			updateStmt.setShort(1, year);
			updateStmt.setInt(2, pevp.getPresidentialElectionVotePercentagesKey());
			updateStmt.executeUpdate();
			pevp.setYear(year);
			;
			return pevp;
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

	public PresidentialElectionVotePercentages updateDemPercent(
			PresidentialElectionVotePercentages pevp, Double demPerc) throws SQLException
	{
		String updatePevp = "UPDATE PresidentialElectionVotePercentages SET `DemocratsPercent`=? "
				+ "WHERE PresidentialElectionVotePercentagesKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePevp);
			updateStmt.setDouble(1, demPerc);
			updateStmt.setInt(2, pevp.getPresidentialElectionVotePercentagesKey());
			updateStmt.executeUpdate();
			pevp.setDemocratsPercent(demPerc);
			return pevp;
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

	public PresidentialElectionVotePercentages updateRepPercent(
			PresidentialElectionVotePercentages pevp, Double repPerc) throws SQLException
	{
		String updatePevp = "UPDATE PresidentialElectionVotePercentages SET `RepublicansPercent`=? "
				+ "WHERE PresidentialElectionVotePercentagesKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePevp);
			updateStmt.setDouble(1, repPerc);
			updateStmt.setInt(2, pevp.getPresidentialElectionVotePercentagesKey());
			updateStmt.executeUpdate();
			pevp.setRepublicansPercent(repPerc);
			return pevp;
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

	public PresidentialElectionVotePercentages updateOtherPercent(
			PresidentialElectionVotePercentages pevp, Double otherPerc)
			throws SQLException
	{
		String updatePevp = "UPDATE PresidentialElectionVotePercentages SET `OtherPercent`=? "
				+ "WHERE PresidentialElectionVotePercentagesKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePevp);
			updateStmt.setDouble(1, otherPerc);
			updateStmt.setInt(2, pevp.getPresidentialElectionVotePercentagesKey());
			updateStmt.executeUpdate();
			pevp.setOtherPercent(otherPerc);
			return pevp;
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

	public PresidentialElectionVotePercentages delete(
			PresidentialElectionVotePercentages presidentialElectionVotePercentages)
			throws SQLException
	{
		String deletePresidentialElectionVotePercentages = "DELETE FROM PresidentialElectionVotePercentages WHERE PresidentialElectionVotePercentagesKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			deleteStmt = connection
					.prepareStatement(deletePresidentialElectionVotePercentages);
			deleteStmt.setInt(1, presidentialElectionVotePercentages
					.getPresidentialElectionVotePercentagesKey());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the
			// PresidentialElectionVotePercentages instance.
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
