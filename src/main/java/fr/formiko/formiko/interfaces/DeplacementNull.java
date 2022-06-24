package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.usual.erreur;

import java.io.Serializable;

/**
*{@summary A null implementation.}<br>
*It print an error for all the methode of implemented class.<br>
*It allow to avoid error of null implementation.<br>
*@author Hydrolien
*@lastEditedVersion 1.24
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
  /**
  *If lauch print an error.
  * @lastEditedVersion 1.24
  */
  public void unMouvement(Creature c, Case p){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }

}
