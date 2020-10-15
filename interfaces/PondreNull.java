package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class PondreNull implements Pondre {

  public void unePonte(Creature c){
    erreur.erreur("Impossible de pondre avec la créature " + c.getId());
  }
}
