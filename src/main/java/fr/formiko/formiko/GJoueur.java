package fr.formiko.formiko;

import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.structures.listes.Liste;

import java.io.Serializable;
import java.util.Comparator;

/**
*{@summary Return all the Creatures of all the players.}
*@lastEditedVersion 2.23
*/
public class GJoueur extends Liste<Joueur> implements Serializable {

  // CONSTRUCTORS --------------------------------------------------------------
  public GJoueur(){
    super();
  }
  public GJoueur(Liste<Joueur> list){
    this();
    for (Joueur c : list) {
      add(c);
    }
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Return all the Creatures of all the players.}
  *@lastEditedVersion 2.23
  */
  public GCreature getGc(){
    GCreature gcGlobal = new GCreature();
    for (Joueur j : this) {
      // gcGlobal.addList(j.getFere().getGc());
      for (Creature t : j.getFere().getGc()) {
        gcGlobal.add(t);
      }
    }
    return gcGlobal;
  }
  public Joueur getJoueurParId(int id){
    for (Joueur j : this) {
      if(j.getId()==id){
        return j;
      }
    }
    return null;
  }
  public GJoueur getJoueurHumain(){
    //TODO we should avoid using new when we can (same for GCreature)
    return new GJoueur(filter(j -> !j.isAI()));
  }
  public int getNbrDeJoueurHumain(){ return getJoueurHumain().length();}
  public int getNbrDIa(){ return length() - getJoueurHumain().length();}
  // FUNCTIONS -----------------------------------------------------------------
  // public String toString(){
  //   String s = g.get("gj")+" : ";
  //   if (début == null){ return s+g.get("vide");}
  //   return s+début.toString();
  // }
  /**
  *{@summary Return a sorted GJoueur by score.}
  *@lastEditedVersion 2.2
  */
  public GJoueur getGjSorted(){
    if(isEmpty()){return new GJoueur();}
    GJoueur gj = new GJoueur();
    Comparator<Joueur> scoreComparator = (Joueur p1, Joueur p2) -> (int)(p1.getScore() - p2.getScore());
    for (Joueur j : this) {
      gj.addSorted(j, scoreComparator);
    }
    return gj;
  }
  /**
  *{@summary Return the number of player still alive.}
  *An alive player still have at lease 1 Ant.
  *@lastEditedVersion 2.23
  */
  public int getNbrDeJoueurVivant(){
    return filter(j -> j.getFere().getGc().length()!=0).length();
  }
  public boolean plusQu1Joueur(){
    if(getNbrDeJoueurVivant()<2){return true;}
    return false;
  }
  public GString scoreToGString(){
    GString gsr = new GString();
    for (Joueur j : this) {
      gsr.add(j.scoreToString());
    }
    return gsr;
  }
  /**
  *{@summary Play for every players.}
  *@lastEditedVersion 2.23
  */
  public void jouer(){
    if(Main.getPartie()!=null && !Main.getPartie().getContinuerLeJeu()){return;}
    if(isEmpty()){
      erreur.erreur("Unable to play for an empty player list");
    }
    for (Joueur j : this) {
      j.jouer();
    }
  }
  /**
  *{@summary Add a message for every players.}
  *@lastEditedVersion 2.23
  */
  public void addMessage(Message m){
    for (Joueur j : this) {
      j.addMessage(m);
    }
  }
  /**
  *{@summary Initialize dark &#38; cloudy squares for every players.}
  *@lastEditedVersion 2.23
  */
  public void initialisationCaseNS(){
    for (Joueur j : this) {
      j.initialisationCaseNS();
    }
  }
  /**
  *{@summary Initialize difficulty initial concequence for every players.}
  *@lastEditedVersion 2.23
  */
  public void prendreEnCompteLaDifficulté(){
    for (Joueur j : this) {
      j.prendreEnCompteLaDifficulté();
    }
  }
  /**
  *{@summary end turn of every players.}
  *@lastEditedVersion 2.23
  */
  public void setAction0AndEndTurn(){
    for (Joueur j : this) {
      j.setAction0AndEndTurn();
    }
  }
}
