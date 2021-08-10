package fr.formiko.views.gui2d;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.Liste;
import fr.formiko.formiko.Joueur;

public class GEtiquetteJoueur extends Liste<EtiquetteJoueur> {

  // CONSTRUCTEUR ---------------------------------------------------------------
  public GEtiquetteJoueur(int x){
    String pseudo = Main.getOp().getPseudo();
    if(pseudo.equals("") || pseudo.equals("-1")){pseudo = g.getM("joueur")+" 1";}
    this.add(new EtiquetteJoueur(pseudo,false));
    for (int i=0;i<x ;i++) {
      this.add(new EtiquetteJoueur(true));
    }
    this.add(new EtiquetteJoueur());
  }
  public GEtiquetteJoueur(){}
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  @Override
  public boolean add(EtiquetteJoueur ej){
    super.add(ej);
    try {
      enableLaunchButtonIfNeeded();
    }catch (Exception e) {}
    return true;
  }
  @Override
  public boolean remove(Object o){
    if(super.remove(o)){
      disableLaunchButtonIfNeeded();
      return true;
    }
    return false;
  }

  public GJoueur getGJoueur(Carte mapo){
    remove(getLast()); // not realy opti.
    int nbrDeFourmi=1;
    GJoueur gj = new GJoueur();
    for (EtiquetteJoueur ej : this ) {
      Joueur j = new Joueur(nbrDeFourmi,ej.getIa(),mapo);
      j.setPseudo(ej.getPseudo());
      j.setPheromone(ej.getCouleur());
      gj.add(j);
      if(!ej.getIa()){ // si c'est un joueur Humain.
        if (mapo.getCasesNuageuses() || mapo.getCasesSombres()){
          j.initialisationCaseNS();
          j.updateCaseSN();
        }
      }
    }
    return gj;
  }

  public void disableLaunchButtonIfNeeded(){
    for (EtiquetteJoueur ej : this ) {
      if(ej.getOuvert() && !ej.getIa()){ //if there is a human player
        return;
      }
    }
    //if there is 0 humain player
    try {
      Panneau.getView().getPnp().getLaunchButton().setEnabled(false);
    }catch (Exception e) {
      erreur.alerte("fail to disable launch button with 0 player");
    }
  }

  public void enableLaunchButtonIfNeeded(){
    for (EtiquetteJoueur ej : this ) {
      if(ej.getOuvert() && !ej.getIa()){ //if there is a human player
        Panneau.getView().getPnp().getLaunchButton().setEnabled(true);
        return;
      }
    }
  }
}
