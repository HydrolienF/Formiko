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
 * @version 1.1
 */
public class MourirInsecte implements Serializable, Mourir {
  /**
   * {@summary Died without beeing eated.}<br>
   *@param c Insecte who is dieing
   *@param r Reason of death
   * @version 1.1
   */
  public void mourir(Creature c, int r){
    System.out.println(c);
    c.setEstMort(true);
    if(c instanceof Insecte){
      Insecte i= (Insecte)c;
      if(i.getEstMort() && i.getAge()>i.getAgeMax()){
        supprimerDeLaCarte(i);
      }else{
        devenirUnCadavre(i);
      }
    }else{
      erreur.erreurType("Insecte","MourirInsecte");
    }
  }
  /**
   * {@summary Delet an Insect from the map.}<br>
   * @version 1.2
   */
  public void supprimerDeLaCarte(Creature c){
    int id = -1;
    if(c instanceof Insecte){
      Insecte i = (Insecte)c;
      id = i.getId();
      i.getCCase().getContenu().getGc().retirer(i);
      try {
        Main.getGi().retirer(i);
      }catch (Exception e) {
        erreur.erreur("L'insecte "+id+" n'as pas pu être retiré de Main.Gi.","MourirInsecte");
      }
    }else{
      erreur.erreur("L'insecte "+id+" n'as pas pu être retiré.","MourirInsecte");
    }
  }
  /**
   * {@summary Transform an Insect to a dead body.}<br>
   * @version 1.2
   */
  public void devenirUnCadavre(Insecte i){
    i.setAge(0);//temps de putréfaction.
    i.setAgeMax(i.getAgeMax()/5);
    i.tour = new TourCreatureMorte();
  }
}
