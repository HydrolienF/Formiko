package fr.formiko.views.gui2d;

import java.awt.BorderLayout;
import javax.swing.JScrollBar;

public class FPanelScrollBar extends FPanel {
  public static int SCROLL_BAR_SIZE = 20;
  private FPanel subPanel;
  private JScrollBar scrollBar;
  private int maxVisibleHeigth;

  public FPanelScrollBar(FPanel subPanel){
    super();
    this.subPanel=subPanel;
    scrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 100, 0, 100);
    scrollBar.setVisible(true);
    // scrollBar.addAdjustmentListener(new MyAdjustmentListener( ));
    add(scrollBar);
    this.subPanel.setLocation(0,0);
    add(this.subPanel);
    setSize(subPanel.getWidth(), subPanel.getHeight());
  }

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
      int racio = (int)((100*maxVisibleHeigth)/h);
      scrollBar.setVisibleAmount(racio);
    }else{
      subPanel.setSize(w+SCROLL_BAR_SIZE, h);
      scrollBar.setVisible(false);
    }
  }

}
