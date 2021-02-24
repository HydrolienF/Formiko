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
    f.eat(5);
    f.runAway();
    cleanItself();
    f.eat(20);
    feedOther(10);
    cleanOther();
    f.eat(80);
    //reproduce();
    backHomeAndShareFood();
    f.eat(100);
    finTour();
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
      int givenFood = f.getNourriture()-((f.getNourritureMax()*foodToQueep)/100);
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
    GCreature gc = f.getCCase().getContenu().getGc().filtreAlliés(f).filtreFaimMax();
    try { // the Creature f may not be in it.
      gc.remove(f);
    }catch (ListItemNotFoundException e) {
    }catch (EmptyListException e) {}
    Creature r = gc.getReine();
    if (r!=null && r.wantFood()) {
      return r;
    }
    GCreature gc2 = gc.filtreWantFood();
    if(gc2.getDébut()!=null){
      return gc2.getDébut().getContenu();
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
    GCreature gc = f.getCCase().getContenu().getGc().filtreAlliés(f).filtrePropreteMax();
    try { // the Creature f may not be in it.
      gc.remove(f);
    }catch (ListItemNotFoundException e) {
    }catch (EmptyListException e) {}
    Creature r = gc.getReine();
    if (r!=null && r.wantClean()) {
      return r;
    }
    GCreature gc2 = gc.filtreWantClean();
    if(gc2.getDébut()!=null){
      return gc2.getDébut().getContenu();
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
  /**
  *{@summary End a turn as an Ant.}<br>
  *@version 1.30
  */
  public void finTour(){
    System.out.println("fin tour");//@a
    debug.débogage("Fin du tour de la Fourmi");
    if(f.getAction()>0){f.setAction(0);}//end the turn normaly
    // Un tour ça coute en age et en nourriture;
    if (!(f.evoluer instanceof EvoluerNull) && f.getStade()<0 && f.getAge()>=f.getAgeMax()){ f.evoluer();}
    f.setAgePlus1(); f.salir();
    f.setNourritureMoinsConsomNourriture(); //will not ask food is it's an egg.
    // if contition de température appartient a l'intervale idéale (et que stade = -1, -2 ou -3) : re setAgePlus1();
    try { //TODO move that to ViewGUI
      if (!f.getFere().getJoueur().getIa()) { //pour un joueur humain.
        Main.getPj().setFActuelle(null);
        Main.getPb().setVisiblePa(false);
      }
      Main.getPs().setIdFourmiAjoué(-1);
    }catch (Exception e) {
      erreur.alerteGUI2Dfail("TourFourmi");
    }
  }
}
