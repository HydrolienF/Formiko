package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to play a turn<br>
 * @author Hydrolien
 * @version 1.24
 */
public class TourCreatureMorte implements Serializable, Tour{
  /**
  *PLay 1 turn with Creature c.
  *@version 1.24
  */
  public void unTour(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    c.setAgePlus1(); //une fois morte une créature a un délai avant disparition total dans la variable age
    debug.débogage("fin du tour de la fourmi.");
  }
  public void preTour(Creature c){}
}
