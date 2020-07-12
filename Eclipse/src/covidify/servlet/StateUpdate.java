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


@WebServlet("/stateupdate")
public class StateUpdate extends HttpServlet {

  protected StateDao stateDao;

  @Override
  public void init() throws ServletException {
    stateDao = StateDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve state and validate.
    String statename = req.getParameter("statename");
    if (statename == null || statename.trim().isEmpty()) {
      messages.put("success", "Please enter a valid State name.");
    } else {
      try {
        State state = stateDao.getStateByName(statename);
        if (state == null) {
          messages.put("success", "State name does not exist.");
        }
        req.setAttribute("state", state);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.getRequestDispatcher("/StateUpdate.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve state and validate.
    String statename = req.getParameter("statename");
    if (statename == null || statename.trim().isEmpty() ) {
      messages.put("success", "Please enter a valid State name.");
    } else {
      try {
        State state = stateDao.getStateByName(statename);
        if (state == null) {
          messages.put("success", "State name does not exist. No update to perform.");
        } else {
          String newStateFIPS = req.getParameter("stateFIPS");
          String newPostalCode = req.getParameter("postalcode");
          if (newStateFIPS != null && !newStateFIPS.trim().isEmpty()) {
            state = stateDao.updateStateFIPS(state, newStateFIPS);
            messages.put("success", "Successfully updated " + statename);
          }
          if (newPostalCode != null && !newPostalCode.trim().isEmpty()) {
            state = stateDao.updatePostalCode(state, newPostalCode);
            messages.put("success", "Successfully updated " + statename);
          }
        }
        req.setAttribute("state", state);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/StateUpdate.jsp").forward(req, resp);
  }
}
