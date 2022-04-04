package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary Creature implementation.}<br>
 * Allow a creature without any action aviable to play a turn<br>
 * Maybe that's not usefull because if action &#60; 0 creature will already end there turn.
 * @author Hydrolien
 * @lastEditedVersion 1.24
 */
public class TourCreatureSansAction implements Serializable, Tour{
  /**
  *PLay 1 turn with Creature c.
  *@lastEditedVersion 1.24
  */
  public void unTour(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    tour(c);
    debug.débogage("fin du tour de la fourmi.");
  }
  public void preTurn(Creature c){}
  /**
  *Do turn actions :<br>
  *manger, grandir (age).<br>
  *if Evoluer != null and age >=maxAge<br>
  *if fourmi : salir<br>
  */
  public void tour(Creature c){
    endTurn(c);
  }
  /**
  *{@summary End a turn.}<br>
  *If turn have already be end on this turn, it will do nothing.
  *@lastEditedVersion 2.5
  */
  @Override
  public void endTurn(Creature c){
    //to avoid to end turn 2 time in the same turn.
    if(c.getLastTurnEnd()==Main.getPartie().getTour()){
      return;
    }else{
      c.setLastTurnEnd(Main.getPartie().getTour());
    }

    if (c.getAge()>=c.getMaxAge() && !(c.evoluer instanceof EvoluerNull)){ c.evoluer();}
    if(c instanceof Fourmi){
      Fourmi f = (Fourmi) c;
      f.salir();
      f.setFoodMoinsConsomFood(); //will not consume food if it's an egg.
    }else{
      c.setFoodMoins1();
    }
    c.setAgePlus1();

    if(c instanceof Fourmi){
      Main.setPlayingAnt(null);
    }
  }

}
