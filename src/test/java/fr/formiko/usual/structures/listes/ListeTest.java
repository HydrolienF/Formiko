package fr.formiko.usual.structures.listes;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.Point;
import fr.formiko.usual.fichier;
import fr.formiko.usual.structures.listes.Liste;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListeTest extends TestCaseMuet {

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testIsEmpty(){
    Liste<String> list = new Liste<String>();
    assertTrue(list.isEmpty());
    list.add("test string");
    assertTrue(!list.isEmpty());
    list.remove("test string");
    assertTrue(list.isEmpty());
    list.add("test string");
    list.add("test string2");
    list.remove("test string");
    assertTrue(!list.isEmpty());
  }
  @Test
  public void testClear(){
    List<String> list = new Liste<String>();
    list.add("test string");
    list.add("test string2");
    list.remove("test string");
    list.clear();
    assertTrue(list.isEmpty());
  }
  @Test
  public void testLength(){
    Liste<Object> l = new Liste<Object>();
    assertEquals(0,l.length());
    l.add(new Point(0,5));
    l.add("string");
    assertEquals(2,l.length());
    l.add(new Object());
    assertEquals(3,l.length());
  }
  @Test
  public void testLength2(){
    Liste<String> l = new Liste<String>();
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
    Liste<String> l = new Liste<String>();
    l.addTail("string");
    l.addTail("pantalon");
    l.addTail("formiko 2");
    assertEquals(3,l.length());
    assertEquals("string pantalon formiko 2 ",l.toString());
  }
  @Test
  public void testAddHead(){
    Liste<String> l = new Liste<String>();
    l.addHead("string");
    l.addHead("pantalon");
    l.addHead("formiko 2");
    assertEquals(3,l.length());
    assertEquals("formiko 2 pantalon string ",l.toString());
  }
  @Test
  public void testAddTailAndHead(){
    Liste<String> l = new Liste<String>();
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
    Liste<String> l = new Liste<String>();
    l.addTail(", formiko");
    l.addTail("la ludo");

    Liste<String> l2 = new Liste<String>();
    l2.add("Pirpo & kartoĉio");
    l2.add("La libro");
    l2.addList(l);
    assertEquals("Pirpo & kartoĉio La libro , formiko la ludo ",l2.toString());
  }
  @Test
  public void testAddList2(){
    Liste<String> l = new Liste<String>();
    l.addTail(", formiko");
    l.addTail("la ludo");

    Liste<String> l2 = new Liste<String>();
    l2.add("Pirpo & kartoĉio");
    l2.addList(l);
    assertEquals("Pirpo & kartoĉio , formiko la ludo ",l2.toString());
  }
  @Test
  public void testAddList3(){
    Liste<String> l = new Liste<String>();
    l.addTail(", formiko");

    Liste<String> l2 = new Liste<String>();
    l2.add("Pirpo & kartoĉio");
    l2.addList(l);
    assertEquals("Pirpo & kartoĉio , formiko ",l2.toString());
  }
  @Test
  public void testAddList4(){
    Liste<String> l = new Liste<String>();

    Liste<String> l2 = new Liste<String>();
    l2.add("Pirpo & kartoĉio");
    l2.addList(l);
    assertEquals("Pirpo & kartoĉio ",l2.toString());
  }
  @Test
  public void testAddList5(){
    Liste<String> l = new Liste<String>();
    l.addTail(", formiko");

    Liste<String> l2 = new Liste<String>();
    l2.addList(l);
    assertEquals(", formiko ",l2.toString());
  }
  @Test
  public void testAddAll(){
    Liste<String> l = new Liste<String>();
    l.addTail(", formiko");
    l.addTail("la ludo");

    Set<String> l2 = new HashSet<String>();
    l2.add("Pirpo & kartoĉio");
    l2.add("La libro");
    l2.addAll(l);
    assertEquals("[, formiko, la ludo, La libro, Pirpo & kartoĉio]",l2.toString());
  }
  @Test
  public void testAddAll2(){
    Set<String> l = new HashSet<String>();
    l.add(", formiko");
    l.add("la ludo");

    Liste<String> l2 = new Liste<String>();
    l2.add("Pirpo & kartoĉio");
    l2.add("La libro");
    l2.addAll(l);
    assertEquals("Pirpo & kartoĉio La libro , formiko la ludo ",l2.toString());
  }
  @Test
  public void testAddAll3(){
    Liste<String> l = new Liste<String>();
    l.addTail(", formiko");
    l.addTail("la ludo");

    Liste<String> l2 = new Liste<String>();
    l2.add("Pirpo & kartoĉio");
    l2.add("La libro");
    l2.addAll(l);
    assertEquals("Pirpo & kartoĉio La libro , formiko la ludo ",l2.toString());
  }
  @Test
  public void testRemoveAll(){
    Liste<String> l = new Liste<String>();
    l.addTail("2");
    l.addTail("4");
    l.add("6");

    Liste<String> l2 = new Liste<String>();
    l2.add("1");
    l2.add("2");
    l2.add("3");
    l2.add("4");
    l2.add("5");
    l2.add("6");
    assertTrue(l2.removeAll(l));
    assertEquals("1 3 5 ",l2.toString());
  }
  @Test
  public void testRemoveAll2(){
    Liste<String> l = new Liste<String>();
    l.addTail("2");
    l.add("6");
    l.addTail("4");

    Liste<String> l2 = new Liste<String>();
    l2.add("1");
    l2.add("2");
    l2.add("3");
    l2.add("4");
    l2.add("5");
    assertTrue(!l2.removeAll(l));
    assertEquals("1 3 5 ",l2.toString());
  }
  // @Test
  // public void testAddTail(){
  //   Liste<String> l = new Liste<String>();
  //   l.addTail("string");
  //   l.addTail("pantalon");
  //   l.addTail("formiko 2");
  //   assertEquals(3,l.length());
  //   assertEquals("string pantalon formiko 2 ",l.toString());
  // }
  @Test
  public void testEquals(){
    Liste<String> l = new Liste<String>();
    Liste<String> l2 = new Liste<String>();
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
    Liste<String> l = new Liste<String>();
    Liste<String> l2 = new Liste<String>();
    l.add("1a");
    l.add("2a");
    l2.add("2a");
    l2.add("1a");
    assertTrue(!l.equals(l2));
    assertTrue(!l2.equals(l));
  }
  @Test
  public void testcontains(){
    Liste<String> l = new Liste<String>();
    assertTrue(!l.contains(null));
    assertTrue(!l.contains("formiko"));
    l.addTail("string");
    assertTrue(!l.contains("formiko"));
    l.addTail("formiko");
    assertTrue(l.contains("formiko"));
    l.addTail("formiko");
    l.addTail("formiko");
    assertTrue(l.contains("formiko"));
    assertTrue(!l.contains(null));
  }
  @Test
  public void testcontains2(){
    Liste<Point> l = new Liste<Point>();
    assertTrue(!l.contains(new Point(0,0)));
    l.add(new Point(0,0));
    assertEquals(1,l.length());
    assertTrue(l.contains(new Point(0,0)));
    assertTrue(!l.contains(new Point(0,1)));
    Point p = new Point(-1,1);
    l.add(p);
    assertTrue(l.contains(p));
    assertTrue(l.contains(new Point(-1,1)));
    assertFalse(l.contains("-1,1"));
    assertTrue(!l.contains("-1,1"));
  }
  @Test
  public void testcontains3(){
    //Creature have an id to test equals()
    Main.ini();
    Main.setPartie(Partie.getDefautlPartie());
    Liste<Creature> l = new Liste<Creature>();
    assertTrue(!l.contains(new Point(0,0)));
    Insecte i = new Insecte();
    Fourmi f = new Fourmi();
    l.add(i);
    l.add(f);
    Insecte i2 = i;
    assertTrue(l.contains(i));
    assertTrue(l.contains(f));
    assertTrue(l.contains(i2));
    assertFalse(l.contains(new Fourmi()));
    assertFalse(l.contains(new Insecte()));
  }
  @Test
  public void testAdd(){
    Liste<String> list = new Liste<String>();
    list.add("test string");
    assertTrue(!list.isEmpty());
    assertEquals(1,list.length());
    assertEquals("test string",list.getItem(0));
  }
  @Test
  public void testRemove(){
    Liste<String> list = new Liste<String>();
    String s = "test string";
    list.add(s);
    assertEquals(1,list.length());
    list.remove(s);
  }
  @Test
  public void testRemove2(){
    Liste<String> list = new Liste<String>();
    list.add("test string");
    assertEquals(1,list.length());
    list.remove("test string");
  }
  @Test
  public void testRemoveItem(){
    Liste<String> list = new Liste<String>();
    list.add("test string");
    assertEquals(1,list.length());
    assertTrue(list.removeItem(0));
    assertEquals(0,list.length());
    assertTrue(!list.removeItem(0));
    assertTrue(!list.removeItem(3));
  }
  @Test
  public void testRemoveItem2(){
    Liste<String> list = new Liste<String>();
    list.add("0c");
    list.add("1c");
    list.add("2c");
    list.add("3c");
    assertEquals(4,list.length());
    assertTrue(list.removeItem(1));
    Liste<String> list2 = new Liste<String>();
    list2.add("0c");
    list2.add("2c");
    list2.add("3c");
    assertEquals(list2, list);
    assertTrue(list.removeItem(1));
    list2 = new Liste<String>();
    list2.add("0c");
    list2.add("3c");
    assertEquals(list2, list);
    assertTrue(list.removeItem(0));
    list2 = new Liste<String>();
    list2.add("3c");
    assertEquals(list2, list);
  }
  @Test
  public void testRemoveDuplicateItem(){
    Liste<String> list = new Liste<String>();
    list.add("0");
    list.add("1");
    list.add("0");
    list.add("0");
    list.add("4");
    list.add("2");
    assertTrue(list.removeDuplicateItem());
    Liste<String> list2 = new Liste<String>();
    list2.add("0");
    list2.add("1");
    list2.add("4");
    list2.add("2");
    assertEquals(list2, list);
  }
  @Test
  public void testAdd1(){
    Liste<String> l = new Liste<String>();
    l.add(0,"a");
    assertThrows(IndexOutOfBoundsException.class, () -> {
        l.add(2,"b");
    });
    l.add(1,"c");
    l.add(1,"d");
    assertEquals("a d c ",l.toString());
  }
  @Test
  public void testAddRemoveAdd(){
    Liste<String> l = new Liste<String>();
    l.add("A");
    l.addTail("B");
    l.remove("B");
    l.add("C");
    assertEquals("A C ",l.toString());
  }
  @Test
  public void testAddRemoveAdd2(){
    Liste<String> l = new Liste<String>();
    l.add("A");
    l.addTail("B");
    l.addTail("C");
    l.addTail("D");
    l.removeItem(1);
    assertEquals("A C D ",l.toString());
  }
  @Test
  public void testAddRemoveAdd3(){
    Liste<String> l = new Liste<String>();
    l.add("A");
    l.addTail("B");
    l.removeItem(1);
    l.addTail("C");
    assertEquals("A C ",l.toString());
  }
  @Test
  public void testAddSorted(){
    Liste<Point> l = new Liste<Point>();
    Comparator<Point> comparator = (Point p1, Point p2) -> (int)(p2.getX() - p1.getX());
    assertTrue(l.addSorted(new Point(2,3), comparator));
    assertEquals("(2,3) ",l.toString());
    assertTrue(l.addSorted(new Point(1,5), comparator));
    assertTrue(l.addSorted(new Point(7,1), comparator));
    assertEquals("(1,5) (2,3) (7,1) ",l.toString());
  }
  @Test
  public void testPopPush(){
    Liste<Point> l = new Liste<Point>();
    assertEquals(null,l.pop());
    l.push(new Point(1,1));
    assertEquals(new Point(1,1), l.pop());
    l.push(new Point(2,-11));
    l.push(new Point(2,1));
    assertEquals(new Point(2,1), l.pop());
    l.push(new Point(0,0));
    assertEquals(2,l.size());
    assertEquals(new Point(0,0), l.pop());
    assertEquals(new Point(2,-11), l.pop());
    assertTrue(l.isEmpty());
  }
  @Test
  public void testPushList(){
    Liste<Point> l = new Liste<Point>();
    l.push(new Point(1,1));
    Liste<Point> l2 = new Liste<Point>();
    l2.push(new Point(2,1));
    l2.push(new Point(3,1));
    l.push(l2);
    assertEquals(new Point(3,1), l.pop());
    assertEquals(new Point(2,1), l.pop());
    assertEquals(new Point(1,1), l.pop());
    assertTrue(l.isEmpty());
  }
  @Test
  public void testPushList2(){
    Liste<Point> l = new Liste<Point>();
    Liste<Point> l2 = new Liste<Point>();
    l2.push(new Point(2,1));
    l2.push(new Point(3,1));
    l.push(l2);
    assertEquals(new Point(3,1), l.pop());
    assertEquals(new Point(2,1), l.pop());
    assertTrue(l.isEmpty());
  }
  @Test
  public void testPushList3(){
    Liste<Point> l = new Liste<Point>();
    Liste<Point> l2 = new Liste<Point>();
    l2.push(new Point(3,1));
    l.push(l2);
    assertEquals(new Point(3,1), l.pop());
    assertTrue(l.isEmpty());
  }
  @Test
  public void testPushList4(){
    Liste<Point> l = new Liste<Point>();
    l.push(new Point(3,1));
    Liste<Point> l2 = new Liste<Point>();
    l.push(l2);
    assertEquals(new Point(3,1), l.pop());
    assertTrue(l.isEmpty());
  }
  @Test
  public void testFilter(){
    Liste<Point> l = new Liste<Point>();
    l.add(new Point(3,1));
    l.add(new Point(2,2));
    l.add(new Point(-1,4));
    l.add(new Point(-2000000000,1));
    assertEquals(new Liste<Point>(),l.filter(p -> p.getY()==0));

    Liste<Point> l2 = new Liste<Point>();
    l2.add(new Point(3,1));
    l2.add(new Point(-2000000000,1));
    assertEquals(l2,l.filter(p -> p.getY()==1));

    l2 = new Liste<Point>();
    l2.add(new Point(3,1));
    l2.add(new Point(2,2));
    assertEquals(l2,l.filter(p -> p.getX()>0));
  }
  @Test
  public void testFilter2(){
    Liste<String> l = new Liste<String>();
    l.add("1");
    l.add("un");
    l.add("unu");
    l.add("one");
    Liste<String> l2 = new Liste<String>();
    l2.add("un");
    l2.add("unu");
    assertEquals(l2,l.filter(str -> str.contains("u")));
  }
  @Test
  public void testGetMost(){
    GPoint l = new GPoint();
    l.add(new Point(0,-1));
    l.add(new Point(2,5));
    l.add(new Point(5,1));
    l.add(new Point(0,0));
    assertEquals(l.get(1),l.getMost((Point p1, Point p2) -> (int)((p2.getX()+p2.getY()) - (p1.getX()+p1.getY()))));
  }
  class GPoint extends Liste<Point>{}
  @Test
  @SuppressWarnings("unchecked")
  public void testSerialisation(){
    Liste<String> l = new Liste<String>();
    l.add("1");
    l.add("un");
    l.add("unu");
    l.add("one");
    String fileName="save"+getId();
    try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))){
      oos.writeObject(l);
    }catch(StackOverflowError e){
      assertTrue(false);
    }catch (Exception e) {
      assertTrue(false);
    }
    Liste loadL = null;
    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
      loadL = (Liste<String>) ois.readObject();
    }catch(StackOverflowError e){
      assertTrue(false);
    }catch (Exception e) {
      assertTrue(false);
    }finally {
      fichier.deleteDirectory(fileName);
    }
    assertEquals(l,loadL);
  }
}
