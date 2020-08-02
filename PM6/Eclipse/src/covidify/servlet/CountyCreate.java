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


@WebServlet("/countycreate")
public class CountyCreate extends HttpServlet {

  protected CountyDao countyDao;
  protected StateDao stateDao;

  @Override
  public void init() throws ServletException {
    countyDao = CountyDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    //Just render the JSP.
    req.getRequestDispatcher("/CountyCreate.jsp").forward(req, resp);
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
      // Create the County.
      String countyFIPS = req.getParameter("countyFIPS");
      String longitude = req.getParameter("longitude");
      String latitude = req.getParameter("latitude");
      try {
        State state = stateDao.getStateByName(statename);
        County county = new County(state, countyFIPS, countyname, Double.valueOf(longitude), Double.valueOf(latitude));
        county = countyDao.create(county);
        messages.put("success", "Successfully created " + countyname);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/CountyCreate.jsp").forward(req, resp);
  }
}
