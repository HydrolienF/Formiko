package fr.formiko.usuel.tests;

import org.junit.jupiter.api.Assertions;

import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;

public class TestCaseMuet extends Assertions{

  private static int idCpt=0;

  public TestCaseMuet(){
    super();
    erreur.setMuet(true);
    debug.setAffLesEtapesDeRésolution(false);
    debug.setAffLesPerformances(false);
    debug.setAffG(false);
  }

  public static int getId(){return idCpt++;}
}
