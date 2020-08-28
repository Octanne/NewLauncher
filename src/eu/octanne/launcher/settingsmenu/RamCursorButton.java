package eu.octanne.launcher.settingsmenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class RamCursorButton extends JComponent {

	protected class RamCursorListener extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			RamCursorButton cursorButtonS = (RamCursorButton) e.getSource();
			if(e.getX() > 64 && e.getX() <= cursorButtonS.getWidth()-15) {
				if(e.getX()-6 <= 64) cursorButton.setBounds(e.getX(), -1, cursorButton.getWidth(), cursorButton.getHeight());
				else cursorButton.setBounds(e.getX()-6, -1, cursorButton.getWidth(), cursorButton.getHeight());
			}else if(e.getX() <= 64) {
				cursorButton.setBounds(64, -1, cursorButton.getWidth(), cursorButton.getHeight());
			}else if(e.getX() > cursorButtonS.getWidth()-15) {
				cursorButton.setBounds(cursorButtonS.getWidth()-15, -1, cursorButton.getWidth(), cursorButton.getHeight());
			}else return;

			// Recalculate selectValue
			int eqPart = cursorButtonS.getWidth()/selectableValue.length;
			for(int part = 1; part < selectableValue.length+1; part++) {
				if(cursorButton.getX() >= part*eqPart && cursorButton.getX() < (part+1)*eqPart) {
					selectedValue = part-1;
					valueShow.setText(selectableValue[part-1]);
					break;
				}
			}

			//System.out.println("mousePressed > POS : (X="+e.getX()+",Y="+e.getY()+") in : "+e.getSource().toString());
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			//System.out.println("mousePressed > POS : (X="+e.getX()+",Y="+e.getY()+") in : "+e.getSource().toString());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			RamCursorButton cursorButtonS = (RamCursorButton) e.getSource();
			if(e.getX() > 64 && e.getX() <= cursorButtonS.getWidth()-15) {
				if(e.getX()-6 <= 64) cursorButton.setBounds(e.getX(), -1, cursorButton.getWidth(), cursorButton.getHeight());
				else cursorButton.setBounds(e.getX()-6, -1, cursorButton.getWidth(), cursorButton.getHeight());
			}else if(e.getX() <= 64) {
				cursorButton.setBounds(64, -1, cursorButton.getWidth(), cursorButton.getHeight());
			}else if(e.getX() > cursorButtonS.getWidth()-15) {
				cursorButton.setBounds(cursorButtonS.getWidth()-15, -1, cursorButton.getWidth(), cursorButton.getHeight());
			}else return;

			// Recalculate selectValue
			int eqPart = cursorButtonS.getWidth()/selectableValue.length;
			for(int part = 1; part < selectableValue.length+1; part++) {
				if(cursorButton.getX() >= part*eqPart && cursorButton.getX() < (part+1)*eqPart) {
					selectedValue = part-1;
					valueShow.setText(selectableValue[part-1]);
					break;
				}
			}

			//System.out.println("mouseDragged > POS : (X="+e.getX()+",Y="+e.getY()+") in : "+e.getSource().toString());
		}
	}

	protected class CursorButton extends JComponent {

		private Image backgroundImage;
		
		public CursorButton(Image img) {
			this.backgroundImage = img;
		}

		@Override
		public void paintComponent(Graphics g) {

			// Drawing Cursor
			g.drawImage(backgroundImage, 0, 0, 12, 26, this);
		}

	}

	/*
	 * Variable
	 */
	private boolean isFirst = true;

	/*
	 * selectableValue
	 */
	private String[] selectableValue;

	/*
	 * Value selected
	 */
	private int selectedValue;

	/*
	 * Background's Image of cursor button
	 */
	private Image backgroundImage;

	/*
	 * Cursor's Image
	 */
	private CursorButton cursorButton;
	private JLabel valueShow;

	private RamCursorListener listener;

	/*
	 * Constructor
	 */
	public RamCursorButton(Image backgroundImage, Image cursorImage, String selectableValue[], int defaultValue) {
		super();

		listener = new RamCursorListener();

		this.selectableValue = selectableValue;
		this.selectedValue = defaultValue;

		this.addMouseListener(listener);
		this.addMouseMotionListener(listener);

		// If the background image is null, throwing an Illegal Argument Exception, else setting it
		if(backgroundImage == null)
			throw new IllegalArgumentException("backgroundImage == null ");
		this.backgroundImage = backgroundImage;

		// If the cursor image is null, throwing an Illegal Argument Exception, else setting it
		if(cursorImage == null)
			throw new IllegalArgumentException("cursorImage == null");
		cursorButton = new CursorButton(cursorImage);
		cursorButton.setBounds(64, -1, 12, 26);
		this.add(cursorButton);

		// Set value Show
		valueShow = new JLabel(selectableValue[defaultValue], SwingConstants.RIGHT);
		valueShow.setForeground(Color.ORANGE);
		valueShow.setFont(new Font("Agency FB", Font.BOLD, 18));
		valueShow.setBounds(-10, -3, 50, 30);
		this.add(valueShow);
	}

	@Override
	public void paintComponent(Graphics g) {
		// Drawing the background
		g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
		
		// Set Cursor Pos
		if(isFirst) {
			isFirst = false;
			int eqPart = this.getWidth()/selectableValue.length;
			if(selectedValue == selectableValue.length-1) {
				cursorButton.setBounds(this.getWidth() - 15, -1, 12, 26);
			}else cursorButton.setBounds(eqPart*selectedValue + 64, -1, 12, 26);
		}
	}

	/**
	 * @return the selectableValue
	 */
	public String[] getSelectableValue() {
		return selectableValue;
	}

	/**
	 * @return the selectedValue
	 */
	public int getSelectedValue() {
		return selectedValue;
	}
}
