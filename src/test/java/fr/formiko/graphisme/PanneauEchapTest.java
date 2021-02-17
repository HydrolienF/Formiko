package fr.formiko.views.gui2d;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.views.gui2d.PanneauEchap;
import fr.formiko.usuel.tests.TestCaseMuet;
import org.junit.jupiter.api.Test;
import fr.formiko.views.gui2d.ini;
import fr.formiko.views.gui2d.PanneauBouton;

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
