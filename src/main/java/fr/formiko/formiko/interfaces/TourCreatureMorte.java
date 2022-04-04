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
 * @lastEditedVersion 1.24
 */
public class TourCreatureMorte implements Serializable, Tour{
  /**
  *PLay 1 turn with Creature c.
  *@lastEditedVersion 2.5
  */
  public void unTour(Creature c){
    endTurn(c);
  }
  public void preTurn(Creature c){}
  /**
  *End 1 turn with Creature c.
  *@lastEditedVersion 2.5
  */
  public void endTurn(Creature c){
    //to avoid to end turn 2 time in the same turn.
    if(c.getLastTurnEnd()==Main.getPartie().getTour()){
      return;
    }else{
      c.setLastTurnEnd(Main.getPartie().getTour());
    }

    c.setAgePlus1();
  }
}
