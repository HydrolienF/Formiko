package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.chargerLesTraductions;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import java.io.File;
import fr.formiko.usuel.image.image;

public class chargerLesTraductionsTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testGetLangue(){
    String t []= {"en","fr","langueTest"};
    chargerLesTraductions.setTLangue(t);
    assertEquals("en",chargerLesTraductions.getLangue(0));
    assertEquals("fr",chargerLesTraductions.getLangue(1));
    assertEquals("langueTest",chargerLesTraductions.getLangue(2));
    //si ca me marche pas.
    assertEquals("en",chargerLesTraductions.getLangue(-1));
    assertEquals("en",chargerLesTraductions.getLangue(3));
    assertEquals("en",chargerLesTraductions.getLangue(500));
    assertEquals("en",chargerLesTraductions.getLangue(-289));
    String t2 []= {};
    chargerLesTraductions.setTLangue(t2);
    assertEquals("en",chargerLesTraductions.getLangue(0));
    assertEquals("en",chargerLesTraductions.getLangue(1));
    chargerLesTraductions.setTLangue(null);
    assertEquals("en",chargerLesTraductions.getLangue(0));
    assertEquals("en",chargerLesTraductions.getLangue(1));
    //assertTrue(true);
  }
  @Test
  public void testGetLangueS(){
    String t []= {"a","bcd","épit"};
    chargerLesTraductions.setTLangue(t);
    //une langue qui y est
    assertEquals(0,chargerLesTraductions.getLangue("a"));
    assertEquals(1,chargerLesTraductions.getLangue("bcd"));
    assertEquals(2,chargerLesTraductions.getLangue("bdc"));
    assertEquals(2,chargerLesTraductions.getLangue("épit"));
    //une langue qui n'y est pas
    assertEquals(2,chargerLesTraductions.getLangue("test"));
    assertEquals(2,chargerLesTraductions.getLangue("ø"));
    //un usage imprévu
    assertEquals(-1,chargerLesTraductions.getLangue(""));
    String s = null;
    assertEquals(-1,chargerLesTraductions.getLangue(s));

    //si la langue 2 n'existe pas dans le tableau des langues.
    String t2 []= {"r"};
    chargerLesTraductions.setTLangue(t2);
    assertEquals(0,chargerLesTraductions.getLangue("r"));
    assertEquals(-1,chargerLesTraductions.getLangue("a"));
    assertEquals(-1,chargerLesTraductions.getLangue("zauvfbiano"));
  }
  //iniTLangue
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

  //créerLesFichiers
  public void testCréerLesFichiers(){
    //fonctionnement normale.
    assertTrue(chargerLesTraductions.iniTLangue());
    assertTrue(chargerLesTraductions.créerLesFichiers());
    //test dans un autre environement
    File f = new File("testDir/");
    f.mkdir();
    chargerLesTraductions.setRep("testDir/");
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
    assertTrue(image.deleteDirectory(f));
    //test avec un autre tableau de langue.
    f = new File("testDir9/");
    f.mkdir();
    String tf2 [] = {"atta.txt","n","ocotô.md"};
    chargerLesTraductions.setTLangue(tf2);
    chargerLesTraductions.setRep("testDir9/");
    assertTrue(chargerLesTraductions.créerLesFichiers());
    String tf3 [] = f.list();
    assertTrue(tableau.contient(tf3,"n.txt"));
    assertTrue(tableau.contient(tf3,"atta.txt.txt"));
    assertTrue(tableau.contient(tf3,"ocotô.md.txt"));
    assertTrue(!tableau.contient(tf3,"en.txt"));
    assertTrue(image.deleteDirectory(f));

    chargerLesTraductions.setRep();
  }

  //estLigneDeTrad
  public void testEstLigneDeTrad(){
    assertTrue(chargerLesTraductions.estLigneDeTrad("a:"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("a:a"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("veiuogz:tehtkph"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("tcuva.t.345.ty:zety"));
    assertTrue(chargerLesTraductions.estLigneDeTrad("efoaeègzn-|`gz:"));
    //false
    assertTrue(!chargerLesTraductions.estLigneDeTrad("ezgriyld/·vbioneg"));
    assertTrue(!chargerLesTraductions.estLigneDeTrad(":"));
    assertTrue(!chargerLesTraductions.estLigneDeTrad(":zeeut"));
    assertTrue(!chargerLesTraductions.estLigneDeTrad("a:etey.:R"));
    //commentaire
    assertTrue(!chargerLesTraductions.estLigneDeTrad("//"));
    assertTrue(!chargerLesTraductions.estLigneDeTrad("//eztt"));
    //ligne vide
    assertTrue(!chargerLesTraductions.estLigneDeTrad(""));
    String s = null;
    assertTrue(!chargerLesTraductions.estLigneDeTrad(s));
  }

  //getTableauDesTrad
  public void testGetTableauDesTrad(){
    File f = new File("testDir10/");
    f.mkdir();
    File ft = new File("testDir10/te.txt");
    try {
      assertTrue(ft.createNewFile());assertTrue(ft.exists());
    }catch (Exception e) {assertTrue(false);}
    String tLangue [] = {"te","ts"};
    chargerLesTraductions.setTLangue(tLangue);
    chargerLesTraductions.setRep("testDir10/");
    String t [] = chargerLesTraductions.getTableauDesTrad(0);
    assertEquals(0,t.length);
    //TODO testé également un tableau avec un fichier non vide.

    /*String t2 [] = chargerLesTraductions.getTableauDesTrad(1);
    assertEquals(0,t2.length);
    String t3 [] = chargerLesTraductions.getTableauDesTrad(2);
    assertEquals(0,t3.length);*/

    assertTrue(image.deleteDirectory(f));
    chargerLesTraductions.setRep();
  }

  //getTableauDesCmd

  //chargerLesTraductions

  //chargerLesTraductionsSansCommande

  //ajouterObjetMap

  //ajouterTradAuto

  //TODO maj déplacer dans un usuel/char.java

  //getPourcentageTraduit

  //getPourcentageTraduitAutomatiquement

  //fini
}
