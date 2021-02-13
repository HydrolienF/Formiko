package fr.formiko.formiko.interfaces;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Creature;
import java.io.Serializable;

/**
 * {@summary hunt.<br>}
 * Allow a Creature to hunt other creature<br>
 * @author Hydrolien
 * @version 1.1
 */
public interface Chasse extends Serializable{
  boolean chasse(Creature c);//ni public ni private indique que c'est dispo seulement dans le package fr.formiko.formiko.
  boolean chasser(Creature c, int directionSiPasDeProie);
  /**
   * {@summary check if can hunt.<br>}
   * @version 1.31
   */
  public default boolean canHuntMore(Creature c){
    return c.getNourriture()<c.getNourritureMax() && c.getAction()>0;
  }
}
