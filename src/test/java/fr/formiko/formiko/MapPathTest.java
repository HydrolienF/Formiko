package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;

public class MapPathTest extends TestCaseMuet {
  @Test
  public void testGetNextCCase1(){
    GCase gc = new GCase(3,3);
    CCase from = gc.getCCase(1,1);
    assertEquals(gc.getCCase(0,0),MapPath.getNextCCase(from,1));
    assertEquals(gc.getCCase(1,0),MapPath.getNextCCase(from,2));
    assertEquals(gc.getCCase(2,0),MapPath.getNextCCase(from,3));
    assertEquals(gc.getCCase(0,1),MapPath.getNextCCase(from,4));
    assertEquals(gc.getCCase(1,1),MapPath.getNextCCase(from,5));
    assertEquals(gc.getCCase(2,1),MapPath.getNextCCase(from,6));
    assertEquals(gc.getCCase(0,2),MapPath.getNextCCase(from,7));
    assertEquals(gc.getCCase(1,2),MapPath.getNextCCase(from,8));
    assertEquals(gc.getCCase(2,2),MapPath.getNextCCase(from,9));
  }
  @Test
  public void testGetNextCCase2(){
    GCase gc = new GCase(3,3);
    CCase from = gc.getCCase(1,0);
    assertEquals(null,MapPath.getNextCCase(from,1));
    assertEquals(null,MapPath.getNextCCase(from,2));
    assertEquals(null,MapPath.getNextCCase(from,3));
    assertEquals(gc.getCCase(0,0),MapPath.getNextCCase(from,4));
    assertEquals(gc.getCCase(1,0),MapPath.getNextCCase(from,5));
    assertEquals(gc.getCCase(2,0),MapPath.getNextCCase(from,6));
    assertEquals(gc.getCCase(0,1),MapPath.getNextCCase(from,7));
    assertEquals(gc.getCCase(1,1),MapPath.getNextCCase(from,8));
    assertEquals(gc.getCCase(2,1),MapPath.getNextCCase(from,9));
  }
  @Test
  public void testMapPath(){
    GCase gc = new GCase(3,4);
    CCase from = gc.getCCase(1,0);
    CCase to = gc.getCCase(2,2);
    MapPath mp = new MapPath(from, to);
    assertEquals(3,mp.getList().length());
    assertEquals(from,mp.getList().getFirst());
    assertEquals(gc.getCCase(2,1),mp.getList().get(1));
    assertEquals(to,mp.getList().getLast());
    assertEquals(to,mp.getList().get(2));
  }
  @Test
  public void testAddPath(){
    GCase gc = new GCase(3,4);
    CCase from = gc.getCCase(1,0);
    CCase to = gc.getCCase(2,0);
    MapPath mp = new MapPath(from, from);
    assertEquals(1,mp.getList().length());
    assertEquals(from,mp.getList().getFirst());
    mp.addPath(from, from); //no add if the end is already this case.
    assertEquals(1,mp.getList().length());
    assertEquals(from,mp.getList().getFirst());
    mp.addToPath(from); //no add if the end is already this case.
    assertEquals(1,mp.getList().length());
    assertEquals(from,mp.getList().getFirst());
    mp.addPath(from, to);
    assertEquals(2,mp.getList().length());
    assertEquals(from,mp.getList().getFirst());
    assertEquals(gc.getCCase(2,0),mp.getList().get(1));
  }
  @Test
  public void testToString(){
    GCase gc = new GCase(3,4);
    CCase from = gc.getCCase(1,0);
    CCase to = gc.getCCase(2,2);
    MapPath mp = new MapPath(from, to);
    assertEquals(3,mp.getList().length());
    assertEquals("(1,0) (2,1) (2,2)",mp.toString());
  }
}
