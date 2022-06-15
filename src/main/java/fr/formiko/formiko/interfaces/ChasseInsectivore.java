package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GInsecte;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to do hunt<br>
 * Ant are able to see other ObjetSurCarteAId as Creature at 1 Case of distance.
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public class ChasseInsectivore implements Serializable, Chasse {
  private Creature c;

  public void setC(Creature cTemp){c=cTemp;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
   * {@summary try to hunt or moove.}<br>
   * @param c The hunting Creature.
   * @param direction The direction were the Creature will go if any Prey is visible.
   * return true if c can hunt more.
   * @lastEditedVersion 1.28
   */
  public boolean chasser(Creature c, int direction){
    setC(c);
    if(!canHuntMore()){return false;}
    GInsecte proieVisible = getProie();
    if (c.getCCase().getContent().getGi().getHead() != null){ // Si il y a un insecte sur la même case
      chasse(c);
    }else if (proieVisible.getHead() != null){ // Si il y a un insecte a coté
      CCase pointDeLaProie = proieVisible.getFirst().getCCase();
      if (Main.getDifficulté() >= 1 || (c instanceof Fourmi && c.getIa()==false)){ // en normal les ia chasse les insectes les plus intéressants sur la case ou elle sont.
        pointDeLaProie = proieVisible.getInsectePlusDeGivenFood().getCCase();
      }
      debug.débogage(g.getM("laFourmi")+" " + c.getId()+ " "+g.get("ChasseInsectivore.1")+" " + pointDeLaProie.getPoint());
      c.ceDeplacer(pointDeLaProie);
    }else { // Si il n'y a pas d'insecte
      c.ceDeplacer(direction);
    }
    return canHuntMore();
  }
  /**
   * {@summary actions during hunt.}<br>
   * An Ant kill an Insect in the same Case<br>
   * It can choose the first 1 or the better 1 depending on the difficulty.<br>
   * @param c The hunting Creature.
   * return true if c can hunt more.
   * @lastEditedVersion 1.1
   */
  public boolean chasse(Creature c){
    setC(c);
    if(!canHuntMore()){return false;}
    //chasse
    Case pointActuel = c.getCCase().getContent();
    GInsecte gi = pointActuel.getGi();
    debug.débogage("Chasse : action = "+c.getAction() + "maxAction = "+c.getMaxAction());
    if (gi.getHead() != null) { // sous forme de str I+id
      Insecte insecteTue;
      if (Main.getDifficulté() >= 0 || ((c instanceof Fourmi) && ((Fourmi)c).getFere().getJoueur().getIa()==false)){ // en normal les ia chasse les insectes les plus intéressants sur la case ou elle sont.
        insecteTue = gi.getInsectePlusDeGivenFood();
      }else{
        insecteTue = gi.getFirst();
      }
      tuer(insecteTue);
      if(!canHuntMore()){return false;}
      depecer(insecteTue);
    }
    return canHuntMore();
  }

  //COMMENT FONCTIONNE LA CHASSE
  /**
   * {@summary fined a prey.}<br>
   * @lastEditedVersion 1.1
   */
  public GInsecte getProie(){
    //TODO on dervrais plutot faire un GCreature pour pouvoir inclure des Fourmis et potentiellement remove des insectes.
    return c.getCCase().getGi(1); // 1 est le rayon du cercle de case pris en compte.
  }
  /**
   * {@summary kill during hunt.}<br>
   * An Ant kill an Insect<br>
   * @param insecteTue The insect that will die.
   * @lastEditedVersion 1.1
   */
  public boolean tuer(Insecte insecteTue){
    if (!insecteTue.getIsDead()){
      Message m = new Message(g.getOr("la","le")+" "+c.getName()+" "+ c.getId()+" "+g.get("chasseInsectivore.2")+" " + insecteTue.getId(), ((Fourmi) c).getFourmiliere().getId(),2);
      insecteTue.setIsDead(true);
      setActionMoins(c);
      return true;
    }else{
      return false;
    }
  }
  /**
   * {@summary Butcher during hunt.}<br>
   * An Ant kill an Insect<br>
   * @param insecteTue The died Insect.
   * @lastEditedVersion 1.1
   */
  public boolean depecer(Insecte insecteTue){
    if(insecteTue==null){return false;}
    Message m = new Message(g.getOr("la","le")+" "+c.getName()+" "+ c.getId()+" "+g.get("chasseInsectivore.3")+" " + insecteTue.getId(), ((Fourmi) c).getFourmiliere().getId(),2);
    int food = math.min(insecteTue.getGivenFood(),c.getMaxFood()-c.getFood());
    if (insecteTue.getGivenFood()==food){
      insecteTue.supprimerDeLaCarte();
    }else{
      insecteTue.setGivenFood(insecteTue.getGivenFood()-food);
    }
    c.ajouteFood(food);
    setActionMoins(c);
    return true;
  }
  public boolean canHuntMore(){return canHuntMore(c);}
}
