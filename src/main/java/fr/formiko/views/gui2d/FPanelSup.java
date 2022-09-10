package fr.formiko.views.gui2d;

import fr.formiko.formiko.*;
import fr.formiko.usual.Time;
import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;
import fr.formiko.usual.g;
import fr.formiko.usual.maths.math;

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
  private CSquare cc2=null;

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
  *<li>If moved on an other Square, it update desc to newSquare.toString().
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
        if(getView().getPe().isOn()){return;}
        if(e.getButton()== MouseEvent.BUTTON1){
          if(getView().getMoveMode()){
            // getView().setCSquare(getCSquare(e));
            movePlayingAnt(e);
            getView().setMoveMode(false);
            // f.ceDeplacer(f.getFere().getJoueur().getIa());
            return;
          }
          Square c = getSquare(e);
          if(c==null){return;}
          GCreature gc = c.getGc();
          if(gc.length()>0){
            Fourmi f = null;
            if(Main.getPlayingJoueur()!=null){
              f = gc.getFourmiParFere(Main.getPlayingJoueur().getFere());
            }else{
              erreur.alerte("No curent player");
            }
            if(f!=null){
              getView().setNextPlayingAnt(f);
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
        if(getView().getPe().isOn()){return;}
        CSquare cc = getCSquare(e);
        mouseMovedUpdate(cc);
      }
    });
  }
  // GET SET -------------------------------------------------------------------
  /**
  *{@summary Update size to fit FPanelCarte.}<br>
  *@lastEditedVersion 2.19
  */
  public void updateSize(){
    if(getView().getPd()!= null && getView().getPd().getNeedToStayMaxSize()){updateSizeMax(); return;}
    // setSize(Main.getDimX()-getView().getPz().getWidth(), Main.getDimY()-getView().getPa().getHeight());
    int paWidth=0;
    if(getView().getPa()!=null){
      paWidth=Main.getDimY()-getView().getPa().getHeight();
    }
    setSize(math.min(Main.getDimX(), getView().getPc().getWidth()-getView().getPc().getX()), paWidth);
    //la 2a version est mieux pour prendre en compte les déplacements.
    //setSize(Main.getDimX()-Main.getPz().getWidth(), Main.getDimY()-math.max(getView().getPa().getHeight(),Main.getPTInt().getHeight()));
  }
  /**
  *{@summary Update size to max one.}<br>
  *@lastEditedVersion 2.19
  */
  public void updateSizeMax(){
    setSize(Main.getDimX(), Main.getDimY());
  }
  /**
  *{@summary Update size to 0,0.}<br>
  *@lastEditedVersion 2.19
  */
  public void updateSizeMin(){
    setSize(0,0);
  }
  // public int getAntIdToPlay(){return antIdToPlay;}
  // public void setAntIdToPlay(int x){antIdToPlay=x;
  //   erreur.info("lets play "+x);
  // }
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Paint nothing.}<br>
  *@lastEditedVersion 2.19
  */
  @Override
  public void paintComponent(Graphics g){
    //do nothing
    // g.setColor(new Color(0,0,100,200));
    // g.fillRect(0,0,getWidth(),getHeight());
  }
  public CSquare getCSquare(MouseEvent e){
    return getCSquare(e.getX(), e.getY());
  }
  public CSquare getCSquare(int x, int y){
    int tc = getView().getPc().getTailleDUneSquare();
    int cx = x/tc;
    int cy = y/tc;
    try {
      return Main.getGc().getCSquare(cx,cy);
    }catch (Exception e2) {
      erreur.erreur("aucune case n'est sélectionné avec les coordonées : "+cx+" "+cy);
      return null;
    }
  }
  public Square getSquare(MouseEvent e){
    try {
      return getCSquare(e).getContent();
    }catch (Exception e2) {
      return null;
    }
  }
  public void mouseMovedUpdate(CSquare cc, boolean force){
    if(getView().getPe().isOn()){return;}
    if(cc==null){getView().setMessageDesc("");cc2=null;return;}
    if(force || cc2==null || !cc2.getContent().equals(cc.getContent())){//si la case a changé.
      cc2=new CSquare(cc.getContent(), cc.getGc());
      getView().setLookedCSquare(cc);
    }
  }
  public void mouseMovedUpdate(CSquare cc){mouseMovedUpdate(cc, false);}
  /**
  *{@summary Move the playing ant to selected Square.}<br>
  *@lastEditedVersion 2.11
  */
  private void movePlayingAnt(MouseEvent e){
    Fourmi f = Main.getPlayingAnt();
    if(f!=null){//si une fourmi est séléctionné.
      CSquare cc = getCSquare(e);
      if(cc!=null){
        f.ceDeplacerPlusieurSquare(cc);
        // f.setBActionHaveChange(true);
        getView().getPb().setActionF(-1);
      }
    }
  }
}
