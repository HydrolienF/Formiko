package fr.formiko.formiko;

import fr.formiko.usuel.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.fichier;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.media.audio.*;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.types.str;

import java.util.HashMap;

//default File since 1.39.0
/**
*{@summary Launch options at Launch of the game.}<br>
*"-" options are launch with normal game when other options are launch alone (Game is never start).<br>
*For exemple ./run.sh -cli will launch the game in cli mode when ./run.sh stats will never launch game but update statistique value on a file.<br>
*@author Hydrolien
*@version 1.44
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
  *@version 1.44
  */
  public static void launchOptionsMinor(String stringOptions){
    switch(stringOptions){
      case "q":
      erreur.setMuet(true);
      break;
      case "d":
      debug.setAffLesEtapesDeRésolution(true);
      break;
      case "p":
      debug.setAffLesPerformances(true);
      break;
      case "g":
      debug.setAffG(true);
      break;
      case "rg":
      case "reload--graphics":
      Main.initialisation();
      Main.getOp().setGarderLesGraphismesTourné(false);
      break;
      case "cli":
      Main.setModeCLI(true);
      break;
      default:
      erreur.alerte("Unknow cli options : "+stringOptions);
    }
  }
  /**
  *{@summary Launch a major options without launching game.}
  *@version 1.44
  */
  public static void launchOptionsMajor(String args[]){
    if(args[0].equals("trad")){
      Main.initialisation();
      if(args.length>1){
        tradCmd(args[1]);
      }else{
        tradCmd();
      }
    }else if(args[0].equals("son")){
      music();
    }else if(args[0].equals("op")){
      //Main.initialisation();
      Main.setOs(new Os());
      Main.setFolder(new Folder());
      Main.iniOp();
      Main.getOp().saveOptions();
    }else if(args[0].equals("supprimer")){
      Main.initialisation();
      //diff.nbrDeLigneDiff("usuel/GString.java","../Formiko108/usuel/GString.java");
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
      System.out.println("test");
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
      Folder folder = new Folder();
      Main.setFolder(folder);
      folder.cleanFolder();
    }else if(args[0].equals("zip")){
      zip();
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
  *<li>2: number of long functions.
  *<li>2: number of short functions.
  *</ul>
  *@version 1.44
  */
  private static void stats(String args[]){
    int valueToPrint = 0;
    if(args.length>2){
      valueToPrint=str.sToI(args[2]);
    }
    if(args.length>1){
      stats.statsJavadoc(args[1]);
    }else{
      stats.statsJavadoc("src/main/",true);
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
      System.out.println(s);
    }
  }
  /**
  *{@summary trim the image from args.}<br>
  *@version 1.21
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
  *{@summary Update 1 translation & print it's #&25;age of translation.}<br>
  *@version 1.42
  */
  private static void tradCmd(String language){
    // Main.startCh();
    chargerLesTraductions.iniTLangue();
    chargerLesTraductions.créerLesFichiers();
    g.setMap(chargerLesTraductions.chargerLesTraductions(1));//chargement des langues.
    System.out.print(chargerLesTraductions.getPourcentageTraduit(chargerLesTraductions.getLanguage(language)));
  }
  /**
  *{@summary Update translation.}<br>
  *@version 1.21
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
  /**
  *{@summary Do sounds or music test.}<br>
  *@version 1.46
  */
  private static void music(){
    Main.initialisation(); //for color & language
    AudioPlayer sp = new AudioPlayer("1.mp3",true,2000);
    sp.play();
    AudioPlayer sp2 = new AudioPlayer("C:/Users/lili5/Music/son/pock.mp3",true,2000);
    sp2.play();
  }
  private static void zip(){
    fichier.zip("tools/", "tools.zip");
    fichier.unzip("tools.zip", "tools2");
  }
}
