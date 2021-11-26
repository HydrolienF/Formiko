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
*@version 2.12
*/
public class FPanelMapMove extends FPanel {
  private Liste<FPanel> lPanelToMove;
  private ThMove th;
  private int spaceInX;
  private int spaceInY;
  private int thickness;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main empty constructor.}<br>
  *@version 2.12
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
  public void build(){
    thickness = Main.getTailleElementGraphique(50);
    int pjw = FPanel.getView().getPj().getWidth();
    int pjh = FPanel.getView().getPj().getHeight();
    FPanel plm = new FPanelListenMove(false, false);
    plm.setSize(pjw, thickness);
    add(plm);
    plm = new FPanelListenMove(true, false);
    plm.setSize(thickness, pjh);
    add(plm);
    plm = new FPanelListenMove(true, true);
    plm.setBounds(pjw-thickness, 0, thickness, pjh);
    add(plm);
    plm = new FPanelListenMove(false, true);
    plm.setBounds(0, pjh-thickness, pjw, thickness);
    add(plm);
    spaceInX=getView().getPj().getWidth();
    spaceInY=getView().getPj().getHeight();
    th = new ThMove();
    th.start();
  }
  public void setOver(boolean inX, boolean up, double speed){
    if(!getView().getActionGameOn() || getView().getPch()!=null){return;}
    int step=(int)((double)thickness*speed)/8;
    if(up){step=-step;}
    if(inX){
      th.setStepInX(step);
    }else{
      th.setStepInY(step);
    }
  }
  public void moveAllSubPanel(int stepInX, int stepInY){
    for (FPanel panelToMove : lPanelToMove) {
      int maxX = math.max(panelToMove.getWidth()-spaceInX,0);
      int maxY = math.max(panelToMove.getHeight()-spaceInY, 0);
      panelToMove.setLocation(math.between(-maxX, 0, panelToMove.getX()+stepInX),
            math.between(-maxY, 0, panelToMove.getY()+stepInY));
    }
  }

  // SUB-CLASS -----------------------------------------------------------------
  class FPanelListenMove extends FPanel {
    private boolean inX;
    private boolean up;
    public FPanelListenMove(boolean inX, boolean up){
      this.inX=inX;
      this.up=up;
      addMouseListener(new MouseListener(){
        @Override
        public void mouseEntered(MouseEvent event) {
          setOver(inX, up, 1.0);
        }
        @Override
        public void mouseExited(MouseEvent event) {
          setOver(inX, up, 0);
        }
        @Override
        public void mouseClicked(MouseEvent event) {}
        @Override
        public void mousePressed(MouseEvent event) {}
        @Override
        public void mouseReleased(MouseEvent event) {}
      });
      addMouseMotionListener(new MouseMotionListener(){
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
          setOver(inX, up, speed);
        }
        @Override
        public void mouseDragged(MouseEvent event) {}
      });
    }
  }
  class ThMove extends Thread {
    private int stepInX;
    private int stepInY;
    public void setStepInX(int x){stepInX=x;}
    public void setStepInY(int x){stepInY=x;}
    public ThMove(){
      stepInX=0;
      stepInY=0;
    }
    @Override
    public void run(){
      while(true){
        moveAllSubPanel(stepInX, stepInY);
        try {
          wait();
        }catch (Exception e) {}
      }
    }
  }
}
