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
public class PondreNull implements Serializable, Pondre {
  /**
  *If lauch print an error.
  *@lastEditedVersion 1.1
  */
  public void unePonte(Creature c){
    erreur.erreur("Impossible de pondre avec la créature " + c.getId());
  }
  /***
  *It can never lay.
  *@lastEditedVersion 1.41
  */
  public boolean canLay(Creature c){return false;}
}
