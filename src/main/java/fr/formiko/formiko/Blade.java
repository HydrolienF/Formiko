package fr.formiko.formiko;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Point;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.Random;

/**
*{@summary Vegetal blade is used to represent grass &#38; moss on map.}<br>
*@author Hydrolien
*@version 2.16
*/
public abstract class Blade extends Point implements Serializable {
  private double angle;
  // private byte direction; //0 to 90
  // private byte length; //0 to 100
  private static Random rand;
  private final static int avoidBorder=10;
  // private static Color col = new Color(14,138,22);
  // private static Color col = new Color(11,93,16); //moss
  /**
  *{@summary Return a new Blade of given type.}<br>
  *@version 2.16
  */
  public static Blade newBlade(byte type){
    switch(type){
      case 2:
        return new BladeMoss();
      case 3:
        return new BladeSand();
      default :
        return new BladeGrass();
    }
  }
  /**
  *{@summary Main contructor with random direction &#38; random location.}<br>
  *@version 2.16
  */
  protected Blade(){
    super(0,0); //TODO avoid double init°.
    if(rand==null){rand=new Random();}
    // direction = (byte)rand.nextInt(91);
    angle = Math.toRadians(-90-45+rand.nextInt(90));
    x = rand.nextInt(100-(2*avoidBorder))+avoidBorder;
    y = rand.nextInt(100-(2*avoidBorder))+avoidBorder;
  }
  // public byte getDirection(){return direction;}
  abstract byte getLength();
  abstract Color getColor();

  /**
  *{@summary Main function that draw this on g.}<br>
  *@param g Graphics where to draw
  *@param xOffset Case offset in x
  *@param yOffset Case offset in y
  *@version 2.16
  */
  public void draw(Graphics g, int xOffset, int yOffset){
    int xT = xOffset+x;
    int yT = yOffset+y;
    int bLen = getLength()*Main.getData().getTailleDUneCase()/100;
    g.setColor(getColor());
    ((Graphics2D)g).setStroke(new BasicStroke(1));
    g.drawLine(xT, yT, xT+(int)(bLen*Math.cos(angle)), yT+(int)(bLen*Math.sin(angle)));
  }
}
