package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;

public class CollecteFourmi implements Collecte {

  // Fonctions propre -----------------------------------------------------------
  public void collecter(Fourmi c, byte direction){
    GGraine proieVisible = c.getCCase().getGg(1);
    if (c.getCCase().getContenu().getGg().getDébut() != null){ // Si il y a une graine sur la même case
      debug.débogage("la graine "+c.getCCase().getContenu().getGg().getDébut().getContenu().getId()+" a été détecté sur la meme case que la Fourmi.");
      debug.débogage("la fourmi est en "+c.getCCase().getContenu().description());
      collecte(c);
    }else if (proieVisible.getDébut() != null){ // Si il y a une graine a coté
      CCase pointDeLaProie = proieVisible.getDébut().getContenu().getCCase();
      if ((Main.getDifficulté() >= 1 || c.getFere().getJoueur().getIa()==false) && proieVisible.getGrainePlusDeNourritureFournie(c)!= null){ // En difficile les ia chasse les insectes les plus intéressants.
        pointDeLaProie = proieVisible.getGrainePlusDeNourritureFournie(c).getCCase();
      }
      debug.débogage("La fourmi " + c.getId()+ " a vue une proie en " + pointDeLaProie.getPoint());
      c.ceDeplacer(pointDeLaProie);
    }else { // Si il n'y a pas d'insecte
      c.ceDeplacer(direction);
    }
  }

  public void collecte(Fourmi c){
    Case pointActuel = c.getCCase().getContenu();
    GGraine gg = pointActuel.getGg();
    if (gg.getDébut() != null){
      Graine graineCollecté;
      if (Main.getDifficulté() >= 0 || c.getFere().getJoueur().getIa()==false){
        graineCollecté = gg.getGrainePlusDeNourritureFournieSansDureté(c);
      }else{
        graineCollecté = gg.getDébut().getContenu();
      }
      debug.débogage("Suppression de la graine "+graineCollecté.getId() + " en "+graineCollecté.getCCase().getContenu().description());
      graineCollecté.mourrir();
      c.setTransporté(graineCollecté);
      //pointActuel.getGg().retirer(graineCollecté);
      //graineCollecté.setCCase(null);
      c.setActionMoins(1); // pour l'instant le cout collecte n'existe pas
    }else{
      erreur.erreur("La fonction collecte ne devrais pas être appeler sur une case vide.");
    }
  }
}
