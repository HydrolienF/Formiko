package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.usuel.tests.TestCaseMuet;
import fr.formiko.usuel.Folder;
import fr.formiko.usuel.fichier;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.ecrireUnFichier;

import java.io.File;

public class ScriptTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testScript(){
    Folder folder = new Folder();
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
    Folder folder = new Folder();
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
    Folder folder = new Folder();
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
    Folder folder = new Folder();
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
    Folder folder = new Folder();
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
    Folder folder = new Folder();
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
    Folder folder = new Folder();
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
    Folder folder = new Folder();
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
}
