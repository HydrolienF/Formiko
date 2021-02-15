package fr.formiko.usuel.exceptions;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.79.5

public class FichierDejaPresentException extends Exception{
  private static final long serialVersionUID = 2873152784453017836L;//généré avec serialver fr.formiko.usuel.exception.FichierDejaPresentException
  public FichierDejaPresentException(){
    this("");
  }
  public FichierDejaPresentException(String nomDuFichierDejaExistant){
    if (!nomDuFichierDejaExistant.equals("")){
      nomDuFichierDejaExistant = " " + nomDuFichierDejaExistant;
    }
    System.out.println("Le fichier"+ nomDuFichierDejaExistant + " est déjà dans le répertoire courant. Impossible de l'écraser.");
  }
}
