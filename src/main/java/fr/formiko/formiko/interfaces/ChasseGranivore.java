package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GGraine;
import fr.formiko.formiko.Graine;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;

import java.io.Serializable;

/**
 * {@summary Ant implementation.}<br>
 * Allow an ant to do hunt<br>
 * Ant are able to see other ObjetSurCarteAId as Creature at 1 Case of distance.
 * @author Hydrolien
 * @version 1.40
 */
public class ChasseGranivore implements Serializable, Chasse {
  private Creature c;

  public void setC(Creature cTemp){c=cTemp;}

  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary collect seeds.}<br>
   *Ant search a seed. If it see a seed on the same Case it take it. If it see a seed on an other Case it goes to the Case.<br>
   *It can choose the first 1 or the better 1 depending on the difficulty.<br>
   *@param c The collecting ant.
   *@version 1.40
   */
  public boolean chasser(Creature c, int direction){
    setC(c);
    if(!canHuntMore()){return false;}
    GGraine proieVisible = getProie();
    if (c.getCCase().getContenu().getGg().getDébut() != null){ // Si il y a une graine sur la même case
      debug.débogage("la graine "+c.getCCase().getContenu().getGg().getDébut().getContenu().getId()+" a été détecté sur la meme case que la Fourmi.");
      debug.débogage("la fourmi est en "+c.getCCase().getContenu().description());
      return chasse(c);
    }else if (proieVisible.getDébut() != null){ // Si il y a une graine a coté
      CCase pointDeLaProie = proieVisible.getDébut().getContenu().getCCase();
      Graine betterSeed = proieVisible.getGrainePlusDeNourritureFournieSansDureté();
      if ((Main.getDifficulté() >= 1 || !c.getIa()) && betterSeed!= null){
        pointDeLaProie = betterSeed.getCCase();
      }
      debug.débogage("La fourmi " + c.getId()+ " a vue une proie en " + pointDeLaProie.getPoint());
      c.ceDeplacer(pointDeLaProie);
    }else { // Si il n'y a pas de graine visible
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
   * @version 1.40
   */
   public boolean chasse(Creature c){
     setC(c);
     if(!canHuntMore()){return false;}
     GGraine gg = c.getCCase().getContenu().getGg();
     if (gg.getDébut() != null){
       Graine graineCollecté;
       if (Main.getDifficulté() >= 0 || !c.getIa()){
         graineCollecté = gg.getGrainePlusDeNourritureFournieSansDureté();
       }else{
         graineCollecté = gg.getDébut().getContenu();
       }
       debug.débogage("Suppression de la graine "+graineCollecté.getId() + " en "+graineCollecté.getCCase().getContenu().description());
       c.setTransported(graineCollecté);
       setActionMoins(c);
     }else{
       erreur.alerte("La fonction collecte ne devrais pas être appeler sur une case vide.","ChasseGranivore.chasse");
     }
     return canHuntMore();
   }

  //COMMENT FONCTIONNE LA CHASSE
  /**
   * {@summary fined a prey.}<br>
   * @version 1.40
   */
  private GGraine getProie(){
    return c.getCCase().getGg(1); // 1 est le rayon du cercle de case pris en compte.
  }
  public boolean canHuntMore(){return canHuntMore(c);}
  /**
   * {@summary Check if can hunt.}<br>
   * It should use Chasse.canHuntMore & specifique condition for granivore species.<br>
   * @version 1.40
   */
  //@Override
  public boolean canHuntMore(Creature c){
    //return c.getTransported()==null && super.canHuntMore(c);
    return c.getTransported()==null && c.getNourriture()<c.getNourritureMax() && c.getAction()>0;
  }
}
