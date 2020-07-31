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
 * FindCountyHospital is the primary entry point into the application.
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

@WebServlet("/findcountyhospital")
public class FindCountyHospital extends HttpServlet
{
	protected CountyHospitalDao countyHospitalDao;
	protected CountyDao countyDao;
	protected StateDao stateDao;

	@Override
	public void init() throws ServletException
	{
		countyHospitalDao = CountyHospitalDao.getInstance();
		countyDao = CountyDao.getInstance();
		stateDao = StateDao.getInstance();
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);
		County county = null;
		List<CountyHospitalData> countyHospitals = null;
		// Retrieve and validate name.
		String countyname = req.getParameter("countyname");
		String statename = req.getParameter("statename");
		if (statename == null || statename.trim().isEmpty() || countyname == null
				|| countyname.trim().isEmpty())
		{
			messages.put("success", "Please enter a valid State and County name.");
		}
		else
		{
			try
			{
				county = countyDao.getCountyByCountyNameAndStateName(countyname,
						statename);
				countyHospitals = countyHospitalDao.getCountyHospitalDataByCounty(county);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success",
					"Displaying results for " + statename + ", " + countyname);
			// Save the previous search term, so it can be used as the default
			// in the input box when rendering FindStateGovernor.jsp.
			messages.put("previousStateName", statename);
			messages.put("previousCountyName", countyname);
		}
		req.setAttribute("countyhospitals", countyHospitals);

		req.getRequestDispatcher("/FindCountyHospital.jsp").forward(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		// Map for storing messages.
		Map<String, String> messages = new HashMap<String, String>();
		req.setAttribute("messages", messages);

		County county = null;
		List<CountyHospitalData> countyHospitals = null;
		// Retrieve and validate name.
		// statename and statename is retrieved from the form POST submission. By
		// default, it
		// is populated by the URL query string (in FindStateGovernor.jsp).

		String countyname = req.getParameter("countyname");
		String statename = req.getParameter("statename");
		if (statename == null || statename.trim().isEmpty() || countyname == null
				|| countyname.trim().isEmpty())
		{
			messages.put("success", "Please enter a valid State and County name.");
		}
		else
		{
			try
			{
				county = countyDao.getCountyByCountyNameAndStateName(countyname,
						statename);
				if (county == null)
				{
					messages.put("success",
							"County does not exist. Could not locate County Hospital List.");
				}
				else
				{
					countyHospitals = countyHospitalDao
							.getCountyHospitalDataByCounty(county);
					if (countyHospitals == null)
					{
						messages.put("success", "CountyHospital List does not exist.");
					}
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
				throw new IOException(e);
			}
			messages.put("success",
					"Displaying results for " + countyname + ", " + statename);
		}
		req.setAttribute("countyhospitals", countyHospitals);

		req.getRequestDispatcher("/FindCountyHospital.jsp").forward(req, resp);
	}
}
