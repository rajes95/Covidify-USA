package covidify.servlet;

import covidify.dal.*;
import covidify.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/populationcreate")
public class PopulationCreate extends HttpServlet {

	protected PopulationDao populationDao;
	protected CountyDao countyDao;

	@Override
	public void init() throws ServletException {
	  populationDao = PopulationDao.getInstance();
	  countyDao = CountyDao.getInstance();
	 }
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);
	    //Just render the JSP.
	    req.getRequestDispatcher("/PopulationCreate.jsp").forward(req, resp);
	  }
	
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);

	    // Retrieve and validate name.
	    String countyname = req.getParameter("countyname");
	    String statename = req.getParameter("statename");
	    if (countyname == null || countyname.trim().isEmpty() || statename == null || statename.trim().isEmpty()) {
	      messages.put("success", "Please enter a valid County and State name pair.");
	    } else {
	      // Create the StateHospital.
	    	String year = req.getParameter("year");
		    String totalPopulation = req.getParameter("totalPopulation");
		    String population60Plus = req.getParameter("population60Plus");
	      try {
	    	  County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
	        if (county == null) {
	          messages.put("success", "County does not exist. Could not locate County.");
	        }
	        else {
	        	Population population = new Population(county, Short.valueOf(year), Integer.valueOf(totalPopulation), Integer.valueOf(population60Plus));
	        	population = populationDao.create(population);
	        	messages.put("success", "Successfully created Population entry for " + countyname + ", " + statename);
	        }
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	    }

	    req.getRequestDispatcher("/PopulationCreate.jsp").forward(req, resp);
	  }
}
