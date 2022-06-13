package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.Fourmiliere;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Joueur;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;

public class GCaseTest extends TestCaseMuet {
  @Test
  public void testLength(){
    GCase g = new GCase(3,5);
    assertEquals(3,g.getWidth());
    assertEquals(5,g.getHeight());
    assertEquals(3*5,g.length());
  }
}
