package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usual.types.str;
import fr.formiko.usual.*;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Properties;

/**
*{@summary Options class.}<br>
*It contain all globals options and can save it.<br>
*@author Hydrolien
*@lastEditedVersion 2.30
*/
public class FOptions extends fr.formiko.usual.Options {
  private String FILE_NAME="FOptions.md";
  // CONSTRUCTORS --------------------------------------------------------------
  public FOptions(){
    iniProperties();
  }
  /**
  *{@summary Initialize properties of the Options.}<br>
  *@lastEditedVersion 1.34
  */
  private void iniProperties(){
    setDefaultProperties();
    loadFromFile(Main.getFolder().getFolderMain()+FILE_NAME);
  }
  // GET SET -------------------------------------------------------------------
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    String propertiesList="";
    for (Object okey : getProperties().keySet()) {
      String key=okey.toString();
      if(!isParameter(key)){
        String cat = getString(key+".cat");
        String mainCat=cat.split("_")[0];
        if(!cat.equals("")){
          propertiesList+=getCatColor(mainCat);
        }
        propertiesList+=key+"="+getString(key);
        propertiesList+=" [";
        propertiesList+="cat="+cat;
        if(!getString(key+".min").equals("")) propertiesList+=", min="+getString(key+".min");
        if(!getString(key+".max").equals("")) propertiesList+=", max="+getString(key+".max");
        if(!getString(key+".minlen").equals("")) propertiesList+=", minlen="+getString(key+".maxlen");
        if(!getString(key+".maxlen").equals("")) propertiesList+=", maxlen="+getString(key+".maxlen");
        propertiesList+="]";
        propertiesList+="\n";
        if(!cat.equals("")){propertiesList+=color.NEUTRAL;}
      }
    }
    return propertiesList;
  }

  private String getCatColor(String mainCat){
    return switch(mainCat){
      case "gui":
      yield color.GREEN;
      case "game":
      yield color.PURPLE;
      case "sounds":
      yield color.BLUE;
      case "debug":
      yield color.YELLOW;
      default:
      yield color.RED;
    };
  }

  private boolean isParameter(String key){
    return (key.endsWith(".max")
        || key.endsWith(".min")
        || key.endsWith(".maxlen")
        || key.endsWith(".minlen")
        || key.endsWith(".cat"));
  }

  private void setDefaultProperties(){
    int wi=0; int he=0;
    try {
      Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
      wi = rec.width;
      he = rec.height;
    }catch (Exception e) {
      erreur.alerte("no screen size found");
    }
    Double racio = (wi+0.0)/1920;// si on a 1920 on change rien. Si c'est moins de pixel on réduit la police et vis versa pour plus.
    int t[]=new int[2];
    if(wi>=1920*2){ //plus de 2*
      t[0]=2;t[1]=2;//t[2]=1;
    }else if(wi>=1920*1.3){ //entre 1,3 et 2
      t[0]=1;t[1]=1;//t[2]=0;
    }else if(wi>=1920*0.8){ // entre 0.8 et 1.3
      t[0]=0;t[1]=1;//t[2]=-1;
    }else if(wi>=1920*0.5){ // entre 0.5 et 0.7
      t[0]=0;t[1]=0;//t[2]=-2;
    }else{ // moins de 0.5
      t[0]=-1;t[1]=-1;//t[2]=-2;
    }
    //setDefaultProperties
    set("alerte", true, "debug");
    set("error", true, "debug");
    set("gui", false, "debug");
    set("info", true, "debug");
    set("message", false, "debug");
    set("paintHitBox", false, "debug");
    set("performance", false, "debug");
    set("endTurnAuto", false, "game");
    set("forceQuit", false, "game");
    set("language", Locale.getDefault().getLanguage(), "game");
    set("pseudo", "", "game");
    set("whaitBeforeLaunchGame", true, "game");
    set("discordRP", false, "game");
    set("lastCheckedVersion", "0.0.0", "game", 5, null);
    set("animationEnable", true, "gui_global");
    set("dateFormat", "yyyy/MM/dd HH:mm:ss", "gui_global");
    set("borderButtonSize", 4, "gui_global", 0, null);
    set("buttonSizeAction", t[1], "gui_global", -2, 2);
    set("fontSizeText", (int)(22*racio), "gui_global", 5, null);
    set("fontSizeTitle", (int)(60*racio), "gui_global", 5, null);
    set("fontText", "Default", "gui_global");
    set("fontTitle", "Insektofobiya", "gui_global");
    set("fontTitlePersonalised", true, "gui_global");
    set("fps", 60, "gui_global", 10, 500);
    set("frameHeight", he, "gui_global", 10, null);
    set("frameWidth", wi, "gui_global", 10, null);
    boolean fs = true;
    if(Os.getOs().isMac()){
      fs=false;
    }
    set("fullscreen", fs, "gui_global");
    set("buttonSizeTX", t[0], "gui_hide", -2, 2);
    set("buttonSizeZoom", t[0], "gui_hide", -2, 2);
    set("keepFilesRotated", true, "gui_hide");
    set("loadingDuringMenus", true, "gui_hide");
    set("modeFPS", true, "gui_hide");
    set("positionSquare", 0, "gui_hide", 0, null);
    //TODO
    set("drawAllyCreatures", true, "gui_partie");
    set("drawEnemyCreatures", true, "gui_partie");
    set("drawNeutralCreatures", true, "gui_partie");
    set("drawOnlyEatable", true, "gui_partie");
    set("drawBlades", true, "gui_partie");
    set("drawSeeds", true, "gui_partie");
    set("instantaneousMovement", false, "gui_partie");
    set("maxMessageDisplay", 10, "gui_partie", 0, 100);
    set("orientedObjectOnMap", true, "gui_partie");
    set("quickMovement", true, "gui_partie");
    set("realisticSize", 30, "gui_partie", 0, 100);
    set("sizeOfMapLines", 2, "gui_partie", 0, 10);
    set("followAntAtStartTurn", true, "gui_partie");
    set("antColorLevel", 1, "gui_pgo", 0, 2);
    set("drawAllAnthillColor", false, "gui_pgo");
    set("drawGrid", true, "gui_pgo");
    set("drawPlayerMessagePanel", true, "gui_pgo");
    set("drawRelationsIcons", true, "gui_pgo");
    set("drawStatesIconsLevel", 1, "gui_pgo", 0, 4);
    set("autoCleaning", true, "partie");
    set("music", true, "sounds");
    set("musicVolume", 50, "sounds", 0, 100);
    set("sound", false, "sounds");
    set("soundVolume", 50, "sounds", 0, 100);
  }


  /**
  *{@summary Save Options.}<br>
  *It load properties from data of Options.java, transform it to properties &#38; then destory properties.
  *@param threaded true if we can do the save in an other tread
  *@lastEditedVersion 2.11
  */
  public void saveOptions(boolean threaded){
    if(threaded){
      new Thread(() -> {
        saveOp();
      }).start();
    }else{
      saveOp();
    }
  }
  public void saveOptions(){saveOptions(true);}
  /**
  *{@summary Save Options.}<br>
  *@lastEditedVersion 2.11
  */
  private void saveOp(){
    saveProperties();
  }

  /**
  *{@summary Save properties of the Options.}<br>
  *@lastEditedVersion 1.34
  */
  private void saveProperties(){
    try {
      OutputStream os = Files.newOutputStream(Path.of(Main.getFolder().getFolderMain()+FILE_NAME));
      getProperties().store(os,"**Options file**\nEvery value can be edit here but variable have specific type. For example instantaneousMovement can only be set to true or false. Some value also need to be in a specific interval as musicVolume that should be in [0,100]. Most value should be out of interval save. But you may need to reset Options to default value by deleting this file if something goes wrong.");
    }catch (IOException e) {
      erreur.erreur("Impossible de sauvegarder les options","Options par défaut choisie");
    }
  }
}
