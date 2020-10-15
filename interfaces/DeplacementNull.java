package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.erreur;

public class DeplacementNull implements Deplacement {

  public void unMouvement(Creature c, boolean bIa){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }
  public void unMouvement(Creature c, CCase p){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }
  public void unMouvement(Creature c, int direction){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }
  public void plusieurMouvement(Creature c, CCase cc){
    erreur.erreur("Impossible de déplacer avec la créature " + c.getId());
  }

}
