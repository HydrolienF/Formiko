package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary trophallaxis.<br/>}
 * Allow a Creature to do a trophallaxis<br/>
 * @author Hydrolien
 * @version 1.1
 */
public interface Trophallaxie extends Serializable{
  void trophallaxie(Creature c1, Creature c2, int nourritureDonnée);
  void trophallaxie(Creature c, int id, int nourritureDonnée);
  void trophallaxer(Creature c);
  int [] getCreatureQuiOnFaim(int t[],Creature c);
}
