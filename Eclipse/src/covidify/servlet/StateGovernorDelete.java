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


@WebServlet("/stategovernordelete")
public class StateGovernorDelete extends HttpServlet {

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
    // Provide a title and render the JSP.
    messages.put("title", "Delete a List of State Governors");
    req.getRequestDispatcher("/StateGovernorDelete.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
    String year = req.getParameter("year");
    String statename = req.getParameter("statename");
    if (statename == null || statename.trim().isEmpty()
            || year == null || year.trim().isEmpty()) {
      messages.put("success", "Please enter a valid State name and Year.");
    }  else {
      // Delete the StateGovernor.
      try {
        //TODO not sure if this way of deletion is allowed, but i think it works and is fine?
        State state = stateDao.getStateByName(statename);
        List<StateGovernor> stateGovernors = stateGovernorDao.getStateGovernorsByStateKeyAndYear(state.getStateKey(), Short.valueOf(year));
        int listSize = stateGovernors.size();
        int counter = 0;
        for (StateGovernor stateGovernor : stateGovernors) {
          stateGovernor = stateGovernorDao.delete(stateGovernor);
          if (stateGovernor == null) {
            counter++;
          }
        }
        // Update the message.
        if (counter == listSize) {
          messages.put("title", "Successfully deleted all State Governor entries for " + year + " in " + statename);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete all State Governor entries for " + year + " in " + statename);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/StateGovernorDelete.jsp").forward(req, resp);
  }
}
