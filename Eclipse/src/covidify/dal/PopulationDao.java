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

public class PopulationDao
{
	protected ConnectionManager connectionManager;
	private static PopulationDao instance = null;

	protected PopulationDao()
	{
		connectionManager = new ConnectionManager();
	}

	public static PopulationDao getInstance()
	{
		if (instance == null)
		{
			instance = new PopulationDao();
		}
		return instance;
	}

	public Population create(Population pop) throws SQLException
	{
		String insertPop = "INSERT INTO Population"
				+ "(CountyFKey,Year,TotalPopulation,Population60Plus) "
				+ "VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try
		{
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertPop,
					Statement.RETURN_GENERATED_KEYS);
			if (pop.getCounty() == null)
			{
				insertStmt.setNull(1, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(1, pop.getCounty().getCountyKey());
			}
			if (pop.getYear() == null)
			{
				insertStmt.setNull(2, Types.DATE);
			}
			else
			{
				insertStmt.setShort(2, pop.getYear());
			}
			if (pop.getTotalPopulation() == null)
			{
				insertStmt.setNull(3, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(3, pop.getTotalPopulation());
			}
			if (pop.getPopulation60Plus() == null)
			{
				insertStmt.setNull(4, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(4, pop.getPopulation60Plus());
			}

			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int populationKey = -1;
			if (resultKey.next())
			{
				populationKey = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			pop.setPopulationKey(populationKey);
			return pop;
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

	public Population getPopulationByPopulationKey(int popKey) throws SQLException
	{
		String selectPop = "SELECT * " + "FROM Population " + "WHERE PopulationKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPop);
			selectStmt.setInt(1, popKey);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			if (results.next())
			{
				Population pop = new Population(results.getInt("PopulationKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getShort("Year"), results.getInt("TotalPopulation"),
						results.getInt("Population60Plus"));

				return pop;
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

	public List<Population> getPopulationByYear(Short year) throws SQLException
	{
		List<Population> pops = new ArrayList<Population>();
		String selectPops = "SELECT * " + "FROM Population " + "WHERE `Year`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPops);
			selectStmt.setShort(1, year);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			while (results.next())
			{
				Population pop = new Population(results.getInt("PopulationKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getShort("Year"), results.getInt("TotalPopulation"),
						results.getInt("Population60Plus"));
				pops.add(pop);
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
		return pops;
	}

	public List<Population> getPopulationByCountyKey(int countyKey) throws SQLException
	{
		List<Population> pops = new ArrayList<Population>();
		String selectPops = "SELECT * " + "FROM Population " + "WHERE `CountyFKey`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectPops);
			selectStmt.setInt(1, countyKey);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			while (results.next())
			{
				Population pop = new Population(results.getInt("PopulationKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getShort("Year"), results.getInt("TotalPopulation"),
						results.getInt("Population60Plus"));
				pops.add(pop);
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
		return pops;
	}

	public Population updateYear(Population pop, Short year) throws SQLException
	{
		String updatePop = "UPDATE Population SET `Year`=? WHERE PopulationKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePop);
			updateStmt.setShort(1, year);
			updateStmt.setInt(2, pop.getPopulationKey());
			updateStmt.executeUpdate();
			pop.setYear(year);
			return pop;
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

	public Population updateTotalPopulation(Population pop, Integer total)
			throws SQLException
	{
		String updatePop = "UPDATE Population SET `TotalPopulation`=? WHERE PopulationKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePop);
			updateStmt.setInt(1, total);
			updateStmt.setInt(2, pop.getPopulationKey());
			updateStmt.executeUpdate();
			pop.setTotalPopulation(total);
			return pop;
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

	public Population updatePopulation60Plus(Population pop, Integer sixtyPlus)
			throws SQLException
	{
		String updatePop = "UPDATE Population SET `Population60Plus`=? WHERE PopulationKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updatePop);
			updateStmt.setInt(1, sixtyPlus);
			updateStmt.setInt(2, pop.getPopulationKey());
			updateStmt.executeUpdate();
			pop.setPopulation60Plus(sixtyPlus);
			return pop;
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

	public Population delete(Population pop) throws SQLException
	{
		String deletePop = "DELETE FROM Population WHERE PopulationKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deletePop);
			deleteStmt.setInt(1, pop.getPopulationKey());
			deleteStmt.executeUpdate();

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
