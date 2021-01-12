package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to play a turn<br/>
 * @author Hydrolien
 * @version 1.24
 */
public class TourReine implements Serializable, Tour{
  private Fourmi f;
  /**
  *PLay 1 turn with Creature c.
  *@version 1.24
  */
  public void unTour( Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    if(c instanceof Fourmi){
      f = (Fourmi) c;
      tour();
      debug.débogage("fin du tour de la fourmi.");
    }else{
      erreur.erreurType("Reine","tourReine");
    }
  }

  public void tour(){

  }

}
