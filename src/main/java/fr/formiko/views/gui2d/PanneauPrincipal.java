package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.images.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PanneauPrincipal extends Panneau {
  private PanneauJeu pj;
  private PanneauMenu pm;
  private Image img;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauPrincipal(){}
  public void construire(){
    this.setLayout(null);
    img = image.getImage("arrierePlan.png");
    img = img.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_SMOOTH);
  }
  // GET SET --------------------------------------------------------------------
  public PanneauJeu getPj(){ return pj;}
  public PanneauMenu getPm(){ return pm;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(Graphics g){
    if (img!=null){
      g.drawImage(img,0,0,this);
    }
    debug.débogage("taille du paneau secondaire : x="+this.getWidth()+", y="+this.getHeight());
  }

  public void addPm(){
    pm = new PanneauMenu();
    pm.setOpaque(false);
    pm.setVisible(true);
    this.add(pm);
  }
  public void addPj(){
    pj = new PanneauJeu();
    pj.setOpaque(false);
    pj.setVisible(true);
    this.add(pj);
  }
  public void removePm(){
    pm.setVisible(false);
    remove(pm);
    pm=null;
  }
  public void removePj(){
    pj.setVisible(false);
    remove(pj);
    pj=null;
  }
}
