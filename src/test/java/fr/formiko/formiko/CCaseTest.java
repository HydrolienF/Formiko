package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.tests.TestCaseMuet;

public class CCaseTest extends TestCaseMuet{
  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testEquals(){
    CCase cc = new CCase(new Case(0,0));
    CCase cc2 = new CCase(new Case(0,0));
    CCase cc3 = new CCase(new Case(0,1));
    CCase cc4 = new CCase(new Case(0,0),null,null,null,null);
    CCase cc6 = new CCase(new Case(4,1));
    CCase cc5 = new CCase(new Case(0,0),null,cc6,null,null);
    assertTrue(cc.equals(cc2));
    assertTrue(!cc.equals(cc3));
    assertTrue(cc.equals(cc4));
    assertTrue(!cc.equals(cc5));
  }
  @Test
  public void testGetDirection(){
    CCase cc = new CCase(new Case(5,5));
    assertEquals(1, cc.getDirection(new CCase(new Case(0,0))));
    assertEquals(1, cc.getDirection(new CCase(new Case(3,3))));
    assertEquals(2, cc.getDirection(new CCase(new Case(5,0))));
    assertEquals(3, cc.getDirection(new CCase(new Case(8,0))));
    assertEquals(3, cc.getDirection(new CCase(new Case(13,2))));
    assertEquals(4, cc.getDirection(new CCase(new Case(0,5))));
    assertEquals(4, cc.getDirection(new CCase(new Case(3,5))));
    assertEquals(5, cc.getDirection(new CCase(new Case(5,5))));
    assertEquals(6, cc.getDirection(new CCase(new Case(290,5))));
    assertEquals(7, cc.getDirection(new CCase(new Case(3,7))));
    assertEquals(8, cc.getDirection(new CCase(new Case(5,8))));
    assertEquals(9, cc.getDirection(new CCase(new Case(12,6))));

  }
  @Test
  public void testnbrDeCaseVoisine(){
    CCase cc = new CCase(new Case(0,0));
    CCase cc2 = new CCase(new Case(0,1));
    CCase cc3 = new CCase(new Case(1,1));
    CCase cc4 = new CCase(new Case(1,0),cc,cc2,null,null);
    assertEquals(0,cc.nbrDeCaseVoisine());
    assertEquals(0,cc2.nbrDeCaseVoisine());
    assertEquals(0,cc3.nbrDeCaseVoisine());
    assertEquals(2,cc4.nbrDeCaseVoisine());
    cc4 = new CCase(new Case(1,0),cc,cc2,cc3,cc);
    assertEquals(4,cc4.nbrDeCaseVoisine());
    cc4.setHaut(null);
    assertEquals(3,cc4.nbrDeCaseVoisine());
    cc4.setBas(cc);
    assertEquals(3,cc4.nbrDeCaseVoisine());
    cc4.setHaut(cc2);
    assertEquals(4,cc4.nbrDeCaseVoisine());
  }
}
