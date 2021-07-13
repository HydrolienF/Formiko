package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.listes.Liste;

import java.io.Serializable;
import java.util.Comparator;

public class GJoueur implements Serializable{
  private CJoueur début, fin;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GJoueur(){}
  // GET SET -----------------------------------------------------------------------
  public CJoueur getHead(){return début;}
  public CJoueur getTail(){return fin;}
  public GCreature getGc(){ // renvoie toutes les créatures de tout les joueurs.
    if (début == null){ return new GCreature();}
    return début.getGc();
  }
  public Joueur getJoueurParId(int id){
    if(début==null){ return null;}
    return début.getJoueurParId(id);
  }
  public GJoueur getJoueurHumain(){
    if(début==null){ return new GJoueur();}
    return début.getJoueurHumain();
  }
  public int getNbrDeJoueurHumain(){ return getJoueurHumain().length();}
  public int getNbrDIa(){ return length() - getJoueurHumain().length();}
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String s = g.get("gj")+" : ";
    if (début == null){ return s+g.get("vide");}
    return s+début.toString();
  }
  public int length(){
    if (début == null){ return 0;}
    return début.length(1);
  }
  public Joueur getJoueurNonIa(){
    // renvoie le 1a joueur non humain ou null si il n'y a que des ia.
    if(début == null){ return null;}
    if(début.getJoueur().getIa() == false){ return début.getJoueur();}
    return début.getJoueurNonIa();
  }
  public boolean getPlusDeFourmi(){
    if(début==null){return true;}
    return début.getPlusDeFourmi();
  }
  /**
  *{@summary Return a sorted GJoueur by score.}
  *@version 2.2
  */
  public GJoueur getGjSorted(){
    if(getHead()==null){return new GJoueur();}
    else{
      //TODO #82 just use addSorted when GJoueur will extends Liste<Joueur>
      Liste<Joueur> list = new Liste<Joueur>();
      Comparator<Joueur> scoreComparator = (Joueur p1, Joueur p2) -> (int)(p1.getScore() - p2.getScore());
      for (Joueur j : toList()) {
        list.addSorted(j, scoreComparator);
      }
      // return list;
      GJoueur gjr = new GJoueur();
      for (Joueur j : list) {
        gjr.add(j);
      }
      return gjr;
    }
  }
  public int getNbrDeJoueurVivant(){
    if (début == null){ return 0;}
    return début.getNbrDeJoueurVivant();
  }
  public boolean plusQu1Joueur(){
    if(getNbrDeJoueurVivant()<2){return true;}
    return false;
  }
  public void afficheScore(){
    if(début!=null){
      GString gs = scoreToGString();
      gs.afficheToi();
    }
  }
  public GString scoreToGString(){
    if (début == null){ return new GString();}
    return début.scoreToGString();
  }
  public void addDébut(Joueur j){
    CJoueur cj = new CJoueur(j);
    if (début == null){
      début = cj;
      fin = cj;
      return;
    }
    if (!début.equals(fin) ){
      debug.débogage("La chaine de joueur actuelle est constitué de " + length()+ " éléments");
      cj.setSuivant(début); // la liste actuelle est ratachée a la nouvelle CJoueur
      début = cj; // la CJoueur devient la 1a
    } else {
      cj.setSuivant(début);
      début = cj;
      actualiserFin();
    }
  }
  /**
  *{@summary Add a player to the GJoueur.}
  *@version 1.29
  */
  public void add(Joueur j){
    addTail(j);
  }
  public void addTail(Joueur j){
    CJoueur cj = new CJoueur(j);
    if(fin == null){
      début = cj;
      fin = cj;
    }else if (début.equals(fin)){
      fin = cj;
      début.setSuivant(fin);
    }else{
      fin.setSuivant(cj);
      fin = cj;
    }
  }
  //TODO #231 should place j depending of it score. (just before the 1a player that have a lower score.)
  public void addOrdonnée(Joueur j){
    if(début==null){add(j);}//le cas ou on a 0 éléments dans la liste.
    else if(début.equals(fin)){//le cas ou on a 1 seul élément dans la liste
      if(début.getContent().getScore()>=j.getScore()){
        addTail(j);
      }else{
        addDébut(j);
      }
    }else{
      if(début.getContent().getScore()>=j.getScore()){
        début.addOrdonnée(j);
      }else{
        addDébut(j);
      }
    }
    actualiserFin();
  }
  public void actualiserFin(){
    fin = début;
    while(fin.getSuivant()!=null){
      fin = fin.getSuivant();
    }
  }
  public void remove(Joueur j){
    if (début != null){
      if(début.getContent() == j){
        début = début.getSuivant();
      }else {
        début.remove(j);
      }
    }
    erreur.erreur("Le joueur "+j.getId()+" n'as pas pue être retiré");
  }
  public void jouer(){
    if(début == null){
      erreur.erreur("Impossible de faire jouer un groupe de joueur vide !");
    }else{
      if(Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()){return;}
      début.jouer();
    }
  }
  public void addMessage(Message m){
    if(début==null){return;}
    début.addMessage(m);
  }
  public void initialisationCaseNS(){
    if(début==null){return;}
    début.initialisationCaseNS();
  }
  public void enregistrerLesScores(){
    if(début==null){return;}
    début.enregistrerLesScores();
  }
  public void prendreEnCompteLaDifficulté(){
    if(début==null){return;}
    début.prendreEnCompteLaDifficulté();
  }
  public void setAction0AndEndTurn(){
    if(début==null){return;}
    début.setAction0AndEndTurn();
  }
  /**
  *{@summary Transform a GJoueur in Liste&lt;Joueur&gt;.}
  *@version 2.2
  */
  public Liste<Joueur> toList(){
    if (début==null){
      Liste<Joueur> lc = new Liste<Joueur>();
      return lc;
    }
    return début.toList();
  }
}
