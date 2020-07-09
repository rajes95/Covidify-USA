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
import java.util.Date;
import java.util.List;

import covidify.model.*;


public class MortalityRatesDao {
  protected ConnectionManager connectionManager;
  private static MortalityRatesDao instance = null;

  protected MortalityRatesDao() {
    connectionManager = new ConnectionManager();
  }

  public static MortalityRatesDao getInstance() {
    if (instance == null) {
      instance = new MortalityRatesDao();
    }
    return instance;
  }
  //TODO here onwards
  public MortalityRates create(MortalityRates mortalityRates) throws SQLException {
    String insertMortalityRates = "INSERT INTO MortalityRates(CountyFKey,Year,NeonatalDisordersMortalityRate,"
    		+ "HIVAIDSandTBMortalityRate,DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate,"
    		+ "ChronicRespiratoryDiseasesMortalityRate,LiverDiseaseMortalityRate,"
    		+ "NutritionalDeficienciesMortalityRate,CardiovascularDiseasesMortalityRate) "
            + "VALUES(?,?,?,?,?,?,?,?,?);";
    Connection connection = null;
    PreparedStatement insertStmt = null;
    ResultSet resultKey = null;
    try {
      connection = connectionManager.getConnection();
      insertStmt = connection.prepareStatement(insertMortalityRates, Statement.RETURN_GENERATED_KEYS);
      if (mortalityRates.getNeonatalRate() == null) {
        insertStmt.setNull(3, Types.VARCHAR);
      } else {
        insertStmt.setDouble(3, mortalityRates.getNeonatalRate());
      }
      if (mortalityRates.getHivRate() == null) {
        insertStmt.setNull(4, Types.INTEGER);
      } else {
        insertStmt.setDouble(4, mortalityRates.getHivRate());
      }
      if (mortalityRates.getDiabetesRate() == null) {
          insertStmt.setNull(5, Types.INTEGER);
        } else {
          insertStmt.setDouble(5, mortalityRates.getDiabetesRate());
        }
      if (mortalityRates.getChronicRespitoraryRate() == null) {
          insertStmt.setNull(6, Types.INTEGER);
        } else {
          insertStmt.setDouble(6, mortalityRates.getChronicRespitoraryRate());
        }
      if (mortalityRates.getLiverDiseaseRate() == null) {
          insertStmt.setNull(7, Types.INTEGER);
        } else {
          insertStmt.setDouble(7, mortalityRates.getLiverDiseaseRate());
        }
      if (mortalityRates.getNutritionalDeficienciesRate() == null) {
          insertStmt.setNull(8, Types.INTEGER);
        } else {
          insertStmt.setDouble(8, mortalityRates.getNutritionalDeficienciesRate());
        }
      if (mortalityRates.getCardiovascularRate() == null) {
          insertStmt.setNull(9, Types.INTEGER);
        } else {
          insertStmt.setDouble(9, mortalityRates.getCardiovascularRate());
        }
      insertStmt.executeUpdate();

      resultKey = insertStmt.getGeneratedKeys();
      int mortalityRatesKey = -1;
      if (resultKey.next()) {
        mortalityRatesKey = resultKey.getInt(1);
      } else {
        throw new SQLException("Unable to retrieve auto-generated key.");
      }
      mortalityRates.setMortalityRatesKey(mortalityRatesKey);
      return mortalityRates;
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


  public List<MortalityRates> getMortalityRatesByCounty(County county) throws SQLException {
		List<MortalityRates> mortalityRatess = new ArrayList<MortalityRates>();
	    String selectMortalityRates =
	            "SELECT MortalityRatesKey,CountyFKey,Year,NeonatalDisordersMortalityRate,HIVAIDSandTBMortalityRate,"
	            + "DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate,ChronicRespiratoryDiseasesMortalityRate,"
	            + "LiverDiseaseMortalityRate,NutritionalDeficienciesMortalityRate,CardiovascularDiseasesMortalityRate"
	            + " FROM MortalityRates WHERE CountyFKey=?;";
	    Connection connection = null;
	    PreparedStatement selectStmt = null;
	    ResultSet results = null;
	    try {
	      connection = connectionManager.getConnection();
	      selectStmt = connection.prepareStatement(selectMortalityRates);
	      selectStmt.setInt(1, county.getCountyKey());
	      results = selectStmt.executeQuery();

	      while(results.next()) {
	        int mortalityRatesKey = results.getInt("MortalityRatesKey");
	        Date resyear = results.getDate("Year");
	        Double resneo = results.getDouble("NeonatalDisordersMortalityRate");
	        Double reshiv = results.getDouble("HIVAIDSandTBMortalityRate");
	        Double resdiab = results.getDouble("DiabetesUrogenitalBloodEndocrineDiseaseMortalityRate");
	        Double resresp = results.getDouble("ChronicRespiratoryDiseasesMortalityRate");
	        Double resliver = results.getDouble("LiverDiseaseMortalityRate");
	        Double resnutr = results.getDouble("NutritionalDeficienciesMortalityRate");
	        Double rescardio = results.getDouble("CardiovascularDiseasesMortalityRate");

	        MortalityRates mortalityRates = new MortalityRates(mortalityRatesKey, county, resyear, resneo,
	        		reshiv,resdiab,resresp,resliver,resnutr,rescardio);
	        mortalityRatess.add(mortalityRates);
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
	    return mortalityRatess;
	  }

  public MortalityRates updateMortalityRates(MortalityRates mortalityRates, County county) throws SQLException {
		String updateMortalityRates = "UPDATE MortalityRates SET CountyFKey=? WHERE MortalityRatesKey=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateMortalityRates);
			updateStmt.setInt(1, county.getCountyKey());
			updateStmt.setInt(2, mortalityRates.getMortalityRatesKey());
			updateStmt.executeUpdate();
			mortalityRates.setCounty(county);
			return mortalityRates;
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


  public MortalityRates delete(MortalityRates mortalityRates) throws SQLException {
    String deleteMortalityRates = "DELETE FROM MortalityRates WHERE MortalityRatesKey=?;";
    Connection connection = null;
    PreparedStatement deleteStmt = null;
    try {
      connection = connectionManager.getConnection();
      deleteStmt = connection.prepareStatement(deleteMortalityRates);
      deleteStmt.setInt(1, mortalityRates.getMortalityRatesKey());
      deleteStmt.executeUpdate();

      // Return null so the caller can no longer operate on the MortalityRates instance.
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
