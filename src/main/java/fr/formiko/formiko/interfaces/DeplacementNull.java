package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary A null implementation.<br>}
 * It print an error for all the methode of implemented class.<br>
 * It allow to avoid error of null implementation.<br>
 * @author Hydrolien
 * @version 1.1
 */
public class DeplacementNull implements Serializable, Deplacement {
  /**
  *If lauch print an error.
  */
  public void unMouvement(Creature c, boolean bIa){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void unMouvement(Creature c, CCase p){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void unMouvement(Creature c, int direction){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void plusieurMouvement(Creature c, CCase cc){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }

}
