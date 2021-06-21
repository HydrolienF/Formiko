package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import fr.formiko.formiko.CCase;
import fr.formiko.formiko.Case;
import fr.formiko.formiko.Creature;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Carte;
import fr.formiko.formiko.interfaces.TourCreatureSansAction;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.tests.TestCaseMuet;

public class TourCreatureSansActionTest extends TestCaseMuet{
  // Fonctions propre -----------------------------------------------------------
  private Creature ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Creature c = new Insecte(new CCase(p.getGc().getCCase(0,0).getContent()));
    return c;
  }
  @Test
  @Timeout(1)
  public void testUnTour(){
    //creature
    Creature c = ini();
    c.setNourriture(20);
    c.setAge(0);
    c.setAction(0);
    c.tour();
    //assertEquals(19,c.getNourriture()); //TODO
    assertEquals(1,c.getAge());

    c.setNourriture(20);
    c.setAge(0);
    c.setAction(10);
    c.tour = new TourCreatureSansAction();
    c.tour();
    assertEquals(19,c.getNourriture());
    assertEquals(1,c.getAge());

    //fourmi
    //TODO !

  }
  @Test
  public void testUnTour2(){
    //test that an ant will grow whitout diing to the last stade: imago.
    //TODO !

  }
}
