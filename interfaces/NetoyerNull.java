package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.Creature;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class NetoyerNull implements Netoyer {

  // Fonctions propre -----------------------------------------------------------
  public void netoyer(Creature c){
    err(c);
  }
  public void netoyer(Creature c, Creature c2){
    err(c);
  }
  public boolean netoyerIa(Creature c){
    err(c);return false;
  }
  public int getNombreDeCreatureANetoyer(Creature c){
    return 0;
  }
  private void err(Creature c){
    erreur.erreur("Impossible de netoyer avec la créature " + c.getId());
  }
}
