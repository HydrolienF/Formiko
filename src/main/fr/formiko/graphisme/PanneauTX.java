package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g;
//def par défaut des fichiers depuis 0.41.2
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import java.awt.Graphics;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Touches;

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
    super.paintComponent(g);
    this.setSize(tailleBouton*x,tailleBouton*y);
    debug.débogage("actualisation du PanneauTX avec pour taille : "+tailleBouton*x+" "+tailleBouton*y);
  }
}
