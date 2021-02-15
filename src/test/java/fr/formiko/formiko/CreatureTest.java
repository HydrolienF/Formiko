package fr.formiko.formiko;


import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Creature;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Insecte;
import fr.formiko.formiko.Main;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;

public class CreatureTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testIsHungry(){
    Main.initialisation();
    GCase gc = new GCase(1,1);
    Insecte i = new Insecte(gc.getCCaseAlléa());
    i.setNourritureMax(10);
    i.setNourriture(5);
    assertTrue(!i.isHungry(0));
    assertTrue(!i.isHungry(20));
    assertTrue(!i.isHungry(40));
    assertTrue(!i.isHungry(49));
    assertTrue(!i.isHungry(50));
    assertTrue(i.isHungry(51));
    assertTrue(i.isHungry(80));
    assertTrue(i.isHungry(100));
    //exception
    assertTrue(!i.isHungry(-20));
    assertTrue(i.isHungry(200));
    //more than full (should be correct by setNourriture(1) when setNourritureMax(1)).
    i.setNourriture(5);
    i.setNourritureMax(1);
    assertTrue(!i.isHungry(0));
    assertTrue(!i.isHungry(100));
    i.setNourritureMax(10);
    i.setNourriture(9);
    assertTrue(!i.isHungry(89));
    assertTrue(i.isHungry(100));
    i.setNourritureMax(100);
    i.setNourriture(2);
    assertTrue(!i.isHungry(2));
    assertTrue(i.isHungry(3));
    i.setNourritureMax(101);
    assertTrue(!i.isHungry(1));
    assertTrue(i.isHungry(2));
    assertTrue(i.isHungry(3));
  }
}
