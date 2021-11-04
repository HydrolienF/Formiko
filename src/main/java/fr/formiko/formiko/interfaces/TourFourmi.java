package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.exceptions.ListItemNotFoundException;
import fr.formiko.usuel.exceptions.ClassTypeException;
import fr.formiko.usuel.exceptions.EmptyListException;

import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to play a turn<br/>
 * @author Hydrolien
 * @version 1.30
 */
public class TourFourmi implements Serializable, Tour{
  protected Fourmi f;
  public void setF(Fourmi fTemp){f=fTemp;}
  /**
  *PLay 1 turn with Creature f.
  *@version 1.24
  */
  public void unTour(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    if(c==null){throw new NullPointerException();}
    if(!(c instanceof Fourmi)){throw new ClassTypeException("Fourmi","Creature");}
    setF((Fourmi) c);
    tour();
    debug.débogage("fin du tour de la fourmi.");
  }

  /**
  *{@summary Do turn actions for an ant.}
  *<ul>
  *<li>1a the ant try to survive.
  *<li>Then it help any friendly ant in the same case.
  *<li>Then it get more food.
  *<li>Finaly it back home and share food with other.
  *</ul>
  *@version 1.29
  */
  public void tour(){
    f.eat(5); //if granivore go back to anthill if anthill have oppen seed or oppenable seed.
    f.runAway();
    cleanItself();
    f.eat(20);
    feedOther(10); //if granivore do not feedOther out of anthill
    cleanOther();
    f.eat(80);
    //reproduce();
    backHomeAndShareFood(); //if granivore oppen seed & feedOther.
    f.eat(100);
    f.setActionTo0();
  }
  /**
  *{@summary End a turn as an Ant.}<br>
  *If turn have already be end on this turn, it will do nothing.
  *@version 2.5
  */
  @Override
  public void endTurn(Creature c){
    if(!(c instanceof Fourmi)){throw new ClassTypeException("Fourmi","Creature");}
    setF((Fourmi) c);
    endTurn();
  }
  /**
  *{@summary End a turn as an Ant.}<br>
  *If turn have already be end on this turn, it will do nothing.
  *@version 2.5
  */
  public void endTurn(){
    //to avoid to end turn 2 time in the same turn.
    if(f.getLastTurnEnd()==Main.getPartie().getTour()){
      return;
    }else{
      f.setLastTurnEnd(Main.getPartie().getTour());
    }
    debug.débogage("Fin du tour de la Fourmi");
    f.setActionTo0();//end the turn normaly
    // Un tour ça coute en age et en food;
    if (f.getStade()<0 && !(f.evoluer instanceof EvoluerNull) && f.getAge()>=f.getMaxAge()){ f.evoluer();}
    f.setAgePlus1();
    if(!f.getIsDead()){
      f.salir();
      if(!f.getIsDead()){
        f.setFoodMoinsConsomFood(); //will not need food is it's an egg.
      }
    }
    // if contition de température appartient a l'intervale idéale (et que stade = -1, -2 ou -3) : re setAgePlus1();
    Main.setPlayingAnt(null);
  }

  /**
  *{@summary To be sur that the ant will be clean.}<br>
  *@version 1.29
  */
  public void cleanItself(){
    while(f.getAction()>0 && f.wantClean() && !(f.netoyer instanceof NetoyerNull)){ f.ceNetoyer();}
  }
  /**
  *{@summary Give food by trophallaxis if other ant need it.}<br>
  *An Ant try to give all it food exept foodToQueep%.<br>
  *An ant try to give food if : it have action left, it have more food than foodToQueep%, it can use Trophallaxie interface.
  *@version 1.29
  */
  public void feedOther(int foodToQueep){
    while(f.getAction()>0 && !(f.isHungry(foodToQueep)) && !(f.trophallaxie instanceof TrophallaxieNull)){
      Creature toFeed = aNourrir();
      if(toFeed==null){return;}
      int givenFood = f.getFood()-((f.getMaxFood()*foodToQueep)/100);
      if(givenFood<1){return;}
      f.trophallaxie(toFeed,givenFood);
    }
  }
  /**
  *{@summary Search the ant that need the more food.}<br>
  *We 1a feed our ant queen if it wantFood.<br>
  *We 2a feed every creature that is concidered as an ally &#38; want food.<br>
  *Finaly we try to feed the queen even if she didn't absolutly need food.<br>
  *@version 1.29
  */
  public Creature aNourrir(){
    GCreature gc = f.getCCase().getContent().getGc().filterAlliés(f).filterFaimMax();
    try { // the Creature f may not be in it.
      gc.remove(f);
    }catch (ListItemNotFoundException e) {
    }catch (EmptyListException e) {}
    Creature r = gc.getReine();
    if (r!=null && r.wantFood()) {
      return r;
    }
    GCreature gc2 = gc.filterWantFood();
    if(gc2.getHead()!=null){
      return gc2.getHead().getContent();
    }
    return r; //r can be null.
  }
  /**
  *{@summary Clean if other ant need it.}<br>
  *@version 1.29
  */
  public void cleanOther(){
    while(f.getAction()>0 && !(f.netoyer instanceof NetoyerNull)){
      Creature toClean = aNetoyer();
      if(toClean==null){return;}
      f.netoyer(toClean);
    }
  }
  /**
  *{@summary Search the ant that need the more to be clean.}<br>
  *We 1a clean our ant queen if it wantClean.<br>
  *We 2a clean every creature that is concidered as an ally &#38; want clean.<br>
  *@version 1.29
  */
  public Creature aNetoyer(){
    GCreature gc = f.getCCase().getContent().getGc().filterAlliés(f).filterHealthMax();
    try { // the Creature f may not be in it.
      gc.remove(f);
    }catch (ListItemNotFoundException e) {
    }catch (EmptyListException e) {}
    Creature r = gc.getReine();
    if (r!=null && r.wantClean()) {
      return r;
    }
    GCreature gc2 = gc.filterWantClean();
    if(gc2.getHead()!=null){
      return gc2.getHead().getContent();
    }
    return null;
  }
  /**
  *{@summary Back home and share food to the Creature that need it.}<br>
  *@version 1.29
  */
  public void backHomeAndShareFood(){
    backHome();
    feedOther(10);
    cleanOther();
  }
  /**
  *{@summary Back home.}<br>
  *We find the direction to be closer to the anthill. And then we go there. <br>
  *@version 1.29
  */
  public void backHome(){
    while (f.getAction()>0 && !f.estALaFere()) {
      int directionDeLaFourmilière = f.getCCase().getDirection(f.getFourmiliere().getCCase());
      f.ceDeplacer(directionDeLaFourmilière);
    }
  }
}
