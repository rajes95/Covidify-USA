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
 * FindPresidentElectionPercent is the primary entry point into the application.
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

@WebServlet("/findpresidentelectionpercent")
public class FindPresidentElectionPercent extends HttpServlet {
  protected PresidentialElectionVotePercentagesDao presidentElectionPercentDao;


  @Override
  public void init() throws ServletException {
    presidentElectionPercentDao = PresidentialElectionVotePercentagesDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    List<PresidentialElectionVotePercentages> presidentElectionPercent = null;
    // Retrieve and validate name.
    String shortYear = req.getParameter("year");
    if (shortYear == null) {
      messages.put("success", "Please enter a valid year (yyyy) Date.");
    } else {
      try {
    	Short shortInput = new shortYear;
        presidentElectionPercent = presidentElectionPercentDao.getPresidentialElectionVotePercentagesByYear(shortInput);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + shortYear);
      // Save the previous search term, so it can be used as the default
      // in the input box when rendering FindPresidentElectionPercent.jsp.
      messages.put("previousDate", shortYear);
    }
    req.setAttribute("presidentElectionPercent", presidentElectionPercent);

    req.getRequestDispatcher("/FindPresidentElectionPercent.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    List<PresidentialElectionVotePercentages> presidentElectionPercent = null;
    // Retrieve and validate name.
    // countyname and statename is retrieved from the form POST submission. By default, it
    // is populated by the URL query string (in FindPresidentElectionPercent.jsp).
    // Retrieve and validate name.
    String shortYear = req.getParameter("year");

    if (shortYear == null) {
      messages.put("success", "Please enter a valid Year (yyyy).");
    } else {
      try {
    	  Short shortInput = new shortYear;
    	  presidentElectionPercent = presidentElectionPercentDao.getPresidentialElectionVotePercentagesByYear(shortInput);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + shortYear);
    }
    req.setAttribute("presidentElectionPercent", presidentElectionPercent);

    req.getRequestDispatcher("/FindPresidentElectionPercent.jsp").forward(req, resp);
  }
}

