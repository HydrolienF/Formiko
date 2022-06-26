package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.tests.TestCaseMuet;
import fr.formiko.fusual.FFolder;
import fr.formiko.usual.fichier;
import fr.formiko.usual.structures.listes.GString;
import fr.formiko.usual.ecrireUnFichier;
import org.junit.jupiter.api.AfterAll;

import java.io.File;

public class ScriptTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testScript(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString(); gs.add("1 line");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderLevels()+"test.formiko"));
    Script script = new Script("test");
    assertTrue(script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testScript2(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString(); gs.add("1 line");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderLevels()+"test.formiko"));
    Script script = new Script("test.formiko");
    assertTrue(script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testScript3(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString(); gs.add("1 line");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderBin()+"test.formiko"));
    Script script = new Script("test");
    assertTrue(!script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testScript4(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString(); gs.add("1 line");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+"test.formiko"));
    Script script = new Script("test");
    assertTrue(!script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testScript5(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString();
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderLevels()+"test.formiko"));
    Script script = new Script("test.formiko");
    assertTrue(!script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testScript6(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString(); gs.add("1 line");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderLevels()+"test.md"));
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderLevels()+"test"));
    Script script = new Script("test");
    assertTrue(!script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testScript7(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString(); gs.add("1 line");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderLevels()+"test.formiko"));
    Script script = new Script("test");
    assertTrue(script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testScript8(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    int x=getId();
    File f = new File("testScriptTemporaryFolder"+x+"/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testScriptTemporaryFolder"+x+"/");
    assertEquals(29,folder.ini(false));
    GString gs = new GString(); gs.add("1 line");
    assertTrue(ecrireUnFichier.ecrireUnFichier(gs,folder.getFolderStable()+folder.getFolderLevels()+"test.formiko"));
    Script script = new Script("test");
    assertTrue(script.script());

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @AfterAll
  public static void clean(){
    FFolder folder = new FFolder(Main.getView());
    Main.setFolder(folder);
    fichier.deleteDirectory("null");
  }
}
