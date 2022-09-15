package fr.formiko.fusual;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usual.g;
import fr.formiko.tests.TestCaseMuet;

public class FTranslationTest extends TestCaseMuet {

  // FUNCTIONS -----------------------------------------------------------------
  @BeforeAll
  public static void ini(){
    Main.ini();
  }
  @Test
  public void testReplaceTranslation2(){
    Main.setLanguage(2);
    Main.iniLangue();
    assertTrue(Main.getLanguageId()==2);
    assertEquals("testEn",g.get("test"));
  }
  @Test
  public void testReplaceTranslation(){
    Main.setLanguage(0);
    Main.iniLangue();
    assertTrue(Main.getLanguageId()==0);
    assertEquals("testEo",g.get("test"));

    String s = "une str normale sans spécial char";
    assertEquals(s,FTranslation.replaceTranslation(s));
    s = "1€ pour Monsieux {X}";
    assertEquals(s,FTranslation.replaceTranslation(s));
    s = "€{clé}";
    assertTrue(!s.equals(FTranslation.replaceTranslation(s)));
    s = "une €{serviette} a la plage";
    assertTrue(!s.equals(FTranslation.replaceTranslation(s)));
    s = "€{test}";
    assertEquals("testEo",FTranslation.replaceTranslation(s));
    Main.getFop().set("language", 2);
    Main.iniLangue();
    assertEquals("testEn",FTranslation.replaceTranslation(s));
    s = "zavs[6+=§]€{bonvenon}~~^^ماò返€{test}€{fourmi}";
    assertEquals("zavs[6+=§]"+g.get("bonvenon")+"~~^^ماò返"+g.get("test")+g.get("fourmi"),FTranslation.replaceTranslation(s));
  }

}
