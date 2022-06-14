package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Case;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;

import java.io.Serializable;
/**
 * {@summary Chasse implementation.}<br>
 * Allow a creature to do hunt as an herbivore.<br>
 * @author Hydrolien
 * @lastEditedVersion 1.28
 */
public class ChasseHerbivore implements Serializable, Chasse {
  Creature c;

  public void setC(Creature cTemp){c=cTemp;}

  public boolean chasser(Creature c, int directionSiPasDeProie){return chasse(c);}
  /**
  *{@summary Hunt as an herbivore.}<br>
  *return true if Insecte have eat or moove.
  *@lastEditedVersion 1.28
  */
  public boolean chasse(Creature c){
    this.c=c;
    int foodEatable = 1;
    if(c instanceof Insecte){foodEatable=((Insecte)(c)).getfoodEatable();}
    if(c.getCCase().getContent().getFoodInsecte() >= foodEatable){
      return manger();
    }else{
      c.ceDeplacer(c.getCCase().getGca(1).getMost((Case c1, Case c2) -> c2.interestForHerbivore()-c1.interestForHerbivore()));
      return true;
    }
  }

  /**
  *{@summary Eat as an herbivore.}<br>
  *return true if Insecte have eat.
  *@lastEditedVersion 1.28
  */
  public boolean manger(){
    byte foodSurCase = c.getCCase().getContent().getFoodInsecte();
    int foodEatable = 1;
    if(c instanceof Insecte){foodEatable=((Insecte)(c)).getfoodEatable();}
    if (foodSurCase > 0){
      byte foodMangé = (byte) math.min(foodSurCase,foodEatable);
      c.getCCase().getContent().removeFoodInsecte(foodMangé);
      c.setFood(c.getFood() + foodMangé);
      if(c instanceof Fourmi){
        c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuByType(((Fourmi) c).getTypeF()).getCoutChasse()/2);
      }else{
        c.setActionMoins(1);
      }
      return true;
    }
    return false;
  }
}
