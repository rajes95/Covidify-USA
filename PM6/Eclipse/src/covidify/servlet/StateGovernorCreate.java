package covidify.servlet;

import covidify.dal.*;
import covidify.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/stategovernorcreate")
public class StateGovernorCreate extends HttpServlet {

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
    //Just render the JSP.
    req.getRequestDispatcher("/StateGovernorCreate.jsp").forward(req, resp);
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
      // Create the StateGovernor.
      String year = req.getParameter("year");
      String governor = req.getParameter("governor");
      String governorParty = req.getParameter("governorParty");
      StateGovernor.GovernorPartyType party = null;
      try{
        party = StateGovernor.GovernorPartyType.valueOf(governorParty);
      } catch (IllegalArgumentException e){
        e.printStackTrace();
        throw new IOException(e);
      }
      try {
        State state = stateDao.getStateByName(statename);
        if (state == null) {
          messages.put("success", "State does not exist. Could not locate State Governor List.");
        } else {
          StateGovernor stateGovernor = new StateGovernor(state, Short.valueOf(year), governor, party);
          stateGovernor = stateGovernorDao.create(stateGovernor);
          if (stateGovernor == null) {
            messages.put("success", "Failed to create State Governor entry for " + year + " in " + statename);
          }
        }
        messages.put("success", "Successfully created State Governor entry for " + year + " in " + statename);
      }  catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/StateGovernorCreate.jsp").forward(req, resp);
  }
}
