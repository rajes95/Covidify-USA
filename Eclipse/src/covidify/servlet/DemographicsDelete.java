package covidify.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import covidify.dal.DemographicsDao;
import covidify.dal.CountyDao;
import covidify.model.Demographics;
import covidify.model.County;


@WebServlet("/demographicdelete")
public class DemographicsDelete extends HttpServlet {

  protected DemographicsDao demographicDao;
  protected CountyDao countyDao;

  @Override
  public void init() throws ServletException {
    demographicDao = DemographicsDao.getInstance();
    countyDao = CountyDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("title", "Delete a Demographics Data Entry");
    req.getRequestDispatcher("/DemographicsDelete.jsp").forward(req, resp);
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
    if (countyname == null || countyname.trim().isEmpty()
            || statename == null || statename.trim().isEmpty()) {
      messages.put("success", "Please enter a valid County and State name pair.");
    }  else {
      // Delete the Demographics.
      try {
        County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        List<Demographics> demographicList = demographicDao.getDemographicsByCounty(county);
        int listSize = demographicList.size();
        int counter = 0;
        for (Demographics demographic : demographicList) {
          demographic = demographicDao.delete(demographic);
          if (demographic == null) {
            counter++;
          }
        }
        // Update the message.
        if (counter == listSize) {
          messages.put("title", "Successfully deleted Demographics data entry for County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete Demographics data entry for County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/DemographicsDelete.jsp").forward(req, resp);
  }
}
