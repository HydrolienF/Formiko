package fr.formiko.graphisme;
import fr.formiko.graphisme.*;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import fr.formiko.usuel.image.image;
import fr.formiko.usuel.math.math;
import javax.swing.ImageIcon;
import fr.formiko.usuel.image.Img;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.image.Pixel;
import fr.formiko.formiko.Main;

public class PanneauZoom extends Panneau {
  private int tailleBouton;
  private Bouton bPlus; private Bouton bMoins; private Bouton bc;//bouton de zoom.
  private Bouton bh; private Bouton bb; private Bouton bd; private Bouton bg; //bouton de déplacements
  private Bouton bd1; private Bouton bd2;
  private boolean initialisationFX;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauZoom(){
    tailleBouton=Main.getTailleBoutonZoom();
    setSize(tailleBouton*3,tailleBouton*3);
  }
  public void construire(){
    initialisationFX=false;
    this.setLayout(new GridBagLayout());
    Image tIB []; tailleBouton=Main.getTailleBoutonZoom();
    if(Main.getPiFond()==null){tIB = chargerTIB();}
    else{tIB = chargerTIB2(Main.getPiFond());}
    Dimension dim = new Dimension(tailleBouton, tailleBouton);
    bMoins = new Bouton("-",(Panneau)this,0,tIB[0]);
    bh = new Bouton("haut",(Panneau)this,1,tIB[1]);
    bPlus = new Bouton("+",(Panneau)this,2,tIB[2]);
    bd = new Bouton("droite",(Panneau)this,5,tIB[3]);
    bc = new Bouton("centrer",(Panneau)this,4,tIB[4]);
    bg = new Bouton("gauche",(Panneau)this,3,tIB[5]);
    bb = new Bouton("bas",(Panneau)this,7,tIB[6]);
    bd1 = new Bouton("centrer sur la fourmi",(Panneau)this,6,tIB[7]);
    bd2 = new Bouton("dézoomer 2",(Panneau)this,8,tIB[8]);
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
  }
  // GET SET --------------------------------------------------------------------
  public int getTailleBouton(){ return tailleBouton;}
  public void setTailleBouton(int x){ tailleBouton=x;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {
      return;
    }
    debug.débogage("taille du panneau de zoom : x="+tailleBouton*3+", y="+tailleBouton*3);
    this.setSize(tailleBouton*3,tailleBouton*3);
  }
  public Image [] chargerTIB(){
    Image [] tIB = new Image[9];
    if(!initialisationFX && !Main.getGarderLesGraphismesTourné()){tournerLesFleches();}
    tIB[0] = image.getImage("moins").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[1] = image.getImage("fleche").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[2] = image.getImage("plus").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[3] = image.getImage("fleche1").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +90°
    tIB[4] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[5] = image.getImage("fleche2").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner -90°
    tIB[6] = image.getImage("fleche3").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +180°
    tIB[7] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[8] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    return tIB;
  }
  public Image [] chargerTIB2(Pixel pi){
    Image [] tIB = new Image[9];
    if(!initialisationFX){
      String ts [] = {"moins.png","fleche.png","plus.png","centrer.png"};
      for (String nom : ts ) {
        Img img = new Img(nom);
        img.changerPixelTransparent(pi);
        img.sauvegarder(nom);
      }
      if(!Main.getGarderLesGraphismesTourné()){tournerLesFleches("fleche");}
      initialisationFX=true;
    }
    tIB[0] = image.getImage("moins").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[1] = image.getImage("fleche").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[2] = image.getImage("plus").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[3] = image.getImage("fleche1").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +90°
    tIB[4] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[5] = image.getImage("fleche2").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner -90°
    tIB[6] = image.getImage("fleche3").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    //tourner +180°
    tIB[7] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    tIB[8] = image.getImage("centrer").getScaledInstance(tailleBouton,tailleBouton ,Image.SCALE_SMOOTH);
    return tIB;
  }
  public void tournerLesFleches(String nom){
    initialisationFX=true;
    Img f = new Img(nom);
    Img f1 = f.clone();
    f1.tourner(1);
    Img f2 = f.clone();
    f2.tourner(3);
    Img f3 = f.clone();
    f3.tourner(2);
    f2.sauvegarder("fleche2.png");
    f1.sauvegarder("fleche1.png");
    f3.sauvegarder("fleche3.png");
  }public void tournerLesFleches(){ tournerLesFleches("fleche");}
  public void doAction(byte ac){
    Main.getPj().doAction(ac);
  }public void doAction(int ac){ doAction((byte)ac);}
}
