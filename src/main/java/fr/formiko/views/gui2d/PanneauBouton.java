package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.structures.listes.*;
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
  private PanneauMiniMapContainer pmmc;
  private PanneauTInt pti;
  private PanneauTBoolean ptb;
  private String descS;
  private FLabel desc;
  private FLabel descTI;
  private int actionF;
  private int choixId;
  private PanneauChamp pchamp;
  private PanneauInfo pi;
  private PanneauInfoText pij;
  private Font fontPij;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauBouton(){}
  public void build(){
    setLayout(null);
    descS=""; desc = new FLabel(getWidth(),FLabel.getDimY());
    desc.setBackground(Main.getData().getButtonColor());
    actionF = -1; choixId = -1;
    int t [] = {0,1,2,3,4,5};
    ptb = new PanneauTBoolean(null);
    pti = new PanneauTInt(null,this);
    pa = new PanneauAction();
    pas = new PanneauActionSup();
    pai = new PanneauActionInf();
    pmmc = new PanneauMiniMapContainer();
    pz = new PanneauZoom();
    descTI = new FLabel();
    descTI.setBackground(Main.getData().getButtonColor());
    setDescTI("");
    setDesc("");
    descTI.setBounds(0,0,800);
    // on ajoute les éléments non visible. Les éléments visible sont add 1 a 1 quand le besoin ce fait sentir.
    add(descTI);
    add(desc);
    add(pz);
  }
  // GET SET -------------------------------------------------------------------
  public String getDesc(){return descS;}
  public void setDesc(String s){
    if(getView().getPe()==null || !getView().getPe().getVisible()){
      descS=s;
      actualiserDesc();
    }
  }
  public int getActionF(){ return actionF;}
  public void setActionF(int x){ actionF=x;}
  public PanneauTInt getPti(){ return pti;}
  public void setPti(PanneauTInt p){pti=p; }
  public int getChoixId(){ return choixId;}
  public void setChoixId(int x){ choixId=x;}
  public FLabel getDescTI(){ return descTI;}
  public void setDescTI(String s){descTI.setTexte(s);}
  public PanneauZoom getPz(){ return pz;}
  public PanneauAction getPa(){ return pa;}
  public PanneauChamp getPChamp(){ return pchamp;}
  public PanneauInfo getPi(){ return pi;}
  public PanneauInfoText getPij(){ return pij;}
  public PanneauTBoolean getPTB(){ return ptb;}
  public PanneauMiniMapContainer getPmmc(){return pmmc;}
  // FUNCTIONS -----------------------------------------------------------------
  public void addPz(){
    remove(pz);
    pz = new PanneauZoom();
    pz.build();
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
    debug.débogage("le composants pti a été placé en 0 FLabel.getDimY() avec pour dimention : "+pti.getXPi()+" "+pti.getYPi());
    add(pti);
  }
  public void removePti(){
    remove(pti);
  }
  public void addPTB(String message){
    ptb=new PanneauTBoolean(g.get(message));
    ptb.setBounds(0,FLabel.getDimY(),ptb.getXPi(),ptb.getYPi());
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
    pa.build();
    int xxx = pa.getTailleBouton();
    pa.setBounds(0,getHeight()-pa.getHeight(),pa.getWidth(),pa.getHeight());
    pas = new PanneauActionSup();
    pas.setBounds(0,getHeight()-pas.getHeight(),pas.getWidth(),pas.getHeight());
    //PanneauActionInf paiPrécédent = pai;
    pai = new PanneauActionInf();
    pai.setBounds(0,getHeight()-pai.getHeight(),pai.getWidth(),pai.getHeight());
    getView().getPs().actualiserTaille();
    // pmmc = new PanneauMiniMapContainer();
    add(pmmc);
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
    remove(pmmc);
  }public void removePA(){removePa();}
  public void addPA(){ int t [] = {0,1,2,3,4,5,6,7}; addPA(t);}
  /*public void modPa(){
    remove(pa);
    pa = new PanneauAction(Main.getPlayingAnt().getTActionFourmi());
    pa.build();
    add(pa);
    revalidate();
    Main.repaint();
  }*/
  public void setVisiblePmmc(boolean b){
    pmmc.setVisible(b);
  }
  public void addPChamp(String défaut,String message){
    setDescTI(message);
    pchamp = new PanneauChamp(défaut);
    pchamp.setBounds(0,FLabel.getDimY(),540,FLabel.getDimY());
    add(pchamp);
    validate();
  }
  public void removePChamp(){ remove(pchamp);setDescTI("");}
  public void addPI(){
    try {
      removePi();
    }catch (Exception e) {}
    Fourmi playingAnt = Main.getPlayingAnt();
    if(playingAnt!=null){
      pi = PanneauInfoCreature.builder().addCreature(playingAnt)
      .setX(Main.getTailleElementGraphiqueX(320))
      .setYByElement(Main.getTailleElementGraphiqueY(30))
      .build();
      pi.setLocation(getWidth()-pi.getWidth(),pz.getTailleBouton()*3);
      add(pi);
    }else{
      erreur.alerte("PanneauInfoCreature haven't been set because playingAnt is null");
    }
  }
  public void removePi(){ remove(pi);}
  public void addPIJ(){
    try {
      removePij();
    }catch (Exception e) {}
    if(getView().getPd()!=null && getView().getPd().isVisible()){return;}
    if(fontPij==null){
      fontPij = new Font(Main.getOp().getPolice(),Font.PLAIN,(int)(Main.getOp().getTaillePolice1()/1.5));
    }
    Fourmi ft = Main.getPlayingAnt();
    if (ft==null){ return;}
    GString gs = ft.getFourmiliere().getJoueur().getGm().gmToGs(Main.getNbrMessageAfficher());
    debug.débogage("affichage console du contenu de gs");
    pij = new PanneauInfoText(gs,Main.getTailleElementGraphiqueX(500),true,fontPij);
    int xx = pz.getTailleBouton()*5;
    debug.débogage("initialisation du PanneauInfoJoueur en "+(getWidth()-xx)+" "+(getHeight()-pij.getYPi()));
    int x = Main.getTailleElementGraphiqueX(320);
    pij.setBounds((getWidth()-x*2)/2,Main.getTailleElementGraphiqueY(100),x,pij.getYPi());
    add(pij);
  }
  public void removePij(){ remove(pij);}
  //repaint() permet de réactualisé paintComponent()
  @Override
  public void paintComponent(Graphics gr){
    // pas mal de setBounds pourrait partir si la fenetre avait une taille fixe.
    try {
      if(!Main.getPartie().getEnCours()){return;}
    }catch (Exception e) {}
    try {
      int xxx=0;
      try {
        xxx = pa.getHeight();
      }catch (Exception e) {}
      desc.setLocation(0,Main.getDimY()-xxx-FLabel.getDimY());//-desc.getHeight()
      descTI.setBounds(0,0,800);
    }catch (Exception e) {
      erreur.erreur("affichage de PanneauBouton");
    }
  }
  public void actualiserDesc(){
    debug.débogage("actualisation de la description");
    if(getView().getActionGameOn()){
      desc.setTexte(descS);
      desc.updateSize();
      try {
        Main.repaint();
      }catch (Exception e) {}
    }else{
      desc.setTexte("");
    }
  }
  public void actualiserDescTI(String s){
    if(getView().getActionGameOn()){
      debug.débogage("actualisation de la descriptionTI");
      try {
        descTI.setTexte(s);
        descTI.updateSize();
      }catch (Exception e) {}
    }else{
      desc.setTexte("");
    }
  }

}
