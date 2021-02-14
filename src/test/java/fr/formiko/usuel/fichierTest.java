package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.fichier;
import fr.formiko.usuel.tests.TestCaseMuet;
import org.junit.jupiter.api.Test;
import java.io.File;
import fr.formiko.usuel.listes.GString;
import fr.formiko.usuel.ecrireUnFichier;

public class fichierTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testListerLesFichiersDuRep(){
    ecrireUnFichier.ecrireUnFichier(new GString(),"fileFichierTest.ja");
    File f = new File("fileFichierTest.ja");
    assertTrue(f.exists());
    File d = new File("repFichierTest");
    d.mkdir();
    GString gs = fichier.listerLesFichiersDuRep(f);
    assertEquals(1,gs.length());
    GString gs2 = new GString(); gs2.add("fileFichierTest.ja");
    //assertEquals(gs2,gs);
    assertTrue(gs2.equals(gs));
    gs = fichier.listerLesFichiersDuRep(d);//juste un répertoire vide n'affiche rien.
    assertTrue(new GString().equals(gs));

    assertTrue(fichier.deleteDirectory(d));
    assertTrue(f.delete());
  }
  @Test
  public void testListerLesFichiersDuRep2(){
    File d = new File("repFichierTest");
    File d2 = new File("repFichierTest/sousRep/");
    d.mkdir();
    d2.mkdir();
    GString gs = fichier.listerLesFichiersDuRep(d);//juste 2 répertoire vide n'affiche rien.
    assertTrue(new GString().equals(gs));
    ecrireUnFichier.ecrireUnFichier(new GString(),"repFichierTest/f2.ja");
    ecrireUnFichier.ecrireUnFichier(new GString(),"repFichierTest/sousRep/f1.ja");
    GString gs2 = new GString();
    gs2.add("repFichierTest/sousRep/f1.ja");
    gs2.add("repFichierTest/f2.ja");
    gs = fichier.listerLesFichiersDuRep(d);//Contient 2 fichier.
    assertTrue(gs2.equals(gs));

    assertTrue(fichier.deleteDirectory(d));
  }


  //deleteDirectory
  @Test
  public void testDeletedDirectory(){
    File f = new File("testDir3");
    File f2 = new File("testDir3/testSubDir");
    File f3 = new File("testDir3/testSubDir/test.txt");
    f.mkdir();f2.mkdir();
    try {
      f3.createNewFile();
    }catch (Exception e) {}
    assertTrue(fichier.deleteDirectory(f));
  }
  @Test
  public void testDeletedDirectoryS(){
    File f = new File("testDir4");
    File f3 = new File("testDir4/tetest.txt");
    f.mkdir();
    try {
      f3.createNewFile();
    }catch (Exception e) {}
    assertTrue(fichier.deleteDirectory("testDir4"));
  }

}
