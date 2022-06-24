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
*@lastEditedVersion 2.10
*/
public class Options implements Serializable {
  //game options
  private boolean game_endTurnAuto;
  private boolean game_forceQuit;
  /** language can be save as byte or String in properties &#38; options file.*/
  private byte game_language; // 0=eo; 1=fr; 2=en;
  private String game_pseudo;
  private boolean game_whaitBeforeLaunchGame;
  private boolean game_discordRP;

  //partie options
  private boolean partie_autoCleaning;

  //debug options
  // private boolean debug_error;
  // private boolean debug_alerte;
  // private boolean debug_info;
  private boolean debug_message;
  private boolean debug_performance;
  private boolean debug_gui;
  private boolean debug_paintHitBox;

  //sounds options
  private boolean sounds_music;
  private boolean sounds_sound;
  private byte sounds_musicVolume;
  private byte sounds_soundVolume;

  //gui options
  private byte gui_hide_buttonSizeZoom;
  private byte gui_global_buttonSizeAction;
  private byte gui_hide_buttonSizeTX;
  private boolean gui_partie_quickMovement;
  private boolean gui_partie_instantaneousMovement;
  private boolean gui_partie_orientedObjectOnMap;
  private byte gui_partie_maxMessageDisplay;
  private boolean gui_partie_drawSeeds;
  private boolean gui_partie_drawAllyCreatures;
  private boolean gui_partie_drawNeutralCreatures;
  private boolean gui_partie_drawEnemyCreatures;
  private boolean gui_partie_drawOnlyEatable;
  private boolean gui_partie_drawBlades;
  private boolean gui_pgo_drawGrid;
  private byte gui_global_borderButtonSize;
  private boolean gui_pgo_drawRelationsIcons;
  private byte gui_pgo_drawStatesIconsLevel;
  private int gui_global_fontSizeText;
  private int gui_global_fontSizeTitle;
  private String gui_global_fontText;
  private String gui_global_fontTitle;
  private boolean gui_global_fontTitlePersonalised;
  private boolean gui_global_fullscreen;
  private int gui_global_frameWidth;
  private int gui_global_frameHeight;
  private boolean gui_hide_loadingDuringMenus;
  private boolean gui_hide_keepFilesRotated;
  private int gui_partie_sizeOfMapLines;
  private boolean gui_partie_followAntAtStartTurn;
  private byte gui_hide_positionCase;
  private byte gui_partie_realisticSize;
  private boolean gui_hide_modeFPS;
  private int gui_global_fps;
  private boolean gui_global_animationEnable;
  private byte gui_pgo_antColorLevel;
  private boolean gui_pgo_drawAllAnthillColor;
  private boolean gui_pgo_drawPlayerMessagePanel;

  private Font font1;
  private Font font2;

  private SortedProperties properties=null;
  // CONSTRUCTORS --------------------------------------------------------------
  public Options(){}
  /**
  *{@summary Builder with only default properties.}<br>
  *@lastEditedVersion 2.7
  */
  public static Options newDefaultOptions(){
    Options op = new Options();
    op.properties = new SortedProperties(op.getDefaultProperties());
    op.propertiesToOptions();
    op.properties = null;
    return op;
  }
  // GET SET -------------------------------------------------------------------
  public byte getLanguage(){return game_language;}
  /**
  *{@summary Set language of Options &#38; Locale.}
  *@lastEditedVersion 2.19
  */
  public void setLanguage(byte x){
    game_language=x;
    String languageCode = chargerLesTraductions.getLanguage(x, Main.getFirstGame());
    if(x>-1 && !languageCode.equals(Locale.getDefault().getLanguage())) {
      Locale.setDefault(new Locale(languageCode));
    }
  }
  public void setLanguage(int x){setLanguage(str.iToBy(x));}
  public int getbuttonSizeZoom(){ return tailleBouton(gui_hide_buttonSizeZoom);}
  public void setbuttonSizeZoom(byte x){ gui_hide_buttonSizeZoom=x;}
  public int getbuttonSizeAction(){ return tailleBouton(gui_global_buttonSizeAction);}
  public void setbuttonSizeAction(byte x){ gui_global_buttonSizeAction=x;}
  public int getbuttonSizeTX(){ return tailleBouton(gui_hide_buttonSizeTX);}
  public void setbuttonSizeTX(byte x){ gui_hide_buttonSizeTX=x;}
  public boolean getQuickMovement(){ return gui_partie_quickMovement;}
  public void setQuickMovement(boolean b){ gui_partie_quickMovement = b;}

