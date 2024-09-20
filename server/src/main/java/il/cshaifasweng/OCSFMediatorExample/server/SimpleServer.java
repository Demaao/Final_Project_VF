package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.CardsPage;
import il.cshaifasweng.OCSFMediatorExample.entities.Branch;
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
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.util.*;


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
		configuration.addAnnotatedClass(Hall.class);
		configuration.addAnnotatedClass(Screening.class);
		configuration.addAnnotatedClass(HeadManager.class); //////////////////
		configuration.addAnnotatedClass(BranchManager.class); /////////////
		configuration.addAnnotatedClass(ContentManager.class); /////////////////
		configuration.addAnnotatedClass(CustomerServiceWorker.class); /////////////
		configuration.addAnnotatedClass(Complaint.class); ///////////////////////////////////
		configuration.addAnnotatedClass(ChangePriceRequest.class);  //////////////////////
		configuration.addAnnotatedClass(Cinema.class); ///////////////////////////////////////
		configuration.addAnnotatedClass(Customer.class); //for testing only
		configuration.addAnnotatedClass(Purchase.class);  //for testing only
		configuration.addAnnotatedClass(HomeMoviePurchase.class);
		configuration.addAnnotatedClass(Card.class);/////////////////////////////////
		configuration.addAnnotatedClass(Notification.class);

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
			generateHalls(session);
			generateScreenings(session);
			generateHeadManager(session);
			generateBranchManager(session);
			generateContentManager(session);
			generateCustomerServiceWorker(session);
			generateCinema(session);  ///////////////////////////////////
			generateComplaints(session);
			generateChangePriceRequest(session); //////////////////////////////////////////////
//			generateHomeMoviePurchases(session);
			generateCustomersAndPurchases(session);
			generateCards(session);
			generateNotifications(session);
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateCinema(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Cinema cinema = new Cinema(100, 90, 1000);
		Cinema.cinema = cinema;
		session.save(cinema);
		session.flush();
	}

	private static void generateComplaints(Session session) throws Exception {
		Complaint complaint1 = new Complaint("Dema Omar", 123123123L, "1234567890",
				"Purchases", "john@example.com", "Overcharged for my last order.", LocalDateTime.of(2024, 9, 11, 20, 30),
				"Thank you for reaching out,\nyou will be refunded accordingly.", false);
		Complaint complaint2 = new Complaint("Jane Smith", 987654321L, "0987654321",
				"Movie link", "jane@example.com", "Unable to access the link.", LocalDateTime.of(2024, 9, 11, 19, 56),
				"Thank you for reaching out,\nthe link will be activated at the screening's scheduled time.", false);
		Complaint complaint3 = new Complaint("Alice Johnson", 555666777L, "1122334455",
				"Other", "alice@example.com", "Loved the new movie selection!", LocalDateTime.of(2024, 9, 9, 11, 32),
				"Thank you for reaching out,\nnew movies are coming out soon!", true);
		Complaint complaint4 = new Complaint("Shada Ghanem", 123456789L, "9988776655",
				"Screening", "bob@example.com", "When will the new movie be released?", LocalDateTime.of(2024, 9, 5, 22, 4),
				"Thank you for reaching out,\nthe release date for the new movie has not yet been announced.\nPlease stay tuned for future updates.", true);

		session.save(complaint1);
		session.save(complaint2);
		session.save(complaint3);
		session.save(complaint4);
		session.flush();
	}

	private static void generateCustomersAndPurchases(Session session) {

		List<Purchase> purchases1 = new ArrayList<>();
		List<Purchase> purchases2 = new ArrayList<>();

		Customer customer1 = new Customer(123123123, "Dima om", "dima.oma@example.com", "0501234567", purchases1, false);
		Customer customer2 = new Customer(123456789, "Shada gh", "shada.gha@example.com", "0527654321", purchases2, false);

		//Create purchases and add them to customers
		Purchase purchase1 = new Purchase("Movie Ticket",LocalDateTime.of(2024,9,15,12,40), "Credit Card", 200.00, customer1,"haifaCinema",2,"Two tickets were ordered for movie:Inside out. at the Haifa branch cinema, Hall number: 2, seats numbers: 12,13. This order has been successfully confirmed.");
		Purchase purchase2 = new Purchase("Movie Card",  LocalDateTime.of(2024,9,18,10,15), "Cash", 800, customer1,"telAvivCinema",1,"A cinema card was ordered containing 20 tickets, which allows access to movie screenings at all our branches based on available seating.");
		Purchase purchase3 = new Purchase("Movie Link",  LocalDateTime.of(2024,9,23,21,10),"Credit Card", 120, customer2,"Movie link was ordered for the movie:Wire Room.Viewing is limited to the screening time you selected.");

		//Adding purchases to the list
		customer1.getPurchaseHistory().add(purchase1);
		customer1.getPurchaseHistory().add(purchase2);
		customer2.getPurchaseHistory().add(purchase3);

		//Saving customers
		session.save(customer1);
		session.save(customer2);
		session.flush();

	}

	private static void generateCards(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Customer customer1 = session.get(Customer.class, 123123123);
		Customer customer2 = session.get(Customer.class, 123456789);
		Card card1 = new Card("Card", LocalDateTime.of(2024, 9, 11, 20, 30), "Credit Card"
				, 1000, customer1, null, null, 1, 15, "Regular");
		session.save(card1);
		Card card2 = new Card("Card", LocalDateTime.of(2024, 8, 8, 11, 11), "Credit Card"
				, 1500, customer2, null,null, 1,4, "VIP");
		session.save(card2);
		session.flush();
	}


	private static void generateNotifications(Session session) throws Exception {  ///////////////////////////////////////////////////////////////////
		Customer customer1 = session.get(Customer.class, 123123123);
		Customer customer2 = session.get(Customer.class, 123456789);
		Notification noti1 = new Notification("New Movie" ,"Watch Barbie today in Haifa Cinema!\nFor more details check the movies page",
				LocalDateTime.of(2024, 9, 24, 11, 11),"Unread",customer1);
		session.save(noti1);
		Notification noti2 = new Notification("New Movie" ,"Watch The Joker today in Haifa Cinema!\nFor more details check the movies page",
				LocalDateTime.of(2024, 9, 24, 11, 11),"Unread",customer2);
		session.save(noti2);
		session.flush();
	}
