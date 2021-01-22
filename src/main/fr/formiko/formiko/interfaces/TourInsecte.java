package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary Insect implementation.<br>}
 * Allow an insect to play a turn.<br>
 * All insect can extends this class to modify runAway(), eat() or reproduce()<br>
 * @author Hydrolien
 * @version 1.24
 */
public class TourInsecte implements Serializable, Tour{
  private Insecte c;
  /**
  *PLay 1 turn with Creature c.
  *@version 1.24
  */
  public void unTour( Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    if(c instanceof Insecte){
      this.c = (Insecte) c;
      tour();
      debug.débogage("fin du tour de l'insecte.");
    }else{
      erreur.erreurType("Insecte","TourInsecte");
    }
  }

  /**
  *{@summary Play a turn as an Insecte.<br>}
  *Turn work like this :
  *<ul>
  *<li>Eat if the creature is starving.
  *<li>Run away from predator.
  *<li>Eat if the creature is hungry.
  *<li>Try to fined an other creature to reproduce.
  *<li>Eat if the creature is not full.
  *<li>Wait untill next turn.
  *</ul>
  *@version 1.13
  */
  public void tour(){
    if (c.getAction()>0) {eat(5);}
    if (c.getAction()>0) {runAway();}
    if (c.getAction()>0) {eat(50);}
    if (c.getAction()>0) {reproduce();}
    if (c.getAction()>0) {eat(100);}

    if(c.getAction()>0){c.setAction(0);}//end the turn normaly
    // Un tour ça coute en age et en nourriture.
    c.setNourritureMoins1();
    c.setAgePlus1();
  }

  /**
  *{@summary Run away if a predator is next to you.<br>}
  *@version 1.28
  */
  private void runAway(){ //TODO
    return;
  }
  /**
  *{@summary Eat with the interface Chasse.<br>}
  *@version 1.28
  */
  private void eat(int percentageOfHungryness){
    if(c.isHungry(percentageOfHungryness)){
      c.chasse();
    }
  }
  /**
  *{@summary reproduce.<br>}
  *@version 1.28
  */
  private void reproduce(){ //TODO
    return;
  }

}
