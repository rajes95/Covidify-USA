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


@WebServlet("/climatecreate")
public class ClimateCreate extends HttpServlet {

	protected ClimateDao climateDao;
	protected CountyDao countyDao;

	@Override
	public void init() throws ServletException {
	  climateDao = ClimateDao.getInstance();
	  countyDao = CountyDao.getInstance();
	 }
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);
	    //Just render the JSP.
	    req.getRequestDispatcher("/ClimateCreate.jsp").forward(req, resp);
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
	    	String elevation = req.getParameter("elevation");
	    	String winterprcp = req.getParameter("winterprcp");
	    	String summerprcp = req.getParameter("summerprcp");
	    	String springprcp = req.getParameter("springprcp");
	    	String autumnprcp = req.getParameter("autumnprcp");
	    	String winterTavg = req.getParameter("winterTavg");
	    	String summerTavg = req.getParameter("summerTavg");
	    	String springTavg = req.getParameter("springTavg");
	    	String autumnTavg = req.getParameter("autumnTavg");
	      try {
	    	  County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
	        if (county == null) {
	          messages.put("success", "County does not exist. Could not locate County.");
	        }
	        else {
	        	Climate climate = new Climate(county, Short.valueOf(year), Double.valueOf(elevation), Double.valueOf(winterprcp), 
	        			Double.valueOf(summerprcp), Double.valueOf(springprcp), Double.valueOf(autumnprcp), Double.valueOf(winterTavg),
	        			Double.valueOf(summerTavg), Double.valueOf(springTavg), Double.valueOf(autumnTavg));
	        	climate = climateDao.create(climate);
	        	messages.put("success", "Successfully created Climate entry for " + countyname + ", " + statename);
	        }
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	    }

	   req.getRequestDispatcher("/ClimateCreate.jsp").forward(req, resp);
	}
}
