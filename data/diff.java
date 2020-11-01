package fr.formiko.usuel;
//import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.lireUnFichier;

public class diff {

  // Fonctions propre -----------------------------------------------------------
  public static int nbrDeLigneDiff(String f1, String f2){
    //on charge les 2 fichiers dans 2 liste chainé de String.
    GString gs1 = lireUnFichierGs(f1);
    GString gs2 = lireUnFichierGs(f2);
    //on compte la différence de ligne entre les 2 fichiers.
    int ligneAjouté = gs2.length() - gs1.length();
    //on parcours le nouveau fichier en cherchant si la ligne existe déjà dans l'ancien fichier.
    int lingeModifiéOuAjouté = gs1.compteLigneDifférenteDe(gs2);

  }
}
