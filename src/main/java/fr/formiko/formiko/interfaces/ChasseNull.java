package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

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
  @Override
  public boolean chasse(Creature c){
    erreur.erreur(g.get("ChasseNull")+" " + c.getId());
    return false;
  }
  /**
  *If lauch print an error.
  */
  @Override
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
  /**
  *{@summary Return true if there is prey on same square.}<br>
  *@lastEditedVersion 2.29
  */
  @Override
  public boolean havePreyOnSameSquare(Creature c){
    return false;
  }
}
