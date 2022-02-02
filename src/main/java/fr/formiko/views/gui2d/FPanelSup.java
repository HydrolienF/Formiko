package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.maths.math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
*{@summary Map mouse listener panel.}<br>
*@author Hydrolien
*@lastEditedVersion 1.42
*/
public class FPanelSup extends FPanel {
  private CCase cc2=null;

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelSup(){}
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
  *@lastEditedVersion 1.42
  */
  public void build(){
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseExited​(MouseEvent e){
        getView().setMessageDesc("");
      }
      @Override
      public void mouseReleased(MouseEvent e) {
        if(e.getButton()== MouseEvent.BUTTON1){
          if(vérifierFPanelDialogue(e)){return;}
          if(getView().getMoveMode()){
            // getView().setCCase(getCCase(e));
            movePlayingAnt(e);
            getView().setMoveMode(false);
            // f.ceDeplacer(f.getFere().getJoueur().getIa());
            return;
          }
          Case c = getCase(e);
          if(c==null){return;}
          GCreature gc = c.getGc();
          if(gc.length()>0){
            Fourmi f = null;
            try {
              f = gc.getFourmiParFere(Main.getPlayingJoueur().getFere());
            }catch (NullPointerException e2) {
              erreur.alerte("No curent player");
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
          if(getView().getMoveMode()){
            getView().setMoveMode(false);
          }else{
            movePlayingAnt(e);
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
    // setSize(Main.getDimX()-getView().getPz().getWidth(), Main.getDimY()-getView().getPa().getHeight());
    setSize(math.min(Main.getDimX(), getView().getPc().getWidth()-getView().getPc().getX()), Main.getDimY()-getView().getPa().getHeight());
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
  @Override
  public void paintComponent(Graphics g){
    //do nothing
    // g.setColor(new Color(100,100,100,100));
    // g.fillRect(0,0,getWidth(),getHeight());
  }
  public CCase getCCase(MouseEvent e){
    return getCCase(e.getX(), e.getY());
  }
  public CCase getCCase(int x, int y){
    int tc = getView().getPc().getTailleDUneCase();
    int cx = x/tc;
    int cy = y/tc;
    try {
      return Main.getGc().getCCase(cx,cy);
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
  public boolean vérifierFPanelDialogue(MouseEvent e){
    try {
      return getView().getPd().clicEn(e.getX(),e.getY());
    }catch (Exception e2) {
      return false;
    }
  }
  public void mouseMovedUpdate(CCase cc, boolean force){
    if(cc==null){getView().setMessageDesc("");cc2=null;return;}
    if(force || cc2==null || !cc2.getContent().equals(cc.getContent())){//si la case a changé.
      cc2=new CCase(cc.getContent());
      getView().setLookedCCase(cc);
    }
  }
  public void mouseMovedUpdate(CCase cc){mouseMovedUpdate(cc, false);}
  /**
  *{@summary Move the playing ant to selected Case.}<br>
  *@lastEditedVersion 2.11
  */
  private void movePlayingAnt(MouseEvent e){
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
