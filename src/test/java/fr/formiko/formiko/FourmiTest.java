package fr.formiko.formiko;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.Fourmi;
import fr.formiko.formiko.GCase;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.interfaces.*;
import fr.formiko.tests.TestCaseMuet;

public class FourmiTest extends TestCaseMuet{
  Partie p;

  // FUNCTIONS -----------------------------------------------------------------
  private Fourmi ini(){
    Main.initialisation();
    p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j);
    assertTrue(p.getGc().getCCase(0,0).getContent().getFere().equals(j.getFere()));
    assertTrue(p.getGc().getCCase(0,1).getContent().getFere()==null);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    j.getFere().getGc().add(f);
    assertEquals(1,j.getFere().getGc().length());
    return f;
  }

  @Test
  public void testWantFood(){
    Fourmi f = ini();
    assertEquals(1,f.getIndividu().getFoodConso(f.getStade()));
    f.setFood(f.getMaxFood());
    assertTrue(!f.wantFood());
    f.setFood(f.getMaxFood()/2);
    assertTrue(!f.wantFood());
    //can't eat for the 2 next turn
    f.setMaxFood(10);
    f.setFood(1);
    assertTrue(f.wantFood());
    //can't eat for the 2 next turn
    f.setMaxFood(2);
    f.setFood(1);
    assertTrue(f.wantFood());
    //can't eat for the 2 next turn but already full.
    f.setMaxFood(1);
    f.setFood(1);
    assertTrue(!f.wantFood());
    //don't have enoth food.
    f.setMaxFood(100);
    f.setFood(1);
    assertTrue(f.wantFood());
    f.setFood(2);
    assertTrue(f.wantFood());
    f.setFood(4);
    assertTrue(f.wantFood());
    f.setFood(5);
    assertTrue(!f.wantFood());
    f.setFood(6);
    assertTrue(!f.wantFood());
    f.setFood(10);
    assertTrue(!f.wantFood());
    f = ini();
    f.setStade(-3);
    assertTrue(!f.wantFood());
    f.setFood(1);
    assertTrue(!f.wantFood());
  }
  @Test
  public void testGetFemelle(){
    Fourmi f = ini();
    Creature c = f;
    assertTrue(f.getFemelle());
    assertTrue(c.getFemelle());
    c.setFemelle(false); //do not change anything for an ant.
    assertTrue(c.getFemelle());
    c = new Fourmi(f.getFere(),f.getEspece(),0);
    assertTrue(c.getFemelle());
    c = new Fourmi(f.getFere(),f.getEspece(),1);
    assertTrue(!c.getFemelle());
    /*c = new Fourmi(f.getFere(),f.getEspece(),2);
    assertTrue(c.getFemelle());*/
    c = new Fourmi(f.getFere(),f.getEspece(),3);
    assertTrue(c.getFemelle());
    //with an other specie.
    c = new Fourmi(f.getFere(),Main.getEspeceParId(1),4);
    assertTrue(c.getFemelle());
  }
  @Test
  public void testWantClean(){
    Fourmi f = ini();
    assertTrue(!f.wantClean());
    f.setHealth(55);
    assertTrue(!f.wantClean());
    f.setHealth(53);
    assertTrue(!f.wantClean());
    f.setHealth(52);
    assertTrue(f.wantClean());
    f.setHealth(50);
    assertTrue(f.wantClean());
    f.setHealth(0);
    assertTrue(f.wantClean());

    Main.getPartie().setDifficulté((byte)1);
    assertTrue(!f.getFere().getJoueur().getIa());
    f.setHealth(63);
    assertTrue(!f.wantClean());
    f.setHealth(62);
    assertTrue(f.wantClean());
    f.setHealth(60);
    assertTrue(f.wantClean());
    f.getFere().getJoueur().setIa(true);
    assertTrue(f.getFere().getJoueur().getIa());
    f.setHealth(43);
    assertTrue(!f.wantClean());
    f.setHealth(42);
    assertTrue(f.wantClean());
  }
  @Test
  public void testFourmi(){
    Fourmi f = ini();
    assertTrue(!f.getFere().getJoueur().getIa());
    assertTrue(f.tour instanceof TourFourmiNonIa);
    Joueur j2 = new Joueur(new Fourmiliere(Main.getPartie().getGc().getCCase(0,0),null),"joueurTest",true);
    j2.getFere().setJoueur(j2);
    Main.getPartie().getGj().add(j2);
    Fourmi f2 = new Fourmi(j2.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    j2.getFere().getGc().add(f2);
    assertTrue(f2.getFere().getJoueur().getIa());
    assertTrue(f2.tour instanceof TourFourmi);
    f2 = new Fourmi(j2.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j2.getFere().getGc().add(f2);
    assertTrue(f2.getFere().getJoueur().getIa());
    assertTrue(f2.tour instanceof TourReine);
  }
  @Test
  public void testIniTour(){
    Fourmi f = ini();
    f.getFere().getJoueur().setIa(false);
    f.iniTour();
    assertTrue(f.tour instanceof TourFourmiNonIa);
    f.getFere().getJoueur().setIa(true);
    f.setTypeF(0);
    assertTrue(f.estReine());
    f.iniTour();
    assertTrue(f.tour instanceof TourReine);
    f.setTypeF(3);
    f.iniTour();
    assertTrue(f.tour instanceof TourFourmi);

  }
  @Test
  public void testIniPheromone(){
    Fourmi f = ini();
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",false);
    j.getFere().setJoueur(j); p.getGj().add(j);
    f.setPheromone((byte)0,(byte)0,(byte)0);
    Fourmi fTest = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    fTest.setCCase(f.getCCase());
    fTest.setFere(f.getFere());
    fTest.iniPheromone();
    assertTrue(f.getEstAllié(fTest));
    assertTrue(fTest.getEstAllié(f));
    fTest = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    do { //it can be the same pheromone randomly, but it shouldn't work all the time.
      fTest.iniPheromone();
    } while (f.getEstAllié(fTest));
    assertTrue(!f.getEstAllié(fTest));
    assertTrue(!fTest.getEstAllié(f));

    Fourmi fQueen = new Fourmi(f.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    assertTrue(fQueen.estReine());
    f.getFere().getGc().add(fQueen);
    assertTrue(f.getEstAllié(fQueen));
    assertTrue(fQueen.getEstAllié(f));

    do { //it can be the same pheromone randomly, but it shouldn't work all the time.
      f.setPheromone(new Pheromone());
    } while (f.getEstAllié(fQueen));
    assertTrue(!fQueen.getEstAllié(f));

    fTest = new Fourmi(fQueen.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    assertTrue(fQueen.estReine());
    assertTrue(fQueen.getFere().equals(fTest.getFere()));
    assertTrue(fQueen.equals(fQueen.getReine()));
    assertTrue(fQueen.equals(fTest.getReine()));
    assertTrue(fQueen.getEstAllié(fTest));
    assertTrue(fTest.getEstAllié(fQueen));
  }
}
