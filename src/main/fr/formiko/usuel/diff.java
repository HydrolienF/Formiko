package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.lireUnFichier;

public class diff {

  // Fonctions propre -----------------------------------------------------------
  public static int nbrDeLigneDiff(String nouveauFichier, String ancienFichier){
    //on charge les 2 fichiers dans 2 liste chainé de String.
    GString gs1 = lireUnFichier.lireUnFichierGs(nouveauFichier);
    GString gs2 = lireUnFichier.lireUnFichierGs(ancienFichier);
    //on compte la différence de ligne entre les 2 fichiers.
    int ligneAjoute = gs1.length() - gs2.length();
    //on parcours le nouveau fichier en cherchant si la ligne existe déjà dans l'ancien fichier.
    //int ligneModifieOuAjoute = gs1.compterLigneDifferenteDe(gs2);
    //autre méthode qui prend compte le fait que certaine ligne comme } sont suceptible d'extisté dans le fichier de base et de pourtant compter comme une ligne ajouté.
    gs1.supprimerLesLignesCommunesAvec(gs2);
    int ligneModifieOuAjoute = gs1.length();
    System.out.println("ligne ajouté : "+ligneAjoute);
    System.out.println("ligne modifié : "+(gs1.length()-ligneAjoute));
    System.out.println("ligne modifié ou ajouté : "+ligneModifieOuAjoute);
    return ligneModifieOuAjoute;
  }
}
