package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.CSquare;
import fr.formiko.formiko.Square;
import fr.formiko.tests.TestCaseMuet;

public class CSquareTest extends TestCaseMuet{
  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testEquals(){
    CSquare cc = new CSquare(new Square(0,0));
    CSquare cc2 = new CSquare(new Square(0,0));
    CSquare cc3 = new CSquare(new Square(0,1));
    CSquare cc4 = new CSquare(new Square(0,0));
    CSquare cc6 = new CSquare(new Square(4,1));
    // CSquare cc5 = new CSquare(new Square(0,0),null,cc6,null,null);
    assertTrue(cc.equals(cc2));
    assertTrue(!cc.equals(cc3));
    assertTrue(cc.equals(cc4));
    // assertTrue(!cc.equals(cc5));
  }
  @Test
  public void testGetDirection(){
    CSquare cc = new CSquare(new Square(5,5));
    assertEquals(1, cc.getDirection(new CSquare(new Square(0,0))));
    assertEquals(1, cc.getDirection(new CSquare(new Square(3,3))));
    assertEquals(2, cc.getDirection(new CSquare(new Square(5,0))));
    assertEquals(3, cc.getDirection(new CSquare(new Square(8,0))));
    assertEquals(3, cc.getDirection(new CSquare(new Square(13,2))));
    assertEquals(4, cc.getDirection(new CSquare(new Square(0,5))));
    assertEquals(4, cc.getDirection(new CSquare(new Square(3,5))));
    assertEquals(5, cc.getDirection(new CSquare(new Square(5,5))));
    assertEquals(6, cc.getDirection(new CSquare(new Square(290,5))));
    assertEquals(7, cc.getDirection(new CSquare(new Square(3,7))));
    assertEquals(8, cc.getDirection(new CSquare(new Square(5,8))));
    assertEquals(9, cc.getDirection(new CSquare(new Square(12,6))));

  }
  @Test
  public void testnbrDeSquareVoisine(){
    CSquare cc = new CSquare(new Square(0,0));
    CSquare cc2 = new CSquare(new Square(0,1));
    CSquare cc3 = new CSquare(new Square(1,1));
    // CSquare cc4 = new CSquare(new Square(1,0),cc,cc2,null,null);
    assertEquals(0,cc.nbrDeSquareVoisine());
    assertEquals(0,cc2.nbrDeSquareVoisine());
    assertEquals(0,cc3.nbrDeSquareVoisine());
    // assertEquals(2,cc4.nbrDeSquareVoisine());
    // cc4 = new CSquare(new Square(1,0),cc,cc2,cc3,cc);
    // assertEquals(4,cc4.nbrDeSquareVoisine());
    // cc4.setUp(null);
    // assertEquals(3,cc4.nbrDeSquareVoisine());
    // cc4.setDown(cc);
    // assertEquals(3,cc4.nbrDeSquareVoisine());
    // cc4.setUp(cc2);
    // assertEquals(4,cc4.nbrDeSquareVoisine());
  }
}
