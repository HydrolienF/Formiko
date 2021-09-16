package fr.formiko.usuel.structures.listes;

import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCreature;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class GGInt implements Serializable{
  private CCInt début, fin;
  // CONSTRUCTORS ----------------------------------------------------------------
  public GGInt(){}
  // GET SET ----------------------------------------------------------------------
  public CCInt getHead(){ return début;}
  public CCInt getTail(){ return fin;}
  // FUNCTIONS -----------------------------------------------------------------
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public void add(GInt contenu){ // On ajoute a la fin par défaut.
    CCInt c = new CCInt(contenu);
    if (fin == null){ // si la liste est complètement vide.
      fin = c;
      début = c;
    } else {
      fin.setSuivant(c);
      c.setPrécédent(fin);
      fin = c;
    }
  }
  public void afficheToi(){
    if(début==null){
      System.out.println(g.get("GGInt",1,"Le GGInt est vide"));
    }else{
      début.afficheTout();
    }
  }
  public String toString(){
    if(début==null){return "";}
    else{return début.toString();}
  }
  public int sommeCase(int ca){
    if(ca<0){return -1;}
    if(début==null){return 0;}
    else{return début.sommeCase(ca);}
  }
}
