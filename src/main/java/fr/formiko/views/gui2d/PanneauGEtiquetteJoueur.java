package fr.formiko.views.gui2d;

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
    CEtiquetteJoueur cej = gej.gethead();
    int wi2 = Main.getF().getWidth()/2;
    int k=0;
    while(cej!=null){
      cej.getContent().setBounds(Desc.getDimY(),k*Desc.getDimY()*3,wi2*gej.length(),Desc.getDimY()*3);
      add(cej.getContent());
      cej=cej.getSuivant();k++;
    }
  }
  // GET SET --------------------------------------------------------------------

  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    if(gej!=null && gej.gethead()!=null && gej.gethead().getContent()!=null){
      g.setColor(Main.getData().getButtonColor());
      int taille = gej.gethead().getContent().getHeight();
      int size = gej.length();
      g.fillRect(0,0,getWidth(),taille*size);
    }
    /*int wi2 = Main.getF().getWidth()/2;
    CEtiquetteJoueur cej = gej.gethead();
    int k=0;
    while(cej!=null){
      cej.getContent().setBounds(0,k*Desc.getDimY()*3,wi2*gej.length(),Desc.getDimY()*3);
      cej=cej.getSuivant();k++;
    }*/
  }
}
