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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JScrollPane;

public class PanneauJeu extends Panneau {
  private PanneauCarte pc;
  private PanneauBouton pb;
  private PanneauChargement pch;
  private PanneauFinPartie pfp;
  private PanneauEchap pe;
  private PanneauSup ps;
  private PanneauDialogue pd;
  private PanneauDialogueInf pdi;
  // CONSTRUCTORS --------------------------------------------------------------
  public PanneauJeu(){
    setLayout(null);
  }
  // GET SET -------------------------------------------------------------------
  public PanneauBouton getPb(){ return pb;}
  public PanneauCarte getPc(){ return pc;}
  public PanneauChargement getPch(){ return pch;}
  public PanneauSup getPs(){return ps;}
  public PanneauEchap getPe(){return pe;}
  public PanneauDialogue getPd(){return pd;}
  public PanneauDialogueInf getPdi(){return pdi;}
  //get set transmis
  public void addPA(){ pb.addPA();}
  public void addPti(int x [], int y){pb.addPti(x,y);}
  public void setDescTI(String s){pb.setDescTI(s);}
  public PanneauTInt getPti(){return pb.getPti(); }
  // FUNCTIONS -----------------------------------------------------------------
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
  }
  public void addPd(){
    if(pd==null){pd = new PanneauDialogue();}
    if(pdi==null){
      PanneauDialogueInf.chargerFond();
      pdi = new PanneauDialogueInf();
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
    pe=new PanneauEchap();
    pe.setBounds(0,0,getWidth(),getHeight());
    add(pe);
  }
  public void addPs(){
    ps=new PanneauSup();
    ps.setBounds(0,0,getWidth(),getHeight());
    add(ps);
  }
  public void addPc(){
    pc = new PanneauCarte();
    pc.updateSize();
    add(pc);
  }
  public void addPb(){
    pb = new PanneauBouton();
    pb.setBounds(0,0,getWidth(),getHeight());
    add(pb);
  }
  public void addPch(){
    pch = new PanneauChargement();
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
  //   pfp = new PanneauFinPartie();
  //   add(pfp);
  // }
  public void addPfp(){
    if(pfp!=null){
      erreur.alerte("Pfp already add");
      return;
    }
    pfp = new PanneauFinPartie();
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
  /*public int getXPourCentré(int x, int taille){
    if(x<taille){ return x;}
    return x/2-(taille);
  }*/
  public void centrerLaCarte(){
    GCase gc = Main.getGc();
    pc.setPosX(math.max(gc.getNbrX()/2 - nbrDeCaseAffichableX(),0));
    pc.setPosY(math.max(gc.getNbrY()/2 - nbrDeCaseAffichableY(),0));
  }
  public int nbrDeCaseAffichableX(){
    return (getWidth()/pc.getTailleDUneCase())+1;
  }
  public int nbrDeCaseAffichableY(){
    return (getHeight()/pc.getTailleDUneCase())+1;
  }
  public void dézoomer(byte x){
    int y1 = Main.getDimX()/Main.getGc().getNbrX();
    int pah = Panneau.getView().getPa().getHeight();
    if(pah==0){pah=Main.getTailleElementGraphique(180);}
    int y2 = (Main.getDimY()-pah)/Main.getGc().getNbrY();
    int y=0;
    if(x==1){ y=math.max(y1,y2);}
    else if(x==2){ y=math.min(y1,y2);}
    pc.setTailleDUneCase(y);
    actionAFaireSiTailleD1CaseChange();
  }
  public void actionAFaireSiTailleD1CaseChange(){
    if (Main.getPartie().getEnCours()){
      Panneau.getView().getPc().updateSize();
      Main.getData().chargerImages();
      Main.getData().iniBackgroundMapImage();
    }
  }
  public void centrerSurLaFourmi(){
    if (Main.getPlayingAnt()==null){erreur.alerte("Impossible de centrer sur une fourmi si aucune fourmi n'est selectionné."); return;}
    int x = nbrDeCaseAffichableX();
    int y = nbrDeCaseAffichableY();
    int posX = Main.getPlayingAnt().getX();
    int posY = Main.getPlayingAnt().getX();
    pc.setPosX(posX + x/2);
    pc.setPosY(posY + y/2);
  }
  public void actionZoom(byte ac){
    if (ac==2) { // zoom
      pc.setTailleDUneCase(math.min((pc.getTailleDUneCase()*4)/3,500));
      actionAFaireSiTailleD1CaseChange();
    }else if(ac==0){ // dézoom
      pc.setTailleDUneCase(math.max((pc.getTailleDUneCase()*3)/4,10));
      actionAFaireSiTailleD1CaseChange();
    }else if(ac==1){
      pc.setPosY(math.max(pc.getPosY()-1,0));
    }else if(ac==7){
      GCase gc = Main.getGc();
      pc.setPosY(math.min(pc.getPosY()+1,gc.getNbrY()-1));
    }else if(ac==5){
      GCase gc = Main.getGc();
      pc.setPosX(math.min(pc.getPosX()+1,gc.getNbrX()-1));
    }else if(ac==3){
      pc.setPosX(math.max(pc.getPosX()-1,0));
    }else if(ac==4){
      centrerLaCarte();
    }else if(ac==6){
      //centrerSurLaFourmi(); //pour l'instant ca fait pas ce qu'il faut.
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
  *{@summary print a question box.}
  *@return answer.
  *@version 1.50
  */
  public String question(String s, String s2){
    // String[] options = {g.get("oui"),g.get("non")};
    // String r = JOptionPane.showOptionDialog(Main.getF(), g.get(s), g.get(s2)+" ?",JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
    String r = JOptionPane.showInputDialog (Main.getF(), g.getM(s), s2, JOptionPane.QUESTION_MESSAGE);
    return r;
  }
  public String question(String s){ return question(s,"?");}
}
