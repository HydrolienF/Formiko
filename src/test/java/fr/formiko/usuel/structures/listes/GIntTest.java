package fr.formiko.usuel.structures.listes;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.structures.listes.GInt;
import fr.formiko.tests.TestCaseMuet;

public class GIntTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testGetCase(){
    GInt gi = new GInt();
    assertEquals(0,gi.getCase(0));
    assertEquals(0,gi.getCase(3));
    gi.add(1);
    assertEquals(1,gi.getCase(0));
    assertEquals(-1,gi.getCase(-1));
    assertEquals(0,gi.getCase(1));
    assertEquals(0,gi.getCase(2));
    gi.add(2);
    gi.add(3);
    assertEquals(1,gi.getCase(0));
    assertEquals(-1,gi.getCase(-1));
    assertEquals(2,gi.getCase(1));
    assertEquals(3,gi.getCase(2));
    assertEquals(0,gi.getCase(3));
  }
}
