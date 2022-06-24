package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to died<br>
 * @author Hydrolien
 * @lastEditedVersion 1.41
 */
public class MourirFourmi implements Serializable, Mourir {
  /**
   *{@summary Kill an ant an print a message.}<br>
   *We need to remove it from the map, from it anthill and from playing ant if it was playing.
   *@param c ant who is diing.
   *@param r reason of death.
   *@lastEditedVersion 1.41
   */
  public void mourir(Creature c, int r){
    if(c==null){return;}
    c.setIsDead(true);
    if(c instanceof Fourmi){
      Fourmi f = (Fourmi)c;
      Message.messageMort(f,r);
      if(f.equals(Main.getPlayingAnt())){
        Main.setPlayingAnt(null);
      }
      deleteFromGame(f);
      if (f.getStade() != 0){
        // TODO les oeuf ou les larves (ou les nymphes) sont mangeable par les autres fourmis.
        //f.getCCase().getContent().getGc().add(new Insecte(f.getCCase(),10 + (int) getFood()/3,0,0,0));
      }
    }else{
      erreur.erreurType("Fourmi");
    }
  }
  /**
   *{@summary Delete the ant from the map.}<br>
   *@param c Creature who is diing.
   *@lastEditedVersion 1.13
   */
  public void supprimerDeLaCarte(Creature c){
    if(c instanceof Fourmi){
      Fourmi f = (Fourmi)c;
      supprimerDeLaCarte(f);
    }else{
      erreur.erreurType("Fourmi");
    }
  }
  /**
   *{@summary Delete the ant from the map and the anthill.}<br>
   *@param f ant who is diing.
   *@lastEditedVersion 1.41
   */
  private void deleteFromGame(Fourmi f){
    f.getFere().nbrFourmisMortePlus1();
    f.getCCase().getContent().getGc().remove(f);
    f.getFere().getGc().remove(f);
  }
}
