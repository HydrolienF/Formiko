package fr.formiko.views.gui2d;

import fr.formiko.usual.debug;

import java.awt.Graphics;

public abstract class FPanelTX extends FPanel{
  protected int tailleBouton;
  protected FPanelBouton pb;
  protected int x;
  protected int y;
  protected String descTI;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelTX(){}
  // GET SET -------------------------------------------------------------------
  public int getXPi(){ return tailleBouton*x;}
  public int getYPi(){ return tailleBouton*y;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    this.setSize(tailleBouton*x,tailleBouton*y);
    debug.débogage("actualisation du FPanelTX avec pour taille : "+tailleBouton*x+" "+tailleBouton*y);
  }
}
