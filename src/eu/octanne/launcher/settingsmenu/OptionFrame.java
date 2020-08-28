package eu.octanne.launcher.settingsmenu;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import fr.theshark34.openlauncherlib.util.Saver;
import fr.theshark34.swinger.Swinger;
import fr.theshark34.swinger.event.SwingerEvent;
import fr.theshark34.swinger.event.SwingerEventListener;
import fr.theshark34.swinger.textured.STexturedButton;

public class OptionFrame extends JPanel implements SwingerEventListener{

	/*
	 * Fading
	 */
    private long start;
    private float alpha;
	
	private Image background = Swinger.getResource("SETTINGS_WINDOW.png");
	
	private SOptionButton saveButton;
	private STexturedButton quitButton;
	private RamCursorButton ramCursorButton;
	
	private Saver saver;
	
    public OptionFrame(Saver saver)
    {
        super();
        
        this.saver = saver;
        
        this.setSize(585, 375);
        this.setLayout(null);
        this.setOpaque(false);
        
        String[] ramChoice = {"1Go", "2Go", "3Go", "4Go", "6Go", "8Go", "10Go", "12Go", "14Go", "16Go"};
        ramCursorButton = new RamCursorButton(Swinger.getResource("Cursor_Button.png"), Swinger.getResource("Cursor.png"), ramChoice, readRam());
        ramCursorButton.setBounds(25, 45, Swinger.getResource("Cursor_Button.png").getWidth(), Swinger.getResource("Cursor_Button.png").getHeight());
        this.add(ramCursorButton);
        
        saveButton = new SOptionButton("Sauvegarder");
        saveButton.setBounds(415, 325);
        saveButton.addEventListener(this);
        this.add(saveButton);
        
        quitButton = new STexturedButton(Swinger.getResource("CLOSE.png"));
        quitButton.setBounds(543, 5);
        this.add(quitButton);
        quitButton.addEventListener(this);
    }
    
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Fade
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, alpha));
		//Background
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//Fade
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, alpha));
	}

    public void fade(int time, String type) {
    	if(!isVisible())setVisible(true);
        if(type.equals("in")) alpha = 0.0f;
        else alpha = 1.0f;
        start = System.currentTimeMillis();

        final int timeInMillis = time * 10;
        final Timer t = new Timer(50, null);
        t.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsed = System.currentTimeMillis() - start;
                if ((elapsed > timeInMillis && type.equals("out")) || (elapsed > timeInMillis && type.equals("in"))) {
                    start = 0;
                    if(type.equals("out"))setVisible(false);
                    else alpha = 1.0f;
                    repaint();
                    t.stop();
                } else {
                    if(type.equals("out"))alpha = 1.0f - (float) elapsed / timeInMillis;
                    if(type.equals("in"))alpha = 0.0f + (float) elapsed / timeInMillis;
                    repaint();
                }
            }
        });
        t.start();
    }

	@Override
	public void onEvent(SwingerEvent event) {
		
		if(event.getSource() == quitButton) {
			close();
		}
		if(event.getSource() == saveButton) {
			close();
		}
	}
	
	public void close() {
		fade(20, "out");
		save();
	}
	
	public void open() {
		fade(20, "in");
	}
	
	/**
     * Get the generated RAM arguments
     *
     * @return An array of two strings containing the arguments
     */
    public String[] getRamArguments()
    {
        int maxRam = Integer.parseInt(ramCursorButton.getSelectableValue()[readRam()].replace("Go", "")) * 1024;
        int minRam = maxRam - 512;

        return new String[]{"-Xms" + minRam + "M", "-Xmx" + maxRam + "M"};
    }

    /**
     * Read the saved ram
     *
     * @return An int, of the selected index of RAM_ARRAY
     */
    private int readRam()
    {
    	return Integer.parseInt(saver.get("ram", "1"));
    }

    /**
     * Save the RAM
     */
    public void save()
    {
		saver.set("ram", String.valueOf(ramCursorButton.getSelectedValue()));
    }

    /**
     * Return the file where to save the ram
     *
     * @return The file where the ram is saved
     *
     * @see #setFile(File)
     */
    public Saver getSaver()
    {
        return saver;
    }

    /**
     * Set the file where to save the ram
     *
     * @param file The new file where the ram is saved
     *
     * @see #getFile()
     */
    public void setSaver(Saver saver)
    {
        this.saver = saver;
    }
	
	
}
