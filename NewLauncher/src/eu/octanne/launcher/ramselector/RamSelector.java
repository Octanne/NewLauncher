package eu.octanne.launcher.ramselector;

import java.io.File;

import fr.theshark34.openlauncherlib.util.Saver;

public class RamSelector
{

    /**
     * The RAM !
     */
    public static final String[] RAM_ARRAY = new String[]{"2Go", "3Go", "4Go", "5Go", "6Go", "7Go", "8Go", "16Go"};

    /**
     * The file where to save the ram
     */
    private Saver saver;

    /**
     * The RAM Selector with a file to save the RAM
     *
     * @param file The file where to save the RAM
     */
    public RamSelector(Saver saver)
    {
        this.saver = saver;
    }

    /**
     * Get the generated RAM arguments
     *
     * @return An array of two strings containing the arguments
     */
    public String[] getRamArguments()
    {
        int maxRam = Integer.parseInt("" + readRam()) * 1024;
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
    	return Integer.parseInt(saver.get("ram", "3"));
    }

    /**
     * Save the RAM
     */
    public void save()
    {
		saver.set("ram", String.valueOf(0));
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
