package fr.formiko.usuel.maths;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.usuel.maths.allea;
import fr.formiko.usuel.tableau;
import fr.formiko.usuel.tests.TestCaseMuet;
import org.junit.jupiter.api.Test;

public class alleaTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  //@RepeatedTest(10)
  public void testGetAllea(){
    //éviter de sortir des limites
    assertTrue(allea.getAllea(5)<5);
    assertTrue(allea.getAllea(5)>=0);
    //on suppose que getAllea ne renvoie pas plus une valeur qu'une autre faute de mieux
  }
  @Test
  public void testGetAlleaErr(){
    assertEquals(-1,allea.getAllea(-1));
    assertEquals(-1,allea.getAllea(-100));
    assertEquals(-1,allea.getAllea(0));
  }
  @Test
  //@RepeatedTest(10)
  public void testGetAlléaDansTableau(){
    int t [] = {0,1,-2,3};
    assertTrue(tableau.contient(t,allea.getAlléaDansTableau(t)));
  }

  @Test
  public void testFluctuer(){
    int x=-20;
    assertEquals(x,allea.fluctuer(x,10));
    assertEquals(x,allea.fluctuer(x,40));
    x=0;
    assertEquals(x,allea.fluctuer(x,10));
    assertEquals(x,allea.fluctuer(x,40));
    x=20;
    //trop de fluctuation
    assertEquals(x,allea.fluctuer(x,120));
    assertEquals(x,allea.fluctuer(x,2120));//ne doit pas etre pris pour 72.#test de str.java.
    //pas assez
    assertEquals(x,allea.fluctuer(x,0));
    assertEquals(x,allea.fluctuer(x,-20));
  }
  @Test
  //@RepeatedTest(10)
  public void testFluctuerCorrect(){
    int x=30;
    int f = allea.fluctuer(x,40);//30 + ou moins 40%
    assertTrue(f<=(30*1.4));
    assertTrue(f>=(30*0.6));
    x=30;
    f = allea.fluctuer(x);//30 + ou moins 10%
    assertTrue(f<=(30*1.1));
    assertTrue(f>=(30*0.9));
  }
}
