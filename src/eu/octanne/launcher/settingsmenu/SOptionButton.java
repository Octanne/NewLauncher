package eu.octanne.launcher.settingsmenu;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.textured.STexturedButton;

public class SOptionButton extends STexturedButton{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7084396034328806140L;
	
	private JLabel text;
	
	public SOptionButton(String buttonText) {
		super(Swinger.getResource("SETTINGS_BUTTON.png"));
		text = new JLabel(buttonText, SwingConstants.CENTER);	
		text.setForeground(Color.ORANGE);
		text.setFont(new Font("Agency FB", Font.BOLD, 18));
		text.setBounds(0, -2, 150, 30);
		this.add(text);
	}
	
	

}
