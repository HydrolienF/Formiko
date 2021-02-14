package fr.formiko.usuel.listes;

import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCreature;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.io.Serializable;

public class GInt implements Serializable{
  private CInt début, fin;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GInt(){}
  public GInt(Fourmiliere fere){
    this();
    GCreature gc = fere.getGc();
    if(gc.length()==0){return;}
    if(gc.getReine()!=null){ajouter(1);}else{ajouter(0);}
    ajouter(gc.getGcStade(0).length()-début.getContenu());
    for(int i=-1; i>-4;i--){
      ajouter(gc.getGcStade(i).length());
    }
    ajouter(fere.getNbrFourmisMorte());
  }
  // GET SET -----------------------------------------------------------------------
  public CInt getDébut(){ return début;}
  public CInt getFin(){ return fin;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public void ajouter(int s){ // On ajoute a la fin par défaut.
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
  public void ajouter(GInt gs){
    if(gs==null){ return;}
    if(this.getDébut()==null){début = gs.getDébut();return;}
    //on lie l'anciène fin au début de gs.
    this.fin.setSuivant(gs.getDébut());
    this.fin.getSuivant().setPrécédent(fin);
    // on change la fin actuelle.
    this.fin = gs.getFin();
  }
  public void add(int s){ ajouter(s);}
  public void add(GInt s){ ajouter(s);}
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
      erreur.alerte("le GInt est null, impossible de calculé le score du joueur.");return -1;
    }else if(length()!=6){
      erreur.alerte("le GInt ne contient pas le bon nombre de CInt, impossible de calculé le score du joueur.");return -1;
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
