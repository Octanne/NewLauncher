package eu.octanne.launcher.ramselector;

import javax.swing.JFrame;

abstract class AbstractOptionFrame extends JFrame
{
    /**
     * The current RAM Selector
     */
    private RamSelector selector;

    /**
     * The Abstract Option Frame
     *
     * @param selector The current RAM Selector
     */
    public AbstractOptionFrame(RamSelector selector)
    {
        this.selector = selector;
    }

    /**
     * Return the current RAM Selector
     *
     * @return The current RAM selector
     */
    public RamSelector getSelector()
    {
        return selector;
    }

    /**
     * Return the selected RAM (as index of RamSelector.RAM_ARRAY)
     *
     * @return The selected RAM index
     *
     * @see #setSelectedIndex(int)
     */
    public abstract int getSelectedIndex();

    /**
     * Set the selected RAM (as index of RamSelector.RAM_ARRAY)
     *
     * @param index The selected RAM index
     *
     * @see #getSelectedIndex()
     */
    public abstract void setSelectedIndex(int index);
}
