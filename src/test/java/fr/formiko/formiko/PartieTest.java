package fr.formiko.formiko;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Carte;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.Partie;
import fr.formiko.tests.TestCaseMuet;
import fr.formiko.usual.Point;
import fr.formiko.usual.erreur;
import fr.formiko.usual.types.str;

public class PartieTest extends TestCaseMuet{
  private Partie p;
  private boolean granivore;
  private boolean insectivore;
  // FUNCTIONS -----------------------------------------------------------------
  private void ini(int nbTurn, String mapName){
    Main.initialisation();
    Carte mapo = new Carte(mapName);
    p = new Partie(0,nbTurn,mapo,1);
    Main.setPartie(p);
    if(granivore){
      if(insectivore){
        int t [] = {0,3};
        p.setAviableSpecies(t);
      }else{
        int t [] = {3};
        p.setAviableSpecies(t);
      }
    }else if(insectivore){
      int t [] = {0};
      p.setAviableSpecies(t);
    }
    p.initialisationElément(0,1,1);
  }
  public void test1LaunchGame(int nbTurn, String mapName, int nbTry, int nbTryThatWorkMin){
    int cpt = 0;
    for (int i=0;i<nbTry ;i++ ) {
      ini(nbTurn,mapName);
      try {
        p.launchGame();
      }catch (Exception e) {
        erreur.println(e);
        e.getStackTrace();
        assertTrue(false);
      }
      if(p.getGj().getJoueurById(1).getFere().getGc().getGcStade(0).length()>1){
        cpt++;
      }
    }
    System.out.println(cpt+">="+nbTryThatWorkMin);
    assertTrue(cpt>=nbTryThatWorkMin);
  }
  public void test2LaunchGame(int nbTurn, String mapName, int nbTry, int nbTryThatWorkMin){
    int cpt = 0;
    for (int i=0;i<nbTry ;i++ ) {
      ini(nbTurn,mapName);
      Main.getPartie().launchGame();
      if(p.getGj().getJoueurById(1).getFere().getGc().getGcStade(0).length()>5){
        cpt++;
      }
    }
    //TOFIX can fail some time
    assertTrue(cpt>=nbTryThatWorkMin);
  }
  @Test
  @Disabled("Tooo long for standard test")
  // It still have some issues with granivore ant, that don't survive that much.
  public void testLaunchGame(){
    //test1LaunchGame(100,"miniWorld",10,9);
    //test1LaunchGame(80,"miniWorld",10,9);
    double multTurn=1;
    for (int i=0; i<3; i++) {
      granivore=(i>0);
      insectivore=(i%2==0);
      if(granivore){
        multTurn=2;
      }
      System.out.println("test "+i+" in "+(50*multTurn)+" turns");
      test1LaunchGame((int)(50*multTurn),"miniWorld",10,8); //at leaste 1 new ant for 80% of the fere
      test2LaunchGame((int)(100*multTurn),"miniWorld",10,5); //at least 5 new ant for 50% of the fere
    }
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
