package fr.formiko.usuel;

import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Chrono;
import fr.formiko.usuel.exceptions.MissingFolderException;
import fr.formiko.usuel.types.str;
import fr.formiko.views.View;

import java.io.File;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
*{@summary Class that have all link to all folder of formiko.}<br>
*You can acces to file by using getters.
*Ex : getFolderStable()+getFolderImages() will return the path to stable images.
*@author Hydrolien
*@version 2.7
*/
public class Folder {
  private String folderMain="data/";
  private String folderStable="stable/";
  private String folderTemporary="temporary/";
  private String folderResourcesPacks="resourcesPacks/";

  private String folderSaves="saves/";

  private String folderBin="bin/";
  private String folderImages="images/";
  private String folderSounds="sounds/";
  private String folderMusiques="musics/";
  private String folderMaps="maps/";
  private String folderLanguages="languages/";
  private String folderLevels="levels/";
  private String folderVideos="videos/";

  private int missingFolder;
  private boolean secondTime;

  public Folder(){
    secondTime=false;
    setFolderMain();
    if(Main.getOs().isWindows()){
      setFolderMain(System.getenv("APPDATA")+"/Formiko/");
    }else if(Main.getOs().isLinux()){
      setFolderMain("/"+System.getProperty("user.home")+"/Formiko/");
    }else if(Main.getOs().isMac()){
      //TODO fined & test a good path for mac.
      // setFolderMain(System.getProperty("user.home")+"/Formiko/");
    }
    // setFolderMain("");//always remove after test
    File folderM = new File(getFolderMain());
    if(!folderM.mkdirs() && !folderM.isDirectory()){
      erreur.alerte("can't create main folder : "+getFolderMain());
      setFolderMain();
      folderM = new File(getFolderMain());
      if(!folderM.mkdirs()){
        erreur.erreur("can't create main folder even in curent repository : "+getFolderMain());
      }
    }
  }
  // GET SET -------------------------------------------------------------------
	public String getFolderMain() {return folderMain+"data/";}
	public void setFolderMain(String folderMain) {this.folderMain = str.sToDirectoryName(folderMain);}
  public void setFolderMain() {setFolderMain("");}
	public String getFolderStable() {return getFolderMain()+folderStable;}
	public void setFolderStable(String folderStable) {this.folderStable = str.sToDirectoryName(folderStable);}
	public String getFolderTemporary() {return getFolderMain()+folderTemporary;}
	public void setFolderTemporary(String folderTemporary) {this.folderTemporary = str.sToDirectoryName(folderTemporary);}
	public String getFolderResourcesPacks() {return getFolderMain()+folderResourcesPacks;}
	public void setFolderResourcesPacks(String folderResourcesPacks) {this.folderResourcesPacks = str.sToDirectoryName(folderResourcesPacks);}

	public String getFolderSaves() {return getFolderMain()+folderSaves;}
	public void setFolderSaves(String folderSaves) {this.folderSaves = str.sToDirectoryName(folderSaves);}
  public String getFolderBin() {return folderBin;}
	public void setFolderBin(String folderBin) {this.folderBin = str.sToDirectoryName(folderBin);}

	public String getFolderImages() {return folderImages;}
	public void setFolderImages(String folderImages) {this.folderImages = str.sToDirectoryName(folderImages);}
	public String getFolderSounds() {return folderSounds;}
	public void setFolderSounds(String folderSounds) {this.folderSounds = str.sToDirectoryName(folderSounds);}
	public String getFolderMusiques() {return folderMusiques;}
	public void setFolderMusiques(String folderMusiques) {this.folderMusiques = str.sToDirectoryName(folderMusiques);}
	public String getFolderMaps() {return folderMaps;}
	public void setFolderMaps(String folderMaps) {this.folderMaps = str.sToDirectoryName(folderMaps);}
	public String getFolderLanguages() {return folderLanguages;}
	public void setFolderLanguages(String folderLanguages) {this.folderLanguages = str.sToDirectoryName(folderLanguages);}
	public String getFolderLevels() {return folderLevels;}
	public void setFolderLevels(String folderLevels) {this.folderLevels = str.sToDirectoryName(folderLevels);}
  public String getFolderVideos() {return folderVideos;}
	public void setFolderVideos(String folderVideos) {this.folderVideos = str.sToDirectoryName(folderVideos);}

