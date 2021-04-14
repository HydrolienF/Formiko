package fr.formiko.usuel;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.types.str;

import java.awt.Font;
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
*@version 1.34
*/
public class Options implements Serializable{
  private byte language=0; // 0=eo; 1=fr; 2=en;
  private byte buttonSizeZoom=0;
  private byte buttonSizeAction=0;
  private byte buttonSizeTX=0;
  private boolean quickMovement;
  private boolean instantaneousMovement;
  private boolean orientedObjectOnMap;
  private byte maxMessageDisplay;
  private boolean drawGrid;
  private boolean forceQuit;
  private byte borderButtonSize;
  private boolean drawIcon;
  private int fontSizeText;
  private int fontSizeTitle;
  private String fontText;
  private String fontTitle;
  private Font font1;
  private Font font2;
  private String pseudo;
  private boolean fullscreen;
  private boolean loadingDuringMenus;
  private boolean keepFilesRotated;
  private boolean whaitBeforeLaunchGame;
  private boolean debug_error;
  private boolean debug_alerte;
  private boolean debug_info;
  private boolean debug_message;
  private boolean debug_performance;
  private boolean debug_gui;
  private int sizeOfMapLines;
  private byte positionCase;
  private boolean music;
  private boolean sound;
  private byte musicVolume;
  private byte soundVolume;
  private byte realisticSize;
  private boolean autoCleaning;

