package fr.formiko.views.gui2d;

import fr.formiko.formiko.Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;

/**
*{@summary the Border class for Formiko.}
*@author Hydrolien
*@lastEditedVersion 2.2
*/
public class FBorder extends AbstractBorder {
  private Color color;
  private int thickness;
  // CONSTRUCTORS --------------------------------------------------------------
  public FBorder(){
    thickness = Main.getBorderButtonSize();
  }
  // GET SET -------------------------------------------------------------------
  public Color getColor() {return color;}
	public void setColor(Color color) {this.color=color;}
	public int getThickness() {return thickness;}
	public void setThickness(int thickness) {this.thickness=thickness;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary main function that paint the border.}
  *@param c the component for which this border is being painted
  *@param g the paint graphics
  *@param x the x position of the painted border
  *@param y the y position of the painted border
  *@param width the width of the painted border
  *@param height the height of the painted border
  *@lastEditedVersion 2.2
  */
  public void paintBorder​(Component c, Graphics g, int x, int y, int width, int height){
    Graphics2D g2d = (Graphics2D)g;
    g2d.setColor(color);
    if(thickness<1){return;}
    BasicStroke ligne = new BasicStroke(thickness);
    g2d.setStroke(ligne);
    g2d.drawRect( thickness/2, thickness/2, c.getWidth()-thickness, c.getHeight()-thickness);
    //TODO #399 use cuve to do different Border cf https://docs.oracle.com/en/java/javase/16/docs/api/java.desktop/java/awt/geom/Path2D.Double.html#curveTo(double,double,double,double,double,double)
  }
}
