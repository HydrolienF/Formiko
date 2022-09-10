package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GSquare;
import fr.formiko.usual.exceptions.NotNullLocationException;
import fr.formiko.formiko.Main;
import fr.formiko.tests.TestCaseMuet;

public class FourmiliereTest extends TestCaseMuet{

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
    Fourmiliere g1 = new Fourmiliere();
    try {
      g1.setCSquare(Main.getCSquare(0,0));
    }catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    }
    assertEquals(Main.getCSquare(0,0), g1.getCSquare());
    Fourmiliere g2 = new Fourmiliere();
    assertEquals(null, g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,1));
    assertEquals(Main.getCSquare(0,1), g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
    g2.setCSquare(Main.getCSquare(100,10));
    assertEquals(null, g2.getCSquare());
    g2.setCSquare(Main.getCSquare(0,3));
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
    assertThrows(NotNullLocationException.class, () -> {
        g1.setCSquare(Main.getCSquare(0,3));
    });
    assertEquals(Main.getCSquare(0,0), g1.getCSquare());
    assertEquals(Main.getCSquare(0,3), g2.getCSquare());
  }
  @Test
  public void testSetCSquare2(){
    ini();
    Fourmiliere fere = new Fourmiliere();
    CSquare cc = fere.getCSquare();
    fere.setCSquare(cc);
  }
}
