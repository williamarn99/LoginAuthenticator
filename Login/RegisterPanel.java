import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 * Register panel class creates a panel that allows the user to
 * create a new account. The class communicates with the database
 * manager to add rows to the database table.
 * 
 * @author William Arnold
 * @version 1.0
 * @since 2020-05-11
 */
public class RegisterPanel implements ActionListener {

	private DatabaseManager dm;
	
	private static FrameMain frame;
	
	private static JPanel panelMain;
	private static JPanel panelError;
	private JPanel panelBack;
	
	private static JLabel labEmail;
	private static JLabel labUsername;
	private static JLabel labPassword;
	private static JLabel labConfirmPass;
	private static JLabel labError;
	
	private static JTextField fieldEmail;
	private static JTextField fieldUsername;
	private static JPasswordField fieldPassword;
	private static JPasswordField fieldConfirmPass;
	
	private static JButton buttonBack;
	private static JButton buttonConfirm;
	
	public RegisterPanel(FrameMain f, JPanel bp) {
		panelBack = bp;
		frame = f;
		
		dm = DatabaseManager.getInstance();
		
		createDisplay(this);
	}
	
	public static void createDisplay(RegisterPanel p) {
		panelMain = new JPanel(new GridBagLayout());
		
		GridBagConstraints bag = new GridBagConstraints();
		
		labEmail = new JLabel("Email");
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 0;
		panelMain.add(labEmail, bag);
		
		labUsername = new JLabel("Username");
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 1;
		panelMain.add(labUsername, bag);

		labPassword = new JLabel("Password");
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 2;
		panelMain.add(labPassword, bag);
		
		labConfirmPass = new JLabel("Confirm Password");
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 3;
		panelMain.add(labConfirmPass, bag);
		
		buttonBack = new JButton("Back");
		buttonBack.addActionListener(p);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 4;
		panelMain.add(buttonBack, bag);
		
		
		fieldEmail = new JTextField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 1;
		bag.gridy = 0;
		panelMain.add(fieldEmail, bag);
		
		fieldUsername = new JTextField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 1;
		bag.gridy = 1;
		panelMain.add(fieldUsername, bag);

		fieldPassword = new JPasswordField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 1;
		bag.gridy = 2;
		panelMain.add(fieldPassword, bag);
		
		fieldConfirmPass = new JPasswordField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 1;
		bag.gridy = 3;
		panelMain.add(fieldConfirmPass, bag);
		
		buttonConfirm = new JButton("Register");
		buttonConfirm.addActionListener(p);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 1;
		bag.gridy = 4;
		panelMain.add(buttonConfirm, bag);
		
		labError = new JLabel("Passwords do not match");
		labError.setForeground(Color.red);
		labError.setVisible(false);
		
		panelError = new JPanel(new FlowLayout());
		
		panelError.add(labError);
		
		frame.add(panelMain, BorderLayout.CENTER);
		frame.add(panelError, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonBack) {
			frame.remove(panelMain);
			frame.remove(panelError);
			frame.createDisplay(frame);
		}
		else if(e.getSource() == buttonConfirm) {
			String user = fieldUsername.getText();
			String email = fieldEmail.getText();
			String pass = fieldPassword.getText();
			String conPass = fieldConfirmPass.getText();
			
			if(!pass.equals(conPass)) {
				labError.setText("Passwords do not match");
				labError.setVisible(true);
			}
			else if(user.isBlank() || email.isBlank() ||
					pass.isBlank() || conPass.isBlank()) {
				labError.setText("All fields must be filled out");
				labError.setVisible(true);
			}
			else if(pass.length() < 8) {
				labError.setText("Password must be at least 8 characters");
				labError.setVisible(true);
			}
			else if(!email.contains("@") || !email.contains(".")) {
				labError.setText("You must enter a valid email address");
				labError.setVisible(true);
			}
			else if(dm.userExists(user)) {
				labError.setText("Username is in use");
				labError.setVisible(true);
			}
			else if(dm.emailExists(email)) {
				labError.setText("Email is in use");
				labError.setVisible(true);
			}
			else {
				frame.remove(panelMain);
				frame.remove(panelError);
			}
		}
	}
}
