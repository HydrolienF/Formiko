package fr.formiko.usuel;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import org.junit.jupiter.api.Disabled;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.io.File;

public class FolderTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  public Folder ini(){
    Folder folder = new Folder();
    //if we need we can change name of sub folder for test.
    return folder;
  }
  public void contain(String s, String ts []){
    for (String sTest : ts ) {
      assertTrue(s.contains(sTest));
    }
  }
  @Test
  public void testIni(){
    Folder folder = ini();
    folder.setFolderMain("testMain/");
    File f = new File(folder.getFolderMain());
    File fToRemove = new File("testMain/");
    fichier.deleteDirectory(f);
    assertEquals(29,folder.ini(false));
    contain("temporary stable resourcesPacks saves",f.list());
    File f2 = new File(folder.getFolderMain()+"temporary/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());
    f2 = new File(folder.getFolderMain()+"stable/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());
    f2 = new File(folder.getFolderMain()+"resourcesPacks/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());

    assertTrue(fichier.deleteDirectory(fToRemove));
    folder.setFolderMain();
  }
  @Test
  public void testIni2(){
    int x = getId();
    Folder folder = ini();
    folder.setFolderMain("testMain"+x+"/");
    File fToRemove = new File("testMain"+x+"/");
    File f = new File(folder.getFolderMain());
    fichier.deleteDirectory(f);
    assertTrue(f.mkdirs());
    File f2 = new File(folder.getFolderMain()+"temporary/");
    assertTrue(f2.mkdirs());
    assertEquals(27,folder.ini(false));
    contain("temporary stable resourcesPacks saves",f.list());
    f2 = new File(folder.getFolderMain()+"temporary/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());
    f2 = new File(folder.getFolderMain()+"stable/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());
    f2 = new File(folder.getFolderMain()+"resourcesPacks/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());

    assertTrue(fichier.deleteDirectory(fToRemove));
    folder.setFolderMain();
  }
  @Test
  public void testIni3(){
    Folder folder = ini();
    folder.setFolderMain("testMain/");
    File fToRemove = new File("testMain/");
    File f = new File(folder.getFolderMain());
    fichier.deleteDirectory(f);
    assertTrue(f.mkdirs());
    File f2 = new File(folder.getFolderMain()+"temporary/");
    assertTrue(f2.mkdirs());
    f2 = new File(folder.getFolderMain()+"otherDir/");
    assertTrue(f2.mkdir());
    f2 = new File(folder.getFolderMain()+"temporary/levels/");
    assertTrue(f2.mkdir());
    File f3 = new File(folder.getFolderMain()+"temporary/testDir2");
    assertTrue(f3.mkdir());
    File f4 = new File(folder.getFolderMain()+"temporary/levels/testSubSubDir");
    assertTrue(f4.mkdir());
    assertEquals(26,folder.ini(false));
    contain("otherDir temporary stable resourcesPacks saves",f.list());
    f2 = new File(folder.getFolderMain()+"temporary/");
    contain("languages testDir2 levels maps videos sounds bin musiques images",f2.list());
    f2 = new File(folder.getFolderMain()+"stable/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());
    f2 = new File(folder.getFolderMain()+"resourcesPacks/");
    contain("languages levels maps videos sounds bin musiques images",f2.list());

    assertTrue(f3.exists());
    assertTrue(f4.exists());
    assertTrue(fichier.deleteDirectory(fToRemove));
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
    contain(folder.getFolderTemporary()+" otherDir "+folder.getFolderStable()+" "+folder.getFolderResourcesPacks(),f.list()));
    f2 = new File("testMain/"+folder.getFolderTemporary());
    contain("otherSubDir "+fullFolderList,f2.list()));
    f2 = new File("testMain/"+folder.getFolderStable());
    contain(fullFolderList,f2.list()));
    f2 = new File("testMain/"+folder.getFolderResourcesPacks());
    contain(fullFolderList,f2.list()));

    assertTrue(fichier.deleteDirectory(f));
    folder.setFolderMain();
  }*/

  @Test
  public void testCleanFolder(){
    Folder f = new Folder();
    int x = getId();
    f.setFolderMain("data"+x+"/");
    f.ini(false);
    File fileToRemove = new File("data"+x+"/");
    fichier.deleteDirectory(fileToRemove);
    File file = new File(f.getFolderMain()+"Options.md");
    file.mkdirs();
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File(f.getFolderMain()+"Keys.txt");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    //file README.md
    file = new File(f.getFolderMain());
    String t [] = file.list();
    contain("Keys.txt Options.md resourcesPacks saves stable temporary",t);
    f.cleanFolder();
    t = file.list();
    //contain("stable",t));
    contain("Keys.txt stable",t);
    fichier.deleteDirectory(fileToRemove);
    f.setFolderMain();
  }
  @Test
  public void testCleanFolder2(){
    Folder f = new Folder();
    int x = getId();
    f.setFolderMain("data"+x+"/");
    File fileToRemove = new File("data"+x+"/");
    fichier.deleteDirectory(fileToRemove);
    f.ini(false);
    File file = new File(f.getFolderMain()+"Options.md");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File(f.getFolderMain()+"README.md");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File(f.getFolderMain()+"qqchose.png");
    try {file.createNewFile();}catch (Exception e) {assertTrue(false);}
    file = new File(f.getFolderMain());
    String t [] = file.list(); tableau.sort(t);
    contain("Options.md README.md qqchose.png resourcesPacks saves stable temporary",t);
    f.cleanFolder();
    t = file.list(); tableau.sort(t);
    contain("README.md qqchose.png stable",t);
    fichier.deleteDirectory(fileToRemove);
    f.setFolderMain();
  }
  @Test
  @Disabled("Tooo long for standard test")
  public void testDownloadData(){
    Folder folder = new Folder();
    int x = getId();
    folder.setFolderMain("data"+x+"/");
    File fileToRemove = new File("data"+x+"/");
    File file = new File(folder.getFolderMain());
    file.mkdirs();
    folder.downloadData();
    String t [] = file.list();
    contain("Keys.txt Options.md resourcesPacks saves stable temporary README.md",t);
    fichier.deleteDirectory(fileToRemove);
    folder.setFolderMain();
  }
  @Test
  @Disabled("Tooo long for standard test")
  public void testDownloadData2(){
    Folder folder = new Folder();
    int x = getId();
    folder.setFolderMain("data"+x+"/");
    File fileToRemove = new File("data"+x+"/");
    folder.ini(true);
    File file = new File(folder.getFolderMain());
    String t [] = file.list();
    contain("Keys.txt Options.md resourcesPacks saves stable temporary README.md",t);
    //delete 1 folder & let it repare it.
    fichier.deleteDirectory(folder.getFolderStable());
    t = file.list();
    contain("Keys.txt Options.md resourcesPacks saves temporary README.md",t);
    assertEquals(1,folder.ini(true));
    contain("Keys.txt Options.md resourcesPacks saves stable temporary README.md",t);
    File stable = new File(folder.getFolderStable());
    contain("languages levels maps videos sounds bin musiques images",stable.list());
    fichier.deleteDirectory(folder.getFolderStable()+folder.getFolderImages());
    contain("languages levels maps videos sounds bin musiques",stable.list());
    assertEquals(1,folder.ini(true));
    contain("languages levels maps videos sounds bin musiques images",stable.list());
    fichier.deleteDirectory(fileToRemove);
    folder.setFolderMain();
  }
}
