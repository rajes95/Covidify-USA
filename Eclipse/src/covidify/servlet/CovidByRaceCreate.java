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


@WebServlet("/covidbyracecreate")
public class CovidByRaceCreate extends HttpServlet {

	protected CovidByRaceDao covidByRaceDao;
	protected StateDao stateDao;

	@Override
	public void init() throws ServletException {
	   covidByRaceDao = CovidByRaceDao.getInstance();
	   stateDao = StateDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	          throws ServletException, IOException {
	    // Map for storing messages.
	    Map<String, String> messages = new HashMap<String, String>();
	    req.setAttribute("messages", messages);
	    //Just render the JSP.
	    req.getRequestDispatcher("/CovidByRaceCreate.jsp").forward(req, resp);
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
	      messages.put("success", "Please enter a State name.");
	    } else {
	      // Create the CovidByRace.
	      String raceType = req.getParameter("racetype");
	      if (raceType == null || raceType.trim().isEmpty()) {
		    	messages.put("success", "Please enter a Race name.");
		  } else {
			  String positive = req.getParameter("positive");
			  String negative = req.getParameter("negative");
			  String death = req.getParameter("death");
			  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			  String stringDate = req.getParameter("date");
			  Date date;
			  java.sql.Date sqlDate;
			  CovidByRace.RaceType race = null;
			  try {
				  date = dateFormat.parse(stringDate);
				  sqlDate = new java.sql.Date(date.getTime());
			  } catch (ParseException e) {
				  e.printStackTrace();
				  throw new IOException(e);
			  }
			  try{
			        race = CovidByRace.RaceType.valueOf(raceType);
			      } catch (IllegalArgumentException e){
			        e.printStackTrace();
			        throw new IOException(e);
			      }
			  try {
				  State state = stateDao.getStateByName(statename);
				  if (state == null) {
			          messages.put("success", "State does not exist.");
				  }
				  CovidByRace covidByRace = new CovidByRace(state, race, Integer.valueOf(positive), Integer.valueOf(negative), Integer.valueOf(death), sqlDate);
				  covidByRace = covidByRaceDao.create(covidByRace);
				  messages.put("success", "Successfully created COVID-19 race entry for " +  statename);
			  } catch (SQLException e) {
				  e.printStackTrace();
				  throw new IOException(e);
			  }
		  }
	    }

	    req.getRequestDispatcher("/CovidByRaceCreate.jsp").forward(req, resp);
	  }
}
