package fr.formiko.views.gui2d;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
*{@summary Listener that update description when it need.}<br>
*@version 2.10
*/
public class DescMouseMotionListener implements MouseMotionListener {
  /**
  *{@summary Update mouse location to hide the message.}<br>
  *@version 2.10
  */
  @Override
  public void mouseMoved(MouseEvent event){
    FPanel.getView().getPj().updateTimeFromLastMove();
  }
  @Override
  public void mouseDragged​(MouseEvent e){}
}