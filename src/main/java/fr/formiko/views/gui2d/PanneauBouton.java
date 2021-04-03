package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.listes.*;
import fr.formiko.usuel.maths.math;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanneauBouton extends Panneau {
  private PanneauZoom pz;
  private PanneauAction pa;
  private PanneauActionSup pas;
  private PanneauActionInf pai;
  private PanneauTInt pti;
  private PanneauTBoolean ptb;
  private String descS;
  private Desc desc;
  private Desc descTI;
  private int actionF;
  private int choixId;
  private PanneauChamp pchamp;
  private PanneauInfo pi;
  private PanneauInfo pij;
  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauBouton(){}
  public void construire(){
    setLayout(null);
    descS=""; desc = new Desc();
    actionF = -1; choixId = -1;
    int t [] = {0,1,2,3,4,5};
    ptb = new PanneauTBoolean(null);
    pti = new PanneauTInt(null,this);
    pa = new PanneauAction();
    pas = new PanneauActionSup();
    pai = new PanneauActionInf();
    pi = new PanneauInfo();
    pij = new PanneauInfo();
    pz = new PanneauZoom();
    add(pi);add(pij);
    descTI = new Desc();
    setDescTI("");
    setDesc("");
    descTI.setBounds(0,0,800);
    // on ajoute les éléments non visible. Les éléments visible sont ajouter 1 a 1 quand le besoin ce fait sentir.
    add(descTI);
    add(desc);
    add(pz);
  }
  // GET SET -------------------------------------------------------------------
  public String getDesc(){return descS;}
  public void setDesc(String s){
    if(Main.getPe()==null || !Main.getPe().getVisible()){
      descS=s;
      actualiserDesc();
    }
  }
  public int getActionF(){ return actionF;}
  public void setActionF(int x){ actionF=x;}
  //public void setActionF(int x){ if(Main.getPac().getEstBoutonActif(x)){actionF=x;}else{erreur.alerte("L'action "+x+" n'est pas dans les actions faisable.","PanneauBouton.setActionF","l'action n'est pas prise en compte.");}}
  public PanneauTInt getPti(){ return pti;}
  public void setPti(PanneauTInt p){pti=p; }
  public int getChoixId(){ return choixId;}
  public void setChoixId(int x){ choixId=x;}
  public Desc getDescTI(){ return descTI;}
  public void setDescTI(String s){descTI.setTexte(s);}
  public PanneauZoom getPz(){ return pz;}
  public PanneauAction getPa(){ return pa;}
  public PanneauChamp getPChamp(){ return pchamp;}
  public PanneauInfo getPi(){ return pi;}
  public PanneauInfo getPij(){ return pij;}
  public PanneauTBoolean getPTB(){ return ptb;}
  // Fonctions propre -----------------------------------------------------------
  public void addPz(){
    remove(pz);
    pz = new PanneauZoom();
    pz.construire();
    int x = pz.getTailleBouton()*3;
    pz.setBounds(getWidth()-x,0,x,x);
    pz.setOpaque(false);
    add(pz);
  }
  public void removePz(){
    remove(pz);
  }
  public void addPti(int t[],int x){
    addPti(t,g.get("pti.desc."+x));
  }
  public void addPti(int t[], String s){
    pti=new PanneauTInt(t,s);
    pti.setBounds(Main.getWidth()-pti.getXPi(),Main.getHeight()-pti.getYPi(),pti.getXPi(),pti.getYPi());
    debug.débogage("le composants pti a été placé en 0 Desc.getDimY() avec pour dimention : "+pti.getXPi()+" "+pti.getYPi());
    add(pti);
  }
  public void removePti(){
    remove(pti);
  }
  public void addPTB(String message){
    ptb=new PanneauTBoolean(g.get(message));
    ptb.setBounds(0,Desc.getDimY(),ptb.getXPi(),ptb.getYPi());
    add(pti);
  }
  public void removePTB(){
    remove(ptb);
  }
  public synchronized void addPa(int t[]){
    try {
      //remove(pai);
      remove(pa);
      remove(pas);
    }catch (Exception e) {}
    pa = new PanneauAction(t);
    pa.construire();
    int xxx = pa.getTailleBouton();
    pa.setBounds(0,getHeight()-pa.getHeight(),pa.getWidth(),pa.getHeight());
    pas = new PanneauActionSup();
    pas.setBounds(0,getHeight()-pas.getHeight(),pas.getWidth(),pas.getHeight());
    //PanneauActionInf paiPrécédent = pai;
    pai = new PanneauActionInf();
    pai.setBounds(0,getHeight()-pai.getHeight(),pai.getWidth(),pai.getHeight());
    Main.getPs().actualiserTaille();
    add(pas);
    add(pa);
    add(pai);
    setVisiblePa(true);
    /*try {
      remove(paiPrécédent);
    }catch (Exception e) {}*/
    revalidate();
    Main.repaint();
  }public void addPA(int t[]){addPa(t);}
  public void setVisiblePa(boolean b){
    pa.setVisible(b);
    pas.setVisible(b);
    //pai.setVisible(b);
  }
  public void removePa(){
    remove(pa);
    remove(pas);
    remove(pai);
  }public void removePA(){removePa();}
  public void addPA(){ int t [] = {0,1,2,3,4,5,6,7}; addPA(t);}
  /*public void modPa(){
    remove(pa);
    pa = new PanneauAction(Main.getPlayingAnt().getTActionFourmi());
    pa.construire();
    add(pa);
    revalidate();
    Main.repaint();
  }*/
  public void addPChamp(String défaut,String message){
    setDescTI(message);
    pchamp = new PanneauChamp(défaut);
    pchamp.setBounds(0,Desc.getDimY(),540,Desc.getDimY());
    add(pchamp);
    validate();
  }
  public void removePChamp(){ remove(pchamp);setDescTI("");}
  public void addPI(){
    try {
      removePi();
    }catch (Exception e) {}
    debug.débogage("addPI()");
    pi = new PanneauInfo(Main.getPlayingAnt(),Main.getTailleElementGraphiqueX(320));
    int xx2 = pz.getTailleBouton()*3;
    debug.débogage("initialisation du PanneauInfo en "+(getWidth()-Main.getTailleElementGraphiqueX(320))+" "+Main.getTailleElementGraphiqueX(320));
    pi.setBounds(getWidth()-Main.getTailleElementGraphiqueX(320),xx2,Main.getTailleElementGraphiqueX(320),pi.getYPi());
    add(pi);
  }
  public void removePi(){ remove(pi);}
  public void addPIJ(){
    //removePij();
    Fourmi ft = Main.getPlayingAnt();
    if (ft==null){ return;}
    GString gs = ft.getFourmiliere().getJoueur().getGm().gmToGs(Main.getNbrMessageAfficher());
    debug.débogage("affichage console du contenu de gs");
    debug.débogage("");
    pij = new PanneauInfo(gs);
    add(pij);
    int xx = pz.getTailleBouton()*5;
    debug.débogage("initialisation du PanneauInfoJoueur en "+(getWidth()-xx)+" "+(getHeight()-pij.getYPi()));
    //pij.setBounds(getWidth()-xx,xx+pi.getY()*(pi.length()+1),pij.getX(),pij.getY()*pij.length());
    pij.setBounds(getWidth()-xx,getHeight()-pij.getY(),pij.getWidth(),pij.getHeight());
  }
  public void removePij(){ remove(pij);}
  //repaint() permet de réactualisé paintComponent()
  public void paintComponent(Graphics gr){
    // pas mal de satBounds pourrait partir si la fenetre avait une taille fixe.
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {}
    try {
      //debug.g("PanneauBouton",getWidth(),getHeight());
      int xxx=0;
      try {
        xxx = pa.getHeight();
      }catch (Exception e) {}
      desc.setSize((int)(desc.getText().length()*Main.getTaillePolice1()*0.6),Desc.getDimY());
      desc.setBounds(0,Main.getDimY()-xxx-desc.getHeight(),desc.getWidth(),desc.getHeight());
      descTI.setBounds(0,0,800);
    }catch (Exception e) {
      erreur.erreur("affichage de PanneauBouton");
    }
  }
  public void actualiserDesc(){
    debug.débogage("actualisation de la description");
    //int xxx = pa.getTailleBouton();
    //desc.setBounds(0,getHeight()-xxx-Desc.getDimY(),(int)(desc.getText().length()*Main.getTaillePolice1()*0.6),Desc.getDimY());
    desc.setTexte(descS);
    try {
      Main.repaint();
    }catch (Exception e) {}
  }
  public void actualiserDescTI(String s){
    debug.débogage("actualisation de la descriptionTI");
    try {
      descTI.setTexte(s);
    }catch (Exception e) {}
  }

}
