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
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.*;
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
		configuration.addAnnotatedClass(Screening.class);
		configuration.addAnnotatedClass(HeadManager.class); //////////////////
		configuration.addAnnotatedClass(BranchManager.class); /////////////
		configuration.addAnnotatedClass(ContentManager.class); /////////////////
		configuration.addAnnotatedClass(CustomerServiceWorker.class); /////////////

		configuration.addAnnotatedClass(Customer.class); //for testing only
		configuration.addAnnotatedClass(Purchase.class);  //for testing only


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
			generateScreenings(session);
			generateHeadManager(session); /////////////////////
			generateBranchManager(session); /////////////////////
			generateContentManager(session); /////////////////////
			generateCustomerServiceWorker(session);

			generateCustomers(session);//for testing only
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateHeadManager(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Branch haifaCinema = session.get(Branch.class, 1);  // שליפת בתי הקולנוע מהמסד הנתונים לפי ה-ID שלהם
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);

		List<Branch> CinemaBranches = new ArrayList<>();  // יצירת רשימת בתי קולנוע עבור הסרט הראשון
		CinemaBranches.add(haifaCinema);
		CinemaBranches.add(telAvivCinema);
		CinemaBranches.add(eilatCinema);
		CinemaBranches.add(telAvivCinema);
		CinemaBranches.add(karmielCinema);
		CinemaBranches.add(jerusalemCinema);

		HeadManager headManager = new HeadManager(324122258, "Shada Ghanem", "shadaGh0512",
				"shada0512", "Head Manager", false, "0527990807",
				"shada.5.12.2001@gmail.com", CinemaBranches);
		session.save(headManager);
		session.flush();
	}

	private static void generateBranchManager(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Branch haifaCinema = session.get(Branch.class, 1);
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);

		BranchManager branchManager1 = new BranchManager(200134667, "Dema Omar","dema123",
				"dema123", "Branch Manager", false, "0508873332",
				"dema@gmail.com", haifaCinema);
		session.save(branchManager1);
		session.flush();

		BranchManager branchManager2 = new BranchManager(304134667, "Hala Ishan","hala123",
				"hala123", "Branch Manager", false, "0520073332",
				"hala@gmail.com", telAvivCinema);
		session.save(branchManager2);
		session.flush();

		BranchManager branchManager3 = new BranchManager(230134667, "Shatha Maree","shatha123",
				"shatha123", "Branch Manager", false, "0508855532",
				"shatha@gmail.com", eilatCinema);
		session.save(branchManager3);
		session.flush();

		BranchManager branchManager4 = new BranchManager(300664667, "Lian Natour","lian99",
				"lian99", "Branch Manager", false, "0508866332",
				"lain@gmail.com", karmielCinema);
		session.save(branchManager4);
		session.flush();

		BranchManager branchManager5 = new BranchManager(200100667, "Rozaline Kozly","roza123",
				"roza123", "Branch Manager", false, "0520073332",
				"raza@gmail.com", jerusalemCinema);
		session.save(branchManager5);
		session.flush();
	}

	private static void generateContentManager(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		ContentManager contentManager1 = new ContentManager(300134667, "Anna Collins","anna123",
				"anna123", "Content Manager", false, "0508000032",
				"annaa@gmail.com");
		session.save(contentManager1);
		session.flush();
	}

	private static void generateCustomerServiceWorker(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		CustomerServiceWorker customerServiceWorker1 = new CustomerServiceWorker(300100007, "Emma Thompson","emma123",
				"emma123", "Customer Service Worker", false, "0500000032",
				"annaa@gmail.com");
		session.save(customerServiceWorker1);
		session.flush();
	}


	private static void generateMovies(Session session) throws Exception {
		byte[][] images = new byte[10][];
		for (int i = 1; i <= 10; i++) {
			images[i - 1] = loadImageFromFile(String.format("images/%d.jpg", i));
		}
		/*
		Branch haifaCinema = session.get(Branch.class, 1);  // שליפת בתי הקולנוע מהמסד הנתונים לפי ה-ID שלהם
		Branch haifaCinema = session.get(Branch.class, 1);
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);


		List<Branch> CinemaBranches1 = new ArrayList<>();  // יצירת רשימת בתי קולנוע עבור הסרט הראשון
		CinemaBranches1.add(haifaCinema);
		CinemaBranches1.add(telAvivCinema);
		CinemaBranches1.add(eilatCinema);

		List<Branch> CinemaBranches1 = new ArrayList<>();
		CinemaBranches1.add(haifaCinema);
		CinemaBranches1.add(telAvivCinema);
		CinemaBranches1.add(eilatCinema);

		List<Branch> CinemaBranches2 = new ArrayList<>();
		CinemaBranches2.add(karmielCinema);
		CinemaBranches2.add(jerusalemCinema);

		List<Branch> CinemaBranches2 = new ArrayList<>();  // יצירת רשימת בתי קולנוע עבור הסרט הראשון
		CinemaBranches2.add(karmielCinema);
		CinemaBranches2.add(jerusalemCinema);
		*/

		Movie num1 = new Movie(1, "A quiet place", "מקום שקט 2", "Michael Sarnoski", 2020, images[0],"Drama","Following the events at home, the Abbott family now face the terrors of the outside world. Forced to venture into the unknown, they realize the creatures that hunt by sound are not the only threats lurking beyond the sand path."

				,"Emily Blunt, John Krasinski, Cillian Murphy","1h 37m");

		session.save(num1);
		session.flush();

		Movie num2 = new Movie(2, "Barbie", "ברבי", "Greta Gerwig", 2023, images[1],"Comedy","Barbie and Ken are having the time of their lives in the colorful and seemingly perfect world of Barbie Land. However, when they get a chance to go to the real world, they soon discover the joys and perils of living among humans."

				,"Margot Robbie, Issa Rae, Ryan Gosling","1h 54m");

		session.save(num2);
		session.flush();

		Movie num3 = new Movie(3, "Fast X", "מהיר ועצבני 10", "Louis Leterrier", 2023,images[2],"Action","Dom Toretto and his family are targeted by the vengeful son of drug kingpin Hernan Reyes."

				,"Vin Diesel, Michelle Rodriguez, Jason Statham","2h 21m");

		session.save(num3);
		session.flush();

		Movie num4 = new Movie(4, "Inside out", "הקול בראש 2", "Kelsey Mann", 2024, images[3],"Adventure","A sequel that features Riley entering puberty and experiencing brand new, more complex emotions as a result. As Riley tries to adapt to her teenage years, her old emotions try to adapt to the possibility of being replaced."

				,"Amy Poehler, Maya Hawke, Kensington Tallman","1h 36m");

		session.save(num4);
		session.flush();

		Movie num5 = new Movie(5, "It Ends With Us", "איתנו זה נגמר", "Justin Baldoni", 2024, images[4],"Drama","When a woman's first love suddenly reenters her life, her relationship with a charming, but abusive neurosurgeon is upended and she realizes she must learn to rely on her own strength to make an impossible choice for her future."

				,"Blake Lively, Justin Baldoni, Jenny Slate","2h 10m");

		session.save(num5);
		session.flush();

		Movie num6 = new Movie(6, "Joker", "ג'וקר", "Todd Philips", 2019, images[5],"Drama","Arthur Fleck, a party clown and a failed stand-up comedian, leads an impoverished life with his ailing mother. However, when society shuns him and brands him as a freak, he decides to embrace the life of crime and chaos in Gotham City."

				,"Joaquin Phoenix, Robert De Niro, Zazie Beetz","2h 2m");

		session.save(num6);
		session.flush();

		Movie num7 = new Movie(7, "Oppenheimer", "אופנהיימר", "Christopher Nolan", 2023, images[6],"Documentary","The story of American scientist J. Robert Oppenheimer and his role in the development of the atomic bomb."

				,"Cillian Murphy, Emily Blunt, Robert Downey","3h 10m");

		session.save(num7);
		session.flush();

		Movie num8 = new Movie(8, "The creator", "היוצר", "Gareth Edward", 2023, images[7],"Action","Against the backdrop of a war between humans and robots with artificial intelligence, a former soldier finds the secret weapon, a robot in the form of a young child."

				,"John Washington, Madeleine Voyles, Gemma Chan","2h 13m");

		session.save(num8);
		session.flush();

		Movie num9 = new Movie(9, "Daughters", "הבנות", "Angela Patton", 2024, images[8],"Documentary","Four young girls prepare for a special Daddy Daughter Dance with their incarcerated fathers, as part of a unique fatherhood program in a Washington"

				,"Veronica Ngo, Ian Verdun, Sturgill Simpson","1h 48m");

		session.save(num9);
		session.flush();

		Movie num10 = new Movie(10, "In the Rearview", "מבט לאחור", "Maciek Hamela", 2023, images[9],"Documentary","A small van traverses war-torn roads, picking up Ukrainians as they abandon their homes at the front. Shuttling them across the battered landscape into exile, the van becomes a fragile refuge, a zone for its passengers' confidences."

				,"Maciek Hamela, Frances Conroy, Hannah Gross","1h 24m");

		session.save(num10);
		session.flush();

	}

	private static void generateSoonMovies(Session session) throws Exception {
		byte[] SoonImage1 = loadImageFromFile("SoonImages/1.jpg");
		byte[] SoonImage2 = loadImageFromFile("SoonImages/2.jpg");
		byte[] SoonImage3 = loadImageFromFile("SoonImages/3.jpg");

		SoonMovie soonMovie1 = new SoonMovie(1, "Blink Twice","צאי מזה" ,"Zoë Kravitz", 2024, SoonImage1, "Mystery" ,"When tech billionaire Slater King meets cocktail waitress Frida at his fundraising gala, he invites her to join him and his friends on a dream vacation on his private island. As strange things start to happen, Frida questions her reality.",
				"Naomi Ackie, Channing Tatum, Alia Shawkat", "1h42m");
		session.save(soonMovie1);
		session.flush();

		SoonMovie soonMovie2 = new SoonMovie(2, "African Giants","ענקים אפריקאים", "Omar S. Kamara", 2024, SoonImage2, "Drama", "Over a weekend visit in Los Angeles, two first-generation Sierra Leonean American brothers navigate the changing dynamics of brotherhood after a surprise announcement.",
						"Tanyell Waivers, Kathleen Kenny, Jerry Hernandez", "1h42m");
		session.save(soonMovie2);
		session.flush();

		SoonMovie soonMovie3 = new SoonMovie(3, "The Killer's Game","המשחק הרוצח" , "J.J. Perry", 2024, SoonImage3, "Action", "A veteran assassin is diagnosed with a life-threatening illness and authorizes a kill on himself. After ordering the kill, an army of former colleagues pounce and a new piece of information comes to light. Insanity ensues.",
				"Sofia Boutella, Lucy Cork, Dave Bautista", "1h44m");
		session.save(soonMovie3);
		session.flush();
	}

	private static void generateHomeMovies(Session session) throws Exception {
		byte[][] homeImages = new byte[6][];  // מערך לאחסון התמונות של סרטי הבית
		for (int i = 1; i <= 6; i++) {       //טוענים את התמונות ושומרים אותם במערך
			homeImages[i - 1] = loadImageFromFile(String.format("forHome_images/%d.jpg", i));
		}

		HomeMovie homeMovie1 = new HomeMovie(1, "Despicable Me 4", "גנוב על החיים", "Chris Renaud", 2024, homeImages[0], "https://Despicable_Me_4_Movie_link.com","Adventure","Gru, Lucy, Margo, Edith, and Agnes welcome a new member to the family, Gru Jr., who is intent on tormenting his dad. Gru faces a new nemesis in Maxime Le Mal and his girlfriend Valentina, and the family is forced to go on the run."
				,"Steve Carell, Kristen Wiig, Pierre Coffin","1h 34m");
		session.save(homeMovie1);
		session.flush();

		HomeMovie homeMovie2 = new HomeMovie(2, "Bad Boys", "בחורים רעים", "Adil El Arbi", 2024, homeImages[1], "https://Bad_Boys_Movie_link.com","Comedy","When their late police captain gets linked to drug cartels, wisecracking Miami cops Mike Lowrey and Marcus Burnett embark on a dangerous mission to clear his name."
				,"Will Smith, Martin Lawrence, Vanessa Hudgens","1h 55m");
		session.save(homeMovie2);
		session.flush();

		HomeMovie homeMovie3 = new HomeMovie(3, "Wire Room", "מלכוד בחדר", "Matt Eskandari", 2022, homeImages[2], "https://Wire_Room_Movie_link.com","Action","While on wire room duty, a federal agent listens in as the target is attacked in his home by a hit squad. Without burning the wire, he must protect the investigation and the target's life from the confines of a room fifty miles away."
				,"Kevin Dillon, Bruce Willis, Oliver Trevena","1h 36m");
		session.save(homeMovie3);
		session.flush();

		HomeMovie homeMovie4 = new HomeMovie(4, "Mission Impossible", "משימה לא אפשרית", "Christopher McQuarrie", 2023, homeImages[3], "https://Mission_Impossible_Movie_link.com","Comedy","Ethan Hunt and his IMF team must track down a dangerous weapon before it falls into the wrong hands."
				,"Tom Cruise, Hayley Atwell, Ving Rhames","2h 43m");
		session.save(homeMovie4);
		session.flush();

		HomeMovie homeMovie5 = new HomeMovie(5, "Deadpool Wolverine", "דדפול & וולברין ", "Shawn Levy", 2024, homeImages[4], "https://Deadpool_Wolverine_Movie_link.com","Adventure","Deadpool is offered a place in the Marvel Cinematic Universe by the Time Variance Authority, but instead recruits a variant of Wolverine to save his universe from extinction"
				,"Ryan Reynolds, Hugh Jackman, Emma Corrin","2h 8m");
		session.save(homeMovie5);
		session.flush();

		HomeMovie homeMovie6 = new HomeMovie(6, "This Time Next Year", "מזל שנפגשנו", "Nick Moore", 2024, homeImages[5], "https://This_Time_Next_Year_Movie_link.com","Comedy","Minnie and Quinn are born on the same day, one minute apart. Their lives may begin together, but their worlds couldn't be more different. Years later they find themselves together again. Maybe it's time to take a chance on love."
				,"Sophie Cookson, Lucien Laviscount, Golda Rosheuvel","1h 56m");
		session.save(homeMovie6);
		session.flush();

	}
	private static void generateBranches(Session session) throws Exception {
		Movie movie1 = session.get(Movie.class, 1);
		Movie movie2 = session.get(Movie.class, 2);
		Movie movie3 = session.get(Movie.class, 3);
		Movie movie4 = session.get(Movie.class, 4);
		Movie movie5 = session.get(Movie.class, 5);
		Movie movie6 = session.get(Movie.class, 6);
		Movie movie7 = session.get(Movie.class, 7);
		Movie movie8 = session.get(Movie.class, 8);
		Movie movie9 = session.get(Movie.class, 9);
		Movie movie10 = session.get(Movie.class, 10);

		Branch haifaCinema = new Branch(1,"Haifa Cinema","Haifa");

		haifaCinema.addMovie(movie2, movie4, movie6, movie8, movie10);
		session.save(haifaCinema);

		Branch telAvivCinema = new Branch(2,"Tel Aviv Cinema","Tel Aviv");
		telAvivCinema.addMovie(movie1, movie3, movie5, movie7, movie9);
		session.save(telAvivCinema);

		Branch eilatCinema = new Branch(3,"Eilat Cinema","Eilat");
		eilatCinema.addMovie(movie1, movie2, movie5, movie6, movie9, movie10);
		session.save(eilatCinema);

		Branch karmielCinema = new Branch(4,"Karmiel Cinema","Karmiel");
		karmielCinema.addMovie(movie3, movie4, movie6, movie8, movie9, movie10);
		session.save(karmielCinema);

		Branch jerusalemCinema = new Branch(5,"Jerusalem Cinema","Jerusalem");
		jerusalemCinema.addMovie(movie1, movie2, movie3, movie7, movie8, movie9, movie10);
		session.save(jerusalemCinema);

		session.flush();
	}

	private static void generateScreenings(Session session) throws Exception {


		Branch haifaCinema = session.get(Branch.class, 1);
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);

		Movie movie1 = session.get(Movie.class, 1);
		Movie movie2 = session.get(Movie.class, 2);
		Movie movie3 = session.get(Movie.class, 3);
		Movie movie4 = session.get(Movie.class, 4);
		Movie movie5 = session.get(Movie.class, 5);
		Movie movie6 = session.get(Movie.class, 6);
		Movie movie7 = session.get(Movie.class, 7);
		Movie movie8 = session.get(Movie.class, 8);
		Movie movie9 = session.get(Movie.class, 9);
		Movie movie10 = session.get(Movie.class, 10);

		List<LocalDateTime> screeningTimes = Arrays.asList(
				LocalDateTime.of(2024, 9, 24, 18, 30),
				LocalDateTime.of(2024, 9, 24, 20, 30),
				LocalDateTime.of(2024, 9, 24, 22, 00),
				LocalDateTime.of(2024, 9, 25, 18, 30),
				LocalDateTime.of(2024, 9, 25, 20, 30),
				LocalDateTime.of(2024, 9, 25, 23, 00),
				LocalDateTime.of(2024, 9, 26, 17, 00),
				LocalDateTime.of(2024, 9, 26, 20, 00),
				LocalDateTime.of(2024, 9, 26, 23, 00),
				LocalDateTime.of(2024, 9, 27, 17, 00),
				LocalDateTime.of(2024, 9, 27, 20, 00),
				LocalDateTime.of(2024, 9, 27, 23, 00),
				LocalDateTime.of(2024, 9, 28, 18, 00),
				LocalDateTime.of(2024, 9, 28, 20, 00),
				LocalDateTime.of(2024, 9, 28, 23, 00),
				LocalDateTime.of(2024, 9, 29, 18, 00),
				LocalDateTime.of(2024, 9, 29, 22, 00),
				LocalDateTime.of(2024, 9, 29, 23, 30),
				LocalDateTime.of(2024, 9, 30, 17, 00),
				LocalDateTime.of(2024, 9, 30, 19, 00),
				LocalDateTime.of(2024, 9, 30, 22, 30)
		);


		for (LocalDateTime time : screeningTimes) {
			movie1.addScreening(time, jerusalemCinema);
			movie1.addScreening(time, telAvivCinema);
			movie1.addScreening(time, eilatCinema);

			movie2.addScreening(time, haifaCinema);
			movie2.addScreening(time, jerusalemCinema);
			movie2.addScreening(time, eilatCinema);

			movie3.addScreening(time, karmielCinema);
			movie3.addScreening(time, jerusalemCinema);
			movie3.addScreening(time, telAvivCinema);

			movie4.addScreening(time, karmielCinema);
			movie4.addScreening(time, haifaCinema);

			movie5.addScreening(time, telAvivCinema);
			movie5.addScreening(time, eilatCinema);

			movie6.addScreening(time, haifaCinema);
			movie6.addScreening(time, karmielCinema);
			movie6.addScreening(time, eilatCinema);

			movie7.addScreening(time, jerusalemCinema);
			movie7.addScreening(time, telAvivCinema);

			movie8.addScreening(time, haifaCinema);
			movie8.addScreening(time, karmielCinema);
			movie8.addScreening(time, jerusalemCinema);

			movie9.addScreening(time, telAvivCinema);
			movie9.addScreening(time, eilatCinema);
			movie9.addScreening(time, karmielCinema);
			movie9.addScreening(time, jerusalemCinema);

			movie10.addScreening(time, karmielCinema);
			movie10.addScreening(time, jerusalemCinema);
			movie10.addScreening(time, haifaCinema);
			movie10.addScreening(time, eilatCinema);

		}

		List<Movie> movies = Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8, movie9, movie10);
		for (Movie movie : movies) {
			session.save(movie);
		}
		session.flush();


		Movie movie14 = session.get(Movie.class, 14);
		Movie movie15 = session.get(Movie.class, 15);
		Movie movie16 = session.get(Movie.class, 16);
		Movie movie17 = session.get(Movie.class, 17);
		Movie movie18 = session.get(Movie.class, 18);
		Movie movie19 = session.get(Movie.class, 19);


		List<LocalDateTime> HomeScreeningTimes = Arrays.asList(
				LocalDateTime.of(2024, 9, 24, 15, 00),
				LocalDateTime.of(2024, 9, 24, 19, 00),
				LocalDateTime.of(2024, 9, 24, 23, 00),
				LocalDateTime.of(2024, 9, 25, 15, 00),
				LocalDateTime.of(2024, 9, 25, 19, 00),
				LocalDateTime.of(2024, 9, 25, 23, 00),
				LocalDateTime.of(2024, 9, 26, 15, 00),
				LocalDateTime.of(2024, 9, 26, 19, 00),
				LocalDateTime.of(2024, 9, 26, 23, 00),
				LocalDateTime.of(2024, 9, 27, 15, 00),
				LocalDateTime.of(2024, 9, 27, 19, 00),
				LocalDateTime.of(2024, 9, 27, 23, 00),
				LocalDateTime.of(2024, 9, 28, 15, 00),
				LocalDateTime.of(2024, 9, 28, 19, 00),
				LocalDateTime.of(2024, 9, 28, 23, 00),
				LocalDateTime.of(2024, 9, 29, 15, 00),
				LocalDateTime.of(2024, 9, 29, 19, 00),
				LocalDateTime.of(2024, 9, 29, 23, 00),
				LocalDateTime.of(2024, 9, 30, 15, 00),
				LocalDateTime.of(2024, 9, 30, 19, 00),
				LocalDateTime.of(2024, 9, 30, 23, 00)
		);
		for (LocalDateTime time : HomeScreeningTimes) {
			movie14.addScreening(time, null);
			movie15.addScreening(time, null);
			movie16.addScreening(time, null);
			movie17.addScreening(time, null);
			movie18.addScreening(time, null);
			movie19.addScreening(time, null);
		}

		session.save(movie14);
		session.save(movie15);
		session.save(movie16);
		session.save(movie17);
		session.save(movie18);
		session.save(movie19);

		session.flush();
	}

    //This code is for testing only
	private static void generateCustomers(Session session) {
		List<Purchase> purchases1 = new ArrayList<>();
		List<Purchase> purchases2 = new ArrayList<>();

		Customer customer1 = new Customer(123123123,"dema omar", "john.doe@example.com", "0501234567", purchases1,false);
		Customer customer2 = new Customer(123456789,"shada ghanem", "jane.smith@example.com", "0527654321", purchases2,false);

		session.save(customer1);
		session.save(customer2);
		session.flush();
	}





	private static byte[] loadImageFromFile(String relativePath) throws IOException {
		InputStream inputStream = SimpleServer.class.getClassLoader().getResourceAsStream(relativePath);
		if (inputStream == null) {
			throw new FileNotFoundException("File not found: " + relativePath);
		}
		return inputStream.readAllBytes();
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

	private static List<Branch> getAllBranches(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Branch> query = builder.createQuery(Branch.class);
		query.from(Branch.class);
		return session.createQuery(query).getResultList();
	}

	private static HeadManager getHeadManager(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<HeadManager> query = builder.createQuery(HeadManager.class);
		query.from(HeadManager.class);
		return session.createQuery(query).getSingleResult();
	}

	private static List<BranchManager> getBranchManager(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<BranchManager> query = builder.createQuery(BranchManager.class);
		query.from(BranchManager.class);
		return session.createQuery(query).getResultList();
	}

	private static ContentManager getContentManager(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<ContentManager> query = builder.createQuery(ContentManager.class);
		query.from(ContentManager.class);
		return session.createQuery(query).getSingleResult();
	}

	private static CustomerServiceWorker getCustomerServiceWorker(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<CustomerServiceWorker> query = builder.createQuery(CustomerServiceWorker.class);
		query.from(CustomerServiceWorker.class);
		return session.createQuery(query).getSingleResult();
	}

	private static List<Employee> getAllEmployees(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Employee> query = builder.createQuery(Employee.class);
		query.from(Employee.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Screening> getScreeningsForMovie(Session session, int movieId) {
		Movie movie = session.get(Movie.class, movieId);
		return new ArrayList<>(movie.getScreenings());
	}

	private static List<Customer> getAllCustomers(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		query.from(Customer.class);
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
			} else if (msgString.equals("login")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					String userNameString = message.getUserName();
					String passwordString = message.getPassword();
					List<Employee> employees = getAllEmployees(session);
					NewMessage newMessage;
					int flag = 0;
					for (Employee employee : employees) {
						flag = 0;
						if (userNameString.equals(employee.getUsername()) && passwordString.equals(employee.getPassword())) {
							if (employee.isOnline()) {
								newMessage = new NewMessage("Alreadylogin");
							} else {
								employee.setIsOnline(true);
								newMessage = new NewMessage(employee, "login");
							}
							client.sendToClient(newMessage);
							session.getTransaction().commit();
							break;
						} else flag = 1;
					}
					if (flag == 1) {
						newMessage = new NewMessage("loginDenied");
						client.sendToClient(newMessage);
						session.getTransaction().commit();
					}

				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("logOut")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Employee> employees = getAllEmployees(session);
					Employee employee = message.getEmployee();
					for (Employee emp : employees) {
						if (emp.getId() == employee.getId()) {
							emp.setIsOnline(false);
						}
					}
					NewMessage newMessage = new NewMessage("logOut");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("screeningTimesRequest")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie requestedMovie = message.getMovie();
					List<Screening> screenings = session.createQuery(
									"from Screening where movie.id = :movieId", Screening.class)
							.setParameter("movieId", requestedMovie.getId())
							.getResultList();
					NewMessage newMessage = new NewMessage(screenings, "screeningTimes");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("removeHomeMovie")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					HomeMovie movie = (HomeMovie) message.getObject();
					HomeMovie movieToDelete = session.get(HomeMovie.class, movie.getId());
					session.remove(movieToDelete);
					List<Movie> movies = getAllMovies(session);
					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage newMessage = new NewMessage(homeMovies, "homeMovies");
					sendToAllClients(newMessage);
					NewMessage newMessage3 = new NewMessage(movies, "movies");
					client.sendToClient(newMessage3);
					NewMessage newMessage2 = new NewMessage("movieRemoved");
					client.sendToClient(newMessage2);
					NewMessage newMessage4 = new NewMessage(movie.getId(), "movieNotAvailable");
					sendToAllClients(newMessage4);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("removeCinemaMovie")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie movie = (Movie) message.getObject();
					Movie movieToDelete = session.get(Movie.class, movie.getId());
					String branch = message.getBranchName();
					if(branch.equals("All") || movieToDelete.getBranches().size() == 1){
						session.remove(movieToDelete);
						NewMessage newMessage4 = new NewMessage(movie.getId(), "movieNotAvailable");
						sendToAllClients(newMessage4);
						NewMessage newMessage2 = new NewMessage("movieRemoved");
						client.sendToClient(newMessage2);
					}
					else {
						Movie movieToDeleteBranch = session.get(Movie.class, movie.getId());
						List<Branch> branches = getAllBranches(session);
						List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
						for (Screening screening : screenings) {
							if (screening.getBranch().getName().equals(branch)) {
								movieToDeleteBranch.getScreenings().remove(screening);
								session.remove(screening);
							}
						}
						for(Branch x : branches) {
							if (x.getName().equals(branch)) {
								x.getMovies().remove(movie);
								movieToDeleteBranch.getBranches().remove(x);
							}
						}
						if(movieToDeleteBranch != null) {
							List<Screening> screeningsForMovie = getScreeningsForMovie(session, movie.getId());
							NewMessage newMessage3 = new NewMessage(screeningsForMovie, "screeningTimes");
							sendToAllClients(newMessage3);
						}
						NewMessage newMessage2 = new NewMessage("movieRemoved");
						client.sendToClient(newMessage2);
					}
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage = new NewMessage(movies, "movies");
					sendToAllClients(newMessage);

					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("addCinemaMovie")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים לצפייה מהבית
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie movie = (Movie) message.getObject();
					List<Branch> branches = getAllBranches(session);
					List<Branch> movieBranches = message.getBranches();
					List<Branch> newBranches = new ArrayList<>();
					List<Screening> screenings = new ArrayList<>();
					List<LocalDateTime> times = message.getDateTimes();
					int i;
					for(Branch x : branches){
						i = 0;
						for(Branch y : movieBranches) {
							if(x.getName().equals(y.getName())){
								if (!newBranches.contains(x))
									newBranches.add(x);
								if(!x.getMovies().contains(movie))
									x.addMovie(movie);
								session.save(x);
								Screening screening = new Screening(times.get(i), movie, x);
								screenings.add(screening);
								movie.addScreening(times.get(i), x);
							}
							i++;
						}
					}
					/*for(Branch y : movieBranches) {
						String hql = "from Branch where name = :branchName";
						Query<Branch> query = session.createQuery(hql, Branch.class);
						query.setParameter("branchName", y.getName());
						Branch branch = query.getSingleResult();
						branch.getMovies().add(movie);
						session.save(branch);
						newBranches.add(branch);
						Screening screening = new Screening(times.get(i), movie, branch);
						screenings.add(screening);
						i++;
					}*/
					movie.setBranches(newBranches);
					//movie.setScreenings(screenings);
					session.save(movie);
					session.flush();
					NewMessage newMessage = new NewMessage("movieAdded");  // שליחת רשימת הסרטים לבית ללקוח עם המחרוזת "homeMovies"
					client.sendToClient(newMessage);
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage1 = new NewMessage(movies, "movies");
					sendToAllClients(newMessage1);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("addHomeMovie")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים לצפייה מהבית
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					HomeMovie homeMovie = (HomeMovie) message.getObject();
					session.save(homeMovie);
					NewMessage newMessage = new NewMessage("movieAdded");  // שליחת רשימת הסרטים לבית ללקוח עם המחרוזת "homeMovies"
					client.sendToClient(newMessage);
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage1 = new NewMessage(movies, "movies");
					client.sendToClient(newMessage1);
					//sendToAllClients(newMessage1);
					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage newMessage2 = new NewMessage(homeMovies, "homeMovies");
					sendToAllClients(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("removeScreening")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();

					// קבלת הנתונים מההודעה
					ScreeningData data = (ScreeningData) message.getObject();
					Movie movie = session.get(Movie.class, data.getMovieId());

					if(movie instanceof HomeMovie){
						HomeMovie homeMovie = session.get(HomeMovie.class, movie.getId());
						LocalDateTime screeningTime = data.getScreeningTime();

						// חיפוש ההקרנה להסרה
						Screening screeningToRemove = movie.getScreenings().stream()
								.filter(screening -> screening.getScreeningTime().equals(screeningTime))
								.findFirst()
								.orElse(null);

						if (screeningToRemove != null) {
							movie.getScreenings().remove(screeningToRemove); // הסרת ההקרנה מהסרט
							session.remove(screeningToRemove); // הסרה מהמסד
						} else {
							System.err.println("Screening not found.");
						}
						session.save(movie); ///////////////////////
					} else {
					Branch branch = session.get(Branch.class, data.getBranchId());
					LocalDateTime screeningTime = data.getScreeningTime();

					if (movie == null || branch == null) {
						System.err.println("Movie or branch not found.");
						return;
					}

					// חיפוש ההקרנה להסרה
					Screening screeningToRemove = movie.getScreenings().stream()
							.filter(screening -> screening.getScreeningTime().equals(screeningTime) && screening.getBranch().equals(branch))
							.findFirst()
							.orElse(null);

					if (screeningToRemove != null) {
						movie.getScreenings().remove(screeningToRemove); // הסרת ההקרנה מהסרט
						branch.getScreenings().remove(screeningToRemove); // הסרת ההקרנה מהסניף
						session.remove(screeningToRemove); // הסרה מהמסד
					} else {
						System.err.println("Screening not found.");
					}
					session.save(movie); ///////////////////////
					}

					// שליחת עדכון לכל הלקוחות
					List<Movie> movies = getAllMovies(session);
					NewMessage newMessage = new NewMessage(movies, "movies");
					sendToAllClients(newMessage);

					List<HomeMovie> homeMovies = getAllHomeMovies(session);
					NewMessage homeMoviesMessage = new NewMessage(homeMovies, "homeMovies");
					sendToAllClients(homeMoviesMessage);

					NewMessage newMessage2 = new NewMessage("screeningRemoved");
					client.sendToClient(newMessage2);

					List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
					NewMessage newMessage3 = new NewMessage(screenings, "screeningTimes");
					sendToAllClients(newMessage3);

					session.getTransaction().commit();

				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			}
			else if (msgString.equals("addScreening")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();

					// Extract screening data from the message
					ScreeningData data = (ScreeningData) message.getObject();
					Movie movie = session.get(Movie.class, data.getMovieId());
					Branch branch = data.getBranchId() != null ? session.get(Branch.class, data.getBranchId()) : null;
					LocalDateTime screeningTime = data.getScreeningTime();

					// Check if the screening already exists
					boolean screeningExists = movie.getScreenings().stream()
							.anyMatch(screening -> screening.getScreeningTime().equals(screeningTime) && (branch == null || screening.getBranch().equals(branch)));

					if (!screeningExists) {
						// Create and add the new screening
						Screening newScreening = new Screening();
						newScreening.setMovie(movie);
						newScreening.setScreeningTime(screeningTime);
						newScreening.setBranch(branch);
						session.persist(newScreening);
						movie.getScreenings().add(newScreening);
						if (branch != null) {
							branch.getScreenings().add(newScreening);
						}
						session.getTransaction().commit();

						// Load updated movie list and send to all clients
						List<Movie> movies = getAllMovies(session);
						NewMessage updateMessage = new NewMessage(movies, "movies");
						sendToAllClients(updateMessage);  // שליחת העדכון לכל הלקוחות

						List<HomeMovie> homeMovies = getAllHomeMovies(session);
						NewMessage homeMoviesMessage = new NewMessage(homeMovies, "homeMovies");
						sendToAllClients(homeMoviesMessage);

						// Send success message to the client who initiated the request
						NewMessage newMessage = new NewMessage("screeningAdded");
						client.sendToClient(newMessage);

						List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
						NewMessage newMessage3 = new NewMessage(screenings, "screeningTimes");
						sendToAllClients(newMessage3);
					} else {
						session.getTransaction().rollback();
						NewMessage newMessage = new NewMessage("Screening already exists");
						client.sendToClient(newMessage);
					}
				} catch (Exception exception) {
					System.err.println("An error occurred while adding a screening: " + exception.getMessage());
				}
			} else if (msgString.equals("editScreening")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();

					// Extract screening data from the message
					ScreeningData data = (ScreeningData) message.getObject();
					Movie movie = session.get(Movie.class, data.getMovieId());
					Branch branch = data.getBranchId() != null ? session.get(Branch.class, data.getBranchId()) : null;
					LocalDateTime newScreeningTime = data.getScreeningTime();

					// Attempt to find the existing screening to update
					Screening screeningToUpdate = movie.getScreenings().stream()
							.filter(screening -> screening.getBranch().equals(branch))
							.filter(screening -> !screening.getScreeningTime().equals(newScreeningTime)) // Ensure we're not comparing with the new time
							.findFirst().orElse(null);

					if (screeningToUpdate != null) {
						// Update the screening time
						screeningToUpdate.setScreeningTime(newScreeningTime);
						session.update(screeningToUpdate);

						// Commit transaction
						session.getTransaction().commit();

						// Load updated movie list and send to all clients
						List<Movie> movies = getAllMovies(session);
						NewMessage updateMessage = new NewMessage(movies, "movies");
						sendToAllClients(updateMessage);

						List<HomeMovie> homeMovies = getAllHomeMovies(session);
						NewMessage homeMoviesMessage = new NewMessage(homeMovies, "homeMovies");
						sendToAllClients(homeMoviesMessage);

						// Send success message to the client who initiated the request
						NewMessage successMessage = new NewMessage("Screening updated");
						client.sendToClient(successMessage);

						List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
						NewMessage screeningTimesMessage = new NewMessage(screenings, "screeningTimes");
						sendToAllClients(screeningTimesMessage);
					} else {
						session.getTransaction().rollback();
						NewMessage errorMessage = new NewMessage("Screening not found");
						client.sendToClient(errorMessage);
					}
				} catch (Exception exception) {
					System.err.println("An error occurred while editing a screening: " + exception.getMessage());
				}
			}
			else if (msgString.equals("loginCustomer")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					int idNumber = message.getId();
					List<Customer> customers = getAllCustomers(session);
					NewMessage newMessage;
					boolean found = false;

					for (Customer customer : customers) {
						if (idNumber == customer.getId()) {
							found = true;
							if (customer.isLoggedIn()) {
								newMessage = new NewMessage("AlreadyloginCustomer");
							} else {
								customer.setLoggedIn(true);
								newMessage = new NewMessage(customer, "loginCustomer");
							}
							client.sendToClient(newMessage);
							session.getTransaction().commit();
							break;
						}
					}

					if (!found) {
						newMessage = new NewMessage("loginDeniedCustomer");
						client.sendToClient(newMessage);
						session.getTransaction().commit();
					}

				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			}
			else if (msgString.equals("logOutCustomer")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Customer customer = (Customer) message.getObject();
					customer.setLoggedIn(false);  // log out
					session.saveOrUpdate(customer);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred while logging out.");
					exception.printStackTrace();
				}
			}




		}
		 catch (IOException e) {
			e.printStackTrace();
		}
	}
}

