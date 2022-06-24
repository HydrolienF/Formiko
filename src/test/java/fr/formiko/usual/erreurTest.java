package fr.formiko.usual;

import org.junit.jupiter.api.Test;

import fr.formiko.usual.erreur;
import fr.formiko.tests.TestCaseMuet;


public class erreurTest extends TestCaseMuet{

  // FUNCTIONS -----------------------------------------------------------------
  @Test
  public void testGetCurentClassAndMethodName(){
    //line number matter for the next assert.
    assertEquals("erreurTest.testGetCurentClassAndMethodName l15",erreur.getCurentClassAndMethodName());
    anOtherFuctionToDoTestWith(); //even, if it have been call from here only last function will be print.
  }
  private void anOtherFuctionToDoTestWith(){
    //line number matter for the next assert.
    assertEquals("erreurTest.anOtherFuctionToDoTestWith l20",erreur.getCurentClassAndMethodName());
  }

}
