package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Carte;
import fr.formiko.formiko.interfaces.TourCreatureSansAction;
import fr.formiko.tests.TestCaseMuet;

public class TourCreatureSansActionTest extends TestCaseMuet{
  // FUNCTIONS -----------------------------------------------------------------
  private Creature ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Creature c = new Insecte(new CCase(p.getGc().getCCase(0,0).getContent()));
    // c.tour = new TourCreatureSansAction(); It should do the same, insect or not.
    return c;
  }
  @Test
  @Timeout(1)
  public void testUnTour(){
    //creature
    Creature c = ini();
    c.setFood(20);
    c.setAge(0);
    c.setAction(0);
    Main.getPartie().addTour();
    c.tour();
    c.endTurn();
    //assertEquals(19,c.getFood()); //TODO
    assertEquals(1,c.getAge());

    c.setFood(20);
    c.setAge(0);
    c.setAction(10);
    Main.getPartie().addTour();
    c.tour = new TourCreatureSansAction();
    c.tour();
    c.endTurn();
    assertEquals(19,c.getFood());
    assertEquals(1,c.getAge());

    //fourmi
    //TODO !

  }
  @Test
  public void testUnTour2(){
    //test that an ant will grow whitout diing to the last stade: imago.
    //TODO !

  }
  @Test
  public void testUnTourWithoutPartieTurnCptUpdate(){
    Creature c = ini();
    c.setFood(20);
    c.setAge(0);
    c.setAction(0);
    Main.getPartie().addTour();
    c.tour();
    c.endTurn();
    assertEquals(1,c.getAge());

    c.setFood(20);
    c.setAge(0);
    c.setAction(10);
    // Main.getPartie().addTour();
    c.tour = new TourCreatureSansAction();
    c.tour();
    c.endTurn();
    //neither tour() nor endTurn() should change anything because Partie turn cpt haven't been update.
    assertEquals(20,c.getFood());
    assertEquals(0,c.getAge());

    c.setAction(0); //same as last 1 by only with no action.
    c.tour = new TourInsecte();
    c.tour();
    c.endTurn();
    //neither tour() nor endTurn() should change anything because Partie turn cpt haven't been update.
    assertEquals(20,c.getFood());
    assertEquals(0,c.getAge());
  }
}
