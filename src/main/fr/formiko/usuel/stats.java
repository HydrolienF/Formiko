package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.liste.GGInt;
import fr.formiko.usuel.liste.CCInt;
import fr.formiko.usuel.liste.GInt;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.liste.CString;
import fr.formiko.usuel.fichier;

/**
*{@summary A tool class about statistic. <br>}
*@author Hydrolien
*@version 1.13
*/
public class stats {

  // Fonctions propre -----------------------------------------------------------
  /**
  *Write the stats of javadoc comments in stats.txt.
  *@version 1.13
  */
  public static void statsJavadoc(){
    Main.débutCh();
    //GString gs = fichier.listerLesFichiersDuRep("src/main/fr/formiko/usuel/outils");
    GString gs = fichier.listerLesFichiersDuRep("src/main/");
    Main.finCh("listage des fichiers");Main.débutCh();
    //gs = la liste des fichiers.

    GGInt ggi = new GGInt();
    CString cs = gs.getDébut();
    while(cs!=null){//pour chaque fichier on récupère le comtenu et on compte les Commentaire et les fonction longue et courte.
      GString contenuDuFichier = lireUnFichier.lireUnFichierGs(cs.getContenu());
      ggi.add(contenuDuFichier.compterFctEtComJavadoc());
      cs = cs.getSuivant();
    }
    Main.finCh("récupération des data");Main.débutCh();

    //GGInt = la liste de toutes les données.
    int sommeDesCom = ggi.sommeCase(2);
    int sommeDesFctL = ggi.sommeCase(1);
    String total = "total : ";
    if(sommeDesFctL>0){total+=((sommeDesCom*100)/sommeDesFctL)+"%";}
    GString gsr = new GString();
    gsr.add(total);
    Main.finCh("calcul puis ajout du total");Main.débutCh();
    //ajouter tt les autres.
    CCInt cci = ggi.getDébut();
    cs = gs.getDébut();
    while(cci!=null){
      gsr.add(toStatJd(cci)+"  "+cs.getContenu());
      cci=cci.getSuivant();
      cs=cs.getSuivant();
    }
    Main.finCh("traitement du GString");Main.débutCh();
    ecrireUnFichier.ecrireUnFichier(gsr,"stats.txt");
    Main.finCh("sauvegarde finale");
  }

  public static String toStatJd(CCInt cci){return toStatJd(cci.getContenu());}
  /**
  *{@summary calculate the %age of commented fonction in a file.}
  *@version 1.13
  */
  public static String toStatJd(GInt gi){
    int sommeDesCom = gi.getCase(2);
    int sommeDesFctL = gi.getCase(1);
    if(sommeDesFctL==0){return "x%";}
    return ((sommeDesCom*100)/sommeDesFctL)+"%";
  }

}
