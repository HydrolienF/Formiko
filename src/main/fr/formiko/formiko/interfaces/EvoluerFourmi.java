package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.Message;
import fr.formiko.formiko.interfaces.*;
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to do grow up passing through stages egg, larva, nymph, imago.<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class EvoluerFourmi implements Serializable, Evoluer{
  /**
   *{@summary ant grow up.<br/>}
   *An Ant will grow up 3 time from stade -3 to 0.<br/>
   *The last grow up is a bit special bexause il allow ant to do new action as PondreReine or TrophallaxieFourmi if getStade is ok &#38; ant species is able to do it.<br/>
   *Every grow up put age before next stade or death to 0. Change ageMax (max age). Change actionMax if ant is now able to do action. Change nourritureMax (max food).
   *@param c The growing up ant.
   *@version 1.3
   */
  public void evoluer(Creature c){
    Fourmi f;
    if(c instanceof Fourmi){
      f = (Fourmi)c;
    }else{
      erreur.erreurType("Fourmi","EvoluerFourmi");
      return;
    }
    if(f.getStade()==0){return;}
    Individu in = f.getIndividu();
    if(Main.getTour()>1){new Message("La fourmi "+f.getId()+" passe au stade suivant");}
    //-2 et -1 = facile = temps plus court pour passer au stade suivant pour les joueurs.
    double diff = f.getMultiplicateurDeDiff();
    f.setStade(f.getStade()+1); f.setAge(0);
    if (f.getStade()==-2) { f.setAgeMax((int)(in.getAgeMax(1)*diff)); f.setNourritureMax(in.getNourritureMax(1));}
    else if(f.getStade()==-1) { f.setAgeMax((int)(in.getAgeMax(2)*diff)); f.setNourritureMax(in.getNourritureMax(2));}
    else if(f.getStade() == 0){
      if(f.getTypeF()<2){f.setAilesCoupees(false);}//si c'est une fourmi ailé qui est désormais imago.
      //TODO ajouter la capacité de voler
      if (in.getCoutPondre() != -1) { f.pondre = new PondreReine(); }
      if (in.getCoutDéplacement() != -1) { f.déplacement = new DeplacementFourmi();}
      if (in.getCoutChasse() != -1) { f.chasse = new ChasseInsectivore();}
      if (in.getCoutTrophallaxie() != -1) { f.trophallaxie = new TrophallaxieFourmi();}
      if (f.getEspece().getGranivore()){ f.collecte = new CollecteFourmi();}
      f.netoyer =  new NetoyerFourmi();
      f.tour = new TourFourmi();
      // caractéristiques de l'espèce :
      f.setNourritureMax(in.getNourritureMax());
      f.setAction(in.getActionMax()); f.setActionMax(in.getActionMax());
      f.setAgeMax(f.getAgeMaxIndividu());
    }
  }

}
