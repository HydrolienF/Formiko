package fr.formiko.views.gui2d;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.GJoueur;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

public class GEtiquetteJoueur {
  private CEtiquetteJoueur head;
  private CEtiquetteJoueur tail;
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
  public CEtiquetteJoueur getHead(){ return head;}
  public CEtiquetteJoueur getTail(){ return tail;}
  // Fonctions propre -----------------------------------------------------------
  public int length(){
    if(head==null){ return 0;}
    return head.length();
  }
  public void afficheToi(){
    if(head==null){System.out.println("le GEtiquetteJoueur est vide");return;}
    System.out.println("GEtiquetteJoueur de "+length()+" éléments.");
    head.afficheToi();
  }
  public GJoueur getGJoueur(Carte mapo){
    if (head==null){ return new GJoueur();}
    this.remove(tail.getContent()); // on retir la tail qui est fermé.
    return head.getGJoueur(mapo);
  }
  public void add(EtiquetteJoueur ej){
    if(head==null){ head = new CEtiquetteJoueur(ej); tail = head;}
    else{
      tail.setSuivant(new CEtiquetteJoueur(ej));
    }
    actualiserFin();
  }
  public void remove(int idX){
    if(head==null){erreur.erreurGXVide("GEtiquetteJoueur");return;}
    if(head.getContent().getId()==idX){
      head = head.getSuivant();
    }else{
      head.remove(idX);
    }
    actualiserFin();
  }public void remove(EtiquetteJoueur ej){remove(ej.getId());}
  public void actualiserFin(){
    //remettre la tail a la tail.
    CEtiquetteJoueur cej = head;
    if(cej==null){tail=null;return;}
    while(cej.getSuivant()!=null){
      cej=cej.getSuivant();
    }
    tail = cej;
  }
}
