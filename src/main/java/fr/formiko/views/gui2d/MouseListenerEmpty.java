package fr.formiko.views.gui2d;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
/**
*{@summary A mouse listener that do nothing.}
* It can be used to implements only 1 function without writing a lot of code.
*@lastEditedVersion 2.15
*/
class MouseListenerEmpty implements MouseListener {
  @Override
  public void mouseClicked(MouseEvent event) {}
  @Override
  public void mouseEntered(MouseEvent event) {}
  @Override
  public void mouseExited(MouseEvent event) {}
  @Override
  public void mousePressed(MouseEvent event) {}
  @Override
  public void mouseReleased(MouseEvent event) {}
}
