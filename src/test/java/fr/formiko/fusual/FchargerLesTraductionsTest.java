package fr.formiko.fusual;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.chargerLesTraductions;
import fr.formiko.usual.g;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.tableau;
import fr.formiko.usual.fichier;

import java.io.File;
import java.util.Map;

public class FchargerLesTraductionsTest extends TestCaseMuet {
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
    assertEquals(107,chargerLesTraductions.getTLangue().length);
    chargerLesTraductions.setRep(null);
  }
  @Test
  public void testCreerLesFichiers(){
    //fonctionnement normale.
    chargerLesTraductions.setRep(null);
    assertTrue(chargerLesTraductions.iniTLangue());
    assertTrue(chargerLesTraductions.créerLesFichiers());
    //test dans un autre environement
    int x = TestCaseMuet.getId();
    File f = new File("testDir"+x);
    f.mkdir();
    chargerLesTraductions.setRep("testDir"+x);
    assertTrue(chargerLesTraductions.créerLesFichiers());
    String tf [] = f.list();
    assertEquals(107,tf.length);
    assertTrue(tableau.contient(tf,"en.txt"));
    assertTrue(tableau.contient(tf,"fr.txt"));
    assertTrue(tableau.contient(tf,"zu.txt"));
    assertTrue(tableau.contient(tf,"eo.txt"));
    assertTrue(!tableau.contient(tf,"ep.txt"));
    assertTrue(!tableau.contient(tf,"zfag.txt"));
    assertTrue(!tableau.contient(tf,"eo"));
    assertTrue(fichier.deleteDirectory(f));
  }
  @Test
  public void testGetTableauDesCmd(){
    String t [] = chargerLesTraductions.getTableauDesCmd();
    assertTrue(t.length>1);//Le fichier contient des lignes (et donc a bien été lu).
  }
  //chargerLesTraductions
  @Test
  public void testChargerLesTraductions(){
    // Main.iniTranslationFolder();
    chargerLesTraductions.setRep(null);
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
    // Main.iniTranslationFolder();
    chargerLesTraductions.setRep(null);
    assertTrue(chargerLesTraductions.iniTLangue());
    Map<String, String> zu = chargerLesTraductions.chargerLesTraductions(chargerLesTraductions.getLanguage("zu"));
    assertEquals("testZu",zu.get("test"));
    //si c'est pas une langue existante.
    Map<String, String> zz = chargerLesTraductions.chargerLesTraductions(chargerLesTraductions.getLanguage("zz"));
    assertEquals("testEn",zz.get("test"));//si la langue n'existe pas on passe a l'anglais.
    assertTrue(!zz.get("test").equals("testZz"));
  }
}
