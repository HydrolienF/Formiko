package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;

import java.awt.Color;
import java.awt.Graphics;
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
  private CCase cc2=null;

  // CONSTRUCTORS --------------------------------------------------------------
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
        getView().getPb().setDesc("");
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
            Fourmi f = null;
            try {
              f = gc.getFourmiParFere(Main.getPlayingJoueur().getFere());
            }catch (NullPointerException e2) {
              System.out.println("NO curent player.");//@a
            }
            if(f!=null){ // && f.getAction()>0
              getView().getPb().setActionF(-2);
              getView().getPb().removePA();
              Main.getPartie().setAntIdToPlay(f.getId());
              // if(f.isAutoMode() && getView().getEndTurnAuto() && f.getFere().getGc().haveDoneAllActionAviable()){ //si fourmi en auto mode, mode fin tour auto & tour fini.
              //   erreur.alerte("WAIIIIIIIIITTTTTT for disable end turn");
              // }
            }
          }
        }else if(e.getButton()== MouseEvent.BUTTON3){
          Fourmi f = Main.getPlayingAnt();
          if(f!=null){//si une fourmi est séléctionné.
            CCase cc = getCCase(e);
            if(cc!=null){
              f.ceDeplacerPlusieurCase(cc);
              f.setBActionHaveChange(true);
            }
          }
        }
      }
    });
    addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent e) {
        CCase cc = getCCase(e);
        mouseMovedUpdate(cc);
      }
    });
  }
  // GET SET -------------------------------------------------------------------
  public void actualiserTaille(){
    if(getView().getPd()!= null && getView().getPd().getNeedToStayMaxSize()){actualiserTailleMax(); return;}
    setSize(Main.getDimX()-getView().getPz().getWidth(), Main.getDimY()-getView().getPa().getHeight());
    //la 2a version est mieux pour prendre en compte les déplacements.
    //setSize(Main.getDimX()-Main.getPz().getWidth(), Main.getDimY()-math.max(getView().getPa().getHeight(),Main.getPTInt().getHeight()));
  }
  public void actualiserTailleMax(){
    setSize(Main.getDimX(), Main.getDimY());
  }
  public void actualiserTailleMin(){
    setSize(0,0);
  }
  // public int getAntIdToPlay(){return antIdToPlay;}
  // public void setAntIdToPlay(int x){antIdToPlay=x;
  //   erreur.info("lets play "+x);
  // }
  // FUNCTIONS -----------------------------------------------------------------
  public void paintComponent(Graphics g){
    //do nothing
  }
  public CCase getCCase(MouseEvent e){
    return getCCase(e.getX(), e.getY());
  }
  public CCase getCCase(int x, int y){
    int tc = getView().getPc().getTailleDUneCase();
    int cx = x/tc;
    int cy = y/tc;
    try {
      return Main.getGc().getCCase(cx+getView().getPc().getPosX(),cy+getView().getPc().getPosY());
    }catch (Exception e2) {
      erreur.erreur("aucune case n'est sélectionné avec les coordonées : "+cx+" "+cy);
      return null;
    }
  }
  public Case getCase(MouseEvent e){
    try {
      return getCCase(e).getContent();
    }catch (Exception e2) {
      return null;
    }
  }
  public boolean vérifierPanneauDialogue(MouseEvent e){
    try {
      return getView().getPd().clicEn(e.getX(),e.getY());
    }catch (Exception e2) {
      return false;
    }
  }
  public void mouseMovedUpdate(CCase cc){
    if(cc==null){getView().getPb().setDesc("");cc2=null;return;}
    if(cc2==null || !cc2.getContent().equals(cc.getContent())){//si la case a changé.
      cc2=new CCase(cc.getContent());
      getView().setLookedCCase(cc);
    }
  }
}
