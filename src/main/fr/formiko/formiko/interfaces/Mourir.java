package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary died.<br/>}
 * Allow a Creature to died<br/>
 * @author Hydrolien
 * @version 1.1
 */
public interface Mourir extends Serializable{
  /**
   * {@summary Died.<br/>}
   *@param c Insecte who is dieing
   *@param r Reason of death
   * @version 1.1
   */
  void mourir(Creature c, int r);
  void supprimerDeLaCarte(Creature c);
}
