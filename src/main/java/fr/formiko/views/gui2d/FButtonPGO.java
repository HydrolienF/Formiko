package fr.formiko.views.gui2d;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;
import java.awt.Graphics2D;
import java.awt.Graphics;

/**
*{@summary Extends of FButton with some added functions to defined color &#38; draw a circle button.}<br>
*@author Hydrolien
*@version 2.10
*/
public class FButtonPGO extends FButton {
  private Supplier supplier;
  private byte colId;
  /**
  *{@summary Main constructor.}<br>
  *It remove rectangle background to draw a circle one.
  *@param action the action of the button (between 350 &#38; 399)
  *@param icon the image of the button
  *@param supplier a function that return a boolean (is enable), or a int/byte corresponding to a color id
  *@version 2.10
  */
  public FButtonPGO(int action, BufferedImage icon, Supplier supplier){
    super(null, null, action, icon);
    this.supplier=supplier;
    setWithBackground(false);
    colId=-1;
    updateColor();
  }
  /**
  *{@summary Paint function.}<br>
  *It draw a colored circle background &#38; the image if not null.
  *@version 2.10
  */
  @Override
  public void paintComponent(Graphics g){
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(getBackgroundColor());
    g2d.fillOval(0,0,getWidth(),getHeight());
    super.paintComponent(g);
  }
  /**
  *{@summary Update color at every clics.}<br>
  *@version 2.10
  */
  @Override
  public void mouseReleased(MouseEvent event) {
    super.mouseReleased(event);
    updateColor();
  }
  /**
  *{@summary Update color depending of supplier return value.}
  *Supplier can return a boolean (is enable), or a int/byte corresponding to a color id.
  *@version 2.10
  */
  public void updateColor(){
    Object o = supplier.get();
    if(o instanceof Boolean) {
      setIsYellow(((Boolean)(o)));
    }else if(o instanceof Integer || o instanceof Byte) {
      colId = ((Byte)(o)).byteValue();
    }
    setDefaultColor();
  }
  /**
  *{@summary Swap color beween green &#38; yellow or other one if colorId have been set.}<br>
  *@version 2.10
  */
  @Override
  public void setDefaultColor(){
    if(colId<0){super.setDefaultColor();}
    else{setColor(colId);}
  }
}
