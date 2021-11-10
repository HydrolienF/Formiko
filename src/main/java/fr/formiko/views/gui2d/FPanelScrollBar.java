package fr.formiko.views.gui2d;

import fr.formiko.usuel.erreur;

import java.awt.BorderLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;

public class FPanelScrollBar extends FPanel {
  public static int SCROLL_BAR_SIZE = 20;
  private FPanel subPanel;
  private JScrollBar scrollBar;
  private int maxVisibleHeigth;

  // CONSTRUCTORS --------------------------------------------------------------
  public FPanelScrollBar(FPanel subPanel){
    super();
    this.subPanel=subPanel;
    System.out.println("new JScrollBar");
    scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 100, 0, 100);
    scrollBar.setVisible(true);
    scrollBar.addAdjustmentListener(new BarAdjustmentListener());
    add(scrollBar);
    this.subPanel.setLocation(0,0);
    add(this.subPanel);
    setSize(subPanel.getWidth(), subPanel.getHeight());
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
      // scrollBar.setVisibleAmount((int)(getRacio()*100.0));
      scrollBar.setVisibleAmount((int)((maxVisibleHeigth*100)/h));
    }else{
      subPanel.setSize(w+SCROLL_BAR_SIZE, h);
      scrollBar.setVisible(false);
    }
  }
  // FUNCTIONS -----------------------------------------------------------------

  // SUB-CLASS -----------------------------------------------------------------
  class BarAdjustmentListener implements AdjustmentListener {
    @Override
    public void adjustmentValueChanged​(AdjustmentEvent e){
      int gap = (int)((scrollBar.getValue()*maxVisibleHeigth)/100);
      // erreur.info("gap = "+gap);
      subPanel.setLocation(0,-gap);
    }
  }
}
