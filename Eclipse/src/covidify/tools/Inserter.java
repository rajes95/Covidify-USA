/**
 * Lily Bessette - CS5200 Database Management - Homework 4 - 7/6/2020
 */
package covidify.tools;

import restaurantReviewApp.dal.*;
import restaurantReviewApp.model.*;

import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

/*
	For each class : exercise the create, read, update, and delete operations
	(if the method exists) using the data access object.

User -
      X public User create(User user)
      X public User getUserByUserName(String userName)
      X public User delete(User user)
CreditCard -
      X public CreditCard create(CreditCard creditCard)
      X public CreditCard getCreditCardByCardNumber(long cardNumber)
      -(Note that the Java datatype is different than what is in the database definition)
      X  public CreditCard getCreditCardById(int creditCardKey)
      X public List<CreditCard> getCreditCardsByUserName(String userName)
      X public CreditCard updateExpiration(CreditCard creditCard, Date newExpiration)
      X public CreditCard delete(CreditCard creditCard)
Company -
      X public Company create(Company company)
      X public Company getCompanyByCompanyName(String companyName)
      X public Company getCompanyById(int companyKey)
      X public Company updateAbout(Company company, String newAbout)
      X public Company delete(Company company)
Restaurant -
      X public Restaurant create(Restaurant restaurant)
      X public Restaurant getRestaurantById(int restaurantId)
      X public List<Restaurant> getRestaurantsByCuisine(Restaurant.CuisineType cusine)
      - Note: Define the CuisineType enum in Restuarants.java (see BlogUsers.java for an example).
      X public List<Restaurant> getRestaurantsByCompanyName(String companyName)
      X public Restaurant delete(Restaurant restaurant)
SitDownRestaurant  -
      X public SitDownRestaurant create(SitDownRestaurant sitDownRestaurant)
      X public SitDownRestaurant getSitDownRestaurantById(int sitDownRestaurantId)
      X public List<SitDownRestaurant> getSitDownRestaurantsByCompanyName(String companyName)
      X public SitDownRestaurant delete(SitDownRestaurant sitDownRestaurant)
TakeOutRestaurant  -
     X public TakeOutRestaurant create(TakeOutRestaurant takeOutRestaurant)
     X public TakeOutRestaurant getTakeOutRestaurantById(int takeOutRestaurantId)
     X public List<TakeOutRestaurant> getTakeOutRestaurantsByCompanyName(String companyName)
     X public TakeOutRestaurant delete(TakeOutRestaurant takeOutRestaurant)
FoodCartRestaurant  -
     X public FoodCartRestaurant create(FoodCartRestaurant foodCartRestaurant)
     X public FoodCartRestaurant getFoodCartRestaurantById(int foodCartRestaurantId)
     X public List<FoodCartRestaurant> getFoodCartRestaurantsByCompanyName(String companyName)
     X public FoodCartRestaurant delete(FoodCartRestaurant foodCartRestaurant)
Review -
     X public Review create(Review review)
     X public Review getReviewById(int reviewId)
     X public List<Review> getReviewsByUserName(String userName)
     X public List<Review> getReviewsByRestaurantId(int restaurantId)
     X public Review delete(Review review)
Recommendation -
     X public Recommendation create(Recommendation recommendation)
     X public Recommendation getRecommendationById(int recommendationId)
     X public List<Recommendation> getRecommendationsByUserName(String userName)
     X public List<Recommendation> getRecommendationsByRestaurantId(int restaurantId)
     X public Recommendation delete(Recommendation recommendation)
Reservation -
     X public Reservation create(Reservation reservation)
     X public Reservation getReservationById(int reservationId)
     X public List<Reservation> getReservationsByUserName(String userName)
     X public List<Reservation> getReservationsBySitDownRestaurantId(int sitDownRestaurantId)
     X public Reservation delete(Reservation reservation)
*/

