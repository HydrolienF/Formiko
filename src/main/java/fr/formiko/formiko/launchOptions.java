package fr.formiko.formiko;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import fr.formiko.usual.*;
import fr.formiko.fusual.FFolder;
import fr.formiko.usual.debug;
import fr.formiko.usual.Usual;
import fr.formiko.usual.erreur;
import fr.formiko.usual.fichier;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.media.audio.*;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.tableau;
import fr.formiko.usual.Translation;
import fr.formiko.usual.types.str;
import fr.formiko.views.ViewNull;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

//default File since 1.39.0
/**
*{@summary Launch options at Launch of the game.}<br>
*"-" options are launch with normal game when other options are launch alone (Game is never start).<br>
*For exemple ./run.sh -cli will launch the game in cli mode when ./run.sh stats will never launch game but update statistique value on a file.<br>
*@author Hydrolien
*@lastEditedVersion 1.44
*/
public class launchOptions {
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Launch a "-" options with the game.}<br>
  *-options affect game but launch it, when non "-" options never launch game.<br>
  *Here is the list of the aviable -options.<br>
  *<ul>
  *<li>-d Print all the debugs infos.
  *<li>-p Print performances info for long action.
  *<li>-g Print the graphics debugs infos.
  *<li>-rg Reload all the graphics saved in data/temporary/images.
  *<li>-cli Launch game but in Console Line Interface.
  *</ul>
  *@lastEditedVersion 1.44
  */
  public static void launchOptionsMinor(String stringOptions){
    String t [] = stringOptions.split("=");
    stringOptions=t[0];
    String opArg="";
    if(t.length>1){opArg=t[1];}
    switch(stringOptions){
      case "q":
      erreur.setMuet(true);
      break;
      case "d":
      debug.setMessage(true);
      break;
      case "p":
      debug.setPerformance(true);
      break;
      case "g":
      debug.setAffG(true);
      break;
      case "rg":
      case "reload--graphics":
      Main.initialisation();
      Main.getFop().set("keepFilesRotated", false);
      break;
      case "cli":
      Main.setModeCLI(true);
      break;
      case "lt":
      case "launchTuto":
      //TODO TOFIX
      Main.setTuto(true);
      Main.setFirstGame(true);
      Main.dontOpenMenuFirst();
      break;
      case "ld":
      case "launchDefaultGame":
      Main.dontOpenMenuFirst();
      break;
      case "ls":
      case "launchScript":
      Partie.setScript(opArg);
      break;
      case "v":
      case "version":
      case "-version":
      printVersion();
      System.exit(0);
      break;
      case "lfl", "launchFromLauncher":
      Main.setLaunchFromLauncher(true);
      break;
      default:
      erreur.alerte("Unknow cli options : "+stringOptions);
    }
  }
  /**
  *{@summary Launch a major options without launching game.}
  *@lastEditedVersion 2.28
  */
  public static void launchOptionsMajor(String args[]){
    switch (args[0]){
      case "trad":{
        Main.initialisation();
        if(args.length>1){
          tradCmd(args[1]);
        }else{
          tradCmd("./data/stable/languages");
        }
        break;
      }
      case "tradChar":{
        if(args.length<3){return;}
        Main.initialisation();
        chargerLesTraductions.iniTLangue();
        boolean b=true;
        if(args.length>3){b=str.sToB(args[3]);}
        if(args[1].equals("all")){
          int len = chargerLesTraductions.getTLangue().length;
          for (int i=0; i<len; i++) {
            tradCharCmd(i, args[2], b);
          }
        }else{
          int id = str.sToI(args[1]); //TODO remove ERROR print if it fail.
          if(id==-1){id=chargerLesTraductions.getLanguage(args[1]);}
          tradCharCmd(id, args[2], b);
        }
        break;
      }
      case "testFont":{
        if(args.length<2){
          args=new String[2];
          args[1]="all";
        }
        Main.initialisation();
        chargerLesTraductions.iniTLangue();
        if(args[1].equals("all")){
          // String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
          Font fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
          for (int i = 0; i < fonts.length; i++) {
            canDisplayLanguages(fonts[i].getName());
            }
        }else{
          canDisplayLanguages(args[1]);
        }
        break;
      }
      case "son":{
        music();
        break;
      }
      case "op":{
        Os.setOs(new Os());
        Main.setFolder(new FFolder(Main.getView()));
        Main.iniOp();
        Main.saveOp();
        break;
      }
      case "fop":{
        Main.initialisation();
        FOptions fop = new FOptions();
        erreur.println(fop);
        erreur.println("sub cat game:");
        erreur.println(fop.getListKeyFromCat("game"));
        break;
      }
      case "supprimer":{
        Main.initialisation();
        //diff.nbrDeLigneDiff("usual/GString.java","../Formiko108/usual/GString.java");
        if(args.length == 4){
          String s = args[1];
          //c'est pas nésséssaire sur le terminal linux mais au cas ou
          if(s.charAt(0)=='"' && s.charAt(s.length()-1)=='"'){
            s = s.substring(1,s.length()-1);
          }
          String f = args[2];
          byte x = str.sToBy(args[3]);
          modificationDeFichier.retirerLesLignesR(s,f,x);//3== ca doit etre une ligne complète.
        }else{
          erreur.alerte("arguments de supprimer incorecte");
        }
        break;
      }
      case "test":{
        erreur.println("test");
        break;
      }
      case "rbt":
      case "rognerBordTransparent":{
        Main.initialisation();
        try {
          rbtCmd(args);
        }catch (Exception e) {
          erreur.erreur("echec de rognage de l'image");
        }
        break;
      }
      // case "cptPixels":{
      //   if(args.length>1){
      //     debug.débogage("chargement de l'image");
      //     Img img = new Img(Images.getImage(args[1],"docs/cc/images/"));
      //     debug.débogage("Image chargée");
      //     img.compterChaquePixelToHtml();
      //   }else{
      //     erreur.alerte("arguments de cptPixels incorecte");
      //   }
      //   break;
      // }
      case "cleanFolder":{
        Os.setOs(new Os());
        FFolder folder = new FFolder(Main.getView());
        if(args.length>1){
          folder.setFolderMain(args[1]);
        }
        Main.setFolder(folder);
        erreur.info("Clean main folder : "+folder.getFolderMain());
        folder.cleanFolder();
        break;
      }
      case "zip":{
        fichier.zip(args[1],args[2]);
        System.exit(0);
        break;
      }
      case "unzip":{
        fichier.unzip(args[1],args[2]);
        System.exit(0);
        break;
      }
      case "download":{
        if(FFolder.download(args[1],args[2],Main.getView())){
          System.exit(0);
        }else{
          System.exit(1);
        }
        break;
      }
      case "downloadAndUnzip":{
        String folderInURL=".";
        if(args.length>3){folderInURL=args[3];}
        if(fichier.downloadAndUnzip(args[1],args[2],folderInURL)){
          System.exit(0);
        }else{
          System.exit(1);
        }
        break;
      }
      case "createBadges":{
        createBadges.createBadges();
        System.exit(0);
        break;
      }
      case "updateDataVersion":{
        updateDataVersion(args);
        System.exit(0);
        break;
      }
      case "launcher":{
        Main.initialisation();
        Main.getView().iniLauncher();
        Main.getView().setDownloadingMessage("Dowloading files");
        int k=0;
        while (k<10) {
          Main.getView().setDownloadingValue(k*7);
          Time.pause(1000);
          k++;
        }
        break;
      }
      case "mem":{
        printMemUse();
        break;
      }
      case "ss":{
        printScreenSize();
        break;
      }
      case "version":{
        printVersion();
        break;
      }
      case "testjson":{
        testJson();
        break;
      }
      default:{
        Usual.main(args);
        // erreur.erreur("Votre options a "+(args.length)+" agruments n'as pas été reconnue : "+tableau.tableauToString(args));
      }
    }
  }
  /**
  *{@summary trim the image from args.}<br>
  *@lastEditedVersion 1.21
  */
  private static void rbtCmd(String args[]){
    String name = "";
    name = args[1];int k=2;
    while(name!=null){
      debug.débogage("=============================Chargement de l'image "+name);
      //Image i = Images.getImage(nom,Images.getREP());
      Img img = new Img(Images.getImage(name,Images.getREP()));
      debug.débogage("=============================Ronage de l'image "+name);
      img.rognerBordTransparent();
      img.actualiserImage();
      debug.débogage("=============================Sauvegarde de l'image "+name);
      img.sauvegarder(Images.getREP(),name+".png");
      try {
        name=args[k++];
      }catch (Exception e) {
        name=null;
      }
    }
  }
  /**
  *{@summary Update translation.}<br>
  *@lastEditedVersion 2.27
  */
  public static void tradCmd(String rep){
    chargerLesTraductions.setRep(rep);
    Main.startCh();
    chargerLesTraductions.iniTLangue();
    chargerLesTraductions.créerLesFichiers();
    Main.endCh("créerLesFichiers");Main.startCh();
    g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
    Main.endCh("chargerLesTraductions");Main.startCh();
    Translation.copieTrads();
    Main.endCh("copieTrads");Main.startCh();
    chargerLesTraductions.affPourcentageTraduit();
    Main.endCh("affPourcentageTraduit");//Main.startCh();
    /*chargerLesTraductions.addTradAuto();
    Main.endCh("addTradAuto");Main.startCh();
    chargerLesTraductions.affPourcentageTraduit();
    Main.endCh("affPourcentageTraduit");*/
  }
  /**
  *{@summary Print part of printable chars.}<br>
  *@lastEditedVersion 2.28
  */
  public static void tradCharCmd(int id, String fontName, boolean b){
    // Main.startCh();
    erreur.println(Translation.partOfPrintableChar(id, fontName, b));
    // chargerLesTraductions.créerLesFichiers();
    // Main.endCh("créerLesFichiers");Main.startCh();
    // g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
    // Main.endCh("chargerLesTraductions");Main.startCh();
    // Translation.copieTrads();
    // Main.endCh("copieTrads");Main.startCh();
    // chargerLesTraductions.affPourcentageTraduit();
    // Main.endCh("affPourcentageTraduit");
  }
  /**
  *{@summary Print if languages can be display by font.}<br>
  *@param fontName name of the tested font
  *@lastEditedVersion 2.28
  */
  public static void canDisplayLanguages(String fontName){
    int cpt=0;
    int len = chargerLesTraductions.getTLangue().length;
    for (int i=0; i<len; i++) {
      if(Translation.canDisplayLanguage(i, fontName)){cpt++;}
    }
    String col = null;
    if(cpt==0){
      col = color.RED;
    }else if(cpt==len){
      col = color.GREEN;
    }else{
      col = color.YELLOW;
    }
    erreur.println(col+fontName+" "+cpt+"/"+len+color.NEUTRAL);
  }
  /**
  *{@summary Do sounds or music test.}<br>
  *@lastEditedVersion 2.28
  */
  private static void music(){
    Main.initialisation(); //for color & language
    AudioPlayer sp = new AudioPlayer("C:/Users/lili5/AppData/Roaming/.formiko/data/stable/musics/Beyond The Warriors - Guifrog.mp3");
    // AudioPlayer sp = new AudioPlayer("C:/Users/lili5/Music/son/BIRDCrow_Corneilles 2 (ID 0956)_LS.mp3");
    sp.setVolume(50);
    sp.play();
    Time.pause(1000);
    System.out.println("pause");
    sp.pause();
    Time.pause(2000);
    System.out.println("resume");
    sp.resume();
    // AudioPlayer sp2 = new AudioPlayer("C:/Users/lili5/Music/son/pock.mp3",true,2000);
    // sp2.play();
    Time.pause(10000);
  }
  /**
  *{@summary Set value of data to last version in version.json.}<br>
  *It is need to help the game to choose the data version that it need.
  *Data version aren't allaws the same that game version because data don't change all time that game is update.
  *@lastEditedVersion 1.51
  */
  public static void updateDataVersion(String args[]){
    try {
      // Main.initialisation();
      Main.setView(new ViewNull());
      Os.setOs(new Os());
      FFolder f = new FFolder(Main.getView());
      f.setFolderMain();
      Main.setFolder(f);
      Main.iniOp();
      // FFolder f = Main.getFolder();
      Reader reader = Files.newBufferedReader(Main.getFolder().getVersionJsonPath());
      JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
      String formikoVersion="";
      GString gs = ReadFile.readFileGs("version.md");
      if(!gs.isEmpty()){
        formikoVersion=gs.getFirst();
      }
      // String currentVersion = f.getCurentVersion();
      String currentVersion=formikoVersion;
      String musicVersion="";
      String dataVersion="";
      if(args.length>1 && args[1].equals("1")){
        erreur.info("Update dataVersion");
        dataVersion = currentVersion;
      }else{
        dataVersion = (String) parser.get("data");
      }
      if(args.length>2 && args[2].equals("1")){
        erreur.info("Update musicVersion");
        musicVersion = currentVersion;
      }else{
        musicVersion = (String) parser.get("music");
      }
      reader.close();

      JsonObject jsr = new JsonObject();
      jsr.put("data",dataVersion);
      jsr.put("music",musicVersion);
      jsr.put("formiko",formikoVersion);
      // File file = new File(f.getFolderMain()+"version.json");
      // file.delete();
      BufferedWriter writer = Files.newBufferedWriter(Main.getFolder().getVersionJsonPath());
      Jsoner.serialize(jsr, writer);
      // erreur.println(jsr);
      // writer.write(jsr.toString());
      writer.close();
    }catch (Exception e) {
      erreur.alerte("can't update data version "+e);
      e.printStackTrace();
    }
  }
  /**
  *{@summary Print data about memory use.}<br>
  *@lastEditedVersion 2.21
  */
  public static void printMemUse(){
    int dataSize = 1024 * 1024;
    Runtime runtime = Runtime.getRuntime();
    erreur.info ("Memory max: " + runtime.maxMemory() / dataSize + "MB");
    erreur.info ("Memory total: " + runtime.totalMemory() / dataSize + "MB",0);
    erreur.info ("Memory free: " + runtime.freeMemory() / dataSize + "MB",0);
    erreur.info ("Memory used: " + (runtime.totalMemory() - runtime.freeMemory()) / dataSize + "MB",0);
  }
  /**
  *{@summary Print available screen size.}<br>
  *@lastEditedVersion 2.22
  */
  public static void printScreenSize(){
    int wi=0; int he=0;
    try {
      Rectangle rec = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
      wi = rec.width;
      he = rec.height;
    }catch (Exception e) {
      erreur.alerte("no screen size found");
    }
    erreur.info("Screen size:"+wi+" "+he);
  }
  /**
  *{@summary Print game, data, music versions.}<br>
  *@lastEditedVersion 2.22
  */
  public static void printVersion(){
    FFolder folder = new FFolder(Main.getView());
    folder.ini(false); //don't download anything
    erreur.info("Formiko version: "+folder.getVersion()+"   data version: "+folder.getCurentDataVersion()+"   music version: "+folder.getCurentMusicVersion());
  }
  /**
  *{@summary Do some test about json file.}<br>
  *One day, it should replace .csv with game data
  *@lastEditedVersion 2.28
  */
  // TODO #574
  private static void testJson(){
    Main.initialisation();
    File f = new File("test.json");
    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    erreur.println(Main.getGe().getFirst());
    try {
      mapper.writeValue(f, Main.getGe().getFirst());
      Espece e = mapper.readValue(f, Espece.class);
      erreur.println(e);
    }catch (IOException e) {
      erreur.erreur("Fail to save as .json "+e);
    }
  }
}
