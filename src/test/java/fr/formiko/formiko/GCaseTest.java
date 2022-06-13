package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

// import fr.formiko.formiko.Carte;
// import fr.formiko.formiko.Fourmi;
// import fr.formiko.formiko.Fourmiliere;
// import fr.formiko.formiko.GCase;
// import fr.formiko.formiko.Joueur;
// import fr.formiko.formiko.Main;
// import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;

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
}
