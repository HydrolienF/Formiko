package fr.formiko.formiko.interfaces;
import fr.formiko.usuel.debug; import fr.formiko.usuel.erreur; import fr.formiko.usuel.g; import fr.formiko.formiko.Main;
//def par défaut des fichiers depuis 0.79.5
import fr.formiko.formiko.interfaces.TourCreatureSansAction;
import fr.formiko.usuel.test.TestCaseMuet;
import org.junit.Test;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;

public class TourCreatureSansActionTest extends TestCaseMuet{
  //C SUPER BUGE POUR L'instant.
  // Fonctions propre -----------------------------------------------------------
  //@Test(timeout=1000) //ne marche pas...
  @Test
  public void testUnTour(){
    Main.initialisation();
    //initialise Partie :
    //Main.setPartie();
    //creature
    Creature c = new Insecte(new CCase(null));
    //Creature c = new Insecte(new CCase(new Case(0,0)));
    c.setNourriture(20);
    c.setAge(0);
    c.tour();
    assertEquals(19,c.getNourriture());
    assertEquals(1,c.getAge());
    //TODO pour l'instant ça boucle sans fin, mettre un minuteur d'1 seconde pour l'execution du test

    //fourmi
    //TODO !

  }
  @Test
  public void testUnTour2(){
    //test that an ant will grow whitout diing to the last stade: imago.
    //TODO !
    assertTrue(false); //ne pose pas de problème ...

  }
}
