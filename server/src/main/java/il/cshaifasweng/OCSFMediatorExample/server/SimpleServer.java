package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.SoonMovie;
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
import java.util.Date;
import java.util.List;
import java.util.Scanner;

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
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void generateMovies(Session session) throws Exception {
		byte[] image1 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\1.jpg");
		byte[] image2 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\2.jpg");
		byte[] image3 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\3.jpg");
		byte[] image4 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\4.jpg");
		byte[] image5 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\5.jpg");
		byte[] image6 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\6.jpg");
		byte[] image7 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\7.jpg");
		byte[] image8 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\images\\8.jpg");


		// יצירת אובייקטי סרט ושמירתם עם התמונות כ-Byte Array
		Movie num1 = new Movie(1, "A quiet place", "מקום שקט 2", "Michael Sarnoski", 2024, "Wednesday at 7:15 PM, Friday at 8:10 PM", image1);
		session.save(num1);
		session.flush();

		Movie num2 = new Movie(2, "Barbie", "ברבי", "Greta Gerwig", 2023, "Sunday at 12:15 PM, Thursday at 9:00 AM", image2);
		session.save(num2);
		session.flush();

		Movie num3 = new Movie(3, "Fast X", "מהיר ועצבני 10", "Louis Leterrier", 2023, "Sunday at 2:00 PM, Friday at 5:30 PM", image3);
		session.save(num3);
		session.flush();

		Movie num4 = new Movie(4, "Inside out", "הקול בראש 2", "Kelsey Mann", 2024, "Wednesday at 10:45 AM", image4);
		session.save(num4);
		session.flush();

		Movie num5 = new Movie(5, "It Ends With Us", "איתנו זה נגמר", "Justin Baldoni", 2024, "Wednesday at 8:30 PM", image5);
		session.save(num5);
		session.flush();

		Movie num6 = new Movie(6, "Joker", "ג'וקר", "Todd Philips", 2019, "Tuesday at 1:00 PM, Thursday at 9:45 AM, Saturday at 11:00 AM", image6);
		session.save(num6);
		session.flush();

		Movie num7 = new Movie(7, "Oppenheimer", "אופנהיימר", "Christopher Nolan", 2023, "Wednesday at 8:30 PM", image7);
		session.save(num7);
		session.flush();

		Movie num8 = new Movie(8, "The creator", "היוצר", "Gareth Edward", 2023, "Saturday at 10:00 AM", image8);
		session.save(num8);
		session.flush();
	}

	private static void generateSoonMovies(Session session) throws Exception {
		byte[] Soonimage1 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\1.jpg");
		byte[] Soonimage2 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\2.jpg");
		byte[] Soonimage3 = loadImageFromFile("C:\\Users\\Shatha\\Final_Project_V2\\server\\src\\main\\resources\\SoonImages\\3.jpg");

		SoonMovie soonMovie1 = new SoonMovie(1,"Blink Twice", "Sep 5,2024", Soonimage1);
		session.save(soonMovie1);
		session.flush();

		SoonMovie soonMovie2 = new SoonMovie(2,"African Giants", "Sep 10,2024", Soonimage2);
		session.save(soonMovie2);
		session.flush();

		SoonMovie soonMovie3 = new SoonMovie(3,"The Killer's Game", "Sep 15,2024", Soonimage3);
		session.save(soonMovie3);
		session.flush();
	}


	private static byte[] loadImageFromFile(String filePath) throws IOException {
		File file = new File(filePath);
		try (InputStream inputStream = new FileInputStream(file)) {
			return inputStream.readAllBytes();  // קריאה של כל התוכן של הקובץ כ-Byte Array
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

	@Override
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		NewMessage message = (NewMessage) msg;
		String msgString = message.getMessage();
		System.out.println("Received message from client: " + msgString);
		try {
			if (msgString.isBlank()) {
				message.setMessage("Error! we got an empty message");
				client.sendToClient(message);
			} else if (msgString.equals("moviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<Movie> movies = getAllMovies(session);
					System.out.println("Movies list to be sent: " + movies);

					NewMessage newMessage = new NewMessage(movies, "movies");  // שליחת רשימת הסרטים ללקוח עם המחרוזת "movies"
					client.sendToClient(newMessage);
					System.out.println("Sent movies list to client with " + movies.size() + " movies.");
					session.getTransaction().commit();
				} catch (Exception exception) {
					System.err.println("An error occurred, changes have been rolled back.");
					exception.printStackTrace();
				}
			} else if (msgString.equals("soonMoviesList")) {  // בדיקה אם ההודעה היא בקשת רשימת סרטים קרובים
				try (Session session = sessionFactory.openSession()) {
					session.beginTransaction();
					List<SoonMovie> soonMovies = getAllSoonMovies(session);
					System.out.println("Soon movies list to be sent: " + soonMovies);

					NewMessage newMessage = new NewMessage(soonMovies, "soonMovies");  // שליחת רשימת הסרטים הקרובים ללקוח עם המחרוזת "soonMovies"
					client.sendToClient(newMessage);
					System.out.println("Sent soon movies list to client with " + soonMovies.size() + " movies.");
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


