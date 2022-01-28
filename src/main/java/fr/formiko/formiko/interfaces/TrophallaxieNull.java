package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.usuel.erreur;

import java.io.Serializable;

/**
 * {@summary A null implementation.}<br>
 * It print an error for all the methode of implemented class.<br>
 * It allow to avoid error of null implementation.<br>
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public class TrophallaxieNull implements Serializable, Trophallaxie {

  /*public void trophallaxieIa(Creature c1, Creature c2){
    erreur.erreur("Impossible de trophalaxer avec la créature " + c1.getId());
  }*/
  /**
  *If lauch print an error.
  */
  public void trophallaxie(Creature c, Creature c2, int x){
    erreur.erreur("Impossible de trophalaxer avec la créature " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void trophallaxie(Creature c, int id, int x){
    trophallaxie(c,(Creature) null, -1);
  }
  /**
  *If lauch print an error.
  */
  public void trophallaxer(Creature c){
    trophallaxie(c,(Creature) null, -1);
  }
  /**
  *If lauch print an error.
  */
  public int [] getCreatureQuiOnFaim(int t [], Creature c){
    trophallaxie(c,(Creature) null, -1);
    return null;
  }
}
