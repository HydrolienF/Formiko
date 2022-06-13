package fr.formiko.views.gui2d;

import fr.formiko.usuel.erreur;
import fr.formiko.usuel.maths.math;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JScrollBar;

/**
*{@summary A Jpanel with a JScrollBar.}<br>
*@lastEditedVersion 2.11
*/
public class FPanelScrollBar extends FPanel {
  public static int SCROLL_BAR_SIZE = 20;
  private static int SCROLL_BAR_MAX = 1000;
  private FPanel subPanel;
  private JScrollBar scrollBar;
  private int maxVisibleHeight;

  // CONSTRUCTORS --------------------------------------------------------------
  /**
  *{@summary Main constructor.}<br>
  *Scrool bar stay hiden if we don't need it.
  *@lastEditedVersion 2.11
  */
  public FPanelScrollBar(FPanel subPanel){
    super();
    scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, SCROLL_BAR_MAX, 0, SCROLL_BAR_MAX);
    scrollBar.setVisible(true);
    scrollBar.addAdjustmentListener(new BarAdjustmentListener());
    addMouseWheelListener(new FMouseWheelListener());
    add(scrollBar);
    setSubPanel(subPanel);
  }

  // GET SET -------------------------------------------------------------------
  public void setMaxVisibleHeight(int mvh){maxVisibleHeight=mvh;}
  @Override
  /**
  *{@summary Set the size of this panel, the scrollBar &#38; the sub panel.}<br>
  *Scrool bar stay hiden if h is lower than maxVisibleHeight.
  *@param w the new wigth
  *@param h the new height
  *@lastEditedVersion 2.11
  */
  public void setSize(int w, int h){
    super.setSize(w+SCROLL_BAR_SIZE, Math.min(h,maxVisibleHeight));
    if(h>maxVisibleHeight){
      subPanel.setSize(w, h);
      scrollBar.setLocation(getWidth()-SCROLL_BAR_SIZE,0);
      scrollBar.setSize(SCROLL_BAR_SIZE, getHeight());
      scrollBar.setVisible(true);
      if(h==0){h=1;}
      scrollBar.setVisibleAmount((int)((maxVisibleHeight*SCROLL_BAR_MAX)/h));
    }else{
      subPanel.setSize(w+SCROLL_BAR_SIZE, h);
      scrollBar.setVisible(false);
    }
  }
  /**
  *{@summary Set the sub panel &#38; refresh location.}<br>
  *@lastEditedVersion 2.11
  */
  public void setSubPanel(FPanel p){
    int oldSubPanelHeight=1;
    boolean wasAtTheEnd = false;
    if(subPanel!=null){
      oldSubPanelHeight=subPanel.getHeight();
      wasAtTheEnd = (scrollBar.getValue() + scrollBar.getVisibleAmount()) >= SCROLL_BAR_MAX;
      remove(subPanel);
    }
    subPanel=p;
    if(subPanel==null){return;}
    add(subPanel);
    subPanel.setLocation(0,0);
    setSize(subPanel.getWidth(), subPanel.getHeight());
    //TODO update srcoll bar location when subPanel.getHeight change.
    // double racio = oldSubPanelHeight;
    // if(racio!=1){racio/=(double)subPanel.getHeight();}
    // if(wasAtTheEnd){
    //   erreur.println("set to the end");
    //   setScrollBarValue(SCROLL_BAR_MAX);
    //   erreur.println(scrollBar.getValue() +" "+ scrollBar.getVisibleAmount());
    //   erreur.println(scrollBar);
    // }
    // else{
    //   setScrollBarValue((int)((double)(scrollBar.getValue())*racio));
    // }
    // updatePanelLoactionFromBar();
  }
  /**
  *{@summary Update scrollBar value with only possible value.}<br>
  *@lastEditedVersion 2.11
  */
  public void setScrollBarValue(int x){
    if(x<0){x=0;}
    else if(x+scrollBar.getVisibleAmount() > SCROLL_BAR_MAX){x=SCROLL_BAR_MAX-scrollBar.getVisibleAmount();}
    scrollBar.setValue(x);
  }
  /**
  *{@summary Update panel location depending of bar value.}<br>
  *@lastEditedVersion 2.11
  */
  public void updatePanelLoactionFromBar(){
    int gap = (int)((scrollBar.getValue()*subPanel.getHeight())/SCROLL_BAR_MAX);
    subPanel.setLocation(0,-gap);
  }
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------
  /**
  *{@summary An AdjustmentListener that update panel location.}<br>
  *@lastEditedVersion 2.11
  *@author Hydrolien
  */
  class BarAdjustmentListener implements AdjustmentListener {
    /**
    *{@summary Function call when bar value change.}<br>
    *It update panel location.
    *@lastEditedVersion 2.11
    */
    @Override
    public void adjustmentValueChanged​(AdjustmentEvent e){
      updatePanelLoactionFromBar();
    }
  }
  /**
  *{@summary A MouseWheelListener that update scroll bar value.}<br>
  *@lastEditedVersion 2.11
  *@author Hydrolien
  */
  class FMouseWheelListener implements MouseWheelListener {
    /**
    *{@summary Function call when mouse wheel is moved.}<br>
    *It update scroll bar value.
    *@lastEditedVersion 2.11
    */
    @Override
    public void mouseWheelMoved​(MouseWheelEvent e){
      int unitIncrement = e.getUnitsToScroll();
      double increment = (unitIncrement*0.03*scrollBar.getVisibleAmount());
      if(increment<1 && increment>0){increment=1;}
      else if(increment>-1 && increment<0){increment=-1;}
      setScrollBarValue(scrollBar.getValue() + (int)increment);
    }
  }
}
