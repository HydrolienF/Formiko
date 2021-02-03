package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary Ant implementation.<br>}
 * Allow an ant to play a turn<br>
 * @author Hydrolien
 * @version 1.24
 */
public class TourReine extends TourFourmi implements Serializable, Tour{
  private Fourmi f;
  /**
  *{@summary Do turn actions for a queen ant.}
  *<ul>
  *<li>1a the ant try to survive.
  *<li>If the ant is alone, it try to get more food.
  *<li>If some other ant (of the same anthill) haven't played yet, it wait that they have.
  *This allow the queen to lay more egg only if every other ant are clean and well feed.
  *<li>Then it help any friendly ant in the anthill place.
  *<li>Finaly it lay more egg.
  *</ul>
  *@version 1.31
  */
  @Override
  public void tour(){
    f.eat(5);
    f.runAway();
    cleanItself();
    if(f.getFere().getGc().length()>1){f.eat(40);}
    if(needToWaitToLetNonQueenAntPlay()){return;}
    backHome();
    feedOther(30);
    lay();
    finTour();
  }
  /**
  *{@summary Wait to be the last ant to play.}<br>
  *The queen whait util all the ant that did'nt have this implementation have played
  *@version 1.31
  */
  public boolean needToWaitToLetNonQueenAntPlay(){
    //TODO return false if for all f in fere.getGc() : !(f.tour instanceof TourReine) || f.getAction()<=0
    return false;
  }
  /**
  *{@summary Lay egg as a queen ant.}<br>
  *@version 1.31
  */
  public void lay(){
    while(f.getAction()>0 && !f.isHungry(30)){
      f.pondre();
    }
  }

}
