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


/**
 * FindCovidByDate is the primary entry point into the application.
 * <p>
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findcountys URL
 * in the browser. doPost() handles the http POST request. This method is called after you click the
 * submit button.
 * <p>
 * To run: 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H. 2. Insert
 * test data. You can do this by running blog.tools.Inserter (right click, Run As > JavaApplication.
 * Notice that this is similar to Runner.java in our JDBC example. 3. Run the Tomcat server at
 * localhost. 4. Point your browser to http://localhost:8080/BlogApplication/findcountys.
 */

@WebServlet("/findcovidbydate")
public class FindCovidByDate extends HttpServlet {
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
    // Retrieve and validate name.
    String countyname = req.getParameter("countyname");
    String statename = req.getParameter("statename");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String stringDate = req.getParameter("date");
    if (countyname == null || countyname.trim().isEmpty() || statename == null || statename.trim().isEmpty() || stringDate == null) {
      messages.put("success", "Please enter a valid County and State name pair and valid Date.");
    } else {
      try {
    	Date date;
    	    try {
    	      date = dateFormat.parse(stringDate);
    	    } catch (ParseException e) {
    	      e.printStackTrace();
    	      throw new IOException(e);
    	    }
        county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        covidbydate = covidByDateDao.getCovidByDateByCountyAndDate(county, date);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + countyname + ", " + statename);
      // Save the previous search term, so it can be used as the default
      // in the input box when rendering FindCovidByDate.jsp.
      messages.put("previousCountyName", countyname);
      messages.put("previousStateName", statename);
      messages.put("previousDate", stringDate);
    }
    req.setAttribute("covidbydate", covidbydate);

    req.getRequestDispatcher("/FindCovidByDate.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    County county = null;
    CovidByDate covidbydate = null;
    // Retrieve and validate name.
    // countyname and statename is retrieved from the form POST submission. By default, it
    // is populated by the URL query string (in FindCovidByDate.jsp).
    String countyname = req.getParameter("countyname");
    String statename = req.getParameter("statename");
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String stringDate = req.getParameter("date");
    Date date;
    java.sql.Date sqlDate;
    try {
      date = dateFormat.parse(stringDate);
      sqlDate = new java.sql.Date(date.getTime());
    } catch (ParseException e) {
      e.printStackTrace();
      throw new IOException(e);
    }
    if (countyname == null || countyname.trim().isEmpty() || statename == null || statename.trim().isEmpty() || date == null) {
      messages.put("success", "Please enter a valid County and State name pair and valid Date.");
    } else {
      try {
        county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        covidbydate = covidByDateDao.getCovidByDateByCountyAndDate(county, sqlDate);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + countyname + ", " + statename);
    }
    req.setAttribute("covidbydate", covidbydate);

    req.getRequestDispatcher("/FindCovidByDate.jsp").forward(req, resp);
  }
}

