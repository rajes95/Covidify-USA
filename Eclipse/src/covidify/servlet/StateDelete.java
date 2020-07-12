package covidify.servlet;

import covidify.dal.*;
import covidify.model.*;

import java.io.IOException;
import java.sql.SQLException;
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


@WebServlet("/statedelete")
public class StateDelete extends HttpServlet {

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
    // Provide a title and render the JSP.
    messages.put("title", "Delete State");
    req.getRequestDispatcher("/StateDelete.jsp").forward(req, resp);
  }

  @Override
  public void doPost(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException {
    // Map for storing messages.
    Map<String, String> messages = new HashMap<String, String>();
    req.setAttribute("messages", messages);

    // Retrieve and validate name.
    String statename = req.getParameter("statename");
    if (statename == null || statename.trim().isEmpty() ) {
      messages.put("success", "Please enter a valid State name.");
    } else {
      // Delete the State.
      try {
        //TODO not sure if this way of deletion is allowed, but i think it would work ?
        State state = stateDao.getStateByName(statename);
        state = stateDao.delete(state);
        // Update the message.
        if (state == null) {
          messages.put("title", "Successfully deleted " + statename);
          messages.put("disableSubmit", "true");
        } else {
          messages.put("title", "Failed to delete " + statename);
          messages.put("disableSubmit", "false");
        }
      } catch (SQLException e) {
        e.printStackTrace();
        throw new IOException(e);
      }
    }

    req.getRequestDispatcher("/StateDelete.jsp").forward(req, resp);
  }
}
