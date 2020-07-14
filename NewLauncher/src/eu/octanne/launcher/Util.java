package eu.octanne.launcher;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Util {
	
	public static Logger logger = Logger.getLogger("LauncherLog");
    public static FileHandler fh;
	
    public static void closeLog() {
    	fh.close();
    }
    
	public static void log(String msg) {
		logger.info(msg);
	}
	
	public static void initLogger() {
	    try {  
	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler(Launcher.gameDirectory.getAbsolutePath()+"/launcher.log", 1000000000, 10000, false);  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        logger.info("Start log...");  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	    logger.info("Log START!");  
	}
	
	public static String doGET(String url, String type) {
		String result = "ERROR 404";
		HttpURLConnection con = null;
		try {

			URL myurl = new URL(url.replace(" ", "+"));
			con = (HttpURLConnection) myurl.openConnection();

			con.setRequestMethod("GET");

			StringBuilder content;

			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = in.readLine()) != null) {

					content.append(line);
					content.append(System.lineSeparator());
				}
				result = content.toString();
			}

			System.out.println(content.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		return result;
	}

	static void downloadFile(String pathStr) throws MalformedURLException, IOException {
		
		System.setProperty("Xmx", "1G");
		
		URLConnection con;
		DataInputStream dis;
		FileOutputStream fos;
		byte[] fileData;

		URL url = new URL(Launcher.updateURL+"/files/"+pathStr);
		con = url.openConnection();

		dis = new DataInputStream(con.getInputStream());

		fileData = new byte[con.getContentLength()];
		LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(fileData.length);
		for (int x = 0; x < fileData.length; x++) {
			fileData[x] = dis.readByte();
			LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(x);
		}

		File f = new File(Launcher.gameDirectory, pathStr);
		f.getParentFile().mkdirs(); //f.createNewFile();
		
		dis.close();
		fos = new FileOutputStream(f);
		fos.write(fileData);
		fos.close();
	}

	public static ArrayList<String> getUpdateFilesInfo(String url) {
		ArrayList<String> result = new ArrayList<String>();
		HttpURLConnection con = null;
		log("GET UpdateFileInfos :");
		try {

			URL myurl = new URL(url);
			con = (HttpURLConnection) myurl.openConnection();

			con.setRequestMethod("GET");

			try (BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()))) {

				String line;

				while ((line = in.readLine()) != null) {
					result.add(line);
					log(line);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			con.disconnect();
		}
		return result;
	}

	@SuppressWarnings("resource")
	public static String getHash(final File file, String hash)
			throws FileNotFoundException, IOException, NoSuchAlgorithmException {
		DigestInputStream stream = null;

		stream = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance(hash));
		final byte[] buffer = new byte[65536];

		int read = stream.read(buffer);
		while (read >= 1)
			read = stream.read(buffer);
		return String.format("%1$032x", new Object[] { new BigInteger(1, stream.getMessageDigest().digest()) });
	}
}
