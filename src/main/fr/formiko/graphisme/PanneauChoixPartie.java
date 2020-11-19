package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.File;

public class PanneauChoixPartie extends Panneau{
  public static String REPSAVE = "data/sauvegarde/";

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauChoixPartie(){
    //TODO afficher listePartie en faire choisir 1 et valider.
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  private String [] listePartie(){
    File f = new File(REPSAVE);
    return f.list();
  }
  private void launch(String nom){
    Main.getPm().setPartie(Main.getPartieSave(REPSAVE+nom));
    Main.getPm().setLancer(true);
  }
}
