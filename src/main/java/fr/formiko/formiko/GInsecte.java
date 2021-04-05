package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.tableau;

import java.io.Serializable;

public class GInsecte implements Serializable{
  protected CInsecte début;
  // CONSTRUCTEUR -----------------------------------------------------------------
  public GInsecte(){}
  public GInsecte(int x){
    this();
    if (x<1){ return; }
    début = new CInsecte(new Insecte());
    for (int i=0;i<x-1 ;i++ ) {
      this.addInsecte(new Insecte());
    }
    debug.débogage("fin de création des x insectes");
  }
  // GET SET -----------------------------------------------------------------------
  public CInsecte getDébut(){return début;}
  public CInsecte getFin(){
    if (début == null ){ return null;}
    CInsecte fin = début;
    while (fin.getSuivant() != null){
      fin = fin.getSuivant();
    }
    return fin;
  }
  // Fonctions propre -----------------------------------------------------------
  public String toString(){
    String s = g.get("gj")+" : ";
    if (début == null){ return s+g.get("vide");}
    return s+début.toString();
  }
  public int length(){
    if(début==null){
      return 0;
    }else {
      return début.length();
    }
  }
  public Insecte getInsectePlusDeNourritureFournie(){
    debug.débogage("Choix de l'insecte qui peut fournir le plus de nourriture");
    CInsecte ci = this.getDébut();
    if (ci == null){ return null;} // si la liste est vide.
    if (ci.getSuivant() == null){ return ci.getInsecte();} // si il y a 1 seul éléments.
    return ci.getInsectePlusDeNourritureFournie(ci.getInsecte());
  }
  public void addInsecte(){
    addInsecte(1);
  }
  public void addInsecte(int x){
    if (x<1){ return ;}
    CInsecte ci = new CInsecte();
    ci.setSuivant(début);
    début = ci;
    debug.débogage((x-1) + " Insectes reste a add");
    addInsecte(x-1);
  }
  public void addInsecteM(int x){
    if (x<1){ return ;}
    CInsecte ci = new CInsecte();
    ci.getInsecte().setEstMort(true);
    ci.setSuivant(début);
    début = ci;
    debug.débogage((x-1) + " Insectes reste a add");
    addInsecte(x-1);
  }
  public void add(Insecte i){
    addInsecte(i);
  }
  public void addInsecte(Insecte i){
    if (i != null){
      CInsecte ci = new CInsecte(i);
      ci.setSuivant(début);
      début = ci;
    }
  }
  public void addGi(GInsecte giTemp){
    if (this.début == null){
      this.début = giTemp.getDébut();
    }else{
      this.getFin().setSuivant(giTemp.getDébut()); // On raccroche les 2 bouts.
    }
  }
  public void tourInsecte(){
    if (début != null){
      début.tourInsecte();
    }
  }
  public void preTour(){
    if (début != null){
      début.preTour();
    }
  }
  public GInsecte getInsecteSurLaCase(Point pTest){
    GInsecte gir = new GInsecte();
    if (début == null){ return null;}
    CInsecte ciTest = début;
    while (ciTest.getSuivant() != null){
      gir.addInsecte(ciTest.getInsecteSurLaCase(pTest));
      ciTest = ciTest.getSuivant();
    }
    return gir;
  }
  public GInsecte getInsecteSurLaCase(Point p1, Point p2){
    GInsecte gir = new GInsecte();
    // Fonction qui renvoie tout les insectes dans le réctangle contenant les points p1 et p2.
    int longueur = 1 + math.valAbs(p1.getX() - p2.getX());
    int largeur = 1 + math.valAbs(p1.getY() - p2.getY());
    debug.débogage("Le rectangle contient " + longueur*largeur + " Points.");
    Point tp [] = new Point [longueur*largeur];
    for (int i=0;i<longueur ;i++ ) {
      for (int j=0;j<largeur ;j++ ) {
        tp[i*largeur + j] = new Point (p1.getX() + i,p1.getY() + j);
      }
    }// là on a la liste de tt les points a testé.
    int lentp = tp.length;
    for (int i=0;i<lentp ;i++ ) {
      gir.addGi(getInsecteSurLaCase(tp[i])); // on ajoute case par case les insectes des cases.
    }
    return gir;
  }
  public String [] getInsecteSurLaCaseStr(Point pTest){
    GInsecte gi = getInsecteSurLaCase(pTest);
    return gi.gInsecteToString();
  }
  public String [] gInsecteToString(){
    String [] tr = new String [0];
    if(début == null){ return tr;}
    CInsecte ci = début;
    while (ci != null){
      tr = tableau.addX (tr, "I" + ci.getContenu().getId());
      ci = ci.getSuivant();
    }
    return tr;
  }
  // Tuer un insecte :
  public void retirerInsecte(Insecte i){
    if (i == null){
      erreur.alerte("Impossiblede retirer null d'un groupe d'insecte");
    }
    if (début == null){
      erreur.alerte("Impossible de retirer un insecte d'un groupe vide.");
    }else if (début.getInsecte().equals(i)){ // Si c'est le 1a
      début = début.getSuivant(); // On en retir 1.
    } else if(début.getSuivant() != null && début.getSuivant().getInsecte().equals(i)){ // Si c'est le 2a
      début.setSuivant(début.getSuivant().getSuivant());
    }else {
      début.retirerInsecte(i);
    }
  }
  public void retirer(Insecte i){ retirerInsecte(i);}
  public void retirer(Creature i){
    if(i instanceof Insecte){retirerInsecte((Insecte)i);}
    else{erreur.erreur("Impossible de retirer la créature "+i.getId()+" car ne n'est pas un insecte.","GInsecte.retirer");}
  }
  public GInsecte getGiVivant(){
    if (début == null){ erreur.erreurGXVide();return new GInsecte();}
    return début.getGiVivant();
  }
  public GCreature toGCreature(){
    if(début==null){return new GCreature();}
    return début.toGCreature();
  }
}
