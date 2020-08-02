package covidify.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import covidify.dal.PresidentialElectionVotePercentagesDao;
import covidify.dal.CountyDao;
import covidify.dal.PresidentialElectionVotePercentagesDao;
import covidify.model.PresidentialElectionVotePercentages;
import covidify.model.County;


@WebServlet("/presidentialelectionpercentdelete")
public class PresidentialElectionPercentDelete extends HttpServlet {

  protected PresidentialElectionVotePercentagesDao presidentialElectionVotePercentagesDao;
  protected CountyDao countyDao;

  @Override
  public void init() throws ServletException {
    presidentialElectionVotePercentagesDao = PresidentialElectionVotePercentagesDao.getInstance();
    countyDao = CountyDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("title", "Delete a Presidential Election Data Entry");
    req.getRequestDispatcher("/PresidentialElectionPercentDelete.jsp").forward(req, resp);
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
    String yearString = req.getParameter("year");
    Short year = Short.valueOf(yearString);

    if (countyname == null || countyname.trim().isEmpty()
            || statename == null || statename.trim().isEmpty()
            || yearString == null || yearString.trim().isEmpty()
            || year ==null ) {
      messages.put("success", "Please enter a valid County and State name pair and valid Year.");
    }  else {
      // Delete the PresidentialElectionVotePercentages.
      try {
        County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        List<PresidentialElectionVotePercentages> presidentialelectionpercentList = presidentialElectionVotePercentagesDao.getPresidentialElectionVotePercentagesByYearAndCounty(year, county);
        int listSize = presidentialelectionpercentList.size();
        int counter = 0;
        for (PresidentialElectionVotePercentages presidentialelectionpercent : presidentialelectionpercentList) {
          presidentialelectionpercent = presidentialElectionVotePercentagesDao.delete(presidentialelectionpercent);
          if (presidentialelectionpercent == null) {
            counter++;
          }
        }
        // Update the message.
        if (counter == listSize) {
          messages.put("title", "Successfully deleted Presidential Election data entry for "+ yearString+ " in County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete  Presidential Election data entry for "+ yearString+ " in County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/PresidentialElectionPercentDelete.jsp").forward(req, resp);
  }
}
