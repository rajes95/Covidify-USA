/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

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

public class PresidentialElectionVotePercentages
{
	protected int presidentialElectionVotePercentagesKey;
	protected County county;
	protected Short year;
	protected Double democratsPercent;
	protected Double republicansPercent;
	protected Double otherPercent;

	public PresidentialElectionVotePercentages(int presidentialElectionVotePercentagesKey,
			County county, Short year, Double democratsPercent, Double republicansPercent,
			Double otherPercent)
	{
		this.presidentialElectionVotePercentagesKey = presidentialElectionVotePercentagesKey;
		this.county = county;
		this.year = year;
		this.democratsPercent = democratsPercent;
		this.republicansPercent = republicansPercent;
		this.otherPercent = otherPercent;
	}

	public PresidentialElectionVotePercentages(int presidentialElectionVotePercentagesKey)
	{
		this.presidentialElectionVotePercentagesKey = presidentialElectionVotePercentagesKey;
	}

	public PresidentialElectionVotePercentages(County county, Short year,
			Double democratsPercent, Double republicansPercent, Double otherPercent)
	{
		this.county = county;
		this.year = year;
		this.democratsPercent = democratsPercent;
		this.republicansPercent = republicansPercent;
		this.otherPercent = otherPercent;
	}

	public int getPresidentialElectionVotePercentagesKey()
	{
		return presidentialElectionVotePercentagesKey;
	}

	public void setPresidentialElectionVotePercentagesKey(
			int presidentialElectionVotePercentagesKey)
	{
		this.presidentialElectionVotePercentagesKey = presidentialElectionVotePercentagesKey;
	}

	public County getCounty()
	{
		return county;
	}

	public void setCounty(County county)
	{
		this.county = county;
	}

	public Short getYear()
	{
		return year;
	}

	public void setYear(Short year)
	{
		this.year = year;
	}

	public Double getDemocratsPercent()
	{
		return democratsPercent;
	}

	public void setDemocratsPercent(Double democratsPercent)
	{
		this.democratsPercent = democratsPercent;
	}

	public Double getRepublicansPercent()
	{
		return republicansPercent;
	}

	public void setRepublicansPercent(Double republicansPercent)
	{
		this.republicansPercent = republicansPercent;
	}

	public Double getOtherPercent()
	{
		return otherPercent;
	}

	public void setOtherPercent(Double otherPercent)
	{
		this.otherPercent = otherPercent;
	}

	public String toString()
	{
		String str = "PresidentialElectionVotePercentagesKey:"
				+ this.getPresidentialElectionVotePercentagesKey() + ", State:"
				+ this.getCounty().getState().getStateName() + ", County:"
				+ this.getCounty().getCountyName() + ", Year:" + this.getYear()
				+ ", DemocratsPercent:" + this.getDemocratsPercent()
				+ ", RepublicansPercent:" + this.getRepublicansPercent()
				+ ", OtherPercent:" + this.getOtherPercent();
		return str;
	}

}