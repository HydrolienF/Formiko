package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class EvoluerNull implements Evoluer {

  public void evoluer(Creature c){
    erreur.erreur(g.get("EvoluerNull")+" " + c.getId());
  }

}
