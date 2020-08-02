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

@WebServlet("/statehospitaldatacreate")
public class StateHospitalDataCreate extends HttpServlet {

	protected StateHospitalDataDao stateHospitalDao;
	protected StateDao stateDao;
	
	@Override
	  public void init() throws ServletException {
		stateHospitalDao = StateHospitalDataDao.getInstance();
		stateDao = StateDao.getInstance();
	  }
	
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);
	    //Just render the JSP.
	    req.getRequestDispatcher("/StateHospitalDataCreate.jsp").forward(req, resp);
	  }
	
	@Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);

	    // Retrieve and validate name.
	    String statename = req.getParameter("statename");
	    if (statename == null || statename.trim().isEmpty()) {
	      messages.put("success", "Please enter a valid State name.");
	    } else {
	      // Create the StateHospital.
	      String year = req.getParameter("year");
	      String numberOfHospitals = req.getParameter("numOfHospitals");
	      String numberOfEmployees = req.getParameter("numOfEmployees");
	      try {
	        State state = stateDao.getStateByName(statename);
	        if (state == null) {
	          messages.put("success", "State does not exist. Could not locate State Governor List.");
	        }
	        else {
	        	StateHospitalData stateHospital = new StateHospitalData(state, Short.valueOf(year), Long.valueOf(numberOfHospitals), Long.valueOf(numberOfEmployees));
	        	stateHospital = stateHospitalDao.create(stateHospital);
	        	messages.put("success", "Successfully created State Hospital entry for " + statename);
	        }
	      } catch (SQLException e) {
	        e.printStackTrace();
	        throw new IOException(e);
	      }
	    }

	    req.getRequestDispatcher("/StateHospitalDataCreate.jsp").forward(req, resp);
	  }
}
