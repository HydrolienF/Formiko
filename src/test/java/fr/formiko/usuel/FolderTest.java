package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.io.File;

public class FolderTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  public Folder ini(){
    Folder folder = new Folder();
    //if we need we can change name of sub folder for test.
    return folder;
  }
  @Test
  public void testIni(){
    Folder folder = ini();
    File f = new File("testMain/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testMain/");
    assertEquals(29,folder.ini());
    assertEquals("temporary stable resourcesPacks saves",tableau.tableauToString(f.list()));
    File f2 = new File("testMain/temporary/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));
    f2 = new File("testMain/stable/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));
    f2 = new File("testMain/resourcesPacks/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testIni2(){
    int x = getId();
    Folder folder = ini();
    File f = new File("testMain"+x+"/");
    fichier.deleteDirectory(f);
    assertTrue(f.mkdir());
    folder.setFolderMain("testMain"+x+"/");
    File f2 = new File("testMain"+x+"/temporary/");
    assertTrue(f2.mkdir());
    assertEquals(27,folder.ini());
    assertEquals("temporary stable resourcesPacks saves",tableau.tableauToString(f.list()));
    f2 = new File("testMain"+x+"/temporary/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));
    f2 = new File("testMain"+x+"/stable/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));
    f2 = new File("testMain"+x+"/resourcesPacks/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }
  @Test
  public void testIni3(){
    Folder folder = ini();
    File f = new File("testMain/");
    fichier.deleteDirectory(f);
    assertTrue(f.mkdir());
    folder.setFolderMain("testMain/");
    File f2 = new File("testMain/temporary/");
    assertTrue(f2.mkdir());
    f2 = new File("testMain/otherDir/");
    assertTrue(f2.mkdir());
    f2 = new File("testMain/temporary/levels/");
    assertTrue(f2.mkdir());
    File f3 = new File("testMain/temporary/testDir2");
    assertTrue(f3.mkdir());
    File f4 = new File("testMain/temporary/levels/testSubSubDir");
    assertTrue(f4.mkdir());
    assertEquals(26,folder.ini());
    assertEquals("otherDir temporary stable resourcesPacks saves",tableau.tableauToString(f.list()));
    f2 = new File("testMain/temporary/");
    assertEquals("languages testDir2 levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));
    f2 = new File("testMain/stable/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));
    f2 = new File("testMain/resourcesPacks/");
    assertEquals("languages levels maps videos sounds bin musiques images",tableau.tableauToString(f2.list()));

    assertTrue(f3.exists());
    assertTrue(f4.exists());
    assertTrue(fichier.deleteDirectory(f));
    assertTrue(!f3.exists());
    assertTrue(!f4.exists());
    assertTrue(!f.exists());
    folder.setFolderMain();
  }
  /*
  @Test
  public void testIni3(){
    Folder folder = new Folder();
    String fullFolderList = folder.getFolderLanguages()+" "+folder.getFolderLevels()+" "+folder.getFolderMaps()+" "+folder.getFolderVideos()+" "+folder.getFolderSounds()+" "+folder.getFolderBin()+" "+folder.getFolderMusiques()+" "+folder.getFolderImages();
    File f = new File("testMain/");
    fichier.deleteDirectory(f);
    folder.setFolderMain("testMain/");
    File f2 = new File("testMain/"+folder.getFolderTemporary());
    f2.mkdir();
    f2 = new File("testMain/otherDir/");
    f2.mkdir();
    f2 = new File("testMain/"+folder.getFolderTemporary()+"otherSubDir/");
    f2.mkdir();
    assertEquals(27,folder.ini());
    assertEquals(folder.getFolderTemporary()+" otherDir "+folder.getFolderStable()+" "+folder.getFolderResourcesPacks(),tableau.tableauToString(f.list()));
    f2 = new File("testMain/"+folder.getFolderTemporary());
    assertEquals("otherSubDir "+fullFolderList,tableau.tableauToString(f2.list()));
    f2 = new File("testMain/"+folder.getFolderStable());
    assertEquals(fullFolderList,tableau.tableauToString(f2.list()));
    f2 = new File("testMain/"+folder.getFolderResourcesPacks());
    assertEquals(fullFolderList,tableau.tableauToString(f2.list()));

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }*/

  @Test
  public void testCleanFolder(){
    Folder f = new Folder();
    int x = getId();
    f.setFolderMain("data"+x+"/");
    f.ini();
    File file = new File("data"+x+"/"+"Options.md");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File("data"+x+"/"+"Keys.txt");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    //file README.md
    file = new File("data"+x+"/");
    String t [] = file.list(); tableau.sort(t);
    assertEquals("Keys.txt Options.md resourcesPacks saves stable temporary",tableau.tableauToString(t));
    f.cleanFolder();
    t = file.list(); tableau.sort(t);
    assertEquals("stable",tableau.tableauToString(t));
    fichier.deleteDirectory(file);
    f.setFolderMain();
  }
  @Test
  public void testCleanFolder2(){
    Folder f = new Folder();
    int x = getId();
    f.setFolderMain("data"+x+"/");
    f.ini();
    File file = new File("data"+x+"/"+"Options.md");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File("data"+x+"/"+"README.md");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File("data"+x+"/"+"qqchose.png");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File("data"+x+"/");
    String t [] = file.list(); tableau.sort(t);
    assertEquals("Options.md README.md qqchose.png resourcesPacks saves stable temporary",tableau.tableauToString(t));
    f.cleanFolder();
    t = file.list(); tableau.sort(t);
    assertEquals("README.md qqchose.png stable",tableau.tableauToString(t));
    fichier.deleteDirectory(file);
    f.setFolderMain();
  }
}
