package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.maths.allea;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class PanneauChargement extends Panneau {
  private Bouton b;
  private int tempsTotalDeChargement;
  private Desc message;
  private PanneauInfo conseil;
  private boolean lancer;
  private Bouton bt;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauChargement(){
    setLayout(null);
    Main.getData().loadImageChargement();
    addMessage();
    addConseil();
    lancer=false;
  }
  // GET SET --------------------------------------------------------------------
  public void setTexte(String s){ message.setTexte(s);}
  public boolean getLancer(){return lancer;}
  public void setLancer(boolean b){lancer=b;}
  // Fonctions propre -----------------------------------------------------------
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
    // message.setBounds(xx,yy*4-Main.getTaillePolice1(),xx*3);
    // conseil.setLocation(xx,(yy*4)-(2*Main.getTaillePolice1())-conseil.getYPi());
  }
  public void addBt(){
    lancer=false;
    bt = new Bouton(g.getM("lancerLeJeu"), Panneau.getView().getPj(), 111);
    bt.setFont(Main.getFont2());
    add(bt);
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    bt.setBounds((int)(xx*1.5),yy*4+Main.getTaillePolice1(),xx*2,Main.getTaillePolice2());
  }
  public void addMessage(){
    message = new Desc();
    message.setTexte("");
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    message.setBounds(xx,yy*4-Main.getTaillePolice1(),xx*3);
    add(message);
  }
  public void addConseil(){
    int x = allea.getAlléa(19)+1;//de 1 a 19.
    String s = g.getM("conseil."+x);
    GString gs = new GString();
    gs.addParMorceaux(s,70,true);//ajoute 70 char par 70 char (sans couper les mots) a la GString
    conseil = new PanneauInfo(gs,(Main.getF().getWidth()*3)/5,false);
    add(conseil);
    int xx = Main.getF().getWidth()/5;
    int yy = Main.getF().getHeight()/5;
    conseil.setLocation(xx,(yy*4)-(2*Main.getTaillePolice1())-conseil.getYPi());
    repaint();
  }
}
