package src.main;
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
 * 
 * 
 * @author William Arnold
 * @version 1.0
 * @since 2020-05-11
 */
public class CodePanel implements ActionListener {
	
	private String code;
	private String email;
	
	private static FrameMain frame;
	
	private static JPanel panelMain;
	private static JPanel panelButtons;
	private JPanel panelBack;
	
	private static JLabel labInstruction;
	private static JLabel labCode;
	private static JLabel labError;
	
	private static JTextField fieldCode;
	
	private static JButton buttonCont;
	private static JButton buttonBack;
	
	public CodePanel(FrameMain f, JPanel bp, String c, String e) {
		panelBack = bp;
		frame = f;
		
		code = c;
		email = e;
		
		createDisplay(this);
	}
	
	public static void createDisplay(CodePanel p) {
		panelMain = new JPanel(new GridBagLayout());
		
		GridBagConstraints bag = new GridBagConstraints();
		
		labInstruction = new JLabel("We sent a code to your email address");
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 0;
		panelMain.add(labInstruction, bag);
		
		labCode = new JLabel("Code");
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 1;
		panelMain.add(labCode, bag);
		
		fieldCode = new JTextField();
		fieldCode.setPreferredSize(new Dimension(250, 20));
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 2;
		panelMain.add(fieldCode, bag);
		
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
		bag.gridy = 3;
		panelMain.add(panelButtons, bag);
		
		labError = new JLabel("Code is incorrect");
		labError.setForeground(Color.red);
		labError.setVisible(false);
		bag.fill = GridBagConstraints.CENTER;
		bag.insets = new Insets(10, 10, 0, 0);
		bag.gridx = 0;
		bag.gridy = 4;
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
			String locCode = fieldCode.getText();
			
			if(!locCode.equals(code)) {
				labError.setVisible(true);
			}
			else {
				frame.remove(panelMain);
				PasswordPanel passPanel = new PasswordPanel(frame, email);
				frame.pack();
			}
		}		
	}
}
