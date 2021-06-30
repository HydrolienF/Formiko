package fr.formiko.usuel.listes;

import fr.formiko.formiko.Message;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class GMessage implements Serializable{
  private CMessage début, fin;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public GMessage(){}
  // GET SET --------------------------------------------------------------------
  public CMessage getHead(){ return début;}
  public CMessage getTail(){ return fin;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public void add(Message s){ // On ajoute a la fin par défaut.
    CMessage c = new CMessage(s);
    if (fin == null){ // si la liste est complètement vide.
      fin = c;
      début = c;
    } else {
      fin.setSuivant(c);
      c.setPrécédent(fin);
      fin = c;
    }
  }
  public void add(GMessage gs){
    if(gs==null){ return;}
    if(this.getHead()==null){début = gs.getHead();return;}
    //on lie l'anciène fin au début de gs.
    this.fin.setSuivant(gs.getHead());
    this.fin.getSuivant().setPrécédent(fin);
    // on change la fin actuelle.
    this.fin = gs.getTail();
  }
  public void afficheToi(){
    if(début==null){
      System.out.println(g.get("GMessage",1,"Le GMessage est vide"));
    }else{
      début.afficheTout();
    }
  }
  public boolean contient(Message s){
    if (début==null){ return false;}
    return début.contient(s);
  }
  public GString gmToGs(int x){
    if (début==null){ return new GString();}
    return fin.gmToGs(x);
  }
}
