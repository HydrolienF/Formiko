package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.usuel.erreur;

import java.io.Serializable;

/**
 * {@summary A null implementation.}<br>
 * It print an error for all the methode of implemented class.<br>
 * It allow to avoid error of null implementation.<br>
 * @author Hydrolien
 * @version 1.1
 */
public class TourNull implements Serializable, Tour {
  /**
  *If lauch print an error.
  */
  public void unTour(Creature c){
    erreur.erreur("Impossible de jouer un tour avec la créature " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void preTour(Creature c){
    erreur.erreur("Impossible de jouer un tour avec la créature " + c.getId());
  }
}
