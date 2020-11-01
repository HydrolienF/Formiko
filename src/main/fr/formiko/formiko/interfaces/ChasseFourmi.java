package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;
import fr.formiko.usuel.math.math;
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to do hunt<br/>
 * Ant are able to see other ObjetSurCarteAId as Creature at 1 Case of distance.
 * @author Hydrolien
 * @version 1.1
 */
public class ChasseFourmi implements Serializable, Chasse {
  private Creature c;

  // Fonctions propre -----------------------------------------------------------
  /**
   * {@summary try to hunt or moove.<br/>}
   * @param c The hunting Creature.
   * @param direction The direction were the Creature will go if any Prey is visible.
   * @version 1.1
   */
  public void chasser(Creature c, int direction){
    this.c = c;
    GInsecte proieVisible = getProie();
    if (c.getCCase().getContenu().getGi().getDébut() != null){ // Si il y a un insecte sur la même case
      chasse(c);
    }else if (proieVisible.getDébut() != null){ // Si il y a un insecte a coté
      CCase pointDeLaProie = proieVisible.getDébut().getInsecte().getCCase();
      if (Main.getDifficulté() >= 1 || ((c instanceof Fourmi) && ((Fourmi)c).getFere().getJoueur().getIa()==false)){ // En difficile les ia chasse les insectes les plus intéressants qu'elle voie.
        pointDeLaProie = proieVisible.getDébut().getInsectePlusDeNourritureFournie().getCCase();
      }
      debug.débogage(g.getM("laFourmi")+" " + c.getId()+ " "+g.get("ChasseFourmi.1")+" " + pointDeLaProie.getPoint());
      c.ceDeplacer(pointDeLaProie);
    }else { // Si il n'y a pas d'insecte
      c.ceDeplacer(direction);
    }
  }
  /**
   * {@summary actions during hunt.<br/>}
   * An Ant kill an Insect in the same Case<br/>
   * It can choose the first 1 or the better 1 depending on the difficulty.<br/>
   * @param c The hunting Creature.
   * @version 1.1
   */
  public void chasse(Creature c){
  this.c = c;
    //chasse
    Case pointActuel = c.getCCase().getContenu();
    GInsecte gi = pointActuel.getGi();
    debug.débogage("Chasse : action = "+c.getAction() + "actionMax = "+c.getActionMax());
    if (gi.getDébut() != null) { // sous forme de str I+id
      Insecte insecteTue;
      if (Main.getDifficulté() >= 0 || ((c instanceof Fourmi) && ((Fourmi)c).getFere().getJoueur().getIa()==false)){ // en normal les ia chasse les insectes les plus intéressants sur la case ou elle sont.
        insecteTue = gi.getInsectePlusDeNourritureFournie();
      }else{
        insecteTue = gi.getDébut().getContenu();
      }
      tuer(insecteTue);
      if (c.getAction()<0){ return;}
      depecer(insecteTue);
    }
  }

  //COMMENT FONCTIONNE LA CHASSE
  /**
   * {@summary fined a prey.<br/>}
   * @version 1.1
   */
  private GInsecte getProie(){
    //TODO on dervrais plutot faire un GCreature pour pouvoir inclure des Fourmis et potentiellement retirer des insectes.
    return c.getCCase().getGi(1); // 1 est le rayon du cercle de case pris en compte.
  }
  /**
   * {@summary kill during hunt.<br/>}
   * An Ant kill an Insect<br/>
   * @param insecteTue The insect that will die.
   * @version 1.1
   */
  private boolean tuer(Insecte insecteTue){
    if (!insecteTue.getEstMort()){
      Message m = new Message(g.getM("laFourmi")+" "+ c.getId()+" "+g.get("ChasseFourmi.2")+" " + insecteTue.getId(), ((Fourmi) c).getFourmiliere().getId(),2);
      insecteTue.setEstMort(true);
      if(c instanceof Fourmi){
        c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getTypeF()).getCoutChasse()/2);
      }else{
        c.setActionMoins(10);
      }
      debug.débogage("tuer l'insecte "+insecteTue.getId()+" effectué");
      return true;
    }else{
      return false;
    }
  }
  /**
   * {@summary butcher during hunt.<br/>}
   * An Ant kill an Insect<br/>
   * @param insecteTue The died Insect.
   * @version 1.1
   */
  private boolean depecer(Insecte insecteTue){
    if(insecteTue==null){return false;}
    Message m = new Message(g.getM("laFourmi")+" "+ c.getId()+" "+g.get("ChasseFourmi.3")+" " + insecteTue.getId(), ((Fourmi) c).getFourmiliere().getId(),2);
    debug.débogage("Nourriture fournie = " + insecteTue.getNourritureFournie());
    int nourriture = math.min(insecteTue.getNourritureFournie(),c.getNourritureMax()-c.getNourriture());
    if (insecteTue.getNourritureFournie()==nourriture){
      insecteTue.supprimerDeLaCarte();
    }else{
      insecteTue.setNourritureFournie(insecteTue.getNourritureFournie()-nourriture);
    }
    c.ajouteNourriture(nourriture);
    debug.débogage("dépecer l'insecte "+insecteTue.getId()+" effectué");
    if(c instanceof Fourmi){
      c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getTypeF()).getCoutChasse()/2);
    }else{
      c.setActionMoins(10);
    }
    return true;
  }
}
