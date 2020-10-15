package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class MourirNull implements Mourir {

  public void mourir(Creature c, int x){
    erreur.erreur("Impossible de mourir avec la créature " + c.getId());
  }
}
