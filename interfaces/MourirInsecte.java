package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5

public class MourirInsecte implements Mourir {
  public void mourir(Creature c, int r){
    System.out.println(c);
    c.setEstMort(true);
    try {
      Insecte i= (Insecte)c;
      if(i.getEstMort() && i.getAge()>i.getAgeMax()){
        try {
          i.getCCase().getContenu().getGc().retirer(i);
          Main.getGi().retirer(i);
        }catch (Exception e) {
          erreur.erreur("L'insecte "+i+" n'as pas pu être retiré.","MourirInsecte");
        }
      }else{
        i.setAge(0);//temps de putréfaction.
        i.setAgeMax(i.getAgeMax()/5);
      }
    }catch (Exception e) {
      erreur.erreurType("Insecte","MourirInsecte");
    }
  }
}
