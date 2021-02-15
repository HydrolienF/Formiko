package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.usuel.erreur;

import java.io.Serializable;

/**
 * {@summary A null implementation.<br>}
 * It print an error for all the methode of implemented class.<br>
 * It allow to avoid error of null implementation.<br>
 * @author Hydrolien
 * @version 1.1
 */
public class NetoyerNull implements Serializable, Netoyer {

  // Fonctions propre -----------------------------------------------------------
  /**
  *If lauch print an error.
  */
  public void netoyer(Creature c){
    err(c);
  }
  /**
  *If lauch print an error.
  */
  public void netoyer(Creature c, Creature c2){
    err(c);
  }
  /**
  *If lauch print an error.
  */
  public boolean netoyerIa(Creature c){
    err(c);return false;
  }
  /**
  *If lauch print an error.
  */
  public int getNombreDeCreatureANetoyer(Creature c){
    return 0;
  }
  /**
  *Print the error.
  */
  private void err(Creature c){
    erreur.erreur("Impossible de netoyer avec la créature " + c.getId());
  }
}
