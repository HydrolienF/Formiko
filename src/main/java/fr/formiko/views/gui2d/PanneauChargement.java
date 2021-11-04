package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.structures.listes.GString;
import fr.formiko.usuel.maths.allea;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class PanneauChargement extends Panneau {
  private FButton b;
  private int tempsTotalDeChargement;
  private FLabel message;
  private FTextArea conseil;
  private boolean lancer;
  private FButton bt;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauChargement(){
    setLayout(null);
    Main.getData().loadImageChargement();
    addMessage();
    addConseil();
    lancer=false;
    bt=null;
  }
  // GET SET -------------------------------------------------------------------
  public void setTexte(String s){ message.setTexte(s);}
  public boolean getLancer(){return lancer;}
  public void setLancer(boolean b){lancer=b;}
  public boolean canBeClose(){return bt!=null;}
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {}
    debug.g("PanneauChargement",this.getWidth(),this.getHeight());
    try {
      g.drawImage(Main.getData().getImageChargement(),0,0,this);
    }catch (Exception e) {}
    // int xx = Main.getF().getWidth()/5;
    // int yy = Main.getF().getHeight()/5;
    // message.setBounds(xx,yy*4-Main.getFontSizeText(),xx*3);
    // conseil.setLocation(xx,(yy*4)-(2*Main.getFontSizeText())-conseil.getYPi());
  }
  public void addBt(){
    lancer=false;
    bt = new FButton(g.getM("lancerLeJeu"), Panneau.getView().getPj(), 111);
    bt.setFont(Main.getFont2());
    add(bt);
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    bt.setBounds((int)(xx*1.5),yy*4+Main.getFontSizeText(),xx*2,Main.getFontSizeTitle());
  }
  public void addMessage(){
    message = new FLabel();
    message.setTexte("");
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    message.setBounds(xx,yy*4-Main.getFontSizeText(),xx*3);
    add(message);
  }
  public void addConseil(){
    int x = allea.getAlléa(19)+1;//de 1 a 19.
    String s = g.getM("conseil."+x);
    conseil = new FTextArea(s,(Main.getF().getWidth()*3)/5);
    // conseil.setMinimumSize(new Dimension((Main.getF().getWidth()*3)/5, FLabel.getDimY()));
    add(conseil);
    conseil.setLocation(Main.getF().getWidth()/5,(Main.getF().getHeight()/5*4)-(2*Main.getFontSizeText())-conseil.getHeight());
    repaint();
  }
}
