package fr.formiko.formiko;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.formiko.Point;
import fr.formiko.usuel.tests.TestCaseMuet;
import fr.formiko.usuel.types.str;

public class PartieTest extends TestCaseMuet{
  private Partie p;
  // FUNCTIONS -----------------------------------------------------------------
  private void ini(int nbTurn, String mapName){
    Main.initialisation();
    Carte mapo = new Carte(mapName);
    p = new Partie(0,nbTurn,mapo,1);
    Main.setPartie(p);
    p.initialisationElément(0,1,1);
  }
  public void test1LaunchGame(int nbTurn, String mapName, int nbTry, int nbTryThatWorkMin){
    int cpt = 0;
    for (int i=0;i<nbTry ;i++ ) {
      ini(nbTurn,mapName);
      try {
        p.launchGame();
      }catch (Exception e) {
        System.out.println(e);
        e.getStackTrace();
        assertTrue(false);
      }
      if(p.getGj().getJoueurParId(1).getFere().getGc().getGcStade(0).length()>1){
        cpt++;
      }
    }
    System.out.println(cpt);//@a
    assertTrue(cpt>=nbTryThatWorkMin);
  }
  public void test2LaunchGame(int nbTurn, String mapName, int nbTry, int nbTryThatWorkMin){
    int cpt = 0;
    for (int i=0;i<nbTry ;i++ ) {
      ini(nbTurn,mapName);
      Main.getPartie().launchGame();
      if(p.getGj().getJoueurParId(1).getFere().getGc().getGcStade(0).length()>5){
        cpt++;
      }
    }
    assertTrue(cpt>=nbTryThatWorkMin);
  }
  @Test
  // @Disabled("Tooo long for standard test")
  public void testLaunchGame(){
    //test1LaunchGame(100,"miniWorld",10,9);
    //test1LaunchGame(80,"miniWorld",10,9);
    test1LaunchGame(50,"miniWorld",10,8); //at leaste 1 new ant.
    //TODO #199
    test2LaunchGame(100,"miniWorld",10,8); //at least 5 new ant.
    //test1LaunchGame(50,"jardin",10,8);
  }

  @Test
  public void testToString(){
    p = new Partie();
    String s = p.toString();
    assertTrue(!str.contient(s,"@"));//on ne doit pas avoir de @ (de toString par défaut de java.)
  }
  @Test
  public void testEquals(){
    Main.initialisation();
    Carte mapo = new Carte("miniWorld");
    p = new Partie(0,100,mapo,1);
    assertTrue(!p.equals(null));
    assertTrue(!p.equals(new Object()));
    assertTrue(!p.equals(new Point(0,0)));
    assertTrue(!p.equals(new Partie()));
  }

}