  /**
  *{@summary Initialize missing folder if some folder are missing.}<br>
  *It will call download if main folder is missing.<br>
  *It will send an info if some were missing and an error if some unfixable folder were missing.
  *@param allowedDownolad true if we can download files.
  *@version 1.46
  */
  public int ini(boolean allowedDownolad){
    missingFolder=0;
    File f = new File(getFolderMain());
    if(newVersionAviable()){
      erreur.info("A new version, "+getLastStableVersion()+" is aviable at https://formiko.fr/download");
    }
    try{
      if(!f.exists() || f.listFiles().length==0){
        f.mkdirs();
        missingFolder++;
        if(allowedDownolad){throw new MissingFolderException("main");}
      }else if(needToUpdateDataVersion()){
        erreur.alerte("A compatible data version ("+getWantedDataVersion()+") is downloaded");
        if(allowedDownolad){downloadData();}
      }

      f = new File(getFolderMain()+"Options.md");
      if(!f.exists() || f.isDirectory()){
        Main.setPremierePartie(true);
      }

      f = new File(getFolderSaves());
      if(f.mkdir()){missingFolder++;}
      iniStable(allowedDownolad);
      iniTemporary();
      iniRessourcesPacks();
    }catch (MissingFolderException e) {
      erreur.erreur("an error occured when fixing file : "+e,"Download file from main repository");
      if(allowedDownolad){downloadData();}
      if(!secondTime){
        secondTime=true;
        return ini();
      }
    }
    if(missingFolder>0){
      erreur.info(missingFolder+" folders were missing & were add.");
    }
    return missingFolder;
  }
  public int ini(){return ini(true);}
  /**
  *{@summary Delete all unnecesary folders and files.}<br>
  *@version 1.46
  */
  public void cleanFolder(){
    File folder = new File(getFolderMain());
    for (File file : folder.listFiles() ) {
      if(!file.getName().equals("stable") && !file.getName().equals("README.md") && !file.getName().equals("Keys.txt")){
        fichier.deleteDirectory(file);
      }
    }
  }
  /**
  *{@summary Initialize stable missing folder.}<br>
  *@param allowedDownolad true if we can download files.
  *@version 1.46
  */
  private void iniStable(boolean allowedDownolad){
    File f = new File(getFolderStable());
    if(f.mkdir() || f.listFiles().length==0){
      missingFolder++;
      if(allowedDownolad){throw new MissingFolderException("stable");}
    }
    f = new File(getFolderStable()+getFolderBin());
    if(f.mkdir() || f.listFiles().length==0){
      missingFolder++;
      if(allowedDownolad){throw new MissingFolderException("stable bin");}
    }
    f = new File(getFolderStable()+getFolderImages());
    if(f.mkdir() || f.listFiles().length==0){
      missingFolder++;
      if(allowedDownolad){throw new MissingFolderException("stable images");}
    }
    f = new File(getFolderStable()+getFolderLanguages());
    if(f.mkdir() || f.listFiles().length==0){
      missingFolder++;
      if(allowedDownolad){throw new MissingFolderException("stable language");}
    }
    f = new File(getFolderStable()+getFolderLevels());
    if(f.mkdir() || f.listFiles().length==0){
      missingFolder++;
      if(allowedDownolad){throw new MissingFolderException("stable level");}
    }
    f = new File(getFolderStable()+getFolderMaps());
    if(f.mkdir() || f.listFiles().length==0){
      missingFolder++;
      if(allowedDownolad){throw new MissingFolderException("stable maps");}
    }
    f = new File(getFolderStable()+getFolderMusiques());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderStable()+getFolderSounds());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderStable()+getFolderVideos());
    if(f.mkdir()){missingFolder++;}
  }
  /**
  *{@summary Initialize temporary missing folder.}<br>
  *@version 1.37
  */
  private void iniTemporary(){
    File f = new File(getFolderTemporary());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderBin());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderImages());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderLanguages());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderLevels());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderMaps());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderMusiques());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderSounds());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderTemporary()+getFolderVideos());
    if(f.mkdir()){missingFolder++;}
  }
  /**
  *{@summary Initialize resourcesPacks missing folder.}<br>
  *@version 1.37
  */
  private void iniRessourcesPacks(){
    File f = new File(getFolderResourcesPacks());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderBin());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderImages());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderLanguages());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderLevels());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderMaps());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderMusiques());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderSounds());
    if(f.mkdir()){missingFolder++;}
    f = new File(getFolderResourcesPacks()+getFolderVideos());
    if(f.mkdir()){missingFolder++;}
  }
  /**
  *{@summary Download main data from github release.}<br>
  *It need Main.version to be correct to work.<br>
  *@version 2.7
  */
  public void downloadData(){
    //TODO #423
    View view = Main.getView();
    view.iniLauncher();
    Main.startCh();
    view.setDownloadingMessage("deleting old file");
    fichier.deleteDirectory(getFolderMain());
    Main.getView().setDownloadingValue(-5);
    view.setDownloadingMessage("creating main Folder");
    File f = new File(getFolderMain());
    f.mkdirs();
    Main.endCh("removeOldData");
    Main.getView().setDownloadingValue(0);
    Main.startCh();
    view.setDownloadingMessage("downloading game data");
    fichier.download("https://github.com/HydrolienF/Formiko/releases/download/"+getWantedDataVersion()+"/data.zip", getFolderMain()+"data.zip", true);
    Main.endCh("downloadData");
    Main.startCh();
    view.setDownloadingMessage("unziping game data");
    fichier.unzip(getFolderMain()+"data.zip",getFolderMain().substring(0,getFolderMain().length()-5));
    Main.endCh("unzipData");
    Main.getView().setDownloadingValue(105);
    System.gc();
    view.setDownloadingMessage("cleaning folders");
    if(!fichier.deleteDirectory(getFolderMain()+"data.zip")){
      erreur.alerte("unable to delete "+getFolderMain()+"data.zip");
    }
    Main.getView().setDownloadingValue(110);
    view.closeLauncher();
  }
  /**
  *{@summary Return true if data version is outdated or overdated.}<br>
  *Each game version have 1 linked data version, so we need to know witch one.
  *Data don't need to be update as many times as game is,
  *so when a new version of data is needed it is publish in github action.
  *A new data version always take curent game version as version number.
  *@version 2.7
  */
  public boolean needToUpdateDataVersion(){
    String wantedDataVersion = getWantedDataVersion();
    String curentDataVersion = getCurentDataVersion();
    if(wantedDataVersion.equals("null") || curentDataVersion.equals("null")){return false;}
    if(!wantedDataVersion.equals(curentDataVersion)){return true;}
    return false;
  }
  /**
  *{@summary Return true if a new version is aviable.}<br>
  *If last stable version > curent version.<br>
  *@version 2.7
  */
  public boolean newVersionAviable(){
    try {
      return isOver(getLastStableVersion(), getCurentVersion());
    }catch (Exception e) {
      return false;
    }
  }
  /**
  *{@summary Return true if v1 > v2.}<br>
  *@version 2.7
  */
  public boolean isOver(String v1, String v2){
    return str.isVersionOver(v1,v2);
  }
  /**
  *{@summary Return the version that game have.}<br>
  *@version 2.7
  */
  public String getCurentVersion(){
    return getXVersion(Paths.get(getFolderMain()+"version.json"), "formiko");
  }
  /**
  *{@summary Return the curent data version that game have.}<br>
  *@version 2.7
  */
  public String getCurentDataVersion(){
    return getXVersion(Paths.get(getFolderMain()+"version.json"), "data");
  }
  /**
  *{@summary Return the curent music version that game have.}<br>
  *@version 2.7
  */
  public String getCurentMusicVersion(){
    return getXVersion(Paths.get(getFolderMain()+"version.json"), "music");
  }
  /**
  *{@summary Return the data version that game want to have.}<br>
  *@version 2.7
  */
  public String getWantedDataVersion(){
    return getXVersion(getVersionJsonPath(), "data");
  }
  /**
  *{@summary Return the music version that game want to have.}<br>
  *@version 2.7
  */
  public String getWantedMusicVersion(){
    return getXVersion(getVersionJsonPath(), "music");
  }
  /**
  *{@summary Return the last stable version downloadable on the web site.}<br>
  *@version 2.7
  */
  public String getLastStableVersion(){
    String fileName = getFolderMain()+"vTemp.json";
    try {
      fichier.download("https://gist.githubusercontent.com/HydrolienF/c7dbc5d2d61b749ff6878e93afdaf53e/raw/version.json", fileName);
      return getXVersion(Paths.get(fileName), "lastStableVersion");
    }catch (Exception e) {
      erreur.alerte("Can't read last stable version");
      return "0.0.0";
    }finally {
      File f = new File(fileName);
      if(!fichier.deleteDirectory(f)){}
    }
  }
  /**
  *{@summary Return the version from path & name of the wanted version.}<br>
  *If it fail, it will return a defaut version.
  *@param pathToJson path to the .json file taht containt version
  *@param nameOfTheVersion name of the version
  *@return a version String as 1.49.12
  *@version 2.7
  */
  public String getXVersion(Path pathToJson, String nameOfTheVersion){
    try {
      // create a reader
      Reader reader = Files.newBufferedReader(pathToJson);
      // create parser
      JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
      // read customer details
      String version = (String) parser.get(nameOfTheVersion);
      return version;
    }catch (Exception e) {
      erreur.alerte("can't read music version");
      return "0.0.0";
    }
  }
  /**
  *{@summary return the path to version.json.}<br>
  *Curent version is in version.md.
  *@version 1.51
  */
  public static Path getVersionJsonPath(){
    File f = new File("version.json");
    if(f.exists()){
      return Paths.get(f.getPath());
    }
    f = new File("app/version.json");
    if(f.exists()){
      return Paths.get(f.getPath());
    }
    f = new File(System.getenv("ProgramFiles")+"/Formiko/app/version.json");
    if(f.exists()){
      return Paths.get(f.getPath());
    }
    erreur.alerte("Can't fined version.json path");
    return Paths.get("");
  }
  /**
  *{@summary Download music data from github release.}<br>
  *It need Main.version to be correct to work.<br>
  *@version 1.53
  */
  public void downloadMusicData(){
    Thread th = new ThDownloadMusicData(this);
    th.start();
  }
}
/**
*{@summary Download music data from github release in a Thread.}<br>
*It need Main.version to be correct to work.<br>
*@version 1.53
*@author Hydrolien
*/
class ThDownloadMusicData extends Thread {
  private Folder folder;
  public ThDownloadMusicData(Folder f){
    folder=f;
  }
  @Override
  public void run(){
    erreur.info("downloadMusicData");
    Main.startCh();
    fichier.download("https://github.com/HydrolienF/Formiko/releases/download/"+folder.getWantedMusicVersion()+"/music.zip",folder.getFolderMain()+"music.zip");
    Main.endCh("downloadMusicData");
    erreur.info("downloadMusicData done");
    Main.startCh();
    fichier.unzip(folder.getFolderMain()+"music.zip",folder.getFolderStable());
    Main.endCh("unzipMusicData");
    System.gc();
    if(!fichier.deleteDirectory(folder.getFolderMain()+"music.zip")){
      erreur.alerte("unable to delete "+folder.getFolderMain()+"music.zip");
    }
    try {
      Main.getMp().iniAvailableMusics();
    }catch (Exception e) {
      erreur.alerte("Can't refresh availableMusics");
    }
  }
}
