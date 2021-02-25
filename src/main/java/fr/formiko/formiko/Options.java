package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.SortedProperties;
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
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
  private Font font1;// =new Font("Arial", Font.BOLD, 20);
  private Font font2;// =new Font("Arial", Font.BOLD, 60);
  private String pseudo;
  private boolean fullscreen;
  private boolean loadingDuringMenus;
  private boolean keepFilesRotated;
  private boolean whaitBeforeLaunchGame;
  private boolean debug_error;
  private boolean debug_alerte;
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
  *{@summary Save the Options in Options.txt.}<br>
  *@version 1.20
  */
  public void sauvegarder(){
    //on s'assure que le fichier n'existe plus pour éviter d'avoir a l'écraser plus tard.
    File f = new File(Main.getFolder().getFolderMain()+"Options.txt");
    if (f.exists()){ // si le fichier d'options existe.
      f.delete();
    }
    //on remplie toute les infos qu'on veut sauvegarder dans un gs.
    GString gs = new GString();
    gs.add("version compatible:"+Main.getVersionActuelle());
    gs.add("language:"+getLanguage());
    gs.ajouter("taille bouton zoom:"+getbuttonSizeZoom());
    gs.ajouter("taille bouton action:"+getTailleBoutonAction());
    gs.ajouter("taille bouton tint:"+getTailleBoutonTX());
    gs.ajouter("quickMovement:"+getMouvementRapide());
    gs.ajouter("instantaneousMovement:"+getDéplacementInstantané());
    gs.ajouter("orientedObjectOnMap:"+getElementSurCarteOrientéAprèsDéplacement());
    gs.ajouter("maxMessageDisplay:"+getNbrMessageAfficher());
    gs.ajouter("drawGrid:"+getDessinerGrille());
    gs.ajouter("forceQuit:"+getForcerQuitter());
    gs.ajouter("borderButtonSize:"+getBordureBouton());
    gs.ajouter("drawIcon:"+getDessinerIcone());
    gs.ajouter("fontSizeText:"+getTaillePolice1());
    gs.ajouter("fontSizeTitle:"+getTaillePolice2());
    gs.ajouter("fontText:"+getPolice());
    gs.ajouter("pseudo:"+getPseudo());
    gs.ajouter("fullscreen:"+getPleinEcran());
    gs.ajouter("chargement pendant les menu:"+getChargementPendantLesMenu());
    gs.ajouter("garder les graphsime tourné:"+getGarderLesGraphismesTourné());
    gs.ajouter("attendre après le chargement de la carte:"+getAttendreAprèsLeChargementDeLaCarte());
    gs.ajouter("afficher les étapes de résolution (débogage):"+getAffLesEtapesDeRésolution());
    gs.ajouter("afficher les performances(débogage):"+getAffLesPerformances());
    gs.ajouter("afficher les étape graphiques(débogage):"+getAffG());
    gs.ajouter("dimention des lignes de la carte:"+getDimLigne());
    gs.ajouter("positionCase:"+getPositionCase());
    gs.ajouter("musique:"+getBMusique());
    gs.ajouter("son:"+getBSon());
    gs.add("musicVolume:"+getVolMusique());
    gs.add("soundVolume:"+getVolSon());
    gs.add("realisticSize:"+getTailleRealiste());
    gs.add("autoCleaning:"+getAutoCleaning());
    //on rempli le fichier avec le GString.
    ecrireUnFichier.ecrireUnFichier(gs,Main.getFolder().getFolderStable()+"Options.txt");
  }
  public void iniOptions(){
    iniProperties();
    //propertiesToOptions(); //transforme properties into Options. //TODO
    //properties=null; //destory properties to save memory. //TODO

    System.out.println(properties);//@a
    saveOptions();//@a
  }
  public void saveOptions(){
    if(properties==null){
      //optionToProperties(); // transforme Options into properties. //TODO
    }
    saveProperties();
  }
  private void iniProperties(){
    properties = new SortedProperties(getDefaultProperties());
    try {
      InputStream is = Files.newInputStream(Path.of(Main.getFolder().getFolderMain()+"Options2.txt"));//TODO remove 2
      properties.load(is);
    }catch (IOException e) {
      erreur.erreur("Impossible de charger les options.","Option.iniProperties","Options par défaut choisie.");
    }
  }
  private void saveProperties(){
    try {
      OutputStream os = Files.newOutputStream(Path.of(Main.getFolder().getFolderMain()+"Options3.txt"));//TODO remove 3
      properties.store(os,"**Options file**\nEvery value can be edit here but variable have specific type. For exemple instantaneousMovement can only be set to true or false. Some value also need to be in a specific interval as musicVolume that sould be in [0,100]. Most value sould be out of intervale save. But you may need to reset Options to default value by deleting this file if something goes wrong.");
    }catch (IOException e) {
      erreur.erreur("Impossible de sauvegarder les options.","Option.saveProperties","Options par défaut choisie.");
    }
  }
  /**
  *{@summary get defaultProperties of the Options.}<br>
  *It can be used to save default Options or to repair Options.txt file if something is mising.<br>
  *Value for version, language, fontSize & butonSize depend of the user computer.<br>
  *@version 1.34
  */
  private Properties getDefaultProperties(){
    Properties defaultProperties = new Properties(34);
    int x = Toolkit.getDefaultToolkit().getScreenSize().width;
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
    defaultProperties.setProperty("fontTitle","");
    defaultProperties.setProperty("pseudo","");
    defaultProperties.setProperty("fullscreen","true");
    defaultProperties.setProperty("loadingDuringMenus","true");
    defaultProperties.setProperty("keepFilesRotated","true");
    defaultProperties.setProperty("whaitBeforeLaunchGame","true");
    defaultProperties.setProperty("debug_error","true");
    defaultProperties.setProperty("debug_alerte","true");
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
    return new SortedProperties(defaultProperties);
  }
}
