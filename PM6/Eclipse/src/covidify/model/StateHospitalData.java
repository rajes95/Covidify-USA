/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

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

public class StateHospitalData
{
	protected int stateHospitalDataKey;
	protected State state;
	protected Short year;
	protected Long numberOfHospitals;
	protected Long numberOfHospitalEmployees;

	public StateHospitalData(int stateHospitalDataKey, State state, Short year,
			Long numberOfHospitals, Long numberOfHospitalEmployees)
	{
		this.stateHospitalDataKey = stateHospitalDataKey;
		this.state = state;
		this.year = year;
		this.numberOfHospitals = numberOfHospitals;
		this.numberOfHospitalEmployees = numberOfHospitalEmployees;
	}

	public StateHospitalData(int stateHospitalDataKey)
	{
		this.stateHospitalDataKey = stateHospitalDataKey;
	}

	public StateHospitalData(State state, Short year, Long numberOfHospitals,
			Long numberOfHospitalEmployees)
	{
		this.state = state;
		this.year = year;
		this.numberOfHospitals = numberOfHospitals;
		this.numberOfHospitalEmployees = numberOfHospitalEmployees;
	}

	public int getStateHospitalDataKey()
	{
		return stateHospitalDataKey;
	}

	public void setStateHospitalDataKey(int stateHospitalDataKey)
	{
		this.stateHospitalDataKey = stateHospitalDataKey;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public Short getYear()
	{
		return year;
	}

	public void setYear(Short year)
	{
		this.year = year;
	}

	public Long getNumberOfHospitals()
	{
		return numberOfHospitals;
	}

	public void setNumberOfHospitals(Long numberOfHospitals)
	{
		this.numberOfHospitals = numberOfHospitals;
	}

	public Long getNumberOfHospitalEmployees()
	{
		return numberOfHospitalEmployees;
	}

	public void setNumberOfHospitalEmployees(Long numberOfHospitalEmployees)
	{
		this.numberOfHospitalEmployees = numberOfHospitalEmployees;
	}

	public String toString()
	{
		String str = "StateHospitalDataKey:" + this.getStateHospitalDataKey()
				+ ", StateName:" + this.getState().getStateName() + ", Year:"
				+ this.getYear() + ", NumberOfHospitals:" + this.getNumberOfHospitals()
				+ ", NumberOfHospitalEmployees:" + this.getNumberOfHospitalEmployees();

		return str;
	}

}
