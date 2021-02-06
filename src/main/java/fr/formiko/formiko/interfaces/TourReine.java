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
    if(!haveSomeHelp()){f.eat(40);}
    if(needToWaitToLetNonQueenAntPlay()){return;}
    backHome();
    feedOther(30);
    if(f.getFere().getGc().getNbrOuvrière()==0){feedOther(10);}
    lay();
    //TODO if it still have action it can clean ant that didn't realy need it but that are not at 100/100 of clean.
    finTour();
  }
  /**
  *{@summary Wait to be the last ant to play.}<br>
  *The queen whait util all the ant that did'nt have this implementation have played
  *@version 1.31
  */
  public boolean needToWaitToLetNonQueenAntPlay(){
    //TODO #182
    //for (Creature c : fere.getGc() ) {
      //if(c.getAction()>0 && !(c.tour instanceof TourReine)){return true;}
    //}
    return false;
  }
  /**
  *{@summary Check if this ant have some help from other ant of the same anthill.}<br>
  *@version 1.31
  */
  public boolean haveSomeHelp(){
    if(f.getFere().getGc().getNbrOuvrière()==0){
      return false;
    }
    return true;
  }
  /**
  *{@summary Lay egg as a queen ant.}<br>
  *If the queen have no help from al list 1 ally imago ant, it don't lay more than 1 egg.
  *@version 1.31
  */
  public void lay(){
    while(f.getAction()>0 && !f.isHungry(30)){ //Maybe we sould set the isHungry limite to a higer value if the anthill is big enough.
      if(!haveSomeHelp() && f.getFere().getGc().getCouvain().length()>=1){return;}
      //Maybe we should check that couvain do not represent a to higth %age.
      //int pourcentageDeCouvain = (100*getAlliéSurLaCase().getCouvain().length()) / this.getFere().length();
      f.pondre();
    }
  }

}
