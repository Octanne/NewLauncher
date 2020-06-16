package eu.octanne.launcher.checkbox;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class STexturedCheckbox extends JComponent implements KeyListener, MouseListener {

	/**
     * If the box is checked
     */
    private boolean checked;
	
	/**
     * The background checkbox image
     */
    private Image backgroundImage;

    /**
     * The image of the little check
     */
    private Image checkImage;

    /**
     * The STexturedCheckbox
     *
     * @param backgroundImage
     *            The background checkbox image
     * @param checkImage
     *            The image of the little check
     */
    public STexturedCheckbox(Image backgroundImage, Image checkImage) {
        super();
        
        this.addMouseListener(this);
        
        // If the background image is null, throwing an Illegal Argument Exception, else setting it
        if(backgroundImage == null)
            throw new IllegalArgumentException("backgroundImage == null ");
        this.backgroundImage = backgroundImage;

        // If the check image is null, throwing an Illegal Argument Exception, else setting it
        if(checkImage == null)
            throw new IllegalArgumentException("checkImage == null");
        this.checkImage = checkImage;
    }

    @Override
    public void paintComponent(Graphics g) {
        // If it is checked
        if(this.isChecked())
            // Drawing the little check image
        	g.drawImage(checkImage, 0, 0, this.getWidth(), this.getHeight(), this);
        else 
            // Drawing the background
        	g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
    }

    /**
     * Set the background checkbox image
     *
     * @param backgroundImage
     *            The new background
     */
    public void setBackgroundImage(Image backgroundImage) {
        // If the given background image is null, throwing an exception, else setting it
        if(backgroundImage == null)
            throw new IllegalArgumentException("backgroundImage == null ");
        this.backgroundImage = backgroundImage;

        repaint();
    }

    /**
     * Return the background checkbox image
     *
     * @return The background
     */
    public Image getBackgroundImage() {
        return this.backgroundImage;
    }

    /**
     * Set the little check image
     *
     * @param checkImage
     *            The new check
     */
    public void setCheckImage(Image checkImage) {
        // If the given check image is null, throwing an exception, else setting it
        if(checkImage == null)
            throw new IllegalArgumentException("checkImage == null ");
        this.checkImage = checkImage;

        repaint();
    }

    /**
     * Return the little check image
     *
     * @return The check
     */
    public Image getCheckImage() {
        return this.checkImage;
    }
    
    /**
     * Set the box checked, or not
     *
     * @param checked
     *            If the box need to be now checked, or not
     */
    public void setChecked(boolean checked) {
        this.checked = checked;

        repaint();
    }
    
    /**
     * Return if the box is checked, or not
     *
     * @return True if it is, false if not
     */
    public boolean isChecked() {
        return this.checked;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setChecked(!checked);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) setChecked(!checked);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}
}
