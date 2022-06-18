package fr.formiko.formiko;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import fr.formiko.usual.*;
import fr.formiko.usual.Folder;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.fichier;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Img;
import fr.formiko.usual.images.image;
import fr.formiko.usual.media.audio.*;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.tableau;
import fr.formiko.usual.trad;
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
      Main.getOp().setKeepFilesRotated(false);
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
      default:
      erreur.alerte("Unknow cli options : "+stringOptions);
    }
  }
  /**
  *{@summary Launch a major options without launching game.}
  *@lastEditedVersion 1.44
  */
  public static void launchOptionsMajor(String args[]){
    if(args[0].equals("trad")){
      Main.initialisation();
      if(args.length>1){
        tradCmd(args[1]);
      }else{
        tradCmd();
      }
    }else if(args[0].equals("tradChar")){
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
    }else if(args[0].equals("testFont")){
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
    }else if(args[0].equals("son")){
      music();
    }else if(args[0].equals("op")){
      //Main.initialisation();
      Main.setOs(new Os());
      Main.setFolder(new Folder(Main.getView()));
      Main.iniOp();
      Main.getOp().saveOptions();
    }else if(args[0].equals("supprimer")){
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
    }else if(args[0].equals("test")){
      erreur.println("test");
    }else if(args[0].equals("trad2")){
      Main.initialisation();
      chargerLesTraductions.iniTLangue();
      chargerLesTraductions.créerLesFichiers();
      g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
      HashMap<String, String> mapEo = chargerLesTraductions.chargerLesTraductions(0);//chargement des langues.
      trad.copieTradBase("eo",mapEo);
      //chargerLesTraductions.addTradAuto();
    }else if (args[0].equals("rbt") || args[0].equals("rognerBordTransparent")){
      Main.initialisation();
      try {
        rbtCmd(args);
      }catch (Exception e) {
        erreur.erreur("echec de rognage de l'image");
      }
    }else if(args[0].equals("stats")){
      stats(args);
    }else if(args[0].equals("statsG")){
      stats.setOnlyLastLine(true);
      stats.setSpliter(';');
      stats2(args);
    }else if(args[0].equals("cptPixels")){
      if(args.length>1){
        //image.setREPTEXTUREPACK("docs/cc/images");
        debug.débogage("chargement de l'image");
        Img img = new Img(image.getImage(args[1],"docs/cc/images/"));
        debug.débogage("Image chargée");
        img.compterChaquePixelToHtml();
      }else{
        erreur.alerte("arguments de cptPixels incorecte");
      }
    }else if(args[0].equals("cleanFolder")){
      Main.setOs(new Os());
      Folder folder = new Folder(Main.getView());
      if(args.length>1){
        folder.setFolderMain(args[1]);
      }
      Main.setFolder(folder);
      erreur.info("Clean main folder : "+folder.getFolderMain());
      folder.cleanFolder();
    }else if(args[0].equals("zip")){
      fichier.zip(args[1],args[2]);
      System.exit(0);
    }else if(args[0].equals("unzip")){
      fichier.unzip(args[1],args[2]);
      System.exit(0);
    }else if(args[0].equals("download")){
      Folder.download(args[1],args[2],Main.getView());
      System.exit(0);
    }else if(args[0].equals("createBadges")){
      createBadges.createBadges();
      System.exit(0);
    }else if(args[0].equals("translateWebSite") || args[0].equals("tws")){
      translateWebSite(args[1],args[2]);
      System.exit(0);
    }else if(args[0].equals("updateDataVersion")){
      updateDataVersion(args);
      System.exit(0);
    }else if(args[0].equals("launcher")){
      Main.initialisation();
      Main.getView().iniLauncher();
      Main.getView().setDownloadingMessage("Dowloading files");
      int k=0;
      while (k<10) {
        Main.getView().setDownloadingValue(k*7);
        Temps.pause(1000);
        k++;
      }
      // System.exit(0);
    // }else if(args[0].equals("testTryCatchNullPointerException")){
    //   testTryCatchNullPointerException.doTest();
    //   System.exit(0);
    }else if(args[0].equals("mem")){
      printMemUse();
    }else if(args[0].equals("ss")){
      printScreenSize();
    }else if(args[0].equals("version")){
      printVersion();
    }else if(args[0].equals("testjson")){
      testJson();
    }else{
      erreur.erreur("Votre options a "+(args.length)+" agruments n'as pas été reconnue : "+tableau.tableauToString(args));
    }
    // Main.quitter();
  }
  /**
  *{@summary update stats in stats.txt.}<br>
  *It can also print a value if it have a 2a args that is an integer.
  *It always print a global value for every file:
  *<ul>
  *<li>1: number of lines.
  *<li>2: number of classes.
  *<li>3: number of long functions.
  *<li>4: number of short functions.
  *</ul>
  *@lastEditedVersion 2.20
  */
  private static void stats(String args[]){
    int valueToPrint = 0;
    if(args.length>2){
      valueToPrint=str.sToI(args[2]);
    }
    if(args.length>1){
      stats.statsJavadoc(args[1], false);
    }else{
      stats.statsJavadoc("src/main/java/", true);
    }
    if(valueToPrint>0){
      String s = switch (valueToPrint) {
        case 1:
        yield stats.sommeNbrDeLigneG+"";
        case 2:
        yield stats.sommeDesClassG+"";
        case 3:
        yield stats.sommeDesFctLG+"";
        case 4:
        yield stats.sommeDesFctCG+"";
        default:
        yield "";
      };
      erreur.println(s);
    }
  }
  /**
  *{@summary Print global stats about the current state of the projet.}<br>
  *@lastEditedVersion 2.20
  */
  private static void stats2(String args[]){
    String result="";
    if(args.length>1){
      result+=stats.getStats(args[1], false).toString();
    }else{
      result+=stats.getStats("src/main/java/", true).toString();
    }
    erreur.println(result);
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
      //Image i = image.getImage(nom,image.getREP());
      Img img = new Img(image.getImage(name,image.getREP()));
      debug.débogage("=============================Ronage de l'image "+name);
      img.rognerBordTransparent();
      img.actualiserImage();
      debug.débogage("=============================Sauvegarde de l'image "+name);
      img.sauvegarder(image.getREP(),name+".png");
      try {
        name=args[k++];
      }catch (Exception e) {
        name=null;
      }
    }
  }
  /**
  *{@summary Update 1 translation &#38; print it's &#37;age of translation.}<br>
  *@lastEditedVersion 1.42
  */
  private static void tradCmd(String language){
    // Main.startCh();
    chargerLesTraductions.iniTLangue();
    chargerLesTraductions.créerLesFichiers();
    g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
    erreur.print(chargerLesTraductions.getPourcentageTraduit(chargerLesTraductions.getLanguage(language)));
  }
  /**
  *{@summary Update translation.}<br>
  *@lastEditedVersion 1.21
  */
  public static void tradCmd(){
    Main.startCh();
    chargerLesTraductions.iniTLangue();
    chargerLesTraductions.créerLesFichiers();
    Main.endCh("créerLesFichiers");Main.startCh();
    g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
    Main.endCh("chargerLesTraductions");Main.startCh();
    trad.copieTrads();
    Main.endCh("copieTrads");Main.startCh();
    chargerLesTraductions.affPourcentageTraduit();
    Main.endCh("affPourcentageTraduit");//Main.startCh();
    /*chargerLesTraductions.addTradAuto();
    Main.endCh("addTradAuto");Main.startCh();
    chargerLesTraductions.affPourcentageTraduit();
    Main.endCh("affPourcentageTraduit");*/
  }
  public static void tradCharCmd(int id, String fontName, boolean b){
    // Main.startCh();
    erreur.println(trad.partOfPrintableChar(id, fontName, b));
    // chargerLesTraductions.créerLesFichiers();
    // Main.endCh("créerLesFichiers");Main.startCh();
    // g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
    // Main.endCh("chargerLesTraductions");Main.startCh();
    // trad.copieTrads();
    // Main.endCh("copieTrads");Main.startCh();
    // chargerLesTraductions.affPourcentageTraduit();
    // Main.endCh("affPourcentageTraduit");
  }
  public static void canDisplayLanguages(String fontName){
    int cpt=0;
    int len = chargerLesTraductions.getTLangue().length;
    for (int i=0; i<len; i++) {
      if(trad.canDisplayLanguage(i, fontName)){cpt++;}
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
  *@lastEditedVersion 1.46
  */
  private static void music(){
    Main.initialisation(); //for color & language
    AudioPlayer sp = new AudioPlayer("1.mp3",true,2000);
    sp.play();
    AudioPlayer sp2 = new AudioPlayer("C:/Users/lili5/Music/son/pock.mp3",true,2000);
    sp2.play();
  }
  /**
  *{@summary Translate the web site files.}<br>
  *@param pathToWebSiteFile path to acces to web site files.
  *@param pathToWebSiteTranslation path to acces to translation files.
  *@lastEditedVersion 1.49
  */
  private static void translateWebSite(String pathToWebSiteFile, String pathToWebSiteTranslation){
    Main.setView(new ViewNull());
    Main.setOs(new Os());
    Main.setFolder(new Folder(Main.getView()));
    Main.iniOp();
    chargerLesTraductions.setRep(pathToWebSiteTranslation);
    // Chrono ch = new Chrono();
    // Main.startCh(ch);
    Main.getOp().setLanguage(0);
    Main.iniLangue();
    trad.translateWebSiteFiles(pathToWebSiteFile, 0);
    Main.getOp().setLanguage(1);
    Main.iniLangue();
    trad.translateWebSiteFiles(pathToWebSiteFile, 1);
    Main.getOp().setLanguage(2);
    Main.iniLangue();
    trad.translateWebSiteFiles(pathToWebSiteFile, 2);
    // Main.endCh("translateWebSite",ch);
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
      Main.setOs(new Os());
      Folder f = new Folder(Main.getView());
      f.setFolderMain();
      Main.setFolder(f);
      Main.iniOp();
      // Folder f = Main.getFolder();
      Reader reader = Files.newBufferedReader(Folder.getVersionJsonPath());
      JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
      String formikoVersion="";
      GString gs = ReadFile.readFileGs("version.md");
      if(!gs.isEmpty()){
        formikoVersion=gs.getFirst();
      }
      String musicVersion="";
      String dataVersion="";
      if(args.length>1 && args[1].equals("1")){
        erreur.info("Update dataVersion");
        dataVersion = f.getCurentVersion();
      }else{
        dataVersion = (String) parser.get("data");
      }
      if(args.length>2 && args[2].equals("1")){
        erreur.info("Update musicVersion");
        musicVersion = f.getCurentVersion();
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
      BufferedWriter writer = Files.newBufferedWriter(Folder.getVersionJsonPath());
      Jsoner.serialize(jsr, writer);
      // erreur.println(jsr);
      // writer.write(jsr.toString());
      writer.close();
    }catch (Exception e) {
      erreur.alerte("can't update data version "+e);
    }
  }
  // /**
  // *{@summary return the curent version.}<br>
  // *Curent version is in version.md.
  // *@lastEditedVersion 1.51
  // */
  // public static String getCurentVersion(){
  //   GString gsIn = ReadFile.readFileGs("version.md");
  //   if(gsIn.length()==0){
  //     gsIn = ReadFile.readFileGs("app/version.md");
  //   }
  //   String version = "x.x.x";
  //   if(gsIn.length()>0){version = gsIn.getItem(0);}
  //   return version;
  // }
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
    Folder folder = new Folder(Main.getView());
    folder.ini(false); //don't download anything
    erreur.info("Formiko "+folder.getCurentVersion()+"   data version: "+folder.getCurentDataVersion()+"   music version: "+folder.getCurentMusicVersion());
  }
  // TODO #574
  private static void testJson(){
    Main.initialisation();
    File f = new File("test.json");
    ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    System.out.println(Main.getGe().getFirst());
    try {
      mapper.writeValue(f, Main.getGe().getFirst());
      Espece e = mapper.readValue(f, Espece.class);
      System.out.println(e);
    }catch (IOException e) {
      erreur.erreur("Fail to save as .json "+e);
    }
  }
}
