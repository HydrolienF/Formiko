package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class CollecteNull implements Collecte {

  public void collecte(Fourmi c){
    erreur.erreur(g.get("CollecteNull")+" " + c.getId());
  }
  public void collecter(Fourmi c, byte directionSiPasDeProie){
    collecte(c);
  }
}
