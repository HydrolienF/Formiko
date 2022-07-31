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
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;

import java.io.Serializable;

/**
*{@summary Ant implementation.}<br>
*Allow an ant to do hunt<br>
*Ant are able to see other ObjetSurCarteAId as Creature at 1 Case of distance.
*@author Hydrolien
*@lastEditedVersion 2.29
*/
public class ChasseGranivore implements Serializable, Chasse {
  private final Creature c;

  public ChasseGranivore(Creature c){
    this.c=c;
  }

  // FUNCTIONS -----------------------------------------------------------------
  /**
   *{@summary collect seeds.}<br>
   *Ant search a seed. If it see a seed on the same Case it take it. If it see a seed on an other Case it goes to the Case.<br>
   *It can choose the first 1 or the better 1 depending on the difficulty.<br>
   *@param c The collecting ant.
   *@lastEditedVersion 1.40
   */
  public boolean chasser(Creature c, int direction){
    if(!canHuntMore()){return eatIfNeed();}
    GGraine proieVisible = getProie();
    if (c.getCCase().getContent().getGg().getHead() != null){ // Si il y a une graine sur la même case
      debug.débogage("la graine "+c.getCCase().getContent().getGg().getFirst().getId()+" a été détecté sur la meme case que la Fourmi.");
      debug.débogage("la fourmi est en "+c.getCCase().getContent().description());
      return chasse(c);
    }else if (proieVisible.getHead() != null){ // Si il y a une graine a coté
      CCase pointDeLaProie = proieVisible.getFirst().getCCase();
      Graine betterSeed = proieVisible.getBetterSeed();
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
   * @lastEditedVersion 1.40
   */
   public boolean chasse(Creature c){
     if(c.getTransported()!=null){
       System.out.println("message dropTransported");
       if(c instanceof Fourmi){
         new Message(g.get("DropTransportedDone"), ((Fourmi)c).getJoueur().getId(), 2);
       }
       c.dropTransported();
       setActionMoins(c);
       return true;
     }
     if(!canHuntMore()){return false;}
     if (havePreyOnSameSquare(c)){
       GGraine gg = c.getCCase().getContent().getGg();
       Graine graineCollecté;
       if (Main.getDifficulté() >= 0 || !c.getIa()){
         graineCollecté = gg.getBetterSeed();
       }else{
         graineCollecté = gg.getFirst();
       }
       // debug.débogage("Suppression de la graine "+graineCollecté.getId() + " en "+graineCollecté.getCCase().getContent().description());
       c.setTransported(graineCollecté);
       setActionMoins(c);
     }else{
       erreur.alerte("Function to collect seed should not be call on empty Square");
     }
     return canHuntMore();
   }

  //COMMENT FONCTIONNE LA CHASSE
  /**
   * {@summary fined a prey.}<br>
   * @lastEditedVersion 1.40
   */
  private GGraine getProie(){
    return c.getCCase().getGg(1); // 1 est le rayon du cercle de case pris en compte.
  }
  public boolean canHuntMore(){return canHuntMore(c);}
  /**
   * {@summary Check if can hunt.}<br>
   * It should use Chasse.canHuntMore &#38; specifics conditions for granivore species.<br>
   * @lastEditedVersion 1.40
   */
  //@Override
  public boolean canHuntMore(Creature c){
    //return c.getTransported()==null && super.canHuntMore(c);
    return c.getTransported()==null && c.getFood()<c.getMaxFood() && c.getAction()>0;
  }
  private boolean eatIfNeed(){
    // if(c.isHungry(90)){
    //   if(){//peux ouvrir la graine qu'elle porte et la manger
    //     //ouvrir puis manger ou juste manger.
    //   }else{
    //     if(!(c instanceof Fourmi)){
    //       return false;
    //     }else if((Fourmi c).estALaFere()){
    //       //choisir une graine mangeable.
    //       //if n'as pas pris de graine return false;
    //     }else{
    //
    //     }
    //   }
    //   return true;
    // }
    return false;
  }

  /**
  *{@summary Return true if there is prey on same square.}<br>
  *@lastEditedVersion 2.29
  */
  //TOTEST
  @Override
  public boolean havePreyOnSameSquare(Creature c){
    return !c.getCCase().getContent().getGg().isEmpty();
  }

  // Special actions for ChasseGranivore ---------------------------------------
  /**
  *{@summary Eat the transported seed.}<br>
  *@lastEditedVersion 2.29
  */
  //TOTEST
  @Override
  public void eatSeed(){
    erreur.info("eatSeed");
    if(canEatSeed()){
      Graine gr = (Graine)c.getTransported();
      if(!gr.isOpen()){
        breakSeed();
      }
      if(!gr.isOpen()){
        System.out.println("message cantOpenSeed");
        if(c instanceof Fourmi){
          new Message(g.get("CantOpenSeed"), ((Fourmi)c).getJoueur().getId(), 2);
        }
      }else{
        c.addFood(gr.getGivenFood());
        c.setTransported(null);
        System.out.println("message eat seed");
        if(c instanceof Fourmi){
          new Message(g.get("OpenSeedDone"), ((Fourmi)c).getJoueur().getId(), 2);
        }
      }
    }else{
      erreur.alerte("An action have been launch even if it should not be aviable");
    }
  }
  /**
  *{@summary Break the transported seed.}<br>
  *@lastEditedVersion 2.29
  */
  //TOTEST
  @Override
  public void breakSeed(){
    erreur.info("breakSeed");
    if(canBreakSeed()){
      Graine gr = (Graine)c.getTransported();
      gr.setOpen(true);
      System.out.println("message breakSeed");
      if(c instanceof Fourmi){
        new Message(g.get("BreakSeedDone"), ((Fourmi)c).getJoueur().getId(), 2);
      }
    }else{
      erreur.alerte("An action have been launch even if it should not be aviable");
    }
  }
  /**
  *{@summary Return true if Creature can eat the transported seed.}<br>
  *@lastEditedVersion 2.29
  */
  @Override
  public boolean canEatSeed(){
    return (c.getAction()>0
        && c.getTransported()!=null
        && c.getTransported() instanceof Graine
        && (((Graine)c.getTransported()).isOpen() || ((Graine)c.getTransported()).canBeOpenBy(c)));
  }
  /**
  *{@summary Return true if Creature can break the transported seed.}<br>
  *@lastEditedVersion 2.29
  */
  @Override
  public boolean canBreakSeed(){
    return (c.getAction()>0
        && c.getTransported()!=null
        && c.getTransported() instanceof Graine
        && ((Graine)c.getTransported()).canBeOpenBy(c));
  }
}
