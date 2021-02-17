package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;

import java.io.Serializable;

/**
 * {@summary Evolve.}<br>
 * Allow a Creature to evolve<br>
 * @author Hydrolien
 * @version 1.1
 */
public interface Evoluer extends Serializable{
  void evoluer(Creature c);
}
