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
import java.util.List;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeSet;

/**
*{@summary Options class.}<br>
*It contain all globals options and can save it.<br>
*@author Hydrolien
*@lastEditedVersion 2.30
*/
public class FOptions extends fr.formiko.usual.Options {
  private String FILE_NAME="Options.md";

  private Font fontText;
  private Font fontTitle;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor that initialize all what class need.}<br>
  *@lastEditedVersion 2.30
  */
  public FOptions(){
    iniProperties();
  }
  /**
  *{@summary Initialize properties of the Options.}<br>
  *@lastEditedVersion 2.30
  */
  private void iniProperties(){
    setDefaultProperties();
    loadFromFile(Main.getFolder().getFolderMain()+FILE_NAME);
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Return the font for text.}
  *It will be set if it haven't been set yet.
  *@lastEditedVersion 2.30
  */
  public Font getFontText(){
    if(fontText==null){fontText=new Font(getString("fontText"),Font.PLAIN,getInt("fontSizeText"));}
    return fontText;
  }
  public Font getFontText(Double d){Font fTemp = new Font(getString("fontText"),Font.PLAIN,(int)(getInt("fontSizeText")*d)); return fTemp;}
  public void setFontText(Font f){fontText=f;}
  /**
  *{@summary Return the font for title.}
  *It will be set if it haven't been set yet.
  *@lastEditedVersion 2.30
  */
  public Font getFontTitle(){
    if(fontTitle==null){fontTitle=new Font(getString("fontTitle"),Font.PLAIN,getInt("fontSizeTitle"));}
    return fontTitle;}
  /**
  *{@summary Return a font that can display given String.}
  *@param stringToDisplay String to test displayability.
  *@lastEditedVersion 2.11
  */
  public Font getFontTitle(String stringToDisplay){
    if(getFontTitle()==null){return getFontText();}
    if(stringToDisplay==null){return getFontTitle();}
    for (char c : stringToDisplay.toCharArray()) {
      if(!getFontTitle().canDisplay(c)){return getFontText().deriveFont((float)getInt("fontSizeTitle"));}
    }
    return getFontTitle();
  }
  public void setFontTitle(Font f){fontTitle=f;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Return colored &#38; sorted properties.}<br>
  *@lastEditedVersion 2.30
  */
  public String toString(){
    String propertiesList="";
    for (Object okey : getProperties().keySet()) {
      String key=okey.toString();
      if(!isParameter(key)){
        String cat = getString(key+".cat");
        String mainCat=cat.split("_")[0];
        if(!cat.equals("") && !isHide(key)){
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
  /**
  *{@summary Return the color of a category.}<br>
  *@lastEditedVersion 2.30
  */
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
  /**
  *{@summary Initialize properties with default values.}<br>
  *@lastEditedVersion 2.30
  */
  private void setDefaultProperties(){
    int wi=0; int he=0;
    try {
      Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
      wi = rec.width;
      he = rec.height;
    }catch (Exception e) {
      erreur.alerte("no screen size found");
    }
    Double racio = wi/1920.0;// si on a 1920 on change rien. Si c'est moins de pixel on réduit la police et vis versa pour plus.
    //setDefaultProperties
    set("warning", true, "debug");
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
    set("discordRP", false, "game", true);
    set("lastCheckedVersion", "0.0.0", "game", true);
    set("animationEnable", true, "gui_global");
    set("dateFormat", "yyyy/MM/dd HH:mm:ss", "gui_global");
    set("borderButtonSize", 4, "gui_global", 0, null);
    set("buttonSizeAction", (int)(160*racio), "gui_global", 0, null);
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
    set("buttonSizeTX", (int)(80*racio), "gui_hide", 0, null, true);
    set("buttonSizeZoom", (int)(80*racio), "gui_hide", 0, null, true);
    set("keepFilesRotated", true, "gui_hide", true);
    set("loadingDuringMenus", true, "gui_hide", true);
    set("modeFPS", true, "gui_hide", true);
    set("positionSquare", 0, "gui_hide", 0, null, true);
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
    set("antColorLevel", 1, "gui_pgo", 0, 2, true);
    set("drawAllAnthillColor", false, "gui_pgo", true);
    set("drawGrid", true, "gui_pgo", true);
    set("drawPlayerMessagePanel", true, "gui_pgo", true);
    set("drawRelationsIcons", true, "gui_pgo", true);
    set("drawStatesIconsLevel", 1, "gui_pgo", 0, 4, true);
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
  *It save only editable properties.
  *@lastEditedVersion 2.30
  */
  private void saveProperties(){
    try {
      Main.startCh();
      OutputStream os = Files.newOutputStream(Path.of(Main.getFolder().getFolderMain()+FILE_NAME));
      Properties toSave = getEditableProperties();
      Main.endCh("filterProperties");
      Main.startCh();
      toSave.store(os,"**Options file**\nEvery value can be edit here but variable have specific type. For example instantaneousMovement can only be set to true or false. Some value also need to be in a specific interval as musicVolume that should be in [0,100]. Most value should be out of interval save. But you may need to reset Options to default value by deleting this file if something goes wrong.");
      Main.endCh("saveProperties");
    }catch (IOException e) {
      erreur.erreur("Unable to save options","Default option will be choose");
    }
  }
  /**
  *{@summary Return the property that can be edited.}<br>
  *@lastEditedVersion 2.30
  */
  private Properties getEditableProperties(){
    Properties editableProperties = new Properties();
    for (Object okey : getProperties().keySet()) {
      String key=okey.toString();
      if(!isParameter(key)){
        editableProperties.put(key, getString(key));
      }
    }
    return editableProperties;
  }

  /**
  *{@summary Specific action that need to be done before calling Option.set.}<br>
  *@lastEditedVersion 2.30
  */
  @Override
  public void set(String key, Object value){
    switch(key){
      case "musicVolume":{
        if(Main.getMp()!=null){
          Main.getMp().setVolMusic(Integer.parseInt(value.toString()));
        }
        break;
      }
      case "music":{
        if(Main.getMp()!=null){
          Main.getMp().setBMusic(Boolean.parseBoolean(value.toString()));
        }
        break;
      }
      case "dateFormat":{
        Time.setDateFormat(value.toString());
        break;
      }
      case "error":{
        Info.PRINT_ERROR=Boolean.parseBoolean(value.toString());
        break;
      }
      case "warning":{
        Info.PRINT_WARNING=Boolean.parseBoolean(value.toString());
        break;
      }case "info":{
        Info.PRINT_INFO=Boolean.parseBoolean(value.toString());
        break;
      }
      case "language":{
        String languageCode;
        try {
          int x=Integer.parseInt(value.toString());
          languageCode = chargerLesTraductions.getLanguage(x);
        }catch (NumberFormatException e) {
          languageCode=value.toString();
        }
        value=languageCode;
        if(!languageCode.equals(Locale.getDefault().getLanguage())) {
          Locale.setDefault(new Locale(languageCode));
        }
      }

    }
    super.set(key, value);
  }
  /**
  *{@summary Patch that call set() for every property after load from file.}<br>
  *Currently we need that to update some thing that will only be if we use set()
  *@lastEditedVersion 2.30
  */
  @Override
  public void loadFromFile(String fileName){
    super.loadFromFile(fileName);
    for (Object okey : getProperties().keySet()) {
      String key=okey.toString();
      set(key, getString(key));
    }
  }

  /**
  *{@summary Return the sorted list of all visible &#38; non parameter key for this cat.}<br>
  *@param cat name of the category
  *@lastEditedVersion 2.30
  */
  public List<String> getListKeyFromCat(String cat){
    LinkedList<String> list = new LinkedList<String>();
    if(cat==null){return list;}
    for (Object okey : getProperties().keySet()) {
      String key=okey.toString();
      //not a parameter, not hide & in cat :
      if(!isParameter(key) && !isHide(key) && cat.equals(getString(key+".cat"))){
        list.add(key);
      }
    }
    return list;
  }
  /**
  *{@summary Return the sorted list of all cat.}<br>
  *@lastEditedVersion 2.30
  */
  @SuppressWarnings("unchecked") //I know that it is a Set<String>
  public List<String> getListOfCat(){
    TreeSet<String> set = new TreeSet<String>();
    for (Object okey : getProperties().keySet()) {
      String key=okey.toString();
      if(key.endsWith(".cat")){
        set.add(getString(key));
      }
    }
    return new ArrayList(set);
  }
  /**
  *{@summary Return true if this key is hide.}<br>
  *@param key name of the option
  *@lastEditedVersion 2.30
  */
  public boolean isHide(String key){
    return Boolean.parseBoolean(getString(key+".hide"));
  }
}
