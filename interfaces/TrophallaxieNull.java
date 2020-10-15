package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class TrophallaxieNull implements Trophallaxie {

  /*public void trophallaxieIa(Creature c1, Creature c2){
    erreur.erreur("Impossible de trophalaxer avec la créature " + c1.getId());
  }*/
  public void trophallaxie(Creature c, Creature c2, int x){
    erreur.erreur("Impossible de trophalaxer avec la créature " + c.getId());
  }
  public void trophallaxie(Creature c, int id, int x){
    trophallaxie(c,(Creature) null, -1);
  }
  public void trophallaxer(Creature c){
    trophallaxie(c,(Creature) null, -1);
  }
}
