package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;
import java.io.Serializable;

/**
 * {@summary Ant implementation.<br/>}
 * Allow an ant to collect seed<br/>
 * @author Hydrolien
 * @version 1.1
 */
public class CollecteFourmi implements Serializable, Collecte {

  // Fonctions propre -----------------------------------------------------------
  /**
   *{@summary collect seeds.<br/>}
   *Ant search a seed. If it see a seed on the same Case it take it. If it see a seed on an other Case it goes to the Case.<br/>
   *It can choose the first 1 or the better 1 depending on the difficulty.<br/>
   *@param f The collecting ant.
   *@version 1.3
   */
  public void collecter(Fourmi f, byte direction){
    GGraine proieVisible = f.getCCase().getGg(1);
    if (f.getCCase().getContenu().getGg().getDébut() != null){ // Si il y a une graine sur la même case
      debug.débogage("la graine "+f.getCCase().getContenu().getGg().getDébut().getContenu().getId()+" a été détecté sur la meme case que la Fourmi.");
      debug.débogage("la fourmi est en "+f.getCCase().getContenu().description());
      collecte(f);
    }else if (proieVisible.getDébut() != null){ // Si il y a une graine a coté
      CCase pointDeLaProie = proieVisible.getDébut().getContenu().getCCase();
      if ((Main.getDifficulté() >= 1 || f.getFere().getJoueur().getIa()==false) && proieVisible.getGrainePlusDeNourritureFournie(f)!= null){ // En difficile les ia chasse les insectes les plus intéressants.
        pointDeLaProie = proieVisible.getGrainePlusDeNourritureFournie(f).getCCase();
      }
      debug.débogage("La fourmi " + f.getId()+ " a vue une proie en " + pointDeLaProie.getPoint());
      f.ceDeplacer(pointDeLaProie);
    }else { // Si il n'y a pas de graine visible
      f.ceDeplacer(direction);
    }
  }
  /**
   *{@summary collect a seed on the same Case.<br/>}
   *Ant search a seed on the same Case<br/>
   *If there is a seed, ant take it (make it disapear on the map) and will carry it to the anthill.
   *@param f The collecting ant.
   *@version 1.3
   */
  public void collecte(Fourmi f){
    Case pointActuel = f.getCCase().getContenu();
    GGraine gg = pointActuel.getGg();
    if (gg.getDébut() != null){
      Graine graineCollecté;
      if (Main.getDifficulté() >= 0 || f.getFere().getJoueur().getIa()==false){
        graineCollecté = gg.getGrainePlusDeNourritureFournieSansDureté(f);
      }else{
        graineCollecté = gg.getDébut().getContenu();
      }
      debug.débogage("Suppression de la graine "+graineCollecté.getId() + " en "+graineCollecté.getCCase().getContenu().description());
      graineCollecté.mourrir();
      f.setTransporté(graineCollecté);
      //pointActuel.getGg().retirer(graineCollecté);
      //graineCollecté.setCCase(null);
      f.setActionMoins(1); // TODO pour l'instant le cout collecte n'existe pas
    }else{
      erreur.erreur("La fonction collecte ne devrais pas être appeler sur une case vide.");
    }
  }
}
