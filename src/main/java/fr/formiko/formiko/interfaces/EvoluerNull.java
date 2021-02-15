package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary A null implementation.<br>}
 * It print an error for all the methode of implemented class.<br>
 * It allow to avoid error of null implementation.<br>
 * @author Hydrolien
 * @version 1.1
 */
public class EvoluerNull implements Serializable, Evoluer {
  /**
  *If lauch print an error.
  */
  public void evoluer(Creature c){
    erreur.erreur(g.get("EvoluerNull")+" " + c.getId());
  }

}
