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


@WebServlet("/covidbydatedelete")
public class CovidByDateDelete extends HttpServlet {

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
    // Provide a title and render the JSP.
    messages.put("title", "Delete a COVID-19 Data Entry");
    req.getRequestDispatcher("/CovidByDateDelete.jsp").forward(req, resp);
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
    if (countyname == null || countyname.trim().isEmpty()
            || statename == null || statename.trim().isEmpty()
            || date == null) {
      messages.put("success", "Please enter a valid County and State name pair and valid Date.");
    }  else {
      // Delete the CovidByDate.
      try {
        //TODO not sure if this way of deletion is allowed, but i think it works and is fine?
        County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        CovidByDate covidByDate = covidByDateDao.getCovidByDateByCountyAndDate(county, sqlDate);
        covidByDate = covidByDateDao.delete(covidByDate);
        // Update the message.
        if (covidByDate == null) {
          messages.put("title", "Successfully deleted COVID-19 data entry for " + date.toString() + "in County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete COVID-19 data entry for " + date.toString() + "in County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/CovidByDateDelete.jsp").forward(req, resp);
  }
}
