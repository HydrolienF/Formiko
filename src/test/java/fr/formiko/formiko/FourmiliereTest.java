package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.usuel.exceptions.NotNullLocationException;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.tests.TestCaseMuet;

public class FourmiliereTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  private void ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,5),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
  }
  @Test
  public void testSetCCase(){
    ini();
    Fourmiliere g1 = new Fourmiliere();
    try {
      g1.setCCase(Main.getCCase(0,0));
    }catch (Exception e) {
      e.printStackTrace();
      assertTrue(false);
    }
    assertEquals(Main.getCCase(0,0), g1.getCCase());
    Fourmiliere g2 = new Fourmiliere();
    assertEquals(null, g2.getCCase());
    g2.setCCase(Main.getCCase(0,1));
    assertEquals(Main.getCCase(0,1), g2.getCCase());
    g2.setCCase(Main.getCCase(0,3));
    assertEquals(Main.getCCase(0,3), g2.getCCase());
    g2.setCCase(Main.getCCase(100,10));
    assertEquals(null, g2.getCCase());
    g2.setCCase(Main.getCCase(0,3));
    assertEquals(Main.getCCase(0,3), g2.getCCase());
    assertThrows(NotNullLocationException.class, () -> {
        g1.setCCase(Main.getCCase(0,3));
    });
    assertEquals(Main.getCCase(0,0), g1.getCCase());
    assertEquals(Main.getCCase(0,3), g2.getCCase());
  }
  @Test
  public void testSetCCase2(){
    ini();
    Fourmiliere fere = new Fourmiliere();
    CCase cc = fere.getCCase();
    fere.setCCase(cc);
  }
}