  public boolean getDrawSeeds(){ return gui_partie_drawSeeds;}
  public void setDrawSeeds(boolean b){ gui_partie_drawSeeds = b;}
  public boolean getDrawOnlyEatable(){ return gui_partie_drawOnlyEatable;}
  public void setDrawOnlyEatable(boolean b){ gui_partie_drawOnlyEatable = b;}
  public boolean getDrawBlades(){ return gui_partie_drawBlades;}
  public void setDrawDrawBlades(boolean b){ gui_partie_drawBlades = b;}
  public boolean getDrawEnemyCreatures(){ return gui_partie_drawEnemyCreatures;}
  public void setDrawEnemyCreatures(boolean b){ gui_partie_drawEnemyCreatures = b;}
  public boolean getDrawNeutralCreatures(){ return gui_partie_drawNeutralCreatures;}
  public void setDrawNeutralCreatures(boolean b){ gui_partie_drawNeutralCreatures = b;}
  public boolean getDrawAllyCreatures(){ return gui_partie_drawAllyCreatures;}
  public void setDrawAllyCreatures(boolean b){ gui_partie_drawAllyCreatures = b;}

  public boolean getInstantaneousMovement(){return gui_partie_instantaneousMovement;}
  public void setInstantaneousMovement(boolean b){gui_partie_instantaneousMovement=b;}
  public boolean getOrientedObjectOnMap(){ return gui_partie_orientedObjectOnMap;}
  public void setOrientedObjectOnMap(boolean b){gui_partie_orientedObjectOnMap=b;}
  public byte getMaxMessageDisplay(){ return gui_partie_maxMessageDisplay;}
  public void setMaxMessageDisplay(int x){ gui_partie_maxMessageDisplay=str.iToBy(x);}
  public boolean getDrawGrid(){ return gui_pgo_drawGrid;}
  public void setDrawGrid(boolean b){gui_pgo_drawGrid=b;}
  public boolean getForceQuit(){ return game_forceQuit;}
  public void setForceQuit(boolean b){ game_forceQuit=b;}
  public byte getBorderButtonSize(){ return gui_global_borderButtonSize;}
  public void setBorderButtonSize(int x){gui_global_borderButtonSize=str.iToBy(x);}
  public boolean getDrawRelationsIcons(){ return gui_pgo_drawRelationsIcons;}
  public void setDrawRelationsIcons(boolean b){gui_pgo_drawRelationsIcons=b;}
  public byte getDrawStatesIconsLevel(){ return gui_pgo_drawStatesIconsLevel;}
  public void setDrawStatesIconsLevel(byte b){gui_pgo_drawStatesIconsLevel=b;}
  public Font getFont1(){ return font1;}
  public Font getFont1(Double d){Font fTemp = new Font(getFontText(),Font.PLAIN,(int)(getFontSizeText()*d)); return fTemp;}
  public void setFont1(Font f){font1=f;}
  public Font getFont2(){return font2;}
  /**
  *{@summary Return a font that can display given String.}
  *@param s String to test displayability.
  *@lastEditedVersion 2.11
  */
  public Font getFontTitle(String s){
    if(getFont2()==null){return getFont1();}
    if(s==null){return getFont2();}
    for (char c : s.toCharArray()) {
      if(!getFont2().canDisplay(c)){return getFont1().deriveFont((float)getFontSizeTitle());}
    }
    return getFont2();
  }
  public void setFont2(Font f){font2=f;}
  public int getFontSizeText(){return gui_global_fontSizeText;}
  public void setFontSizeText(int x){gui_global_fontSizeText=x;}
  public int getFontSizeTitle(){return gui_global_fontSizeTitle;}
  public void setFontSizeTitle(int x){gui_global_fontSizeTitle=x;}
  public String getFontText(){ return gui_global_fontText;}
  public void setFontText(String s){gui_global_fontText=s;}
  public String getFontTitle(){ return gui_global_fontTitle;}
  public void setFontTitle(String s){gui_global_fontTitle=s;}
  public String getPseudo(){ return game_pseudo;}
  public void setPseudo(String s){game_pseudo=s;}
  public boolean getFullscreen(){ return gui_global_fullscreen;}
  public void setFullscreen(boolean b){ gui_global_fullscreen=b;}
  public int getFrameWidth(){return gui_global_frameWidth;}
  public void setFrameWidth(int x){gui_global_frameWidth=x;}
  public int getFrameHeight(){return gui_global_frameHeight;}
  public void setFrameHeight(int x){gui_global_frameHeight=x;}
  public boolean getLoadingDuringMenus(){ return gui_hide_loadingDuringMenus;}
  public void setLoadingDuringMenus(boolean b){gui_hide_loadingDuringMenus=b;}
  public boolean getKeepFilesRotated(){ return gui_hide_keepFilesRotated;}
  public void setKeepFilesRotated(boolean b){gui_hide_keepFilesRotated=b;}
  public boolean getWhaitBeforeLaunchGame(){ return game_whaitBeforeLaunchGame;}
  public void setWhaitBeforeLaunchGame(boolean b){game_whaitBeforeLaunchGame=b;}
  public boolean getDiscordRP(){ return game_discordRP;}
  public void setDiscordRP(boolean b){game_discordRP=b;}

