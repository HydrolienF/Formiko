package fr.formiko.views.gui2d;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Main;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.Liste;
import fr.formiko.formiko.Joueur;
/**
*{@summary Liste of EtiquetteJoueur.}
*It is used to store &#38; print data to the user,
*so that he can choose player info before start the game.
*@lastEditedVersion 2.5
*@author Hydrolien
*/
public class GEtiquetteJoueur extends Liste<EtiquetteJoueur> {

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary create a basic GEtiquetteJoueur with x items.}<br>
  *It have 1 humain player &#38; x-1 ia player.
  *@param x number of player.
  *@lastEditedVersion 1.x
  */
  public GEtiquetteJoueur(int x){
    if(x<1 || x>1000){throw new IllegalArgumentException();}
    String pseudo = Main.getOp().getPseudo();
    if(pseudo.equals("") || pseudo.equals("-1")){pseudo = g.getM("joueur")+" 1";}
    this.add(new EtiquetteJoueur(pseudo,false));
    for (int i=0;i<x ;i++) {
      this.add(new EtiquetteJoueur(true));
    }
    this.add(new EtiquetteJoueur());
  }
  /** Create a empty GEtiquetteJoueur. */
  public GEtiquetteJoueur(){}
  // GET SET -------------------------------------------------------------------

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary add an Item &#38; enableLaunchButtonIfNeeded.}
  *@lastEditedVersion 2.5
  */
  @Override
  public boolean add(EtiquetteJoueur ej){
    super.add(ej);
    try {
      enableLaunchButtonIfNeeded();
    }catch (Exception e) {}
    return true;
  }
  /**
  *{@summary remove an Item &#38; disableLaunchButtonIfNeeded.}
  *@lastEditedVersion 2.5
  */
  @Override
  public boolean remove(Object o){
    if(super.remove(o)){
      disableLaunchButtonIfNeeded();
      return true;
    }
    return false;
  }
  /**
  *{@summary return this as a fully usable GJoueur.}<br>
  *It use Pseudo, Pheromone &#38; ia value to create new players.
  *@lastEditedVersion 2.5
  */
  public GJoueur getGJoueur(Carte mapo){
    int nbrDeFourmi=1;
    GJoueur gj = new GJoueur();
    for (EtiquetteJoueur ej : this ) {
      if(ej.getOuvert()){
        Joueur j = new Joueur(nbrDeFourmi,ej.getIa(),mapo);
        j.setPseudo(ej.getPseudo());
        j.setPheromone(ej.getCouleur());
        gj.add(j);
        if(!ej.getIa()){ // si c'est un joueur Humain.
          if (mapo.getSquaresNuageuses() || mapo.getSquaresSombres()){
            j.initialisationSquareNS();
            j.updateSquareSN();
          }
        }
      }
    }
    return gj;
  }
  /**
  *{@summary if there is 0 humain player, turn off launch button.}
  *@lastEditedVersion 2.5
  */
  public void disableLaunchButtonIfNeeded(){
    for (EtiquetteJoueur ej : this ) {
      if(ej.getOuvert() && !ej.getIa()){ //if there is a human player
        return;
      }
    }
    //if there is 0 humain player
    try {
      FPanel.getView().getPnp().getLaunchButton().setEnabled(false);
    }catch (Exception e) {
      erreur.alerte("fail to disable launch button with 0 player");
    }
  }
  /**
  *{@summary if there is at least 1 humain player, turn on launch button.}
  *@lastEditedVersion 2.5
  */
  public void enableLaunchButtonIfNeeded(){
    for (EtiquetteJoueur ej : this ) {
      if(ej.getOuvert() && !ej.getIa()){ //if there is a human player
        FPanel.getView().getPnp().getLaunchButton().setEnabled(true);
        return;
      }
    }
  }
}
