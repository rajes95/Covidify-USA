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
 * FindStateGovernor is the primary entry point into the application.
 * <p>
 * Note the logic for doGet() and doPost() are almost identical. However, there
 * is a difference: doGet() handles the http GET request. This method is called
 * when you put in the /findstates URL in the browser. doPost() handles the http
 * POST request. This method is called after you click the submit button.
 * <p>
 * To run: 1. Run the SQL script to recreate your database schema:
 * http://goo.gl/86a11H. 2. Insert test data. You can do this by running
 * blog.tools.Inserter (right click, Run As > JavaApplication. Notice that this
 * is similar to Runner.java in our JDBC example. 3. Run the Tomcat server at
 * localhost. 4. Point your browser to
 * http://localhost:8080/BlogApplication/findstates.
 */

@WebServlet("/findstategovernor")
public class FindStateGovernor extends HttpServlet
{
	protected StateGovernorDao stateGovernorDao;
	protected StateDao stateDao;

	@Override
	public void init() throws ServletException
	{
		stateGovernorDao = StateGovernorDao.getInstance();
		stateDao = StateDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		State state = null;
		List<StateGovernor> stategovernors = null;
		// Retrieve and validate name.
		String year = req.getParameter("year");
		String statename = req.getParameter("statename");
		if (statename == null || statename.trim().isEmpty() || year == null
				|| year.trim().isEmpty())
		{
			messages.put("success", "Please enter a valid State name and Year.");
		}
		else
		{
			try
			{
				state = stateDao.getStateByName(statename);
				stategovernors = stateGovernorDao.getStateGovernorsByStateKeyAndYear(
						state.getStateKey(), Short.valueOf(year));
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + statename + ", " + year);
			// Save the previous search term, so it can be used as the default
			// in the input box when rendering FindStateGovernor.jsp.
			messages.put("previousStateName", statename);
			messages.put("previousYear", statename);
		}
		req.setAttribute("stategovernors", stategovernors);

		req.getRequestDispatcher("/FindStateGovernor.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		State state = null;
		List<StateGovernor> stategovernors = null;
		// Retrieve and validate name.
		// statename and statename is retrieved from the form POST submission. By
		// default, it
		// is populated by the URL query string (in FindStateGovernor.jsp).

		String year = req.getParameter("year");
		String statename = req.getParameter("statename");
		if (statename == null || statename.trim().isEmpty() || year == null
				|| year.trim().isEmpty())
		{
			messages.put("success", "Please enter a valid State name and Year.");
		}
		else
		{
			try
			{
				state = stateDao.getStateByName(statename);
				if (state == null)
				{
					messages.put("success",
							"State does not exist. Could not locate State Governor List.");
				}
				else
				{
					stategovernors = stateGovernorDao.getStateGovernorsByStateKeyAndYear(
							state.getStateKey(), Short.valueOf(year));
					if (stategovernors == null)
					{
						messages.put("success", "State Governor List does not exist.");
					}
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + statename + ", " + year);
		}
		req.setAttribute("stategovernors", stategovernors);

		req.getRequestDispatcher("/FindStateGovernor.jsp").forward(req, resp);
	}
}
