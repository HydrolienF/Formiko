package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
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
public class CollecteNull implements Serializable, Collecte {
  /**
  *If lauch print an error.
  */
  public void collecte(Fourmi c){
    erreur.erreur(g.get("CollecteNull")+" " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void collecter(Fourmi c, byte directionSiPasDeProie){
    collecte(c);
  }
}
