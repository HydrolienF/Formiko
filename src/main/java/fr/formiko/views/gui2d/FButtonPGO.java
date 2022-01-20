package fr.formiko.views.gui2d;

import fr.formiko.usuel.g;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.function.Supplier;

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
    addMouseMotionListener(new DescMouseMotionListener());
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
    g2d.fillOval(0,0,getWidth()-1,getHeight()-1);
    g2d.setStroke(new BasicStroke(1));
    g2d.setColor(Color.BLACK);
    g2d.drawOval(0,0,getWidth()-1,getHeight()-1);
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
    setSelected(true);
    FPanel.getView().getPj().updateTimeFromLastMove();
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
  /**
  *{@summary set the button selected or not.}<br>
  *Desc are print on the button (mouse located).
  *@param selected true if button is selected.
  *@version 2.10
  */
  @Override
  public void setSelected(boolean selected){
    super.setSelected(selected, true);
  }
  /**
  *{@summary Return a string representing the enabled or not state of the button.}<br>
  *@version 2.10
  */
  public String getDescEnabled(){
    String sr = "";
    if(colId!=-1){return "";}
    if(isYellow){
      sr=g.get("enabled");
    }else{
      sr=g.get("disabled");
    }
    return "("+sr+")";
  }
  /**
  *{@summary Return the description of the button.}<br>
  *@version 2.10
  */
  @Override
  protected String getDesc(){
    String descEnable = getDescEnabled();
    if(!descEnable.equals("")){descEnable=" "+descEnable;}
    return super.getDesc()+descEnable;
  }
}
