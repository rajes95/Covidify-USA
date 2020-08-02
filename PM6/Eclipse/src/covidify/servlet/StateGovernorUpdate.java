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


@WebServlet("/stategovernorupdate")
public class StateGovernorUpdate extends HttpServlet {
  protected StateGovernorDao stateGovernorDao;
  protected StateDao stateDao;

  @Override
  public void init() throws ServletException {
    stateGovernorDao = StateGovernorDao.getInstance();
    stateDao = StateDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    State state = null;
    StateGovernor stategovernor = null;
    // Retrieve state and validate.

    String statename = req.getParameter("statename");
    String year = req.getParameter("year");
    String governorname = req.getParameter("governorname");
    
    if (statename == null || statename.trim().isEmpty() || year == null || year.trim().isEmpty() || governorname == null || governorname.trim().isEmpty() ) {
      messages.put("success", "Please enter a valid State name, Year, and Governor name.");
    } else {
      try {
        state = stateDao.getStateByName(statename);
        if (state == null) {
          messages.put("success", "State does not exist. Could not locate State Governor entry.");
        } else {
          stategovernor = stateGovernorDao.getStateGovernorsByStateKeyYearGovernorName(state.getStateKey(), Short.valueOf(year), governorname);
          if (stategovernor == null) {
            messages.put("success", "State Governor entry does not exist.");
          }
        }
        req.setAttribute("stategovernor", stategovernor);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.getRequestDispatcher("/StateGovernorUpdate.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    State state = null;
    StateGovernor stategovernor = null;
    // Retrieve state and validate.
    String statename = req.getParameter("statename");
    String year = req.getParameter("year");
    String governorname = req.getParameter("governorname");
    
    if (statename == null || statename.trim().isEmpty() || year == null || year.trim().isEmpty() || governorname == null || governorname.trim().isEmpty() ) {
      messages.put("success", "Please enter a valid State name, Year, and Governor name.");
    } else {
      try {
        state = stateDao.getStateByName(statename);
        if (state == null) {
          messages.put("success", "State does not exist. Could not locate State Governor entry.");
        } else {
          stategovernor = stateGovernorDao.getStateGovernorsByStateKeyYearGovernorName(state.getStateKey(), Short.valueOf(year), governorname);
          if (stategovernor == null) {
            messages.put("success", "State Governor entry does not exist.");
          }
        }
        if (state != null &&  stategovernor != null) {
        	String newName = req.getParameter("newname");
        	String newYear = req.getParameter("newyear");
        	String newParty = req.getParameter("newparty");
          if (newName != null && !newName.trim().isEmpty()) {
            stategovernor = stateGovernorDao.updateGovernorName(stategovernor, newName);
            messages.put("success", "Successfully updated State Governor data entry, "+ governorname + ", for " + year + " in State: " + statename);
          }
          if (newYear != null && !newYear.trim().isEmpty()) {
            stategovernor = stateGovernorDao.updateGovernorYear(stategovernor, Short.valueOf(newYear));
            messages.put("success", "Successfully updated State Governor data entry, "+ governorname + ", for " + year + " in State: " + statename);
          }
          if (newParty != null && !newParty.trim().isEmpty()) {
        	  StateGovernor.GovernorPartyType newPartyEnum = null;
              try{
            	  newPartyEnum = StateGovernor.GovernorPartyType.valueOf(newParty);
              } catch (IllegalArgumentException e){
                e.printStackTrace();
                throw new IOException(e);
              }
              stategovernor = stateGovernorDao.updateGovernorParty(stategovernor, newPartyEnum);
              messages.put("success", "Successfully updated State Governor data entry, "+ governorname + ", for " + year + " in State: " + statename);
            }
        }
        req.setAttribute("stategovernor", stategovernor);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/StateGovernorUpdate.jsp").forward(req, resp);
  }
}


