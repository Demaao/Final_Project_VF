package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.Branch;


import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SimpleServer extends AbstractServer {
	private static final SessionFactory sessionFactory = getSessionFactory();

	public SimpleServer(int port) throws Exception {
		super(port);
		initializeDatabase();
	}

	private static SessionFactory getSessionFactory() throws HibernateException {
		Configuration configuration = new Configuration();

		Scanner input = new Scanner(System.in);

		System.out.println("MySQL Database Name: FinalDatabase");

		System.out.print("Please enter your password: ");
		String password = input.next();

		input.close();

		configuration.setProperty("hibernate.connection.password", password);
		configuration.addAnnotatedClass(Movie.class);
		configuration.addAnnotatedClass(SoonMovie.class);
		configuration.addAnnotatedClass(HomeMovie.class);
		configuration.addAnnotatedClass(Branch.class);

		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties())
				.build();
		return configuration.buildSessionFactory(serviceRegistry);
	}

	private static void initializeDatabase() throws Exception {
		try (Session session = sessionFactory.openSession()) {
			session.beginTransaction();
			generateMovies(session);
			generateSoonMovies(session);
			generateHomeMovies(session);
			generateBranches(session);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void generateMovies(Session session) throws Exception {
		byte[] image1 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\1.jpg");
		byte[] image2 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\2.jpg");
		byte[] image3 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\3.jpg");
		byte[] image4 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\4.jpg");
		byte[] image5 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\5.jpg");
		byte[] image6 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\6.jpg");
		byte[] image7 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\7.jpg");
		byte[] image8 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\8.jpg");
		byte[] image9 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\9.jpg");
		byte[] image10 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\images\\10.jpg");

		Branch haifaCinema = session.get(Branch.class, 1);  // שליפת בתי הקולנוע מהמסד הנתונים לפי ה-ID שלהם
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);
		byte[] image1 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\1.jpg");
		byte[] image2 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\2.jpg");
		byte[] image3 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\3.jpg");
		byte[] image4 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\4.jpg");
		byte[] image5 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\5.jpg");
		byte[] image6 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\6.jpg");
		byte[] image7 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\7.jpg");
		byte[] image8 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\8.jpg");

		List<Branch> CinemaBranches = new ArrayList<>();  // יצירת רשימת בתי קולנוע עבור הסרט הראשון
		CinemaBranches.add(haifaCinema);
		CinemaBranches.add(telAvivCinema);
		CinemaBranches.add(eilatCinema);
		CinemaBranches.add(telAvivCinema);
		CinemaBranches.add(karmielCinema);
		CinemaBranches.add(jerusalemCinema);


		Movie num1 = new Movie(1, "A quiet place", "מקום שקט 2", "Michael Sarnoski", 2020, image1,"Drama","Following the events at home, the Abbott family now face the terrors of the outside world. Forced to venture into the unknown, they realize the creatures that hunt by sound are not the only threats lurking beyond the sand path."
				,"Emily Blunt,John Krasinski,Cillian Murphy","1h 37m",CinemaBranches );
		session.save(num1);
		session.flush();

		Movie num2 = new Movie(2, "Barbie", "ברבי", "Greta Gerwig", 2023, image2,"Comedy","Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans."
				,"Margot Robbie,Issa Rae,Ryan Gosling","1h 54m",CinemaBranches);
		session.save(num2);
		session.flush();

		Movie num3 = new Movie(3, "Fast X", "מהיר ועצבני 10", "Louis Leterrier", 2023,image3,"Action","Dom Toretto and his family are targeted by the vengeful son of drug kingpin Hernan Reyes."
				,"Vin Diesel,Michelle Rodriguez,Jason Statham","2h 21m",CinemaBranches);
		session.save(num3);
		session.flush();

		Movie num4 = new Movie(4, "Inside out", "הקול בראש 2", "Kelsey Mann", 2024, image4,"Adventure","A sequel that features Riley entering puberty and experiencing brand new, more complex emotions as a result. As Riley tries to adapt to her teenage years, her old emotions try to adapt to the possibility of being replaced."
				,"Amy Poehler,Maya Hawke,Kensington Tallman","1h 36m",CinemaBranches);
		session.save(num4);
		session.flush();

		Movie num5 = new Movie(5, "It Ends With Us", "איתנו זה נגמר", "Justin Baldoni", 2024, image5,"Drama","When a woman's first love suddenly reenters her life, her relationship with a charming, but abusive neurosurgeon is upended and she realizes she must learn to rely on her own strength to make an impossible choice for her future."
				,"Blake Lively,Justin Baldoni,Jenny Slate","2h 10m",CinemaBranches);
		session.save(num5);
		session.flush();

		Movie num6 = new Movie(6, "Joker", "ג'וקר", "Todd Philips", 2019, image6,"Drama","Arthur Fleck, a party clown and a failed stand-up comedian, leads an impoverished life with his ailing mother. However, when society shuns him and brands him as a freak, he decides to embrace the life of crime and chaos in Gotham City."
				,"Joaquin Phoenix,Robert De Niro,Zazie Beetz","2h 2m",CinemaBranches);
		session.save(num6);
		session.flush();

		Movie num7 = new Movie(7, "Oppenheimer", "אופנהיימר", "Christopher Nolan", 2023, image7,"Documentary","The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb."
				,"Cillian Murphy Emily BluntRobert Downey","3h 10m",CinemaBranches);
		session.save(num7);
		session.flush();

		Movie num8 = new Movie(8, "The creator", "היוצר", "Gareth Edward", 2023, image8,"Action","Against the backdrop of a war between humans and robots with artificial intelligence, a former soldier finds the secret weapon, a robot in the form of a young child."
				,"John Washington,Madeleine Voyles,Gemma Chan","2h 13m",CinemaBranches);
		session.save(num8);
		session.flush();

		Movie num9 = new Movie(9, "Daughters", "הבנות", "Angela Patton", 2024, image9,"Documentary","Four young girls prepare for a special Daddy Daughter Dance with their incarcerated fathers, as part of a unique fatherhood program in a Washington"
				,"Veronica Ngo,Ian Verdun,Sturgill Simpson","1h 48m",CinemaBranches);
		session.save(num9);
		session.flush();

		Movie num10 = new Movie(10, "In the Rearview", "מבט לאחור", "Maciek Hamela", 2023, image10,"Documentary","A small van traverses war-torn roads, picking up Ukrainians as they abandon their homes at the front. Shuttling them across the battered landscape into exile, the van becomes a fragile refuge, a zone for its passengers' confidences."
				,"Maciek Hamela,Frances Conroy,Hannah Gross","1h 24m",CinemaBranches);
		session.save(num10);
		session.flush();

	}

	private static void generateSoonMovies(Session session) throws Exception {
		byte[] Soonimage1 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\1.jpg");
		byte[] Soonimage2 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\2.jpg");
		byte[] Soonimage3 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\3.jpg");
		byte[] Soonimage1 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\1.jpg");
		byte[] Soonimage2 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\2.jpg");
		byte[] Soonimage3 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\3.jpg");

		SoonMovie soonMovie1 = new SoonMovie(1, "Blink Twice","צאי מזה" ,"Sep 5,2024", Soonimage1);
		session.save(soonMovie1);
		session.flush();

		SoonMovie soonMovie2 = new SoonMovie(2, "African Giants","ענקים אפריקאים", "Sep 10,2024", Soonimage2);
		session.save(soonMovie2);
		session.flush();

		SoonMovie soonMovie3 = new SoonMovie(3, "The Killer's Game","המשחק הרוצח" ,"Sep 15,2024", Soonimage3);
		session.save(soonMovie3);
		session.flush();
	}

	private static void generateHomeMovies(Session session) throws Exception {
		byte[] image1 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\forHome_images\\1.jpg");
		byte[] image2 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\forHome_images\\2.jpg");
		byte[] image3 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\forHome_images\\3.jpg");
		byte[] image4 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\forHome_images\\4.jpg");
		byte[] image5 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\forHome_images\\5.jpg");
		byte[] image6 = loadImageFromFile("C:\\Users\\USER\\IdeaProjects\\Final_Project_V2\\server\\src\\main\\resources\\forHome_images\\6.jpg");

		HomeMovie homeMovie1 = new HomeMovie(1, "Despicable Me 4", "גנוב על החיים", "Chris Renaud", 2024, image1, "https://Despicable_Me_4_Movie_link.com","Adventure","Gru, Lucy, Margo, Edith, and Agnes welcome a new member to the family, Gru Jr., who is intent on tormenting his dad. Gru faces a new nemesis in Maxime Le Mal and his girlfriend Valentina, and the family is forced to go on the run."
				,"Steve Carell,Kristen Wiig,Pierre Coffin","1h 34m");
		session.save(homeMovie1);
		session.flush();

		HomeMovie homeMovie2 = new HomeMovie(2, "Bad Boys", "בחורים רעים", "Adil El Arbi", 2024, image2, "https://Bad_Boys_Movie_link.com","Comedy","When their late police captain gets linked to drug cartels, wisecracking Miami cops Mike Lowrey and Marcus Burnett embark on a dangerous mission to clear his name."
				,"Will Smith,Martin Lawrence,Vanessa Hudgens","1h 55m");
		session.save(homeMovie2);
		session.flush();

		HomeMovie homeMovie3 = new HomeMovie(3, "Wire Room", "מלכוד בחדר", "Matt Eskandari", 2022, image3, "https://Wire_Room_Movie_link.com","Action","While on wire room duty, a federal agent listens in as the target is attacked in his home by a hit squad. Without burning the wire, he must protect the investigation and the target's life from the confines of a room fifty miles away."
				,"Kevin Dillon,Bruce Willis,Oliver Trevena","1h 36m");
		session.save(homeMovie3);
		session.flush();

		HomeMovie homeMovie4 = new HomeMovie(4, "Mission Impossible", "משימה לא אפשרית", "Christopher McQuarrie", 2023, image4, "https://Mission_Impossible_Movie_link.com","Comedy","Ethan Hunt and his IMF team must track down a dangerous weapon before it falls into the wrong hands."
				,"Tom Cruise,Hayley Atwell,Ving Rhames","2h 43m");
		session.save(homeMovie4);
		session.flush();

		HomeMovie homeMovie5 = new HomeMovie(5, "Deadpool Wolverine", "דדפול & וולברין ", "Shawn Levy", 2024, image5, "https://Deadpool_Wolverine_Movie_link.com","Adventure","Deadpool is offered a place in the Marvel Cinematic Universe by the Time Variance Authority, but instead recruits a variant of Wolverine to save his universe from extinction"
				,"Ryan Reynolds,Hugh Jackman,Emma Corrin","2h 8m");
		session.save(homeMovie5);
		session.flush();

		HomeMovie homeMovie6 = new HomeMovie(6, "his Time Next Year", "מזל שנפגשנו", "Nick Moore", 2024, image6, "https://This_Time_Next_Year_Movie_link.com","Comedy","Minnie and Quinn are born on the same day, one minute apart. Their lives may begin together, but their worlds couldn't be more different. Years later they find themselves together again. Maybe it's time to take a chance on love."
				,"Sophie Cookson,Lucien Laviscount,Golda Rosheuvel","1h 56m");
		session.save(homeMovie6);
		session.flush();

	}
	private static void generateBranches(Session session) throws Exception {
		Branch haifaCinema = new Branch(1,"Haifa Cinema","Haifa");
		session.save(haifaCinema);

		Branch telAvivCinema = new Branch(2,"Tel Aviv Cinema","Tel Aviv");
		session.save(telAvivCinema);

		Branch eilatCinema = new Branch(3,"Eilat Cinema","Eilat");
		session.save(eilatCinema);

		Branch karmielCinema = new Branch(4,"Karmiel Cinema","Karmiel");
		session.save(karmielCinema);

		Branch jerusalemCinema = new Branch(5,"Jerusalem Cinema","Jerusalem");
		session.save(jerusalemCinema);

		session.flush();
	}



	private static byte[] loadImageFromFile(String filePath) throws IOException {
		File file = new File(filePath);
		try (InputStream inputStream = new FileInputStream(file)) {
			return inputStream.readAllBytes();
		}
	}

	private static List<Movie> getAllMovies(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		return session.createQuery(query).getResultList();
	}

	private static List<SoonMovie> getAllSoonMovies(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<SoonMovie> query = builder.createQuery(SoonMovie.class);
		query.from(SoonMovie.class);
		return session.createQuery(query).getResultList();
	}

	private static List<HomeMovie> getAllHomeMovies(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<HomeMovie> query = builder.createQuery(HomeMovie.class);
		query.from(HomeMovie.class);
		return session.createQuery(query).getResultList();
	}


	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		NewMessage message = (NewMessage) msg;
		String msgString = message.getMessage();
		try {
			if (msgString.isBlank()) {
				message.setMessage("Error! we got an empty message");
				client.sendToClient(message);
			} else if (msgString.equals("moviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage = new NewMessage(movies, "movies");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("soonMoviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים קרובים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<SoonMovie> soonMovies = getAllSoonMovies(session);
					NewMessage newMessage = new NewMessage(soonMovies, "soonMovies");  // שליחת רשימת הסרטים הקרובים ללקוח עם המחרוזת "soonMovies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("homeMoviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים לצפייה מהבית
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage newMessage = new NewMessage(homeMovies, "homeMovies");  // שליחת רשימת הסרטים לבית ללקוח עם המחרוזת "homeMovies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

