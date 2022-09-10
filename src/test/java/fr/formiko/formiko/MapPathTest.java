package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;

public class MapPathTest extends TestCaseMuet {
  @Test
  public void testGetNextCSquare1(){
    GSquare gc = new GSquare(3,3);
    CSquare from = gc.getCSquare(1,1);
    assertEquals(gc.getCSquare(0,0),MapPath.getNextCSquare(from,1));
    assertEquals(gc.getCSquare(1,0),MapPath.getNextCSquare(from,2));
    assertEquals(gc.getCSquare(2,0),MapPath.getNextCSquare(from,3));
    assertEquals(gc.getCSquare(0,1),MapPath.getNextCSquare(from,4));
    assertEquals(gc.getCSquare(1,1),MapPath.getNextCSquare(from,5));
    assertEquals(gc.getCSquare(2,1),MapPath.getNextCSquare(from,6));
    assertEquals(gc.getCSquare(0,2),MapPath.getNextCSquare(from,7));
    assertEquals(gc.getCSquare(1,2),MapPath.getNextCSquare(from,8));
    assertEquals(gc.getCSquare(2,2),MapPath.getNextCSquare(from,9));
  }
  @Test
  public void testGetNextCSquare2(){
    GSquare gc = new GSquare(3,3);
    CSquare from = gc.getCSquare(1,0);
    assertEquals(null,MapPath.getNextCSquare(from,1));
    assertEquals(null,MapPath.getNextCSquare(from,2));
    assertEquals(null,MapPath.getNextCSquare(from,3));
    assertEquals(gc.getCSquare(0,0),MapPath.getNextCSquare(from,4));
    assertEquals(gc.getCSquare(1,0),MapPath.getNextCSquare(from,5));
    assertEquals(gc.getCSquare(2,0),MapPath.getNextCSquare(from,6));
    assertEquals(gc.getCSquare(0,1),MapPath.getNextCSquare(from,7));
    assertEquals(gc.getCSquare(1,1),MapPath.getNextCSquare(from,8));
    assertEquals(gc.getCSquare(2,1),MapPath.getNextCSquare(from,9));
  }
  @Test
  public void testMapPath(){
    GSquare gc = new GSquare(3,4);
    CSquare from = gc.getCSquare(1,0);
    CSquare to = gc.getCSquare(2,2);
    assertNotNull(from);
    assertNotNull(to);
    MapPath mp = new MapPath(from, to);
    assertEquals(3,mp.getList().length());
    assertEquals(from,mp.getList().getFirst());
    assertEquals(gc.getCSquare(2,1),mp.getList().get(1));
    assertEquals(to,mp.getList().getLast());
    assertEquals(to,mp.getList().get(2));
  }
  @Test
  public void testAddPath(){
    GSquare gc = new GSquare(3,4);
    CSquare from = gc.getCSquare(1,0);
    CSquare to = gc.getCSquare(2,0);
    assertNotNull(from);
    assertNotNull(to);
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
    assertEquals(gc.getCSquare(2,0),mp.getList().get(1));
  }
  @Test
  public void testToString(){
    GSquare gc = new GSquare(3,4);
    CSquare from = gc.getCSquare(1,0);
    CSquare to = gc.getCSquare(2,2);
    assertNotNull(from);
    assertNotNull(to);
    MapPath mp = new MapPath(from, to);
    assertEquals(3,mp.getList().length());
    assertEquals("(1,0) (2,1) (2,2)",mp.toString());
  }
  private Fourmi iniFourmi(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GSquare(11,11),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCSquare(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    j.getFere().getGc().add(f);
    return f;
  }
  @Test
  public void testUpdateMovingSquareByTurn(){
    Fourmi f = iniFourmi();
    GSquare gc = Main.getPartie().getGc();
    CSquare from = gc.getCSquare(10,10);
    CSquare to = gc.getCSquare(2,2);
    MapPath mp = new MapPath(from, to);
    assertEquals(9,mp.getList().length());
    mp.updateMovingSquareByTurn(f);
    assertEquals("(10,10) (9,9) (8,8) (7,7) (6,6) (5,5) (4,4) (3,3) (2,2)  5 4",mp.toString());
  }
  @Test
  public void testUpdateMovingSquareByTurn2(){
    Fourmi f = iniFourmi();
    f.setAction(10);
    GSquare gc = Main.getPartie().getGc();
    CSquare from = gc.getCSquare(10,10);
    CSquare to = gc.getCSquare(2,2);
    MapPath mp = new MapPath(from, to);
    assertEquals(9,mp.getList().length());
    mp.updateMovingSquareByTurn(f);
    assertEquals("(10,10) (9,9) (8,8) (7,7) (6,6) (5,5) (4,4) (3,3) (2,2)  2 4 3",mp.toString());
  }
}
