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


@WebServlet("/countyupdate")
public class CountyUpdate extends HttpServlet {

  protected CountyDao countyDao;

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

    // Retrieve county and validate.
    String countyname = req.getParameter("countyname");
    String statename = req.getParameter("statename");
    if (countyname == null || countyname.trim().isEmpty() || statename == null || statename.trim().isEmpty()) {
      messages.put("success", "Please enter a valid County and State name pair.");
    } else {
      try {
        County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        if (county == null) {
          messages.put("success", "County and State name pair does not exist.");
        }
        req.setAttribute("county", county);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.getRequestDispatcher("/CountyUpdate.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve county and validate.
    String countyname = req.getParameter("countyname");
    String statename = req.getParameter("statename");
    if (countyname == null || countyname.trim().isEmpty() || statename == null || statename.trim().isEmpty()) {
      messages.put("success", "Please enter a valid County and State name pair.");
    } else {
      try {
        County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        if (county == null) {
          messages.put("success", "County and State name pair does not exist. No update to perform.");
        } else {
          String newCountyFIPS = req.getParameter("countyFIPS");
          String newLongitude = req.getParameter("longitude");
          String newLatitude = req.getParameter("latitude");
          if (newCountyFIPS != null && !newCountyFIPS.trim().isEmpty()) {
            county = countyDao.updateCountyFIPS(county, newCountyFIPS);
            messages.put("success", "Successfully updated " + countyname);
          }
          if (newLongitude != null && !newLongitude.trim().isEmpty()) {
            county = countyDao.updateLongitude(county, Double.valueOf(newLongitude));
            messages.put("success", "Successfully updated " + countyname);
          }
          if (newLatitude != null && !newLatitude.trim().isEmpty()) {
            county = countyDao.updateLatitude(county, Double.valueOf(newLatitude));
            messages.put("success", "Successfully updated " + countyname);
          }
        }
        req.setAttribute("county", county);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/CountyUpdate.jsp").forward(req, resp);
  }
}
