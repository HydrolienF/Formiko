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
    assertNotNull(from);
    assertNotNull(to);
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
    assertEquals(gc.getCCase(2,0),mp.getList().get(1));
  }
  @Test
  public void testToString(){
    GCase gc = new GCase(3,4);
    CCase from = gc.getCCase(1,0);
    CCase to = gc.getCCase(2,2);
    assertNotNull(from);
    assertNotNull(to);
    MapPath mp = new MapPath(from, to);
    assertEquals(3,mp.getList().length());
    assertEquals("(1,0) (2,1) (2,2)",mp.toString());
  }
  private Fourmi iniFourmi(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(11,11),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceById(0), (byte) 3, (byte) 0);
    j.getFere().getGc().add(f);
    return f;
  }
  @Test
  public void testUpdateMovingCaseByTurn(){
    Fourmi f = iniFourmi();
    GCase gc = Main.getPartie().getGc();
    CCase from = gc.getCCase(10,10);
    CCase to = gc.getCCase(2,2);
    MapPath mp = new MapPath(from, to);
    assertEquals(9,mp.getList().length());
    mp.updateMovingCaseByTurn(f);
    assertEquals("(10,10) (9,9) (8,8) (7,7) (6,6) (5,5) (4,4) (3,3) (2,2)  5 4",mp.toString());
  }
  @Test
  public void testUpdateMovingCaseByTurn2(){
    Fourmi f = iniFourmi();
    f.setAction(10);
    GCase gc = Main.getPartie().getGc();
    CCase from = gc.getCCase(10,10);
    CCase to = gc.getCCase(2,2);
    MapPath mp = new MapPath(from, to);
    assertEquals(9,mp.getList().length());
    mp.updateMovingCaseByTurn(f);
    assertEquals("(10,10) (9,9) (8,8) (7,7) (6,6) (5,5) (4,4) (3,3) (2,2)  2 4 3",mp.toString());
  }
}
