package fr.formiko.formiko.interfaces;

import org.junit.jupiter.api.Test;

import fr.formiko.formiko.*;
import fr.formiko.formiko.Main;
import fr.formiko.formiko.interfaces.TourInsecte;
import fr.formiko.usuel.tests.TestCaseMuet;

public class TourReineTest extends TestCaseMuet{
  // Fonctions propre -----------------------------------------------------------
  private Fourmi ini(){
    Main.initialisation();
    Partie p = new Partie(0,100,new Carte(new GCase(1,2),0,0,1,false,false),1);
    Main.setPartie(p);
    p.setAppartionInsecte(false);
    p.setAppartionGraine(false);
    Joueur j = new Joueur(new Fourmiliere(p.getGc().getCCase(0,0),null),"joueurTest",true);
    j.getFere().setJoueur(j);
    assertTrue(p.getGc().getCCase(0,0).getContent().getFere().equals(j.getFere()));
    assertTrue(p.getGc().getCCase(0,1).getContent().getFere()==null);
    p.getGj().add(j);
    Fourmi f = new Fourmi(j.getFere(),Main.getEspeceParId(0), (byte) 0, (byte) 0);
    j.getFere().getGc().add(f);
    assertEquals(1,j.getFere().getGc().length());
    //f.tour = new TourReine();
    ((TourFourmi)(f.tour)).setF(f);
    return f;
  }
  @Test
  public void testNeedToWaitToLetNonQueenAntPlay(){
    Fourmi f = ini();
    assertTrue(!((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f.getFere().getGc().add(f2);
    f2.setAction(f2.getActionMax());
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.setStade(-2);
    assertTrue(f2.getAction()>0);
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.setStade(0);
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.setAction(0);
    assertTrue(!((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.tour = new TourReine();
    assertTrue(!((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.setAction(1);
    assertTrue(!((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.tour = new TourInsecte();
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.tour = new TourFourmi();
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());

    Fourmi f3 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f.getFere().getGc().add(f3);
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f3.setAction(0);f2.setAction(0);
    assertTrue(!((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f3.setAction(1);
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f3.setAction(-1);
    assertTrue(!((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());
    f2.setAction(1);
    assertTrue(((TourReine)(f.tour)).needToWaitToLetNonQueenAntPlay());

  }
  @Test
  public void testHaveSomeHelp(){
    Fourmi f = ini();
    assertTrue(!((TourReine)(f.tour)).haveSomeHelp());
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f.getFere().getGc().add(f2);
    assertTrue(((TourReine)(f.tour)).haveSomeHelp());
    f2.setCCase(0,1);
    assertTrue(((TourReine)(f.tour)).haveSomeHelp());
    f = ini();
    f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) -1);
    assertTrue(!((TourReine)(f.tour)).haveSomeHelp());
    f = ini();
    f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) -3);
    assertTrue(!((TourReine)(f.tour)).haveSomeHelp());
    f = ini();
    f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) -2);
    assertTrue(!((TourReine)(f.tour)).haveSomeHelp());
  }
  @Test
  public void testLay(){
    Fourmi f = ini();
    assertTrue(f.estReine());
    assertTrue(f.tour instanceof TourReine);
    f.setAction(0);
    ((TourReine)(f.tour)).lay();
    assertEquals(1,f.getFere().getGc().length());

    f.setAction(f.getActionMax());
    f.setNourriture(0);
    ((TourReine)(f.tour)).lay();
    assertEquals(1,f.getFere().getGc().length());

    //if queen alone
    f.setAction(f.getActionMax());
    f.setNourriture(f.getNourritureMax());
    assertTrue(!((TourReine)(f.tour)).haveSomeHelp());
    ((TourReine)(f.tour)).lay();
    assertEquals(2,f.getFere().getGc().length());

    //if queen help
    f = ini();
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f.getFere().getGc().add(f2);
    f.setAction(f.getActionMax());
    f.setNourriture(f.getNourritureMax());
    ((TourReine)(f.tour)).lay();
    assertEquals(32,f.getFere().getGc().length());

    f = ini();
    f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f.getFere().getGc().add(f2);
    f.setAction(f.getActionMax());
    f.setNourriture(f.getNourritureMax()/3);
    ((TourReine)(f.tour)).lay();
    assertEquals(4,f.getFere().getGc().length());
    assertTrue(f.getAction()>0);
    assertTrue(f.getNourriture()>(f.getNourritureMax()/5));
  }
  @Test
  public void TestTour(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,1);
    i.setNourritureFournie(300);
    f.setAction(5);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = f.getNourritureMax()/20+1;
    f.setNourriture(nourriture);
    f.tour();
    assertTrue(f.getAction()<=0);
    assertEquals(Main.getGc().getCCase(0,1),f.getCCase());
    assertEquals(nourriture - 3, f.getNourriture());
    assertTrue(f.getProprete()>f.getSeuilDeRisqueDInfection());
    assertTrue(i.getEstMort());
    assertEquals(2,Main.getGc().getCCase(0,1).getContent().getGc().length());
  }
  @Test
  public void TestTour2(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,1);
    i.setNourritureFournie(300);
    f.setAction(15);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = f.getNourritureMax()/20+1;
    f.setNourriture(nourriture);
    f.tour();
    assertTrue(f.getAction()<=0);
    assertEquals(Main.getGc().getCCase(0,1),f.getCCase());
    assertEquals(nourriture - 3, f.getNourriture());
    assertTrue(f.getProprete()>f.getSeuilDeRisqueDInfection());
    assertTrue(i.getEstMort());
    assertEquals(2,Main.getGc().getCCase(0,1).getContent().getGc().length());
  }
  @Test
  public void TestTour22(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,0);
    i.setNourritureFournie(300);
    f.setAction(15);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = f.getNourritureMax()/20+1;
    f.setNourriture(nourriture);
    f.tour();
    assertTrue(f.getAction()<=0);
    assertEquals(Main.getGc().getCCase(0,1),f.getCCase());
    assertEquals(nourriture - 3 + 300, f.getNourriture());
    assertTrue(f.getProprete()>f.getSeuilDeRisqueDInfection());
    assertTrue(i.getEstMort());
    assertEquals(1,Main.getGc().getCCase(0,1).getContent().getGc().length());
  }
  @Test
  public void TestTour3(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,1);
    i.setNourritureFournie(300);
    f.setAction(30);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = f.getNourritureMax()/20+1;
    f.setNourriture(nourriture);
    f.tour();
    assertTrue(f.getAction()<=0);
    assertEquals(Main.getGc().getCCase(0,1),f.getCCase());
    assertEquals(nourriture - 3 + 300, f.getNourriture());
    assertTrue(f.getProprete()>f.getSeuilDeRisqueDInfection());
    assertTrue(i.getEstMort());
    assertEquals(1,Main.getGc().getCCase(0,1).getContent().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,0).getContent().getGc().length());
  }
  @Test
  public void TestTour4(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,1);
    i.setNourritureFournie(300);
    f.setAction(50);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = f.getNourritureMax()/20+1;
    f.setNourriture(nourriture);
    f.tour();
    assertTrue(f.getAction()<=0);
    assertEquals(Main.getGc().getCCase(0,0),f.getCCase());
    assertTrue(f.getProprete()>f.getSeuilDeRisqueDInfection());
    assertTrue(i.getEstMort());
    assertEquals(0,Main.getGc().getCCase(0,1).getContent().getGc().length());
    assertEquals(2,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertEquals(1,Main.getGc().getCCase(0,0).getContent().getGc().getCouvain().length());
    assertEquals(nourriture - 3 + 300 - 12, f.getNourriture());
    assertEquals(10, Main.getGc().getCCase(0,0).getContent().getGc().getCouvain().getFirst().getNourriture());
  }
  @Test
  public void TestTour5(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f.getFere().getGc().add(f2);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,1);
    i.setNourritureFournie(300);
    f.setAction(50);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = 100; //need to hunt if eat(40) but not if eat(5).
    f.setNourriture(nourriture);
    f.tour();
    assertEquals(47,f.getAction());
  }
  @Test
  public void TestTour6(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f2.setAction(0);
    f.getFere().getGc().add(f2);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,1);
    i.setNourritureFournie(300);
    f.setAction(20);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = 100; //need to hunt if eat(40) but not if eat(5).
    f.setNourriture(nourriture);
    f.tour();
    assertTrue(f.getAction()<=0);
    assertEquals(Main.getGc().getCCase(0,0),f.getCCase());
    assertEquals(nourriture - 3, f.getNourriture());
    assertTrue(f.getProprete()>f.getSeuilDeRisqueDInfection());
    assertTrue(!i.getEstMort());
    assertEquals(1,Main.getGc().getCCase(0,1).getContent().getGc().length());
    assertEquals(2,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertEquals(0,Main.getGc().getCCase(0,0).getContent().getGc().getCouvain().length());
  }
  @Test
  public void TestTour7(){
    Fourmi f = ini();
    f.setCCase(0,1);
    Fourmi f2 = new Fourmi(f.getFere(),f.getEspece(), (byte) 3, (byte) 0);
    f2.setAction(0);
    f.getFere().getGc().add(f2);
    Insecte i = new Insecte(Main.getGc().getCCase(0,1),0,100,1);
    i.setNourritureFournie(300);
    f.setAction(50);
    f.setProprete(30);
    assertTrue(f.getProprete()<f.getSeuilDeRisqueDInfection());
    int nourriture = 200; //need to hunt if eat(40) but not if eat(5).
    f.setNourriture(nourriture);
    f.tour();
    assertTrue(f.getAction()<=0);
    assertEquals(Main.getGc().getCCase(0,0),f.getCCase());
    assertEquals(nourriture - 3 - (12*5), f.getNourriture());
    assertTrue(f.getProprete()>f.getSeuilDeRisqueDInfection());
    assertTrue(!i.getEstMort());
    assertEquals(1,Main.getGc().getCCase(0,1).getContent().getGc().length());
    assertEquals(7,Main.getGc().getCCase(0,0).getContent().getGc().length());
    assertEquals(5,Main.getGc().getCCase(0,0).getContent().getGc().getCouvain().length());
  }

  @Test
  public void testTourNull(){
    Fourmi f = ini();
    assertTrue(f!=null);
    f.setTypeF((byte)3);
    assertEquals(25,f.getIndividu().getNétoyage());
    //without action :
    f.setAction(0);
    f.setNourriture(19);
    assertEquals(0,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(19,f.getNourriture());
    assertEquals(100,f.getProprete());
    f.tour();
    assertEquals(1,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(18,f.getNourriture());
    assertTrue(f.getPropretéPerdu()!=0);
    assertEquals(100-f.getPropretéPerdu(),f.getProprete());

    f.setAction(0);
    f.setTypeF((byte)0);
    f.tour();
    assertEquals(2,f.getAge());
    assertEquals(0,f.getAction());
    assertEquals(15,f.getNourriture());
  }
}
