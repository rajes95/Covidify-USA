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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import covidify.model.*;

/*
CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`CovidByDate` (
  `CovidByDateKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Date` DATE NULL,
  `CovidDeaths` INT NULL,
  `CovidCases` INT NULL,
  PRIMARY KEY (`CovidByDateKey`),
  INDEX `CountyFKey1_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `CovUniq` (`CountyFKey` ASC, `Date` ASC),
  CONSTRAINT `CountyFKey1`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE RESTRICT
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

public class CovidByDateDao
{
	protected ConnectionManager connectionManager;
	private static CovidByDateDao instance = null;

	protected CovidByDateDao()
	{
		connectionManager = new ConnectionManager();
	}

	public static CovidByDateDao getInstance()
	{
		if (instance == null)
		{
			instance = new CovidByDateDao();
		}
		return instance;
	}

	public CovidByDate create(CovidByDate covidByDate) throws SQLException
	{
		String insertCovidByDate = "INSERT INTO CovidByDate(CountyFKey,Date,CovidDeaths,CovidCases) "
				+ "VALUES(?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try
		{
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertCovidByDate,
					Statement.RETURN_GENERATED_KEYS);
			if (covidByDate.getCounty() == null)
			{
				insertStmt.setNull(1, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(1, covidByDate.getCounty().getCountyKey());
			}
			if (covidByDate.getDate() == null)
			{
				insertStmt.setNull(2, Types.TIMESTAMP);
			}
			else
			{
				insertStmt.setTimestamp(2,
						new Timestamp(covidByDate.getDate().getTime()));
			}
			if (covidByDate.getCovidDeaths() == null)
			{
				insertStmt.setNull(3, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(3, covidByDate.getCovidDeaths());
			}
			if (covidByDate.getCovidCases() == null)
			{
				insertStmt.setNull(4, Types.INTEGER);
			}
			else
			{
				insertStmt.setInt(4, covidByDate.getCovidCases());
			}
			insertStmt.executeUpdate();

			resultKey = insertStmt.getGeneratedKeys();
			int covidByDateKey = -1;
			if (resultKey.next())
			{
				covidByDateKey = resultKey.getInt(1);
			}
			else
			{
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			covidByDate.setCovidByDateKey(covidByDateKey);
			return covidByDate;
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

	public CovidByDate getCovidByDateByCovidByDateKey(int covKey) throws SQLException
	{
		String selectCov = "SELECT * " + "FROM CovidByDate " + "WHERE CovidByDateKey=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCov);
			selectStmt.setInt(1, covKey);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			if (results.next())
			{
				CovidByDate cov = new CovidByDate(results.getInt("CovidByDateKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getDate("Date"), results.getInt("CovidDeaths"),
						results.getInt("CovidCases"));

				return cov;
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

	public List<CovidByDate> getCovidByDateByCountyKey(int countyKey) throws SQLException
	{
		List<CovidByDate> covs = new ArrayList<CovidByDate>();
		String selectCovs = "SELECT * " + "FROM CovidByDate " + "WHERE `CountyFKey`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCovs);
			selectStmt.setInt(1, countyKey);
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			while (results.next())
			{
				CovidByDate cov = new CovidByDate(results.getInt("CovidByDateKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getDate("Date"), results.getInt("CovidDeaths"),
						results.getInt("CovidCases"));
				covs.add(cov);
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
		return covs;
	}

	public List<CovidByDate> getCovidByDateByDate(Date date) throws SQLException
	{
		List<CovidByDate> covs = new ArrayList<CovidByDate>();
		String selectCovs = "SELECT * " + "FROM CovidByDate " + "WHERE `Date`=?;";
		;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try
		{
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectCovs);
			selectStmt.setTimestamp(1, new Timestamp(date.getTime()));
			results = selectStmt.executeQuery();
			CountyDao countyDao = CountyDao.getInstance();

			while (results.next())
			{
				CovidByDate cov = new CovidByDate(results.getInt("CovidByDateKey"),
						countyDao.getCountyByCountyKey(results.getInt("CountyFKey")),
						results.getDate("Date"), results.getInt("CovidDeaths"),
						results.getInt("CovidCases"));
				covs.add(cov);
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
		return covs;
	}

	public CovidByDate updateDate(CovidByDate cov, Date date) throws SQLException
	{
		String updateCov = "UPDATE CovidByDate SET `Date`=? WHERE CovidByDateKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCov);
			updateStmt.setTimestamp(1, new Timestamp(date.getTime()));
			updateStmt.setInt(2, cov.getCovidByDateKey());
			updateStmt.executeUpdate();
			cov.setDate(date);
			return cov;
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

	public CovidByDate updateCovidDeaths(CovidByDate cov, int covDeaths)
			throws SQLException
	{
		String updateCov = "UPDATE CovidByDate SET `CovidDeaths`=? WHERE CovidByDateKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCov);
			updateStmt.setInt(1, covDeaths);
			updateStmt.setInt(2, cov.getCovidByDateKey());
			updateStmt.executeUpdate();
			cov.setCovidDeaths(covDeaths);
			return cov;
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

	public CovidByDate updateCovidCases(CovidByDate cov, int covCases) throws SQLException
	{
		String updateCov = "UPDATE CovidByDate SET `CovidCases`=? WHERE CovidByDateKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateCov);
			updateStmt.setInt(1, covCases);
			updateStmt.setInt(2, cov.getCovidByDateKey());
			updateStmt.executeUpdate();
			cov.setCovidCases(covCases);
			return cov;
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

	public CovidByDate delete(CovidByDate covidByDate) throws SQLException
	{
		String deleteCovidByDate = "DELETE FROM CovidByDate WHERE CovidByDateKey=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try
		{
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteCovidByDate);
			deleteStmt.setInt(1, covidByDate.getCovidByDateKey());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the CovidByDate
			// instance.
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
