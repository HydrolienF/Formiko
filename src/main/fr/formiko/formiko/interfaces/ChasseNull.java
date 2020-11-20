package fr.formiko.formiko.interfaces;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Creature;
import java.io.Serializable;

/**
 * {@summary A null implementation.<br/>}
 * It print an error for all the methode of implemented class.<br/>
 * It allow to avoid error of null implementation.<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class ChasseNull implements Serializable, Chasse {
  /**
  *If lauch print an error.
  */
  public void chasse(Creature c){
    erreur.erreur(g.get("ChasseNull")+" " + c.getId());
  }
  /**
  *If lauch print an error.
  */
  public void chasser(Creature c, int directionSiPasDeProie){
    chasse(c);
  }
}
