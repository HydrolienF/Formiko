package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
public interface Deplacement {
  // Ici on définie juste les méthodes avec leur argument que doivent avoir les class qui implémente Deplacement
  void unMouvement(Creature c, boolean bIa);
  void unMouvement(Creature c, CCase p);
  void unMouvement(Creature c, int direction);
  void plusieurMouvement(Creature c, CCase p);

}
