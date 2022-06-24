package fr.formiko.usual;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.usual.Folder;
import fr.formiko.usual.Save;
import fr.formiko.usual.fichier;
import fr.formiko.tests.TestCaseMuet;

import java.io.File;

public class SaveTest extends TestCaseMuet {
  private Folder f;
  @Test
  public void testSave(){
    f = new Folder(Main.getView());
    Main.setFolder(f);
    Save save = Save.getSave();
    int idS = save.getIdS();
    assertTrue(idS>-1);
    save.addSave();
    assertEquals(idS+1,save.getIdS());
  }
  @Test
  public void testSave2(){
    new Save("unexistingFolder8747/.save");
    Save save = Save.getSave();
    int idS = save.getIdS();
    assertEquals(1,idS);
    save.addSave();
    assertEquals(idS+1,save.getIdS());
    save.save();
    File f = new File("unexistingFolder8747/.save");
    assertTrue(!f.exists());
  }
  @Test
  public void testSave3(){
    int x = TestCaseMuet.getId();
    File dir = new File("testDir"+x);
    dir.mkdir();
    new Save("testDir"+x+"/.save");
    Save save = Save.getSave();
    int idS = save.getIdS();
    assertEquals(1,idS);
    save.addSave();
    assertEquals(idS+1,save.getIdS());
    save.save();
    File f = new File("testDir"+x+"/.save");
    assertTrue(f.exists());
    assertTrue(dir.exists());
    assertTrue(fichier.deleteDirectory(dir));
  }
  @Test
  public void testSave4(){
    int x = TestCaseMuet.getId();
    File dir = new File("testDir"+x);
    dir.mkdir();
    new Save("testDir"+x+"/.save");
    File f3 = new File("testDir"+x+"/.save");
    try {
      f3.createNewFile();
    }catch (Exception e) {}
    assertTrue(f3.exists());
    Save save = Save.getSave();
    int idS = save.getIdS();
    assertEquals(1,idS);
    save.addSave();
    assertEquals(idS+1,save.getIdS());
    assertTrue(dir.exists());
    assertTrue(fichier.deleteDirectory(dir));
  }
}
