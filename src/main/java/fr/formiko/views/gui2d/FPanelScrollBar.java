package fr.formiko.views.gui2d;

import fr.formiko.usuel.erreur;
import fr.formiko.usuel.maths.math;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JScrollBar;

public class FPanelScrollBar extends FPanel {
  public static int SCROLL_BAR_SIZE = 20;
  private FPanel subPanel;
  private JScrollBar scrollBar;
  private int maxVisibleHeigth;

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelScrollBar(FPanel subPanel){
    super();
    scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 1000, 0, 1000);
    scrollBar.setVisible(true);
    scrollBar.addAdjustmentListener(new BarAdjustmentListener());
    addMouseWheelListener(new FMouseWheelListener());
    add(scrollBar);
    setSubPanel(subPanel);
  }

  // GET SET -------------------------------------------------------------------
  public void setMaxVisibleHeigth(int mvh){maxVisibleHeigth=mvh;}
  @Override
  public void setSize(int w, int h){
    super.setSize(w+SCROLL_BAR_SIZE, Math.min(h,maxVisibleHeigth));
    if(h>maxVisibleHeigth){
      subPanel.setSize(w, h);
      scrollBar.setLocation(getWidth()-SCROLL_BAR_SIZE,0);
      scrollBar.setSize(SCROLL_BAR_SIZE, getHeight());
      scrollBar.setVisible(true);
      if(h==0){h=1;}
      scrollBar.setVisibleAmount((int)((maxVisibleHeigth*1000)/h));
    }else{
      subPanel.setSize(w+SCROLL_BAR_SIZE, h);
      scrollBar.setVisible(false);
    }
  }
  public void setSubPanel(FPanel p){
    if(subPanel!=null){remove(subPanel);}
    subPanel=p;
    if(subPanel==null){return;}
    add(subPanel);
    subPanel.setLocation(0,0);
    setSize(subPanel.getWidth(), subPanel.getHeight());
    //TODO update srcoll bar location when subPanel.getHeight change.
  }
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------
  class BarAdjustmentListener implements AdjustmentListener {
    @Override
    public void adjustmentValueChanged​(AdjustmentEvent e){
      int gap = (int)((scrollBar.getValue()*subPanel.getHeight())/1000);
      subPanel.setLocation(0,-gap);
    }
  }
  class FMouseWheelListener implements MouseWheelListener {
    @Override
    public void mouseWheelMoved​(MouseWheelEvent e){
      int unitIncrement = e.getUnitsToScroll();
      double increment = (unitIncrement*0.03*scrollBar.getVisibleAmount());
      if(increment<1 && increment>0){increment=1;}
      else if(increment>-1 && increment<0){increment=-1;}
      scrollBar.setValue(scrollBar.getValue() + (int)increment);
    }
  }
}