//
//	private static void generateHomeMoviePurchases(Session session) {
//		HomeMovie homeMovie = new HomeMovie();
//		session.save(homeMovie);
//		session.flush();
//	}


	private static void generateChangePriceRequest(Session session) throws Exception {
		Cinema cinema = session.get(Cinema.class, 1);
		ChangePriceRequest changePriceRequest1 = new ChangePriceRequest(cinema.getCardPrice(), 110, "Card", "Denied", LocalDateTime.of(2024, 9, 5, 22, 4));
		session.save(changePriceRequest1);
		session.flush();

		ChangePriceRequest changePriceRequest2 = new ChangePriceRequest(80, 100, "Ticket", "Confirmed", LocalDateTime.of(2024, 9, 1, 22, 30));
		session.save(changePriceRequest2);
		session.flush();

		ChangePriceRequest changePriceRequest3 = new ChangePriceRequest(cinema.getLinkTicketPrice(), 80, "Link Ticket", "Received", LocalDateTime.of(2024, 8, 8, 9, 55));
		session.save(changePriceRequest3);
		session.flush();
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

	private static void generateHalls(Session session) throws Exception {

		Branch haifaCinema = session.get(Branch.class, 1);
		Branch telAvivCinema = session.get(Branch.class, 2);
		Branch eilatCinema = session.get(Branch.class, 3);
		Branch karmielCinema = session.get(Branch.class, 4);
		Branch jerusalemCinema = session.get(Branch.class, 5);


		Hall hall1 = new Hall(1,4, 5, 18, "1",jerusalemCinema);
		jerusalemCinema.getHalls().add(hall1); // הוספת האולם לסניף
		session.save(hall1);


		Hall hall2 = new Hall(2,5, 5, 25, "2",haifaCinema);
		haifaCinema.getHalls().add(hall2); // הוספת האולם לסניף
		session.save(hall2);

		Hall hall3 = new Hall(3,6, 6, 36, "3",haifaCinema);
		haifaCinema.getHalls().add(hall3); // הוספת האולם לסניף
		session.save(hall3);

		Hall hall4 = new Hall(4,5, 6, 28, "4",jerusalemCinema);
		jerusalemCinema.getHalls().add(hall4); // הוספת האולם לסניף
		session.save(hall4);

		Hall hall5 = new Hall(5,3, 6, 18, "5",haifaCinema);
		haifaCinema.getHalls().add(hall5);
		session.save(hall5);

		Hall hall6 = new Hall(6,6, 5, 30, "6",telAvivCinema);
		telAvivCinema.getHalls().add(hall6);
		session.save(hall6);

		Hall hall7 = new Hall(7,5, 5, 23, "7",jerusalemCinema);
		jerusalemCinema.getHalls().add(hall7);
		session.save(hall7);

		Hall hall8 = new Hall(8,5, 4, 20, "8",karmielCinema);
		karmielCinema.getHalls().add(hall8);
		session.save(hall8);

		Hall hall9 = new Hall(9,4, 5, 18, "9",telAvivCinema);
		telAvivCinema.getHalls().add(hall9);
		session.save(hall9);

		Hall hall10 = new Hall(10,5, 5, 23, "10",karmielCinema);
		karmielCinema.getHalls().add(hall10);
		session.save(hall10);


		Hall hall11 = new Hall(11,4, 4, 16, "11",karmielCinema);
		karmielCinema.getHalls().add(hall11);
		session.save(hall11);

		Hall hall12 = new Hall(12,4, 5, 18, "12",eilatCinema);
		eilatCinema.getHalls().add(hall12);
		session.save(hall12);

		Hall hall13 = new Hall(13,5, 5, 25, "13",eilatCinema);
		eilatCinema.getHalls().add(hall13);
		session.save(hall13);

		Hall hall14 = new Hall(14,6, 6, 36, "14",eilatCinema);
		eilatCinema.getHalls().add(hall14);
		session.save(hall14);

		Hall hall15 = new Hall(15,5, 6, 28, "15",telAvivCinema);
		telAvivCinema.getHalls().add(hall15);
		session.save(hall15);

		Hall hall16 = new Hall(16,5, 6, 28, "16",jerusalemCinema);
		jerusalemCinema.getHalls().add(hall16);
		session.save(hall16);

		Hall hall17 = new Hall(17,6, 5, 30, "17",karmielCinema);
		karmielCinema.getHalls().add(hall17);
		session.save(hall17);

		Hall hall18 = new Hall(18,5, 5, 23, "18",karmielCinema);
		karmielCinema.getHalls().add(hall18);
		session.save(hall18);

		Hall hall19 = new Hall(19,5, 4, 20, "19",telAvivCinema);
		telAvivCinema.getHalls().add(hall19);
		session.save(hall19);


		Hall hall20 = new Hall(20,4, 5, 18, "20",telAvivCinema);
		telAvivCinema.getHalls().add(hall20);
		session.save(hall20);


		Hall hall21 = new Hall(21,5, 5, 23, "21",karmielCinema);
		karmielCinema.getHalls().add(hall21);
		session.save(hall21);

		Hall hall22 = new Hall(22,4, 5, 18, "22",jerusalemCinema);
		jerusalemCinema.getHalls().add(hall22);
		session.save(hall22);


		Hall hall23 = new Hall(23,5, 5, 25, "23",haifaCinema);
		haifaCinema.getHalls().add(hall23);
		session.save(hall23);

		Hall hall24 = new Hall(24,6, 6, 36, "24",eilatCinema);
		eilatCinema.getHalls().add(hall24);
		session.save(hall24);

		Hall hall25 = new Hall(25,5, 6, 28, "25",eilatCinema);
		eilatCinema.getHalls().add(hall25);
		session.save(hall25);

		Hall hall26 = new Hall(26,3, 6, 18, "26",jerusalemCinema);
		jerusalemCinema.getHalls().add(hall26);
		session.save(hall26);

		Hall hall27 = new Hall(27,3, 6, 18, "27",jerusalemCinema);
		jerusalemCinema.getHalls().add(hall27);
		session.save(hall27);

		Hall hall28 = new Hall(28,3, 6, 18, "28",haifaCinema);
		haifaCinema.getHalls().add(hall28);
		session.save(hall28);

		Hall hall29 = new Hall(29,3, 6, 18, "29",eilatCinema);
		eilatCinema.getHalls().add(hall29);
		session.save(hall29);

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
				LocalDateTime.of(2024, 9, 24, 17, 00),
				LocalDateTime.of(2024, 9, 24, 20, 00),
				LocalDateTime.of(2024, 9, 24, 23, 00),
				LocalDateTime.of(2024, 9, 25, 16, 30),
				LocalDateTime.of(2024, 9, 25, 19, 30),
				LocalDateTime.of(2024, 9, 25, 23, 00),
				LocalDateTime.of(2024, 9, 26, 17, 00),
				LocalDateTime.of(2024, 9, 26, 20, 00),
				LocalDateTime.of(2024, 9, 26, 23, 00),
				LocalDateTime.of(2024, 9, 27, 17, 00),
				LocalDateTime.of(2024, 9, 27, 20, 00),
				LocalDateTime.of(2024, 9, 27, 23, 00),
				LocalDateTime.of(2024, 9, 28, 17, 00),
				LocalDateTime.of(2024, 9, 28, 20, 30),
				LocalDateTime.of(2024, 9, 28, 23, 30),
				LocalDateTime.of(2024, 9, 29, 17, 00),
				LocalDateTime.of(2024, 9, 29, 20, 30),
				LocalDateTime.of(2024, 9, 29, 23, 30),
				LocalDateTime.of(2024, 9, 30, 17, 00),
				LocalDateTime.of(2024, 9, 30, 20, 00),
				LocalDateTime.of(2024, 9, 30, 23, 00)
		);

		Hall hall1_ = session.get(Hall.class,1);
		Hall hall2_ = session.get(Hall.class,2);
		Hall hall3_ = session.get(Hall.class,3);
		Hall hall4_ = session.get(Hall.class,4);
		Hall hall5_ = session.get(Hall.class,5);
		Hall hall6_ = session.get(Hall.class,6);
		Hall hall7_ =session.get(Hall.class,7);
		Hall hall8_ =session.get(Hall.class,8);
		Hall hall9_ = session.get(Hall.class,9);
		Hall hall10_ = session.get(Hall.class,10);
		Hall hall11_ = session.get(Hall.class,11);
		Hall hall12_ =session.get(Hall.class,12);
		Hall hall13_ =session.get(Hall.class,13);
		Hall hall14_ = session.get(Hall.class,14);
		Hall hall15_ = session.get(Hall.class,15);
		Hall hall16_ = session.get(Hall.class,16);
		Hall hall17_ = session.get(Hall.class,17);
		Hall hall18_ = session.get(Hall.class,18);
		Hall hall19_ = session.get(Hall.class,19);
		Hall hall20_ =session.get(Hall.class,20);
		Hall hall21_ = session.get(Hall.class,21);
		Hall hall22_ =session.get(Hall.class,22);
		Hall hall23_ =session.get(Hall.class,23);
		Hall hall24_ =session.get(Hall.class,24);
		Hall hall25_ = session.get(Hall.class,25);
		Hall hall26_ = session.get(Hall.class,26);
		Hall hall27_ =session.get(Hall.class,27);
		Hall hall28_ = session.get(Hall.class,28);
		Hall hall29_ = session.get(Hall.class,29);


		for (LocalDateTime time : screeningTimes) {
			movie1.addScreening(time, jerusalemCinema, hall1_);   //On each of the dates and times defined , the movie will be screened in the same hall.
			movie1.addScreening(time, telAvivCinema,hall6_);
			movie1.addScreening(time, eilatCinema,hall12_);

			movie2.addScreening(time, haifaCinema, hall2_);
			movie2.addScreening(time, jerusalemCinema, hall7_);
			movie2.addScreening(time, eilatCinema, hall13_);

			movie3.addScreening(time, karmielCinema,hall17_);
			movie3.addScreening(time, jerusalemCinema,hall22_);
			movie3.addScreening(time, telAvivCinema,hall15_);


			movie4.addScreening(time, karmielCinema,hall18_);
			movie4.addScreening(time, haifaCinema,hall23_);

			movie5.addScreening(time, telAvivCinema,hall19_);
			movie5.addScreening(time, eilatCinema,hall24_);

			movie6.addScreening(time, haifaCinema, hall3_);
			movie6.addScreening(time, karmielCinema, hall8_);
			movie6.addScreening(time, eilatCinema, hall14_);

			movie7.addScreening(time, jerusalemCinema, hall4_);
			movie7.addScreening(time, telAvivCinema, hall9_);

			movie8.addScreening(time, haifaCinema, hall5_);
			movie8.addScreening(time, karmielCinema, hall10_);
			movie8.addScreening(time, jerusalemCinema, hall16_);

			movie9.addScreening(time, telAvivCinema,hall20_);
			movie9.addScreening(time, eilatCinema,hall25_);
			movie9.addScreening(time, karmielCinema, hall11_);
			movie9.addScreening(time, jerusalemCinema,hall27_);

			movie10.addScreening(time, karmielCinema,hall21_);
			movie10.addScreening(time, jerusalemCinema,hall26_);
			movie10.addScreening(time, haifaCinema,hall28_);
			movie10.addScreening(time, eilatCinema,hall29_);

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
			movie14.addScreening(time, null,null);
			movie15.addScreening(time, null,null);
			movie16.addScreening(time, null,null);
			movie17.addScreening(time, null,null);
			movie18.addScreening(time, null,null);
			movie19.addScreening(time, null,null);
		}

		session.save(movie14);
		session.save(movie15);
		session.save(movie16);
		session.save(movie17);
		session.save(movie18);
		session.save(movie19);

		session.flush();
	}

	private static List<Card> getAllCards(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Card> query = builder.createQuery(Card.class);
		query.from(Card.class);
		return session.createQuery(query).getResultList();
	}

	private static List<Notification> getAllNotifications(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Notification> query = builder.createQuery(Notification.class);
		query.from(Notification.class);
		return session.createQuery(query).getResultList();
	}

	// for testing
	private static List<Customer> getAllCustomers(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Customer> query = builder.createQuery(Customer.class);
		query.from(Customer.class);
		return session.createQuery(query).getResultList();
	}


	private static List<Movie> getAllMovies(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Movie> query = builder.createQuery(Movie.class);
		query.from(Movie.class);
		return session.createQuery(query).getResultList();
	}

	private static List<ChangePriceRequest> getAllRequests(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<ChangePriceRequest> query = builder.createQuery(ChangePriceRequest.class);
		query.from(ChangePriceRequest.class);
		return session.createQuery(query).getResultList();
	}

	private static byte[] loadImageFromFile(String relativePath) throws IOException {
		InputStream inputStream = SimpleServer.class.getClassLoader().getResourceAsStream(relativePath);
		if (inputStream == null) {
			throw new FileNotFoundException("File not found: " + relativePath);
		}
		return inputStream.readAllBytes();
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

	private static List<HomeMoviePurchase> getAllHomeMoviePurchases(Session session) throws Exception {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<HomeMoviePurchase> query = builder.createQuery(HomeMoviePurchase.class);
		query.from(HomeMoviePurchase.class);
		return session.createQuery(query).getResultList();
	}

	private static HeadManager getHeadManager(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<HeadManager> query = builder.createQuery(HeadManager.class);
		query.from(HeadManager.class);
		return session.createQuery(query).getSingleResult();
	}

	private static Cinema cinema(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Cinema> query = builder.createQuery(Cinema.class);
		query.from(Cinema.class);
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

	private static List<Complaint> getAllComplaints(Session session) throws Exception { //////////////////////
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Complaint> query = builder.createQuery(Complaint.class);
		query.from(Complaint.class);
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
					if (branch.equals("All") || movieToDelete.getBranches().size() == 1) {
						session.remove(movieToDelete);
						NewMessage newMessage4 = new NewMessage(movie.getId(), "movieNotAvailable");
						sendToAllClients(newMessage4);
						NewMessage newMessage2 = new NewMessage("movieRemoved");
						client.sendToClient(newMessage2);
					} else {
						Movie movieToDeleteBranch = session.get(Movie.class, movie.getId());
						List<Branch> branches = getAllBranches(session);
						List<Screening> screenings = getScreeningsForMovie(session, movie.getId());
						for (Screening screening : screenings) {
							if (screening.getBranch().getName().equals(branch)) {
								movieToDeleteBranch.getScreenings().remove(screening);
								session.remove(screening);
							}
						}
						for (Branch x : branches) {
							if (x.getName().equals(branch)) {
								x.getMovies().remove(movie);
								movieToDeleteBranch.getBranches().remove(x);
							}
						}
						if (movieToDeleteBranch != null) {
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
			} else if (msgString.equals("addCinemaMovie")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Movie movie = (Movie) message.getObject();
					List<Branch> branches = getAllBranches(session);
					List<Branch> movieBranches = message.getBranches();
					List<Branch> newBranches = new ArrayList<>();
					List<Screening> screenings = new ArrayList<>();
					List<LocalDateTime> times = message.getDateTimes();
					int i;
					for (Branch x : branches) {
						i = 0;
						for (Branch y : movieBranches) {
							if (x.getName().equals(y.getName())) {
								if (!newBranches.contains(x))
									newBranches.add(x);
								if (!x.getMovies().contains(movie))
									x.addMovie(movie);
								session.save(x);
								Screening screening = new Screening(times.get(i), movie, x);
								screenings.add(screening);
								movie.addScreening(times.get(i), x, null);  /////////////////////////////////////////////////////////
							}
							i++;
						}
					}
					movie.setBranches(newBranches);
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

					if (movie instanceof HomeMovie) {
						HomeMovie homeMovie = session.get(HomeMovie.class, movie.getId());
						LocalDateTime screeningTime = data.getScreeningTime();


						Screening screeningToRemove = movie.getScreenings().stream()
								.filter(screening -> screening.getScreeningTime().equals(screeningTime))
								.findFirst()
								.orElse(null);

						if (screeningToRemove != null) {
							movie.getScreenings().remove(screeningToRemove);
							session.remove(screeningToRemove);
						} else {
							System.err.println("Screening not found.");
						}
						session.save(movie);
					} else {
						Branch branch = session.get(Branch.class, data.getBranchId());
						LocalDateTime screeningTime = data.getScreeningTime();

						if (movie == null || branch == null) {
							System.err.println("Movie or branch not found.");
							return;
						}

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
			} else if (msgString.equals("addScreening")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();

					// Extract screening data from the message
					ScreeningData data = (ScreeningData) message.getObject();
					Movie movie = session.get(Movie.class, data.getMovieId());
					Branch branch = data.getBranchId() != null ? session.get(Branch.class, data.getBranchId()) : null;
					Hall hall = data.getHallId() != null ? session.get(Hall.class, data.getHallId()) : null;
					LocalDateTime screeningTime = data.getScreeningTime();

					// Check if the screening already exists
					boolean screeningExists = movie.getScreenings().stream()
							.anyMatch(screening -> screening.getScreeningTime().equals(screeningTime)
									&& screening.getBranch().equals(branch)
									&& screening.getHall().equals(hall));

					if (!screeningExists) {
						// Create and add the new screening
						Screening newScreening = new Screening();
						newScreening.setMovie(movie);
						newScreening.setScreeningTime(screeningTime);
						newScreening.setBranch(branch);
						newScreening.setHall(hall);

						session.persist(newScreening);
						movie.getScreenings().add(newScreening);

						if (branch != null) {
							branch.getScreenings().add(newScreening);
						}
						if (hall != null) {
							hall.getScreenings().add(newScreening);
						}

						session.getTransaction().commit();

						// Load updated movie list and send to all clients
						List<Movie> movies = getAllMovies(session);
						NewMessage updateMessage = new NewMessage(movies, "movies");
						sendToAllClients(updateMessage);

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
			}

			else if (msgString.equals("editScreening")) {
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
			} else if (msgString.equals("submitComplaint")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Complaint complaint = (Complaint) message.getObject();
					session.save(complaint);
					NewMessage newMessage = new NewMessage("complaintSubmitted");
					client.sendToClient(newMessage);
					List<Complaint> complaints = getAllComplaints(session);
					NewMessage newMessage2 = new NewMessage(complaints, "complaints");
					sendToAllClients(newMessage2);
					long id = complaint.getCustomerID();
					int id2 = (int) id;
					Customer customer = session.get(Customer.class, id2);
					if (customer == null) {

						Customer customer1 = new Customer(id2, complaint.getName(), complaint.getEmail(),
								complaint.getPhoneNumber(), null, false);
						session.save(customer1);
					}
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("complaintsList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Complaint> complaints = getAllComplaints(session);
					NewMessage newMessage = new NewMessage(complaints, "complaints");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("answerComplaint")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Complaint complaint = (Complaint) message.getObject();
					Complaint complaintToAnswer = session.get(Complaint.class, complaint.getId());
					complaintToAnswer.setResponse(complaint.getResponse());
					complaintToAnswer.setStatus(true);
					session.save(complaintToAnswer);
					List<Complaint> complaints = getAllComplaints(session);
					NewMessage newMessage = new NewMessage(complaints, "complaints");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage("complaintAnswered");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("changePriceRequest")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					ChangePriceRequest changePriceRequest = (ChangePriceRequest) message.getObject();
					session.save(changePriceRequest);
					List<ChangePriceRequest> requests = getAllRequests(session);
					NewMessage newMessage = new NewMessage(requests, "requests");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage("requestReceived");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("requestsList")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<ChangePriceRequest> requests = getAllRequests(session);
					NewMessage newMessage = new NewMessage(requests, "requests");
					sendToAllClients(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("confirmPriceUpdate")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Cinema cinema = session.get(Cinema.class, 1);
					ChangePriceRequest changePriceRequest = (ChangePriceRequest) message.getObject();
					List<ChangePriceRequest> requests = getAllRequests(session);
					ChangePriceRequest request = session.get(ChangePriceRequest.class, changePriceRequest.getId());
					request.setStatus("Confirmed");
					switch (changePriceRequest.getProduct()) {
						case "Ticket":
							cinema.setTicketPrice(changePriceRequest.getNewPrice());
							break;
						case "Link Ticket":
							cinema.setLinkTicketPrice(changePriceRequest.getNewPrice());
							break;
						case "Card":
							cinema.setCardPrice(changePriceRequest.getNewPrice());
					}
					session.save(cinema);
					session.save(request);
					NewMessage newMessage = new NewMessage(requests, "requests");
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage(cinema, "cinema");
					sendToAllClients(newMessage2);
					NewMessage newMessage3 = new NewMessage("requestConfirmed");
					client.sendToClient(newMessage3);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("denyPriceUpdate")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					ChangePriceRequest changePriceRequest = (ChangePriceRequest) message.getObject();
					List<ChangePriceRequest> requests = getAllRequests(session);
					ChangePriceRequest request = session.get(ChangePriceRequest.class, changePriceRequest.getId());
					request.setStatus("Denied");
					session.save(request);
					NewMessage newMessage = new NewMessage(requests, "requests");
					sendToAllClients(newMessage);
					NewMessage newMessage2 = new NewMessage("requestDenied");
					client.sendToClient(newMessage2);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("cinema")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Cinema cinema = session.get(Cinema.class, 1);
					NewMessage newMessage = new NewMessage(cinema, "cinema");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("loginCustomer")) {
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
			} else if (msgString.equals("logOutCustomer")) {
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
			} else if (msgString.equals("cards")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Card> cards = getAllCards(session);
					NewMessage newMessage = new NewMessage(cards, "cardsList");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("purchaseCards")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Purchase> purchaseCards = (List<Purchase>) message.getObject();
					for (Purchase card : purchaseCards) {
						session.save(card);
					}
					Customer customer = purchaseCards.get(0).getCustomer();
					Customer savedCustomer = session.get(Customer.class, customer.getId());
					if (savedCustomer == null) {
						session.save(customer);
					} else {
						for (Purchase purchase : purchaseCards) {
							savedCustomer.addPurchase(purchase);
							session.save(purchase);
						}
					}
					NewMessage newMessage1 = new NewMessage("purchaseSuccessful");
					client.sendToClient(newMessage1);
					List<Card> cards = getAllCards(session);
					NewMessage newMessage = new NewMessage(cards, "cardsList");
					sendToAllClients(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("notifications")) {
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Notification> notifications = getAllNotifications(session);
					NewMessage newMessage = new NewMessage(notifications, "notificationsList");
					client.sendToClient(newMessage);
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
				}
			} else if (msgString.equals("fetchPurchases")) {
				int customerId = message.getId();
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Customer customer = session.get(Customer.class, customerId);
					if (customer != null) {
						List<Purchase> purchases = customer.getPurchaseHistory();
						NewMessage responseMessage = new NewMessage(purchases, "purchasesResponse");
						client.sendToClient(responseMessage);
					} else {
						NewMessage responseMessage = new NewMessage("No customer found with ID: " + customerId, "error");
						client.sendToClient(responseMessage);
					}
					session.getTransaction().commit();
				} catch (Exception e) {
					System.err.println("An error occurred during fetchPurchases: " + e.getMessage());
				}
			} else if (msgString.equals("fetchPurchases")) {
				int customerId = message.getId();
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					Customer customer = session.get(Customer.class, customerId);
					if (customer != null) {
						List<Purchase> purchases = customer.getPurchaseHistory();
						NewMessage responseMessage = new NewMessage(purchases, "purchasesResponse");
						client.sendToClient(responseMessage);
					} else {
						NewMessage responseMessage = new NewMessage("No customer found with ID: " + customerId, "error");
						client.sendToClient(responseMessage);
					}
					session.getTransaction().commit();
				} catch (Exception e) {
					System.err.println("An error occurred during fetchPurchases: " + e.getMessage());
					e.printStackTrace();
				}
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}