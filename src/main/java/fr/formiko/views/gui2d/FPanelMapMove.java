package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Temps;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.structures.listes.Liste;
import fr.formiko.views.gui2d.FPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.Thread;
import java.util.List;

/**
*{@summary Panel that make map move.}<br>
*@author Hydrolien
*@version 2.12
*/
public class FPanelMapMove extends FPanel {
  private Liste<FPanel> lPanelToMove;
  // private ThMove thMove;
  private Thread th;
  private boolean runningInX;
  private boolean runningInY;
  private int thickness;
  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main empty constructor.}<br>
  *@version 2.12
  */
  public FPanelMapMove(){
    lPanelToMove = new Liste<FPanel>();
  }

  public void addSubPanel(FPanel panelToMove){
    lPanelToMove.add(panelToMove);
  }

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
  }
  public void setOver(boolean inX, boolean up, boolean start){
    if(!getView().getActionGameOn() || getView().getPch()!=null){return;}
    if(start){
      int stepValue=thickness/8;
      final int step;
      if(up){step=-stepValue;}
      else{step=stepValue;}
      if(inX){
        th = new Thread(){
          @Override
          public void run(){
            runningInX=true;
            while(runningInX){
              for (FPanel panelToMove : lPanelToMove) {
                panelToMove.setLocation(panelToMove.getX()+step, panelToMove.getY());
              }
              Temps.pause(10, this);
            }
          }
        };
      }else{
        th = new Thread(){
          @Override
          public void run(){
            runningInY=true;
            while(runningInY){
              for (FPanel panelToMove : lPanelToMove) {
                panelToMove.setLocation(panelToMove.getX(), panelToMove.getY()+step);
              }
              Temps.pause(10, this);
            }
          }
        };
      }
      th.start();
    }else{
      if(inX){
        runningInX=false;
      }else{
        runningInY=false;
      }
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
          setOver(inX, up, true);
        }
        @Override
        public void mouseExited(MouseEvent event) {
          setOver(inX, up, false);
        }
        @Override
        public void mouseClicked(MouseEvent event) {}
        @Override
        public void mousePressed(MouseEvent event) {}
        @Override
        public void mouseReleased(MouseEvent event) {}
      });
    }
  }
  //TODO #430 be able to update x or y down or up from 1 pixel
  // class ThMove extends Thread {
  //   private List<Consumer> list;
  //   @Override
  //   public void run(){
  //     for (Function f : list) {
  //       f.apply();
  //     }
  //     Temps.pause(20);
  //   }
  // }
}
