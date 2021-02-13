package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.Carte;
import fr.formiko.usuel.chargerCarte;
import fr.formiko.usuel.types.str;
import fr.formiko.usuel.tests.TestCaseMuet;

public class PartieTest extends TestCaseMuet{
  private Partie p;
  // Fonctions propre -----------------------------------------------------------
  private void ini(){
    Main.initialisation();
    String nomCarte = "miniMonde";
    Carte mapo = new Carte(chargerCarte.chargerCarte(nomCarte));
    Partie p = new Partie(0,100,mapo,1);
    Main.setPartie(p);
    p.initialisationElément(0,1,1);
  }
  @Test
  public void testToString(){
    Partie p = new Partie();
    String s = p.toString();
    assertTrue(!str.contient(s,"@"));//on ne doit pas avoir de @ (de toString par défaut de java.)
  }
  @Test
  public void testLaunchGame(){
    ini();
    //p.launchGame();
  }

}
