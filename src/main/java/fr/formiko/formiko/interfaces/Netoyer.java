package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;

import java.io.Serializable;

/**
 * {@summary clean.}<br>
 * Allow a Creature to do clean an other 1 or itself<br>
 * @author Hydrolien
 * @version 1.1
 */
public interface Netoyer extends Serializable{
  void netoyer(Creature c);//la creature cible doit etre choisi par le joueur.
  void netoyer(Creature c, Creature c2);//la 1a fais l'action la 2a la subie.
  boolean netoyerIa(Creature c);
  int getNombreDeCreatureANetoyer(Creature c);
}
