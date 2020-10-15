package fr.formiko.formiko.interfaces;
import fr.formiko.formiko.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;
import fr.formiko.usuel.math.math;

public class ChasseFourmi implements Chasse {
  private Creature c;

  // Fonctions propre -----------------------------------------------------------
  public void chasser(Creature c, int direction){
    this.c = c;
    GInsecte proieVisible = getProie();
    if (c.getCCase().getContenu().getGi().getDébut() != null){ // Si il y a un insecte sur la même case
      chasse(c);
    }else if (proieVisible.getDébut() != null){ // Si il y a un insecte a coté
      CCase pointDeLaProie = proieVisible.getDébut().getInsecte().getCCase();
      if (Main.getDifficulté() >= 1 || ((Fourmi)c).getFere().getJoueur().getIa()==false){ // En difficile les ia chasse les insectes les plus intéressants qu'elle voie.
        pointDeLaProie = proieVisible.getDébut().getInsectePlusDeNourritureFournie().getCCase();
      }
      debug.débogage(g.getM("laFourmi")+" " + c.getId()+ " "+g.get("ChasseFourmi.1")+" " + pointDeLaProie.getPoint());
      c.ceDeplacer(pointDeLaProie);
    }else { // Si il n'y a pas d'insecte
      c.ceDeplacer(direction);
    }
  }

  public void chasse(Creature c){
  this.c = c;
    //chasse
    Case pointActuel = c.getCCase().getContenu();
    GInsecte gi = pointActuel.getGi();
    debug.débogage("Chasse : action = "+c.getAction() + "actionMax = "+c.getActionMax());
    if (gi.getDébut() != null) { // sous forme de str I+id
      Insecte insecteTué;
      if (Main.getDifficulté() >= 0 || ((Fourmi)c).getFere().getJoueur().getIa()==false){ // en normal les ia chasse les insectes les plus intéressants sur la case ou elle sont.
        insecteTué = gi.getInsectePlusDeNourritureFournie();
      }else{
        insecteTué = gi.getDébut().getContenu();
      }
      //tuer :
      if (!insecteTué.getEstMort()){
        Message m = new Message(g.getM("laFourmi")+" "+ c.getId()+" "+g.get("ChasseFourmi.2")+" " + insecteTué.getId(), ((Fourmi) c).getFourmiliere().getId(),2);
        insecteTué.setEstMort(true);
        c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getType()).getCoutChasse()/2);
        if (c.getAction()<0){ return;}
      }
      //dépeusser :
      Message m = new Message(g.getM("laFourmi")+" "+ c.getId()+" "+g.get("ChasseFourmi.3")+" " + insecteTué.getId(), ((Fourmi) c).getFourmiliere().getId(),2);
      debug.débogage("Nourriture fournie = " + insecteTué.getNourritureFournie());
      int nourriture = math.min(insecteTué.getNourritureFournie(),((Fourmi) c).getNourritureMax()-((Fourmi) c).getNourriture());
      if (insecteTué.getNourritureFournie()==nourriture){
        Main.getGi().retirerInsecte(insecteTué);
        insecteTué.getCCase().getContenu().getGc().retirer(insecteTué);
      }else{
        insecteTué.setNourritureFournie(insecteTué.getNourritureFournie()-nourriture);
      }
      ((Fourmi) c).ajouteNourriture(nourriture);
      c.setActionMoins(((Fourmi) (c)).getEspece().getGIndividu().getIndividuParType(((Fourmi) c).getType()).getCoutChasse()/2);
    }
  }

  //COMMENT FONCTIONNE LA CHASSE
  private GInsecte getProie(){
    return c.getCCase().getGi(1); // 1 est le rayon du cercle de case pris en compte.
  }
}
