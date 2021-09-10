package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.images.Img;
import fr.formiko.usuel.images.Pixel;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanneauZoom extends Panneau {
  private FButton bPlus; private FButton bMoins; private FButton bc;//bouton de zoom.
  private FButton bh; private FButton bb; private FButton bd; private FButton bg; //bouton de déplacements
  private FButton bd1; private FButton bd2;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauZoom(){
    int tailleBouton=Main.getbuttonSizeZoom();
    setSize(tailleBouton*3,tailleBouton*3);
  }
  public void build(){
    int tailleBouton=Main.getbuttonSizeZoom();
    this.setLayout(new GridBagLayout());
    Image tIB []; tailleBouton=Main.getbuttonSizeZoom();
    //if(Main.getPiFond()==null){tIB = chargerTIB();}
    //else{tIB = chargerTIB2(Main.getPiFond());}
    tIB = Main.getData().chargerTIBZoom();
    Dimension dim = new Dimension(tailleBouton, tailleBouton);
    bMoins = new FButton("-",(Panneau)this,0,tIB[0]);
    bh = new FButton("haut",(Panneau)this,7,tIB[1]);
    bPlus = new FButton("+",(Panneau)this,2,tIB[2]);
    bd = new FButton("droite",(Panneau)this,3,tIB[3]);
    bc = new FButton("centrer",(Panneau)this,4,tIB[4]);
    bg = new FButton("gauche",(Panneau)this,5,tIB[5]);
    bb = new FButton("bas",(Panneau)this,1,tIB[6]);
    bd1 = new FButton("centrer sur la fourmi",(Panneau)this,6,tIB[7]);
    bd2 = new FButton("dézoomer 2",(Panneau)this,8,tIB[8]);
    bPlus.setPreferredSize(dim); bMoins.setPreferredSize(dim); bb.setPreferredSize(dim); bh.setPreferredSize(dim); bd.setPreferredSize(dim); bg.setPreferredSize(dim);bc.setPreferredSize(dim); bd1.setPreferredSize(dim); bd2.setPreferredSize(dim);
    //L'objet servant à positionner les composants
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridy = 0;
    gbc.gridx = 0;
    add(bPlus,gbc);
    gbc.gridx = 1;
    add(bh,gbc);
    gbc.gridx = 2;
    add(bMoins,gbc);
    gbc.gridy = 1;
    gbc.gridx = 0;
    add(bg,gbc);
    gbc.gridx = 1;
    add(bc,gbc);
    gbc.gridx = 2;
    add(bd,gbc);
    gbc.gridy = 2;
    gbc.gridx = 0;
    add(bd1,gbc);
    gbc.gridx = 1;
    add(bb,gbc);
    gbc.gridx = 2;
    add(bd2,gbc);
    // Color col = new Color(0,0,0,0);
    bPlus.setColor(-1);
    bMoins.setColor(-1);
    bh.setColor(-1);
    bb.setColor(-1);
    bd.setColor(-1);
    bg.setColor(-1);
    bc.setColor(-1);
    bd1.setColor(-1);
    bd2.setColor(-1);
    // bPlus.setBordure(false);
    // bMoins.setBordure(false);
    // bh.setBordure(false);
    // bb.setBordure(false);
    // bd.setBordure(false);
    // bg.setBordure(false);
    // bc.setBordure(false);
    // bd1.setBordure(false);
    // bd2.setBordure(false);
    setEnabled(true);
  }
  // GET SET -------------------------------------------------------------------
  public int getTailleBouton(){ return Main.getbuttonSizeZoom();}
  //public void setTailleBouton(int x){ tailleBouton=x;}
  public void setEnabled(boolean boo){
    bPlus.setEnabled(boo);
    bMoins.setEnabled(boo);
    bh.setEnabled(boo);
    bb.setEnabled(boo);
    bd.setEnabled(boo);
    bg.setEnabled(boo);
    bc.setEnabled(boo);
    bd1.setEnabled(boo);bd2.setEnabled(boo);
    super.setEnabled(boo);
  }
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {
      return;
    }
    int tailleBouton=Main.getbuttonSizeZoom();
    debug.débogage("taille du panneau de zoom : x="+tailleBouton*3+", y="+tailleBouton*3);
    this.setSize(tailleBouton*3,tailleBouton*3);
  }
  public void doAction(byte ac){
    Panneau.getView().getPj().doAction(ac);
  }public void doAction(int ac){ doAction((byte)ac);}
}
