package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;

import java.io.Serializable;
/**
 * {@summary play a turn.}<br>
 * @author Hydrolien
 * @lastEditedVersion 1.24
 */
public interface Tour extends Serializable{
  /***
  *{@summary play a turn.}<br>
  *@param c The Creature that need to play a turn.
  *@lastEditedVersion 1.33
  */
  void unTour(Creature c);
  //void preTurn(Creature c);
  /***
  *{@summary end a turn.}<br>
  *Turn can be end only 1 times per turn.
  *@param c The Creature that need to end a turn.
  *@lastEditedVersion 2.5
  */
  default void endTurn(Creature c){}
}
