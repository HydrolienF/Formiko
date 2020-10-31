package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary A null implementation.<br/>}
 * It print an error for all the methode of implemented class.<br/>
 * It allow to avoid error of null implementation.<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class CollecteNull implements Serializable, Collecte {

  public void collecte(Fourmi c){
    erreur.erreur(g.get("CollecteNull")+" " + c.getId());
  }
  public void collecter(Fourmi c, byte directionSiPasDeProie){
    collecte(c);
  }
}
