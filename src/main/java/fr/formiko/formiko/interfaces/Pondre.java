package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;
/**
 * {@summary lay.<br>}
 * Allow a Creature to lay<br>
 * @author Hydrolien
 * @version 1.1
 */
public interface Pondre extends Serializable{
  void unePonte(Creature c);
}
