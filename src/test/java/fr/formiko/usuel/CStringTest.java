package fr.formiko.usuel.listes;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.listes.CString;
import fr.formiko.usuel.tests.TestCaseMuet;
import org.junit.jupiter.api.Test;

public class CStringTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testEstCom(){
    CString cs = new CString("/*public void trophallaxieIa(Creature c1, Creature c2){");
    assertTrue(cs.estCom());
    cs = new CString("//public void trophallaxieIa(Creature c1, Creature c2){");
    assertTrue(cs.estCom());
    cs = new CString("/*public void trophallaxieIa(Creature c1, Creature c2){//blablabla");
    assertTrue(cs.estCom());
    cs = new CString("public void trophallaxieIa(Creature c1, Creature c2){//blablabla");
    assertTrue(!cs.estCom());
    cs = new CString("          //public void trophallaxieIa(Creature c1, Creature c2){");
    assertTrue(cs.estCom());
    cs = new CString("public void trophallaxieIa(Creature c1, Creature c2){ //un com suuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuper long");
    assertTrue(!cs.estCom());
  }

}
