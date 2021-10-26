package fr.formiko.views.gui2d;

import fr.formiko.usuel.debug;

import java.awt.Graphics;

public abstract class PanneauTX extends Panneau{
  protected int tailleBouton;
  protected PanneauBouton pb;
  protected int x;
  protected int y;
  protected String descTI;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauTX(){}
  // GET SET -------------------------------------------------------------------
  public int getXPi(){ return tailleBouton*x;}
  public int getYPi(){ return tailleBouton*y;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    this.setSize(tailleBouton*x,tailleBouton*y);
    debug.débogage("actualisation du PanneauTX avec pour taille : "+tailleBouton*x+" "+tailleBouton*y);
  }
}
