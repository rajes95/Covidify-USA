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

@WebServlet("/mortalityratescreate")
public class MortalityRatesCreate extends HttpServlet {

	protected MortalityRatesDao mortalityRatesDao;
	protected CountyDao countyDao;

	@Override
	public void init() throws ServletException {
	  mortalityRatesDao = MortalityRatesDao.getInstance();
	  countyDao = CountyDao.getInstance();
	 }
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);
	    //Just render the JSP.
	    req.getRequestDispatcher("/MortalityRatesCreate.jsp").forward(req, resp);
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
	    	String neonatal = req.getParameter("neonatal");
	    	String hivaids = req.getParameter("hivaids");
	    	String diabetes = req.getParameter("diabetes");
	    	String chronicResp = req.getParameter("chronicresp");
	    	String liverDisease = req.getParameter("liverdisease");
	    	String nutritionalDef = req.getParameter("nutritionaldef");
	    	String cardiovascular = req.getParameter("cardiovascular");
	      try {
	    	  County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
	        if (county == null) {
	          messages.put("success", "County does not exist. Could not locate County.");
	        }
	        else {
	        	MortalityRates mortalityRates = new MortalityRates(county, Short.valueOf(year), Double.valueOf(neonatal), Double.valueOf(hivaids), 
	        			Double.valueOf(diabetes), Double.valueOf(chronicResp), Double.valueOf(liverDisease), Double.valueOf(nutritionalDef),
	        			Double.valueOf(cardiovascular));
	        	mortalityRates = mortalityRatesDao.create(mortalityRates);
	        	messages.put("success", "Successfully created Mortality Rates entry for " + countyname + ", " + statename);
	        }
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	    }

	   req.getRequestDispatcher("/MortalityRatesCreate.jsp").forward(req, resp);
	}
}
