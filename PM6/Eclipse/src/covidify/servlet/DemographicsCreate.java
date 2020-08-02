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


@WebServlet("/demographiccreate")
public class DemographicsCreate extends HttpServlet {
	
	protected DemographicsDao demographicDao;
	protected CountyDao countyDao;

	@Override
	public void init() throws ServletException {
	  demographicDao = DemographicsDao.getInstance();
	  countyDao = CountyDao.getInstance();
	 }
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);
	    //Just render the JSP.
	    req.getRequestDispatcher("/DemographicsCreate.jsp").forward(req, resp);
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
	      // Create the Demographic.
	    	String year = req.getParameter("year");
		    String white = req.getParameter("white");
		    String africanamerican = req.getParameter("africanamerican");
		    String latino = req.getParameter("latino");
		    String nativeamerican = req.getParameter("nativeamerican");
		    String asian = req.getParameter("asian");
		    String other = req.getParameter("other");
		    String poverty = req.getParameter("poverty");
		    String medianage = req.getParameter("medianage");
		    String medianearnings = req.getParameter("medianearnings");
	      try {
	    	  County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
	        if (county == null) {
	          messages.put("success", "County does not exist. Could not locate County.");
	        }
	        else {
	        	Demographics demographics = new Demographics(county, Short.valueOf(year), Double.valueOf(white), Double.valueOf(africanamerican),
	        			Double.valueOf(latino), Double.valueOf(nativeamerican), Double.valueOf(asian), Double.valueOf(other), Double.valueOf(poverty),
	        			Double.valueOf(medianage), Double.valueOf(medianearnings));
	        	demographics = demographicDao.create(demographics);
	        	messages.put("success", "Successfully created Demographics entry for " + countyname + ", " + statename);
	        }
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	    }

	    req.getRequestDispatcher("/DemographicsCreate.jsp").forward(req, resp);
	  }
}
