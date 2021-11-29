package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;

public class PointTest extends TestCaseMuet {

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testEquals(){
    Point p = new Point(0,0);
    Point p2 = new Point(0,0);
    assertTrue(p.equals(p2));
    assertTrue(p.equals(new Point(0,0)));
    p2 = p;
    assertTrue(p.equals(p2));
    p2 = null;
    assertTrue(!p.equals(p2));
    p2 = new Point(1,0);
    assertTrue(!p.equals(p2));
    p = new Point(10,-6);
    p2 = new Point(10,-6);
    assertTrue(p.equals(p2));
  }
  @Test
  public void testEquals2(){
    Point p = new Point(0,0);
    String s=null;
    assertFalse(p.equals(s));
    Integer i=null;
    assertFalse(p.equals(i));
    i=new Integer(2);
    assertFalse(p.equals(i));
    assertTrue(p.equals("0;0"));
    assertTrue(p.equals("0,0"));
    //la ligne suivante ne marche pas... parce que string.split() et . ne sont pas compatible...
    //assertTrue(p.equals("0.0"));
    assertTrue(p.equals("0 0"));
    assertTrue(p.equals("0 +0"));
    assertTrue(p.equals("+0,+0"));
    assertTrue(p.equals("-0,0"));
    assertTrue(!p.equals("-1,+1"));
    assertTrue(!p.equals("-1,0"));
    assertTrue(!p.equals("0,+1"));

    assertTrue(!p.equals("0;0;0"));
    assertTrue(!p.equals("6,5,1"));
    assertTrue(!p.equals("6,5 1"));
    assertTrue(!p.equals("0,0 1"));
    //assertTrue(!p.equals("0 0,0"));
    p = new Point(389744,-60);
    assertTrue(p.equals("+389744,-60"));
    assertTrue(p.equals("389744,-60"));
    assertTrue(p.equals("389744;-60"));
    assertTrue(p.equals("389744 -60"));
    assertTrue(p.equals("000389744 -060"));
    assertTrue(!p.equals("-389744 -60"));
    assertTrue(!p.equals("-60,389744"));
    assertTrue(!p.equals("-60 +389744"));
    assertTrue(!p.equals("60;-389744"));
  }
  @Test
  public void testToString(){
    Point p = new Point(1,2);
    assertEquals("(1,2)",p.toString());
    p = new Point(-300000,0);
    assertEquals("(-300000,0)",p.toString());
  }
  @Test
  public void testClone(){
    Point p = new Point(1,-52);
    Point p2=p.clone();
    assertEquals(p,p2);
    p2.setY(-51);
    assertNotEquals(p,p2);
    assertEquals(new Point(1,-51),p2);
  }
  @Test
  public void testPointString(){
    Point p = new Point("1;a");
    assertEquals(new Point(1,-1),p);
    p = new Point("b;a");
    assertEquals(new Point(-1,-1),p);
    p = new Point("14");
    assertEquals(new Point(14,-1),p);
    p = new Point("c;14");
    assertEquals(new Point(-1,14),p);
  }
  @Test
  public void testGetSet(){
    Point p = new Point(1,2);
    assertEquals(1,p.getX());
    assertEquals(2,p.getY());
    p.setX(3);
    assertEquals(3,p.getX());
    assertEquals(2,p.getY());
    p.setY(-6);
    assertEquals(3,p.getX());
    assertEquals(-6,p.getY());
  }
  @Test
  public void testAdd(){
    Point p = new Point(1,2);
    p.addX(1);
    assertEquals(2,p.getX());
    p.addX(-10);
    assertEquals(-8,p.getX());
    p.addY(0);
    assertEquals(2,p.getY());
  }
}
