/**
 * REAL Team Six - CS5200 Database Management - PM04
 */
package covidify.tools;

import covidify.dal.*;
import covidify.model.*;
import covidify.model.StateGovernor.GovernorPartyType;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * main() runner, used for the app demo.
 * 
 */
public class Inserter
{

	public static void main(String[] args) throws SQLException
	{
		// DAO instances.
		StateDao stateDao = StateDao.getInstance();
		CountyDao countyDao = CountyDao.getInstance();
		StateGovernorDao govDao = StateGovernorDao.getInstance();
		StateHospitalDataDao shdDao = StateHospitalDataDao.getInstance();
		PresidentialElectionVotePercentagesDao pevpDao = PresidentialElectionVotePercentagesDao
				.getInstance();
		PopulationDao popDao = PopulationDao.getInstance();

		// INSERT rows into CovidifyUSA database.

		// READ.
		State cali = stateDao.getStateByName("California");
		System.out.println(cali);

		County santaClara = countyDao.getCountyByFIPS("06085");
		System.out.println(santaClara);

		Population pop = new Population(santaClara, (short) 2012, 5000, 50000);
		pop = popDao.create(pop);

//		List<Population> pops = popDao
//				.getPopulationByCountyKey(pop.getCounty().getCountyKey());
//
//		for (Population p : pops)
//		{
//			System.out.println(p);
//		}
//
//		pops = popDao.getPopulationByYear((short) 2020);
//
//		for (Population p : pops)
//		{
//			System.out.println(p);
//		}
//		popDao.updateYear(pop, (short) 1999);
//		popDao.updateTotalPopulation(pop, 700);
//		popDao.updatePopulation60Plus(pop, 100);
//
//		pop = popDao.getPopulationByPopulationKey(pop.getPopulationKey());
//
//		System.out.println(pop);
//
//		popDao.delete(pop);

//		PresidentialElectionVotePercentages pevp = new PresidentialElectionVotePercentages(
//				santaClara, (short) 2020, .25, .30, .30);
//		pevp = pevpDao.create(pevp);
//
//		System.out.println(pevp);
//
//		pevp = pevpDao
//				.getPresidentialElectionVotePercentagesByPresidentialElectionVotePercentagesKey(
//						pevp.getPresidentialElectionVotePercentagesKey());
//
//		System.out.println(pevp);
//
//		List<PresidentialElectionVotePercentages> pevps = pevpDao
//				.getPresidentialElectionVotePercentagesByYear((short) 2008);
//
//		for(PresidentialElectionVotePercentages p : pevps)
//		{
//			System.out.println(p);
//		}
//
//		pevpDao.updateYear(pevp, (short) 2019);
//		pevpDao.updateDemPercent(pevp, .75);
//		pevpDao.updateRepPercent(pevp, .24);
//		pevpDao.updateOtherPercent(pevp, .01);
//
//		List<PresidentialElectionVotePercentages> pevps = pevpDao
//				.getPresidentialElectionVotePercentagesByCountyKey(
//						pevp.getCounty().getCountyKey());
//
//		for (PresidentialElectionVotePercentages p : pevps)
//		{
//			System.out.println(p);
//		}
//
//		pevpDao.delete(pevp);

//		StateHospitalData sh = new StateHospitalData(cali, (short) 2010, 100L, 20000L);
//		sh = shdDao.create(sh);
//
//		sh = shdDao
//				.getStateHospitalDataByStateHospitalDataKey(sh.getStateHospitalDataKey());
//		System.out.println(sh);
//
//		List<StateHospitalData> shs = shdDao
//				.getStateHospitalDataByStateKey(sh.getState().getStateKey());
//		System.out.println(shs);
//
//		List<StateHospitalData> byYear = shdDao.getStateHospitalDataByYear((short) 2019);
//
//		for (StateHospitalData b : byYear)
//		{
//			System.out.println(b);
//		}
//
//		shdDao.updateStateHospitalDataYear(sh, (short) 2000);
//		shdDao.updateStateHospitalDataNumOfHospitals(sh, 2000L);
//		shdDao.updateStateHospitalDataNumOfHospitalEmployees(sh, 2000L);
//
//		System.out.println(shdDao.getStateHospitalDataByStateHospitalDataKey(
//				sh.getStateHospitalDataKey()));
//
//		shdDao.delete(sh);

//		StateGovernor newGov = new StateGovernor(cali, (short) 2005,
//				"Arnold Schwarzenegger", GovernorPartyType.Republican);
//
//		newGov = govDao.create(newGov);
//
//		System.out.println(newGov);
//
//		StateGovernor governator = govDao
//				.getStateGovernorByStateGovernorKey(newGov.getStateGovernorKey());
//		System.out.println(governator);
//
//		List<StateGovernor> govs = govDao.getStateGovernorsByPartyAndYear(
//				GovernorPartyType.Republican, (short) 2020);
//		for (StateGovernor g : govs)
//		{
//			System.out.println(g);
//		}
//
//		List<StateGovernor> goves = govDao
//				.getStateGovernorsByStateKeyAndYear(cali.getStateKey(), (short) 2020);
//
//		for (StateGovernor g : goves)
//		{
//			System.out.println(g);
//		}
//
//		governator = govDao.updateGovernorName(governator, "Governator");
//		System.out.println(
//				govDao.getStateGovernorByStateGovernorKey(newGov.getStateGovernorKey()));
//		governator = govDao.updateGovernorParty(governator, GovernorPartyType.Other);
//		System.out.println(
//				govDao.getStateGovernorByStateGovernorKey(newGov.getStateGovernorKey()));
//
//		govDao.delete(governator);

	}

}
