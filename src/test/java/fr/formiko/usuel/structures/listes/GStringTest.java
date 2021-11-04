package fr.formiko.usuel.structures.listes;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usuel.structures.listes.GString;

public class GStringTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
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
  @Test
  public void testCompterFct(){
    GString gs = new GString();
    //GInt gi = new GInt(); gi.add();
    assertEquals(0,gs.compterFct().getCase(0));
    assertEquals(0,gs.compterFct().getCase(1));
    gs.add("xrctvbnag");
    assertEquals(0,gs.compterFct().getCase(0));
    assertEquals(0,gs.compterFct().getCase(1));
    gs.add("public int a = 0;");
    assertEquals(0,gs.compterFct().getCase(0));
    assertEquals(0,gs.compterFct().getCase(1));
    gs.add("public void testFctR (){");
    assertEquals(0,gs.compterFct().getCase(0));
    assertEquals(1,gs.compterFct().getCase(1));
    gs.add("public void testFctR (){...}");
    assertEquals(1,gs.compterFct().getCase(0));
    assertEquals(1,gs.compterFct().getCase(1));
    gs = new GString();
    gs.add("public void testFctR (){...}");
    assertEquals(1,gs.compterFct().getCase(0));
    assertEquals(0,gs.compterFct().getCase(1));
  }
  @Test
  public void testCompterFct2(){
    GString gs = new GString();
    gs.add("public class PondreNull implements Serializable, Pondre {");
    gs.add("  ");
    gs.add("  public void unePonte(Creature c){");
    gs.add("    erreur.erreur(\"Impossible de pondre avec la créature \" + c.getId());");
    gs.add("  }");
    gs.add("}");
    assertEquals(0,gs.compterFct().getCase(0));
    assertEquals(2,gs.compterFct().getCase(1));
  }
  @Test
  public void testCompterComJavadoc(){
    GString gs = new GString();
    assertEquals(0,gs.compterComJavadoc());
    gs.add("//tey");
    gs.add("/*");
    gs.add("lol ca C fun");
    gs.add("*/");
    assertEquals(0,gs.compterComJavadoc());
    gs.add("/**");
    assertEquals(1,gs.compterComJavadoc());
    gs = new GString();
    gs.add("/**blablabla");
    assertEquals(1,gs.compterComJavadoc());
    gs = new GString();
    gs.add("/***");
    assertEquals(0,gs.compterComJavadoc());
  }
  @Test
  public void testCompterComJavadoc2(){
    GString gs = new GString();
    gs.add("public class PondreNull implements Serializable, Pondre {");
    gs.add("  ");
    gs.add("  public void unePonte(Creature c){");
    gs.add("    erreur.erreur(\"Impossible de pondre avec la créature \" + c.getId());");
    gs.add("  }");
    gs.add("}");
    assertEquals(0,gs.compterComJavadoc());
    gs.add("/**");
    assertEquals(1,gs.compterComJavadoc());
  }

  @Test
  public void testCompterFctEnDetail(){
    GString gs = new GString();
    assertEquals(0,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(0,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));

    gs = new GString();
    gs.add("class C{");
    gs.add("interface I{");
    assertEquals(2,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(0,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));
    gs = new GString();
    gs.add("public void fct(int a){");
    assertEquals(0,gs.compterFctEnDetail().getCase(0));
    assertEquals(1,gs.compterFctEnDetail().getCase(1));
    assertEquals(0,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));
    gs = new GString();
    gs.add("private void fct(int a){");
    assertEquals(0,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(0,gs.compterFctEnDetail().getCase(2));
    assertEquals(1,gs.compterFctEnDetail().getCase(3));
    gs = new GString();
    gs.add("protected void fct(intString s a){");
    assertEquals(0,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(1,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));
    gs = new GString();
    gs.add("protected x(){");
    assertEquals(0,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(1,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));

    gs = new GString();
    gs.add("protected void fct(intString s a){");
    gs.add("dfghj");
    gs.add("protected");
    assertEquals(0,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(1,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));
    gs = new GString();
    gs.add("protected void fct(intString s a){");
    gs.add("// la class sert a ...");
    gs.add("//une fct protected est là.");
    assertEquals(0,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(1,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));

    gs = new GString();
    gs.add("protected void fct(intString s a){");
    gs.add("class {");
    gs.add("fct protected (){");
    assertEquals(1,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(2,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));

    gs.add("class {}");
    gs.add("public () {}");
    assertEquals(1,gs.compterFctEnDetail().getCase(0));
    assertEquals(0,gs.compterFctEnDetail().getCase(1));
    assertEquals(2,gs.compterFctEnDetail().getCase(2));
    assertEquals(0,gs.compterFctEnDetail().getCase(3));
  }
}
