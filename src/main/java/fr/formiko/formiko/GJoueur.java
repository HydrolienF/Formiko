package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Message;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;
import java.util.Comparator;

public class GJoueur implements Serializable{
  private CJoueur début, fin;
  // CONSTRUCTORS ----------------------------------------------------------------
  public GJoueur(){}
  // GET SET ----------------------------------------------------------------------
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
  // FUNCTIONS -----------------------------------------------------------------
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
  *@lastEditedVersion 2.2
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
  /**
  *{@summary Return the number of player still alive.}
  *An alive player still have at lease 1 Ant.
  *@lastEditedVersion 2.23
  */
  public int getNbrDeJoueurVivant(){
    return toList().filter(j -> j.getFere().getGc().length()!=0).length();
  }
  public boolean plusQu1Joueur(){
    if(getNbrDeJoueurVivant()<2){return true;}
    return false;
  }
  public GString scoreToGString(){
    GString gsr = new GString();
    for (Joueur j : toList()) {
      gsr.add(j.scoreToString());
    }
    return gsr;
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
  *@lastEditedVersion 1.29
  */
  public void add(Joueur j){
    addFin(j);
  }
  public void addFin(Joueur j){
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
  /**
  *{@summary Play for every players.}
  *@lastEditedVersion 2.23
  */
  public void jouer(){
    if(Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()){return;}
    if(toList().isEmpty()){
      erreur.erreur("Unable to play for an empty player list");
    }
    for (Joueur j : toList()) {
      j.jouer();
    }
  }
  /**
  *{@summary Add a message for every players.}
  *@lastEditedVersion 2.23
  */
  public void addMessage(Message m){
    for (Joueur j : toList()) {
      j.addMessage(m);
    }
  }
  /**
  *{@summary Initialize dark &#38; cloudy squares for every players.}
  *@lastEditedVersion 2.23
  */
  public void initialisationCaseNS(){
    for (Joueur j : toList()) {
      j.initialisationCaseNS();
    }
  }
  // public void enregistrerLesScores(){
  //   if(début==null){return;}
  //   début.enregistrerLesScores();
  // }
  /**
  *{@summary Initialize difficulty initial concequence for every players.}
  *@lastEditedVersion 2.23
  */
  public void prendreEnCompteLaDifficulté(){
    for (Joueur j : toList()) {
      j.prendreEnCompteLaDifficulté();
    }
  }
  /**
  *{@summary end turn of every players.}
  *@lastEditedVersion 2.23
  */
  public void setAction0AndEndTurn(){
    for (Joueur j : toList()) {
      j.setAction0AndEndTurn();
    }
  }
  /**
  *{@summary Transform a GJoueur in Liste&lt;Joueur&gt;.}
  *@lastEditedVersion 2.2
  */
  public Liste<Joueur> toList(){
    if (début==null){
      Liste<Joueur> lc = new Liste<Joueur>();
      return lc;
    }
    return début.toList();
  }
}
