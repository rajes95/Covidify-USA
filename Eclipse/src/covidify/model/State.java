/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

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

public class State
{
	protected int stateKey;
	protected String stateFIPS;
	protected String postalCode;
	protected String stateName;

	public State(int stateKey, String stateFIPS, String postalCode, String stateName)
	{
		this.stateKey = stateKey;
		this.stateFIPS = stateFIPS;
		this.postalCode = postalCode;
		this.stateName = stateName;
	}

	public State(int stateKey)
	{
		this.stateKey = stateKey;
	}

	public State(String stateFIPS, String postalCode, String stateName)
	{
		this.stateFIPS = stateFIPS;
		this.postalCode = postalCode;
		this.stateName = stateName;
	}

	public int getStateKey()
	{
		return stateKey;
	}

	public void setStateKey(int stateKey)
	{
		this.stateKey = stateKey;
	}

	public String getStateFIPS()
	{
		return stateFIPS;
	}

	public void setStateFIPS(String stateFIPS)
	{
		this.stateFIPS = stateFIPS;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String postalCode)
	{
		this.postalCode = postalCode;
	}

	public String getStateName()
	{
		return stateName;
	}

	public void setStateName(String stateName)
	{
		this.stateName = stateName;
	}

	public String toString()
	{

		String str = "StateKey:" + this.getStateKey() + ", StateFIPS:"
				+ this.getStateFIPS() + ", PostalCode:" + this.getPostalCode()
				+ ", StateName:" + this.getStateName();

		return str;
	}

}
