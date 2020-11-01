package fr.formiko.formiko;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.conversiondetype.str;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import fr.formiko.formiko.Partie;

public class PartieTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testToString(){
    Partie p = new Partie();
    String s = p.toString();
    assertTrue(!str.contient(s,"@"));//on ne doit pas avoir de @ (de toString par défaut de java.)
  }
}
