package eu.octanne.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import fr.theshark34.openauth.AuthPoints;
import fr.theshark34.openauth.AuthenticationException;
import fr.theshark34.openauth.Authenticator;
import fr.theshark34.openauth.model.AuthAgent;
import fr.theshark34.openauth.model.response.AuthResponse;
import fr.theshark34.openlauncherlib.LaunchException;
import fr.theshark34.openlauncherlib.external.ExternalLaunchProfile;
import fr.theshark34.openlauncherlib.external.ExternalLauncher;
import fr.theshark34.openlauncherlib.minecraft.AuthInfos;
import fr.theshark34.openlauncherlib.minecraft.GameFolder;
import fr.theshark34.openlauncherlib.minecraft.GameInfos;
import fr.theshark34.openlauncherlib.minecraft.GameTweak;
import fr.theshark34.openlauncherlib.minecraft.GameType;
import fr.theshark34.openlauncherlib.minecraft.GameVersion;
import fr.theshark34.openlauncherlib.minecraft.MinecraftLauncher;

public class Launcher {

	public static final String launcherName = "McBoyard";
	public static final String launcherVersion = "V1";

	public static final String updateURL = "http://launcher.octanne.eu/Updater"; //"https://files.obeprod.fr/launcher/Updater"

	public static final GameVersion gameVersion = new GameVersion("1.12.2", GameType.V1_8_HIGHER);
	public static final GameInfos gameInfo = new GameInfos(launcherName, gameVersion, new GameTweak[] {GameTweak.OPTIFINE});
	private static AuthInfos authInfos;

	public static final File gameDirectory = gameInfo.getGameDir(); 
	public static final File crashDirectory = new File(gameDirectory, "crash-reports");

	public static void auth(String username, String password) throws AuthenticationException {
		Authenticator authenticator = new Authenticator(Authenticator.MOJANG_AUTH_URL, AuthPoints.NORMAL_AUTH_POINTS);
		AuthResponse response = authenticator.authenticate(AuthAgent.MINECRAFT, username, password, "");
		authInfos = new AuthInfos(response.getSelectedProfile().getName(), response.getAccessToken(), response.getSelectedProfile().getId());
	}

	public static void launch() throws LaunchException
	{
		/*InternalLaunchProfile profile = MinecraftLauncher.createInternalProfile(gameInfo, GameFolder.BASIC, authInfos);
		InternalLauncher launcher = new InternalLauncher(profile);
		LauncherFrame.getInstance().dispose();
		launcher.launch();*/

		ExternalLaunchProfile profile = MinecraftLauncher.createExternalProfile(gameInfo, GameFolder.BASIC, authInfos);
		profile.getVmArgs().addAll(Arrays.asList(LauncherFrame.getInstance().getLauncherPanel().getOptionFrame().getRamArguments()));
		ExternalLauncher launcher = new ExternalLauncher(profile);
		LauncherFrame.getInstance().setVisible(false);
		launcher.launch();
		System.exit(0);

	}

	public static void update() {
		LauncherFrame.getInstance().getLauncherPanel().setInfoText("En attente du serveur...");
		ArrayList<String> updateFilesInfo = Util.getUpdateFilesInfo(updateURL);
		ArrayList<String> paths = new ArrayList<String>();

		int i = 0;
		int max = updateFilesInfo.size();
		LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(updateFilesInfo.size());
		if(!updateFilesInfo.isEmpty()) {
			// Scan
			for(String fileInfo : updateFilesInfo) {
				// path : md5 : active
				String[] infos = fileInfo.split(":");
				String localHash = "NULL";
				boolean exist = true; 
				try {
					localHash = Util.getHash(new File(gameDirectory, infos[0]), "MD5");
				}catch (FileNotFoundException e){
					exist = false;
				}catch (NoSuchAlgorithmException | IOException e) {
					LauncherFrame.getInstance().getLauncherPanel().setInfoText("Erreur lors de la vérification.");
					e.printStackTrace();
				}
				if(!localHash.equals(infos[1]) && (infos[2].equals("1") || !exist)) {
					//System.out.println(infos[0] + " add to DL");
					paths.add(infos[0]);
				}
				i++;
				LauncherFrame.getInstance().getLauncherPanel().setInfoText("Vérification des fichiers - ("+i+"/"+max+")");
				LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(i);
			}
			//System.out.println(paths.size() + " files need to be DL");
			// Download
			i = 0;
			max = paths.size();
			LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setMaximum(updateFilesInfo.size());
			for(String path : paths) {
				try {
					Util.downloadFile(path);
					//System.out.println(paths + " has been DL");
				} catch (IOException e) {
					LauncherFrame.getInstance().getLauncherPanel().setInfoText("Erreur lors du téléchargement.");
					e.printStackTrace();
				}
				i++;
				LauncherFrame.getInstance().getLauncherPanel().setInfoText("Téléchargement des fichiers - ("+i+"/"+max+")");
				LauncherFrame.getInstance().getLauncherPanel().getProgressBar().setValue(i);
			}
		}else {
			LauncherFrame.getInstance().getLauncherPanel().setInfoText("Erreur lors de la vérification.");
		}
	}
}
