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

	public static Logger logger;  
	
	public static void log(String msg) {
		logger.info(msg);
		System.out.println(msg);
	}
	
	public static void initLogger() {
		logger = Logger.getLogger("LaunchDebug");
	    FileHandler fh;  
	    try {  
	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("launcher.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();
	        fh.setFormatter(formatter);

	        logger.info("Start LOGGING");  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }
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
		URLConnection con;
		DataInputStream dis;
		FileOutputStream fos;
		byte[] fileData;

		String urlSTR = Launcher.updateURL + "/files/" + pathStr;
		URL url = new URL(urlSTR.replace(" ", "%20"));
		con = url.openConnection();

		dis = new DataInputStream(con.getInputStream());

		fileData = new byte[con.getContentLength()];
		for (int x = 0; x < fileData.length; x++) {
			fileData[x] = dis.readByte();
		}

		dis.close();

		File f = new File(Launcher.gameDirectory, pathStr);
		f.getParentFile().mkdirs();

		fos = new FileOutputStream(Launcher.gameDirectory.getAbsolutePath()+"/"+pathStr);
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
