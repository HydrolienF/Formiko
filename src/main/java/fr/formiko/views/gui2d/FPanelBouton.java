package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.structures.listes.*;

import java.awt.Color;
import java.awt.Component;
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

public class FPanelBouton extends FPanel {
  private FPanelZoom pz;
  private FPanelAction pa;
  private FPanelActionSup pas;
  private FPanelActionInf pai;
  private FPanelMiniMapContainer pmmc;
  private FPanelTInt pti;
  private FPanelTBoolean ptb;
  private String descS;
  private FLabel desc;
  private FLabel descTI;
  private int actionF;
  private int choixId;
  private FPanelChamp pchamp;
  private FPanelInfo pi;
  private FPanelInfoText pij;
  private Font fontPij;
  private Liste<Component> lToRemove;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelBouton(){}
  public void build(){
    setLayout(null);
    descS=""; desc = new FLabel(getWidth(),FLabel.getDimY());
    desc.setBackground(Main.getData().getButtonColor());
    actionF = -1; choixId = -1;
    int t [] = {0,1,2,3,4,5};
    ptb = new FPanelTBoolean(null);
    pti = new FPanelTInt(null,this);
    pa = new FPanelAction();
    pas = new FPanelActionSup();
    pai = new FPanelActionInf();
    pmmc = new FPanelMiniMapContainer();
    pz = new FPanelZoom();
    descTI = new FLabel();
    descTI.setBackground(Main.getData().getButtonColor());
    setDescTI("");
    setDesc("");
    descTI.setBounds(0,0,800);
    // on ajoute les éléments non visible. Les éléments visible sont add 1 a 1 quand le besoin ce fait sentir.
    add(descTI);
    add(desc);
    add(pz);
    lToRemove = new Liste<Component>();
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
  public FPanelTInt getPti(){ return pti;}
  public void setPti(FPanelTInt p){pti=p; }
  public int getChoixId(){ return choixId;}
  public void setChoixId(int x){ choixId=x;}
  public FLabel getDescTI(){ return descTI;}
  public void setDescTI(String s){descTI.setTexte(s);}
  public FPanelZoom getPz(){ return pz;}
  public FPanelAction getPa(){ return pa;}
  public FPanelChamp getPChamp(){ return pchamp;}
  public FPanelInfo getPi(){ return pi;}
  public FPanelInfoText getPij(){ return pij;}
  public FPanelTBoolean getPTB(){ return ptb;}
  public FPanelMiniMapContainer getPmmc(){return pmmc;}
  // FUNCTIONS -----------------------------------------------------------------
  public void removes(){
    while(!lToRemove.isEmpty()){
      remove(lToRemove.pop());
    }
  }
  public void addPz(){
    remove(pz);
    pz = new FPanelZoom();
    pz.build();
    int x = pz.getbuttonSize()*3;
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
    pti=new FPanelTInt(t,s);
    pti.setBounds(Main.getWidth()-pti.getXPi(),Main.getHeight()-pti.getYPi(),pti.getXPi(),pti.getYPi());
    debug.débogage("le composants pti a été placé en 0 FLabel.getDimY() avec pour dimention : "+pti.getXPi()+" "+pti.getYPi());
    add(pti);
  }
  public void removePti(){
    remove(pti);
  }
  public void addPTB(String message){
    ptb=new FPanelTBoolean(g.get(message));
    ptb.setBounds(0,FLabel.getDimY(),ptb.getXPi(),ptb.getYPi());
    add(pti);
  }
  public void removePTB(){
    remove(ptb);
  }
  public synchronized void addPa(int t[]){
    lToRemove.add(pa);
    lToRemove.add(pas);
    lToRemove.add(pai);
    // lToRemove.add(pmmc);
    pa = new FPanelAction(t);
    pa.build();
    int xxx = pa.getbuttonSize();
    pa.setBounds(0,getHeight()-pa.getHeight(),pa.getWidth(),pa.getHeight());
    pas = new FPanelActionSup();
    pas.setBounds(0,getHeight()-pas.getHeight(),pas.getWidth(),pas.getHeight());
    //FPanelActionInf paiPrécédent = pai;
    pai = new FPanelActionInf();
    pai.setBounds(0,getHeight()-pai.getHeight(),pai.getWidth(),pai.getHeight());
    getView().getPs().actualiserTaille();
    // pmmc = new FPanelMiniMapContainer();
    add(pmmc);
    add(pas);
    add(pa);
    add(pai);
    removes();
    setVisiblePa(true);
    /*try {
      remove(paiPrécédent);
    }catch (Exception e) {}*/
    revalidate();
    // Main.repaint();
  }public void addPA(int t[]){addPa(t);}
  public void setVisiblePa(boolean b){
    pa.setVisible(b);
    pas.setVisible(b);
    //pai.setVisible(b);
  }
  public void removePa(){
    // erreur.info("removePA",3);
    remove(pa);
    remove(pas);
    remove(pai);
    remove(pmmc);
  }public void removePA(){removePa();}
  public void addPA(){ int t [] = {0,1,2,3,4,5,6,7}; addPA(t);}
  /*public void modPa(){
    remove(pa);
    pa = new FPanelAction(Main.getPlayingAnt().getTActionFourmi());
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
    pchamp = new FPanelChamp(défaut);
    pchamp.setBounds(0,FLabel.getDimY(),540,FLabel.getDimY());
    add(pchamp);
    validate();
  }
  public void removePChamp(){ remove(pchamp);setDescTI("");}
  public void addPI(){
    lToRemove.add(pi);
    Fourmi playingAnt = Main.getPlayingAnt();
    if(playingAnt!=null){
      pi = FPanelInfoCreature.builder().addCreature(playingAnt)
      .setX(Main.getTailleElementGraphiqueX(320))
      .setYByElement(Main.getTailleElementGraphiqueY(32))
      .build();
      pi.setLocation(getWidth()-pi.getWidth(),pz.getbuttonSize()*3);
      removes();
      add(pi);
    }else{
      erreur.alerte("FPanelInfoCreature haven't been set because playingAnt is null");
    }
  }
  public void removePi(){remove(pi);}
  public void addPIJ(){
    try {
      removePij();
    }catch (Exception e) {}
    if(!Main.getOp().getDrawPlayerMessagePanel()){return;}
    if(getView().getPd()!=null && getView().getPd().isVisible()){return;}
    if(fontPij==null){
      fontPij = new Font(Main.getOp().getFontText(),Font.PLAIN,(int)(Main.getOp().getFontSizeText()/1.5));
    }
    Joueur playingPlayer = Main.getPlayingJoueur();
    if (playingPlayer==null){ return;}
    GString gs = playingPlayer.getGm().gmToGs(Main.getMaxMessageDisplay());
    pij = new FPanelInfoText(gs,Main.getTailleElementGraphiqueX(500),true,fontPij);
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
      erreur.erreur("affichage de FPanelBouton");
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
