package fr.formiko.usuel.liste;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.liste.GInt;
import fr.formiko.usuel.tests.TestCaseMuet;
import org.junit.Test;

public class GIntTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
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
