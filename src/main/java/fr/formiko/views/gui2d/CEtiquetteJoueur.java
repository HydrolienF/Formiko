package fr.formiko.views.gui2d;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Joueur;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

public class CEtiquetteJoueur {
  public EtiquetteJoueur contenu;
  public CEtiquetteJoueur suivant;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public CEtiquetteJoueur(EtiquetteJoueur ej){
    contenu=ej;
  }
  public CEtiquetteJoueur(){}
  // GET SET --------------------------------------------------------------------
  public EtiquetteJoueur getContent(){return contenu;}
  public void setContenu(EtiquetteJoueur ej){contenu=ej;}
  public CEtiquetteJoueur getSuivant(){ return suivant;}
  public void setSuivant(CEtiquetteJoueur cej){ suivant=cej;}
  public void setSuivant(EtiquetteJoueur ej){ setSuivant(new CEtiquetteJoueur(ej));}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(suivant!=null){ return suivant.length()+1;}
    return 1;
  }
  public GJoueur getGJoueur(Carte mapo){
    int nbrDeFourmi=1;
    CEtiquetteJoueur cej = this;
    GJoueur gj = new GJoueur();
    while (cej!=null){
      Joueur j = new Joueur(nbrDeFourmi,cej.getContent().getIa(),mapo);
      j.setPseudo(cej.getContent().getPseudo());
      j.setPheromone(cej.getContent().getCouleur());
      gj.add(j);
      if(!cej.getContent().getIa()){ // si c'est un joueur Humain.
        if (mapo.getCasesNuageuses() || mapo.getCasesSombres()){
          j.initialisationCaseNS();
          j.updateCaseSN();
        }
      }
      cej = cej.getSuivant();
    }
    return gj;
  }
  public void afficheToi(){
    CEtiquetteJoueur cej = this;
    while(cej!=null){
      cej.getContent().afficheToi();
      cej=cej.getSuivant();
    }
  }
  public void remove(int idX){
    CEtiquetteJoueur cej = this;
    while(cej.getSuivant()!=null){
      if(cej.getSuivant().getContent().getId()==idX){cej.setSuivant(cej.getSuivant().getSuivant());break;} //on saute un mayon.
      cej=cej.getSuivant();
    }
    if(cej==null){ erreur.erreur("impossible de remove l'élément "+idX+" du GEtiquetteJoueur spécifié.");}
  }
}
