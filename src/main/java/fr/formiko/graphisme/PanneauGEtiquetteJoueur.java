package fr.formiko.graphisme;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.g;

import java.awt.Graphics;

public class PanneauGEtiquetteJoueur extends Panneau{
  private GEtiquetteJoueur gej;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauGEtiquetteJoueur(GEtiquetteJoueur gej){
    this.gej=gej;
    this.setLayout(null);
    //int nbrDeJoueur = gej.length();
    CEtiquetteJoueur cej = gej.getDébut();
    int wi2 = Main.getF().getWidth()/2;
    int k=0;
    while(cej!=null){
      cej.getContenu().setBounds(0,k*Desc.getDimY()*3,wi2*gej.length(),Desc.getDimY()*3);
      add(cej.getContenu());
      cej=cej.getSuivant();k++;
    }
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    /*int wi2 = Main.getF().getWidth()/2;
    CEtiquetteJoueur cej = gej.getDébut();
    int k=0;
    while(cej!=null){
      cej.getContenu().setBounds(0,k*Desc.getDimY()*3,wi2*gej.length(),Desc.getDimY()*3);
      cej=cej.getSuivant();k++;
    }*/
  }
}
