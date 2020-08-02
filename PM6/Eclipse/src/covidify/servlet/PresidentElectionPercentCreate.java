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

@WebServlet("/presidentialelectionpercentcreate")
public class PresidentElectionPercentCreate extends HttpServlet {

	protected PresidentialElectionVotePercentagesDao presDao;
	protected CountyDao countyDao;

	@Override
	public void init() throws ServletException {
	  presDao = PresidentialElectionVotePercentagesDao.getInstance();
	  countyDao = CountyDao.getInstance();
	 }
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);
	    //Just render the JSP.
	    req.getRequestDispatcher("/PresidentialElectionPercentCreate.jsp").forward(req, resp);
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
		    String dempercent = req.getParameter("dempercent");
		    String reppercent = req.getParameter("reppercent");
		    String indpercent = req.getParameter("indpercent");
	      try {
	    	  County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
	        if (county == null) {
	          messages.put("success", "County does not exist. Could not locate County.");
	        }
	        else {
	        	PresidentialElectionVotePercentages pres = new PresidentialElectionVotePercentages(county, Short.valueOf(year), Double.valueOf(dempercent), Double.valueOf(reppercent), Double.valueOf(indpercent));
	        	pres = presDao.create(pres);
	        	messages.put("success", "Successfully created Presidential Election Percentage entry for " + countyname + ", " + statename);
	        }
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	    }

	    req.getRequestDispatcher("/PresidentialElectionPercentCreate.jsp").forward(req, resp);
	  }
}

