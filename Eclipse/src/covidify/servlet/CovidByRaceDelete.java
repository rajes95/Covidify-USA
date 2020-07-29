package covidify.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import covidify.dal.CovidByRaceDao;
import covidify.dal.StateDao;
import covidify.model.CovidByRace;
import covidify.model.State;
import covidify.model.StateGovernor;


@WebServlet("/covidbyracedelete")
public class CovidByRaceDelete extends HttpServlet {
	

  protected CovidByRaceDao covidByRaceDao;
  protected StateDao stateDao;

  @Override
  public void init() throws ServletException {
    covidByRaceDao = CovidByRaceDao.getInstance();
    stateDao = StateDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("title", "Delete a COVID-19 Race Data Entry");
    req.getRequestDispatcher("/CovidByRaceDelete.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
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
    if (statename == null || statename.trim().isEmpty()
            || statename == null || statename.trim().isEmpty()
            || date == null) {
      messages.put("success", "Please enter a valid Date and State name.");
    }  else {
      try {
        State state = stateDao.getStateByName(statename);
        List<CovidByRace> covidByRaceList = covidByRaceDao.getCovidByRaceByStateAndDate(state, sqlDate);
        int listSize = covidByRaceList.size();
        int counter = 0;
        for (CovidByRace covidByRace : covidByRaceList) {
          covidByRace = covidByRaceDao.delete(covidByRace);
          if (covidByRace == null) {
            counter++;
          }
        }
        if (counter == listSize) {
          messages.put("title", "Successfully deleted all COVID-19 Race data entries for " + date.toString() + " in State: " + statename);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete all COVID-19 Race data entries for " + date.toString() + " in State: " + statename );
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/CovidByRaceDelete.jsp").forward(req, resp);
  }
}
