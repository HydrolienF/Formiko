package fr.formiko.views.gui2d;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.listes.GString;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;

public class PanneauInfo extends Panneau {
  private int nbrDeDesc;
  private Desc desc [];
  private int xPi; private int yPi;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauInfo(GString gs, int xD){
    debug.débogage("création d'un panneauInfo avec "+gs.length()+" éléments.");
    this.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    nbrDeDesc=gs.length();
    int yD = Desc.getDimY();
    xPi=xD; yPi=yD*nbrDeDesc;
    this.setSize(xPi,yPi);
    debug.débogage(getSize()+"");
    gbc.gridx = 0; int k=0;
    for (String s : gs ) {
      gbc.gridy = k;k++;
      Desc desc = new Desc(xD,yD);
      desc.setTexte(s);
      this.add(desc,gbc);
    }
  }
  public PanneauInfo(GString gs){
    this(gs, Main.getPz().getTailleBouton()*5);
  }
  public PanneauInfo(Fourmi f,int xD){
    this(f.descriptionGString(),xD);
  }
  public PanneauInfo(Fourmi f){
    this(f.descriptionGString());
  }
  public PanneauInfo(){}
  // GET SET -------- ------------------------------------------------------------
  public int length(){ return nbrDeDesc;}
  public int getXPi(){ return xPi;}
  public int getYPi(){ return yPi;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    debug.débogage("actualisation du PanneauInfo avec pour taille : "+this.getWidth()+" "+this.getHeight());
  }
}