/**
 * main() runner, used for the app demo.
 * <p>
 * Instructions: 1. Create a new MySQL schema and then run the CREATE TABLE statements from lecture:
 * http://goo.gl/86a11H. 2. Update ConnectionManager with the correct user, password, and schema.
 */
public class Inserter {

  public static void main(String[] args) throws SQLException {
    // DAO instances.
    UserDao userDao = UserDao.getInstance();
    CreditCardDao creditCardDao = CreditCardDao.getInstance();
    RestaurantDao restaurantDao = RestaurantDao.getInstance();
    FoodCartRestaurantDao foodCartRestaurantDao = FoodCartRestaurantDao.getInstance();
    SitDownRestaurantDao sitDownRestaurantDao = SitDownRestaurantDao.getInstance();
    TakeOutRestaurantDao takeOutRestaurantDao = TakeOutRestaurantDao.getInstance();
    CompanyDao companyDao = CompanyDao.getInstance();
    RecommendationDao recommendationDao = RecommendationDao.getInstance();
    ReviewDao reviewDao = ReviewDao.getInstance();
    ReservationDao reservationDao = ReservationDao.getInstance();
/*
    // INSERT objects from our model.

    User user1 = new User("susanBessette","momlovesourdogmore","Susan","Bessette","suebee@yahoo.com","5087281111");
    user1 = userDao.create(user1);
    User user2 = new User("tomBessette","dadisthebest","Tom","Bessette","tomnnsb@gmail.com","5080006437");
    user2 = userDao.create(user2);

    Date date1 = new Date(7 / 1 / 2022);
    Date date2 = new Date(2 / 1 / 2021);

    CreditCard creditCard1 = new CreditCard("Susan Bessette", Long.valueOf("123123123123123"), date1, user1);
    creditCard1 = creditCardDao.create(creditCard1);
    CreditCard creditCard2 = new CreditCard("Tom Bessette", Long.valueOf("77777722222777"), date2, user2);
    creditCard2 = creditCardDao.create(creditCard2);

    
    Company company1 = new Company( "The 2nd Chaos Conglomorate", "The 2nd Massive corporate takeover machine");
    company1 = companyDao.create(company1);
    Company company2 = new Company("The 2nd Food Frenzy", "The 2nd Holding company for all the food.");
    company2 = companyDao.create(company2);
    Company company3 = new Company( "The 2nd Slow Start", "The 2nd Company that is so conservative, we don\"t own anything");
    company3 = companyDao.create(company3);
    Company company4 = new Company( "The 2nd McRalph Corporation", "The 2nd We have lots of restaurants.");
    company4 = companyDao.create(company4);

    SitDownRestaurant restaurant1 = new SitDownRestaurant("The 2nd Abalone Al\"s", "Abalone and more", "Abalone $5.00 Everything else market price", "8am to 7pm Tuesdays only", Boolean.FALSE, "1 Seafood Lane", null, "Boston", "MA", "02450", Restaurant.CuisineType.valueOf("hispanic"), company2, 70);
    restaurant1 = sitDownRestaurantDao.create(restaurant1);
    SitDownRestaurant restaurant2 = new SitDownRestaurant("The 2nd Beth\"s Boulangerie", "French food with a twist", "Pan de Campagne $4.54", "dawn til dusk, seven days", Boolean.TRUE, "2 Rue des Poisson", null, "Paris", "GA", "12345", Restaurant.CuisineType.valueOf("european"), null, 22);
    restaurant2 = sitDownRestaurantDao.create(restaurant2);
    SitDownRestaurant restaurant3 = new SitDownRestaurant("The 2nd Chad\"s Chicken", "Fried chicken from a secret recipe", "Chicken.  What were you expecting?", "Weekends only, until we sell out", Boolean.TRUE, "3 Rooster Road ", null, "Atlanta", "GA", "12344", Restaurant.CuisineType.valueOf("american"), company2,33);
    restaurant3 = sitDownRestaurantDao.create(restaurant3);
    TakeOutRestaurant restaurant4 = new TakeOutRestaurant("The 2nd Danni\"s Dumplings", "Country style dumpling service", "Dumplin\"s $2 each", "Sunday only", Boolean.TRUE, "4 Dumpling Drive", null, "Marietta", "GA", "12367", Restaurant.CuisineType.valueOf("american"), company1, 10);
    restaurant4 = takeOutRestaurantDao.create(restaurant4);
    TakeOutRestaurant restaurant5 = new TakeOutRestaurant("The 2nd Eve\"s Eggrolls", "Everything eggroll", "Eggrolls.  What else?", "weekdays noon to 2pm", Boolean.TRUE, "5 Lobster Lane", null, "Philadelphia", "PA", "24568", Restaurant.CuisineType.valueOf("asian"), company1, 15);
    restaurant5 = takeOutRestaurantDao.create(restaurant5);
    TakeOutRestaurant restaurant6 = new TakeOutRestaurant("The 2nd Frank\"s Franks", "Franks.  Nothing more.", "Franks $3", "When there\"s a ballgame", Boolean.TRUE, "6 Spice Street", null, "Baltimore", "MD", "34554", Restaurant.CuisineType.valueOf("american"), null,45);
    restaurant6 = takeOutRestaurantDao.create(restaurant6);
    FoodCartRestaurant restaurant7 = new FoodCartRestaurant("The 2nd McRalph\"s #1", "A big chain", "McFood", "Daily", Boolean.TRUE, "1 Main St", null, "Boston", "MA", "02132", Restaurant.CuisineType.valueOf("asian"), company4, Boolean.FALSE);
    restaurant7 = foodCartRestaurantDao.create(restaurant7);
    FoodCartRestaurant restaurant8 = new FoodCartRestaurant("The 2nd McRalph\"s #2", "a big chain", "McFood", "Daily", Boolean.TRUE, "2 Other St", null, "Boston", "MA", "02915", Restaurant.CuisineType.valueOf("asian"), company4, Boolean.TRUE);
    restaurant8 = foodCartRestaurantDao.create(restaurant8);
    FoodCartRestaurant restaurant9 = new FoodCartRestaurant("The 2nd McRalph\"s #3", "a big chain", "McFood", "Daily", Boolean.TRUE, "3 Local Lane", null, "Boston", "MA", "02915", Restaurant.CuisineType.valueOf("asian"), company4, Boolean.FALSE);
    restaurant9 = foodCartRestaurantDao.create(restaurant9);
    SitDownRestaurant restaurant10 = new SitDownRestaurant("The 2nd McRalph\"s #4", "a big chain", "McFood", "Daily", Boolean.TRUE, "4 Hamburger St", null, "Boston", "MA", "02915", Restaurant.CuisineType.valueOf("asian"), company4, 80);
    restaurant10 = sitDownRestaurantDao.create(restaurant10);
    SitDownRestaurant restaurant11 = new SitDownRestaurant("The 2nd McRalph\"s #5", "a big chain", "McFood", "Daily", Boolean.TRUE, "5 Pizza Parkway", null, "Boston", "MA", "02915", Restaurant.CuisineType.valueOf("asian"), company4,20);
    restaurant11 = sitDownRestaurantDao.create(restaurant11);


    Date date3 = new Date(7 / 4 / 2020);
    Date date4 = new Date(2 / 1 / 2020);

    Review review1 = new Review( user1, restaurant4, date3, "Best Italian food in Boston! Loved the atmosphere!", (float) 4.8);
    review1 = reviewDao.create(review1);
    Review review2 = new Review( user2, restaurant11, date4, "What a great snack! Quick service and delicious food!", (float) 5);
    review2 = reviewDao.create(review2);



    Recommendation recommendation1 = new Recommendation(user1, restaurant5);
    recommendation1 = recommendationDao.create(recommendation1);
    Recommendation recommendation2 = new Recommendation(user2, restaurant5);
    recommendation2 = recommendationDao.create(recommendation2);


    Date date5 = Date.valueOf("2020-7-22");

    Reservation reservation1 = new Reservation(user1, restaurant3, date5,date5,4);
    reservation1 = reservationDao.create(reservation1);
    Reservation reservation2 = new Reservation(user2, restaurant1, date5, date5, 9);
    reservation2 = reservationDao.create(reservation2);

*/
    // READ.
    User p1 = userDao.getUserByUserName("DianneD");
    User p2 = userDao.getUserByUserName("lilybessette");

    System.out.format("Reading user: u:%s f:%s l:%s ps:%s e:%s p:%s\n",
            p1.getUserName(), p1.getFirstName(), p1.getLastName(),
            p1.getPasswordHash(), p1.getEmail(), p1.getPhoneNum());
    System.out.format("Reading user: u:%s f:%s l:%s ps:%s e:%s p:%s\n",
            p2.getUserName(), p2.getFirstName(), p2.getLastName(),
            p2.getPasswordHash(), p2.getEmail(), p2.getPhoneNum());


    creditCardDao.getCreditCardByCardNumber();

    CreditCard getCreditCardById(int creditCardKey)
    List<CreditCard> getCreditCardsByUserName("lilybessette")
    CreditCard updateExpiration(CreditCard creditCard, Date newExpiration)


    Company c1 = companyDao.getCompanyByCompanyName("Beth\"s Boulangerie");
    Company c2 = companyDao.getCompanyByCompanyName("Abalone Al\"s");


  }




}
		/*

		

		Persons p1 = personsDao.getPersonFromUserName("b");
		List<Persons> pList1 = personsDao.getPersonsFromFirstName("bruce");
		System.out.format("Reading person: u:%s f:%s l:%s \n",
			p1.getUserName(), p1.getFirstName(), p1.getLastName());
		for(Persons p : pList1) {
			System.out.format("Looping persons: u:%s f:%s l:%s \n",
				p.getUserName(), p.getFirstName(), p.getLastName());
		}
		Administrators a1 = administratorsDao.getAdministratorFromUserName("a");
		List<Administrators> aList1 = administratorsDao.getAdministratorsFromFirstName("bruce");
		System.out.format("Reading administrator: u:%s f:%s l:%s d:%s \n",
			a1.getUserName(), a1.getFirstName(), a1.getLastName(), a1.getLastLogin());
		for(Administrators a : aList1) {
			System.out.format("Looping administrators: u:%s f:%s l:%s \n",
				a.getUserName(), a.getFirstName(), a.getLastName(), a.getLastLogin());
		}
		BlogUsers bu1 = blogUsersDao.getBlogUserFromUserName("bu");
		List<BlogUsers> buList1 = blogUsersDao.getBlogUsersFromFirstName("bruce");
		System.out.format("Reading blog user: u:%s f:%s l:%s d:%s s:%s \n",
			bu1.getUserName(), bu1.getFirstName(), bu1.getLastName(), bu1.getDob(), bu1.getStatusLevel().name());
		for(BlogUsers bu : buList1) {
			System.out.format("Looping blog users: u:%s f:%s l:%s d:%s s:%s \n",
				bu.getUserName(), bu.getFirstName(), bu.getLastName(), bu.getDob(), bu.getStatusLevel().name());
		}
		List<BlogPosts> bpList1 = blogPostsDao.getBlogPostsForUser(bu1);
		for(BlogPosts bp : bpList1) {
			System.out.format("Looping blog posts: t:%s c:%s u:%s \n",
				bp.getTitle(), bp.getContent(), bu1.getUserName());
		}
		List<BlogComments> bcList1 = blogCommentsDao.getBlogCommentsForUser(blogUser1);
		for(BlogComments bc : bcList1) {
			System.out.format("Looping blog comments: t:%s u:%s \n",
				bc.getContent(), blogUser1.getUserName());
		}
		bcList1 = blogCommentsDao.getBlogCommentsForUser(blogUser);
		for(BlogComments bc : bcList1) {
			System.out.format("Looping blog comments: t:%s u:%s \n",
				bc.getContent(), blogUser.getUserName());
		}
		List<Reshares> rList1 = resharesDao.getResharesForUser(blogUser2);
		for(Reshares r : rList1) {
			System.out.format("Looping reshare: i:%s u:%s t:%s \n",
				r.getReshareId(), r.getBlogUser().getUserName(),
				r.getBlogPost().getTitle());
		}
	 */


