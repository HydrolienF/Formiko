package fr.formiko.formiko.interfaces;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Creature;
import java.io.Serializable;

/**
 * {@summary evolve.<br/>}
 * Allow a Creature to evolve<br/>
 * @author Hydrolien
 * @version 1.1
 */
public interface Evoluer extends Serializable{
  void evoluer(Creature c);
}
