package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary Insect implementation.}<br>
 * Allow an insect to play a turn.<br>
 * All insects can extends this class to modify runAway(), eat() or reproduce()<br>
 * @author Hydrolien
 * @version 1.28
 */
public class TourInsecte implements Serializable, Tour{
  private Insecte c;
  /**
  *PLay 1 turn with Creature c.
  *@version 1.24
  */
  public void unTour(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    if(c instanceof Insecte){
      this.c = (Insecte) c;
      tour();
      debug.débogage("fin du tour de l'insecte.");
    }else{
      erreur.erreurType("Insecte");
    }
  }

  /**
  *{@summary Play a turn as an Insecte.}<br>
  *Turn work like this :<br>
  *<ul>
  *<li>Eat if the creature is starving.
  *<li>Run away from predator.
  *<li>Eat if the creature is hungry.
  *<li>Try to fined an other creature to reproduce.
  *<li>Eat if the creature is not full.
  *<li>End turn.
  *</ul>
  *@version 1.28
  */
  public void tour(){
    c.eat(5);
    c.runAway();
    c.eat(50);
    reproduce();
    c.eat(100);
    finTour();
  }
  /**
  *{@summary End a turn as an Insecte.}<br>
  *@version 1.28
  */
  public void finTour(){
    if(c.getAction()>0){c.setAction(0);}//end the turn normaly
    // Un tour ça coute en age et en nourriture.
    c.setNourritureMoins1();
    c.setAgePlus1();
  }

  /**
  *{@summary reproduce.}<br>
  *@version 1.28
  */
  private void reproduce(){ //TODO
    //while(c.getAction()>0  && c.ceReproduire()){
    return;
  }

}