  private SortedProperties properties=null;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public Options(){}
  // GET SET --------------------------------------------------------------------
  public byte getLanguage(){return language;}
  public void setLangue(byte x){language=x;} public void setLangue(int x){setLangue(str.iToBy(x));}
  public int getbuttonSizeZoom(){ return tailleBouton(buttonSizeZoom);}
  public void setTailleBoutonZoom(byte x){ buttonSizeZoom=x;}
  public int getTailleBoutonAction(){ return tailleBouton(buttonSizeAction);}
  public void setTailleBoutonAction(byte x){ buttonSizeAction=x;}
  public int getTailleBoutonTX(){ return tailleBouton(buttonSizeTX);}
  public void setTailleBoutonTX(byte x){ buttonSizeTX=x;}
  public boolean getMouvementRapide(){ return quickMovement;}
  public void setMouvementRapide(boolean b){ quickMovement = b;}
  public boolean getDéplacementInstantané(){return instantaneousMovement;}
  public void setDéplacementInstantané(boolean b){instantaneousMovement=b;}
  public boolean getElementSurCarteOrientéAprèsDéplacement(){ return orientedObjectOnMap;}
  public void setElementSurCarteOrientéAprèsDéplacement(boolean b){orientedObjectOnMap=b;}
  public byte getNbrMessageAfficher(){ return maxMessageDisplay;}
  public void setNbrMessageAfficher(int x){ maxMessageDisplay=str.iToBy(x);}
  public boolean getDessinerGrille(){ return drawGrid;}
  public void setDessinerGrille(boolean b){drawGrid=b; }
  public boolean getForcerQuitter(){ return forceQuit;}
  public void setForcerQuitter(boolean b){ forceQuit=b;}
  public byte getBordureBouton(){ return borderButtonSize;}
  public void setBordureBouton(int x){borderButtonSize=str.iToBy(x);}
  public boolean getDessinerIcone(){ return drawIcon;}
  public void setDessinerIcone(boolean b){drawIcon=b;}
  public Font getFont1(){ return font1;}
  public Font getFont1(Double d){ Font fTemp = new Font(getPolice(),Font.PLAIN,(int)(getTaillePolice1()*d)); return fTemp;}
  public void setFont1(Font f){ font1=f;}
  public Font getFont2(){ return font2;}
  public void setFont2(Font f){ font2=f;}
  public int getTaillePolice1(){ return fontSizeText;}
  public void setTaillePolice1(int x){ fontSizeText=x;}
  public int getTaillePolice2(){ return fontSizeTitle;}
  public void setTaillePolice2(int x){ fontSizeTitle=x;}
  public String getPolice(){ return fontText;}
  public void setPolice(String s){fontText=s;}
  public String getPseudo(){ return pseudo;}
  public void setPseudo(String s){pseudo=s;}
  public boolean getPleinEcran(){ return fullscreen;}
  public void setPleinEcran(boolean b){ fullscreen=b;}
  public boolean getChargementPendantLesMenu(){ return loadingDuringMenus;}
  public void setChargementPendantLesMenu(boolean b){loadingDuringMenus=b;}
  public boolean getGarderLesGraphismesTourné(){ return keepFilesRotated;}
  public void setGarderLesGraphismesTourné(boolean b){keepFilesRotated=b;}
  public boolean getAttendreAprèsLeChargementDeLaCarte(){ return whaitBeforeLaunchGame;}
  public void setAttendreAprèsLeChargementDeLaCarte(boolean b){whaitBeforeLaunchGame=b;}
  public boolean getAffLesEtapesDeRésolution(){return debug_message;}
  public void setAffLesEtapesDeRésolution(boolean b){debug_message=b;}
  public boolean getAffLesPerformances(){return debug_performance;}
  public void setAffLesPerformances(boolean b){debug_performance=b;}
  public boolean getAffG(){return debug_gui;}
  public void setAffG(boolean b){debug_gui=b;}
  public int getDimLigne(){ return sizeOfMapLines;}
  public void setDimLigne(int x){sizeOfMapLines=x;}
  public byte getPositionCase(){return positionCase;}
  public void setPositionCase(byte x){positionCase=x;}
  public boolean getBMusique(){return music;}
  public void setBMusique(boolean b){music=b;}
  public boolean getBSon(){return sound;}
  public void setBSon(boolean b){sound =b;}
  public byte getVolMusique(){return musicVolume;}
  public void setVolMusique(byte x){musicVolume=x;}
  public byte getVolSon(){return soundVolume;}
  public void setVolSon(byte x){soundVolume=x;}
  public byte getTailleRealiste(){return realisticSize;}
  public void setTailleRealiste(byte x){realisticSize=x;}public void setTailleRealiste(int x){realisticSize=str.iToBy(x);}
  public boolean getAutoCleaning(){return autoCleaning;}
  public void setAutoCleaning(boolean b){autoCleaning=b;}
  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary Initialize Options.}<br>
  *It load properties from Option.md, transform it to all the Option value &#38; delete properties.
  *@version 1.34
  */
  public void iniOptions(){
    iniProperties();
    propertiesToOptions(); //transform properties into Options.
    properties=null; //destory properties to save memory.
  }
  /**
  *{@summary Save Options.}<br>
  *It load properties from data of Options.java, transform it to properties &#38; then destory properties.
  *@version 1.34
  */
  public void saveOptions(){
    if(properties==null){
      optionToProperties(); // transform Options into properties.
    }
    saveProperties();
    properties=null;
  }

  //private functions ----------------------------------------------------------
  /**
  *{@summary Initialize properties of the Options.}<br>
  *@version 1.34
  */
  private void iniProperties(){
    properties = new SortedProperties(getDefaultProperties());
    try {
      InputStream is = Files.newInputStream(Path.of(Main.getFolder().getFolderMain()+"Options.md"));
      properties.load(is);
    }catch (IOException e) {
      erreur.erreur("Impossible de charger les options.","Options par défaut choisie.");
      saveDeflautProperties();
    }
  }
  /**
  *{@summary Save properties of the Options.}<br>
  *@version 1.34
  */
  private void saveProperties(){
    try {
      OutputStream os = Files.newOutputStream(Path.of(Main.getFolder().getFolderMain()+"Options.md"));
      properties.store(os,"**Options file**\nEvery value can be edit here but variable have specific type. For exemple instantaneousMovement can only be set to true or false. Some value also need to be in a specific interval as musicVolume that sould be in [0,100]. Most value sould be out of intervale save. But you may need to reset Options to default value by deleting this file if something goes wrong.");
    }catch (IOException e) {
      erreur.erreur("Impossible de sauvegarder les options.","Options par défaut choisie.");
    }
  }
  /**
  *{@summary Save default properties.}<br>
  *@version 1.34
  */
  private void saveDeflautProperties(){
    Properties properties = getDefaultProperties();
    try {
      OutputStream os = Files.newOutputStream(Path.of(Main.getFolder().getFolderMain()+"Options.md"));
      properties.store(os,"**Options file**\nEvery values can be edit here but variable have specific type. For exemple instantaneousMovement can only be set to true or false. Some value also need to be in a specific interval as musicVolume that sould be in [0,100]. Most value sould be out of intervale save. But you may need to reset Options to default value by deleting this file if something goes wrong.");
    }catch (IOException e) {
      erreur.erreur("Impossible de sauvegarder les options par défaut.");
    }
  }
  /**
  *{@summary get defaultProperties of the Options.}<br>
  *It can be used to save default Options or to repair Options.md file if something is mising.<br>
  *Value for version, language, fontSize &#38; butonSize depend of the user computer.<br>
  *@version 1.34
  */
  private SortedProperties getDefaultProperties(){
    SortedProperties defaultProperties = new SortedProperties(34);
    int x=0
    try {
      int x = Toolkit.getDefaultToolkit().getScreenSize().width;
    }catch (Exception e) {
      erreur.alerte("no screen size");
    }
    Double racio = (x+0.0)/1920;// si on a 1920 on change rien. Si c'est moins de pixel on réduit la police et vis versa pour plus.
    chargerLesTraductions.iniTLangue();
    int t[]=new int[2];
    if(x>=1920*2){ //plus de 2*
      t[0]=2;t[1]=2;//t[2]=1;
    }else if(x>=1920*1.3){ //entre 1,3 et 2
      t[0]=1;t[1]=1;//t[2]=0;
    }else if(x>=1920*0.8){ // entre 0.8 et 1.3
      t[0]=0;t[1]=1;//t[2]=-1;
    }else if(x>=1920*0.5){ // entre 0.5 et 0.7
      t[0]=0;t[1]=0;//t[2]=-2;
    }else{ // moins de 0.5
      t[0]=-1;t[1]=-1;//t[2]=-2;
    }
    //setDefaultProperties
    defaultProperties.setProperty("version",""+Main.getVersionActuelle());
    String lang = Locale.getDefault().getLanguage();
    defaultProperties.setProperty("language",""+chargerLesTraductions.getLanguage(lang));
    defaultProperties.setProperty("buttonSizeZoom",""+t[0]);
    defaultProperties.setProperty("buttonSizeAction",""+t[1]);
    defaultProperties.setProperty("buttonSizeTX",""+t[0]);
    defaultProperties.setProperty("quickMovement","true");
    defaultProperties.setProperty("instantaneousMovement","true");
    defaultProperties.setProperty("orientedObjectOnMap","true");
    defaultProperties.setProperty("maxMessageDisplay","10");
    defaultProperties.setProperty("drawGrid","true");
    defaultProperties.setProperty("forceQuit","false");
    defaultProperties.setProperty("borderButtonSize","2");
    defaultProperties.setProperty("drawIcon","true");
    defaultProperties.setProperty("fontSizeText",""+(int)(30*racio));
    defaultProperties.setProperty("fontSizeTitle",""+(int)(60*racio));
    defaultProperties.setProperty("fontText","Arial");
    defaultProperties.setProperty("fontTitle","Arial");
    defaultProperties.setProperty("pseudo","");
    defaultProperties.setProperty("fullscreen","true");
    defaultProperties.setProperty("loadingDuringMenus","true");
    defaultProperties.setProperty("keepFilesRotated","true");
    defaultProperties.setProperty("whaitBeforeLaunchGame","true");
    defaultProperties.setProperty("debug_error","true");
    defaultProperties.setProperty("debug_alerte","true");
    defaultProperties.setProperty("debug_info","true");
    defaultProperties.setProperty("debug_message","false");
    defaultProperties.setProperty("debug_performance","false");
    defaultProperties.setProperty("debug_gui","false");
    defaultProperties.setProperty("sizeOfMapLines","2");
    defaultProperties.setProperty("music","false");
    defaultProperties.setProperty("sound","false");
    defaultProperties.setProperty("musicVolume","50");
    defaultProperties.setProperty("soundVolume","50");
    defaultProperties.setProperty("realisticSize","30");
    defaultProperties.setProperty("autoCleaning","true");
    defaultProperties.setProperty("positionCase","0");
    return defaultProperties;
  }
  /**
  *{@summary tranform a byte into a button size.}<br>
  *@version 1.20
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
  *@version 1.34
  */
  private void propertiesToOptions(){
    language=str.sToBy(properties.getProperty("language"));
    buttonSizeZoom=str.sToBy(properties.getProperty("buttonSizeZoom"));
    buttonSizeAction=str.sToBy(properties.getProperty("buttonSizeAction"));
    buttonSizeTX=str.sToBy(properties.getProperty("buttonSizeTX"));
    quickMovement=str.sToB(properties.getProperty("quickMovement"));
    instantaneousMovement=str.sToB(properties.getProperty("instantaneousMovement"));
    orientedObjectOnMap=str.sToB(properties.getProperty("orientedObjectOnMap"));
    maxMessageDisplay=str.sToBy(properties.getProperty("maxMessageDisplay"));
    drawGrid=str.sToB(properties.getProperty("drawGrid"));
    forceQuit=str.sToB(properties.getProperty("forceQuit"));
    borderButtonSize=str.sToBy(properties.getProperty("borderButtonSize"));
    drawIcon=str.sToB(properties.getProperty("drawIcon"));
    fontSizeText=str.sToI(properties.getProperty("fontSizeText"));
    fontSizeTitle=str.sToI(properties.getProperty("fontSizeTitle"));
    fontText=properties.getProperty("fontText");
    fontTitle=properties.getProperty("fontTitle");
    font1=new Font(fontText, Font.BOLD, fontSizeText);
    font2=new Font(fontTitle, Font.BOLD, fontSizeTitle);
    pseudo=properties.getProperty("pseudo");
    fullscreen=str.sToB(properties.getProperty("fullscreen"));
    loadingDuringMenus=str.sToB(properties.getProperty("loadingDuringMenus"));
    keepFilesRotated=str.sToB(properties.getProperty("keepFilesRotated"));
    whaitBeforeLaunchGame=str.sToB(properties.getProperty("whaitBeforeLaunchGame"));
    debug_error=str.sToB(properties.getProperty("debug_error"));
    debug_alerte=str.sToB(properties.getProperty("debug_alerte"));
    debug_info=str.sToB(properties.getProperty("debug_info"));
    debug_message=str.sToB(properties.getProperty("debug_message"));
    debug_performance=str.sToB(properties.getProperty("debug_performance"));
    debug_gui=str.sToB(properties.getProperty("debug_gui"));
    sizeOfMapLines=str.sToI(properties.getProperty("sizeOfMapLines"));
    positionCase=str.sToBy(properties.getProperty("positionCase"));
    music=str.sToB(properties.getProperty("music"));
    sound=str.sToB(properties.getProperty("sound"));
    musicVolume=str.sToBy(properties.getProperty("musicVolume"));
    soundVolume=str.sToBy(properties.getProperty("soundVolume"));
    realisticSize=str.sToBy(properties.getProperty("realisticSize"));
    autoCleaning=str.sToB(properties.getProperty("autoCleaning"));
  }
  /**
  *{@summary tranform properties into Options var.}<br>
  *@version 1.34
  */
  private void optionToProperties(){
    properties = new SortedProperties(getDefaultProperties());
    properties.setProperty("language",""+language);
    properties.setProperty("buttonSizeZoom",""+buttonSizeZoom);
    properties.setProperty("buttonSizeAction",""+buttonSizeAction);
    properties.setProperty("buttonSizeTX",""+buttonSizeTX);
    properties.setProperty("quickMovement",""+quickMovement);
    properties.setProperty("instantaneousMovement",""+instantaneousMovement);
    properties.setProperty("orientedObjectOnMap",""+orientedObjectOnMap);
    properties.setProperty("maxMessageDisplay",""+maxMessageDisplay);
    properties.setProperty("drawGrid",""+drawGrid);
    properties.setProperty("forceQuit",""+forceQuit);
    properties.setProperty("borderButtonSize",""+borderButtonSize);
    properties.setProperty("drawIcon",""+drawIcon);
    properties.setProperty("fontSizeText",""+fontSizeText);
    properties.setProperty("fontSizeTitle",""+fontSizeTitle);
    properties.setProperty("fontText",""+fontText);
    properties.setProperty("fontTitle",""+fontTitle);
    properties.setProperty("pseudo",""+pseudo);
    properties.setProperty("fullscreen",""+fullscreen);
    properties.setProperty("loadingDuringMenus",""+loadingDuringMenus);
    properties.setProperty("keepFilesRotated",""+keepFilesRotated);
    properties.setProperty("whaitBeforeLaunchGame",""+whaitBeforeLaunchGame);
    properties.setProperty("debug_error",""+debug_error);
    properties.setProperty("debug_alerte",""+debug_alerte);
    properties.setProperty("debug_message",""+debug_message);
    properties.setProperty("debug_performance",""+debug_performance);
    properties.setProperty("debug_gui",""+debug_gui);
    properties.setProperty("sizeOfMapLines",""+sizeOfMapLines);
    properties.setProperty("positionCase",""+positionCase);
    properties.setProperty("music",""+music);
    properties.setProperty("sound",""+sound);
    properties.setProperty("musicVolume",""+musicVolume);
    properties.setProperty("soundVolume",""+soundVolume);
    properties.setProperty("realisticSize",""+realisticSize);
    properties.setProperty("autoCleaning",""+autoCleaning);
  }
}
