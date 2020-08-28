package eu.octanne.launcher.ramselector;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class OptionFrame extends AbstractOptionFrame
{
    /**
     * The Label "RAM : "
     */
    private JLabel ramLabel;

    /**
     * The RAM selection combo box
     */
    @SuppressWarnings("rawtypes")
	private JComboBox ramBox;

    /**
     * The Option Frame
     *
     * @param selector The current Ram Selector
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public OptionFrame(RamSelector selector)
    {
        super(selector);

        this.setTitle("Options");
        this.setResizable(false);
        this.setSize(275, 100);
        this.setLocationRelativeTo(null);
        this.setLayout(null);

        ramLabel = new JLabel("Ram : ");
        ramLabel.setBounds(15, 20, 45, 25);
        this.add(ramLabel);

        ramBox = new JComboBox(RamSelector.RAM_ARRAY);
        ramBox.setBounds(65, 20, 195, 25);
        this.add(ramBox);
    }

    @Override
    public int getSelectedIndex()
    {
        return ramBox.getSelectedIndex();
    }

    @Override
    public void setSelectedIndex(int index)
    {
        ramBox.setSelectedIndex(index);
    }
}
