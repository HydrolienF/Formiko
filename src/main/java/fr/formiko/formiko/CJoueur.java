package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.ecrireUnFichier;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;

public class CJoueur implements Serializable{
  private Joueur contenu;
  private CJoueur suivant;
  // CONSTRUCTORS ----------------------------------------------------------------
  public CJoueur(Joueur j){
    contenu = j;
  }
  // GET SET ----------------------------------------------------------------------
  public void setSuivant(CJoueur c){suivant = c;}
  public CJoueur getSuivant(){return suivant;}
  public Joueur getJoueur(){return getContent();}
  public Joueur getContent(){return contenu;}
  public GCreature getGc(){
    CJoueur act = this;
    GCreature gcr = new GCreature();
    gcr.add(this.getContent().getFere().getGc().copier());
    while(act.getSuivant() != null){
      GCreature gcr2 = act.getSuivant().getContent().getFere().getGc().copier();
      gcr.add(gcr2);
      act=act.getSuivant();
    }
    return gcr;
  }
  public Joueur getJoueurParId(int id){
    if(this.getContent().getId()==id){ return this.getContent();}
    if(this.getSuivant()==null){return null;}
    return suivant.getJoueurParId(id);
  }
  public GJoueur getJoueurHumain(){
    GJoueur gjr = new GJoueur();
    CJoueur cj = this;
    while(cj!= null){
      if(!cj.getContent().getIa()){ gjr.add(cj.getContent());}
      cj = cj.getSuivant();
    }
    return gjr;
  }
  // FUNCTIONS -----------------------------------------------------------------
  public String toString(){
    if(suivant==null){return contenu.toString();}
    return contenu.toString() +"\n"+ suivant.toString();
  }
  public int length(int x){
    if (suivant != null) { x++; return suivant.length(x);}
    return x;
  }
  public Joueur getJoueurNonIa(){
    if(getSuivant() == null){ return null;} // On a rien trouvé
    if(getSuivant().getJoueur().getIa() == false){ return getSuivant().getJoueur();} // on a trouvé
    return getSuivant().getJoueurNonIa(); // on continue les recherches.
  }
  public int getNbrDeJoueurVivant(){
    int x = 0; if(contenu.getFere().getGc().length()!=0){x=1;}
    if (suivant!=null){return x+suivant.getNbrDeJoueurVivant();}
    else{ return x;}
  }
  // public void afficheToiParciel(){
  //   String m = "";
  //   if (suivant != null ){ m = " "+g.get("CJoueur.2")+" "+ this.getSuivant().getJoueur().getId();}
  //   erreur.println(g.get("CJoueur.1")+" " + this.contenu.getId() + m);
  // }
  // public void afficheTout(){
  //   erreur.println(this);
  //   if (suivant != null){
  //     suivant.afficheTout();
  //   }
  // }
  public void remove(Joueur j){
    if (suivant == null){
      erreur.erreur("Le joueur "+j.getId()+" n'as pas pue être retiré");
    } else if( suivant.getContent()==j){
      suivant = suivant.getSuivant();
    }else{
      suivant.remove(j);
    }
  }
  // public void enregistrerLesScores(){
  //   GString gs = new GString();
  //   CJoueur cj = this;
  //   gs.add(cj.getContent().getFere().enregistrerLesScores());
  //   cj=cj.getSuivant();
  //   //
  //   while(cj!=null){
  //     gs.add(cj.getContent().getFere().enregistrerLesScores());
  //     cj=cj.getSuivant();
  //   }
  //   gs = gs.transformerScore();
  //   ecrireUnFichier.ecrireUnFichier(gs,Main.getFolder().getFolderTemporary()+"score.csv");
  // }
  /**
  *{@summary Transform a GJoueur in Liste&lt;Joueur&gt;.}
  *@lastEditedVersion 1.38
  */
  public Liste<Joueur> toList(){
    CJoueur cc = this;
    Liste<Joueur> lc = new Liste<Joueur>();
    while(cc!= null){
      lc.add(cc.getContent());
      cc = cc.getSuivant();
    }
    return lc;
  }
}
