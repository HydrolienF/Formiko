package fr.formiko.tests;

import org.junit.jupiter.api.Assertions;

import fr.formiko.usual.debug;
import fr.formiko.usual.erreur;

public class TestCaseMuet extends Assertions {

  private static int idCpt=0;

  public TestCaseMuet(){
    super();
    erreur.setMuet(true);
    debug.setMessage(false);
    debug.setPerformance(false);
    debug.setAffG(false);
  }

  public static int getId(){return idCpt++;}
}
