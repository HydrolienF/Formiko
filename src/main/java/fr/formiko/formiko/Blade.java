package fr.formiko.formiko;

import fr.formiko.usuel.Point;

import java.util.Random;
import java.awt.Graphics;
import java.awt.Color;

/**
*{@summary Grass blade is used to represent grass on map.}<br>
*@author Hydrolien
*@version 2.16
*/
public class Blade extends Point {
  private double angle;
  // private byte direction; //0 to 90
  // private byte length; //0 to 100
  private static Random rand;
  private static Color col = new Color(0,142,14);
  /**
  *{@summary Main contructor with random direction &#38; random location.}<br>
  *@version 2.16
  */
  public Blade(){
    super(0,0); //TODO avoid double init°.
    if(rand==null){rand=new Random();}
    // direction = (byte)rand.nextInt(91);
    angle = Math.toRadians(45+rand.nextInt(90));
    x = rand.nextInt(100);
    y = rand.nextInt(100);
  }
  // public byte getDirection(){return direction;}
  private byte getLength(){return 4;}

  /**
  *{@summary Main function that draw this on g.}<br>
  *@param g Graphics where to draw
  *@param xOffset Case offset in x
  *@param yOffset Case offset in y
  *@version 2.16
  */
  public void draw(Graphics g, int xOffset, int yOffset){
    g.setColor(col);
    g.drawLine(xOffset+x, yOffset+y, (int)(getLength()*Math.cos(angle)), (int)(getLength()*Math.sin(angle)));
  }
}
