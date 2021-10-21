package fr.formiko.usuel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;
import fr.formiko.views.ViewNull;

public class tradTest extends TestCaseMuet {

  // FUNCTIONS -----------------------------------------------------------------
  @BeforeAll
  public static void ini(){
    Main.ini();
  }
  @Test
  // @Disabled("TODO #449 fix in github action") //it's working fined on my linux computer.
  public void testReplaceTranslation2(){
    Main.setLangue(2);
    Main.iniLangue();
    assertTrue(Main.getLanguage()==2);
    assertEquals("testEn",g.get("test"));
  }
  @Test
  // @Disabled("TODO #449 fix in github action") //it's working fined on my linux computer.
  public void testReplaceTranslation(){
    Main.setLangue(0);
    Main.iniLangue();
    assertTrue(Main.getLanguage()==0);
    assertEquals("testEo",g.get("test"));

    String s = "une str normale sans spécial char";
    assertEquals(s,trad.replaceTranslation(s));
    s = "1€ pour Monsieux {X}";
    assertEquals(s,trad.replaceTranslation(s));
    s = "€{clé}";
    assertTrue(!s.equals(trad.replaceTranslation(s)));
    s = "une €{serviette} a la plage";
    assertTrue(!s.equals(trad.replaceTranslation(s)));
    s = "€{test}";
    assertEquals("testEo",trad.replaceTranslation(s));
    Main.getOp().setLangue(2);
    Main.iniLangue();
    assertEquals("testEn",trad.replaceTranslation(s));
    s = "zavs[6+=§]€{bonvenon}~~^^ماò返€{test}€{fourmi}";
    assertEquals("zavs[6+=§]"+g.get("bonvenon")+"~~^^ماò返"+g.get("test")+g.get("fourmi"),trad.replaceTranslation(s));
  }

}
