package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.structures.listes.Liste;

public class GCaseTest extends TestCaseMuet {
  @Test
  public void testLength(){
    GCase gca = new GCase(3,5);
    assertEquals(3,gca.getWidth());
    assertEquals(5,gca.getHeight());
    assertEquals(3*5,gca.length());
  }
  @Test
  public void testGetCCase(){
    GCase gca = new GCase(3,5);
    assertEquals(null, gca.getCCase(4,1));
    assertEquals(2, gca.getCCase(2,3).getX());
  }
  @Test
  public void testGetCasesBetween1(){
    GCase gca = new GCase(10,5);
    assertEquals(gca.getCCase(0,0).getContent(),gca.getCasesBetween(gca.getCCase(0,0),gca.getCCase(0,0)).getFirst());
    assertEquals(gca.getCCase(2,3).getContent(),gca.getCasesBetween(2,3,2,3).getFirst());
    assertEquals(gca.getCCase(2,3).getContent(),gca.getCasesBetween(gca.getCCase(2,3),0).getFirst());
  }
  @Test
  public void testGetCasesBetween0(){
    GCase gca = new GCase(10,5);
    assertTrue(gca.getCasesBetween(gca.getCCase(2,3),-1).isEmpty());
  }
  @Test
  public void testGetCasesBetweenMoreThan1(){
    GCase gca = new GCase(10,5);
    assertEquals(2,gca.getCasesBetween(2,3,2,4).length());
    assertEquals(9,gca.getCasesBetween(gca.getCCase(2,3),1).length());
    assertEquals(4,gca.getCasesBetween(gca.getCCase(0,0),1).length());
    assertEquals(6,gca.getCasesBetween(gca.getCCase(0,2),1).length());
  }
  @Test
  public void testGetCasesBetweenMoreThan1b(){
    GCase gca = new GCase(10,6);
    Liste<Case> l = gca.getCasesBetween(gca.getCCase(3,3),2);
    assertEquals(25,l.length());
    assertTrue(l.contains(gca.getCCase(1,1).getContent()));
    assertFalse(l.contains(gca.getCCase(0,3).getContent()));
    assertTrue(l.contains(gca.getCCase(3,3).getContent()));
    assertTrue(l.contains(gca.getCCase(4,5).getContent()));
  }
}
