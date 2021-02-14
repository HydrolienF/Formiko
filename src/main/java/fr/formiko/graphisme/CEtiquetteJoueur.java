package fr.formiko.graphisme;

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
  public EtiquetteJoueur getContenu(){return contenu;}
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
      Joueur j = new Joueur(nbrDeFourmi,cej.getContenu().getIa(),mapo);
      j.setPseudo(cej.getContenu().getPseudo());
      j.setPheromone(cej.getContenu().getCouleur());
      gj.ajouter(j);
      if(!cej.getContenu().getIa()){ // si c'est un joueur Humain.
        if (mapo.getCasesNuageuses() || mapo.getCasesSombres()){
          j.initialisationCaseNS();
          j.actualiserCaseSN();
        }
      }
      cej = cej.getSuivant();
    }
    return gj;
  }
  public void afficheToi(){
    CEtiquetteJoueur cej = this;
    while(cej!=null){
      cej.getContenu().afficheToi();
      cej=cej.getSuivant();
    }
  }
  public void retirer(int idX){
    CEtiquetteJoueur cej = this;
    while(cej.getSuivant()!=null){
      if(cej.getSuivant().getContenu().getId()==idX){cej.setSuivant(cej.getSuivant().getSuivant());break;} //on saute un mayon.
      cej=cej.getSuivant();
    }
    if(cej==null){ erreur.erreur("impossible de retirer l'élément "+idX+" du GEtiquetteJoueur spécifié.");}
  }
}
