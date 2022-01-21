package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.maths.math;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.types.str;
import fr.formiko.views.gui2d.FLabel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class FPanelJeu extends FPanel {
  private FPanelCarte pc;
  private FPanelBouton pb;
  private FPanelChargement pch;
  private FPanelFinPartie pfp;
  private FPanelEchap pe;
  private FPanelSup ps;
  private FPanelDialogue pd;
  private FPanelDialogueInf pdi;
  private FPanelPanelMove pmmo;

  private FLabel labelMessage;
  private ThreadMessagesDesc th;

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelJeu(){
    setLayout(null);
    labelMessage = new FLabel("");
    labelMessage.setBackground(new Color(225,225,225));
    FBorder border = new FBorder();
    border.setColor(Color.BLACK);
    border.setThickness(1);
    labelMessage.setBorder(border);
    add(labelMessage);
  }
  // GET SET -------------------------------------------------------------------
  public FPanelBouton getPb(){ return pb;}
  public FPanelCarte getPc(){ return pc;}
  public FPanelChargement getPch(){ return pch;}
  public FPanelSup getPs(){return ps;}
  public FPanelEchap getPe(){return pe;}
  public FPanelDialogue getPd(){return pd;}
  public FPanelDialogueInf getPdi(){return pdi;}
  public FPanelPanelMove getPmmo(){return pmmo;}
  //get set transmis
  public void addPA(){ pb.addPA();}
  public void addPti(int x [], int y){pb.addPti(x,y);}
  public void setDescTI(String s){pb.setDescTI(s);}
  public FPanelTInt getPti(){return pb.getPti(); }
  // FUNCTIONS -----------------------------------------------------------------
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  public void addPd(){
    if(pd==null){pd = new FPanelDialogue();}
    if(pdi==null){
      FPanelDialogueInf.chargerFond();
      pdi = new FPanelDialogueInf();
    }
    add(pd);
    add(pdi);
  }
  public void initialiserPd(String s, boolean needToStayMaxSize){
    pd.initialiser(s, needToStayMaxSize);
    pdi.initialiser();
    // pd.setLocation(0,0);
    revalidate();
  }
  public void removePd(){
    remove(pd);
    remove(pdi);
  }
  public void addPe(){
    pe=new FPanelEchap();
    pe.setBounds(0,0,getWidth(),getHeight());
    add(pe);
  }
  public void addPs(){
    ps=new FPanelSup();
    ps.setSize(getWidth(),getHeight());
    add(ps);
  }
  public void addPc(){
    pc = new FPanelCarte();
    pc.updateSize();
    add(pc);
  }
  public void addPb(){
    pb = new FPanelBouton();
    pb.setBounds(0,0,getWidth(),getHeight());
    add(pb);
  }
  public void addPch(){
    pch = new FPanelChargement();
    add(pch);
    pch.setVisible(false);
  }
  public void iniPch(){
    pch.setBounds(0,0,getWidth(),getHeight());
    if(pd!=null){
      pd.setVisible(false);
      pdi.setVisible(false);
    }
    pc.setVisible(false);
    pb.setVisible(false);
    pch.setVisible(true);
    // getView().repaint();
  }
  public void removePch(){
    remove(pch);
    pch = null;
    if(pd!=null){
      if(pd.getWidth()>1){
        pd.setVisible(true);
        pdi.setVisible(true);
      }
    }
    pc.setVisible(true);
    pb.setVisible(true);
    action.updateMouseLocation();
  }
  // public void addPfp(){
  //   pfp = new FPanelFinPartie();
  //   add(pfp);
  // }
  public void addPfp(){
    if(pfp!=null){
      erreur.alerte("Pfp already add");
      return;
    }
    pfp = new FPanelFinPartie();
    add(pfp);
  }
  public void addPfp(String mess, GJoueur gj, boolean withButton, boolean canResumeGame){
    pfp.setBounds(getWidth()/4,Main.getTailleElementGraphiqueX(250),getWidth()/2,Main.getDimY()-(2*Main.getTailleElementGraphiqueX(250)));
    pfp.ini(mess,gj,withButton,canResumeGame);
    if(withButton){
      pb.setVisible(false);
      ps.setSize(0,0);
    }
  }
  public void removePfp(){
    remove(pfp);
    pfp = null;
    pc.setVisible(true);
    pb.setVisible(true);
    ps.actualiserTaille();
  }
  public void addPmmo(){
    pmmo = new FPanelPanelMove();
    pmmo.setSize(getWidth(), getHeight());
    add(pmmo);
  }
  /*public int getXPourCentré(int x, int taille){
    if(x<taille){ return x;}
    return x/2-(taille);
  }*/
  public int nbrDeCaseAffichableX(){
    return (getWidth()/pc.getTailleDUneCase())+1;
  }
  public int nbrDeCaseAffichableY(){
    return (getHeight()/pc.getTailleDUneCase())+1;
  }
  public void dézoomer(byte x){
    int y1 = Main.getDimX()/Main.getGc().getNbrX();
    int pah = FPanel.getView().getPa().getHeight();
    if(pah==0){pah=Main.getTailleElementGraphique(180);}
    int y2 = (Main.getDimY()-pah)/Main.getGc().getNbrY();
    int y=0;
    if(x==1){ y=math.max(y1,y2);}
    else if(x==2){ y=math.min(y1,y2);}
    y = math.between(Main.getTailleElementGraphique(100), Main.getTailleElementGraphique(500), y);
    pc.setTailleDUneCase(y);
    actionAFaireSiTailleD1CaseChange();
  }
  public void actionAFaireSiTailleD1CaseChange(){
    if (Main.getPartie().getEnCours()){
      FPanel.getView().getPc().updateSize();
      Main.getData().chargerImages();
      getView().getPmmc().build();
      Main.getData().iniBackgroundMapImage();
      getView().setBladeChanged(true);
    }
  }
  public void actionZoom(byte ac){
    if (ac==2) { // zoom
      pc.setTailleDUneCase(math.min((pc.getTailleDUneCase()*4)/3,500));
      actionAFaireSiTailleD1CaseChange();
    }else if(ac==0){ // dézoom
      pc.setTailleDUneCase(math.max((pc.getTailleDUneCase()*3)/4,10));
      actionAFaireSiTailleD1CaseChange();
    }else if(ac==1){
      // pc.setPosY(math.max(pc.getPosY()-1,0));
    }else if(ac==7){
      // GCase gc = Main.getGc();
      // pc.setPosY(math.min(pc.getPosY()+1,gc.getNbrY()-1));
    }else if(ac==5){
      // GCase gc = Main.getGc();
      // pc.setPosX(math.min(pc.getPosX()+1,gc.getNbrX()-1));
    }else if(ac==3){
      // pc.setPosX(math.max(pc.getPosX()-1,0));
    }else if(ac==4){ //center over anthill
      if(Main.getPlayingJoueur()==null || Main.getPlayingJoueur().getFere()==null){return;}
      getView().centerOverCase(Main.getPlayingJoueur().getFere().getCCase().getContent());
    }else if(ac==6){ //center over playing ant
      if(Main.getPlayingAnt()==null){return;}
      getView().centerOverCase(Main.getPlayingAnt().getCCase().getContent());
    }else if(ac==8){
      dézoomer((byte)2);
    }
    Main.repaint();
  }

  /**
  *{@summary print an alerte box.}
  *@version 1.49
  */
  public void alerte(String s, String s2){
    JOptionPane jop1 = new JOptionPane();
    jop1.showMessageDialog(Main.getF(), s, s2, JOptionPane.INFORMATION_MESSAGE);
  }
  public void alerte(String s){ alerte(s,g.getM("information"));}
  /**
  *{@summary Print a question box.}
  *@param popUpName name of the popUp
  *@param popUpMessage message of the popUp
  *@return answer.
  *@version 1.50
  */
  public String question(String popUpName, String popUpMessage){
    String r = JOptionPane.showInputDialog (Main.getF(), g.getM(popUpName), popUpMessage, JOptionPane.QUESTION_MESSAGE);
    return r;
  }
  /***
  *{@summary Print a question box.}
  *@param popUpName name of the popUp
  *@return answer.
  *@version 1.50
  */
  public String question(String popUpName){ return question(popUpName,"?");}

  /**
  *{@summary Update time from last move in the Thread.}
  *@version 2.7
  */
  public void updateTimeFromLastMove(){
    if(th==null){return;}
    th.updateTimeFromLastMove();
  }
  /**
  *{@summary Update message.}<br>
  *It will initialize &#38; launch ThreadMessagesDesc if it is null.
  *@version 2.7
  */
  public void updateThreadMessagesDesc(String message){
    if(th==null){
      th = new ThreadMessagesDesc();
      th.start();
    }
    th.setMessage(message);
  }

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Thread used to print a description message at mouse location.}<br>
  *Message is print only after 0.5s if mouse don't move.
  *@author Hydrolien
  *@version 2.7
  */
  class ThreadMessagesDesc extends Thread {
    private String message;
    private long timeFromLastMove;
    private boolean needToUpdateTimeFromLastMove;
    public void setMessage(String s){message=s;}
    public void updateTimeFromLastMove(){needToUpdateTimeFromLastMove=true;}

    /**
    *{@summary Main function that update message if needed every 50ms.}<br>
    *@version 2.7
    */
    @Override
    public void run(){
      needToUpdateTimeFromLastMove=false;
      while (true) {
        boolean visible = false;
        if(message!=null && !message.equals("")){
          if(needToUpdateTimeFromLastMove){
            timeFromLastMove = System.currentTimeMillis();
            needToUpdateTimeFromLastMove=false;
          }else{
            long currentTime = System.currentTimeMillis();
            long timeElapsed = currentTime-timeFromLastMove;
            if(timeElapsed>500){
              if(timeElapsed<600){
                labelMessage.setText(message);
                labelMessage.updateSize();
                Point curentLocation = MouseInfo.getPointerInfo().getLocation();
                labelMessage.setLocation((int)(curentLocation.getX()-labelMessage.getWidth()), (int)(curentLocation.getY()-labelMessage.getHeight()));
              }
              visible = true;
            }
          }
        }else{
          needToUpdateTimeFromLastMove=true;
        }
        labelMessage.setVisible(visible);
        // Temps.pause(50);
        try {
          sleep(50);
        }catch (InterruptedException e) {
          erreur.erreur("thread have been interupted");
        }
      }
    }
  }
}
