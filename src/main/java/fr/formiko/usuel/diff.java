package fr.formiko.usuel;

//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.lireUnFichier;
/**
 * {@summary Tool to count add or suppress line.}<br>
 * @author Hydrolien
 * @lastEditedVersion 1.0
 */
public class diff {

  // FUNCTIONS -----------------------------------------------------------------
  /**
   * {@summary Count add or suppress line.}<br>
   * @lastEditedVersion 1.0
   */
  public static int nbrDeLigneDiff(String nouveauFichier, String ancienFichier){
    //on charge les 2 fichiers dans 2 liste chainé de String.
    GString gs1 = lireUnFichier.readFileGs(nouveauFichier);
    GString gs2 = lireUnFichier.readFileGs(ancienFichier);
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
