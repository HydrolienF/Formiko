package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.lireUnFichier;
import fr.formiko.formiko.Options;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.chargerLesTraductions;
import java.awt.Toolkit;
import java.io.File;
import java.awt.Font;
import java.util.Locale;
import fr.formiko.usuel.image.image;

public class chargerLesOptions {
  // Fonctions propre -----------------------------------------------------------
  public static Options chargerLesOptions(){
    //pas de vérifications de la 1a partie du texte qui nous intéresse pas tant que le fichier est dans l'ordre.
    File f = new File("data/Options.txt");
    if (!f.exists()){ // si le fichier d'options n'existe pas.
      chargerLesOptionsDe0();
    }
    String t [] = lireUnFichier.lireUnFichier("data/Options.txt");
    Options op = new Options();
    int k=0;
    if(decoderUnFichier.getIntDeLaLigne(t[k]) != Main.getVersionActuelle()){ return op;}k++;
    op.setLangue( decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setTailleBoutonZoom( decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setTailleBoutonAction( decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setTailleBoutonTX( decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setMouvementRapide(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setDéplacementInstantané(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setElementSurCarteOrientéAprèsDéplacement(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setNbrMessageAfficher( decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setDessinerGrille(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setForcerQuitter(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setBordureBouton(decoderUnFichier.getIntDeLaLigne(t[k]));k++;
    op.setDessinerIcone(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setTaillePolice1( decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setTaillePolice2( decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setPolice(decoderUnFichier.getStringDeLaLigne(t[k]));k++;
    op.setFont1(new Font(op.getPolice(),Font.PLAIN,op.getTaillePolice1()));
    op.setFont2(new Font(op.getPolice(),Font.BOLD,op.getTaillePolice2()));
    op.setPseudo(decoderUnFichier.getStringDeLaLigne(t[k]));k++;
    op.setPleinEcran(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setChargementPendantLesMenu(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setGarderLesGraphismesTourné(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setAttendreAprèsLeChargementDeLaCarte(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setAffLesEtapesDeRésolution(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setAffLesPerformances(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setAffG(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setDimLigne(decoderUnFichier.getIntDeLaLigne(t[k]));k++;
    op.setPositionCase(decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setBMusique(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setBSon(decoderUnFichier.getBooleanDeLaLigne(t[k]));k++;
    op.setVolMusique(decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setVolSon(decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    op.setTailleRealiste(decoderUnFichier.getByteDeLaLigne(t[k]));k++;
    try {
      File dossier = new File(image.REP2);
      //si les éléments sont tourné mais qu'il n'y a pas beaucoup d'image, il en manque.
      if(op.getElementSurCarteOrientéAprèsDéplacement() && dossier.listFiles().length<50){op.setGarderLesGraphismesTourné(false);erreur.alerte("Les graphismes déjà présent semble bien trop peu nombreux pour être complet","chargerLesOptions","Les graphismes seront rechargé pendant le lancement du jeu");}
    }catch (Exception e) {
      erreur.erreur("le dossier REP2 n'existe pas.");
    }
    return op;
  }
  public static void chargerLesOptionsDe0(int langue, String pseudo){
    //TODO passer par la méthode sauvegarder de Option pour tout sauvegarder.
    GString gs = new GString();
    gs.ajouter("version compatible:"+Main.getVersionActuelle());
    gs.ajouter("langue:"+langue);
    int x = Toolkit.getDefaultToolkit().getScreenSize().width; int t[]=new int[2];
    if(x>=1920*2){ //plus de 2*
      t[0]=2;t[1]=2;//t[2]=1;
    }else if(x>=1920*1.3){ //entre 1,3 et 2
      t[0]=1;t[1]=1;//t[2]=0;
    }else if(x>=1920*0.8){ // entre 0.8 et 1.3
      t[0]=0;t[1]=1;//t[2]=-1;
    }else if(x>=1920*0.5){ // entre 0.5 et 0.7
      t[0]=0;t[1]=0;t[2]=-2;
    }else{ // moins de 0.5
      t[0]=-1;t[1]=-1;//t[2]=-2;
    }
    Double racio = x+0.0;
    racio = racio / 1920; // si on a 1920 on change rien. Si c'est moins de pixel on réduit la police et vis versa pour plus.
    int taillePolice1 = (int)(30*racio);
    int taillePolice2 = (int)(60*racio);
    gs.ajouter("taille bouton zoom:"+t[0]);
    gs.ajouter("taille bouton action:"+t[1]);
    gs.ajouter("taille bouton tint:"+t[0]);
    gs.ajouter("mouvementRapide:1");
    gs.ajouter("déplacementInstantané:1");
    gs.ajouter("fourmiOrientéAprèsDéplacement:1");
    gs.ajouter("nbrMessageAfficher:10");
    gs.ajouter("dessinerGrille:1");
    gs.ajouter("forcerQuitter:0");
    gs.ajouter("bordureBouton:2");
    gs.ajouter("dessinerIcone:1");
    gs.ajouter("taillePolice1:"+taillePolice1);
    gs.ajouter("taillePolice2:"+taillePolice2);
    gs.ajouter("police:Arial");
    gs.ajouter("pseudo:"+pseudo);
    gs.ajouter("pleinEcran:1");
    gs.ajouter("chargement pendant les menu:1");
    gs.ajouter("garder les fichier tourné:1");
    gs.ajouter("attendre après le chargement de la carte:1");
    gs.ajouter("afficher les étapes de résolution (débogage):0");
    gs.ajouter("afficher les performances(débogage):0");
    gs.ajouter("afficher les étape graphiques(débogage):0");
    gs.ajouter("dimention des lignes de la carte:"+(int)(2*racio));
    gs.ajouter("positionCase:2");
    gs.ajouter("""
    musique:0
    son:0
    volMusique:100
    volSon:100
    tailleRéaliste:0
    """);
    //TODO la musique est temporairement désactivé. La réactivé quand elle marchera.
    ecrireUnFichier.ecrireUnFichier(gs,"data/Options.txt");
  }
  public static void chargerLesOptionsDe0(int l){chargerLesOptionsDe0(l,"");}
  public static void chargerLesOptionsDe0(){
    Locale currentLocale = Locale.getDefault();
    debug.débogage("Langue locale = "+currentLocale.getLanguage());
    String lang = currentLocale.getLanguage();
    chargerLesOptionsDe0(chargerLesTraductions.getLangue(lang));
  }
}
