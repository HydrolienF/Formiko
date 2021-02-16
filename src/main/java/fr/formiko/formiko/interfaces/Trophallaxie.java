package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Main;

import java.io.Serializable;

/**
 * {@summary trophallaxis.}<br>
 * Allow a Creature to do a trophallaxis<br>
 * @author Hydrolien
 * @version 1.1
 */
public interface Trophallaxie extends Serializable{
  void trophallaxie(Creature c1, Creature c2, int nourritureDonnée);
  void trophallaxie(Creature c, int id, int nourritureDonnée);
  void trophallaxer(Creature c);
  int [] getCreatureQuiOnFaim(int t[],Creature c);
}
