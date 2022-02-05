package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary A null implementation.}<br>
 * It print an error for all the methode of implemented class.<br>
 * It allow to avoid error of null implementation.<br>
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public class ChasseNull implements Serializable, Chasse {
  /**
  *If lauch print an error.
  */
  public boolean chasse(Creature c){
    erreur.erreur(g.get("ChasseNull")+" " + c.getId());
    return false;
  }
  /**
  *If lauch print an error.
  */
  public boolean chasser(Creature c, int directionSiPasDeProie){
    return chasse(c);
  }
  /**
  *If lauch print an error.
  */
  @Override
  public boolean canHuntMore(Creature c){
    return chasse(c);
  }
}
