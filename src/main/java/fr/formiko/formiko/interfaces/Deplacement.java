package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;

import java.io.Serializable;

/**
*{@summary Move.}<br>
*Allow a Creature to moove<br>
*@author Hydrolien
*@lastEditedVersion 1.24
*/
public interface Deplacement extends Serializable{
  // Ici on définie juste les méthodes avec leur argument que doivent avoir les class qui implémente Deplacement
  void unMouvement(Creature c, boolean bIa);
  void unMouvement(Creature c, CCase p);
  void unMouvement(Creature c, Case p);
  void unMouvement(Creature c, int direction);
  void plusieurMouvement(Creature c, CCase p);

}
