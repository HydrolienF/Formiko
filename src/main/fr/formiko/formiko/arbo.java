package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.File;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.chargerLesOptions;
import fr.formiko.usuel.image.image;

/**
*{@summary Check that every needed folder and every file is in data/.<br>}
*It can repair folder if some file have been deleted. (Some file are not repearable.)
*@author Hydrolien
*@version 1.13
*/
public class arbo {
  private static int nbrDImage=49;
  private static int nbrDeCarte=1;
  private static int nbrDeLangue=110;

  // Fonctions propre -----------------------------------------------------------
  /**
  *{@summary Check that every needed folder and every file is in data/.<br>}
  *@author Hydrolien
  *@version 1.13
  */
  public static boolean arborécenceIntacte(){
    //la condition pour concidérer qu'on lance le jeu pour la 1a fois.
    File f = new File("data/Options.txt");
    if(!f.exists() || f.isDirectory()){
      Main.setPremierePartie(true);
      return false;}
    //data
    File data = new File("data");
    if(!data.exists() || !data.isDirectory()){ return false;}
    //les sous dociers
    File carte = new File("data/carte");
    if(!carte.exists() || !carte.isDirectory()){ return false;}
    File imag = new File(image.REP);
    if(!imag.exists() || !imag.isDirectory()){ return false;}
    File langue = new File("data/langue");
    if(!langue.exists() || !langue.isDirectory()){ return false;}
    File sauvegarde = new File("data/sauvegarde");
    if(!sauvegarde.exists() || !sauvegarde.isDirectory()){ return false;}
    //les sous fichier
    f = new File("data/Espece.csv");
    if(!f.exists() || f.isDirectory()){ return false;}
    f = new File("data/Individu.csv");
    if(!f.exists() || f.isDirectory()){ return false;}
    f = new File("data/Temps.txt");
    if(!f.exists() || f.isDirectory()){ return false;}
    f = new File("data/Key.txt");
    if(!f.exists() || f.isDirectory()){ return false;}
    //le contenu de chaque dossier
    String listc [] = carte.list();
    if(listc.length<nbrDeCarte){return false;}
    String listi [] = imag.list();
    if(listi.length<nbrDImage){return false;}
    String listl [] = langue.list();
    if(listl.length<nbrDeLangue){return false;}
    //image.REP/temporaire.
    File temporaire = new File(image.REP2);
    if(!temporaire.exists() || !temporaire.isDirectory()){ return false;}
    temporaire = new File(image.REP+"ressourcesPack");
    if(!temporaire.exists() || !temporaire.isDirectory()){ return false;}
    return true;
  }
  /**
  *{@summary Fix data/. id it's need.<br>}
  *@author Hydrolien
  *@version 1.13
  */
  public static void réparationArboréscence(){
    if(!Main.getPremierePartie()){System.out.println("tentative de réparation de l'arborécence des fichier enclanchée.");}
    //data
    File data = new File("data");
    if(!data.exists() || !data.isDirectory()){
      erreur.erreur("Impossible de recréé l'entièreté des documents contenu dans data. Assurez vous que le fichier data est dans le même docier que Formiko.jar.","arbo.réparationArboréscence",true);
    }
    //les sous dociers
    File carte = new File("data/carte");
    if(!carte.exists() || !carte.isDirectory()){
      carte.mkdir();
    }
    File imag = new File(image.REP);
    if(!imag.exists() || !imag.isDirectory()){
      imag.mkdir();
    }
    File langue = new File("data/langue");
    if(!langue.exists() || !langue.isDirectory()){
      langue.mkdir();
    }
    File sauvegarde = new File("data/sauvegarde");
    if(!sauvegarde.exists() || !sauvegarde.isDirectory()){
      sauvegarde.mkdir();
    }
    //les sous fichier
    File f = new File("data/Espece.csv");
    if(!f.exists() || f.isDirectory()){
      erreur.erreur("Impossible de lancer une partie sans le fichier des Espèces","arbo.réparationArboréscence",true);
    }
    f = new File("data/Individu.csv");
    if(!f.exists() || f.isDirectory()){
      erreur.erreur("Impossible de lancer une partie sans le fichier des Individus","arbo.réparationArboréscence",true);
    }
    f = new File("data/Temps.txt");
    if(!f.exists() || f.isDirectory()){
      Temps.initialiserFichierTemps();
    }
    f = new File("data/Key.txt");
    if(!f.exists() || f.isDirectory()){
      erreur.erreur("Impossible de lancer une partie sans le fichier des touches Key.txt","arbo.réparationArboréscence",true);
    }
    f = new File("data/Options.txt");
    if(!f.exists() || f.isDirectory()){
      chargerLesOptions.chargerLesOptionsDe0(Main.getVersionActuelle());
    }
    //le contenu de chaque dossier
    String listc [] = carte.list();
    if(listc.length<nbrDeCarte){
      erreur.alerte("Toutes les cartes ne seront pas disponible.","arbo.réparationArboréscence");
      créerCarteRéparée();
    }
    String listi [] = imag.list();
    if(listi.length<nbrDImage){
      f = new File(image.REP+"null.png");
      if(!f.exists() || f.isDirectory()){
        erreur.erreur("La texture null manque, impossible de lancer le jeu sans celle ci.","arbo.réparationArboréscence",true);
      }
      erreur.erreur("Toutes les textures ne seront pas disponible, les textures absente seront remplacé par une texture violette et noir","arbo.réparationArboréscence");

    }
    String listl [] = imag.list();
    if(listl.length<nbrDeLangue){
      f = new File("data/langue/fr.txt");
      if(!f.exists() || f.isDirectory()){
        erreur.erreur("La langue français, impossible de lancer le jeu sans celle ci.","arbo.réparationArboréscence",true);
      }
      erreur.alerte("Toutes les langues ne seront pas disponible.","arbo.réparationArboréscence");
    }
    //image.REP2
    File temporaire = new File(image.REP2);
    if(!temporaire.exists() || !temporaire.isDirectory()){
      temporaire.mkdir();
    }
    temporaire = new File(image.REP+"ressourcesPack");
    if(!temporaire.exists() || !temporaire.isDirectory()){
      temporaire.mkdir();
    }
    if(!Main.getPremierePartie()){
      System.out.println("réparation terminé sans rencontrer d'erreur fatale. Si vous avez eux des message d'erreur, il est conseillé de réinstaller le jeu pour éviter que certaine fonctionnalités soit limitées.");
      System.out.println("Si vous souhaitez réinstaller le jeu sans perdre vos donné, téléchargez la même version et suivez les instruction si dessous.");
      System.out.println("Pour conserver vos sauvegarde déplacer le contenu du fichier sauvegarde actule vers le nouveau dossier sauvegarde");
      System.out.println("Pour conserver vos Options et vos réglage de touches vous pouvez remplacer le nouveau fichier Key.txt et le nouveau fichier Optionx.txt par l'ancien");
      System.out.println("Si vous avez créé de nouvelle cartes non présente dans le jeu de base vous pouvez également les déplacer dans le nouveau répertoire des cartes. (idem pour les textures)");
    }
  }
  /**
  *Create a void map if any map have been found.
  *@version 1.13
  */
  private static void créerCarteRéparée(){
    GString gs = new GString();
    gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");gs.add("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
    ecrireUnFichier.ecrireUnFichier(gs,"data/carte/carteRéparée");
  }
}
