package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary collect seed.}<br>
 * Allow a Creature to collect seed<br>
 * @author Hydrolien
 * @version 1.1
 */
public interface Collecte extends Serializable{
  void collecte(Fourmi c);
  void collecter(Fourmi c, byte directionSiPasDeProie);
}
