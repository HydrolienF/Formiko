package fr.formiko.usuel.listes;

import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCreature;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class GInt implements Serializable{
  private CInt début, fin;
  // CONSTRUCTORS ----------------------------------------------------------------
  public GInt(){}
  public GInt(Fourmiliere fere){
    this();
    GCreature gc = fere.getGc();
    if(gc.length()==0){return;}
    if(gc.getReine()!=null){add(1);}else{add(0);}
    add(gc.getGcStade(0).length()-début.getContent());
    for(int i=-1; i>-4;i--){
      add(gc.getGcStade(i).length());
    }
    add(fere.getNbrFourmisMorte());
  }
  // GET SET ----------------------------------------------------------------------
  public CInt getHead(){ return début;}
  public CInt getTail(){ return fin;}
  // FUNCTIONS -----------------------------------------------------------------
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public void add(int s){ // On ajoute a la fin par défaut.
    CInt c = new CInt(s);
    if (fin == null){ // si la liste est complètement vide.
      fin = c;
      début = c;
    } else {
      fin.setSuivant(c);
      c.setPrécédent(fin);
      fin = c;
    }
  }
  public void add(GInt gs){
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
      System.out.println(g.get("GInt",1,"Le GInt est vide"));
    }else{
      début.afficheTout();
      System.out.println();
    }
  }
  public int calculerScore(Fourmiliere fere){
    if(début==null){
      erreur.alerte("le GInt est null, impossible de calculer le score du joueur.");return -1;
    }else if(length()!=6){
      erreur.alerte("le GInt ne contient pas le bon nombre de CInt, impossible de calculer le score du joueur.");return -1;
    }else{
      return début.calculerScore(fere);
    }
  }
  public String toString(){
    if(début==null){return "";}
    else{
      return début.toString()+"\n";
    }
  }
  /**
  *return the ca element of the GInt. (or 0 if not found.)
  */
  public int getCase(int ca){
    if(ca<0){return -1;}
    if(début==null){return 0;}
    else{return début.getCase(ca);}
  }
}
