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
/*
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

    if (statename == null || statename.trim().isEmpty() || statename == null || statename.trim().isEmpty() || stringDate == null) {
      messages.put("success", "Please enter a valid State and State name pair and valid Date.");
    } else {
      try {
        state = stateDao.getStateByStateNameAndStateName(statename, statename);
        if (state == null) {
          messages.put("success", "State and State name pair does not exist. Could not locate StateGovernor entry.");
        } else {
          stategovernor = stateGovernorDao.get(state, date);
          if (stategovernor == null) {
            messages.put("success", "StateGovernor entry does not exist.");
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
    String statename = req.getParameter("statename");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String stringDate = req.getParameter("date");
    Date date;
    try {
      date = dateFormat.parse(stringDate);
    } catch (ParseException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
    if (statename == null || statename.trim().isEmpty() || statename == null || statename.trim().isEmpty() || date == null) {
      messages.put("success", "Please enter a valid State and State name pair and valid Date.");
    } else {
      try {
        state = stateDao.getStateByStateNameAndStateName(statename, statename);
        if (state == null) {
          messages.put("success", "State and State name pair does not exist. Could not locate StateGovernor entry.");
        } else {
          stategovernor = stateGovernorDao.getStateGovernorByStateAndDate(state, date);
          if (stategovernor == null) {
            messages.put("success", "StateGovernor entry does not exist.");
          }
        }
        if (state != null &&  stategovernor != null) {
          String newCovidDeaths = req.getParameter("coviddeaths");
          String newCovidCases = req.getParameter("covidcases");

          if (newCovidDeaths != null && !newCovidDeaths.trim().isEmpty()) {
            stategovernor = stateGovernorDao.updateCovidDeaths(stategovernor, Integer.valueOf(newCovidDeaths));
            messages.put("success", "Successfully updated COVID-19 data entry for " + date.toString() + "in State: " + statename + ", " + statename);
          }
          if (newCovidCases != null && !newCovidCases.trim().isEmpty()) {
            stategovernor = stateGovernorDao.updateCovidDeaths(stategovernor, Integer.valueOf(newCovidCases));
            messages.put("success", "Successfully updated COVID-19 data entry for " + date.toString() + "in State: " + statename + ", " + statename);
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
*/
}


