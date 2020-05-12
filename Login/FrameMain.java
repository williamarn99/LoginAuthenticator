import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Frame main class generates the frame that holds all
 * panels within the application. The class also generates
 * the initial panel containing the login and links to
 * other panels.
 * 
 * @author William Arnold
 * @version 1.0
 * @since 2020-05-11
 */
public class FrameMain extends JFrame implements ActionListener, KeyListener, WindowListener {
	
	private JPanel panelMain;
	private JPanel panelButtons;
	
	private JLabel labUsername;
	private JLabel labPassword;
	private JLabel labInvalidCred;
	
	private JTextField fieldUsername;
	private JPasswordField fieldPassword;
	
	private JButton buttonForgotCred;
	private JButton buttonLogin;
	private JButton buttonRegisterUser;
	
	private DatabaseManager dm;
	
	public FrameMain() {
		createDisplay(this);
		
		addWindowListener(this);
	}
	
	public void createDisplay(JFrame f) {
		f.setPreferredSize(new Dimension(400, 300));
		f.setLayout(new BorderLayout());
		
		panelMain = new JPanel(new GridBagLayout());
		
		GridBagConstraints bag = new GridBagConstraints();
		
		labUsername = new JLabel("Username");
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 0;
		panelMain.add(labUsername, bag);
		
		fieldUsername = new JTextField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 1;
		panelMain.add(fieldUsername, bag);
		
		labPassword = new JLabel("Password");
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 2;
		panelMain.add(labPassword, bag);
		
		fieldPassword = new JPasswordField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 3;
		panelMain.add(fieldPassword, bag);
		
		buttonForgotCred = new JButton("Forgot Credentials");
		buttonForgotCred.addActionListener(this);
		buttonLogin = new JButton("Login");
		buttonLogin.addActionListener(this);
		buttonRegisterUser = new JButton("Register User");
		buttonRegisterUser.addActionListener(this);
		
		panelButtons = new JPanel(new FlowLayout());
		panelButtons.add(buttonForgotCred);
		panelButtons.add(buttonLogin);
		panelButtons.add(buttonRegisterUser);
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 4;
		panelMain.add(panelButtons, bag);
		
		labInvalidCred = new JLabel("Invalid Username/Password");
		labInvalidCred.setForeground(Color.red);
		labInvalidCred.setVisible(false);
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 5;
		panelMain.add(labInvalidCred, bag);
		
		dm = DatabaseManager.getInstance();
		
		f.add(panelMain, BorderLayout.CENTER);
		
		f.pack();
		f.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonLogin) {
			String user = fieldUsername.getText();
			String pass = fieldPassword.getText();
			
			boolean valid = dm.verifyUser(user, pass);
			
			if(!valid) {
				labInvalidCred.setVisible(true);
			}
			else {
				this.remove(panelMain);
				
				JPanel p = new JPanel(new FlowLayout());
			}
		}
		else if(e.getSource() == buttonRegisterUser) {
			this.remove(panelMain);
			RegisterPanel regPanel = new RegisterPanel(this, panelMain);
			this.pack();
		}
		else if(e.getSource() == buttonForgotCred) {
			this.remove(panelMain);
			ForgotPanel forPanel = new ForgotPanel(this, panelMain);
			this.pack();
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			String user = fieldUsername.getText();
			String pass = fieldPassword.getText();
			
			boolean valid = dm.verifyUser(user, pass);
			
			if(!valid) {
				labInvalidCred.setVisible(true);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		dispose();
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
