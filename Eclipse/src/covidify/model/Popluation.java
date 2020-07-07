/**
 * Real Team Six- CS5200 Database Management - CovidifyUSA - PM4
 * <p>
 * Lily Bessette, Ari Fleischer, Elise Jortberg, Rajesh Sakhamuru
 */
package covidify.model;



public class Popluation {
  protected int countyKey;
  protected String name;
  protected String description;

  public Popluation(int companyKey, String name, String description){
    this.companyKey = companyKey;
    this.name = name;
    this.description = description;
  }

  public Popluation(int companyKey){
    this.companyKey = companyKey;
  }

  public Popluation(String name, String description){
    this.name = name;
    this.description = description;
  }
  public int getCompanyKey() {
    return companyKey;
  }

  public void setCompanyKey(int companyKey) {
    this.companyKey = companyKey;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}