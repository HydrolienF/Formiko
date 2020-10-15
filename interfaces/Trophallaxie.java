package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
public interface Trophallaxie {
  void trophallaxie(Creature c1, Creature c2, int nourritureDonnée);
  void trophallaxie(Creature c, int id, int nourritureDonnée);
  void trophallaxer(Creature c);
}
