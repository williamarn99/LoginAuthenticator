import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
 * The forgot panel button allows for the 
 * 
 * @author William Arnold
 * @version 1.0
 * @since 2020-05-11
 */
public class ForgotPanel implements ActionListener {
	
	private DatabaseManager dm;
	private SendEmail se;
	
	private String code;
	
	private static FrameMain frame;
	
	private static JPanel panelMain;
	private static JPanel panelButtons;
	private JPanel panelBack;
	
	private static JLabel labEmail;
	private static JLabel labError;
	
	private static JTextField fieldEmail;
	
	private static JButton buttonCont;
	private static JButton buttonBack;
	
	public ForgotPanel(FrameMain f, JPanel bp) {
		panelBack = bp;
		frame = f;
		
		dm = DatabaseManager.getInstance();
		se = SendEmail.getInstance();
		
		createDisplay(this);
	}
	
	public static void createDisplay(ForgotPanel p) {
		panelMain = new JPanel(new GridBagLayout());
		
		GridBagConstraints bag = new GridBagConstraints();
		
		labEmail = new JLabel("Email");
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 0;
		panelMain.add(labEmail, bag);
		
		fieldEmail = new JTextField();
		fieldEmail.setPreferredSize(new Dimension(250, 20));
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 1;
		panelMain.add(fieldEmail, bag);
		
		buttonBack = new JButton("Back");
		buttonBack.addActionListener(p);
		buttonCont = new JButton("Continue");
		buttonCont.addActionListener(p);
		
		panelButtons = new JPanel(new FlowLayout());
		panelButtons.add(buttonBack);
		panelButtons.add(buttonCont);
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 2;
		panelMain.add(panelButtons, bag);
		
		labError = new JLabel("Email address not recognized");
		labError.setForeground(Color.red);
		labError.setVisible(false);
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 3;
		panelMain.add(labError, bag);
		
		frame.add(panelMain, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == buttonBack) {
			frame.remove(panelMain);
			frame.createDisplay(frame);
		}
		else if(e.getSource() == buttonCont) {
			String email = fieldEmail.getText();
			
			if(!dm.emailExists(email)) {
				labError.setVisible(true);
			}
			else {
				code = se.sendMessage();
				
				frame.remove(panelMain);
				CodePanel codPanel = new CodePanel(frame, panelBack, code, email);
				frame.pack();
			}
		}
	}
}
