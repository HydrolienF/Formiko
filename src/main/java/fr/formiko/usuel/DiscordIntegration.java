package fr.formiko.usuel;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.time.Instant;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DiscordIntegration {
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
		try(CreateParams params = new CreateParams()){
			params.setClientID(698611073133051974L);
			params.setFlags(CreateParams.getDefaultFlags());
			// Create the Core
			try(Core core = new Core(params)){
				// Create the Activity
				try(Activity activity = new Activity()){
					activity.setDetails("Running an example");
					activity.setState("and having fun");

					// Setting a start time causes an "elapsed" field to appear
					activity.timestamps().setStart(Instant.now());

					// We are in a party with 10 out of 100 people.
					activity.party().size().setMaxSize(100);
					activity.party().size().setCurrentSize(10);

					// Make a "cool" image show up
					activity.assets().setLargeImage("test");

					// Setting a join secret and a party ID causes an "Ask to Join" button to appear
					activity.party().setID("Party!");
					activity.secrets().setJoinSecret("Join!");

					// Finally, update the current activity to our activity
					core.activityManager().updateActivity(activity);
				}

        // core.runCallbacks();

				// Run callbacks forever
        // new Thread(() -> {
          while(true){
            core.runCallbacks();
            Temps.pause(16);
          }
        // }).start();
			}
		}
	}

  public static File downloadDiscordLibrary() throws IOException {
    // Find out which name Discord's library has (.dll for Windows, .so for Linux)
    String name = "discord_game_sdk";
    String suffix;

    String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);
    String arch = System.getProperty("os.arch").toLowerCase(Locale.ROOT);

    if(osName.contains("windows")){
      suffix = ".dll";
    }else if(osName.contains("linux")){
      suffix = ".so";
    }else if(osName.contains("mac os")){
      suffix = ".dylib";
    }else{
      throw new RuntimeException("cannot determine OS type: "+osName);
    }

    /*
    Some systems report "amd64" (e.g. Windows and Linux), some "x86_64" (e.g. Mac OS).
    At this point we need the "x86_64" version, as this one is used in the ZIP.
     */
    if(arch.equals("amd64"))
      arch = "x86_64";

    // Path of Discord's library inside the ZIP
    String zipPath = "lib/"+arch+"/"+name+suffix;

    // Open the URL as a ZipInputStream
    URL downloadUrl = new URL("https://dl-game-sdk.discordapp.net/2.5.6/discord_game_sdk.zip");
    ZipInputStream zin = new ZipInputStream(downloadUrl.openStream());

    // Search for the right file inside the ZIP
    ZipEntry entry;
    while((entry = zin.getNextEntry())!=null){
      if(entry.getName().equals(zipPath)){
        // Create a new temporary directory
        // We need to do this, because we may not change the filename on Windows
        File tempDir = new File(System.getProperty("java.io.tmpdir"), "java-"+name+System.nanoTime());
        if(!tempDir.mkdir())
          throw new IOException("Cannot create temporary directory");
        tempDir.deleteOnExit();

        // Create a temporary file inside our directory (with a "normal" name)
        File temp = new File(tempDir, name+suffix);
        temp.deleteOnExit();

        // Copy the file in the ZIP to our temporary file
        Files.copy(zin, temp.toPath());

        // We are done, so close the input stream
        zin.close();

        // Return our temporary file
        System.out.println("download done");//@a
        return temp;
      }
      // next entry
      zin.closeEntry();
    }
    zin.close();
    // We couldn't find the library inside the ZIP
    return null;
  }
}
