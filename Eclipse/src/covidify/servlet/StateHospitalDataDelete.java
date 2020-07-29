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

import covidify.dal.StateDao;
import covidify.dal.StateHospitalDataDao;
import covidify.dal.StateHospitalDataDao;
import covidify.model.State;
import covidify.model.StateHospitalData;


@WebServlet("/statehospitaldatadelete")
public class StateHospitalDataDelete extends HttpServlet {

  protected StateHospitalDataDao stateHospitalDataDao;
  protected StateDao stateDao;

  @Override
  public void init() throws ServletException {
    stateHospitalDataDao = StateHospitalDataDao.getInstance();
    stateDao = StateDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    // Provide a title and render the JSP.
    messages.put("title", "Delete a State Hospital Data Entry");
    req.getRequestDispatcher("/StateHospitalDataDelete.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
    String statename = req.getParameter("statename");
    String yearString = req.getParameter("year");
    Short year = Short.valueOf(yearString);

    if (statename == null || statename.trim().isEmpty()
            || yearString == null || yearString.trim().isEmpty()
            || year ==null ) {
      messages.put("success", "Please enter a valid State and Year pair.");
    }  else {
      // Delete the StateHospitalData.
      try {
        State state = stateDao.getStateByName(statename);
        List<StateHospitalData> stateHospitalDataList = stateHospitalDataDao.getStateHospitalDataByYearAndState(year, state);
        int listSize = stateHospitalDataList.size();
        int counter = 0;
        for (StateHospitalData stateHospitalData : stateHospitalDataList) {
          stateHospitalData = stateHospitalDataDao.delete(stateHospitalData);
          if (stateHospitalData == null) {
            counter++;
          }
        }
        // Update the message.
        if (counter == listSize) {
          messages.put("title", "Successfully deleted State Hospital data entry for "+ yearString+ " in " + statename );
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete  State Hospital data entry for "+ yearString+ " in " + statename );
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/StateHospitalDataDelete.jsp").forward(req, resp);
  }
}