/*
	For each class : exercise the create, read, update, and delete operations
	(if the method exists) using the data access object.

User -
      X public User delete(User user)
CreditCard -


      -(Note that the Java datatype is different than what is in the database definition)
      X  public CreditCard getCreditCardById(int creditCardKey)
      X public List<CreditCard> getCreditCardsByUserName(String userName)
      X public CreditCard updateExpiration(CreditCard creditCard, Date newExpiration)
      X public CreditCard delete(CreditCard creditCard)
Company -
      X public Company create(Company company)
      X public Company getCompanyByCompanyName(String companyName)
      X public Company getCompanyById(int companyKey)
      X public Company updateAbout(Company company, String newAbout)
      X public Company delete(Company company)
Restaurant -
      X public Restaurant create(Restaurant restaurant)
      X public Restaurant getRestaurantById(int restaurantId)
      X public List<Restaurant> getRestaurantsByCuisine(Restaurant.CuisineType cusine)
      - Note: Define the CuisineType enum in Restuarants.java (see BlogUsers.java for an example).
      X public List<Restaurant> getRestaurantsByCompanyName(String companyName)
      X public Restaurant delete(Restaurant restaurant)
SitDownRestaurant  -
      X public SitDownRestaurant create(SitDownRestaurant sitDownRestaurant)
      X public SitDownRestaurant getSitDownRestaurantById(int sitDownRestaurantId)
      X public List<SitDownRestaurant> getSitDownRestaurantsByCompanyName(String companyName)
      X public SitDownRestaurant delete(SitDownRestaurant sitDownRestaurant)
TakeOutRestaurant  -
     X public TakeOutRestaurant create(TakeOutRestaurant takeOutRestaurant)
     X public TakeOutRestaurant getTakeOutRestaurantById(int takeOutRestaurantId)
     X public List<TakeOutRestaurant> getTakeOutRestaurantsByCompanyName(String companyName)
     X public TakeOutRestaurant delete(TakeOutRestaurant takeOutRestaurant)
FoodCartRestaurant  -
     X public FoodCartRestaurant create(FoodCartRestaurant foodCartRestaurant)
     X public FoodCartRestaurant getFoodCartRestaurantById(int foodCartRestaurantId)
     X public List<FoodCartRestaurant> getFoodCartRestaurantsByCompanyName(String companyName)
     X public FoodCartRestaurant delete(FoodCartRestaurant foodCartRestaurant)
Review -
     X public Review create(Review review)
     X public Review getReviewById(int reviewId)
     X public List<Review> getReviewsByUserName(String userName)
     X public List<Review> getReviewsByRestaurantId(int restaurantId)
     X public Review delete(Review review)
Recommendation -
     X public Recommendation create(Recommendation recommendation)
     X public Recommendation getRecommendationById(int recommendationId)
     X public List<Recommendation> getRecommendationsByUserName(String userName)
     X public List<Recommendation> getRecommendationsByRestaurantId(int restaurantId)
     X public Recommendation delete(Recommendation recommendation)
Reservation -
     X public Reservation create(Reservation reservation)
     X public Reservation getReservationById(int reservationId)
     X public List<Reservation> getReservationsByUserName(String userName)
     X public List<Reservation> getReservationsBySitDownRestaurantId(int sitDownRestaurantId)
     X public Reservation delete(Reservation reservation)
*/

