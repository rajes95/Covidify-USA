/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

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

public class County
{
	protected int countyKey;
	protected State state;
	protected String countyFIPS;
	protected String countyName;
	// TODO: Switching these to String will solve a nullPointer error in CountyDao
	// when longitude and latitude aren't present (are null in db).
	protected Double longitude;
	protected Double latitude;

	public County(int countyKey, State state, String countyFIPS, String countyName,
			Double longitude, Double latitude)
	{
		this.countyKey = countyKey;
		this.state = state;
		this.countyFIPS = countyFIPS;
		this.countyName = countyName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public County(int countyKey)
	{
		this.countyKey = countyKey;
	}

	public County(State state, String countyFIPS, String countyName, Double longitude,
			Double latitude)
	{
		this.state = state;
		this.countyFIPS = countyFIPS;
		this.countyName = countyName;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public int getCountyKey()
	{
		return countyKey;
	}

	public void setCountyKey(int countyKey)
	{
		this.countyKey = countyKey;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public String c()
	{
		return countyFIPS;
	}

	public void setCountyFIPS(String countyFIPS)
	{
		this.countyFIPS = countyFIPS;
	}

	public String getCountyName()
	{
		return countyName;
	}

	public void setCountyName(String countyName)
	{
		this.countyName = countyName;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double latitude)
	{
		this.latitude = latitude;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double longitude)
	{
		this.longitude = longitude;
	}

	public String toString()
	{

		String str = "CountyKey:" + this.getCountyKey() + ", StateName:"
				+ this.getState().getStateName() + ", CountyFIPS:" + this.getCountyFIPS()
				+ ", CountyName:" + this.getCountyName() + ", Longitude:"
				+ this.getLongitude() + ", Latitude:" + this.getLatitude();

		return str;
	}

}