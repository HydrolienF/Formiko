package fr.formiko.usuel.images;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.Point;
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
    assertTrue(!p.equals(null));
    assertTrue(!p.equals(p3));
    assertTrue(!p.equals(new Point(1,1)));
    p3 = new Pixel(0,0,256);
    assertTrue(!p.equals(p3));
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

}
