package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to died<br>
 * @author Hydrolien
 * @version 1.41
 */
public class MourirFourmi implements Serializable, Mourir {
  /**
   *{@summary Kill an ant an print a message.}<br>
   *We need to remove it from the map, from it anthill and from playing ant if it was playing.
   *@param c ant who is diing.
   *@param r reason of death.
   *@version 1.41
   */
  public void mourir(Creature c, int r){
    if(c==null){return;}
    c.setEstMort(true);
    if(c instanceof Fourmi){
      Fourmi f = (Fourmi)c;
      Message.messageMort(f,r);
      if(f.equals(Main.getPlayingAnt())){
        Main.setPlayingAnt(null);
      }
      deleteFromGame(f);
      if (f.getStade() != 0){
        // TODO les oeuf ou les larves (ou les nymphes) sont mangeable par les autres fourmis.
        //f.getCCase().getContenu().getGc().add(new Insecte(f.getCCase(),10 + (int) getNourriture()/3,0,0,0));
      }
    }else{
      erreur.erreurType("Fourmi","MourirFourmi.Mourir");
    }
  }
  /**
   *{@summary Delete the ant from the map.}<br>
   *@param c Creature who is diing.
   *@version 1.13
   */
  public void supprimerDeLaCarte(Creature c){
    if(c instanceof Fourmi){
      Fourmi f = (Fourmi)c;
      supprimerDeLaCarte(f);
    }else{
      erreur.erreurType("Fourmi","MourirFourmi.Mourir");
    }
  }
  /**
   *{@summary Delete the ant from the map and the anthill.}<br>
   *@param c ant who is diing.
   *@version 1.41
   */
  private void deleteFromGame(Fourmi f){
    f.getFere().nbrFourmisMortePlus1();
    f.getCCase().getContenu().getGc().retirer(f);
    f.getFere().getGc().retirer(f);
  }
}
