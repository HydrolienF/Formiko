package fr.formiko.usuel.structures.listes;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.structures.listes.GGInt;
import fr.formiko.usuel.structures.listes.GInt;
import fr.formiko.tests.TestCaseMuet;

public class GGIntTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testSommeCase(){
    GInt gi = new GInt();
    gi.add(1);
    gi.add(2);
    gi.add(3);
    GInt gi2 = new GInt();
    gi2.add(0);
    gi2.add(1);
    gi2.add(3);
    GGInt ggi = new GGInt();
    ggi.add(gi);
    ggi.add(gi2);
    assertEquals(1,ggi.sommeCase(0));
    assertEquals(3,ggi.sommeCase(1));
    assertEquals(6,ggi.sommeCase(2));
    assertEquals(-1,ggi.sommeCase(-1));
    assertEquals(-1,ggi.sommeCase(-5));
    assertEquals(0,ggi.sommeCase(3));
  }
}
