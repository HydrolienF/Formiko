package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.Message;
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.ecrireUnFichier;
import java.io.Serializable;

public class CJoueur implements Serializable{
  private Joueur contenu;
  private CJoueur suivant;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public CJoueur(Joueur j){
    contenu = j;
  }
  // GET SET -----------------------------------------------------------------------
  public void setSuivant(CJoueur c){suivant = c;}
  public CJoueur getSuivant(){return suivant;}
  public Joueur getJoueur(){return getContenu();}
  public Joueur getContenu(){return contenu;}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    if(suivant==null){return contenu.toString();}
    return contenu.toString() +"\n"+ suivant.toString();
  }
  public int length(int x){
    if (suivant != null) { x++; return suivant.length(x);}
    return x;
  }
  public GCreature getGc(){
    CJoueur act = this;
    GCreature gcr = new GCreature();
    gcr.ajouter(this.getContenu().getFere().getGc().copier());
    while(act.getSuivant() != null){
      GCreature gcr2 = act.getSuivant().getContenu().getFere().getGc().copier();
      gcr.ajouter(gcr2);
      act=act.getSuivant();
    }
    return gcr;
  }
  public Joueur getJoueurParId(int id){
    if(this.getContenu().getId()==id){ return this.getContenu();}
    if(this.getSuivant()==null){return null;}
    return suivant.getJoueurParId(id);
  }
  public GJoueur getJoueurIa(boolean b){
    GJoueur gjr = new GJoueur();
    CJoueur cj = this;
    while(cj!= null){
      if(b){
        if(cj.getContenu().getIa()){ gjr.ajouter(cj.getContenu());}
      }else{
        if(!cj.getContenu().getIa()){ gjr.ajouter(cj.getContenu());}
      }
      cj = cj.getSuivant();
    }
    return gjr;
  }
  public Joueur getJoueurNonIa(){
    if(getSuivant() == null){ return null;} // On a rien trouvé
    if(getSuivant().getJoueur().getIa() == false){ return getSuivant().getJoueur();} // on a trouvé
    return getSuivant().getJoueurNonIa(); // on continue les recherches.
  }
  public boolean getPlusDeFourmi(){
    if(getContenu().getFere().getGc().length()>0){ return false;} //si on a trouvé une fourmi
    else if(suivant!=null){ return suivant.getPlusDeFourmi();}// sinon on test le joueur suivant.
    return true; //si tout les joueurs on été parcouru
  }
  public GJoueur getGjOrdonné(){
    GJoueur gjr = new GJoueur();
    CJoueur cj = this;
    while(cj!=null){
      Joueur j = cj.getContenu();
      gjr.addOrdonnée(j);
      cj=cj.getSuivant();
    }
    return gjr;
  }
  public int getNbrDeJoueurVivant(){
    int x = 0; if(contenu.getFere().getGc().length()!=0){x=1;}
    if (suivant!=null){return x+suivant.getNbrDeJoueurVivant();}
    else{ return x;}
  }
  public void afficheToiParciel(){
    String m = "";
    if (suivant != null ){ m = " "+g.get("CJoueur.2")+" "+ this.getSuivant().getJoueur().getId();}
    System.out.println(g.get("CJoueur.1")+" " + this.contenu.getId() + m);
  }
  public void afficheTout(){
    System.out.println(this);
    if (suivant != null){
      suivant.afficheTout();
    }
  }
  public GString scoreToGString(){
    GString gsr = new GString();
    CJoueur cj = this;
    while(cj!=null){
      gsr.add(cj.getContenu().scoreToString());
      cj=cj.getSuivant();
    }
    return gsr;
  }
  public void addOrdonnée(Joueur j){
    CJoueur cj = this;
    while(cj!=null && cj.getSuivant()!=null){
      if(cj.getSuivant().getContenu().getScore()>j.getScore()){//si on a pas atteint la place voulue.
        cj=cj.getSuivant();
      }else{//placé après le 1a joueur qui a un plus mauvais score.
        CJoueur temp = cj.getSuivant();//l'ancien maillon suivant.
        cj.setSuivant(new CJoueur(j));
        cj.getSuivant().setSuivant(temp);
        return;
      }
      if(cj.getSuivant()==null){ // placé en dernière position
        cj.setSuivant(new CJoueur(j));
      }
    }
  }
  public void retirer(Joueur j){
    if (suivant == null){
      erreur.erreur("Le joueur "+j.getId()+" n'as pas pue être retiré");
    } else if( suivant.getContenu()==j){
      suivant = suivant.getSuivant();
    }else{
      suivant.retirer(j);
    }
  }
  public void jouer(){
    debug.débogage("Lancement du tour du joueurs "+contenu.getId());
    contenu.jouer();
    if(suivant != null){
      suivant.jouer();
    }
  }
  public void addMessage(Message m){
    CJoueur cj = this;
    while(cj!=null){
      debug.débogage("Ajout d'1 message");
      cj.getContenu().addMessage(m);
      cj=cj.getSuivant();
    }
  }
  public void initialisationCaseNS(){
    CJoueur cj = this;
    while(cj!=null){
      cj.getContenu().initialisationCaseNS();
      cj=cj.getSuivant();
    }
  }
  public void enregistrerLesScores(){
    GString gs = new GString();
    CJoueur cj = this;
    gs.add(cj.getContenu().getFere().enregistrerLesScores());
    cj=cj.getSuivant();
    //
    while(cj!=null){
      gs.add(cj.getContenu().getFere().enregistrerLesScores());
      cj=cj.getSuivant();
    }
    gs = gs.transformerScore();
    ecrireUnFichier.ecrireUnFichier(gs,"data/score.csv");
  }
  public void prendreEnCompteLaDifficulté(){
    CJoueur cj = this;
    while(cj!=null){
      cj.getContenu().prendreEnCompteLaDifficulté();
      cj = cj.getSuivant();
    }
  }
  public void setAction0(){
    CJoueur cj = this;
    while(cj!=null){
      cj.getContenu().setAction0();
      cj = cj.getSuivant();
    }
  }
}
