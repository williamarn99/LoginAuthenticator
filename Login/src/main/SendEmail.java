package src.main;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

/**
 * This class utilizes the javax jars for activation and java mail
 * to allow the application to utilize the user's email address
 * for account recovery.
 * 
 * @author William Arnold
 * @version 1.0
 * @since 2020-05-11
 */
public class SendEmail {
	
	private static SendEmail instance;
	
	private final String SENDER = "testingProgram@javaapp.com";
	private final String HOST = "localhost";
	private final String ALPHA_NUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
										 + "0123456789"
										 + "abcdefghijklmnopqrstuvwxyz";
	
	Properties properties;
	
	private String recipient;
	
	private Session session;
	
	/**
	 * Sets system properties and instantiates a session that allows emails
	 * to be sent over a localhost session
	 */
	private SendEmail() {
		properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		
		session = Session.getDefaultInstance(properties);
	}
	
	/**
	 * The getInstance method provides a singleton implementation of the SendEmail class
	 * allowing for all other classes within the program to send emails within
	 * instantiating a new session.
	 * 
	 * @return instance Singleton instance of the Send Email class
	 */
	public static SendEmail getInstance() {
		if(instance == null) {
			instance = new SendEmail();
		}
		
		return instance;
	}
	
	/**
	 * Allows the user to set the recipient of the email after getting
	 * the manager with the getInstance method
	 * 
	 * @param email Address to send the email to
	 */
	public void setRecipient(String email) {
		recipient = email;
	}
	
	/**
	 *  Sends an email to the user containing a code that allows them to enter
	 *  in the application for account recovery.
	 * 
	 * @return code the 8 digit alphanumeric code that is emailed to the user
	 * @throws MessagingException If the email cannot be sent properly
	 */
	public String sendMessage() {
		String code = generateCode();
		
		try {
			MimeMessage message = new MimeMessage(session);
			
			message.setFrom(new InternetAddress(SENDER));
			
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			
			message.setSubject("Account Recovery");
			
			message.setText("Your recover code is:\n\n" + code + "\n\nThank you.");
			
			Transport.send(message);
			System.out.println("Email sent successfully.");
		} catch(MessagingException ex) {
			ex.printStackTrace();
		}
		
		return code;
	}
	
	/**
	 * 
	 * 
	 * @return
	 */
	private String generateCode() {
		StringBuilder sb = new StringBuilder(8);
		
		for(int i = 0; i < 8; i++) {
			int index = (int)(ALPHA_NUMERIC.length()
							* Math.random());
			
			sb.append(ALPHA_NUMERIC.charAt(index));
		}
		
		return sb.toString();
	}
}
