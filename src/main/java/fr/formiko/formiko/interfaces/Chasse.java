package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

import java.io.Serializable;

/**
 * {@summary hunt.}<br>
 * Allow a Creature to hunt other creature<br>
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public interface Chasse extends Serializable{
  boolean chasse(Creature c);//ni public ni private indique que c'est dispo seulement dans le package fr.formiko.formiko.
  boolean chasser(Creature c, int directionSiPasDeProie);
  /***
  *{@summary Return true if there is prey on same square.}<br>
  *@lastEditedVersion 2.29
  */
  boolean havePreyOnSameSquare(Creature c);
  /**
   * {@summary check if can hunt.}<br>
   * @lastEditedVersion 1.31
   */
  public default boolean canHuntMore(Creature c){
    return c.getFood()<c.getMaxFood() && c.getAction()>0;
  }
  /**
   * {@summary Lower creature actions count.}<br>
   * @lastEditedVersion 1.40
   */
  public default void setActionMoins(Creature c){
    if(c instanceof Fourmi){
      c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuByType(((Fourmi) c).getTypeF()).getCoutChasse()/2);
    }else{
      c.setActionMoins(10);
    }
  }
  /** Default implementation that always return false. */
  public default boolean canEatSeed(){return false;}
  /** Default implementation that always return false. */
  public default boolean canBreakSeed(){return false;}
  /** Default implementation that do nothing. */
  public default void eatSeed(){}
  /** Default implementation that do nothing. */
  public default void breakSeed(){}
}
