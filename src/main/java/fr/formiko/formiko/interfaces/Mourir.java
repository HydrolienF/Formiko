package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;

import java.io.Serializable;

/**
 * {@summary died.}<br>
 * Allow a Creature to died<br>
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public interface Mourir extends Serializable{
  /***
   *{@summary Died.}<br>
   *@param c Insecte who is dieing
   *@param r Reason of death
   *@lastEditedVersion 1.13
   */
  void mourir(Creature c, int r);
  /***
   * {@summary Delete on the map.}<br>
   *@param c Insecte who is dieing
   * @lastEditedVersion 1.13
   */
  void supprimerDeLaCarte(Creature c);
}
