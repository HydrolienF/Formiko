package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class ChasseNull implements Chasse {

  public void chasse(Creature c){
    erreur.erreur(g.get("ChasseNull")+" " + c.getId());
  }
  public void chasser(Creature c, int directionSiPasDeProie){
    chasse(c);
  }
}
