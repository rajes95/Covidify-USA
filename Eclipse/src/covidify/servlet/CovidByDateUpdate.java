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


@WebServlet("/covidbydateupdate")
public class CovidByDateUpdate extends HttpServlet {
  protected CovidByDateDao covidByDateDao;
  protected CountyDao countyDao;

  @Override
  public void init() throws ServletException {
    covidByDateDao = CovidByDateDao.getInstance();
    countyDao = CountyDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    County county = null;
    CovidByDate covidbydate = null;
    // Retrieve county and validate.
    String countyname = req.getParameter("countyname");
    String statename = req.getParameter("statename");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String stringDate = req.getParameter("date");
    if (countyname == null || countyname.trim().isEmpty() || statename == null || statename.trim().isEmpty() || stringDate == null) {
      messages.put("success", "Please enter a valid County and State name pair and valid Date.");
    } else {
    	Date date;
        java.sql.Date sqlDate;
        try {
          date = dateFormat.parse(stringDate);
          sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
          e.printStackTrace();
          throw new IOException(e);
        }
      try {
        county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        if (county == null) {
          messages.put("success", "County and State name pair does not exist. Could not locate COVID-19 Data entry.");
        } else {
          covidbydate = covidByDateDao.getCovidByDateByCountyAndDate(county, sqlDate);
          if (covidbydate == null) {
            messages.put("success", "CovidByDate entry does not exist.");
          }
        }
        req.setAttribute("covidbydate", covidbydate);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }
    req.getRequestDispatcher("/CovidByDateUpdate.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    County county = null;
    CovidByDate covidbydate = null;
    // Retrieve county and validate.
    String countyname = req.getParameter("countyname");
    String statename = req.getParameter("statename");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String stringDate = req.getParameter("date");
    if (countyname == null || countyname.trim().isEmpty() || statename == null || statename.trim().isEmpty() || stringDate == null) {
      messages.put("success", "Please enter a valid County and State name pair and valid Date.");
    } else {
    	Date date;
        java.sql.Date sqlDate;
        try {
          date = dateFormat.parse(stringDate);
          sqlDate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
          e.printStackTrace();
          throw new IOException(e);
        }
      try {
        county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        if (county == null) {
          messages.put("success", "County and State name pair does not exist. Could not locate COVID-19 Data entry.");
        } else {
          covidbydate = covidByDateDao.getCovidByDateByCountyAndDate(county, sqlDate);
          if (covidbydate == null) {
            messages.put("success", "CovidByDate entry does not exist.");
          }
        }
        if (county != null &&  covidbydate != null) {
          String newCovidDeaths = req.getParameter("coviddeaths");
          String newCovidCases = req.getParameter("covidcases");

          if (newCovidDeaths != null && !newCovidDeaths.trim().isEmpty()) {
            covidbydate = covidByDateDao.updateCovidDeaths(covidbydate, Integer.valueOf(newCovidDeaths));
            messages.put("success", "Successfully updated COVID-19 data entry for " + date.toString() + " in County: " + countyname + ", " + statename);
          }
          if (newCovidCases != null && !newCovidCases.trim().isEmpty()) {
            covidbydate = covidByDateDao.updateCovidDeaths(covidbydate, Integer.valueOf(newCovidCases));
            messages.put("success", "Successfully updated COVID-19 data entry for " + date.toString() + " in County: " + countyname + ", " + statename);
          }
        }
        req.setAttribute("covidbydate", covidbydate);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/CovidByDateUpdate.jsp").forward(req, resp);
  }
}
