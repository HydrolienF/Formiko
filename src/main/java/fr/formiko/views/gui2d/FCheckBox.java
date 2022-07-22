package fr.formiko.views.gui2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import java.awt.Insets;

/**
*{@summary A simple checkBox using Formiko UI style.}<br>
*@author Hydrolien
*@lastEditedVersion 2.26
*/
public class FCheckBox extends JCheckBox {
  /**
  *{@summary Main constructor using Formiko UI style.}<br>
  *@lastEditedVersion 2.26
  */
  public FCheckBox(){
    super();
    setForeground(Color.BLACK);
    setBackground(new Color(0,0,0,0));
    setBorderPainted(false);
    setOpaque(false); //solve the UI glitching issues
    setContentAreaFilled(false);
    // toggleButton.setPreferredSize(new Dimension(128, 128));
    FontMetrics boxFontMetrics = getFontMetrics(getFont());
    int boxSize=(int)(boxFontMetrics.getHeight()*0.6);
    setIcon(new FCheckBoxIcon(boxSize, false));
    setSelectedIcon(new FCheckBoxIcon(boxSize, true));
  }
  /**
  *{@summary A simple checkBoxIcon using Formiko UI style.}<br>
  *@author Hydrolien
  *@lastEditedVersion 2.28
  */
  class FCheckBoxIcon implements Icon {
    private int size;
    private boolean checked;
    /**
    *{@summary Main constructor.}<br>
    *@lastEditedVersion 2.28
    */
    public FCheckBoxIcon(int size, boolean checked){
      this.size=size;
      this.checked=checked;
    }
    @Override
    public int getIconWidth(){return size;}
    @Override
    public int getIconHeight(){return size;}
    /**
    *{@summary Main function that draw a box &#38; a check if checked is true.}<br>
    *@lastEditedVersion 2.28
    */
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y){
      if(g instanceof Graphics2D){
        ((Graphics2D)g).setStroke(new BasicStroke(getIconWidth()/12.0f));
      }
      g.setColor(Color.WHITE);
      g.fillRect(x,y,getIconWidth(),getIconHeight());
      g.setColor(Color.BLACK);
      g.drawRect(x,y,getIconWidth(),getIconHeight());
      if(checked){
        int size=getIconWidth();
        g.drawLine(x+(int)(size*0.2),y+(int)(size*0.5),x+(int)(size*0.4),y+(int)(size*0.8));
        g.drawLine(x+(int)(size*0.85),y+(int)(size*0.2),x+(int)(size*0.4),y+(int)(size*0.8));
      }
    }
  }
}
