package fr.formiko.views.gui2d;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

public class GEtiquetteJoueur {
  private CEtiquetteJoueur début;
  private CEtiquetteJoueur fin;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public GEtiquetteJoueur(int x){
    String pseudo = Main.getOp().getPseudo();
    if(pseudo.equals("") || pseudo.equals("-1")){pseudo = g.getM("joueur")+" 1";}
    this.add(new EtiquetteJoueur(pseudo,false));
    for (int i=0;i<x ;i++) {
      this.add(new EtiquetteJoueur(true));
    }
    this.add(new EtiquetteJoueur());
  }
  public GEtiquetteJoueur(){}
  // GET SET --------------------------------------------------------------------
  public CEtiquetteJoueur gethead(){ return début;}
  public CEtiquetteJoueur getTail(){ return fin;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(début==null){ return 0;}
    return début.length();
  }
  public void afficheToi(){
    if(début==null){System.out.println("le GEtiquetteJoueur est vide");return;}
    System.out.println("GEtiquetteJoueur de "+length()+" éléments.");
    début.afficheToi();
  }
  public GJoueur getGJoueur(Carte mapo){
    if (début==null){ return new GJoueur();}
    this.retirer(fin.getContent()); // on retir la fin qui est fermé.
    return début.getGJoueur(mapo);
  }
  public void add(EtiquetteJoueur ej){
    if(début==null){ début = new CEtiquetteJoueur(ej); fin = début;}
    else{
      fin.setSuivant(new CEtiquetteJoueur(ej));
    }
    actualiserFin();
  }
  public void retirer(int idX){
    if(début==null){erreur.erreurGXVide("GEtiquetteJoueur");return;}
    if(début.getContent().getId()==idX){
      début = début.getSuivant();
    }else{
      début.retirer(idX);
    }
    actualiserFin();
  }public void retirer(EtiquetteJoueur ej){retirer(ej.getId());}
  public void actualiserFin(){
    //remettre la fin a la fin.
    CEtiquetteJoueur cej = début;
    if(cej==null){fin=null;return;}
    while(cej.getSuivant()!=null){
      cej=cej.getSuivant();
    }
    fin = cej;
  }
}
