package fr.formiko.formiko.interfaces;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

/**
 * {@summary Insect implementation.}<br>
 * Allow an insect to died<br>
 * @author Hydrolien
 * @lastEditedVersion 1.1
 */
public class MourirInsecte implements Serializable, Mourir {
  /**
   * {@summary Died without beeing eated.}<br>
   *@param c Insecte who is dieing
   *@param r Reason of death
   * @lastEditedVersion 1.1
   */
  public void mourir(Creature c, int r){
    c.setIsDead(true);
    if(c instanceof Insecte){
      Insecte i= (Insecte)c;
      if(i.getIsDead() && i.getAge()>i.getMaxAge()){
        supprimerDeLaCarte(i);
      }else{
        devenirUnCadavre(i);
      }
    }else{
      erreur.erreurType("Insecte");
    }
  }
  /**
   * {@summary Delet an Insect from the map.}<br>
   * @lastEditedVersion 1.2
   */
  public void supprimerDeLaCarte(Creature c){
    int id = -1;
    if(c instanceof Insecte){
      Insecte i = (Insecte)c;
      try {
        id = i.getId();
        i.getCCase().getContent().getGc().remove(i);
      }catch (Exception e) {
        erreur.erreur("L'insecte "+id+" n'as pas pu être retiré de sa case.");
      }
      try {
        Main.getGi().remove(i);
      }catch (Exception e) {
        erreur.erreur("L'insecte "+id+" n'as pas pu être retiré de Main.Gi.");
      }
    }else{
      erreur.erreur("L'insecte "+id+" n'as pas pu être retiré.");
    }
  }
  /**
   * {@summary Transform an Insect to a dead body.}<br>
   * @lastEditedVersion 1.2
   */
  public void devenirUnCadavre(Insecte i){
    i.setAge(0);//temps de putréfaction.
    i.setMaxAge(i.getMaxAge()/5);
    i.tour = new TourCreatureMorte();
  }
}
