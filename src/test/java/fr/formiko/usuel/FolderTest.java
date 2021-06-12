package fr.formiko.usuel;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.tests.TestCaseMuet;

import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;

public class FolderTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  public Folder ini(){
    Folder folder = new Folder();
    //if we need we can change name of sub folder for test.
    return folder;
  }
  public void contain(String s, String ts []){
    String ts2 [] = s.split(" ");
    Arrays.sort(ts2);
    Arrays.sort(ts);
    int len = ts2.length;
    for (int i=0; i<len; i++) {
      // assertTrue(s.contains(sTest));
      assertEquals(ts[i], ts2[i]);
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
    contain("languages levels maps videos sounds bin musics images",f2.list());
    f2 = new File(folder.getFolderMain()+"stable/");
    contain("languages levels maps videos sounds bin musics images",f2.list());
    f2 = new File(folder.getFolderMain()+"resourcesPacks/");
    contain("languages levels maps videos sounds bin musics images",f2.list());

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
    contain("languages levels maps videos sounds bin musics images",f2.list());
    f2 = new File(folder.getFolderMain()+"stable/");
    contain("languages levels maps videos sounds bin musics images",f2.list());
    f2 = new File(folder.getFolderMain()+"resourcesPacks/");
    contain("languages levels maps videos sounds bin musics images",f2.list());

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
    contain("languages testDir2 levels maps videos sounds bin musics images",f2.list());
    f2 = new File(folder.getFolderMain()+"stable/");
    contain("languages levels maps videos sounds bin musics images",f2.list());
    f2 = new File(folder.getFolderMain()+"resourcesPacks/");
    contain("languages levels maps videos sounds bin musics images",f2.list());

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
    String fullFolderList = folder.getFolderLanguages()+" "+folder.getFolderLevels()+" "+folder.getFolderMaps()+" "+folder.getFolderVideos()+" "+folder.getFolderSounds()+" "+folder.getFolderBin()+" "+folder.getFoldermusics()+" "+folder.getFolderImages();
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
    contain("Keys.txt Options.md",t);
    f.cleanFolder();
    t = file.list();
    //contain("stable",t));
    contain("Keys.txt",t);
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
    contain("README.md stable",t);
    // contain("qqchose.png Options.md",t,false);
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
    fichier.deleteDirectory(fileToRemove);
    folder.ini(true);
    File file = new File(folder.getFolderMain());
    String t [] = file.list();
    contain("Keys.txt Options.md resourcesPacks saves stable temporary README.md",t);
    //delete 1 folder & let it repare it.
    fichier.deleteDirectory(folder.getFolderStable());
    t = file.list();
    contain("Keys.txt Options.md resourcesPacks saves temporary README.md",t);
    assertEquals(1,folder.ini(true));
    t = file.list();
    contain("Keys.txt Options.md resourcesPacks saves stable temporary README.md",t);
    File stable = new File(folder.getFolderStable());
    contain("languages levels maps videos sounds bin musics images",stable.list());
    fichier.deleteDirectory(folder.getFolderStable()+folder.getFolderImages());
    contain("languages levels maps videos sounds bin musics",stable.list());
    assertEquals(1,folder.ini(true));
    contain("languages levels maps videos sounds bin musics images",stable.list());
    fichier.deleteDirectory(fileToRemove);
    folder.setFolderMain();
  }
  @Test
  @Disabled("Some changes have been done")
  public void testGetVersionJsonPath(){
    GString gs = lireUnFichier.lireUnFichierGs("version.json");
    assertTrue(gs.length()>0);
    fichier.deleteDirectory("version.json");
    // assertEquals(Paths.get(""),Folder.getVersionJsonPath()); //formiko may be instal & it can find Program Files rep
    File f = new File("version.json");
    try {
      f.createNewFile();
    }catch (Exception e) {assertTrue(false);}
    assertEquals(Paths.get("version.json"),Folder.getVersionJsonPath());
    File f2 = new File("app/version.json");
    File rep = new File("app");
    rep.mkdirs();
    // f2.delete();
    try {
      f2.createNewFile();
    }catch (Exception e) {assertTrue(false);}
    assertEquals(Paths.get("version.json"),Folder.getVersionJsonPath());
    f.delete();
    assertEquals(Paths.get("app/version.json"),Folder.getVersionJsonPath());
    assertTrue(fichier.deleteDirectory(rep));
    // assertEquals(Paths.get(""),Folder.getVersionJsonPath()); //formiko may be instal & it can find Program Files rep
    ecrireUnFichier.ecrireUnFichier(gs,"version.json");
  }
}
