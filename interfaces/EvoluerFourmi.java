package fr.formiko.formiko;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;
import fr.formiko.formiko.Evoluer;
import fr.formiko.formiko.interfaces.*;

public class EvoluerFourmi implements Evoluer{

  public void evoluer(Creature c){
    Fourmi f;
    try {
      f = (Fourmi)c;
    }catch (Exception e) {
      erreur.erreur("Impossible de faire EvoluerFourmi une créature qui n'est pas une Fourmi.");
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
      if (in.getCoutPondre() != -1) { f.pondre = new PondreReine(); }
      if (in.getCoutDéplacement() != -1) { f.déplacement = new DeplacementFourmi();}
      if (in.getCoutChasse() != -1) { f.chasse = new ChasseFourmi();}
      if (in.getCoutTrophallaxie() != -1) { f.trophallaxie = new TrophallaxieFourmi();}
      if (f.getEspece().getGranivore()){ f.collecte = new CollecteFourmi();}
      f.netoyer =  new NetoyerFourmi();
      // caractéristiques de l'espèce :
      f.setNourritureMax(in.getNourritureMax());
      f.setAction(in.getActionMax()); f.setActionMax(in.getActionMax());
      f.setAgeMax(f.getAgeMaxIndividu());
    }
  }

}
