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


@WebServlet("/statecreate")
public class StateCreate extends HttpServlet {

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
    //Just render the JSP.
    req.getRequestDispatcher("/StateCreate.jsp").forward(req, resp);
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
      // Create the State.
      String stateFIPS = req.getParameter("stateFIPS");
      String postalCode = req.getParameter("postalcode");
      try {
        State state = new State(stateFIPS, postalCode, statename);
        state = stateDao.create(state);
        messages.put("success", "Successfully created " + statename);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/StateCreate.jsp").forward(req, resp);
  }
}
