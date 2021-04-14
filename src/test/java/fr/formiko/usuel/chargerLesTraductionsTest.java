package fr.formiko.usuel;


import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.images.image;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class chargerLesTraductionsTest extends TestCaseMuet{

  // get set -------------------------------------------------------------------
  public void testIniMap(){
    chargerLesTraductions.iniMap();
    assertTrue(chargerLesTraductions.getMap()!=null);
    assertEquals(0,chargerLesTraductions.getMap().size());
  }

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testgetLanguage(){
    String t []= {"en","fr","langueTest"};
    chargerLesTraductions.setTLangue(t);
    assertEquals("en",chargerLesTraductions.getLanguage(0));
    assertEquals("fr",chargerLesTraductions.getLanguage(1));
    assertEquals("langueTest",chargerLesTraductions.getLanguage(2));
    //si ca me marche pas.
    assertEquals("en",chargerLesTraductions.getLanguage(-1));
    assertEquals("en",chargerLesTraductions.getLanguage(3));
    assertEquals("en",chargerLesTraductions.getLanguage(500));
    assertEquals("en",chargerLesTraductions.getLanguage(-289));
    String t2 []= {};
    chargerLesTraductions.setTLangue(t2);
    assertEquals("en",chargerLesTraductions.getLanguage(0));
    assertEquals("en",chargerLesTraductions.getLanguage(1));
    chargerLesTraductions.setTLangue(null);
    assertEquals("en",chargerLesTraductions.getLanguage(0));
    assertEquals("en",chargerLesTraductions.getLanguage(1));
    //assertTrue(true);
  }
  @Test
  public void testgetLanguageS(){
    String t []= {"a","bcd","épit"};
    chargerLesTraductions.setTLangue(t);
    //une langue qui y est
    assertEquals(0,chargerLesTraductions.getLanguage("a"));
    assertEquals(1,chargerLesTraductions.getLanguage("bcd"));
    assertEquals(2,chargerLesTraductions.getLanguage("bdc"));
    assertEquals(2,chargerLesTraductions.getLanguage("épit"));
    //une langue qui n'y est pas
    assertEquals(2,chargerLesTraductions.getLanguage("test"));
    assertEquals(2,chargerLesTraductions.getLanguage("ø"));
    //un usage imprévu
    assertEquals(2,chargerLesTraductions.getLanguage(""));
    String s = null;
    assertEquals(2,chargerLesTraductions.getLanguage(s));

    //si la langue 2 n'existe pas dans le tableau des langues.
    String t2 []= {"r"};
    chargerLesTraductions.setTLangue(t2);
    assertEquals(0,chargerLesTraductions.getLanguage("r"));
    assertEquals(-1,chargerLesTraductions.getLanguage("a"));
    assertEquals(-1,chargerLesTraductions.getLanguage("zauvfbiano"));
  }
  //iniTLangue
  @Test
  public void testIniTLangue(){
    //fonctionnement normale.
    assertTrue(chargerLesTraductions.iniTLangue());
    //test dans un autre environement
    String rep = chargerLesTraductions.getRep();
    chargerLesTraductions.setRep("");
    assertTrue(!chargerLesTraductions.iniTLangue());
    assertEquals(1,chargerLesTraductions.getTLangue().length);//iniTLangue a dû s'auto corrigé en chargant juste l'anglais.
    chargerLesTraductions.setRep(rep);
    assertTrue(chargerLesTraductions.iniTLangue());
    assertEquals(105,chargerLesTraductions.getTLangue().length);
  }

  @Test
  public void testCreerLesFichiers(){
    //fonctionnement normale.
    assertTrue(chargerLesTraductions.iniTLangue());
    assertTrue(chargerLesTraductions.créerLesFichiers());
    //test dans un autre environement
    int x = TestCaseMuet.getId();
    File f = new File("testDir"+x);
    f.mkdir();
    chargerLesTraductions.setRep("testDir"+x);
    assertTrue(chargerLesTraductions.créerLesFichiers());
    String tf [] = f.list();
    assertEquals(105,tf.length);
    assertTrue(tableau.contient(tf,"en.txt"));
    assertTrue(tableau.contient(tf,"fr.txt"));
    assertTrue(tableau.contient(tf,"zu.txt"));
    assertTrue(tableau.contient(tf,"eo.txt"));
    assertTrue(!tableau.contient(tf,"ep.txt"));
    assertTrue(!tableau.contient(tf,"zfag.txt"));
    assertTrue(!tableau.contient(tf,"eo"));
    assertTrue(fichier.deleteDirectory(f));
    //test avec un autre tableau de langue.
    int y = TestCaseMuet.getId();
    f = new File("testDir"+y);
    f.mkdir();
    String tf2 [] = {"atta.txt","n","ocotô.md"};
    chargerLesTraductions.setTLangue(tf2);
    chargerLesTraductions.setRep("testDir"+y);
    assertTrue(chargerLesTraductions.créerLesFichiers());
    String tf3 [] = f.list();
    assertTrue(tableau.contient(tf3,"n.txt"));
    assertTrue(tableau.contient(tf3,"atta.txt.txt"));
    assertTrue(tableau.contient(tf3,"ocotô.md.txt"));
    assertTrue(!tableau.contient(tf3,"en.txt"));
    assertTrue(fichier.deleteDirectory(f));

    chargerLesTraductions.setRep();
  }

  //estLigneDeTrad
  @Test
  public void testEstLigneDeTrad(){
    assertTrue(chargerLesTraductions.estLigneDeTrad("a:"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("a:a"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("veiuogz:tehtkph"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("tcuva.t.345.ty:zety"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("efoaeègzn-|`gz:"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("a:etey.:R"));
    //false
    assertTrue(!chargerLesTraductions.estLigneDeTrad("ezgriyld/·vbioneg"));
    assertTrue(!chargerLesTraductions.estLigneDeTrad(":"));
    assertTrue(!chargerLesTraductions.estLigneDeTrad(":zeeut"));
    //commentaire
    assertTrue(!chargerLesTraductions.estLigneDeTrad("//"));
    assertTrue(!chargerLesTraductions.estLigneDeTrad("//eztt"));
    //ligne vide
    assertTrue(!chargerLesTraductions.estLigneDeTrad(""));
    String s = null;
    assertTrue(!chargerLesTraductions.estLigneDeTrad(s));
  }

  //getTableauDesTrad
  @Test
  public void testGetTableauDesTrad(){
    int x = TestCaseMuet.getId();
    File f = new File("testDir"+x);
    f.mkdir();
    File ft = new File("testDir"+x+"/te.txt");
    try {
      assertTrue(ft.createNewFile());assertTrue(ft.exists());
    }catch (Exception e) {assertTrue(false);}
    String tLangue [] = {"te","ts"};
    chargerLesTraductions.setTLangue(tLangue);
    chargerLesTraductions.setRep("testDir"+x);
    String t [] = chargerLesTraductions.getTableauDesTrad(0);
    assertEquals(0,t.length);

    String t2 [] = chargerLesTraductions.getTableauDesTrad(1);
    assertEquals(0,t2.length);
    String t3 [] = chargerLesTraductions.getTableauDesTrad(2);
    assertEquals(0,t3.length);

    //test d' 'un tableau avec un fichier non vide.
    GString gs = new GString();
    gs.add("//com");
    gs.add("ex:test");
    gs.add("ex2:dfgh");
    gs.add("ex3:qzg");
    String tLangue2 [] = {"te","ts","test"};
    chargerLesTraductions.setTLangue(tLangue2);
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    assertEquals(4,chargerLesTraductions.getTableauDesTrad(2).length);

    assertTrue(fichier.deleteDirectory(f));
    chargerLesTraductions.setRep();
  }

  //getTableauDesCmd
  @Test
  public void testGetTableauDesCmd(){
    String t [] = chargerLesTraductions.getTableauDesCmd();
    assertTrue(t.length>1);//Le fichier contient des lignes (et donc a bien été lu).
    int x = TestCaseMuet.getId();

    File f = new File("testDir"+x);
    f.mkdir();
    File ft = new File("testDir"+x+"/te.txt");
    try {
      assertTrue(ft.createNewFile());assertTrue(ft.exists());
    }catch (Exception e) {assertTrue(false);}
    chargerLesTraductions.setRep("testDir"+x);
    String t2 [] = chargerLesTraductions.getTableauDesCmd();
    assertEquals(0,t2.length);
    assertTrue(fichier.deleteDirectory(f));
    chargerLesTraductions.setRep();
  }

  //chargerLesTraductions
  @Test
  public void testChargerLesTraductions(){
    chargerLesTraductions.setRep();
    assertTrue(chargerLesTraductions.iniTLangue());
    Map<String, String> fr = chargerLesTraductions.chargerLesTraductions(1);
    assertEquals("testFr",fr.get("test"));
    Map<String, String> eo = chargerLesTraductions.chargerLesTraductions(0);
    assertEquals("testEo",eo.get("test"));
    Map<String, String> zu = chargerLesTraductions.chargerLesTraductions(chargerLesTraductions.getLanguage("zu"));
    assertEquals("testZu",zu.get("test"));
    //si c'est pas une langue existante.
    Map<String, String> zz = chargerLesTraductions.chargerLesTraductions(chargerLesTraductions.getLanguage("zz"));
    assertEquals("testEn",zz.get("test"));
    assertTrue(!zz.get("test").equals("testZz"));


    assertEquals("test",fr.get("cmd.1"));
    assertEquals("test",eo.get("cmd.1"));
    assertEquals("test",zu.get("cmd.1"));
  }

  //chargerLesTraductionsSansCommande
  @Test
  public void testChargerLesTraductionsSansCommande(){
    chargerLesTraductions.setRep();
    assertTrue(chargerLesTraductions.iniTLangue());
    Map<String, String> zu = chargerLesTraductions.chargerLesTraductions(chargerLesTraductions.getLanguage("zu"));
    assertEquals("testZu",zu.get("test"));
    //si c'est pas une langue existante.
    Map<String, String> zz = chargerLesTraductions.chargerLesTraductions(chargerLesTraductions.getLanguage("zz"));
    assertEquals("testEn",zz.get("test"));//si la langue n'existe pas on passe a l'anglais.
    assertTrue(!zz.get("test").equals("testZz"));
  }

  //addObjetMap
  @Test
  public void testaddObjetMap(){
    chargerLesTraductions.iniMap();//remet a 0.
    assertEquals(0,chargerLesTraductions.getMap().size());
    chargerLesTraductions.addObjetMap("ezgubo.zegh");//ne fait rien si ce n'est pas une ligne de trad.
    assertEquals(0,chargerLesTraductions.getMap().size());
    chargerLesTraductions.addObjetMap("uneCle:une valeur X");//ne fait rien si ce n'est pas une ligne de trad.
    assertEquals(1,chargerLesTraductions.getMap().size());
    assertEquals("une valeur X",chargerLesTraductions.getMap().get("uneCle"));
    //la meme chose :
    chargerLesTraductions.addObjetMap("uneCle:une valeur X");//ne fait rien si ce n'est pas une ligne de trad.
    assertEquals(1,chargerLesTraductions.getMap().size());
    assertEquals("une valeur X",chargerLesTraductions.getMap().get("uneCle"));
    //une valeur différente.
    chargerLesTraductions.addObjetMap("uneCle:une valeur Y");//ne fait rien si ce n'est pas une ligne de trad.
    assertEquals(1,chargerLesTraductions.getMap().size());
    assertEquals("une valeur Y",chargerLesTraductions.getMap().get("uneCle"));
    //une autre clé
    chargerLesTraductions.addObjetMap("a:une valeur Z");//ne fait rien si ce n'est pas une ligne de trad.
    assertEquals(2,chargerLesTraductions.getMap().size());
    assertEquals("une valeur Z",chargerLesTraductions.getMap().get("a"));
    assertEquals("une valeur Y",chargerLesTraductions.getMap().get("uneCle"));
  }
  @Test
  public void testaddObjetMap2(){
    chargerLesTraductions.iniMap();//remet a 0.
    assertEquals(0,chargerLesTraductions.getMap().size());
    chargerLesTraductions.addObjetMap("uneCle:Une valeur X");//ne fait rien si ce n'est pas une ligne de trad.
    assertEquals(1,chargerLesTraductions.getMap().size());
    assertEquals("une valeur X",chargerLesTraductions.getMap().get("uneCle"));
  }

  //addTradAuto
  //testé dans ThTrad.

  //getPourcentageTraduit
  @Test
  public void testGetPourcentageTraduit(){
    GString gs = new GString();
    gs.add("//com");
    gs.add("ex:test");
    gs.add("ex2:");
    gs.add("ex3:");
    int x=TestCaseMuet.getId();
    File f = new File("testDir"+x);
    f.mkdir();
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    chargerLesTraductions.setRep("testDir"+x);
    String tl []= {"test"};
    chargerLesTraductions.setTLangue(tl);
    assertEquals(-1,chargerLesTraductions.getPourcentageTraduit(0));
    //assertEquals(-1,chargerLesTraductions.getPourcentageTraduit(1));

    //autre
    gs = new GString();
    gs.add("//com");
    gs.add("ex:test");
    gs.add("ex2:dfgh");
    gs.add("ex3:qzg");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    assertEquals(-1,chargerLesTraductions.getPourcentageTraduit(0));

    fichier.deleteDirectory(f);
    chargerLesTraductions.setRep();
  }
  @Test
  public void testGetPourcentageTraduit2(){
    int x = TestCaseMuet.getId();
    File f = new File("testDir"+x);
    f.mkdir();
    GString gsFr = new GString();
    gsFr.add("//com");
    gsFr.add("ex:test");
    gsFr.add("ex2:trad");
    gsFr.add("ex3:tradghj");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gsFr,"testDir"+x+"/fr.txt"));
    GString gs = new GString();
    gs.add("//com");
    gs.add("ex:test");
    gs.add("ex2:");
    gs.add("ex3:");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    chargerLesTraductions.setRep("testDir"+x);
    String tl []= {"test","fr"};
    chargerLesTraductions.setTLangue(tl);
    assertEquals(33,chargerLesTraductions.getPourcentageTraduit(0));

    //autre
    gs = new GString();
    gs.add("//com");
    gs.add("ex:test");
    gs.add("ex2:dfgh");
    gs.add("ex3:qzg");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    assertEquals(100,chargerLesTraductions.getPourcentageTraduit(0));

    fichier.deleteDirectory(f);
    chargerLesTraductions.setRep();
  }

  //getPourcentageTraduitAutomatiquement
  @Test
  public void testGetPourcentageTraduitAutomatiquement(){
    int x = TestCaseMuet.getId();
    File f = new File("testDir"+x);
    f.mkdir();
    GString gsFr = new GString();
    gsFr.add("//com");
    gsFr.add("ex:test");
    gsFr.add("ex2:trad");
    gsFr.add("//com2");
    gsFr.add("ex3:tradghj");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gsFr,"testDir"+x+"/fr.txt"));
    GString gs = new GString();
    gs.add("//com");
    gs.add("ex:test[auto]");
    gsFr.add("//comAllea");
    gs.add("ex2:zta[otto]");
    gs.add("ex3:");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    chargerLesTraductions.setRep("testDir"+x);
    String tl []= {"test","fr"};
    chargerLesTraductions.setTLangue(tl);
    assertEquals(33,chargerLesTraductions.getPourcentageTraduitAutomatiquement(0));

    //autre
    gs = new GString();
    gs.add("//com");
    gs.add("ex:test[auto]");
    gs.add("ex2:dfgh[auto]");
    gs.add("ex3:qzg[auto]");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    assertEquals(100,chargerLesTraductions.getPourcentageTraduitAutomatiquement(0));

    fichier.deleteDirectory(f);
    chargerLesTraductions.setRep();
  }
  @Test
  public void testCompletMapWithFullTranslatedLanguage(){
    int x = TestCaseMuet.getId();
    File f = new File("testDir"+x);
    f.mkdir();
    GString gsFr = new GString();
    gsFr.add("//com");
    gsFr.add("ex:testEN");
    gsFr.add("ex2:tradInENglish");
    gsFr.add("ex3:world");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gsFr,"testDir"+x+"/en.txt"));
    GString gs = new GString();
    gs.add("//com");
    gs.add("ex:testAF");
    gs.add("ex2:");
    gs.add("ex3:");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,"testDir"+x+"/test.txt"));
    chargerLesTraductions.setRep("testDir"+x);
    String tl []= {"test","en"};
    chargerLesTraductions.setTLangue(tl);
    g.setMap(chargerLesTraductions.chargerLesTraductions(0));
    assertEquals("testAF",g.get("ex"));
    assertEquals("ex2",g.get("ex2"));
    chargerLesTraductions.completMapWithFullTranslatedLanguage();
    assertEquals("testAF",g.get("ex"));
    assertEquals("tradInENglish",g.get("ex2"));

    fichier.deleteDirectory(f);
    chargerLesTraductions.setRep();
  }
}
