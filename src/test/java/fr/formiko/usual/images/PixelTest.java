package fr.formiko.usual.images;

import org.junit.jupiter.api.Test;

import fr.formiko.usual.Point;
// import fr.formiko.formiko.Pheromone;
import fr.formiko.tests.TestCaseMuet;

import java.awt.Color;

public class PixelTest extends TestCaseMuet{

  // Fonctions propre ----------------------------------------------------------
  @Test
  public void testPixel(){
    Pixel p = new Pixel(0,0,0);
    assertEquals(-128,p.getR());
    assertEquals(-128,p.getG());
    assertEquals(-128,p.getB());
    assertEquals(127,p.getA());
    p = new Pixel(10,200,30);
    assertEquals(-118,p.getR());
    assertEquals(200-128,p.getG());
    assertEquals(-98,p.getB());
    assertEquals(127,p.getA());
  }
  @Test
  public void testPixel2(){
    Pixel p = new Pixel(0,0,0,0);
    assertEquals(-128,p.getR());
    assertEquals(-128,p.getG());
    assertEquals(-128,p.getB());
    assertEquals(-128,p.getA());
    p = new Pixel(255,255,255,255);
    assertEquals(127,p.getR());
    assertEquals(127,p.getG());
    assertEquals(127,p.getB());
    assertEquals(127,p.getA());
    p = new Pixel(55,5,255,222);
    assertEquals(127-200,p.getR());
    assertEquals(-123,p.getG());
    assertEquals(127,p.getB());
    assertEquals(222-128,p.getA());
    p = new Pixel(355,5,255,222);
    assertEquals(127,p.getR());
  }
  @Test
  public void testToString(){
    Pixel p = new Pixel(0,50,123,222);
    assertEquals("0 50 123 222",p.toString());
    p = new Pixel(0,50,123,255);
    assertEquals("0 50 123",p.toString());
    p = new Pixel(-1,56,143);
    assertEquals("0 56 143",p.toString());
  }
  @Test
  public void testEquals(){
    Pixel p = new Pixel(0,0,0);
    Pixel p2 = new Pixel(0,0,0,255);
    Pixel p3 = new Pixel(1,0,0);
    assertTrue(p.equals(p2));
    assertTrue(p2.equals(p));
    assertTrue(p.equals(p));
    assertTrue(!p.equals((Pixel)null));
    assertTrue(!p.equals((String)null));
    assertTrue(!p.equals(p3));
    assertTrue(!p.equals(new Point(1,1)));
    p3 = new Pixel(0,0,256);
    assertTrue(!p.equals(p3));
    assertNotEquals(new Pixel(1,2,3,4), new Pixel(1,2,3,5));
    assertEquals(new Pixel(1,2,3,4), new Pixel(1,2,3,4));
    assertNotEquals(new Pixel(1,3,3), new Pixel(1,2,3));
  }
  @Test
  public void testPiToColor(){
    Pixel p = new Pixel(0,0,0);
    Color c = p.piToColor();
    assertEquals(255,c.getAlpha());
    assertEquals(0,c.getRed());
    assertEquals(0,c.getGreen());
    assertEquals(0,c.getBlue());
    p = new Pixel(30,100,-0);
    c = p.piToColor();
    assertEquals(30,c.getRed());
    assertEquals(100,c.getGreen());
    assertEquals(0,c.getBlue());
  }
  @Test
  public void testColorToGrey(){
    assertEquals(null, Pixel.colorToGrey(null));
    assertEquals(new Color(0,0,0), Pixel.colorToGrey(new Color(0,0,0)));
    assertEquals(new Color(254,254,254), Pixel.colorToGrey(new Color(255,255,255)));
    assertEquals(new Color(71,71,71), Pixel.colorToGrey(new Color(20,70,210)));
  }
  @Test
  public void testSetters(){
    Pixel p = new Pixel(1,2,3,4);
    p.setR((byte)(5-128));
    p.setG((byte)(6-128));
    p.setB((byte)(7-128));
    p.setA((byte)(8-128));
    assertEquals(new Pixel(5,6,7,8), p);
  }
  // @Test
  // public void testPixelFromPheromone(){
  //   assertEquals(new Pixel(5,6,7), new Pixel(new Pheromone(-128+5,-128+6,-128+7)));
  // }

}
