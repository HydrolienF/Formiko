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
public class TourFourmi implements Serializable, Tour{
  private Fourmi f;
  /**
  *PLay 1 turn with Creature c.
  *@version 1.24
  */
  public void unTour(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    if(c instanceof Fourmi){
      f = (Fourmi) c;
      tour();
      debug.débogage("fin du tour de la fourmi.");
    }else{
      erreur.erreurType("Fourmi","TourFourmi");
    }
  }

  /**
  *Do turn actions
  */
  public void tour(){//TODO have all the turn here.
    f.tourF();
    /*
    preTour();
    c.eat(5);
    c.runAway();
    cleanItself();
    c.eat(20);
    feedOther();
    c.cleanOther();
    eat(80);
    backHomeToShareFood(); or reproduce();
    c.eat(100);
    finTour();

    */
  }
  public void finTour(){
    debug.débogage("Fin du tour de la Fourmi");
    // Un tour ça coute en age et en nourriture;
    f.setAgePlus1(); f.salir();
    if (f.getStade() == 0 || f.getStade() == -1 || f.getStade() == -2) {f.setNourritureMoinsConsomNourriture();}
    // if contition de température appartient a l'intervale idéale (et que stade = -1, -2 ou -3) : re setAgePlus1();
    if (!f.getFere().getJoueur().getIa()) { //pour un joueur humain.
      Main.getPj().setFActuelle(null);
      Main.getPb().setVisiblePa(false);
    }
    Main.getPs().setIdFourmiAjoué(-1);
  }
}
