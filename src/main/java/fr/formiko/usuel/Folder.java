package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.types.str;

import java.io.File;

/**
*{@summary Class that have all link to all folder of formiko.}<br>
*You can acces to file by using getters.
*Ex : getFolderStable()+getFolderImages() will return the path to stable images.
*@author Hydrolien
*@version 1.37
*/
public class Folder{
  private String folderMain="data/";
  private String folderStable="stable/";
  private String folderTemporary="temporary/";
  private String folderResourcesPacks="resourcesPacks/";

  private String folderSaves="saves/";

  private String folderBin="bin/";
  private String folderImages="images/";
  private String folderSounds="sounds/";
  private String folderMusiques="musiques/";
  private String folderMaps="maps/";
  private String folderLanguages="languages/";
  private String folderLevels="levels/";
  private String folderVideos="videos/";

  private int missingFolder;

  public Folder(){
    // if(Main.getOs().isWindows()){
    //   setFolderMain(System.getenv("APPDATA")+"/Formiko/data/");
    // }
  }
  // GET SET -------------------------------------------------------------------
	public String getFolderMain() {return folderMain;}
	public void setFolderMain(String folderMain) {this.folderMain = str.sToDirectoryName(folderMain);}
  public void setFolderMain() {setFolderMain("data/");}
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
  *@version 1.37
  */
  public int ini(){
    missingFolder=0;
    File f = new File(getFolderMain());
    if(!f.exists()){
      erreur.erreurMissingFolder("main");
      missingFolder++;
      //TODO test that it work & print an error only if it fail.
      downloadData();
    }

    f = new File(getFolderMain()+"Options.md");
    if(!f.exists() || f.isDirectory()){
      Main.setPremierePartie(true);
    }

    f = new File(getFolderSaves());
    if(f.mkdir()){missingFolder++;}
    iniStable();
    iniTemporary();
    iniRessourcesPacks();
    if(missingFolder>0){
      erreur.info(missingFolder+" folders were missing & were add.");
    }
    return missingFolder;
  }
  /**
  *{@summary Delete all unnecesary folders and files.}<br>
  *@version 1.37
  */
  public void cleanFolder(){
    File f = new File(getFolderTemporary());
    fichier.deleteDirectory(f);
    f = new File(getFolderResourcesPacks());
    fichier.deleteDirectory(f);
    f = new File(getFolderSaves());
    fichier.deleteDirectory(f);
    f = new File(getFolderMain()+"Keys.txt");
    //f.delete();
    f = new File(getFolderMain()+"Options.md");
    f.delete();
  }
  /**
  *{@summary Initialize stable missing folder.}<br>
  *@version 1.37
  */
  private void iniStable(){
    File f = new File(getFolderStable());
    if(f.mkdir()){erreur.erreurMissingFolder("stable");missingFolder++;}
    f = new File(getFolderStable()+getFolderBin());
    if(f.mkdir()){erreur.erreurMissingFolder("stable bin");missingFolder++;}
    f = new File(getFolderStable()+getFolderImages());
    if(f.mkdir()){erreur.erreurMissingFolder("stable images");missingFolder++;}
    f = new File(getFolderStable()+getFolderLanguages());
    if(f.mkdir()){erreur.erreurMissingFolder("stable language");missingFolder++;}
    f = new File(getFolderStable()+getFolderLevels());
    if(f.mkdir()){erreur.erreurMissingFolder("stable level");missingFolder++;}
    f = new File(getFolderStable()+getFolderMaps());
    if(f.mkdir()){erreur.erreurMissingFolder("stable maps");missingFolder++;}
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
  */
  public void downloadData(){
    File f = new File(getFolderMain());
    f.mkdirs();
    fichier.download("https://github.com/HydrolienF/Formiko/releases/download/"+Main.getVersionActuelle()+"/data.zip",getFolderMain()+"data.zip");
    fichier.unzip(getFolderMain()+"data.zip",getFolderMain().substring(0,getFolderMain().length()-5));
    fichier.deleteDirectory(getFolderMain()+"data.zip");
  }
}
