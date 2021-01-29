package fr.formiko.formiko;


import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.usuel.debug;
import fr.formiko.usuel.erreur;
import fr.formiko.usuel.g;
import fr.formiko.usuel.tests.TestCaseMuet;
import fr.formiko.usuel.type.str;


public class PartieTest extends TestCaseMuet{

  // Fonctions propre -----------------------------------------------------------
  @Test
  public void testToString(){
    Partie p = new Partie();
    String s = p.toString();
    assertTrue(!str.contient(s,"@"));//on ne doit pas avoir de @ (de toString par défaut de java.)
  }
}
