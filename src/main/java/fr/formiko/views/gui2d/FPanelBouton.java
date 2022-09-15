package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.images.Images;
import fr.formiko.usual.maths.math;
import fr.formiko.usual.structures.listes.*;

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
  private FPanelIngameMenu pigm;
  private FPanelMusic pmu;
  private FPanelAction pa;
  private FPanelActionSup pas;
  private FPanelActionInf pai;
  private FPanelMiniMapContainer pmmc;
  private FPanelTInt pti;
  private FPanelTBoolean ptb;
  private String descS;
  private FTextArea desc;
  private FLabel descTI;
  private int actionF;
  private int choixId;
  private FPanelChamp pchamp;
  private FPanelInfo pi;
  private FPanelInfo piGc;
  private FPanelInfoText pij;
  private Font fontPij;
  private Liste<Component> lToRemove;
  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelBouton(){}
  public void build(){
    setLayout(null);
    descS="";
    actionF = -1; choixId = -1;
    int t [] = {0,1,2,3,4,5};
    ptb = new FPanelTBoolean(null);
    pti = new FPanelTInt(null,this);
    pa = new FPanelAction();
    pas = new FPanelActionSup();
    pai = new FPanelActionInf();
    pmmc = new FPanelMiniMapContainer();
    // pmmc.build(); //is build after FPanelCarte
    setVisiblePmmc(false);
    pz = new FPanelZoom();
    descTI = new FLabel();
    descTI.setBackground(Main.getData().getButtonColor());
    setDescTI("");
    desc = new FTextArea("",getWidth()-pmmc.getWidth());
    desc.setBackground(Main.getData().getButtonColor());
    setDesc("");
    descTI.setBounds(0,0,800);
    // on ajoute les éléments non visible. Les éléments visible sont add 1 a 1 quand le besoin ce fait sentir.
    add(descTI);
    // Add desc to FPanelCarte make it not-mouse listener because it's under FPanelSup. That's was we need to print desc of Square even id there are under desc.
    getView().getPText().add(desc);
    add(pz);
    add(pmmc);
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
  /**
  *{@summary Setter that notify playing ant in case it was waiting for an action to be choose.}
  *@lastEditedVersion 2.28
  */
  public void setActionF(int x){
    actionF=x;
    if(Main.getPlayingAnt()!=null){
      synchronized (Main.getPlayingAnt()) {
        Main.getPlayingAnt().notifyAll();
      }
    }
  }
  public FPanelTInt getPti(){ return pti;}
  public void setPti(FPanelTInt p){pti=p; }
  public int getChoixId(){ return choixId;}
  public void setChoixId(int x){ choixId=x;}
  public FLabel getDescTI(){ return descTI;}
  public void setDescTI(String s){descTI.setTexte(s);}
  public FPanelZoom getPz(){ return pz;}
  public FPanelIngameMenu getPigm(){ return pigm;}
  public FPanelMusic getPmu(){ return pmu;}
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
  /**
  *{@summary Add the FPanelZoom.}
  *It also update size &#38; location if needed.
  *@lastEditedVersion 2.28
  */
  public void addPz(){
    remove(pz);
    pz = new FPanelZoom();
    pz.build();
    int x = pz.getbuttonSize()*4;
    pz.setLocation(getWidth()-x,0);
    pz.setOpaque(false);
    add(pz);
  }
  /**
  *{@summary Remove the FPanelZoom.}
  *@lastEditedVersion 2.28
  */
  public void removePz(){
    remove(pz);
  }
  /**
  *{@summary Add the FPanelIngameMenu.}
  *It also update size &#38; location if needed.
  *@lastEditedVersion 2.28
  */
  public void addPigm(){
    pigm=new FPanelIngameMenu();
    pigm.setLocation(getWidth()-pigm.getWidth(), pz.getHeight());
    add(pigm);
    pmu.setLocation(getWidth()-pmu.getWidth(), pz.getHeight()+pigm.getHeight());
  }
  /**
  *{@summary Remove the FPanelIngameMenu.}
  *@lastEditedVersion 2.28
  */
  public void removePigm(){
    remove(pigm);
  }
  /**
  *{@summary Add the FPanelMusic.}
  *It also update size &#38; location if needed.
  *@lastEditedVersion 2.28
  */
  public void addPmu(){
    pmu=new FPanelMusic();
    pmu.setVisible(false);
    add(pmu);
  }
  /**
  *{@summary Remove the FPanelMusic.}
  *@lastEditedVersion 2.28
  */
  public void removePmu(){
    remove(pmu);
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
    Main.startCh();
    lToRemove.add(pa);
    lToRemove.add(pas);
    lToRemove.add(pai);
    pa = new FPanelAction(t);
    pa.build();
    int xxx = pa.getbuttonSize();
    pa.setBounds(0,getHeight()-pa.getHeight(),pa.getWidth(),pa.getHeight());
    FPanel.getView().getPmmo().setSpaceInY(getView().getPj().getHeight()-getView().getPa().getHeight());
    pas = new FPanelActionSup();
    pas.setBounds(0,getHeight()-pas.getHeight(),pas.getWidth(),pas.getHeight());
    //FPanelActionInf paiPrécédent = pai;
    pai = new FPanelActionInf();
    pai.setBounds(0,getHeight()-pai.getHeight(),pai.getWidth(),pai.getHeight());
    getView().getPs().updateSize();
    setVisiblePmmc(true);
    add(pas);
    add(pa);
    add(pai);
    removes();
    setVisiblePa(true);
    revalidate();
    Main.endCh("Update FPanelInfo");
  }public void addPA(int t[]){addPa(t);}
  public void setVisiblePa(boolean b){
    pa.setVisible(b);
    pas.setVisible(b);
    //pai.setVisible(b);
  }
  public boolean getVisiblePa(){return pa.isVisible();}
  public void hidePa(){
    removePa();
  }
  public void removePa(){
    // erreur.info("removePA",3);
    remove(pa);
    remove(pas);
    remove(pai);
  }public void removePA(){removePa();}
  public void addPA(){
    int t [] = {0,1,2,3,4,5,6,7};
    addPA(t);
  }
  /*public void modPa(){
    remove(pa);
    pa = new FPanelAction(Main.getPlayingAnt().getTActionFourmi());
    pa.build();
    add(pa);
    revalidate();
    Main.repaint();
  }*/
  public void setVisiblePmmc(boolean b){
    getPmmc().setVisible(b);
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
    Main.startCh();
    lToRemove.add(pi);
    lToRemove.add(piGc);
    Fourmi playingAnt = Main.getPlayingAnt();
    if(playingAnt!=null){
      pi = FPanelInfoCreature.builder().addCreature(playingAnt)
      .setX(Main.getTailleElementGraphiqueX(320))
      .setYByElement(Main.getTailleElementGraphiqueY(32))
      .build();
      pi.setLocation(getWidth()-pi.getWidth(),pz.getbuttonSize()*3);
      piGc = FPanelInfoGCreature.builder().addCreaturesOnSameSquare(playingAnt)
      .setX(Main.getTailleElementGraphiqueX(320))
      .setYByElement(Main.getTailleElementGraphiqueY(32))
      // .setYByElement(Main.getTailleElementGraphiqueY(40))
      .setAllowPanelsOnSameLine(true)
      .build();
      if(piGc!=null){
        piGc.setLocation(getWidth()-piGc.getWidth(),pz.getbuttonSize()*3+pi.getHeight());
      }
      removes();
      add(pi);
      if(piGc!=null){
        add(piGc);
      }
    }else{
      removes();
      erreur.alerte("FPanelInfoCreature haven't been set because playingAnt is null");
    }
    Main.endCh("Update FPanelAction");
  }
  public void removePi(){
    remove(pi);
    if(piGc!=null){
      remove(piGc);
    }
  }
  public void addPIJ(){
    try {
      removePij();
    }catch (Exception e) {}
    if(!Main.getFop().getBoolean("drawPlayerMessagePanel")){return;}
    if(getView().getPd()!=null && getView().getPd().isVisible()){return;}
    if(fontPij==null){
      fontPij = new Font(Main.getFop().getString("fontText"),Font.PLAIN,(int)(Main.getFop().getInt("fontSizeText")/1.5));
    }
    Joueur playingPlayer = Main.getPlayingJoueur();
    if (playingPlayer==null){ return;}
    GString gs = playingPlayer.getGm().gmToGs(Main.getFop().getInt("maxMessageDisplay"));
    pij = new FPanelInfoText(gs,Main.getTailleElementGraphiqueX(500),true,fontPij);
    //center with aviable space for map.
    pij.setLocation((getWidth()-getView().getPz().getWidth()-pij.getWidth())/2,Main.getTailleElementGraphiqueY(100));
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
      descTI.setBounds(0,0,800);
    }catch (Exception e) {
      erreur.erreur("affichage de FPanelBouton");
    }
  }
  public void actualiserDesc(){
    debug.débogage("actualisation de la description");
    if(desc==null){return;}
    if(getView().getActionGameOn()){
      desc.setText(descS);
      desc.updateSize();
      desc.setLocation(0,Main.getDimY()-pa.getHeight()-desc.getHeight());
      // desc.setLocation(0,0);
      try {
        Main.repaint();
      }catch (Exception e) {}
    }else{
      desc.setText("");
    }
  }
  public void actualiserDescTI(String s){
    if(descTI==null){return;}
    if(getView().getActionGameOn()){
      debug.débogage("actualisation de la descriptionTI");
      try {
        descTI.setTexte(s);
        descTI.updateSize();
      }catch (Exception e) {}
    }else{
      descTI.setTexte("");
    }
  }

}
