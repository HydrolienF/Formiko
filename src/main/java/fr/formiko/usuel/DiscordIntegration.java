package fr.formiko.usuel;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.erreur;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
*{@summary A discord intergration to have rich presence on discord app.}
*@lastEditedVersion 2.10
*@author Hydrolien
*/
public class DiscordIntegration {
  private static Activity activity;
  private static Core core;
  private static CreateParams params;
  private static boolean needToUpdateActivity=false;

  public static void setNeedToUpdateActivity(boolean b){needToUpdateActivity=b;}
  /**
  *{@summary Main function to launch the discord rich presence.}
  *@lastEditedVersion 2.10
  */
  public static void discordRPC() {
    if(!Main.getOp().getDiscordRP()){return;}
    File discordLibrary = null;
    try {
      discordLibrary = downloadDiscordLibrary();
    }catch (IOException e) {}
		if(discordLibrary == null){
      if(Main.getOs().isLinux()){return;} //TODO remove when #484 will be fix.
			erreur.erreur("Error downloading Discord SDK");
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
      // try {
      //   Thread.wait();
      // }catch (InterruptedException e) {}
      if(needToUpdateActivity){updateActivity();}
    }
	}

  private static void updateActivity(){
    needToUpdateActivity=false;
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
  /**
  *{@summary Dowload library depending of the OS.}
  *@lastEditedVersion 2.10
  */
  public static File downloadDiscordLibrary() throws IOException {
    // Find out which name Discord's library has (.dll for Windows, .so for Linux, .dylib for mac)
    String name = "discord_game_sdk";

    String suffix = "";
    Os os = Main.getOs();
    if(os.isWindows()){
      suffix = ".dll";
    }else if(os.isLinux()){
      suffix = ".so";
      //TODO #484 fix Error with discordRPC
      return null;
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
    erreur.info("downloading Discord Intergration file");
    while((entry = zin.getNextEntry())!=null){
      if(entry.getName().equals(zipPath)){
        // Copy the file in the ZIP to our temporary file
        Files.copy(zin, discordLibFile.toPath());
        // We are done, so close the input stream
        zin.close();
        // Return our temporary file
        erreur.info("download Discord Intergration file done");
        if(os.isLinux()){
          //TODO #484 fix Error gio: discord:///library/826437546024108077/launch: L’emplacement indiqué n’est pas pris en charge
          //https://github.com/NathaanTFM/discord-game-sdk-python/issues/3
          File f2 = new File(Main.getFolder().getFolderTemporary()+name);
          Files.copy(discordLibFile.toPath(), new FileOutputStream(f2));
          discordLibFile.delete();
          discordLibFile = f2;
        }
        return discordLibFile;
      }
      // next entry
      zin.closeEntry();
    }
    zin.close();
    // We couldn't find the library inside the ZIP
    erreur.alerte("download Discord Intergration faild");
    return null;
  }
}
