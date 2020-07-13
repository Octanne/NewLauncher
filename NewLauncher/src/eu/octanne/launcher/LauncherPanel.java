package eu.octanne.launcher;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import eu.octanne.launcher.checkbox.STexturedCheckbox;
import eu.octanne.launcher.settingsmenu.OptionFrame;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.colored.SColoredBar;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

public class LauncherPanel extends JPanel implements SwingerEventListener, KeyListener{
	
	private boolean fieldEnabled = true;
	
	private Image background = Swinger.getResource("BACKGROUND.png");
	
	private Saver saver = new Saver(new File(Launcher.gameDirectory, "launcher.properties"));

	private JTextField usernameField = new JTextField(this.saver.get("username"));
	private JPasswordField passwordField = new JPasswordField();
	
	private STexturedButton playButton = new STexturedButton(Swinger.getResource("JOUER.png"));
	private STexturedButton quitButton = new STexturedButton(Swinger.getResource("CLOSE.png"));
	private STexturedButton hideButton = new STexturedButton(Swinger.getResource("MINIMIZE.png"));
	private STexturedButton ramButton = new STexturedButton(Swinger.getResource("SETTINGS.png"));
	
	private	STexturedCheckbox savePasswordCheckbox = new STexturedCheckbox(Swinger.getResource("CHECKBOX.png"), Swinger.getResource("CHECKBOX_CHECK.png"));
	
	private Image emailTexture = Swinger.getResource("EMAIL.png");
	private Image passwordTexture = Swinger.getResource("MDP.png");
		
	private SColoredBar progressBar = new SColoredBar(Swinger.getTransparentWhite(100), Swinger.getTransparentInstance(Color.GREEN, 175));
	private JLabel infoLabel = new JLabel("Clique sur Jouer !", SwingConstants.CENTER);
	
	private OptionFrame optionFrame = new OptionFrame(saver);
	
	public LauncherPanel() {
		this.setLayout(null);
		
		optionFrame.setBounds(195, 125, 585, 375);
		optionFrame.setVisible(false);
		this.add(optionFrame);
		
		usernameField.setForeground(Color.ORANGE);
		usernameField.setFont(new Font("Agency FB", Font.PLAIN, 21));
		usernameField.setCaretColor(Color.WHITE);
		usernameField.setOpaque(false);
		passwordField.setBackground(Swinger.getTransparentInstance(Color.WHITE, 0));
		usernameField.setBorder(null);
		usernameField.setBounds(648, 259, 236, 38);
		usernameField.addKeyListener(this);
		this.add(usernameField);
		
		passwordField.setForeground(Color.ORANGE);
		passwordField.setFont(new Font("Verdana", Font.PLAIN, 18));
		passwordField.setCaretColor(Color.WHITE);
		passwordField.setBackground(Swinger.getTransparentInstance(Color.WHITE, 0));
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		passwordField.setBounds(648, 341, 236, 38);
		passwordField.addKeyListener(this);
		passwordField.setText(saver.get("password", ""));
		
		this.add(passwordField);
		
		//savePasswordCheckbox.setBounds(645, 390, 189, 26);
		//if(!passwordField.getText().isEmpty()) savePasswordCheckbox.setChecked(true);
		
		//this.add(savePasswordCheckbox);
		
		playButton.setBounds(690, 443, 200, 40);
		playButton.addEventListener(this);
		this.add(playButton);
		
		ramButton.addEventListener(this);
		ramButton.setBounds(690 - 50, 445, 36, 36);
		this.add(ramButton);
		
		quitButton.setBounds(920, 18);
		quitButton.addEventListener(this);
		this.add(quitButton);
		
		hideButton.setBounds(877, 18);
		hideButton.addEventListener(this);
		this.add(hideButton);
		
		progressBar.setBounds(12, 593, 951, 20);
		this.add(progressBar);
		
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setFont(usernameField.getFont().deriveFont(22F));
		infoLabel.setBounds(12, 560, 951, 25);
		this.add(infoLabel);
	}
	
	@Override
	public void onEvent(SwingerEvent e) {
		Object eSource = e.getSource();
		
		if(eSource == playButton) {
			initConnection();
		} else if(eSource == quitButton) {
			boolean animIsOk = true;
			try {
				Class.forName("com.sun.awt.AWTUtilities");
			} catch (ClassNotFoundException en) {
				animIsOk = false;
				en.printStackTrace();
			}
			if(animIsOk) {
				try {
					Class<?> animatorClass = Class.forName("fr.theshark34.swinger.animation.Animator");
					Method meth = animatorClass.getMethod("fadeOutFrame", Window.class, int.class, Runnable.class);
					meth.invoke(null, LauncherFrame.getInstance(), 2, new Runnable() {
						
						@Override
						public void run() {
							System.exit(0);
						}
					});
					/*Animator.fadeOutFrame(LauncherFrame.getInstance(), 2, new Runnable() {
						
						@Override
						public void run() {
							System.exit(0);
						}
					});*/
				} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException en) {
					en.printStackTrace();
				}
			}
		}else if(eSource == hideButton) {
			LauncherFrame.getInstance().setState(1);
		}else if(eSource == ramButton && fieldEnabled) {
			optionFrame.open();
		}
	}
	

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		Object eSource = e.getSource();
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(eSource == usernameField) passwordField.requestFocus();
			if(eSource == passwordField) initConnection();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Background
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
		
		//Fields Textures
		g.drawImage(emailTexture, 640, 230, 250, 67, this);
		g.drawImage(passwordTexture, 640, 310, 250, 70, this);
	}
	
	private void setFieldsEnabled(boolean enabled) {
		usernameField.setEnabled(enabled);
		passwordField.setEnabled(enabled);
		playButton.setEnabled(enabled);
		fieldEnabled = enabled;
	}
	
	public SColoredBar getProgressBar() {
		return progressBar;
	}
	
	public OptionFrame getOptionFrame() {
		return optionFrame;
	}
	
	public void setInfoText(String text) {
		infoLabel.setText(text);
	}
	
	@SuppressWarnings("deprecation")
	private void initConnection() {
		setFieldsEnabled(false);
		
		if(usernameField.getText().replaceAll(" ", "").length() == 0 || passwordField.getText().length() == 0) {
			setInfoText("Erreur, veuiller entrer un email et un mot de passe valides...");
			setFieldsEnabled(true);
			return;
		}
		
		Thread t = new Thread() {
			@Override
			public void run() {
				try {
				Launcher.auth(usernameField.getText(), passwordField.getText());
				} catch (AuthenticationException e) {
					if(e.getErrorModel().getError().equals("ForbiddenOperationException"))setInfoText("Erreur, la combinaison mot de passe, email est incorrect.");
					else setInfoText("Erreur rencontrée : " + e.getErrorModel().getErrorMessage());
					setFieldsEnabled(true);
					return;
				}
				
				saver.set("username", usernameField.getText());
				if(savePasswordCheckbox.isChecked()) saver.set("password", passwordField.getText());
				else saver.set("password", "");
				optionFrame.save();

				try {
					Launcher.update();
				} catch (Exception e) {
					LauncherFrame.getCrashReporter().catchError(e, "Impossible de mettre à jour le jeu !");
				}
				try {
					Launcher.launch();
				} catch (LaunchException e) {
					LauncherFrame.getCrashReporter().catchError(e, "Impossible de lancer le jeu !");
				}
			} 
		};
		t.start();
	}
}
