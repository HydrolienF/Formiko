package fr.formiko.graphisme;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.graphisme.PanneauEchap;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import fr.formiko.graphisme.ini;
import fr.formiko.graphisme.PanneauBouton;

public class PanneauEchapTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testSetDesc(){
    //TODO refaire ce test lorsqu'on pourra faire des test sur des élément graphiques sans avoir besoin de faire toutes l'initialisation d'1 nouvelle Partie.
    /*Main.initialisation();
    //ini.initialiserToutLesPaneauxVide();
    Main.launch();
    PanneauEchap pe = Main.getPe();
    PanneauBouton pb = Main.getPb();
    pe.setVisible(false);
    pb.setDesc("testDesc");
    assertEquals("testDesc",pb.getDesc());
    pb.setDesc("testDesc2");
    assertEquals("testDesc2",pb.getDesc());
    pe.setVisible(false);
    pb.setDesc("testDesc3");
    assertEquals("testDesc2",pb.getDesc());*/
  }
  @Test
  public void testDoAction(){

  }
}
