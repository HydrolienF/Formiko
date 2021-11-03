package fr.formiko.usuel;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;

import fr.formiko.formiko.Main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DiscordIntegration {
  private static Activity activity;
  private static Core core;
  private static CreateParams params;
  public static void discordRPC() {
    File discordLibrary = null;
    try {
      discordLibrary = downloadDiscordLibrary();
    }catch (IOException e) {}
		if(discordLibrary == null){
			System.err.println("Error downloading Discord SDK.");
			return;
		}
		// Initialize the Core
		Core.init(discordLibrary);

		// Set parameters for the Core
		params = new CreateParams();
		params.setClientID(826437546024108077L);
		params.setFlags(CreateParams.getDefaultFlags());
		// Create the Core
    core = new Core(params);
		// Create the Activity
		activity = new Activity();
		// activity.setDetails("Running an example");

		// Setting a start time causes an "elapsed" field to appear
		activity.timestamps().setStart(Instant.now());

		// We are in a party with 10 out of 100 people.
		// activity.party().size().setMaxSize(100);
		// activity.party().size().setCurrentSize(10);
    // Setting a join secret and a party ID causes an "Ask to Join" button to appear
    // activity.party().setID("Party!");
    // activity.secrets().setJoinSecret("Join!");

		// Finally, update the current activity to our activity
    updateActivity();

		// Run callbacks forever
    while(true){
      core.runCallbacks();
      Temps.pause(100);
    }
	}

  public static void updateActivity(){
    if(activity!=null){
      try {
        activity.setState(Main.getFolder().getCurentVersion());
        if(Main.getView().getActionGameOn()){
          activity.assets().setLargeImage(Main.getPartie().getCarte().getMapName());
          activity.assets().setLargeText(Main.getPartie().getCarte().getMapName());
          activity.assets().setSmallImage("ant");
          activity.assets().setSmallText("lasius Niger");
        }else{
          activity.assets().setLargeImage("f");
          activity.assets().setLargeText("Menu");
          activity.assets().setSmallImage("");
          activity.assets().setSmallText("");
        }
        core.activityManager().updateActivity(activity);
      }catch (Exception e) {
        erreur.alerte("Fail to update discord rich presence");
      }
    }
  }

  public static File downloadDiscordLibrary() throws IOException {
    // Find out which name Discord's library has (.dll for Windows, .so for Linux, .dylib for mac)
    String name = "discord_game_sdk";

    String suffix = "";
    Os os = Main.getOs();
    if(os.isWindows()){
      suffix = ".dll";
    }else if(os.isLinux()){
      suffix = ".so";
    }else if(os.isMac()){
      suffix = ".dylib";
    }else{
      erreur.erreur("cannot determine OS type");
    }

    File discordLibFile = new File(Main.getFolder().getFolderTemporary()+name+suffix);
    if(discordLibFile.isFile()){return discordLibFile;}

    // Path of Discord's library inside the ZIP
    String zipPath = "lib/"+os.getARCH()+"/"+name+suffix;

    // Open the URL as a ZipInputStream
    URL downloadUrl = new URL("https://dl-game-sdk.discordapp.net/2.5.6/discord_game_sdk.zip");
    ZipInputStream zin = new ZipInputStream(downloadUrl.openStream());

    // Search for the right file inside the ZIP
    ZipEntry entry;
    while((entry = zin.getNextEntry())!=null){
      if(entry.getName().equals(zipPath)){
        // Copy the file in the ZIP to our temporary file
        Files.copy(zin, discordLibFile.toPath());
        // We are done, so close the input stream
        zin.close();
        // Return our temporary file
        erreur.info("download Discord Intergration file done");
        return discordLibFile;
      }
      // next entry
      zin.closeEntry();
    }
    zin.close();
    // We couldn't find the library inside the ZIP
    return null;
  }
}
