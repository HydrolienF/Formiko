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
    l.add((String)null); //it shouldn't add it.
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
  public void testAddHead(){
    List<String> l = new List<String>();
    l.addHead("string");
    l.addHead("pantalon");
    l.addHead("formiko 2");
    assertEquals(3,l.length());
    assertEquals("formiko 2 pantalon string ",l.toString());
  }
  @Test
  public void testAddTailAndHead(){
    List<String> l = new List<String>();
    l.addTail("0");
    l.addHead("1");
    l.addHead("2");
    l.addTail("3");
    l.addHead("4");
    assertEquals(5,l.length());
    assertEquals("4 2 1 0 3 ",l.toString());
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
  public void testEquals(){
    List<String> l = new List<String>();
    List<String> l2 = new List<String>();
    assertTrue(l.equals(l2));
    assertTrue(l2.equals(l));
    assertTrue(!l.equals(null));
    l.add("an item");
    assertTrue(!l.equals(l2));
    assertTrue(!l2.equals(l));
    l2.add("an item");
    assertTrue(l.equals(l2));
    assertTrue(l2.equals(l));
    l2.add("2a item");
    assertTrue(!l.equals(l2));
    assertTrue(!l2.equals(l));
  }
  @Test
  public void testEquals2(){
    List<String> l = new List<String>();
    List<String> l2 = new List<String>();
    l.add("1a");
    l.add("2a");
    l2.add("2a");
    l2.add("1a");
    assertTrue(!l.equals(l2));
    assertTrue(!l2.equals(l));
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
  @Test
  public void testAdd(){
    List<String> list = new List<String>();
    list.add("test string");
    assertTrue(!list.isEmpty());
    assertEquals(1,list.length());
    assertEquals("test string",list.getItem(0));
  }
  public void testRemove(){
    List<String> list = new List<String>();
    String s = "test string";
    list.add(s);
    assertEquals(1,list.length());
    list.remove(s);
  }
  public void testRemove2(){
    List<String> list = new List<String>();
    list.add("test string");
    assertEquals(1,list.length());
    list.remove("test string");
  }
  public void testRemoveItem(){
    List<String> list = new List<String>();
    list.add("test string");
    assertEquals(1,list.length());
    assertTrue(list.removeItem(0));
    assertEquals(0,list.length());
    assertTrue(!list.removeItem(0));
    assertTrue(!list.removeItem(3));
  }
  public void testRemoveItem2(){
    List<String> list = new List<String>();
    list.add("0");
    list.add("1");
    list.add("2");
    list.add("3");
    assertEquals(4,list.length());
    assertTrue(list.removeItem(1));
    List<String> list2 = new List<String>();
    list.add("0");
    list.add("2");
    list.add("3");
    assertEquals(list2, list);
    assertTrue(list.removeItem(1));
    list2 = new List<String>();
    list.add("0");
    list.add("3");
    assertEquals(list2, list);
    assertTrue(list.removeItem(0));
    list2 = new List<String>();
    list.add("3");
    assertEquals(list2, list);
  }
  public void testRemoveDuplicateItem(){
    List<String> list = new List<String>();
    list.add("0");
    list.add("1");
    list.add("2");
    list.add("0");
    list.add("0");
    list.add("4");
    assertTrue(list.removeDuplicateItem());
    List<String> list2 = new List<String>();
    list2.add("0");
    list2.add("1");
    list2.add("2");
    list2.add("3");
    list2.add("4");
    assertEquals(list2, list);
  }
}
