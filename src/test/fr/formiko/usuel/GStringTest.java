package fr.formiko.usuel.liste;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.liste.GString;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;

public class GStringTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testEquals(){
    GString gs = new GString();
    assertTrue(!gs.equals(null));
    assertTrue(gs.equals(gs));
    assertTrue(gs.equals(new GString()));
    gs.add("");
    assertTrue(!gs.equals(new GString()));
    gs = new GString();
    gs.add("xcvbjnk");
    assertTrue(!gs.equals(new GString()));
    assertTrue(gs.equals(gs));
    GString gs2 = new GString();
    gs2.add(gs);
    assertTrue(gs.equals(gs2));
    gs2 = new GString();
    gs2.add("xcvbjnk");
    assertTrue(gs.equals(gs2));

    gs2.add("2");
    gs.add("2");
    assertTrue(gs.equals(gs2));
    gs.add("2");
    assertTrue(!gs.equals(gs2));
  }

}
