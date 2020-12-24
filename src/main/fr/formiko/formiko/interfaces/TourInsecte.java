package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to play a turn<br/>
 * @author Hydrolien
 * @version 1.24
 */
public class TourInsecte implements Serializable, Tour{
  private Insecte f;
  /**
  *PLay 1 turn with Creature c.
  *@version 1.24
  */
  public void unTour( Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    if(c instanceof Insecte){
      f = (Insecte) c;
      tour();
      debug.débogage("fin du tour de l'insecte.");
    }else{
      erreur.erreurType("Insecte","TourInsecte");
    }
  }

  /**
  *{@summary Play a turn as an Insecte.<br>}
  *@version 1.13
  */
  public void tour(){
    int i=0;
    while(f.getAction()>i){
      if(f.getNourriture()<f.getNourritureMax()/2 && f.getCCase().getContenu().getNourritureInsecte() > f.getNourritureMangeable()){
        f.manger();
      }else{
        f.ceDeplacer(true); // ce déplacer de façon alléatoire.
      }
      i++;
    }
    // Un tour ça coute en age et en nourriture.
    f.setNourritureMoins1();
    f.setAgePlus1();
  }

}
