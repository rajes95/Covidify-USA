package covidify.servlet;

import covidify.dal.*;
import covidify.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * FindState is the primary entry point into the application.
 * <p>
 * Note the logic for doGet() and doPost() are almost identical. However, there is a difference:
 * doGet() handles the http GET request. This method is called when you put in the /findstates URL
 * in the browser. doPost() handles the http POST request. This method is called after you click the
 * submit button.
 * <p>
 * To run: 1. Run the SQL script to recreate your database schema: http://goo.gl/86a11H. 2. Insert
 * test data. You can do this by running blog.tools.Inserter (right click, Run As > JavaApplication.
 * Notice that this is similar to Runner.java in our JDBC example. 3. Run the Tomcat server at
 * localhost. 4. Point your browser to http://localhost:8080/BlogApplication/findstates.
 */

@WebServlet("/findstate")
public class FindState extends HttpServlet {

  protected StateDao stateDao;

  @Override
  public void init() throws ServletException {
    stateDao = StateDao.getInstance();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);
    State state = null;

    // Retrieve and validate name.
    String statename = req.getParameter("statename");
    if (statename == null || statename.trim().isEmpty()) {
      messages.put("success", "Please enter a valid State name.");
    } else {
      try {
        state = stateDao.getStateByName(statename);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + statename + ", " + statename);
      // Save the previous search term, so it can be used as the default
      // in the input box when rendering FindState.jsp.
      messages.put("previousStateName", statename);
    }
    req.setAttribute("state", state);

    req.getRequestDispatcher("/FindState.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    State state = null;

    // Retrieve and validate name.
    // statename and statename is retrieved from the form POST submission. By default, it
    // is populated by the URL query string (in FindState.jsp).
    String statename = req.getParameter("statename");
    if (statename == null || statename.trim().isEmpty()) {
      messages.put("success", "Please enter a valid State name.");
    } else {
      try {
        state = stateDao.getStateByName(statename);
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
      messages.put("success", "Displaying results for " + statename);
    }
    req.setAttribute("state", state);

    req.getRequestDispatcher("/FindState.jsp").forward(req, resp);
  }
}

