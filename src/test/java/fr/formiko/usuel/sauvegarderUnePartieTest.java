package fr.formiko.usuel;

// import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.formiko.triche;

import java.io.File;

public class sauvegarderUnePartieTest extends TestCaseMuet{
  private File f=null;
  // FUNCTIONS -----------------------------------------------------------------
  public void ini(){
    Main.initialisation();
    int x = allea.getAllea(10000);
    sauvegarderUnePartie.sauvegarder(new Partie(), "test"+x);
    String s = sauvegarderUnePartie.getNomDuFichierComplet();
    f = new File(s);
  }
  @Test
  public void testGetNomDuFichierComplet(){
    int x = allea.getAllea(10000);
    sauvegarderUnePartie.setS("test"+x);
    String s = sauvegarderUnePartie.getNomDuFichierComplet();
    assertTrue(s.contains(".save"));
    assertTrue(s.contains(sauvegarderUnePartie.getRep()));
    int y = allea.getAllea(10000);
    sauvegarderUnePartie.setS("test"+y+"save");
    String s2 = sauvegarderUnePartie.getNomDuFichierComplet();
    assertTrue(s2.contains(".save"));
    assertTrue(s2.contains(sauvegarderUnePartie.getRep()));
  }
  @Test
  //@BeforeAll
  public void testSauvegarder(){
    ini();
    assertTrue(f.exists());//il existe.
    assertTrue(f.isFile());//c'est un fichier pas un dossier.
    // try {
    //   Files.delete(f.toPath());
    // }catch (Exception e) {
    //   erreur.println(e);
    //   assertTrue(false);
    // }
    // FileUtils.forceDelete(f)
    // int k=0;
    // while (!f.delete()) {
    //   System.gc();
    //   Temps.pause(500);
    //   if(k==10){assertTrue(false);}
    //   k++;
    // }
    // assertTrue(fichier.deleteDirectory(f));
    assertTrue(f.delete());
    assertTrue(!f.exists());
  }
  @Test
  //@BeforeAll
  public void testCharger(){
    Main.setPartie(new Partie()); //nouvelle partie vide.
    debug.setDPG(false);
    Main.initialisation();
    Main.setPartie(Partie.getDefautlPartie());
    sauvegarderUnePartie.sauvegarder(Main.getPartie(), "testVraisPartie");
    Partie p = sauvegarderUnePartie.charger("testVraisPartie");
    assertTrue(Main.getPartie().equals(p));
    assertTrue(sauvegarderUnePartie.supprimer("testVraisPartie"));
    assertTrue(!sauvegarderUnePartie.supprimer("testVraisPartie"));//le fichier n'existe déja plus.
  }
  @Test
  public void testSupprimer(){
    ini();
    assertTrue(f.exists());//il existe.
    assertTrue(f.isFile());//c'est un fichier pas un dossier.
    assertTrue(f.delete());//le fichier ce supprime bien.
  }
  @Test
  public void testSaveId(){
    int x = sauvegarderUnePartie.getSave().getIdS()+1;
    ini();
    assertEquals(x,sauvegarderUnePartie.getSave().getIdS());
    assertTrue(f.delete());
  }
  @Test
  public void testSaveWithPLayerSelected(){
    Main.setPartie(new Partie()); //nouvelle partie vide.
    debug.setDPG(false);
    Main.initialisation();
    Main.setPartie(Partie.getDefautlPartie());
    Main.getPartie().initialisationElément();
    assertTrue(Main.getPartie().setPlayingAnt(triche.getFourmiParId("1")));
    sauvegarderUnePartie.sauvegarder(Main.getPartie(), "testVraisPartie");
    Partie p = sauvegarderUnePartie.charger("testVraisPartie");
    assertTrue(Main.getPartie().equals(p));
    assertTrue(sauvegarderUnePartie.supprimer("testVraisPartie"));
    assertTrue(!sauvegarderUnePartie.supprimer("testVraisPartie"));//le fichier n'existe déja plus.
  }
  @Test
  public void testSaveWithoutPLayerSelected(){
    Main.setPartie(new Partie()); //nouvelle partie vide.
    debug.setDPG(false);
    Main.initialisation();
    Main.setPartie(Partie.getDefautlPartie());
    Main.getPartie().initialisationElément();
    sauvegarderUnePartie.sauvegarder(Main.getPartie(), "testVraisPartie");
    Partie p = sauvegarderUnePartie.charger("testVraisPartie");
    assertEquals(p, null);
  }
}
