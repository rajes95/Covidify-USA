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


@WebServlet("/covidbydatecreate")
public class CovidByDateCreate extends HttpServlet {

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
    //Just render the JSP.
    req.getRequestDispatcher("/CovidByDateCreate.jsp").forward(req, resp);
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
      // Create the CovidByDate.
      String covidDeaths = req.getParameter("coviddeaths");
      String covidCases = req.getParameter("covidcases");
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
      try {
        County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        CovidByDate covidByDate = new CovidByDate(county, sqlDate, Integer.valueOf(covidDeaths), Integer.valueOf(covidCases));
        covidByDate = covidByDateDao.create(covidByDate);
        messages.put("success", "Successfully created COVID-19 data entry for " + date.toString() + "in County: " + countyname + ", " + statename);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/CovidByDateCreate.jsp").forward(req, resp);
  }
}
