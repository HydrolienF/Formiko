package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.CCase;

import java.io.Serializable;

/**
 * {@summary moove.}<br>
 * Allow a Creature to moove<br>
 * @author Hydrolien
 * @version 1.1
 */
public interface Deplacement extends Serializable{
  // Ici on définie juste les méthodes avec leur argument que doivent avoir les class qui implémente Deplacement
  void unMouvement(Creature c, boolean bIa);
  void unMouvement(Creature c, CCase p);
  void unMouvement(Creature c, int direction);
  void plusieurMouvement(Creature c, CCase p);

}
