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
 * FindStateHospital is the primary entry point into the application.
 * <p>
 * Note the logic for doGet() and doPost() are almost identical. However, there
 * is a difference: doGet() handles the http GET request. This method is called
 * when you put in the /findcountys URL in the browser. doPost() handles the
 * http POST request. This method is called after you click the submit button.
 * <p>
 * To run: 1. Run the SQL script to recreate your database schema:
 * http://goo.gl/86a11H. 2. Insert test data. You can do this by running
 * blog.tools.Inserter (right click, Run As > JavaApplication. Notice that this
 * is similar to Runner.java in our JDBC example. 3. Run the Tomcat server at
 * localhost. 4. Point your browser to
 * http://localhost:8080/BlogApplication/findcountys.
 */

@WebServlet("/findstatehospitaldata")
public class FindStateHospital extends HttpServlet
{
	protected StateHospitalDataDao stateHospitalDao;
	protected StateDao stateDao;

	@Override
	public void init() throws ServletException
	{
		stateHospitalDao = StateHospitalDataDao.getInstance();
		stateDao = StateDao.getInstance();

	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		List<StateHospitalData> stateHospital = null;
		// Retrieve and validate name.
		String shortYear = req.getParameter("year");
		String stateStr = req.getParameter("state");
		State state;

		if (shortYear == null || stateStr == null)
		{
			messages.put("success", "Please enter a valid Year (yyyy) and State Name.");
		}
		else
		{
			try
			{
				state = stateDao.getStateByName(stateStr);
				stateHospital = stateHospitalDao.getStateHospitalDataByYearAndState(
						Short.valueOf(shortYear), state);

			}
			catch (SQLException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + shortYear);
			// Save the previous search term, so it can be used as the default
			// in the input box when rendering FindStateHospital.jsp.
			messages.put("previousDate", shortYear);
		}
		req.setAttribute("stateHospital", stateHospital);

		req.getRequestDispatcher("/FindStateHospital.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		List<StateHospitalData> stateHospital = null;
		// Retrieve and validate name.
		// countyname and statename is retrieved from the form POST submission. By
		// default, it
		// is populated by the URL query string (in FindStateHospital.jsp).
		// Retrieve and validate name.
		String shortYear = req.getParameter("year");
		String stateStr = req.getParameter("state");
		System.out.println(shortYear);
		State state;
		
		if (shortYear == null || stateStr == null)
		{
			messages.put("success", "Please enter a valid Year (yyyy) and State Name.");
		}
		else
		{
			try
			{
				state = stateDao.getStateByName(stateStr);
				System.out.println(state);
				stateHospital = stateHospitalDao.getStateHospitalDataByYearAndState(
						Short.valueOf(shortYear), state);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success", "Displaying results for " + shortYear);
		}
		req.setAttribute("stateHospital", stateHospital);
//		System.out.println(stateHospital);

		req.getRequestDispatcher("/FindStateHospital.jsp").forward(req, resp);
	}
}
