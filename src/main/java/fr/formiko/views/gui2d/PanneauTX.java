package fr.formiko.views.gui2d;

import fr.formiko.formiko.Touches;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public abstract class PanneauTX extends Panneau{
  protected int tailleBouton;
  protected PanneauBouton pb;
  protected int x;
  protected int y;
  protected String descTI;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauTX(){
    addKeyListener(new Touches());
  }
  // GET SET --------------------------------------------------------------------
  public int getXPi(){ return tailleBouton*x;}
  public int getYPi(){ return tailleBouton*y;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    this.setSize(tailleBouton*x,tailleBouton*y);
    debug.débogage("actualisation du PanneauTX avec pour taille : "+tailleBouton*x+" "+tailleBouton*y);
  }
}
