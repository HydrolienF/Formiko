package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.GSquare;
import fr.formiko.formiko.Main;
import fr.formiko.tests.TestCaseMuet;

public class GraineTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GSquare(1,5),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
  }
  @Test
  public void testSetCSquare(){
    ini();
    Graine g1 = new Graine(Main.getGc().getCSquare(0,0),100,(byte)10);
    assertEquals(Main.getCSquare(0,0), g1.getCSquare());
    Graine g2 = new Graine(null,10,(byte)20);
    assertEquals(null, g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,0));
    assertEquals(Main.getCSquare(0,0), g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
    g2.setCSquare(Main.getCSquare(100,10));
    assertEquals(null, g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
    g1.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g1.getCSquare());
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
  }
  @Test
  public void testGraine(){
    ini();
    assertEquals(0,Main.getGc().getCSquare(0,0).getContent().getGg().length());
    Graine g1 = new Graine(Main.getGc().getCSquare(0,0),100,(byte)10);
    assertEquals(1,Main.getGc().getCSquare(0,0).getContent().getGg().length());
    assertEquals(g1,Main.getGc().getCSquare(0,0).getContent().getGg().getFirst());
    Graine g2 = new Graine(Main.getGc().getCSquare(0,0),10,(byte)100);
    assertEquals(2,Main.getGc().getCSquare(0,0).getContent().getGg().length());
    assertTrue(Main.getGc().getCSquare(0,0).getContent().getGg().contains(g2));
    assertTrue(Main.getGc().getCSquare(0,0).getContent().getGg().contains(g1));
  }
}
