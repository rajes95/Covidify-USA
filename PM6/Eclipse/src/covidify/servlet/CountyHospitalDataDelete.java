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

import covidify.dal.CountyDao;
import covidify.dal.CountyHospitalDao;
import covidify.model.County;
import covidify.model.CountyHospitalData;
import covidify.model.StateGovernor;


@WebServlet("/countyhospitaldelete")
public class CountyHospitalDataDelete extends HttpServlet {

  protected CountyHospitalDao countyHospitalDataDao;
  protected CountyDao countyDao;

  @Override
  public void init() throws ServletException {
    countyHospitalDataDao = CountyHospitalDao.getInstance();
    countyDao = CountyDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("title", "Delete County Hospital Data Entry");
    req.getRequestDispatcher("/CountyHospitalDelete.jsp").forward(req, resp);
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
      // Delete the CountyHospitalData.
      try {
        County county = countyDao.getCountyByCountyNameAndStateName(countyname, statename);
        List<CountyHospitalData> countyHospitalDataList = countyHospitalDataDao.getCountyHospitalDataByCounty(county);
        int listSize = countyHospitalDataList.size();
        int counter = 0;
        for (CountyHospitalData countyHospitalData : countyHospitalDataList) {
          countyHospitalData = countyHospitalDataDao.delete(countyHospitalData);
          if (countyHospitalData == null) {
            counter++;
          }
        }
        // Update the message.
        if (counter == listSize) {
          messages.put("title", "Successfully deleted County Hospital data entry for County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete County Hospital data entry for County: " + countyname + ", " + statename);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/CountyHospitalDelete.jsp").forward(req, resp);
  }
}
