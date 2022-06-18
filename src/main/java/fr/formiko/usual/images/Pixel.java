package fr.formiko.usual.images;

import fr.formiko.usual.types.str;

import java.awt.Color;

/**
*{@summary Pixel class used by images package.}<br>
*@author Hydrolien
*@lastEditedVersion 1.39
*/
public class Pixel {
  private byte r;
  private byte g;
  private byte b;
  private byte a;
  // CONSTRUCTEUR --------------------------------------------------------------
  /**
  *{@summary Main constructor private. Use the int 1 to create a Pixel.}<br>
  *@param r Red value between -128 &#38; 127.
  *@param g Green value between -128 &#38; 127.
  *@param b Blue value between -128 &#38; 127.
  *@param a Alpha value between -128 &#38; 127.
  *@lastEditedVersion 1.39
  */
  private Pixel(byte r, byte g, byte b, byte a){
    this.r=r; this.g=g; this.b=b; this.a=a;
  }
  /**
  *{@summary Main constructor with int.}<br>
  *@param r Red value between 0 &#38; 255.
  *@param g Green value between 0 &#38; 255.
  *@param b Blue value between 0 &#38; 255.
  *@param a Alpha value between 0 &#38; 255.
  *@lastEditedVersion 1.39
  */
  public Pixel(int r, int g, int b, int a){
    this(str.iToBy(r-128),str.iToBy(g-128),str.iToBy(b-128),str.iToBy(a-128));
  }
  /**
  *{@summary Secondary constructor.}<br>
  *It will set Alpha value to 255 (maximum value).
  *@param r Red value between 0 &#38; 255.
  *@param g Green value between 0 &#38; 255.
  *@param b Blue value between 0 &#38; 255.
  *@lastEditedVersion 1.39
  */
  public Pixel(int r, int g, int b){
    this(r,g,b,255);
  }
  /**
  *{@summary Secondary constructor that use Pheromone.}<br>
  *@param ph Pheromone of the Creature to colored. Pheromone have 3 color value: red, green, blue.
  *@lastEditedVersion 1.39
  */
  // public Pixel(Pheromone ph){
  //   this((byte)ph.getR(),(byte)ph.getG(),(byte)ph.getB(),(byte)127);
  // }
  // GET SET -------------------------------------------------------------------
  public byte getR(){return r;}
  public byte getG(){return g;}
  public byte getB(){return b;}
  public byte getA(){return a;}
  public void setR(byte x){ r=x;}
  public void setG(byte x){ g=x;}
  public void setB(byte x){ b=x;}
  public void setA(byte x){ a=x;}
  // FUNCTIONS -----------------------------------------------------------------
  /**
  *{@summary Standard equals function.}
  *Null &#38; other class type proof.
  *@param o o is the Object to test. It can be null or something else than this class.
  *@lastEditedVersion 1.31
  */
  @Override
  public boolean equals(Object o){
    if(o==null || !(o instanceof Pixel)){return false;}
    Pixel p = (Pixel)o;
    if(this.getR()!=p.getR()){ return false;}
    if(this.getG()!=p.getG()){ return false;}
    if(this.getB()!=p.getB()){ return false;}
    if(this.getA()!=p.getA()){ return false;}
    return true;
  }
  /**
  *{@summary Return pixel as a String.}<br>
  *If alpha value is 127, it will not be print.<br>
  *@lastEditedVersion 1.39
  */
  public String toString(){
    String s = (r+128)+" "+(g+128)+" "+(b+128);
    if(a<127)s+=" "+(a+128);
    return s;
  }
  /**
  *{@summary Return pixel as a Color.}<br>
  *@lastEditedVersion 1.39
  */
  public Color piToColor(){
    return new Color(r+128,g+128,b+128,a+128);
  }
  /**
  *{@summary Return Color as a grey color.}<br>
  *@lastEditedVersion 1.54
  */
  public static Color colorToGrey(Color c){
    if(c==null){return null;}
    int g = (int)(0.2989*((double)c.getRed()) + 0.5870*((double)c.getGreen()) + 0.1140*((double)c.getBlue()));
    return new Color(g,g,g, c.getAlpha());
  }
}
