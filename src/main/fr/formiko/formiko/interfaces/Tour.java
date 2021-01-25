package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;
/**
 * {@summary play a turn.<br>}
 * @author Hydrolien
 * @version 1.24
 */
public interface Tour extends Serializable{
  void unTour(Creature c);
  //void preTour(Creature c);
}
