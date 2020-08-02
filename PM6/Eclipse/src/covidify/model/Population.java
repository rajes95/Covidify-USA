/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;

/*

CREATE TABLE IF NOT EXISTS `CovidifyUSA`.`Population` (
  `PopulationKey` INT NOT NULL AUTO_INCREMENT,
  `CountyFKey` INT NOT NULL,
  `Year` YEAR NULL,
  `TotalPopulation` INT NULL,
  `Population60Plus` INT NULL,
  PRIMARY KEY (`PopulationKey`),
  INDEX `CountyFKey7_idx` (`CountyFKey` ASC),
  UNIQUE INDEX `Unique` (`CountyFKey` ASC, `Year` ASC),
  CONSTRAINT `CountyFKey7`
    FOREIGN KEY (`CountyFKey`)
    REFERENCES `CovidifyUSA`.`County` (`CountyKey`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

 */

public class Population
{
	protected int populationKey;
	protected County county;
	protected Short year;
	protected Integer totalPopulation;
	protected Integer population60Plus;

	public Population(int populationKey, County county, Short year,
			Integer totalPopulation, Integer population60Plus)
	{
		this.populationKey = populationKey;
		this.county = county;
		this.year = year;
		this.totalPopulation = totalPopulation;
		this.population60Plus = population60Plus;
	}

	public Population(int populationKey)
	{
		this.populationKey = populationKey;
	}

	public Population(County county, Short year, Integer totalPopulation,
			Integer population60Plus)
	{
		this.county = county;
		this.year = year;
		this.totalPopulation = totalPopulation;
		this.population60Plus = population60Plus;
	}

	public int getPopulationKey()
	{
		return populationKey;
	}

	public void setPopulationKey(int populationKey)
	{
		this.populationKey = populationKey;
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

	public Integer getTotalPopulation()
	{
		return totalPopulation;
	}

	public void setTotalPopulation(Integer totalPopulation)
	{
		this.totalPopulation = totalPopulation;
	}

	public Integer getPopulation60Plus()
	{
		return population60Plus;
	}

	public void setPopulation60Plus(Integer population60Plus)
	{
		this.population60Plus = population60Plus;
	}

	public String toString()
	{
		String str = "PopulationKey:" + this.getPopulationKey() + ", State:"
				+ this.getCounty().getState().getStateName() + ", County:"
				+ this.getCounty().getCountyName() + ", Year:" + this.getYear()
				+ ", TotalPopulation:" + this.getTotalPopulation() + ", Population60Plus:"
				+ this.getPopulation60Plus();
		return str;
	}
}
