package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
*{@summary Map mouse listener panel.}<br>
*@author Hydrolien
*@version 1.42
*/
public class PanneauSup extends Panneau{
  private int idFourmiAjoué=-1;
  private CCase cc2=null;

  // CONSTRUCTEUR ---------------------------------------------------------------
  public PanneauSup(){}
  /**
  *{@summary Build this.}<br>
  *It add 2 mouse listener to update map description with case data, to move playingAnt &#38; to swap playing ant.<br>
  *<ul>
  *<li>If exited it set desc to "".
  *<li>If released :
  *<ul>
  *<li>with BUTTON1 it swap playingAnt.
  *<li>with BUTTON3 it move playingAnt.
  *</ul>
  *<li>If moved on an other Case, it update desc to newCase.toString().
  *</ul>
  *@version 1.42
  */
  public void build(){
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited​(MouseEvent e){
        Main.getPb().setDesc("");
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.getButton()== MouseEvent.BUTTON1){
          if(vérifierPanneauDialogue(e)){return;}
          GCreature gc = new GCreature();
          try {
            gc = getCase(e).getGc();
          }catch (Exception e2) {}
          if(gc.length()>0){
            Fourmi f = gc.getFourmiParFere(Main.getPlayingAnt().getFere());
            if(f!=null && f.getAction()>0){
              Main.getPj().setActionF(-2);
              Main.getPb().removePA();
              setIdFourmiAjoué(f.getId());
            }
          }
        }else if(e.getButton()== MouseEvent.BUTTON3){
          Fourmi f = Main.getPlayingAnt();
          if(f!=null){//si une fourmi est séléctionné.
            CCase cc = getCCase(e);
            if(cc!=null){
              f.ceDeplacerPlusieurCase(cc);
              Main.getPj().setActionF(-2);
              setIdFourmiAjoué(f.getId());
            }
          }
        }
      }
    });
    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        //Temps.pause(10);
        CCase cc = getCCase(e);
        if(cc==null){Main.getPb().setDesc("");cc2=null;return;}
        if(cc2==null || !cc2.getContenu().equals(cc.getContenu())){//si la case a changé.
          cc2=new CCase(cc.getContenu());
          Main.getPb().setDesc(cc.getContenu().toString());
        }
      }
    });
  }
  // GET SET --------------------------------------------------------------------
  public void actualiserTaille(){
    setSize(Main.getDimX()-Main.getPz().getWidth(), Main.getDimY()-Main.getPa().getHeight());
    //la 2a version est mieux pour prendre en compte les déplacements.
    //setSize(Main.getDimX()-Main.getPz().getWidth(), Main.getDimY()-math.max(Main.getPa().getHeight(),Main.getPTInt().getHeight()));
  }
  public void actualiserTailleMax(){
    setSize(Main.getDimX(), Main.getDimY());
  }
  public void actualiserTailleMin(){
    setSize(0,0);
  }
  public int getIdFourmiAjoué(){return idFourmiAjoué;}
  public void setIdFourmiAjoué(int x){idFourmiAjoué=x;}
  // Fonctions propre -----------------------------------------------------------
  public void paintComponent(){

  }
  public CCase getCCase(MouseEvent e){
    int tc = Main.getPc().getTailleDUneCase();
    int cx = e.getX()/tc;
    int cy = e.getY()/tc;
    try {
      return Main.getGc().getCCase(cx+Main.getPc().getPosX(),cy+Main.getPc().getPosY());
    }catch (Exception e2) {
      erreur.erreur("aucune case n'est sélectionné avec les coordonées : "+cx+" "+cy);
      return null;
    }
  }
  public Case getCase(MouseEvent e){
    try {
      return getCCase(e).getContenu();
    }catch (Exception e2) {
      return null;
    }
  }
  public boolean vérifierPanneauDialogue(MouseEvent e){
    try {
      return Main.getPd().clicEn(e.getX(),e.getY());
    }catch (Exception e2) {
      return false;
    }
  }
}
