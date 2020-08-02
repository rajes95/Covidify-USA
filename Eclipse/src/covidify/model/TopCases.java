package covidify.model;

import java.util.Date;



		public class TopCases
		{
			protected State state;
			protected Date date;
			protected Integer covidCases;

			public TopCases( State state, Date date, 
					Integer covidCases)
			{
				this.state = state;
				this.date = date;
				this.covidCases = covidCases;
			}
			
			
			public State getState()
			{
				return state;
			}

			public void setStatae(State state)
			{
				this.state = state;
			}
			
			public Date getDate()
			{
				return date;
			}

			public void setDate(Date date)
			{
				this.date = date;
			}

			public Integer getCovidCases()
			{
				return covidCases;
			}

			public void setCovidCases(Integer covidCases)
			{
				this.covidCases = covidCases;
			}
			
		}
		

