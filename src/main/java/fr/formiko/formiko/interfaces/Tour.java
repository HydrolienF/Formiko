package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;

import java.io.Serializable;
/**
 * {@summary play a turn.}<br>
 * @author Hydrolien
 * @version 1.24
 */
public interface Tour extends Serializable{
  /***
   * {@summary play a turn.}<br>
   * @param c The Creature that need to play a turn.
   * @version 1.33
   */
  void unTour(Creature c);
  //void preTour(Creature c);
}
