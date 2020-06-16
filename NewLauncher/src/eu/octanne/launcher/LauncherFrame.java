package eu.octanne.launcher;

import javax.swing.JFrame;

import fr.theshark34.openlauncherlib.util.CrashReporter;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.animation.Animator;
import fr.theshark34.swinger.util.WindowMover;

public class LauncherFrame extends JFrame {

	private static LauncherFrame instance; 
	private LauncherPanel launcherPanel;
	private static CrashReporter crashReporter;

	public LauncherFrame(boolean isBootstrap) {
		//Main
		if(isBootstrap) {
			Swinger.setSystemLookNFeel();
			Swinger.setResourcePath("/eu/octanne/launcher/ressources/");
			Launcher.gameDirectory.mkdir();
			Launcher.crashDirectory.mkdir();
			crashReporter = new CrashReporter(Launcher.launcherName+" | Launcher "+Launcher.launcherVersion, Launcher.crashDirectory);

			instance = this;
		}
		
		//Constructor
		this.setTitle(Launcher.launcherName+" | Launcher");
		this.setSize(975, 625);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.setIconImage(Swinger.getResource("logo.png"));
		this.setContentPane(launcherPanel = new LauncherPanel());

		WindowMover mover = new WindowMover(this);
		this.addMouseListener(mover);
		this.addMouseMotionListener(mover);

		this.setVisible(true);

		Animator.fadeInFrame(this, 2);
	}

	public static void main(String[] args) {
		Swinger.setSystemLookNFeel();
		Swinger.setResourcePath("/eu/octanne/launcher/ressources/");
		Launcher.gameDirectory.mkdir();
		Launcher.crashDirectory.mkdir();
		crashReporter = new CrashReporter(Launcher.launcherName+" | Launcher "+Launcher.launcherVersion, Launcher.gameDirectory);

		instance = new LauncherFrame(false);
	}

	public static LauncherFrame getInstance() {
		return instance;
	}

	public LauncherPanel getLauncherPanel() {
		return this.launcherPanel;
	}
	public static CrashReporter getCrashReporter() {
		return crashReporter;
	}

}
