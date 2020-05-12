import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * The database manager class uses data from a text file to initialize an SQL Database
 * that holds the data of all of the accounts associated with the application.
 * The class contains various queries that allow users to create accounts and 
 * modify account information.
 * 
 * @author William Arnold
 * @version 1.0
 * @since 2020-05-11
 */
public class DatabaseManager {

	private static DatabaseManager instance;
	
	private static ResultSet rs;
	private static Statement stmt;
	private static PreparedStatement preStmt;
	private static Connection conn;
	
	private String _url;
	private String user;
	private String pass;
	private String driver;
	
	/**
	 * The constructor instantiates the connection to the postgres SQL database through
	 * a text file containing the data of the url, username, password and driver file.
	 * 
	 * @throws FileNotFoundException If the text file containing database login information
	 * 								 (DBCredentials.txt) is not found.
	 * @throws SQLException If the data in the text file containing database login information
	 * 						does not connect to an existing database.
	 */
	private DatabaseManager() {
		rs = null;
		stmt = null;
		preStmt = null;
		conn = null;
		
		try {
			File file = new File("DBCredentials.txt");
			
			Scanner reader = new Scanner(file);
			
			_url = reader.nextLine();
			user = reader.nextLine();
			pass = reader.nextLine();
			driver = reader.nextLine();
			
			System.out.println("Connecting to \"" + _url + "\" as user "
					+ user);
		} catch (FileNotFoundException e) {
			System.out.println("The DBCredentials.txt file must be present in the project root directory.");
			e.printStackTrace();
		}
		
		try {
			Class.forName(driver);
			
			conn = DriverManager.getConnection(_url, user, pass);
			
			conn.setAutoCommit(false);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * The getInstance method provides a singleton implementation of the Database Manager
	 * allowing for all other classes within the program to access the same database
	 * without the need of reconnecting through a new instance of the Database Manager.
	 * 
	 * @return instance Singleton instance of the Database Manager
	 */
	public static DatabaseManager getInstance() {
		if(instance == null) {
			instance = new DatabaseManager();
		}
		
		return instance;
	}
	
	/**
	 * Closes all of the resources associates with the database. Run this upon the application
	 * closing to ensure database resources are not leaked.
	 * 
	 * @throws SQLException If attempting to close a database resource that is already closed.
	 * 						The exception is prevented by the if statements checking for null.
	 */
	public void closeResources() {
		try {
			if(rs != null) {
				rs.close();
			}
			if(stmt != null) {
				stmt.close();
			}
			if(preStmt != null) {
				preStmt.close();
			}
			if(conn != null ) {
				conn.close();
			}
		} catch(SQLException se) {
			se.printStackTrace();
		}
	}
	
	/**
	 * Allows the database manager to contact the database and add a new user to the
	 * credentials table. All error handling is done within the client application
	 * so we do not need to check for duplication keys.
	 * 
	 * @param email Email address of new user
	 * @param locUser Username of new user
	 * @param locPass Password of new user
	 * @throws SQLException If the insert statement cannot be completed
	 */
	public void insertUser(String email, String locUser, String locPass) {
		try {
			preStmt = conn.prepareStatement("INSERT INTO CREDENTIALS VALUES (?, ?, ?)");
			
			preStmt.setString(1, locUser);
			preStmt.setString(2, locPass);
			preStmt.setString(3, email);
			
			preStmt.close();
			
			conn.commit();
			
			System.out.println("User " + locUser + " inserted.");
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Allows the database manager to send an update statement to the database
	 * that allows a row in the credentials table to reset the password. We do
	 * not need to check if a row exists with the given password because this
	 * is handled in the client application.
	 * 
	 * @param locPass New password for the user
	 * @param locEmail Email associated with user account
	 * @throws SQLException If the update statement cannot be processed by the database
	 */
	public void changePass(String locPass, String locEmail) {
		try {
			preStmt = conn.prepareStatement("UPDATE CREDENTIALS SET PASSWORD = ? WHERE EMAIL = ?");
			
			preStmt.setString(1, locPass);
			preStmt.setString(2, locEmail);
			
			preStmt.close();
			
			conn.commit();
			
			System.out.println("User with email " + locEmail + " had their password reset.");
		} catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	/**
	 * Allows the database manager to send a query statement to the database
	 * that checks if a row in the credentials table contains both the username
	 * and the password.
	 * 
	 * @param locPass New password for the user
	 * @param locEmail Email associated with user account
	 * @return Boolean value depending on whether or not the user exists
	 * @throws SQLException If the update statement cannot be processed by the database
	 */
	public boolean verifyUser(String locUser, String locPass) {
		int count;
		
		try {
			preStmt = conn.prepareStatement("SELECT count(EMAIL) FROM CREDENTIALS "
					+ "WHERE USERNAME = ? AND PASSWORD = ?");
			
			preStmt.setString(1, locUser);
			preStmt.setString(2, locPass);
			
			preStmt.close();
			
			conn.commit();
			
			count = rs.getInt(1);
			
			if(count == 1) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
	
	/**
	 * Sends a query to the database to validate that a given username exists
	 * within the credentials table. This prevents new users to repeat a username
	 * that is already taken.
	 * 
	 * @param locUser Username entered in client application
	 * @return boolean value depending on whether or not user exists
	 * @throws SQLException If the query cannot be processed by the database
	 */
	public boolean userExists(String locUser) {
		int count;
		
		try {
			preStmt = conn.prepareStatement("SELECT count(EMAIL) FROM CREDENTIALS "
					+ "WHERE USERNAME = ?");
			
			preStmt.setString(1, locUser);
			
			preStmt.close();
			
			conn.commit();
			
			count = rs.getInt(1);
			
			if(count == 1) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
	
	public boolean emailExists(String locEmail) {
		int count;
		
		try {
			preStmt = conn.prepareStatement("SELECT count(USERNAME) FROM CREDENTIALS "
					+ "WHERE EMAIL = ?");
			
			preStmt.setString(1, locEmail);
			
			preStmt.close();
			
			conn.commit();
			
			count = rs.getInt(1);
			
			if(count == 1) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}
}
