package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import java.io.Serializable;

/**
 * {@summary Creature implementation.<br/>}
 * Allow a creature without any action aviable to play a turn<br/>
 * @author Hydrolien
 * @version 1.24
 */
public class TourCreatureSansAction implements Serializable, Tour{
  private Creature c;
  /**
  *PLay 1 turn with Creature c.
  *@version 1.24
  */
  public void unTour(Creature c){
    debug.débogage("la créature "+c.getId()+" tente de jouer un tour");
    this.c = c;
    tour();
    debug.débogage("fin du tour de la fourmi.");
  }

  public void tour(){
    // Un tour ça coute en age et en nourriture.
    c.setAgePlus1();
    if(c instanceof Fourmi){
      Fourmi f = (Fourmi) c;
      f.salir();
      if (f.getStade() == 0 || f.getStade() == -1 || f.getStade() == -2) {f.setNourritureMoinsConsomNourriture();}
    }else{
      c.setNourritureMoins1();
    }
  }

}
