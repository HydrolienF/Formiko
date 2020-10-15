package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;

public class MourirFourmi implements Mourir {
  public void mourir(Creature c, int r){
    c.setEstMort(true);
    try{
      Fourmi f = (Fourmi)c;
      Message.messageMort(f,r);
      f.getFere().nbrFourmisMortePlus1();
      f.getCCase().getContenu().getGc().retirer(f);
      f.getFere().getGc().retirer(f);
      if (f.getStade() != 0){
        // a implémenté plus tard
        // les oeuf ou les larves (ou les nymphes) sont mangeable par les autres fourmis.
        //f.getCCase().getContenu().getGc().ajouter(new Insecte(f.getCCase(),10 + (int) getNourriture()/3,0,0,0));
      }

    }catch (Exception e) {
      erreur.erreurType("Fourmi","MourirFourmi.Mourir");
    }
  }
}