  public boolean getMessage(){return debug_message;}
  public void setMessage(boolean b){debug_message=b;}
  public boolean getError(){return Info.PRINT_ERROR;}
  public void setError(boolean b){Info.PRINT_ERROR=b;}
  public boolean getWarning(){return Info.PRINT_WARNING;}
  public void setWarning(boolean b){Info.PRINT_WARNING=b;}
  public boolean getInfo(){return Info.PRINT_INFO;}
  public void setInfo(boolean b){Info.PRINT_INFO=b;}

  public boolean getPerformance(){return debug_performance;}
  public void setPerformance(boolean b){debug_performance=b;}
  public boolean getAffG(){return debug_gui;}
  public void setAffG(boolean b){debug_gui=b;}

  public boolean getPaintHitBox(){return debug_paintHitBox;}
  public void setPaintHitBox(boolean b){debug_paintHitBox=b;}
  public int getSizeOfMapLines(){ return gui_partie_sizeOfMapLines;}
  public void setSizeOfMapLines(int x){gui_partie_sizeOfMapLines=x;}
  public boolean getFollowAntAtStartTurn(){return gui_partie_followAntAtStartTurn;}
  public void setFollowAntAtStartTurn(boolean b){gui_partie_followAntAtStartTurn=b;}
  public byte getPositionCase(){return gui_hide_positionCase;}
  public void setPositionCase(byte x){gui_hide_positionCase=x;}
  public boolean getBMusic(){return sounds_music;}
  /**
  *{@summary Update this value &#38; MusicPlayer one.}
  *@lastEditedVersion 2.25
  */
  public void setBMusic(boolean b){
    sounds_music=b;
    if(Main.getMp()!=null){
      Main.getMp().setBMusic(b);
    }
  }
  public boolean getBSon(){return sounds_sound;}
  public void setBSon(boolean b){sounds_sound=b;}
  public byte getVolMusic(){return sounds_musicVolume;}
  /**
  *{@summary Update this value &#38; MusicPlayer one.}
  *@lastEditedVersion 2.25
  */
  public void setVolMusic(byte x){
    sounds_musicVolume=x;
    if(Main.getMp()!=null){
      Main.getMp().setVolMusic((int)x);
    }
  }
  public byte getVolSon(){return sounds_soundVolume;}
  public void setVolSon(byte x){sounds_soundVolume=x;}
  public byte getRealisticSize(){return gui_partie_realisticSize;}
  public void setRealisticSize(byte x){gui_partie_realisticSize=x;}public void setRealisticSize(int x){gui_partie_realisticSize=str.iToBy(x);}
  public boolean getAutoCleaning(){return partie_autoCleaning;}
  public void setAutoCleaning(boolean b){partie_autoCleaning=b;}
  public boolean getModeFPS(){return gui_hide_modeFPS;}
  public void setModeFPS(boolean b){gui_hide_modeFPS=b;}
  public int getFps(){return gui_global_fps;}
  public void setFps(int b){gui_global_fps=b;}
  public byte getAntColorLevel(){return gui_pgo_antColorLevel;}
  public void setAntColorLevel(byte x){gui_pgo_antColorLevel=x;}
  public boolean getDrawAllAnthillColor(){return gui_pgo_drawAllAnthillColor;}
  public void setDrawAllAnthillColor(boolean x){gui_pgo_drawAllAnthillColor=x;}
  public boolean getDrawPlayerMessagePanel(){return gui_pgo_drawPlayerMessagePanel;}
  public void setDrawPlayerMessagePanel(boolean x){gui_pgo_drawPlayerMessagePanel=x;}
  public boolean getEndTurnAuto(){return game_endTurnAuto;}
  public void setEndTurnAuto(boolean b){game_endTurnAuto=b;}
  public boolean getAnimationEnable(){return gui_global_animationEnable;}
  public void setAnimationEnable(boolean b){gui_global_animationEnable=b;}
  public boolean getFontTitlePersonalised(){return gui_global_fontTitlePersonalised;}
  public void setFontTitlePersonalised(boolean b){gui_global_fontTitlePersonalised=b;}
  public String getDateFormat(){return Time.getDateFormat();}
  public void setDateFormat(String s){Time.setDateFormat(s);}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Initialize Options.}<br>
  *It load properties from Option.md, transform it to all the Option value &#38; delete properties.
  *@lastEditedVersion 1.34
  */
  public void iniOptions(){
    iniProperties();
    propertiesToOptions(); //transform properties into Options.
    properties=null; //destory properties to save memory.
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
    optionToProperties(); // transform Options into properties.
    saveProperties();
    properties=null; //destory properties to save memory.
  }

  //private functions ----------------------------------------------------------
  /**
  *{@summary Initialize properties of the Options.}<br>
  *@lastEditedVersion 1.34
  */
  private void iniProperties(){
    properties = new SortedProperties(getDefaultProperties());
    try {
      InputStream is = Files.newInputStream(Path.of(Main.getFolder().getFolderMain()+"Options.md"));
      properties.load(is);
    }catch (IOException e) {
      if(!Main.getFirstGame()){
        erreur.erreur("Impossible de charger les options","Options par défaut choisie");
      }else{
        erreur.info("Options par défaut choisie");
      }
      saveDeflautProperties();
    }
  }
  /**
  *{@summary Save properties of the Options.}<br>
  *@lastEditedVersion 1.34
  */
  private void saveProperties(){
    try {
      OutputStream os = Files.newOutputStream(Path.of(Main.getFolder().getFolderMain()+"Options.md"));
      properties.store(os,"**Options file**\nEvery value can be edit here but variable have specific type. For example gui_partie_instantaneousMovement can only be set to true or false. Some value also need to be in a specific interval as sounds_musicVolume that should be in [0,100]. Most value should be out of interval save. But you may need to reset Options to default value by deleting this file if something goes wrong.");
    }catch (IOException e) {
      erreur.erreur("Impossible de sauvegarder les options","Options par défaut choisie");
    }
  }
  /**
  *{@summary Save default properties.}<br>
  *@lastEditedVersion 1.34
  */
  private void saveDeflautProperties(){
    Properties properties = getDefaultProperties();
    try {
      OutputStream os = Files.newOutputStream(Path.of(Main.getFolder().getFolderMain()+"Options.md"));
      properties.store(os,"**Options file**\nEvery values can be edit here but variable have specific type. For example gui_partie_instantaneousMovement can only be set to true or false. Some value also need to be in a specific interval as sounds_musicVolume that should be in [0,100]. Most value should be out of interval save. But you may need to reset Options to default value by deleting this file if something goes wrong.");
    }catch (IOException e) {
      erreur.erreur("Impossible de sauvegarder les options par défaut.");
    }
  }
  /**
  *{@summary get defaultProperties of the Options.}<br>
  *It can be used to save default Options or to repair Options.md file if something is mising.<br>
  *Value for version, language, fontSize &#38; butonSize depend of the user computer.<br>
  *@lastEditedVersion 2.16
  */
  private SortedProperties getDefaultProperties(){
    SortedProperties defaultProperties = new SortedProperties(34);
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
    defaultProperties.setProperty("debug_alerte","true");
    defaultProperties.setProperty("debug_error","true");
    defaultProperties.setProperty("debug_gui","false");
    defaultProperties.setProperty("debug_info","true");
    defaultProperties.setProperty("debug_message","false");
    defaultProperties.setProperty("debug_paintHitBox","false");
    defaultProperties.setProperty("debug_performance","false");
    defaultProperties.setProperty("game_endTurnAuto","false");
    defaultProperties.setProperty("game_forceQuit","false");
    defaultProperties.setProperty("game_language",Locale.getDefault().getLanguage());
    defaultProperties.setProperty("game_pseudo","");
    defaultProperties.setProperty("game_whaitBeforeLaunchGame","true");
    defaultProperties.setProperty("game_discordRP","false");
    defaultProperties.setProperty("gui_global_animationEnable","true");
    defaultProperties.setProperty("gui_global_dateFormat","yyyy/MM/dd HH:mm:ss");
    defaultProperties.setProperty("gui_global_borderButtonSize","4");
    defaultProperties.setProperty("gui_global_buttonSizeAction",""+t[1]);
    defaultProperties.setProperty("gui_global_fontSizeText",""+(int)(22*racio));
    defaultProperties.setProperty("gui_global_fontSizeTitle",""+(int)(60*racio));
    defaultProperties.setProperty("gui_global_fontText","Default");
    defaultProperties.setProperty("gui_global_fontTitle","Insektofobiya");
    defaultProperties.setProperty("gui_global_fontTitlePersonalised","true");
    defaultProperties.setProperty("gui_global_fps","60");
    defaultProperties.setProperty("gui_global_frameHeight",""+he);
    defaultProperties.setProperty("gui_global_frameWidth",""+wi);
    if(Os.getOs().isMac()){
      defaultProperties.setProperty("gui_global_fullscreen","false");
    }else{
      defaultProperties.setProperty("gui_global_fullscreen","true");
    }
    defaultProperties.setProperty("gui_hide_buttonSizeTX",""+t[0]);
    defaultProperties.setProperty("gui_hide_buttonSizeZoom",""+t[0]);
    defaultProperties.setProperty("gui_hide_keepFilesRotated","true");
    defaultProperties.setProperty("gui_hide_loadingDuringMenus","true");
    defaultProperties.setProperty("gui_hide_modeFPS","true");
    defaultProperties.setProperty("gui_hide_positionCase","0");
    defaultProperties.setProperty("gui_partie_drawAllyCreatures","true");
    defaultProperties.setProperty("gui_partie_drawEnemyCreatures","true");
    defaultProperties.setProperty("gui_partie_drawNeutralCreatures","true");
    defaultProperties.setProperty("gui_partie_drawOnlyEatable","true");
    defaultProperties.setProperty("gui_partie_drawBlades","true");
    defaultProperties.setProperty("gui_partie_drawSeeds","true");
    defaultProperties.setProperty("gui_partie_instantaneousMovement","false");
    defaultProperties.setProperty("gui_partie_maxMessageDisplay","10");
    defaultProperties.setProperty("gui_partie_orientedObjectOnMap","true");
    defaultProperties.setProperty("gui_partie_quickMovement","true");
    defaultProperties.setProperty("gui_partie_realisticSize","30");
    defaultProperties.setProperty("gui_partie_sizeOfMapLines","2");
    defaultProperties.setProperty("gui_partie_followAntAtStartTurn","true");
    defaultProperties.setProperty("gui_pgo_antColorLevel","1");
    defaultProperties.setProperty("gui_pgo_drawAllAnthillColor","false");
    defaultProperties.setProperty("gui_pgo_drawGrid","true");
    defaultProperties.setProperty("gui_pgo_drawPlayerMessagePanel","true");
    defaultProperties.setProperty("gui_pgo_drawRelationsIcons","true");
    defaultProperties.setProperty("gui_pgo_drawStatesIconsLevel","1");
    defaultProperties.setProperty("partie_autoCleaning","true");
    defaultProperties.setProperty("sounds_music","true");
    defaultProperties.setProperty("sounds_musicVolume","50");
    defaultProperties.setProperty("sounds_sound","false");
    defaultProperties.setProperty("sounds_soundVolume","50");
    return defaultProperties;
  }
  /**
  *{@summary tranform a byte into a button size.}<br>
  *@lastEditedVersion 1.20
  */
  private int tailleBouton(byte x){
    if(x>2 && x%20==0){return x;}
    if(x==0){ return 80;}
    if(x==-1){ return 40;}
    if(x==1){ return 160;}
    if(x==-2){ return 20;}
    if(x==2){ return 320;}
    erreur.erreur("La taille des boutons spécifiée n'est pas correcte.","La taille moyenne a été choisie par défaut");
    return 80;
  }
  /**
  *{@summary tranform properties into Options var.}<br>
  *@lastEditedVersion 2.7
  */
  private void propertiesToOptions(){
    try {
      setLanguage((byte)str.sToLThrows(properties.getProperty("game_language")));
    }catch (Exception e) {
      if(Main.getFolder()==null){return;}
      try {
        setLanguage(str.iToBy(chargerLesTraductions.getLanguage(properties.getProperty("game_language"))));
      }catch (Exception e2) {
        erreur.alerte("game_language can't be load from properties");
        setLanguage(2);
      }
    }
    setWarning(str.sToB(properties.getProperty("debug_alerte")));
    setError(str.sToB(properties.getProperty("debug_error")));
    debug_gui=str.sToB(properties.getProperty("debug_gui"));
    setInfo(str.sToB(properties.getProperty("debug_info")));
    debug_message=str.sToB(properties.getProperty("debug_message"));
    debug_paintHitBox=str.sToB(properties.getProperty("debug_paintHitBox"));
    debug_performance=str.sToB(properties.getProperty("debug_performance"));
    game_endTurnAuto=str.sToB(properties.getProperty("game_endTurnAuto"));
    game_forceQuit=str.sToB(properties.getProperty("game_forceQuit"));
    game_pseudo=properties.getProperty("game_pseudo");
    game_whaitBeforeLaunchGame=str.sToB(properties.getProperty("game_whaitBeforeLaunchGame"));
    game_discordRP=str.sToB(properties.getProperty("game_discordRP"));
    gui_global_animationEnable=str.sToB(properties.getProperty("gui_global_animationEnable"));
    setDateFormat(properties.getProperty("gui_global_dateFormat"));
    gui_global_borderButtonSize=str.sToBy(properties.getProperty("gui_global_borderButtonSize"));
    gui_global_buttonSizeAction=str.sToBy(properties.getProperty("gui_global_buttonSizeAction"));
    gui_global_fontSizeText=str.sToI(properties.getProperty("gui_global_fontSizeText"));
    gui_global_fontSizeTitle=str.sToI(properties.getProperty("gui_global_fontSizeTitle"));
    gui_global_fontText=properties.getProperty("gui_global_fontText");
    gui_global_fontTitle=properties.getProperty("gui_global_fontTitle");
    gui_global_fontTitlePersonalised=str.sToB(properties.getProperty("gui_global_fontTitlePersonalised"));
    gui_global_fps=str.sToI(properties.getProperty("gui_global_fps"));
    gui_global_frameHeight=str.sToI(properties.getProperty("gui_global_frameHeight"));
    gui_global_frameWidth=str.sToI(properties.getProperty("gui_global_frameWidth"));
    gui_global_fullscreen=str.sToB(properties.getProperty("gui_global_fullscreen"));
    gui_hide_buttonSizeTX=str.sToBy(properties.getProperty("gui_hide_buttonSizeTX"));
    gui_hide_buttonSizeZoom=str.sToBy(properties.getProperty("gui_hide_buttonSizeZoom"));
    gui_hide_keepFilesRotated=str.sToB(properties.getProperty("gui_hide_keepFilesRotated"));
    gui_hide_loadingDuringMenus=str.sToB(properties.getProperty("gui_hide_loadingDuringMenus"));
    gui_hide_modeFPS=str.sToB(properties.getProperty("gui_hide_modeFPS"));
    gui_hide_positionCase=str.sToBy(properties.getProperty("gui_hide_positionCase"));
    gui_partie_drawAllyCreatures=str.sToB(properties.getProperty("gui_partie_drawAllyCreatures"));
    gui_partie_drawEnemyCreatures=str.sToB(properties.getProperty("gui_partie_drawEnemyCreatures"));
    gui_partie_drawNeutralCreatures=str.sToB(properties.getProperty("gui_partie_drawNeutralCreatures"));
    gui_partie_drawOnlyEatable=str.sToB(properties.getProperty("gui_partie_drawOnlyEatable"));
    gui_partie_drawBlades=str.sToB(properties.getProperty("gui_partie_drawBlades"));
    gui_partie_drawSeeds=str.sToB(properties.getProperty("gui_partie_drawSeeds"));
    gui_partie_instantaneousMovement=str.sToB(properties.getProperty("gui_partie_instantaneousMovement"));
    gui_partie_maxMessageDisplay=str.sToBy(properties.getProperty("gui_partie_maxMessageDisplay"));
    gui_partie_orientedObjectOnMap=str.sToB(properties.getProperty("gui_partie_orientedObjectOnMap"));
    gui_partie_quickMovement=str.sToB(properties.getProperty("gui_partie_quickMovement"));
    gui_partie_realisticSize=str.sToBy(properties.getProperty("gui_partie_realisticSize"));
    gui_partie_sizeOfMapLines=str.sToI(properties.getProperty("gui_partie_sizeOfMapLines"));
    gui_partie_followAntAtStartTurn=str.sToB(properties.getProperty("gui_partie_followAntAtStartTurn"));
    gui_pgo_antColorLevel=str.sToBy(properties.getProperty("gui_pgo_antColorLevel"));
    gui_pgo_drawAllAnthillColor=str.sToB(properties.getProperty("gui_pgo_drawAllAnthillColor"));
    gui_pgo_drawGrid=str.sToB(properties.getProperty("gui_pgo_drawGrid"));
    gui_pgo_drawPlayerMessagePanel=str.sToB(properties.getProperty("gui_pgo_drawPlayerMessagePanel"));
    gui_pgo_drawRelationsIcons=str.sToB(properties.getProperty("gui_pgo_drawRelationsIcons"));
    gui_pgo_drawStatesIconsLevel=str.sToBy(properties.getProperty("gui_pgo_drawStatesIconsLevel"));
    partie_autoCleaning=str.sToB(properties.getProperty("partie_autoCleaning"));
    sounds_music=str.sToB(properties.getProperty("sounds_music"));
    sounds_musicVolume=str.sToBy(properties.getProperty("sounds_musicVolume"));
    sounds_sound=str.sToB(properties.getProperty("sounds_sound"));
    sounds_soundVolume=str.sToBy(properties.getProperty("sounds_soundVolume"));

    updateFont();
  }
  /**
  *{@summary update the 2 font.}<br>
  *@lastEditedVersion 2.16
  */
  public void updateFont(){
    // Main.iniFontFolder();
    if(gui_global_fontText.equals("Default")){
      font1=new Font("Default", Font.BOLD, gui_global_fontSizeText);
    }else{
      if(!Fonts.createFonts(gui_global_fontText)){
        if(!Main.getFirstGame()){
          erreur.alerte("fail to set font for text");
        }
        font1=new Font("Default", Font.BOLD, gui_global_fontSizeText);
      }else{
        font1=new Font(gui_global_fontText, Font.BOLD, gui_global_fontSizeText);
      }
    }
    if(!Fonts.createFonts(gui_global_fontTitle)){
      if(!Main.getFirstGame()){
        erreur.alerte("fail to set font for title");
      }
      font2=new Font(gui_global_fontText, Font.PLAIN, gui_global_fontSizeTitle);
    }else{
      font2=new Font(gui_global_fontTitle, Font.PLAIN, gui_global_fontSizeTitle);
    }
  }
  /**
  *{@summary tranform properties into Options var.}<br>
  *@lastEditedVersion 2.5
  */
  private void optionToProperties(){
    properties = new SortedProperties(getDefaultProperties());
    properties.setProperty("debug_alerte",""+getWarning());
    properties.setProperty("debug_error",""+getError());
    properties.setProperty("debug_info",""+getInfo());
    properties.setProperty("debug_gui",""+debug_gui);
    properties.setProperty("debug_message",""+debug_message);
    properties.setProperty("debug_paintHitBox",""+debug_paintHitBox);
    properties.setProperty("debug_performance",""+debug_performance);
    properties.setProperty("game_endTurnAuto",""+game_endTurnAuto);
    properties.setProperty("game_forceQuit",""+game_forceQuit);
    properties.setProperty("game_language",chargerLesTraductions.getLanguageAsString(game_language));
    properties.setProperty("game_pseudo",""+game_pseudo);
    properties.setProperty("game_whaitBeforeLaunchGame",""+game_whaitBeforeLaunchGame);
    properties.setProperty("game_discordRP",""+game_discordRP);
    properties.setProperty("gui_global_animationEnable",""+gui_global_animationEnable);
    properties.setProperty("gui_global_dateFormat",getDateFormat());
    properties.setProperty("gui_global_borderButtonSize",""+gui_global_borderButtonSize);
    properties.setProperty("gui_global_buttonSizeAction",""+gui_global_buttonSizeAction);
    properties.setProperty("gui_global_fontSizeText",""+gui_global_fontSizeText);
    properties.setProperty("gui_global_fontSizeTitle",""+gui_global_fontSizeTitle);
    properties.setProperty("gui_global_fontText",""+gui_global_fontText);
    properties.setProperty("gui_global_fontTitle",gui_global_fontTitle);
    properties.setProperty("gui_global_fontTitlePersonalised",""+gui_global_fontTitlePersonalised);
    properties.setProperty("gui_global_fps",""+gui_global_fps);
    properties.setProperty("gui_global_frameHeight",""+gui_global_frameHeight);
    properties.setProperty("gui_global_frameWidth",""+gui_global_frameWidth);
    properties.setProperty("gui_global_fullscreen",""+gui_global_fullscreen);
    properties.setProperty("gui_hide_buttonSizeTX",""+gui_hide_buttonSizeTX);
    properties.setProperty("gui_hide_buttonSizeZoom",""+gui_hide_buttonSizeZoom);
    properties.setProperty("gui_hide_keepFilesRotated",""+gui_hide_keepFilesRotated);
    properties.setProperty("gui_hide_loadingDuringMenus",""+gui_hide_loadingDuringMenus);
    properties.setProperty("gui_hide_modeFPS",""+gui_hide_modeFPS);
    properties.setProperty("gui_hide_positionCase",""+gui_hide_positionCase);
    properties.setProperty("gui_partie_drawAllyCreatures",""+gui_partie_drawAllyCreatures);
    properties.setProperty("gui_partie_drawEnemyCreatures",""+gui_partie_drawEnemyCreatures);
    properties.setProperty("gui_partie_drawNeutralCreatures",""+gui_partie_drawNeutralCreatures);
    properties.setProperty("gui_partie_drawOnlyEatable",""+gui_partie_drawOnlyEatable);
    properties.setProperty("gui_partie_drawBlades",""+gui_partie_drawBlades);
    properties.setProperty("gui_partie_drawSeeds",""+gui_partie_drawSeeds);
    properties.setProperty("gui_partie_instantaneousMovement",""+gui_partie_instantaneousMovement);
    properties.setProperty("gui_partie_maxMessageDisplay",""+gui_partie_maxMessageDisplay);
    properties.setProperty("gui_partie_orientedObjectOnMap",""+gui_partie_orientedObjectOnMap);
    properties.setProperty("gui_partie_quickMovement",""+gui_partie_quickMovement);
    properties.setProperty("gui_partie_realisticSize",""+gui_partie_realisticSize);
    properties.setProperty("gui_partie_sizeOfMapLines",""+gui_partie_sizeOfMapLines);
    properties.setProperty("gui_partie_followAntAtStartTurn",""+gui_partie_followAntAtStartTurn);
    properties.setProperty("gui_pgo_antColorLevel", ""+gui_pgo_antColorLevel);
    properties.setProperty("gui_pgo_drawAllAnthillColor", ""+gui_pgo_drawAllAnthillColor);
    properties.setProperty("gui_pgo_drawGrid",""+gui_pgo_drawGrid);
    properties.setProperty("gui_pgo_drawPlayerMessagePanel", ""+gui_pgo_drawPlayerMessagePanel);
    properties.setProperty("gui_pgo_drawRelationsIcons",""+gui_pgo_drawRelationsIcons);
    properties.setProperty("gui_pgo_drawStatesIconsLevel",""+gui_pgo_drawStatesIconsLevel);
    properties.setProperty("partie_autoCleaning",""+partie_autoCleaning);
    properties.setProperty("sounds_music",""+sounds_music);
    properties.setProperty("sounds_musicVolume",""+sounds_musicVolume);
    properties.setProperty("sounds_sound",""+sounds_sound);
    properties.setProperty("sounds_soundVolume",""+sounds_soundVolume);
  }
}
