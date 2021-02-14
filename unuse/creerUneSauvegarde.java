package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.listes.CString;
import java.time.LocalDateTime;

public class creerUneSauvegarde{
  // On doit avoir en sortie un tableau de Strings ligne par ligne.
  public static void creerUneSauvegarde(){
    GString lr = new GString();
    lr.ajouter("Version compatible : "+ str.str(Main.getVersionActuelle()));
    lr.ajouter(coderUnFichier.codeTour());
    //lr.ajouter(coderUnFichier.grilleToString());
    lr.ajouter("Date de création : " + Main.getDateDeCréation());
    lr.ajouter("Date de sauvegarde : " + LocalDateTime.now());
    GJoueur gj = Main.getGj();
    //int nbrDeJoueur = gj.length();
    //lr.ajouter("Nombre de Joueur : " + nbrDeJoueur);
    // On parcour la liste des joueurs.
    CJoueur cj = gj.getDébut();
    while (cj != null ) {
      lr.ajouter(coderUnFichier.codeJoueur(cj.getJoueur()));
      cj = cj.getSuivant();
    }
    GInsecte gi = Main.getGi();
    CInsecte ci = gi.getDébut();
    while (ci != null ){
      lr.ajouter(coderUnFichier.insecteToString(ci.getInsecte()));
      ci = ci.getSuivant();
    }
    ecrireUnFichier.ecrireUnFichier(lr);
  }
}