package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.views.gui2d.FPanel;
import fr.formiko.usuel.maths.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.Thread;
import java.util.List;

/**
*{@summary Panel that make map move.}<br>
*@author Hydrolien
*@version 2.13
*/
public class FPanelMapMove extends FPanel {
  private Liste<FPanel> lPanelToMove;
  private ThMoveSubPanel th;
  private int spaceInX;
  private int spaceInY;
  private int thickness;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main empty constructor.}<br>
  *@version 2.13
  */
  public FPanelMapMove(){
    lPanelToMove = new Liste<FPanel>();
  }

  // GET SET -------------------------------------------------------------------
  public void addSubPanel(FPanel panelToMove){lPanelToMove.add(panelToMove);}
  public int getSpaceInX(){return spaceInX;}
  public void setSpaceInX(int x){spaceInX=x;}
  public int getSpaceInY(){return spaceInY;}
  public void setSpaceInY(int x){spaceInY=x;}

  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Initialize all sub components &#38; variables.}<br>
  *@version 2.13
  */
  public void build(){
    thickness = Main.getTailleElementGraphique(10); //thin moving area are a bit hard to use without fullscreen.
    int pjw = FPanel.getView().getPj().getWidth();
    int pjh = FPanel.getView().getPj().getHeight();
    FPanel plm = new FPanelListenMove(false, false); //top
    plm.setBounds(thickness, 0, pjw-(2*thickness), thickness);
    add(plm);
    plm = new FPanelListenMove(true, false);
    plm.setBounds(0, thickness, thickness, pjh-(2*thickness));
    add(plm);
    plm = new FPanelListenMove(true, true);
    plm.setBounds(pjw-thickness, thickness, thickness, pjh-(2*thickness));
    add(plm);
    plm = new FPanelListenMove(false, true); //bottom
    plm.setBounds(thickness, pjh-thickness, pjw-(2*thickness), thickness);
    add(plm);

    plm = new FPanelListenMove(false, false, true, false);
    plm.setBounds(0, 0, thickness, thickness);
    add(plm);
    plm = new FPanelListenMove(false, false, true, true);
    plm.setBounds(pjw-thickness, 0, thickness, thickness);
    add(plm);
    plm = new FPanelListenMove(true, true, true, true);
    plm.setBounds(pjw-thickness, pjh-thickness, thickness, thickness);
    add(plm);
    plm = new FPanelListenMove(false, true, true, false);
    plm.setBounds(0, pjh-thickness, thickness, thickness);
    add(plm);

    spaceInX=getView().getPj().getWidth();
    spaceInY=getView().getPj().getHeight();
    th = new ThMoveSubPanel();
    th.start();
  }
  /**
  *{@summary Launch or stop move in thread.}<br>
  *@param inX true if we are moving in x, false if we are moving in y
  *@param up true if windows should up in X or in Y
  *@param speed moving speed in [0;2]
  *@version 2.13
  */
  public void setOver(boolean inX, boolean up, double speed){
    if(!getView().getActionGameOn() || getView().getPch()!=null){return;}
    int step=(int)((double)thickness*speed);
    if(up){step=-step;}
    if(inX){
      th.setStepInX(step);
    }else{
      th.setStepInY(step);
    }
    if(speed!=0){
      th.needToWait=false;
    }
  }
  /**
  *{@summary Move all sub panel in x &#38; y.}<br>
  *@param stepInX moving speed in x
  *@param stepInY moving speed in y
  *@version 2.13
  */
  public void moveAllSubPanel(int stepInX, int stepInY){
    for (FPanel panelToMove : lPanelToMove) {
      setLocationSubPanel(panelToMove, panelToMove.getX()+stepInX, panelToMove.getY()+stepInY);
    }
  }
  /**
  *{@summary Center all sub panel in x &#38; y.}<br>
  *@param x x that need to be on the center
  *@param y y that need to be on the center
  *@version 2.14
  */
  public void centerOver(int x, int y){
    x-=getWidth()/2;
    y-=getHeight()/2;
    for (FPanel panelToMove : lPanelToMove) {
      setLocationSubPanel(panelToMove,-x,-y);
    }
  }
  /**
  *{@summary Set location of a sub panel in x &#38; y.}<br>
  *@param panelToMove panel to move
  *@param x new x
  *@param y new y
  *@version 2.14
  */
  public void setLocationSubPanel(FPanel panelToMove, int x, int y){
    int maxX = math.max(panelToMove.getWidth()-spaceInX,0);
    int maxY = math.max(panelToMove.getHeight()-spaceInY, 0);
    panelToMove.setLocation(math.between(-maxX, 0, x), math.between(-maxY, 0, y));
  }

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary Panel used to listen mouse move.}<br>
  *The mouse listeners will update speed depending of how close of the border we are.
  *@author Hydrolien
  *@version 2.13
  */
  class FPanelListenMove extends FPanel {
    private boolean inX;
    private boolean up;
    /**
    *{@summary Main constructor with mouse listeners.}<br>
    *@param inX true if we are moving in x
    *@param up true if we are moving up
    *@param both true if we are moving in x & in y
    *@param up true if we are moving up for the 2a direction
    *If this is big enoth, the mouse listeners will update speed depending of how close of the border we are.
    * Or stop it if we leave the panel.
    *@version 2.13
    */
    public FPanelListenMove(boolean inX, boolean up, boolean both, boolean up2){
      this.inX=inX;
      this.up=up;
      addMouseListener(new MouseListener(){
        /**
        *{@summary Start moving.}<br>
        *@version 2.13
        */
        @Override
        public void mouseEntered(MouseEvent event) {
          if(both){
            setOver(!inX, up2, 1.0);
          }
          setOver(inX, up, 1.0);
        }
        /**
        *{@summary Stop moving.}<br>
        *@version 2.13
        */
        @Override
        public void mouseExited(MouseEvent event) {
          if(both){
            setOver(!inX, up2, 0);
          }
          setOver(inX, up, 0);
        }
        @Override
        public void mouseClicked(MouseEvent event) {}
        @Override
        public void mousePressed(MouseEvent event) {}
        @Override
        public void mouseReleased(MouseEvent event) {}
      });
      if(thickness>Main.getTailleElementGraphique(20)){
        addMouseMotionListener(new MouseMotionListener(){
          /**
          *{@summary Update speed depending of how close of the border we are.}<br>
          *@version 2.13
          */
          @Override
          public void mouseMoved(MouseEvent event) {
            double speed=1.0;
            int val=0;
            if(inX){
              val=event.getX();
              if(up){val=val-getWidth()+thickness;}
            }else{
              val=event.getY();
              if(up){val=val-getHeight()+thickness;}
            }
            if(up){
              speed = (double)(val)/(double)(thickness);
            }else{
              speed = 1 - (double)(val)/(double)(thickness);
            }
            // if(val==0 || val==getWidth()-1 || val==getHeight()-1){speed*=1.5;}
            if(both){ //TODO speed used is or X or Y, it should be a different 1 for x & y.
              setOver(!inX, up2, speed);
            }
            setOver(inX, up, speed);
          }
          @Override
          public void mouseDragged(MouseEvent event) {}
        });
      }
    }
    /**
    *{@summary Main constructor with mouse listeners.}<br>
    *@param inX true if we are moving in x
    *@param up true if we are moving up
    *If this is big enoth, the mouse listeners will update speed depending of how close of the border we are.
    * Or stop it if we leave the panel.
    *@version 2.13
    */
    public FPanelListenMove(boolean inX, boolean up){
      this(inX, up, false, false);
    }
  }
  /**
  *{@summary Thread used to do the move.}<br>
  *@author Hydrolien
  *@version 2.13
  */
  class ThMoveSubPanel extends Thread {
    private int stepInX;
    private int stepInY;
    private volatile boolean needToWait;
    public void setStepInX(int x){stepInX=x;}
    public void setStepInY(int x){stepInY=x;}
    /**
    *{@summary Main constructor.}<br>
    *@version 2.13
    */
    public ThMoveSubPanel(){
      stepInX=0;
      stepInY=0;
    }
    /**
    *{@summary Main methode that make sub panel move.}<br>
    * It will wait untill a stepInX or stepInY is not 0
    * &#38; then move every 10 ms.
    *@version 2.13
    */
    @Override
    public void run(){
      needToWait=false;
      while(true){
        while(needToWait){
          Thread.onSpinWait();
        }
        needToWait=(stepInX==0 && stepInY==0);
        moveAllSubPanel(stepInX, stepInY);
        Temps.pause(10);
      }
    }
  }
}
