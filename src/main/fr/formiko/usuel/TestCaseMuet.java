package fr.formiko.usuel.test;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import junit.framework.TestCase;

public class TestCaseMuet extends TestCase{
  public TestCaseMuet(){
    super();
    erreur.setMuet(true);
    debug.setAffLesEtapesDeRésolution(false);
  }
}
