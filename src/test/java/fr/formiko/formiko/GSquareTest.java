package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.structures.listes.Liste;

public class GSquareTest extends TestCaseMuet {
  @Test
  public void testLength(){
    GSquare gca = new GSquare(3,5);
    assertEquals(3,gca.getWidth());
    assertEquals(5,gca.getHeight());
    assertEquals(3*5,gca.length());
  }
  @Test
  public void testGetCSquare(){
    GSquare gca = new GSquare(3,5);
    assertEquals(null, gca.getCSquare(4,1));
    assertEquals(2, gca.getCSquare(2,3).getX());
  }
  @Test
  public void testGetSquaresBetween1(){
    GSquare gca = new GSquare(10,5);
    assertEquals(gca.getCSquare(0,0).getContent(),gca.getSquaresBetween(gca.getCSquare(0,0),gca.getCSquare(0,0)).getFirst());
    assertEquals(gca.getCSquare(2,3).getContent(),gca.getSquaresBetween(2,3,2,3).getFirst());
    assertEquals(gca.getCSquare(2,3).getContent(),gca.getSquaresBetween(gca.getCSquare(2,3),0).getFirst());
  }
  @Test
  public void testGetSquaresBetween0(){
    GSquare gca = new GSquare(10,5);
    assertTrue(gca.getSquaresBetween(gca.getCSquare(2,3),-1).isEmpty());
  }
  @Test
  public void testGetSquaresBetweenMoreThan1(){
    GSquare gca = new GSquare(10,5);
    assertEquals(2,gca.getSquaresBetween(2,3,2,4).length());
    assertEquals(9,gca.getSquaresBetween(gca.getCSquare(2,3),1).length());
    assertEquals(4,gca.getSquaresBetween(gca.getCSquare(0,0),1).length());
    assertEquals(6,gca.getSquaresBetween(gca.getCSquare(0,2),1).length());
  }
  @Test
  public void testGetSquaresBetweenMoreThan1b(){
    GSquare gca = new GSquare(10,6);
    Liste<Square> l = gca.getSquaresBetween(gca.getCSquare(3,3),2);
    assertEquals(25,l.length());
    assertTrue(l.contains(gca.getCSquare(1,1).getContent()));
    assertFalse(l.contains(gca.getCSquare(0,3).getContent()));
    assertTrue(l.contains(gca.getCSquare(3,3).getContent()));
    assertTrue(l.contains(gca.getCSquare(4,5).getContent()));
  }
}
