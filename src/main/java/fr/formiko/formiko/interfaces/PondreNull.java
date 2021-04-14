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
public class PondreNull implements Serializable, Pondre {
  /**
  *If lauch print an error.
  *@version 1.1
  */
  public void unePonte(Creature c){
    erreur.erreur("Impossible de pondre avec la créature " + c.getId());
  }
  /***
  *It can never lay.
  *@version 1.41
  */
  public boolean canLay(Creature c){return false;}
}
