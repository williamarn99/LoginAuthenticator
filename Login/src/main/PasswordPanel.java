package src.main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * 
 * @author William Arnold
 * @version 1.0
 * @since 2020-05-11
 */
public class PasswordPanel implements ActionListener {

	private DatabaseManager dm;
	
	private String email;
	
	private static FrameMain frame;
	
	private static JPanel panelMain;
	
	private static JLabel labNewPass;
	private static JLabel labConfirmPass;
	private static JLabel labError;
	
	private static JTextField fieldNewPass;
	private static JTextField fieldConfirmPass;
	
	private static JButton buttonCont;
	
	public PasswordPanel(FrameMain f, String e) {
		frame = f;
		email = e;
		
		dm = DatabaseManager.getInstance();
		
		createDisplay(this);
	}
	
	public static void createDisplay(PasswordPanel p) {
		panelMain = new JPanel(new GridBagLayout());
		
		GridBagConstraints bag = new GridBagConstraints();
		
		labNewPass = new JLabel("New Password");
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 0;
		panelMain.add(labNewPass, bag);
		
		labConfirmPass = new JLabel("Email");
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 1;
		panelMain.add(labConfirmPass, bag);
		
		fieldNewPass = new JTextField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 1;
		bag.gridy = 0;
		panelMain.add(fieldNewPass, bag);
		
		fieldConfirmPass = new JTextField();
		bag.fill = GridBagConstraints.HORIZONTAL;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 1;
		bag.gridy = 1;
		panelMain.add(fieldConfirmPass, bag);
		
		GridBagConstraints bag2 = new GridBagConstraints();
		
		buttonCont = new JButton("Submit");
		buttonCont.addActionListener(p);
		bag2.fill = GridBagConstraints.CENTER;
		bag2.insets = new Insets(10, 10, 0, 0);
		bag2.gridx = 0;
		bag2.gridy = 0;
		panelMain.add(buttonCont, bag2);
		
		labError = new JLabel("Password must be at least 8 characters");
		labError.setForeground(Color.red);
		labError.setVisible(false);
		bag2.fill = GridBagConstraints.CENTER;
		bag2.insets = new Insets(10, 10, 0, 0);
		bag2.gridx = 0;
		bag2.gridy = 1;
		panelMain.add(labError, bag2);
		
		frame.add(panelMain, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String locPass = fieldNewPass.getText();
		String locConf = fieldConfirmPass.getText();
		
		if(e.getSource() == buttonCont) {
			if(locPass.length() < 8) {
				labError.setText("Password must be at least 8 characters");
				labError.setVisible(true);
			}
			else if(!locPass.equals(locConf)) {
				labError.setText("Passwords must match");
				labError.setVisible(true);
			}
			else {
				dm.changePass(locPass, email);
				
				frame.remove(panelMain);
				frame.createDisplay(frame);
			}
		}
	}
}
