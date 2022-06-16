package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.usual.erreur;

import java.io.Serializable;

/**
 * {@summary A null implementation.}<br>
 * It print an error for all the methode of implemented class.<br>
 * It allow to avoid error of null implementation.<br>
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public class MourirNull implements Serializable, Mourir {
  /**
  *If lauch print an error.
  */
  public void mourir(Creature c, int x){
    erreur.erreur("Impossible de mourir avec la créature " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void supprimerDeLaCarte(Creature c){
    erreur.erreur("Impossible de supprimerDeLaCarte la créature " + c.getId());
  }
}
