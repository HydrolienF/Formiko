package fr.formiko.usuel;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.sauvegarderUnePartie;
import fr.formiko.usuel.tests.TestCaseMuet;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import fr.formiko.usuel.maths.allea;
import fr.formiko.formiko.Partie;
import java.io.File;

public class sauvegarderUnePartieTest extends TestCaseMuet{
  private File f=null;
  // Fonctions propre -----------------------------------------------------------
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
  public void ini(){
    int x = allea.getAllea(10000);
    sauvegarderUnePartie.sauvegarder(new Partie(), "test"+x);
    String s = sauvegarderUnePartie.getNomDuFichierComplet();
    f = new File(s);
  }
  @Test
  @Before
  public void testSauvegarder(){
    ini();
    assertTrue(f.exists());//il existe.
    assertTrue(f.isFile());//c'est un fichier pas un dossier.
    assertTrue(f.delete());
  }
  @Test
  @Before
  public void testCharger(){
    Main.setPartie(new Partie()); //nouvelle partie vide.
    debug.setDPG(false);
    Main.initialisation();
    Main.setPartie(Main.getPartieParDéfaut());
    sauvegarderUnePartie.sauvegarder(Main.getPartie(), "testVraisPartie");
    Partie p = sauvegarderUnePartie.charger("testVraisPartie");
    assertTrue(Main.getPartie().toString().equals(p.toString()));//@a pour l'instant les Parties ne sont pas égale.
    assertTrue(sauvegarderUnePartie.supprimer("testVraisPartie"));
    assertTrue(!sauvegarderUnePartie.supprimer("testVraisPartie"));//le fichier n'existe déja plus.
  }
  @Test
  @Before
  public void testSupprimer(){
    ini();
    assertTrue(f.exists());//il existe.
    assertTrue(f.isFile());//c'est un fichier pas un dossier.
    assertTrue(f.delete());//le fichier ce supprime bien.
  }

}
