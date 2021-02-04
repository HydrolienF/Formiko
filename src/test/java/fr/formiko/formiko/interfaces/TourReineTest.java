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
    assertTrue(p.getGc().getCCase(0,0).getContenu().getFere().equals(j.getFere()));
    assertTrue(p.getGc().getCCase(0,1).getContenu().getFere()==null);
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
    //TODO #182
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
    ((TourReine)(f.tour)).lay();
    assertEquals(2,f.getFere().getGc().length());

    //if queen help
    f = ini();
    Fourmi f2 = new Fourmi(f.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
    f.getFere().getGc().add(f2);
    f.setAction(f.getActionMax());
    f.setNourriture(f.getNourritureMax());
    ((TourReine)(f.tour)).lay();
    assertEquals(32,f.getFere().getGc().length());

    f = ini();
    f2 = new Fourmi(f.getFere(),Main.getEspeceParId(0), (byte) 3, (byte) 0);
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
    //TODO #182
  }
}
