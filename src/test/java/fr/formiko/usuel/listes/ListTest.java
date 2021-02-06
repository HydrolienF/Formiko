package fr.formiko.usuel.listes;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.listes.List;
import fr.formiko.formiko.Point;
import fr.formiko.usuel.tests.TestCaseMuet;

public class ListTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testLength(){
    List<Object> l = new List<Object>();
    assertEquals(0,l.length());
    l.add(new Point(0,5));
    l.add("string");
    assertEquals(2,l.length());
    l.add(new Object());
    assertEquals(3,l.length());
  }
  @Test
  public void testLength2(){
    List<String> l = new List<String>();
    assertEquals(0,l.length());
    l.add("string");
    l.add("pantalon");
    l.add("formiko");
    assertEquals(3,l.length());
    for (int i=0;i<10 ;i++ ) {
      l.add(i+" formiko");
    }
    assertEquals(13,l.length());
    l.add(null); //it shouldn't add it.
    assertEquals(13,l.length());
  }
  @Test
  public void testAddTail(){
    List<String> l = new List<String>();
    l.addTail("string");
    l.addTail("pantalon");
    l.addTail("formiko 2");
    assertEquals(3,l.length());
    assertEquals("string pantalon formiko 2 ",l.toString());
  }
  @Test
  public void testAddList(){
    List<String> l = new List<String>();
    l.addTail(", formiko");
    l.addTail("la ludo");

    List<String> l2 = new List<String>();
    l2.add("Pirpo & kartoĉio");
    l2.add("La libro");
    l2.addList(l);
    assertEquals("Pirpo & kartoĉio La libro , formiko la ludo ",l2.toString());
  }
  @Test
  public void testContaint(){
    List<String> l = new List<String>();
    assertTrue(!l.containt(null));
    assertTrue(!l.containt("formiko"));
    l.addTail("string");
    assertTrue(!l.containt("formiko"));
    l.addTail("formiko");
    assertTrue(l.containt("formiko"));
    l.addTail("formiko");
    l.addTail("formiko");
    assertTrue(l.containt("formiko"));
    assertTrue(!l.containt(null));
  }
  @Test
  public void testContaint2(){
    List<Point> l = new List<Point>();
    assertTrue(!l.containt(new Point(0,0)));
    l.add(new Point(0,0));
    assertEquals(1,l.length());
    //assertTrue(l.containt(new Point(0,0)));
    assertTrue(!l.containt(new Point(0,1)));
    Point p = new Point(-1,1);
    l.add(p);
    assertTrue(l.containt(p));
    //TODO #197 it do not use the overriding equals methode.
    //assertTrue(l.containt(new Point(-1,1)));
  }
}
